#include "SteamMatchmakingRulesResponse.h"

SteamMatchmakingRulesResponse::SteamMatchmakingRulesResponse(JNIEnv* env, jobject callback)
	: SteamCallbackAdapter(env, callback) {

}

SteamMatchmakingRulesResponse::~SteamMatchmakingRulesResponse() {

}

void SteamMatchmakingRulesResponse::RulesResponded(const char *pchRule, const char *pchValue) {
	invokeCallback({
		callVoidMethod(env, "rulesResponded", "(Ljava/lang/String;Ljava/lang/String;)V",
			env->NewStringUTF(pchRule), env->NewStringUTF(pchValue));
	});
}

void SteamMatchmakingRulesResponse::RulesFailedToRespond() {
	invokeCallback({
		callVoidMethod(env, "rulesFailedToRespond", "()V");
	});
}

void SteamMatchmakingRulesResponse::RulesRefreshComplete() {
	invokeCallback({
		callVoidMethod(env, "rulesRefreshComplete", "()V");
	});
}
