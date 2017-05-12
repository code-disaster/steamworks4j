#pragma once

#include "SteamCallbackAdapter.h"
#include <steam_api.h>

class SteamMatchmakingPingResponse
    : public SteamCallbackAdapter
    , public ISteamMatchmakingPingResponse {

public:
	SteamMatchmakingPingResponse(JNIEnv* env, jobject callback);
	~SteamMatchmakingPingResponse();

	void ServerResponded(gameserveritem_t &server);
	void ServerFailedToRespond();
};
