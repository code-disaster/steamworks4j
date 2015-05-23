#include "SteamNetworkingCallback.h"

SteamNetworkingCallback::SteamNetworkingCallback(JNIEnv* env, jobject callback)
	: SteamCallbackAdapter(env, callback)
	, m_CallbackP2PSessionConnectFail(this, &SteamNetworkingCallback::onP2PSessionConnectFail)
	, m_CallbackP2PSessionRequest(this, &SteamNetworkingCallback::onP2PSessionRequest)
	, m_CallbackGameServerP2PSessionConnectFail(this, &SteamNetworkingCallback::onGameServerP2PSessionConnectFail)
	, m_CallbackGameServerP2PSessionRequest(this, &SteamNetworkingCallback::onGameServerP2PSessionRequest) {

}

SteamNetworkingCallback::~SteamNetworkingCallback() {

}

void SteamNetworkingCallback::onP2PSessionConnectFail(P2PSessionConnectFail_t* callback) {
	invokeCallback({
		callVoidMethod(env, "onP2PSessionConnectFail", "(JI)V",
			(jlong) callback->m_steamIDRemote.ConvertToUint64(), (jint) callback->m_eP2PSessionError);
	});
}

void SteamNetworkingCallback::onP2PSessionRequest(P2PSessionRequest_t* callback) {
	invokeCallback({
		callVoidMethod(env, "onP2PSessionRequest", "(J)V",
			(jlong) callback->m_steamIDRemote.ConvertToUint64());
	});
}

void SteamNetworkingCallback::onGameServerP2PSessionConnectFail(P2PSessionConnectFail_t* callback) {
	invokeCallback({
		callVoidMethod(env, "onP2PSessionConnectFail", "(JI)V",
			(jlong) callback->m_steamIDRemote.ConvertToUint64(), (jint) callback->m_eP2PSessionError);
	});
}

void SteamNetworkingCallback::onGameServerP2PSessionRequest(P2PSessionRequest_t* callback) {
	invokeCallback({
		callVoidMethod(env, "onP2PSessionRequest", "(J)V",
			(jlong) callback->m_steamIDRemote.ConvertToUint64());
	});
}
