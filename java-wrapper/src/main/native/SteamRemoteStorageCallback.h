#pragma once

#include "SteamCallbackAdapter.h"
#include <steam_api.h>

class SteamRemoteStorageCallback : public SteamCallbackAdapter {

public:
    SteamRemoteStorageCallback(JNIEnv* env, jobject callback);
    ~SteamRemoteStorageCallback();

	void onFileShareResult(RemoteStorageFileShareResult_t* callback, bool error);
	CCallResult<SteamRemoteStorageCallback, RemoteStorageFileShareResult_t> onFileShareResultCall;

	void onPublishFileResult(RemoteStoragePublishFileResult_t* callback, bool error);
	CCallResult<SteamRemoteStorageCallback, RemoteStoragePublishFileResult_t> onPublishFileResultCall;
};
