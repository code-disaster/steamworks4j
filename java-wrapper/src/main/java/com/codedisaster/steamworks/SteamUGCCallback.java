package com.codedisaster.steamworks;

public interface SteamUGCCallback {

	void onUGCQueryCompleted(SteamUGCQuery query, int numResultsReturned, int totalMatchingResults,
							 boolean isCachedData, SteamResult result);

	void onSubscribeItem(SteamPublishedFileID publishedFileID, SteamResult result);

	void onUnsubscribeItem(SteamPublishedFileID publishedFileID, SteamResult result);
	
	void onRequestUGCDetails(SteamUGCDetails details, SteamResult result);

}
