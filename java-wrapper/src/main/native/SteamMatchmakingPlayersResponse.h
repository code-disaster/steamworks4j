#pragma once

#include "SteamCallbackAdapter.h"
#include <steam_api.h>

class SteamMatchmakingPlayersResponse
	: public SteamCallbackAdapter
	, public ISteamMatchmakingPlayersResponse {

public:
	SteamMatchmakingPlayersResponse(JNIEnv* env, jobject callback);
	~SteamMatchmakingPlayersResponse();

	void AddPlayerToList(const char *pchName, int nScore, float flTimePlayed);
	void PlayersFailedToRespond();
	void PlayersRefreshComplete();
};
