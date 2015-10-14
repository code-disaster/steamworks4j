package com.codedisaster.steamworks;

public interface SteamMatchmakingCallback {

	void onFavoritesListChanged(int ip, int queryPort, int connPort, int appID, int flags, boolean add, int accountID);

	void onLobbyInvite(SteamID steamIDUser, SteamID steamIDLobby, long gameID);

	void onLobbyEnter(SteamID steamIDLobby, int chatPermissions, boolean blocked,
					  SteamMatchmaking.ChatRoomEnterResponse response);

	void onLobbyDataUpdate(SteamID steamIDLobby, SteamID steamIDMember, boolean success);

	void onLobbyChatUpdate(SteamID steamIDLobby, SteamID steamIDUserChanged,
						   SteamID steamIDMakingChange, SteamMatchmaking.ChatMemberStateChange stateChange);

	void onLobbyChatMessage(SteamID steamIDLobby, SteamID steamIDUser,
							SteamMatchmaking.ChatEntryType entryType, int chatID);

	void onLobbyGameCreated(SteamID steamIDLobby, SteamID steamIDGameServer, int ip, short port);

	void onLobbyMatchList(int lobbiesMatching);

	void onLobbyKicked(SteamID steamIDLobby, SteamID steamIDAdmin, boolean kickedDueToDisconnect);

	void onLobbyCreated(SteamResult result, SteamID steamIDLobby);

	void onFavoritesListAccountsUpdated(SteamResult result);

}
