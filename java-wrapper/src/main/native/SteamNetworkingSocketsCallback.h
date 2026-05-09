#pragma once

#include "SteamCallbackAdapter.h"
#include <steam_api.h>

class SteamNetworkingSocketsCallback : public SteamCallbackAdapter {

public:
	SteamNetworkingSocketsCallback(JNIEnv* env, jobject callback);
	~SteamNetworkingSocketsCallback();

	STEAM_CALLBACK(SteamNetworkingSocketsCallback, onConnectionStatusChanged, SteamNetConnectionStatusChangedCallback_t);

};