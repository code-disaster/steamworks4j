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

	void onCreateItem(CreateItemResult_t* callback, bool error);
	CCallResult<SteamUGCCallback, CreateItemResult_t> onCreateItemCall;

	void onSubmitItemUpdate(SubmitItemUpdateResult_t* callback, bool error);
	CCallResult<SteamUGCCallback, SubmitItemUpdateResult_t> onSubmitItemUpdateCall;
	
	void onDeleteItem(DeleteItemResult_t* callback, bool error);
	CCallResult<SteamUGCCallback, DeleteItemResult_t> onDeleteItemCall;

	STEAM_CALLBACK(SteamUGCCallback, onDownloadItemResult, DownloadItemResult_t, m_CallbackDownloadItemResult);

	void onUserFavoriteItemsListChanged(UserFavoriteItemsListChanged_t* callback, bool error);
	CCallResult<SteamUGCCallback, UserFavoriteItemsListChanged_t> onUserFavoriteItemsListChangedCall;

	void onSetUserItemVote(SetUserItemVoteResult_t* callback, bool error);
	CCallResult<SteamUGCCallback, SetUserItemVoteResult_t> onSetUserItemVoteCall;

	void onGetUserItemVote(GetUserItemVoteResult_t* callback, bool error);
	CCallResult<SteamUGCCallback, GetUserItemVoteResult_t> onGetUserItemVoteCall;

	void onStartPlaytimeTracking(StartPlaytimeTrackingResult_t* callback, bool error);
	CCallResult<SteamUGCCallback, StartPlaytimeTrackingResult_t> onStartPlaytimeTrackingCall;

	void onStopPlaytimeTracking(StopPlaytimeTrackingResult_t* callback, bool error);
	CCallResult<SteamUGCCallback, StopPlaytimeTrackingResult_t> onStopPlaytimeTrackingCall;

	void onStopPlaytimeTrackingForAllItems(StopPlaytimeTrackingResult_t* callback, bool error);
	CCallResult<SteamUGCCallback, StopPlaytimeTrackingResult_t> onStopPlaytimeTrackingForAllItemsCall;
};
