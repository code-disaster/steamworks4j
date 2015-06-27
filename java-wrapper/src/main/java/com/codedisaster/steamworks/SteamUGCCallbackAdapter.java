package com.codedisaster.steamworks;

@SuppressWarnings("unused")
class SteamUGCCallbackAdapter extends SteamCallbackAdapter<SteamUGCCallback> {

	SteamUGCCallbackAdapter(SteamUGCCallback callback) {
		super(callback);
	}

	public void onUGCQueryCompleted(long handle, int numResultsReturned, int totalMatchingResults,
									boolean isCachedData, int result) {

		callback.onUGCQueryCompleted(new SteamUGCQuery(handle), numResultsReturned,
				totalMatchingResults, isCachedData, SteamResult.byValue(result));
	}

	public void onSubscribeItem(long publishedFileID, int result) {
		callback.onSubscribeItem(new SteamPublishedFileID(publishedFileID), SteamResult.byValue(result));
	}
	
	public void onUnsubscribeItem(long publishedFileID, int result) {
		callback.onUnsubscribeItem(new SteamPublishedFileID(publishedFileID), SteamResult.byValue(result));
	}
	
	public void onRequestUGCDetails(long publishedFileID, int result, String title, String description,
									long fileHandle, long previewFileHandle, String fileName,
									boolean cachedData, int votesUp, int votesDown, long ownerID,
									int timeCreated, int timeUpdated) {

		SteamUGCDetails details = new SteamUGCDetails();
		details.publishedFileID = publishedFileID;
		details.result = result;
		details.title = title;
		details.description = description;
		details.fileHandle = fileHandle;
		details.previewFileHandle = previewFileHandle;
		details.fileName = fileName;
		details.votesUp = votesUp;
		details.votesDown = votesDown;
		details.ownerID = ownerID;
		details.timeCreated = timeCreated;
		details.timeUpdated = timeUpdated;
		
		callback.onRequestUGCDetails(details, SteamResult.byValue(result));
	}
	
}
