package com.codedisaster.steamworks.test;

import com.codedisaster.steamworks.*;
import com.codedisaster.steamworks.test.mixin.FriendsMixin;

import java.util.HashMap;
import java.util.Map;

public class SteamMatchmakingTestApp extends SteamTestApp {

	private FriendsMixin friends;
	private SteamMatchmaking matchmaking;
	private Map<Long, SteamID> lobbies = new HashMap<Long, SteamID>();

	private SteamMatchmakingCallback matchmakingCallback = new SteamMatchmakingCallback() {
		@Override
		public void onFavoritesListChanged(int ip, int queryPort, int connPort, int appID, int flags, boolean add, int accountID) {
			System.out.println("[onFavoritesListChanged]");
		}

		@Override
		public void onLobbyInvite(SteamID steamIDUser, SteamID steamIDLobby, long gameID) {
			System.out.println("LobbyInvite received for " + steamIDLobby);
			System.out.println("  - from user: " + steamIDUser);
			System.out.println("  - for game: " + gameID);

			System.out.println("  - auto-joining lobby ...");

			lobbies.put(steamIDLobby.getNativeHandle(), steamIDLobby);
			matchmaking.joinLobby(steamIDLobby);
		}

		@Override
		public void onLobbyEnter(SteamID steamIDLobby, int chatPermissions, boolean blocked, SteamMatchmaking.ChatRoomEnterResponse response) {
			System.out.println("Lobby entered: " + steamIDLobby);
			System.out.println("  - response: " + response);

			int numMembers = matchmaking.getNumLobbyMembers(steamIDLobby);
			System.out.println("  - " + numMembers + " members in lobby");
			for (int i = 0; i < numMembers; i++) {
				SteamID member = matchmaking.getLobbyMemberByIndex(steamIDLobby, i);
				System.out.println("    - " + i + ": accountID=" + member.getAccountID());
			}
		}

		@Override
		public void onLobbyDataUpdate(SteamID steamIDLobby, SteamID steamIDMember, boolean success) {
			System.out.println("[onLobbyDataUpdate]");
		}

		@Override
		public void onLobbyChatUpdate(SteamID steamIDLobby, SteamID steamIDUserChanged, SteamID steamIDMakingChange, SteamMatchmaking.ChatMemberStateChange stateChange) {
			System.out.println("LobbyChatUpdate for " + steamIDLobby);
			System.out.println("  - user changed: " + steamIDUserChanged);
			System.out.println("  - made by user: " + steamIDMakingChange);
			System.out.println("  - state changed: " + stateChange.name());
		}

		@Override
		public void onLobbyChatMessage(SteamID steamIDLobby, SteamID steamIDUser, SteamMatchmaking.ChatEntryType entryType, int chatID) {
			System.out.println("[onLobbyChatMessage]");
		}

		@Override
		public void onLobbyGameCreated(SteamID steamIDLobby, SteamID steamIDGameServer, int ip, short port) {
			System.out.println("[onLobbyGameCreated]");
		}

		@Override
		public void onLobbyMatchList(int lobbiesMatching) {
			System.out.println("Found " + lobbiesMatching + " matching lobbies.");

			lobbies.clear();
			for (int i = 0; i < lobbiesMatching; i++) {
				SteamID lobby = matchmaking.getLobbyByIndex(i);
				lobbies.put(lobby.getNativeHandle(), lobby);
			}

			listLobbies();
		}

		@Override
		public void onLobbyKicked(SteamID steamIDLobby, SteamID steamIDAdmin, boolean kickedDueToDisconnect) {
			System.out.println("Kicked from lobby: " + steamIDLobby);
			System.out.println("  - by user (admin): " + steamIDAdmin);
			System.out.println("  - kicked due to disconnect: " + (kickedDueToDisconnect ? "yes" : "no"));
		}

		@Override
		public void onLobbyCreated(SteamResult result, SteamID steamIDLobby) {
			System.out.println("Lobby created: " + steamIDLobby);
			System.out.println("  - result: " + result.name());
			if (result == SteamResult.OK) {
				lobbies.put(steamIDLobby.getNativeHandle(), steamIDLobby);
			}
		}

		@Override
		public void onFavoritesListAccountsUpdated(SteamResult result) {
			System.out.println("[onFavoritesListAccountsUpdated]");
		}
	};

	private void listLobbies() {
		for (Map.Entry<Long, SteamID> lobby : lobbies.entrySet()) {
			System.out.print("  " + Long.toHexString(lobby.getKey()) + ": ");
			if (lobby.getValue().isValid()) {
				int members = matchmaking.getNumLobbyMembers(lobby.getValue());
				System.out.println(members + " members");
			} else {
				System.err.println("invalid SteamID!");
			}
		}
	}

	@Override
	protected void registerInterfaces() {
		friends = new FriendsMixin();
		matchmaking = new SteamMatchmaking(matchmakingCallback);
	}

	@Override
	protected void unregisterInterfaces() {
		friends.dispose();
		matchmaking.dispose();
	}

	@Override
	protected void processUpdate() throws SteamException {

	}

	@Override
	protected void processInput(String input) throws SteamException {

		if (input.equals("lobby list")) {
			listLobbies();
		} else if (input.startsWith("lobby request ")) {
			int limit = Integer.parseInt(input.substring("lobby request ".length()));
			System.out.println("  requesting up to " + limit + " lobbies.");
			matchmaking.addRequestLobbyListResultCountFilter(limit);
			matchmaking.requestLobbyList();
		} else if (input.startsWith("lobby create ")) {
			int maxMembers = Integer.parseInt(input.substring("lobby create ".length()));
			System.out.println("  creating lobby for " + maxMembers+ " players.");
			matchmaking.createLobby(SteamMatchmaking.LobbyType.FriendsOnly, maxMembers);
		} else if (input.startsWith("lobby join ")) {
			long id = Long.parseLong(input.substring("lobby join ".length()), 16);
			if (lobbies.containsKey(id)) {
				System.out.println("  joining lobby " + id);
				matchmaking.joinLobby(lobbies.get(id));
			} else {
				System.err.println("No lobby found: " + id);
			}
		} else if (input.startsWith("lobby leave ")) {
			long id = Long.parseLong(input.substring("lobby leave ".length()), 16);
			if (lobbies.containsKey(id)) {
				System.out.println("  leaving lobby " + id);
				matchmaking.leaveLobby(lobbies.get(id));
				lobbies.remove(id);
			} else {
				System.err.println("No lobby found: " + id);
			}
		} else if (input.startsWith("lobby invite ")) {
			String[] ids = input.substring("lobby invite ".length()).split(" ");
			if (ids.length == 2) {
				long lobbyID = Long.parseLong(ids[0], 16);
				int playerAccountID = Integer.parseInt(ids[1]);
				if (lobbies.containsKey(lobbyID)) {
					System.out.println("  inviting player " + playerAccountID + "to lobby " + lobbyID);
					if (friends.isFriendAccountID(playerAccountID)) {
						matchmaking.inviteUserToLobby(lobbies.get(lobbyID), friends.getFriendSteamID(playerAccountID));
					} else {
						System.err.println("No player (friend) found: " + playerAccountID);
					}
				} else {
					System.err.println("No lobby found: " + lobbyID);
				}
			} else {
				System.err.println("Expecting: 'lobby invite <lobbyID> <userID>'");
			}
		}

		friends.processInput(input);
	}

	public static void main(String[] arguments) {
		new SteamMatchmakingTestApp().clientMain(arguments);
	}

}
