#include "SteamNetworkingSocketsCallback.h"

SteamNetworkingSocketsCallback::SteamNetworkingSocketsCallback(JNIEnv* env, jobject callback) : 
	SteamCallbackAdapter(env, callback) {
}

SteamNetworkingSocketsCallback::~SteamNetworkingSocketsCallback() {
}

void SteamNetworkingSocketsCallback::onConnectionStatusChanged(SteamNetConnectionStatusChangedCallback_t* callback) {
	invokeCallback({
			callVoidMethod(env, "onConnectionStatusChanged", "(IJII)V",
				callback->m_hConn,
				callback->m_info.m_identityRemote.GetSteamID64(),
				callback->m_info.m_eState,
				callback->m_eOldState);
		});
}