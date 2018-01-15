#include "SteamUGCCallback.h"

SteamUGCCallback::SteamUGCCallback(JNIEnv* env, jobject callback)
	: SteamCallbackAdapter(env, callback)
	, m_CallbackDownloadItemResult(this, &SteamUGCCallback::onDownloadItemResult) {

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

void SteamUGCCallback::onDeleteItem(DeleteItemResult_t* callback, bool error) {
	invokeCallback({
		callVoidMethod(env, "onDeleteItem", "(JI)V", (jlong) callback->m_nPublishedFileId, (jint) callback->m_eResult);
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

void SteamUGCCallback::onCreateItem(CreateItemResult_t* callback, bool error) {
	invokeCallback({
		callVoidMethod(env, "onCreateItem", "(JZI)V", (jlong) callback->m_nPublishedFileId,
		    (jboolean) callback->m_bUserNeedsToAcceptWorkshopLegalAgreement, (jint) callback->m_eResult);
	});
}

void SteamUGCCallback::onSubmitItemUpdate(SubmitItemUpdateResult_t* callback, bool error) {
	invokeCallback({
		callVoidMethod(env, "onSubmitItemUpdate", "(JZI)V", (jlong) callback->m_nPublishedFileId,
		    (jboolean) callback->m_bUserNeedsToAcceptWorkshopLegalAgreement, (jint) callback->m_eResult);
	});
}

void SteamUGCCallback::onDownloadItemResult(DownloadItemResult_t* callback) {
	invokeCallback({
		callVoidMethod(env, "onDownloadItemResult", "(IJI)V", (jint) callback->m_unAppID,
		    (jlong) callback->m_nPublishedFileId, (jint) callback->m_eResult);
	});
}

void SteamUGCCallback::onUserFavoriteItemsListChanged(UserFavoriteItemsListChanged_t* callback, bool error) {
	invokeCallback({
		callVoidMethod(env, "onUserFavoriteItemsListChanged", "(JZI)V", (jlong) callback->m_nPublishedFileId,
		    (jboolean) callback->m_bWasAddRequest, (jint) callback->m_eResult);
	});
}

void SteamUGCCallback::onSetUserItemVote(SetUserItemVoteResult_t* callback, bool error) {
	invokeCallback({
		callVoidMethod(env, "onSetUserItemVote", "(JZI)V", (jlong) callback->m_nPublishedFileId,
		    (jboolean) callback->m_bVoteUp, (jint) callback->m_eResult);
	});
}

void SteamUGCCallback::onGetUserItemVote(GetUserItemVoteResult_t* callback, bool error) {
	invokeCallback({
		callVoidMethod(env, "onGetUserItemVote", "(JZZZI)V", (jlong) callback->m_nPublishedFileId,
		    (jboolean) callback->m_bVotedUp, (jboolean) callback->m_bVotedDown,
		    (jboolean) callback->m_bVoteSkipped, (jint) callback->m_eResult);
	});
}

void SteamUGCCallback::onStartPlaytimeTracking(StartPlaytimeTrackingResult_t* callback, bool error) {
	invokeCallback({
		callVoidMethod(env, "onStartPlaytimeTracking", "(I)V", (jint) callback->m_eResult);
	});
}

void SteamUGCCallback::onStopPlaytimeTracking(StopPlaytimeTrackingResult_t* callback, bool error) {
	invokeCallback({
		callVoidMethod(env, "onStopPlaytimeTracking", "(I)V", (jint) callback->m_eResult);
	});
}

void SteamUGCCallback::onStopPlaytimeTrackingForAllItems(StopPlaytimeTrackingResult_t* callback, bool error) {
	invokeCallback({
		callVoidMethod(env, "onStopPlaytimeTrackingForAllItems", "(I)V", (jint) callback->m_eResult);
	});
}
