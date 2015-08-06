#pragma once

#include "SteamCallbackAdapter.h"
#include <steam_api.h>

class SteamHTTPCallback : public SteamCallbackAdapter {

public:
	SteamHTTPCallback(JNIEnv* env, jobject callback);
	~SteamHTTPCallback();

	STEAM_CALLBACK(SteamHTTPCallback, onHTTPRequestCompleted, HTTPRequestCompleted_t, m_CallbackHTTPRequestCompleted);
	STEAM_CALLBACK(SteamHTTPCallback, onHTTPRequestHeadersReceived, HTTPRequestHeadersReceived_t, m_CallbackHTTPRequestHeadersReceived);
	STEAM_CALLBACK(SteamHTTPCallback, onHTTPRequestDataReceived, HTTPRequestDataReceived_t, m_CallbackHTTPRequestDataReceived);
};
