#include "SteamMatchmakingPlayersResponse.h"

SteamMatchmakingPlayersResponse::SteamMatchmakingPlayersResponse(JNIEnv* env, jobject callback)
	: SteamCallbackAdapter(env, callback) {

}

SteamMatchmakingPlayersResponse::~SteamMatchmakingPlayersResponse() {
}

void SteamMatchmakingPlayersResponse::AddPlayerToList(const char *pchName, int nScore, float flTimePlayed) {
	invokeCallback({
		callVoidMethod(env, "addPlayerToList", "(Ljava/lang/String;IF)V",
			env->NewStringUTF(pchName), nScore, flTimePlayed);
	});
}

void SteamMatchmakingPlayersResponse::PlayersFailedToRespond() {
	invokeCallback({
		callVoidMethod(env, "playersFailedToRespond", "()V");
	});
}

void SteamMatchmakingPlayersResponse::PlayersRefreshComplete() {
	invokeCallback({
		callVoidMethod(env, "playersRefreshComplete", "()V");
	});
}
