package com.codedisaster.steamworks.test.mixin;

import com.codedisaster.steamworks.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.codedisaster.steamworks.SteamNativeHandle.getNativeHandle;

public class FriendsMixin {

	private SteamFriends friends;
	private Map<Integer, SteamID> friendUserIDs = new ConcurrentHashMap<Integer, SteamID>();

	private SteamFriendsCallback friendsCallback = new SteamFriendsCallback() {
		@Override
		public void onSetPersonaNameResponse(boolean success, boolean localSuccess, SteamResult result) {
			System.out.println("Set persona name response: " +
					"success=" + success +
					", localSuccess=" + localSuccess +
					", result=" + result.name());
		}

		@Override
		public void onPersonaStateChange(SteamID steamID, SteamFriends.PersonaChange change) {

			switch (change) {

				case Name:
					System.out.println("Persona name received: " +
							"accountID=" + steamID.getAccountID() +
							", name='" + friends.getFriendPersonaName(steamID) + "'");
					break;

				default:
					System.out.println("Persona state changed (unhandled): " +
							"accountID=" + steamID.getAccountID() +
							", change=" + change.name());
					break;
			}
		}

		@Override
		public void onGameOverlayActivated(boolean active, boolean userInitiated, int appID) {

		}

		@Override
		public void onGameLobbyJoinRequested(SteamID steamIDLobby, SteamID steamIDFriend) {
			System.out.println("Game lobby join requested");
			System.out.println("  - lobby: " + Long.toHexString(getNativeHandle(steamIDLobby)));
			System.out.println("  - by friend accountID: " + steamIDFriend.getAccountID());
		}

		@Override
		public void onAvatarImageLoaded(SteamID steamID, int image, int width, int height) {

		}

		@Override
		public void onFriendRichPresenceUpdate(SteamID steamIDFriend, int appID) {

		}

		@Override
		public void onGameRichPresenceJoinRequested(SteamID steamIDFriend, String connect) {

		}

		@Override
		public void onGameServerChangeRequested(String server, String password)
		{
			
		}
	};

	public FriendsMixin() {
		friends = new SteamFriends(friendsCallback);
	}

	public void dispose() {
		friends.dispose();
	}

	public void processInput(String input) {

		if (input.equals("persona get")) {
			System.out.println("persona name: " + friends.getPersonaName());
		} else if (input.startsWith("persona set ")) {
			String personaName = input.substring("persona set ".length());
			friends.setPersonaName(personaName);
		} else if (input.equals("friends list")) {

			int friendsCount = friends.getFriendCount(SteamFriends.FriendFlags.Immediate);
			System.out.println(friendsCount + " friends");

			for (int i = 0; i < friendsCount; i++) {

				SteamID steamIDUser = friends.getFriendByIndex(i, SteamFriends.FriendFlags.Immediate);
				friendUserIDs.put(steamIDUser.getAccountID(), steamIDUser);

				String personaName = friends.getFriendPersonaName(steamIDUser);
				SteamFriends.PersonaState personaState = friends.getFriendPersonaState(steamIDUser);

				System.out.println("  - " + steamIDUser.getAccountID() + " (" +
						personaName + ", " + personaState.name() + ")");
			}
		}

	}

	public boolean isFriendAccountID(int accountID) {
		return friendUserIDs.containsKey(accountID);
	}

	public SteamID getFriendSteamID(int accountID) {
		return friendUserIDs.get(accountID);
	}

}
