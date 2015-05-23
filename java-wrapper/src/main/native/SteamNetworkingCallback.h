#pragma once

#include "SteamCallbackAdapter.h"
#include <steam_gameserver.h>

class SteamNetworkingCallback : public SteamCallbackAdapter {

public:
	SteamNetworkingCallback(JNIEnv* env, jobject callback);
	~SteamNetworkingCallback();

	STEAM_CALLBACK(SteamNetworkingCallback, onP2PSessionConnectFail, P2PSessionConnectFail_t, m_CallbackP2PSessionConnectFail);
	STEAM_CALLBACK(SteamNetworkingCallback, onP2PSessionRequest, P2PSessionRequest_t, m_CallbackP2PSessionRequest);

	STEAM_GAMESERVER_CALLBACK(SteamNetworkingCallback, onGameServerP2PSessionConnectFail, P2PSessionConnectFail_t, m_CallbackGameServerP2PSessionConnectFail);
	STEAM_GAMESERVER_CALLBACK(SteamNetworkingCallback, onGameServerP2PSessionRequest, P2PSessionRequest_t, m_CallbackGameServerP2PSessionRequest);
};
