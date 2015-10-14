#include "SteamMatchmakingCallback.h"

SteamMatchmakingCallback::SteamMatchmakingCallback(JNIEnv* env, jobject callback)
	: SteamCallbackAdapter(env, callback) {

}

SteamMatchmakingCallback::~SteamMatchmakingCallback() {

}
