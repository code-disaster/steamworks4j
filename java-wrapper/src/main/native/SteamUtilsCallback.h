#pragma once

#include "SteamCallbackAdapter.h"
#include <steam_api.h>

class SteamUtilsCallback : public SteamCallbackAdapter {

public:
	SteamUtilsCallback(JNIEnv* env, jobject callback);
	~SteamUtilsCallback();

	void enableWarningMessageHook(bool enabled);
	void onWarningMessage(int severity, const char *debugText);
};
