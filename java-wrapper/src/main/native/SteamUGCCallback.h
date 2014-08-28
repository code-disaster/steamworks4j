#pragma once

#include "SteamCallbackAdapter.h"
#include <steam_api.h>

class SteamUGCCallback : public SteamCallbackAdapter {

public:
    SteamUGCCallback(JNIEnv* env, jobject callback);
    ~SteamUGCCallback();

	void onUGCQueryCompleted(SteamUGCQueryCompleted_t* callback, bool error);
	CCallResult<SteamUGCCallback, SteamUGCQueryCompleted_t> onUGCQueryCompletedCall;
};
