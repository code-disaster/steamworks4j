package com.codedisaster.steamworks.test;

import com.codedisaster.steamworks.*;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SteamNetworkingTestApp extends SteamTestApp {

	private static final int defaultChannel = 1;
	private static final int readBufferCapacity = 4096;
	private static final int sendBufferCapacity = 4096;

	private SteamFriends friends;
	private SteamNetworking networking;

	private ByteBuffer packetReadBuffer = ByteBuffer.allocateDirect(readBufferCapacity);
	private ByteBuffer packetSendBuffer = ByteBuffer.allocateDirect(sendBufferCapacity);

	private Map<Integer, SteamID> friendUserIDs = new ConcurrentHashMap<Integer, SteamID>();
	private Map<Integer, SteamID> remoteUserIDs = new ConcurrentHashMap<Integer, SteamID>();

	private SteamFriendsCallback friendsCallback = new SteamFriendsCallback() {
		@Override
		public void onPersonaStateChange(SteamID steamID, SteamFriends.PersonaChange change) {
			System.out.println("Persona state changed: " +
					"accountID=" + steamID.getAccountID() + ", change=" + change.name());
		}
	};

	private SteamNetworkingCallback peer2peerCallback = new SteamNetworkingCallback() {
		@Override
		public void onP2PSessionConnectFail(SteamID steamIDRemote, SteamNetworking.P2PSessionError sessionError) {
			System.out.println("P2P connection failed: userID=" + steamIDRemote.getAccountID() +
					", error: " + sessionError);

			int userID = steamIDRemote.getAccountID();
			if (remoteUserIDs.containsKey(userID)) {
				remoteUserIDs.remove(userID);
			}
		}

		@Override
		public void onP2PSessionRequest(SteamID steamIDRemote) {
			System.out.println("P2P connection requested by userID " + steamIDRemote.getAccountID());
			remoteUserIDs.put(steamIDRemote.getAccountID(), steamIDRemote);
			networking.acceptP2PSessionWithUser(steamIDRemote);
		}
	};

	@Override
	protected void registerInterfaces() {
		friends = new SteamFriends(SteamAPI.getSteamFriendsPointer(), friendsCallback);
		networking = new SteamNetworking(SteamAPI.getSteamNetworkingPointer(), peer2peerCallback);

		networking.allowP2PPacketRelay(true);
	}

	@Override
	protected void processUpdate() throws SteamException {

		int packetSize = networking.isP2PPacketAvailable(defaultChannel);

		if (packetSize > 0) {

			SteamID sender = networking.readP2PPacket(packetReadBuffer, defaultChannel);

			if (sender != null) {

				int bytesReceived = packetReadBuffer.limit();
				System.out.println("Rcv packet: userID=" + sender.getAccountID() + ", " + bytesReceived + " bytes");

				byte[] bytes = new byte[bytesReceived];
				packetReadBuffer.get(bytes);

				String message;

				try {
					message = new String(bytes, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					throw new SteamException(e);
				}

				System.out.println("Rcv message: \"" + message + "\"");
			}

		}

	}

	@Override
	protected void processInput(String input) throws SteamException {

		if (input.startsWith("p2p send ")) {
			String[] params = input.substring("p2p send ".length()).split(" ");
			int receiverID = Integer.valueOf(params[0]);

			SteamID steamIDReceiver = null;
			if (remoteUserIDs.containsKey(receiverID)) {
				steamIDReceiver = remoteUserIDs.get(receiverID);
			} else if (friendUserIDs.containsKey(receiverID)) {
				steamIDReceiver = friendUserIDs.get(receiverID);
			} else {
				System.out.println("Error: unknown userID " + receiverID + " (no friend, not connected)");
			}

			if (steamIDReceiver != null) {

				packetSendBuffer.position(0);
				packetSendBuffer.limit(packetSendBuffer.capacity());

				byte[] bytes = params[1].getBytes(Charset.forName("UTF-8"));

				packetSendBuffer.put(bytes);
				packetSendBuffer.limit(packetSendBuffer.position());

				networking.sendP2PPacket(steamIDReceiver, packetSendBuffer,
						SteamNetworking.P2PSend.Unreliable, defaultChannel);

			} else {
				System.out.println("Error: unknown userID " + receiverID);
			}
		} else if (input.equals("p2p list")) {
			System.out.println("P2P connected users:");
			if (remoteUserIDs.size() == 0) {
				System.out.println("  none");
			}
			for (SteamID steamIDUser : remoteUserIDs.values()) {
				System.out.println("  " + steamIDUser.getAccountID());
			}
		} else if (input.equals("friends list")) {
			int friendsCount = friends.getFriendCount(SteamFriends.FriendFlags.Immediate);
			System.out.println(friendsCount + " friends");
			for (int i = 0; i < friendsCount; i++) {
				SteamID steamIDUser = friends.getFriendByIndex(i, SteamFriends.FriendFlags.Immediate);
				friendUserIDs.put(steamIDUser.getAccountID(), steamIDUser);
				String personaName = friends.getFriendPersonaName(steamIDUser);
				SteamFriends.PersonaState personaState = friends.getFriendPersonaState(steamIDUser);
				System.out.println("  " + steamIDUser.getAccountID() + "(" +
						personaName + ", " + personaState.name() + ")");
			}
		}

	}

	public static void main(String[] arguments) {
		new SteamNetworkingTestApp().clientMain(arguments);
	}

}
