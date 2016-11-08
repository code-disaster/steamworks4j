package com.codedisaster.steamworks;

public interface SteamUGCCallback {

	void onUGCQueryCompleted(SteamUGCQuery query, int numResultsReturned, int totalMatchingResults,
							 boolean isCachedData, SteamResult result);

	void onSubscribeItem(SteamPublishedFileID publishedFileID, SteamResult result);

	void onUnsubscribeItem(SteamPublishedFileID publishedFileID, SteamResult result);
	
	void onRequestUGCDetails(SteamUGCDetails details, SteamResult result);

	void onCreateItem(SteamPublishedFileID publishedFileID, boolean needsToAcceptWLA, SteamResult result);

	void onSubmitItemUpdate(boolean needsToAcceptWLA, SteamResult result);

	void onDownloadItemResult(int appID, SteamPublishedFileID publishedFileID, SteamResult result);

	void onUserFavoriteItemsListChanged(SteamPublishedFileID publishedFileID,
										boolean wasAddRequest, SteamResult result);

	void onSetUserItemVote(SteamPublishedFileID publishedFileID, boolean voteUp, SteamResult result);

	void onGetUserItemVote(SteamPublishedFileID publishedFileID, boolean votedUp,
						   boolean votedDown, boolean voteSkipped, SteamResult result);

	void onStartPlaytimeTracking(SteamResult result);

	void onStopPlaytimeTracking(SteamResult result);

	void onStopPlaytimeTrackingForAllItems(SteamResult result);

}
