package com.codedisaster.steamworks.test;

import com.codedisaster.steamworks.*;
import com.codedisaster.steamworks.test.mixin.FriendsMixin;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SteamNetworkingTest extends SteamTestApp {

	private static final int defaultChannel = 1;
	private static final Charset messageCharset = Charset.forName("UTF-8");

	private static final int readBufferCapacity = 4096;
	private static final int sendBufferCapacity = 4096;

	private FriendsMixin friends;
	private SteamNetworking networking;

	private ByteBuffer packetReadBuffer = ByteBuffer.allocateDirect(readBufferCapacity);
	private ByteBuffer packetSendBuffer = ByteBuffer.allocateDirect(sendBufferCapacity);

	private SteamUser user;
	private Map<Integer, SteamID> remoteUserIDs = new ConcurrentHashMap<Integer, SteamID>();

	private SteamAuthTicket userAuthTicket;
	private ByteBuffer userAuthTicketData = ByteBuffer.allocateDirect(256);

	private SteamID remoteAuthUser;
	private ByteBuffer remoteAuthTicketData = ByteBuffer.allocateDirect(256);

	private final byte[] AUTH = "AUTH".getBytes(Charset.defaultCharset());

	private SteamUserCallback userCallback = new SteamUserCallback() {
		@Override
		public void onAuthSessionTicket(SteamAuthTicket authTicket, SteamResult result) {

		}

		@Override
		public void onValidateAuthTicket(SteamID steamID,
										 SteamAuth.AuthSessionResponse authSessionResponse,
										 SteamID ownerSteamID) {

			System.out.println("Auth session response for userID " + steamID.getAccountID() + ": " +
					authSessionResponse.name() + ", borrowed=" + (steamID.equals(ownerSteamID) ? "yes" : "no"));

			if (authSessionResponse == SteamAuth.AuthSessionResponse.AuthTicketCanceled) {
				// ticket owner has cancelled the ticket, end the session
				endAuthSession();
			}
		}

		@Override
		public void onMicroTxnAuthorization(int appID, long orderID, boolean authorized) {

		}

		@Override
		public void onEncryptedAppTicket(SteamResult result) {

		}
	};

	private SteamNetworkingCallback peer2peerCallback = new SteamNetworkingCallback() {
		@Override
		public void onP2PSessionConnectFail(SteamID steamIDRemote, SteamNetworking.P2PSessionError sessionError) {
			System.out.println("P2P connection failed: userID=" + steamIDRemote.getAccountID() +
					", error: " + sessionError);

			unregisterRemoteSteamID(steamIDRemote);
		}

		@Override
		public void onP2PSessionRequest(SteamID steamIDRemote) {
			System.out.println("P2P connection requested by userID " + steamIDRemote.getAccountID());
			registerRemoteSteamID(steamIDRemote);
			networking.acceptP2PSessionWithUser(steamIDRemote);
		}
	};

	@Override
	protected void registerInterfaces() {
		user = new SteamUser(userCallback);

		friends = new FriendsMixin();
		networking = new SteamNetworking(peer2peerCallback);

		networking.allowP2PPacketRelay(true);
	}

	@Override
	protected void unregisterInterfaces() {
		cancelAuthTicket();
		user.dispose();
		friends.dispose();
		networking.dispose();
	}

	@Override
	protected void processUpdate() throws SteamException {

		int[] packetSize = new int[1];
		if (networking.isP2PPacketAvailable(defaultChannel, packetSize)) {

			SteamID steamIDSender = new SteamID();

			if (packetSize[0] > packetReadBuffer.capacity()) {
				throw new SteamException("incoming packet larger than read buffer can handle");
			}

			packetReadBuffer.clear();
			// this isn't needed actually, buffer passed in can be larger than message to read
			packetReadBuffer.limit(packetSize[0]);

			int packetReadSize = networking.readP2PPacket(steamIDSender, packetReadBuffer, defaultChannel);

			if (packetReadSize == 0) {
				System.err.println("Rcv packet: expected " + packetSize[0] + " bytes, but got none");
			} else if (packetReadSize < packetSize[0]) {
				System.err.println("Rcv packet: expected " + packetSize[0] + " bytes, but only got " + packetReadSize);
			}

			// limit to actual data received
			packetReadBuffer.limit(packetReadSize);

			if (packetReadSize > 0) {

				// register, if unknown
				registerRemoteSteamID(steamIDSender);

				int bytesReceived = packetReadBuffer.limit();
				System.out.println("Rcv packet: userID=" + steamIDSender.getAccountID() + ", " + bytesReceived + " bytes");

				byte[] bytes = new byte[bytesReceived];
				packetReadBuffer.get(bytes);

				// check for magic bytes first
				int magicBytes = checkMagicBytes(packetReadBuffer, AUTH);
				if (magicBytes > 0) {
					// extract ticket
					remoteAuthTicketData.clear();
					remoteAuthTicketData.put(bytes, magicBytes, bytesReceived - magicBytes);
					remoteAuthTicketData.flip();
					System.out.println("Auth ticket received: " + remoteAuthTicketData.toString() +
							" [hash: " + remoteAuthTicketData.hashCode() + "]");
					// auth
					beginAuthSession(steamIDSender);
				} else {
					// plain text message
					String message = new String(bytes, messageCharset);
					System.out.println("Rcv message: \"" + message + "\"");
				}
			}
		}
	}

	@Override
	protected void processInput(String input) throws SteamException {

		if (input.startsWith("p2p send ")) {
			String[] params = input.substring("p2p send ".length()).split(" ");
			int receiverID = Integer.parseInt(params[0]);

			SteamID steamIDReceiver = null;
			if (remoteUserIDs.containsKey(receiverID)) {
				steamIDReceiver = remoteUserIDs.get(receiverID);
			} else if (friends.isFriendAccountID(receiverID)) {
				steamIDReceiver = friends.getFriendSteamID(receiverID);
			} else {
				System.out.println("Error: unknown userID " + receiverID + " (no friend, not connected)");
			}

			if (steamIDReceiver != null) {

				packetSendBuffer.clear(); // pos=0, limit=cap

				for (int i = 1; i < params.length; i++) {
					byte[] bytes = params[i].getBytes(messageCharset);
					if (i > 1) {
						packetSendBuffer.put((byte) 0x20);
					}
					packetSendBuffer.put(bytes);
				}

				packetSendBuffer.flip(); // limit=pos, pos=0

				networking.sendP2PPacket(steamIDReceiver, packetSendBuffer,
						SteamNetworking.P2PSend.Unreliable, defaultChannel);
			}
		} else if (input.startsWith("p2p close ")) {
			int remoteID = Integer.parseInt(input.substring("p2p close ".length()));

			SteamID steamIDRemote = null;
			if (remoteUserIDs.containsKey(remoteID)) {
				steamIDRemote = remoteUserIDs.get(remoteID);
			} else {
				System.out.println("Error: unknown remote ID " + remoteID + " (not connected)");
			}

			if (steamIDRemote != null) {
				networking.closeP2PSessionWithUser(steamIDRemote);
				unregisterRemoteSteamID(steamIDRemote);
			}
		} else if (input.equals("p2p list")) {
			System.out.println("P2P connected users:");
			if (remoteUserIDs.size() == 0) {
				System.out.println("  none");
			}
			for (SteamID steamIDUser : remoteUserIDs.values()) {
				System.out.println("  " + steamIDUser.getAccountID());
			}
		} else if (input.startsWith("auth ticket ")) {
			String authCmd = input.substring("auth ticket ".length());
			if (authCmd.equals("get")) {
				getAuthTicket();
			} else if (authCmd.equals("cancel")) {
				cancelAuthTicket();
			} else if (authCmd.equals("send")) {
				broadcastAuthTicket();
			} else if (authCmd.equals("end")) {
				endAuthSession();
			}
		}

		friends.processInput(input);
	}

	private void registerRemoteSteamID(SteamID steamIDUser) {
		if (!remoteUserIDs.containsKey(steamIDUser.getAccountID())) {
			remoteUserIDs.put(steamIDUser.getAccountID(), steamIDUser);
		}
	}

	private void unregisterRemoteSteamID(SteamID steamIDUser) {
		remoteUserIDs.remove(steamIDUser.getAccountID());
	}

	private void getAuthTicket() throws SteamException {
		cancelAuthTicket();
		userAuthTicketData.clear();
		int[] sizeRequired = new int[1];
		userAuthTicket = user.getAuthSessionTicket(userAuthTicketData, sizeRequired);
		if (userAuthTicket.isValid()) {
			int numBytes = userAuthTicketData.limit();
			System.out.println("Auth session ticket length: " + numBytes);
			System.out.println("Auth ticket created: " + userAuthTicketData.toString() +
					" [hash: " + userAuthTicketData.hashCode() + "]");
		} else {
			if (sizeRequired[0] < userAuthTicketData.capacity()) {
				System.out.println("Error: failed creating auth ticket");
			} else {
				System.out.println("Error: buffer too small for auth ticket, need " + sizeRequired[0] + " bytes");
			}
		}
	}

	private void cancelAuthTicket() {
		if (userAuthTicket != null && userAuthTicket.isValid()) {
			System.out.println("Auth ticket cancelled");
			user.cancelAuthTicket(userAuthTicket);
			userAuthTicket = null;
		}
	}

	private void beginAuthSession(SteamID steamIDSender) throws SteamException {
		endAuthSession();
		System.out.println("Starting auth session with user: " + steamIDSender.getAccountID());
		remoteAuthUser = steamIDSender;
		user.beginAuthSession(remoteAuthTicketData, remoteAuthUser);
	}

	private void endAuthSession() {
		if (remoteAuthUser != null) {
			System.out.println("End auth session with user: " + remoteAuthUser.getAccountID());
			user.endAuthSession(remoteAuthUser);
			remoteAuthUser = null;
		}
	}

	private void broadcastAuthTicket() throws SteamException {
		if (userAuthTicket == null || !userAuthTicket.isValid()) {
			System.out.println("Error: won't broadcast nil auth ticket");
			return;
		}

		for (Map.Entry<Integer, SteamID> remoteUser : remoteUserIDs.entrySet()) {

			System.out.println("Send auth to remote user: " + remoteUser.getKey() +
					"[hash: " + userAuthTicketData.hashCode() + "]");

			packetSendBuffer.clear(); // pos=0, limit=cap

			packetSendBuffer.put(AUTH); // magic bytes
			packetSendBuffer.put(userAuthTicketData);

			userAuthTicketData.flip(); // limit=pos, pos=0
			packetSendBuffer.flip(); // limit=pos, pos=0

			networking.sendP2PPacket(remoteUser.getValue(), packetSendBuffer,
					SteamNetworking.P2PSend.Reliable, defaultChannel);
		}
	}

	private int checkMagicBytes(ByteBuffer buffer, byte[] magicBytes) {
		for (int b = 0; b < magicBytes.length; b++) {
			if (buffer.get(b) != magicBytes[b]) {
				return 0;
			}
		}
		return magicBytes.length;
	}

	public static void main(String[] arguments) {
		new SteamNetworkingTest().clientMain(arguments);
	}

}
