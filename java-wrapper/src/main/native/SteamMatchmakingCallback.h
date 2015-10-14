#pragma once

#include "SteamCallbackAdapter.h"
#include <steam_api.h>

class SteamMatchmakingCallback : public SteamCallbackAdapter {

public:
	SteamMatchmakingCallback(JNIEnv* env, jobject callback);
	~SteamMatchmakingCallback();
};
