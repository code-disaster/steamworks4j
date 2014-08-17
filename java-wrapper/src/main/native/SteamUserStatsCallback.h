#pragma once

#include "SteamCallbackAdapter.h"
#include <steam_api.h>

class SteamUserStatsCallback : public SteamCallbackAdapter {

public:
    SteamUserStatsCallback(JNIEnv* env, jobject callback);
    ~SteamUserStatsCallback();

    STEAM_CALLBACK(SteamUserStatsCallback, onUserStatsReceived, UserStatsReceived_t, m_CallbackUserStatsReceived);
	STEAM_CALLBACK(SteamUserStatsCallback, onUserStatsStored, UserStatsStored_t, m_CallbackUserStatsStored);

};
