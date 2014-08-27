#include "SteamUserStatsCallback.h"

SteamUserStatsCallback::SteamUserStatsCallback(JNIEnv* env, jobject callback)
    : SteamCallbackAdapter(env, callback)
    , m_CallbackUserStatsReceived(this, &SteamUserStatsCallback::onUserStatsReceived)
    , m_CallbackUserStatsStored(this, &SteamUserStatsCallback::onUserStatsStored) {

}

SteamUserStatsCallback::~SteamUserStatsCallback() {

}

void SteamUserStatsCallback::onUserStatsReceived(UserStatsReceived_t* callback) {
	invokeCallback({
        callVoidMethod(env, "onUserStatsReceived", "(JJI)V", (jlong) callback->m_nGameID,
            (jlong) callback->m_steamIDUser.ConvertToUint64(), (jint) callback->m_eResult);
	});
}

void SteamUserStatsCallback::onUserStatsStored(UserStatsStored_t* callback) {
	invokeCallback({
    	callVoidMethod(env, "onUserStatsStored", "(JI)V", (jlong) callback->m_nGameID, (jint) callback->m_eResult);
    });
}
