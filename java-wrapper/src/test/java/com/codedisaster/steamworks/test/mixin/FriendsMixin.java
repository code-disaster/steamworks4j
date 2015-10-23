package com.codedisaster.steamworks.test.mixin;

import com.codedisaster.steamworks.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FriendsMixin {

	private SteamFriends friends;
	private Map<Integer, SteamID> friendUserIDs = new ConcurrentHashMap<Integer, SteamID>();

	private SteamFriendsCallback friendsCallback = new SteamFriendsCallback() {
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
		public void onGameLobbyJoinRequested(SteamID steamIDLobby, SteamID steamIDFriend) {
			System.out.println("Game lobby join requested");
			System.out.println("  - lobby: " + Long.toHexString(steamIDLobby.getNativeHandle()));
			System.out.println("  - by friend accountID: " + steamIDFriend.getAccountID());
		}
	};

	public FriendsMixin() {
		friends = new SteamFriends(friendsCallback);
	}

	public void dispose() {
		friends.dispose();
	}

	public void processInput(String input) {

		if (input.equals("friends list")) {

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
