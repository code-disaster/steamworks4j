package com.codedisaster.steamworks.test;

import com.codedisaster.steamworks.*;

public class SteamMatchmakingTestApp extends SteamTestApp {

	private SteamMatchmaking matchmaking;

	private SteamMatchmakingCallback matchmakingCallback = new SteamMatchmakingCallback() {
		@Override
		public void onFavoritesListChanged(int ip, int queryPort, int connPort, int appID, int flags, boolean add, int accountID) {
			System.out.println("- onFavoritesListChanged");
		}

		@Override
		public void onLobbyInvite(SteamID steamIDUser, SteamID steamIDLobby, long gameID) {
			System.out.println("- onLobbyInvite");
		}

		@Override
		public void onLobbyEnter(SteamID steamIDLobby, int chatPermissions, boolean blocked, SteamMatchmaking.ChatRoomEnterResponse response) {
			System.out.println("- onLobbyEnter");
		}

		@Override
		public void onLobbyDataUpdate(SteamID steamIDLobby, SteamID steamIDMember, boolean success) {
			System.out.println("- onLobbyDataUpdate");
		}

		@Override
		public void onLobbyChatUpdate(SteamID steamIDLobby, SteamID steamIDUserChanged, SteamID steamIDMakingChange, SteamMatchmaking.ChatMemberStateChange stateChange) {
			System.out.println("- onLobbyChatUpdate");
		}

		@Override
		public void onLobbyChatMessage(SteamID steamIDLobby, SteamID steamIDUser, SteamMatchmaking.ChatEntryType entryType, int chatID) {
			System.out.println("- onLobbyChatMessage");
		}

		@Override
		public void onLobbyGameCreated(SteamID steamIDLobby, SteamID steamIDGameServer, int ip, short port) {
			System.out.println("- onLobbyGameCreated");
		}

		@Override
		public void onLobbyMatchList(int lobbiesMatching) {
			System.out.println("Found " + lobbiesMatching + " matching lobbies.");
			for (int i = 0; i < lobbiesMatching; i++) {
				SteamID lobby = matchmaking.getLobbyByIndex(i);
				System.out.print("  " + i + ": ");
				if (lobby.isValid()) {
					int members = matchmaking.getNumLobbyMembers(lobby);
					System.out.println(members + " members");
					/* member IDs are invalid if not in that lobby
					for (int j = 0; j < members; j++) {
						SteamID member = matchmaking.getLobbyMemberByIndex(lobby, j);
						System.out.print("    " + j + ": ");
						if (member.isValid()) {
							System.out.println("accountID=" + member.getAccountID());
						} else {
							System.out.println("invalid SteamID!");
						}
					}*/
				} else {
					System.out.println("invalid SteamID!");
				}
			}
		}

		@Override
		public void onLobbyKicked(SteamID steamIDLobby, SteamID steamIDAdmin, boolean kickedDueToDisconnect) {
			System.out.println("- onLobbyKicked");
		}

		@Override
		public void onLobbyCreated(SteamResult result, SteamID steamIDLobby) {
			System.out.println("- onLobbyCreated");
		}

		@Override
		public void onFavoritesListAccountsUpdated(SteamResult result) {
			System.out.println("- onFavoritesListAccountsUpdated");
		}
	};

	@Override
	protected void registerInterfaces() {
		matchmaking = new SteamMatchmaking(matchmakingCallback);
	}

	@Override
	protected void unregisterInterfaces() {
		matchmaking.dispose();
	}

	@Override
	protected void processUpdate() throws SteamException {

	}

	@Override
	protected void processInput(String input) throws SteamException {

		if (input.startsWith("lobby request ")) {
			int limit = Integer.parseInt(input.substring("lobby request ".length()));
			System.out.println("  requesting up to " + limit + " lobbies.");
			matchmaking.addRequestLobbyListResultCountFilter(limit);
			matchmaking.requestLobbyList();
		}

	}

	public static void main(String[] arguments) {
		new SteamMatchmakingTestApp().clientMain(arguments);
	}

}
