#pragma once

#include "SteamCallbackAdapter.h"
#include <steam_api.h>

class SteamRemoteStorageCallback : public SteamCallbackAdapter {

public:
    SteamRemoteStorageCallback(JNIEnv* env, jobject callback);
    ~SteamRemoteStorageCallback();

    void onFileWriteAsyncComplete(RemoteStorageFileWriteAsyncComplete_t* callback, bool error);
	CCallResult<SteamRemoteStorageCallback, RemoteStorageFileWriteAsyncComplete_t> onFileWriteAsyncCompleteCall;

    void onFileReadAsyncComplete(RemoteStorageFileReadAsyncComplete_t* callback, bool error);
	CCallResult<SteamRemoteStorageCallback, RemoteStorageFileReadAsyncComplete_t> onFileReadAsyncCompleteCall;

	void onFileShareResult(RemoteStorageFileShareResult_t* callback, bool error);
	CCallResult<SteamRemoteStorageCallback, RemoteStorageFileShareResult_t> onFileShareResultCall;

	void onDownloadUGCResult(RemoteStorageDownloadUGCResult_t* callback, bool error);
	CCallResult<SteamRemoteStorageCallback, RemoteStorageDownloadUGCResult_t> onDownloadUGCResultCall;

	void onPublishFileResult(RemoteStoragePublishFileResult_t* callback, bool error);
	CCallResult<SteamRemoteStorageCallback, RemoteStoragePublishFileResult_t> onPublishFileResultCall;

	void onUpdatePublishedFileResult(RemoteStorageUpdatePublishedFileResult_t* callback, bool error);
	CCallResult<SteamRemoteStorageCallback, RemoteStorageUpdatePublishedFileResult_t> onUpdatePublishedFileResultCall;

    STEAM_CALLBACK(SteamRemoteStorageCallback, onPublishedFileSubscribed, RemoteStoragePublishedFileSubscribed_t, m_CallbackPublishedFileSubscribed);
    STEAM_CALLBACK(SteamRemoteStorageCallback, onPublishedFileUnsubscribed, RemoteStoragePublishedFileUnsubscribed_t, m_CallbackPublishedFileUnsubscribed);
    STEAM_CALLBACK(SteamRemoteStorageCallback, onPublishedFileDeleted, RemoteStoragePublishedFileDeleted_t, m_CallbackPublishedFileDeleted);
};
