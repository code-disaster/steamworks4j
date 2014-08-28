package com.codedisaster.steamworks;

public interface SteamUGCCallback {

	void onUGCQueryCompleted(SteamUGCQuery query, int numResultsReturned, int totalMatchingResults,
							 boolean isCachedData, SteamResult result);

}
