#include "SteamUGCCallback.h"

SteamUGCCallback::SteamUGCCallback(JNIEnv* env, jobject callback)
    : SteamCallbackAdapter(env, callback) {

}

SteamUGCCallback::~SteamUGCCallback() {

}
