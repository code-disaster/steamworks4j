#pragma once

#include "SteamCallbackAdapter.h"
#include <steam_api.h>

class SteamUGCCallback : public SteamCallbackAdapter {

public:
	SteamUGCCallback(JNIEnv* env, jobject callback);
	~SteamUGCCallback();

	void onUGCQueryCompleted(SteamUGCQueryCompleted_t* callback, bool error);
	CCallResult<SteamUGCCallback, SteamUGCQueryCompleted_t> onUGCQueryCompletedCall;

	void onSubscribeItem(RemoteStorageSubscribePublishedFileResult_t* callback, bool error);
	CCallResult<SteamUGCCallback, RemoteStorageSubscribePublishedFileResult_t> onSubscribeItemCall;

	void onUnsubscribeItem(RemoteStorageUnsubscribePublishedFileResult_t* callback, bool error);
	CCallResult<SteamUGCCallback, RemoteStorageUnsubscribePublishedFileResult_t> onUnsubscribeItemCall;

	void onRequestUGCDetails(SteamUGCRequestUGCDetailsResult_t* callback, bool error);
	CCallResult<SteamUGCCallback, SteamUGCRequestUGCDetailsResult_t> onRequestUGCDetailsCall;
};
