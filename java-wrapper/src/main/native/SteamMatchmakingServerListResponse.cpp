#include "SteamMatchmakingServerListResponse.h"

SteamMatchmakingServerListResponse::SteamMatchmakingServerListResponse(JNIEnv* env, jobject callback)
	: SteamCallbackAdapter(env, callback) {

}

SteamMatchmakingServerListResponse::~SteamMatchmakingServerListResponse() {

}

void SteamMatchmakingServerListResponse::ServerResponded(HServerListRequest hRequest, int iServer) {
	invokeCallback({
		callVoidMethod(env, "serverResponded", "(JI)V", (jlong) hRequest, iServer);
	});
}

void SteamMatchmakingServerListResponse::ServerFailedToRespond(HServerListRequest hRequest, int iServer) {
	invokeCallback({
		callVoidMethod(env, "serverFailedToRespond", "(JI)V", (jlong) hRequest, iServer);
	});
}

void SteamMatchmakingServerListResponse::RefreshComplete(HServerListRequest hRequest, EMatchMakingServerResponse response) {
	invokeCallback({
		callVoidMethod(env, "refreshComplete", "(JI)V", (jlong) hRequest, (jint) response);
	});
}
