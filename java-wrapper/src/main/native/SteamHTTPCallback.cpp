#include "SteamHTTPCallback.h"

SteamHTTPCallback::SteamHTTPCallback(JNIEnv* env, jobject callback)
	: SteamCallbackAdapter(env, callback)
	, m_CallbackHTTPRequestHeadersReceived(this, &SteamHTTPCallback::onHTTPRequestHeadersReceived)
	, m_CallbackHTTPRequestDataReceived(this, &SteamHTTPCallback::onHTTPRequestDataReceived) {

}

SteamHTTPCallback::~SteamHTTPCallback() {

}

void SteamHTTPCallback::onHTTPRequestCompleted(HTTPRequestCompleted_t* callback, bool error) {
	invokeCallback({
		callVoidMethod(env, "onHTTPRequestCompleted", "(JJZII)V",
			(jlong) callback->m_hRequest, (jlong) callback->m_ulContextValue, callback->m_bRequestSuccessful,
			(jint) callback->m_eStatusCode, (jint) callback->m_unBodySize);
	});
}

void SteamHTTPCallback::onHTTPRequestHeadersReceived(HTTPRequestHeadersReceived_t* callback) {
	invokeCallback({
		callVoidMethod(env, "onHTTPRequestHeadersReceived", "(JJ)V",
			(jlong) callback->m_hRequest, (jlong) callback->m_ulContextValue);
	});
}

void SteamHTTPCallback::onHTTPRequestDataReceived(HTTPRequestDataReceived_t* callback) {
	invokeCallback({
		callVoidMethod(env, "onHTTPRequestDataReceived", "(JJII)V",
			(jlong) callback->m_hRequest, (jlong) callback->m_ulContextValue,
			(jint) callback->m_cOffset, (jint) callback->m_cBytesReceived);
	});
}
