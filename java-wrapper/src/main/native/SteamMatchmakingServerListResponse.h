#pragma once

#include "SteamCallbackAdapter.h"
#include <steam_api.h>

class SteamMatchmakingServerListResponse
	: public SteamCallbackAdapter
	, public ISteamMatchmakingServerListResponse {

public:
	SteamMatchmakingServerListResponse(JNIEnv* env, jobject callback);
	~SteamMatchmakingServerListResponse();

	void ServerResponded(HServerListRequest hRequest, int iServer);
	void ServerFailedToRespond(HServerListRequest hRequest, int iServer);
	void RefreshComplete(HServerListRequest hRequest, EMatchMakingServerResponse response);
};
