#include "SteamUGCCallback.h"

SteamUGCCallback::SteamUGCCallback(JNIEnv* env, jobject callback)
    : SteamCallbackAdapter(env, callback)
    , m_CallbackUGCQueryCompleted(this, &SteamUGCCallback::onUGCQueryCompleted) {

}

SteamUGCCallback::~SteamUGCCallback() {

}

void SteamUGCCallback::onUGCQueryCompleted(SteamUGCQueryCompleted_t* callback) {
	invokeCallback({
        callVoidMethod(env, "onUGCQueryCompleted", "(JIIZI)V", (jlong) callback->m_handle,
            (jint) callback->m_unNumResultsReturned, (jint) callback->m_unTotalMatchingResults,
            (jboolean) callback->m_bCachedData, (jint) callback->m_eResult);
	});
}
