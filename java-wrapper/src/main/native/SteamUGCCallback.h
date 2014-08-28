#pragma once

#include "SteamCallbackAdapter.h"
#include <steam_api.h>

class SteamUGCCallback : public SteamCallbackAdapter {

public:
    SteamUGCCallback(JNIEnv* env, jobject callback);
    ~SteamUGCCallback();

    STEAM_CALLBACK(SteamUGCCallback, onUGCQueryCompleted, SteamUGCQueryCompleted_t, m_CallbackUGCQueryCompleted);
};
