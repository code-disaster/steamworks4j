package com.codedisaster.steamworks;

@SuppressWarnings("unused")
class SteamRemoteStorageCallbackAdapter extends SteamCallbackAdapter<SteamRemoteStorageCallback> {

	SteamRemoteStorageCallbackAdapter(SteamRemoteStorageCallback callback) {
		super(callback);
	}

	void onFileShareResult(long fileHandle, String fileName, int result) {
		callback.onFileShareResult(new SteamUGCHandle(fileHandle),
				fileName, SteamResult.byValue(result));
	}

	void onDownloadUGCResult(long fileHandle, int result) {
		callback.onDownloadUGCResult(new SteamUGCHandle(fileHandle), SteamResult.byValue(result));
	}

	void onPublishFileResult(long publishedFileID, boolean needsToAcceptWLA, int result) {
		callback.onPublishFileResult(new SteamPublishedFileID(publishedFileID),
				needsToAcceptWLA, SteamResult.byValue(result));
	}

	void onUpdatePublishedFileResult(long publishedFileID, boolean needsToAcceptWLA, int result) {
		callback.onUpdatePublishedFileResult(new SteamPublishedFileID(publishedFileID),
				needsToAcceptWLA, SteamResult.byValue(result));
	}
}
