#pragma once

#include "SteamGameServerCallbackAdapter.h"
#include <steam_gameserver.h>

class SteamGameServerStatsCallback : public SteamGameServerCallbackAdapter {

public:
	SteamGameServerStatsCallback(JNIEnv* env, jobject callback);
	~SteamGameServerStatsCallback();

	STEAM_GAMESERVER_CALLBACK(SteamGameServerStatsCallback, onStatsReceived, GSStatsReceived_t, m_CallbackStatsReceived);
	STEAM_GAMESERVER_CALLBACK(SteamGameServerStatsCallback, onStatsStored, GSStatsStored_t, m_CallbackStatsStored);
	STEAM_GAMESERVER_CALLBACK(SteamGameServerStatsCallback, onStatsUnloaded, GSStatsUnloaded_t, m_CallbackStatsUnloaded);
};
