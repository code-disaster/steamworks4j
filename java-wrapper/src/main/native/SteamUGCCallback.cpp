#include "SteamUGCCallback.h"

SteamUGCCallback::SteamUGCCallback(JNIEnv* env, jobject callback)
    : SteamCallbackAdapter(env, callback) {

}

SteamUGCCallback::~SteamUGCCallback() {

}

void SteamUGCCallback::onUGCQueryCompleted(SteamUGCQueryCompleted_t* callback, bool error) {
	invokeCallback({
        callVoidMethod(env, "onUGCQueryCompleted", "(JIIZI)V", (jlong) callback->m_handle,
            (jint) callback->m_unNumResultsReturned, (jint) callback->m_unTotalMatchingResults,
            (jboolean) callback->m_bCachedData, (jint) callback->m_eResult);
	});
}

void SteamUGCCallback::onSubscribeItem(RemoteStorageSubscribePublishedFileResult_t* callback, bool error) {
	invokeCallback({
        callVoidMethod(env, "onSubscribeItem", "(JI)V", (jlong) callback->m_nPublishedFileId, (jint) callback->m_eResult);
	});
}

void SteamUGCCallback::onUnsubscribeItem(RemoteStorageUnsubscribePublishedFileResult_t* callback, bool error) {
	invokeCallback({
        callVoidMethod(env, "onUnsubscribeItem", "(JI)V", (jlong) callback->m_nPublishedFileId, (jint) callback->m_eResult);
	});
}
