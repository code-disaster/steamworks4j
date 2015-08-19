#pragma once

#include "SteamCallbackAdapter.h"
#include <steam_api.h>

class SteamHTTPCallback : public SteamCallbackAdapter {

public:
	SteamHTTPCallback(JNIEnv* env, jobject callback);
	~SteamHTTPCallback();

	void onHTTPRequestCompleted(HTTPRequestCompleted_t* callback, bool error);
	CCallResult<SteamHTTPCallback, HTTPRequestCompleted_t> onHTTPRequestCompletedCall;

	STEAM_CALLBACK(SteamHTTPCallback, onHTTPRequestHeadersReceived, HTTPRequestHeadersReceived_t, m_CallbackHTTPRequestHeadersReceived);
	STEAM_CALLBACK(SteamHTTPCallback, onHTTPRequestDataReceived, HTTPRequestDataReceived_t, m_CallbackHTTPRequestDataReceived);
};
