#pragma once

#include "SteamCallbackAdapter.h"
#include <steam_api.h>

class SteamNetworkingCallback : public SteamCallbackAdapter {

public:
	SteamNetworkingCallback(JNIEnv* env, jobject callback);
	~SteamNetworkingCallback();

	STEAM_CALLBACK(SteamNetworkingCallback, onP2PSessionConnectFail, P2PSessionConnectFail_t, m_CallbackP2PSessionConnectFail);
	STEAM_CALLBACK(SteamNetworkingCallback, onP2PSessionRequest, P2PSessionRequest_t, m_CallbackP2PSessionRequest);
};
