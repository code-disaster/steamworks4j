#include "SteamRemoteStorageCallback.h"
#include "SteamUtils.h"

SteamRemoteStorageCallback::SteamRemoteStorageCallback(JNIEnv* env, jobject callback)
    : SteamCallbackAdapter(env, callback)
    , m_CallbackPublishedFileSubscribed(this, &SteamRemoteStorageCallback::onPublishedFileSubscribed)
    , m_CallbackPublishedFileUnsubscribed(this, &SteamRemoteStorageCallback::onPublishedFileUnsubscribed)
    , m_CallbackPublishedFileDeleted(this, &SteamRemoteStorageCallback::onPublishedFileDeleted) {

}

SteamRemoteStorageCallback::~SteamRemoteStorageCallback() {

}

void SteamRemoteStorageCallback::onFileWriteAsyncComplete(RemoteStorageFileWriteAsyncComplete_t* callback, bool error) {
    invokeCallback({
        callVoidMethod(env, "onFileWriteAsyncComplete", "(I)V", (jint) callback->m_eResult);
    });
}

void SteamRemoteStorageCallback::onFileReadAsyncComplete(RemoteStorageFileReadAsyncComplete_t* callback, bool error) {
    invokeCallback({
        callVoidMethod(env, "onFileReadAsyncComplete", "(I)V", (jint) callback->m_eResult);
    });
}

void SteamRemoteStorageCallback::onFileShareResult(RemoteStorageFileShareResult_t* callback, bool error) {
	invokeCallback({
        callVoidMethod(env, "onFileShareResult", "(JLjava/lang/String;I)V",
            (jlong) callback->m_hFile, env->NewStringUTF(callback->m_rgchFilename), (jint) callback->m_eResult);
	});
}

void SteamRemoteStorageCallback::onDownloadUGCResult(RemoteStorageDownloadUGCResult_t* callback, bool error) {
    invokeCallback({
        callVoidMethod(env, "onDownloadUGCResult", "(JI)V", (jlong) callback->m_hFile, (jint) callback->m_eResult);
    });
}

void SteamRemoteStorageCallback::onPublishFileResult(RemoteStoragePublishFileResult_t* callback, bool error) {
    invokeCallback({
        callVoidMethod(env, "onPublishFileResult", "(JZI)V", (jlong) callback->m_nPublishedFileId,
            callback->m_bUserNeedsToAcceptWorkshopLegalAgreement, (jint) callback->m_eResult);
    });
}

void SteamRemoteStorageCallback::onUpdatePublishedFileResult(RemoteStorageUpdatePublishedFileResult_t* callback, bool error) {
    invokeCallback({
        callVoidMethod(env, "onUpdatePublishedFileResult", "(JZI)V", (jlong) callback->m_nPublishedFileId,
            callback->m_bUserNeedsToAcceptWorkshopLegalAgreement, (jint) callback->m_eResult);
    });
}

void SteamRemoteStorageCallback::onPublishedFileSubscribed(RemoteStoragePublishedFileSubscribed_t* callback) {
    invokeCallback({
        callVoidMethod(env, "onPublishedFileSubscribed", "(JI)V",
                (jlong) callback->m_nPublishedFileId, callback->m_nAppID);
    });
}

void SteamRemoteStorageCallback::onPublishedFileUnsubscribed(RemoteStoragePublishedFileUnsubscribed_t* callback) {
    invokeCallback({
        callVoidMethod(env, "onPublishedFileUnsubscribed", "(JI)V",
                (jlong) callback->m_nPublishedFileId, callback->m_nAppID);
    });
}

void SteamRemoteStorageCallback::onPublishedFileDeleted(RemoteStoragePublishedFileDeleted_t* callback) {
    invokeCallback({
        callVoidMethod(env, "onPublishedFileDeleted", "(JI)V",
                (jlong) callback->m_nPublishedFileId, callback->m_nAppID);
    });
}
