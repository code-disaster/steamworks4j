package com.codedisaster.steamworks.test;

import com.codedisaster.steamworks.*;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SteamNetworkingTestApp extends SteamTestApp {

	private static final int defaultChannel = 1;
	private static final Charset messageCharset = Charset.forName("UTF-8");

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
		friends = new SteamFriends(friendsCallback);
		networking = new SteamNetworking(peer2peerCallback, SteamNetworking.API.Client);

		networking.allowP2PPacketRelay(true);
	}

	@Override
	protected void unregisterInterfaces() {
		friends.dispose();
		networking.dispose();
	}

	@Override
	protected void processUpdate() throws SteamException {

		int packetSize = networking.isP2PPacketAvailable(defaultChannel);

		if (packetSize > 0) {

			SteamID steamIDSender = new SteamID();

			if (networking.readP2PPacket(steamIDSender, packetReadBuffer, defaultChannel) > 0) {

				int bytesReceived = packetReadBuffer.limit();
				System.out.println("Rcv packet: userID=" + steamIDSender.getAccountID() + ", " + bytesReceived + " bytes");

				byte[] bytes = new byte[bytesReceived];
				packetReadBuffer.get(bytes);

				String message = new String(bytes, messageCharset);

				System.out.println("Rcv message: \"" + message + "\"");

				// register, if unknown
				registerRemoteSteamID(steamIDSender);
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
			int remoteID = Integer.valueOf(input.substring("p2p close ".length()));

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
		} else if (input.equals("friends list")) {
			int friendsCount = friends.getFriendCount(SteamFriends.FriendFlags.Immediate);
			System.out.println(friendsCount + " friends");
			for (int i = 0; i < friendsCount; i++) {
				SteamID steamIDUser = friends.getFriendByIndex(i, SteamFriends.FriendFlags.Immediate);
				friendUserIDs.put(steamIDUser.getAccountID(), steamIDUser);
				String personaName = friends.getFriendPersonaName(steamIDUser);
				SteamFriends.PersonaState personaState = friends.getFriendPersonaState(steamIDUser);
				System.out.println("  " + steamIDUser.getAccountID() + " (" +
						personaName + ", " + personaState.name() + ")");
			}
		}

	}

	private void registerRemoteSteamID(SteamID steamIDUser) {
		if (!remoteUserIDs.containsKey(steamIDUser.getAccountID())) {
			remoteUserIDs.put(steamIDUser.getAccountID(), steamIDUser);
		}
	}

	private void unregisterRemoteSteamID(SteamID steamIDUser) {
		remoteUserIDs.remove(steamIDUser.getAccountID());
	}

	public static void main(String[] arguments) {
		new SteamNetworkingTestApp().clientMain(arguments);
	}

}
