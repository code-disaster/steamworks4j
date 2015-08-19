#pragma once

#include "SteamCallbackAdapter.h"
#include <steam_gameserver.h>

class SteamGameServerHTTPCallback : public SteamCallbackAdapter {

public:
	SteamGameServerHTTPCallback(JNIEnv* env, jobject callback);
	~SteamGameServerHTTPCallback();

	void onHTTPRequestCompleted(HTTPRequestCompleted_t* callback, bool error);
	CCallResult<SteamGameServerHTTPCallback, HTTPRequestCompleted_t> onHTTPRequestCompletedCall;

	STEAM_GAMESERVER_CALLBACK(SteamGameServerHTTPCallback, onHTTPRequestHeadersReceived, HTTPRequestHeadersReceived_t, m_CallbackHTTPRequestHeadersReceived);
	STEAM_GAMESERVER_CALLBACK(SteamGameServerHTTPCallback, onHTTPRequestDataReceived, HTTPRequestDataReceived_t, m_CallbackHTTPRequestDataReceived);
};
