#pragma once

#include "SteamCallbackAdapter.h"
#include <steam_api.h>

class SteamMatchmakingRulesResponse
	: public SteamCallbackAdapter
	, public ISteamMatchmakingRulesResponse {

public:
	SteamMatchmakingRulesResponse(JNIEnv* env, jobject callback);
	~SteamMatchmakingRulesResponse();

	void RulesResponded(const char *pchRule, const char *pchValue);
	void RulesFailedToRespond();
	void RulesRefreshComplete();
};
