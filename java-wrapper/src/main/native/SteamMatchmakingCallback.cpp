#include "SteamMatchmakingCallback.h"

SteamMatchmakingCallback::SteamMatchmakingCallback(JNIEnv* env, jobject callback)
	: SteamCallbackAdapter(env, callback) {

}

SteamMatchmakingCallback::~SteamMatchmakingCallback() {

}

void SteamMatchmakingCallback::onLobbyMatchList(LobbyMatchList_t* callback, bool error) {
	invokeCallback({
		callVoidMethod(env, "onLobbyMatchList", "(I)V", (jint) callback->m_nLobbiesMatching);
	});
}
