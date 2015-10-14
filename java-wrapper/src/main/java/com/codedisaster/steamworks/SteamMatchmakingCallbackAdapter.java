package com.codedisaster.steamworks;

@SuppressWarnings("unused")
class SteamMatchmakingCallbackAdapter extends SteamCallbackAdapter<SteamMatchmakingCallback> {

	private static final SteamMatchmaking.ChatMemberStateChange[] stateChangeValues =
			SteamMatchmaking.ChatMemberStateChange.values();

	SteamMatchmakingCallbackAdapter(SteamMatchmakingCallback callback) {
		super(callback);
	}

	void onFavoritesListChanged(int ip, int queryPort, int connPort, int appID, int flags, boolean add, int accountID) {
		callback.onFavoritesListChanged(ip, queryPort, connPort, appID, flags, add, accountID);
	}

	void onLobbyInvite(long steamIDUser, long steamIDLobby, long gameID) {
		callback.onLobbyInvite(new SteamID(steamIDUser), new SteamID(steamIDLobby), gameID);
	}

	void onLobbyEnter(long steamIDLobby, int chatPermissions, boolean blocked, int response) {
		callback.onLobbyEnter(new SteamID(steamIDLobby), chatPermissions, blocked,
				SteamMatchmaking.ChatRoomEnterResponse.byCode(response));
	}

	void onLobbyDataUpdate(long steamIDLobby, long steamIDMember, boolean success) {
		callback.onLobbyDataUpdate(new SteamID(steamIDLobby), new SteamID(steamIDMember), success);
	}

	void onLobbyChatUpdate(long steamIDLobby, long steamIDUserChanged, long steamIDMakingChange, int stateChange) {
		SteamID lobby = new SteamID(steamIDLobby);
		SteamID userChanged = new SteamID(steamIDUserChanged);
		SteamID makingChange = new SteamID(steamIDMakingChange);
		for (SteamMatchmaking.ChatMemberStateChange value : stateChangeValues) {
			if (SteamMatchmaking.ChatMemberStateChange.isSet(value, stateChange)) {
				callback.onLobbyChatUpdate(lobby, userChanged, makingChange, value);
			}
		}
	}

	void onLobbyChatMessage(long steamIDLobby, long steamIDUser, int entryType, int chatID) {
		callback.onLobbyChatMessage(new SteamID(steamIDLobby), new SteamID(steamIDUser),
				SteamMatchmaking.ChatEntryType.byCode(entryType), chatID);
	}

	void onLobbyGameCreated(long steamIDLobby, long steamIDGameServer, int ip, short port) {
		callback.onLobbyGameCreated(new SteamID(steamIDLobby), new SteamID(steamIDGameServer), ip, port);
	}

	void onLobbyMatchList(int lobbiesMatching) {
		callback.onLobbyMatchList(lobbiesMatching);
	}

	void onLobbyKicked(long steamIDLobby, long steamIDAdmin, boolean kickedDueToDisconnect) {
		callback.onLobbyKicked(new SteamID(steamIDLobby), new SteamID(steamIDAdmin), kickedDueToDisconnect);
	}

	void onLobbyCreated(int result, long steamIDLobby) {
		callback.onLobbyCreated(SteamResult.byValue(result), new SteamID(steamIDLobby));
	}

	void onFavoritesListAccountsUpdated(int result) {
		callback.onFavoritesListAccountsUpdated(SteamResult.byValue(result));
	}

}
