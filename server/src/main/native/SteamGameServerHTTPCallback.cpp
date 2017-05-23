#include "SteamGameServerHTTPCallback.h"

SteamGameServerHTTPCallback::SteamGameServerHTTPCallback(JNIEnv* env, jobject callback)
	: SteamGameServerCallbackAdapter(env, callback)
	, m_CallbackHTTPRequestHeadersReceived(this, &SteamGameServerHTTPCallback::onHTTPRequestHeadersReceived)
	, m_CallbackHTTPRequestDataReceived(this, &SteamGameServerHTTPCallback::onHTTPRequestDataReceived) {

}

SteamGameServerHTTPCallback::~SteamGameServerHTTPCallback() {

}

void SteamGameServerHTTPCallback::onHTTPRequestCompleted(HTTPRequestCompleted_t* callback, bool error) {
	invokeCallback({
		callVoidMethod(env, "onHTTPRequestCompleted", "(JJZII)V",
			(jlong) callback->m_hRequest, (jlong) callback->m_ulContextValue, callback->m_bRequestSuccessful,
			(jint) callback->m_eStatusCode, (jint) callback->m_unBodySize);
	});
}

void SteamGameServerHTTPCallback::onHTTPRequestHeadersReceived(HTTPRequestHeadersReceived_t* callback) {
	invokeCallback({
		callVoidMethod(env, "onHTTPRequestHeadersReceived", "(JJ)V",
			(jlong) callback->m_hRequest, (jlong) callback->m_ulContextValue);
	});
}

void SteamGameServerHTTPCallback::onHTTPRequestDataReceived(HTTPRequestDataReceived_t* callback) {
	invokeCallback({
		callVoidMethod(env, "onHTTPRequestDataReceived", "(JJII)V",
			(jlong) callback->m_hRequest, (jlong) callback->m_ulContextValue,
			(jint) callback->m_cOffset, (jint) callback->m_cBytesReceived);
	});
}
