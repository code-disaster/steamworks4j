#include "SteamRemoteStorageCallback.h"
#include "SteamUtils.h"

SteamRemoteStorageCallback::SteamRemoteStorageCallback(JNIEnv* env, jobject callback)
    : SteamCallbackAdapter(env, callback) {

}

SteamRemoteStorageCallback::~SteamRemoteStorageCallback() {

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
