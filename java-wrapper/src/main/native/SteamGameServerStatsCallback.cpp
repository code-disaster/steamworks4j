#include "SteamGameServerStatsCallback.h"

SteamGameServerStatsCallback::SteamGameServerStatsCallback(JNIEnv* env, jobject callback)
	: SteamCallbackAdapter(env, callback)
	, m_CallbackStatsReceived(this, &SteamGameServerStatsCallback::onStatsReceived)
	, m_CallbackStatsStored(this, &SteamGameServerStatsCallback::onStatsStored)
	, m_CallbackStatsUnloaded(this, &SteamGameServerStatsCallback::onStatsUnloaded) {
}

SteamGameServerStatsCallback::~SteamGameServerStatsCallback() {

}

void SteamGameServerStatsCallback::onStatsReceived(GSStatsReceived_t* callback) {
	invokeCallback({
		callVoidMethod(env, "onStatsReceived", "(IJ)V",
			(jint) callback->m_eResult,(jlong) callback->m_steamIDUser.ConvertToUint64());
	});
}

void SteamGameServerStatsCallback::onStatsStored(GSStatsStored_t* callback) {
	invokeCallback({
		callVoidMethod(env, "onStatsStored", "(IJ)V",
			(jint) callback->m_eResult,(jlong) callback->m_steamIDUser.ConvertToUint64());
	});
}

void SteamGameServerStatsCallback::onStatsUnloaded(GSStatsUnloaded_t* callback) {
	invokeCallback({
		callVoidMethod(env, "onStatsUnloaded", "(J)V",
			(jlong) callback->m_steamIDUser.ConvertToUint64());
	});
}