#include "SteamGameServerNetworkingCallback.h"

SteamGameServerNetworkingCallback::SteamGameServerNetworkingCallback(JNIEnv* env, jobject callback)
	: SteamCallbackAdapter(env, callback)
	, m_CallbackP2PSessionConnectFail(this, &SteamGameServerNetworkingCallback::onP2PSessionConnectFail)
	, m_CallbackP2PSessionRequest(this, &SteamGameServerNetworkingCallback::onP2PSessionRequest) {

}

SteamGameServerNetworkingCallback::~SteamGameServerNetworkingCallback() {

}

void SteamGameServerNetworkingCallback::onP2PSessionConnectFail(P2PSessionConnectFail_t* callback) {
	invokeCallback({
		callVoidMethod(env, "onP2PSessionConnectFail", "(JI)V",
			(jlong) callback->m_steamIDRemote.ConvertToUint64(), (jint) callback->m_eP2PSessionError);
	});
}

void SteamGameServerNetworkingCallback::onP2PSessionRequest(P2PSessionRequest_t* callback) {
	invokeCallback({
		callVoidMethod(env, "onP2PSessionRequest", "(J)V",
			(jlong) callback->m_steamIDRemote.ConvertToUint64());
	});
}
