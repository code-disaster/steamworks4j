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

void SteamUGCCallback::onRequestUGCDetails(SteamUGCRequestUGCDetailsResult_t* callback, bool error) {
	invokeCallback({
		callVoidMethod(env, "onRequestUGCDetails", "(JILjava/lang/String;Ljava/lang/String;JJLjava/lang/String;ZIIJII)V",
			(jlong) callback->m_details.m_nPublishedFileId, (jint) callback->m_details.m_eResult,
			env->NewStringUTF(callback->m_details.m_rgchTitle), env->NewStringUTF(callback->m_details.m_rgchDescription),
			callback->m_details.m_hFile, callback->m_details.m_hPreviewFile, env->NewStringUTF(callback->m_details.m_pchFileName),
			(jboolean) callback->m_bCachedData, (jint) callback->m_details.m_unVotesUp,
			(jint) callback->m_details.m_unVotesDown, (jlong) callback->m_details.m_ulSteamIDOwner,
			(jint) callback->m_details.m_rtimeCreated, (jint) callback->m_details.m_rtimeUpdated);
	});
}

