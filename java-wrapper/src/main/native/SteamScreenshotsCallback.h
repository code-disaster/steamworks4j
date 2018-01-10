#pragma once

#include "SteamCallbackAdapter.h"
#include <steam_api.h>

class SteamScreenshotsCallback : public SteamCallbackAdapter {

public:
    SteamScreenshotsCallback(JNIEnv* env, jobject callback);
    ~SteamScreenshotsCallback();

	STEAM_CALLBACK(SteamScreenshotsCallback, onScreenshotReady, ScreenshotReady_t, m_CallbackScreenshotReady);
	STEAM_CALLBACK(SteamScreenshotsCallback, onScreenshotRequested, ScreenshotRequested_t, m_CallbackScreenshotRequested);
};
