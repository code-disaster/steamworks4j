#pragma once

#include "SteamCallbackAdapter.h"
#include <steam_api.h>

class SteamMatchmakingCallback : public SteamCallbackAdapter {

public:
	SteamMatchmakingCallback(JNIEnv* env, jobject callback);
	~SteamMatchmakingCallback();

	void onLobbyMatchList(LobbyMatchList_t* callback, bool error);
	CCallResult<SteamMatchmakingCallback, LobbyMatchList_t> onLobbyMatchListCall;

	void onLobbyCreated(LobbyCreated_t* callback, bool error);
	CCallResult<SteamMatchmakingCallback, LobbyCreated_t> onLobbyCreatedCall;

	void onLobbyEnter(LobbyEnter_t* callback, bool error);
	CCallResult<SteamMatchmakingCallback, LobbyEnter_t> onLobbyEnterCall;

	void onLobbyInvite(LobbyInvite_t* callback, bool error);
	CCallResult<SteamMatchmakingCallback, LobbyInvite_t> onLobbyInviteCall;

	void onLobbyChatUpdate(LobbyChatUpdate_t* callback, bool error);
	CCallResult<SteamMatchmakingCallback, LobbyChatUpdate_t> onLobbyChatUpdateCall;
};
