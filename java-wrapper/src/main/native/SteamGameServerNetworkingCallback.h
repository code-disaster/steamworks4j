#pragma once

#include "SteamCallbackAdapter.h"
#include <steam_gameserver.h>

class SteamGameServerNetworkingCallback : public SteamCallbackAdapter {

public:
	SteamGameServerNetworkingCallback(JNIEnv* env, jobject callback);
	~SteamGameServerNetworkingCallback();

	STEAM_GAMESERVER_CALLBACK(SteamGameServerNetworkingCallback, onP2PSessionConnectFail, P2PSessionConnectFail_t, m_CallbackP2PSessionConnectFail);
	STEAM_GAMESERVER_CALLBACK(SteamGameServerNetworkingCallback, onP2PSessionRequest, P2PSessionRequest_t, m_CallbackP2PSessionRequest);
};
