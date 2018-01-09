#pragma once

#include "SteamCallbackAdapter.h"
#include <steam_api.h>

class SteamScreenshotsCallback : public SteamCallbackAdapter {

public:
    SteamScreenshotsCallback(JNIEnv* env, jobject callback);
    ~SteamScreenshotsCallback();

    void onScreenshotReady(ScreenshotReady_t* callback, bool error);
	CCallResult<SteamScreenshotsCallback, ScreenshotReady_t> onScreenshotReadyCall;

    void onScreenshotRequested(ScreenshotRequested_t* callback, bool error);
	CCallResult<SteamScreenshotsCallback, ScreenshotRequested_t> onScreenshotRequestedCall;
};
