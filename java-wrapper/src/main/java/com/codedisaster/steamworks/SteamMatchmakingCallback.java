package com.codedisaster.steamworks;

public interface SteamMatchmakingCallback {

	default void onFavoritesListChanged(int ip, int queryPort, int connPort, int appID, int flags, boolean add, int accountID) {
	}

	default void onLobbyInvite(SteamID steamIDUser, SteamID steamIDLobby, long gameID) {
	}

	default void onLobbyEnter(SteamID steamIDLobby, int chatPermissions, boolean blocked,
							  SteamMatchmaking.ChatRoomEnterResponse response) {
	}

	default void onLobbyDataUpdate(SteamID steamIDLobby, SteamID steamIDMember, boolean success) {
	}

	default void onLobbyChatUpdate(SteamID steamIDLobby, SteamID steamIDUserChanged,
								   SteamID steamIDMakingChange, SteamMatchmaking.ChatMemberStateChange stateChange) {
	}

	default void onLobbyChatMessage(SteamID steamIDLobby, SteamID steamIDUser,
									SteamMatchmaking.ChatEntryType entryType, int chatID) {
	}

	default void onLobbyGameCreated(SteamID steamIDLobby, SteamID steamIDGameServer, int ip, short port) {
	}

	default void onLobbyMatchList(int lobbiesMatching) {
	}

	default void onLobbyKicked(SteamID steamIDLobby, SteamID steamIDAdmin, boolean kickedDueToDisconnect) {
	}

	default void onLobbyCreated(SteamResult result, SteamID steamIDLobby) {
	}

	default void onFavoritesListAccountsUpdated(SteamResult result) {
	}

}
