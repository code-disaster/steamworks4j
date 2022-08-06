package com.codedisaster.steamworks;

public interface SteamUGCCallback {

	default void onUGCQueryCompleted(SteamUGCQuery query, int numResultsReturned, int totalMatchingResults,
									 boolean isCachedData, SteamResult result) {
	}

	default void onSubscribeItem(SteamPublishedFileID publishedFileID, SteamResult result) {
	}

	default void onUnsubscribeItem(SteamPublishedFileID publishedFileID, SteamResult result) {
	}

	default void onRequestUGCDetails(SteamUGCDetails details, SteamResult result) {
	}

	default void onCreateItem(SteamPublishedFileID publishedFileID, boolean needsToAcceptWLA, SteamResult result) {
	}

	default void onSubmitItemUpdate(SteamPublishedFileID publishedFileID,
									boolean needsToAcceptWLA, SteamResult result) {
	}

	default void onDownloadItemResult(int appID, SteamPublishedFileID publishedFileID, SteamResult result) {
	}

	default void onUserFavoriteItemsListChanged(SteamPublishedFileID publishedFileID,
												boolean wasAddRequest, SteamResult result) {
	}

	default void onSetUserItemVote(SteamPublishedFileID publishedFileID, boolean voteUp, SteamResult result) {
	}

	default void onGetUserItemVote(SteamPublishedFileID publishedFileID, boolean votedUp,
								   boolean votedDown, boolean voteSkipped, SteamResult result) {
	}

	default void onStartPlaytimeTracking(SteamResult result) {
	}

	default void onStopPlaytimeTracking(SteamResult result) {
	}

	default void onStopPlaytimeTrackingForAllItems(SteamResult result) {
	}

	default void onDeleteItem(SteamPublishedFileID publishedFileID, SteamResult result) {
	}

}
