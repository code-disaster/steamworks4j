#pragma once

#include "SteamCallbackAdapter.h"
#include <steam_api.h>

class SteamMatchmakingCallback : public SteamCallbackAdapter {

public:
	SteamMatchmakingCallback(JNIEnv* env, jobject callback);
	~SteamMatchmakingCallback();

	void onLobbyMatchList(LobbyMatchList_t* callback, bool error);
	CCallResult<SteamMatchmakingCallback, LobbyMatchList_t> onLobbyMatchListCall;
};
