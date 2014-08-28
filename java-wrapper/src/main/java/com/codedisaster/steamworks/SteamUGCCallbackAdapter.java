package com.codedisaster.steamworks;

class SteamUGCCallbackAdapter {

	private SteamUGCCallback callback;

	SteamUGCCallbackAdapter(SteamUGCCallback callback) {
		this.callback = callback;
	}

	public void onUGCQueryCompleted(long handle, int numResultsReturned, int totalMatchingResults,
									boolean isCachedData, int result) {

		callback.onUGCQueryCompleted(new SteamUGCQuery(handle), numResultsReturned,
				totalMatchingResults, isCachedData, SteamResult.byValue(result));
	}

}
