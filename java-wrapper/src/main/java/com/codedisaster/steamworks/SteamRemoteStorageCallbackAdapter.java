package com.codedisaster.steamworks;

public class SteamRemoteStorageCallbackAdapter extends SteamCallbackAdapter<SteamRemoteStorageCallback> {

	SteamRemoteStorageCallbackAdapter(SteamRemoteStorageCallback callback) {
		super(callback);
	}

	public void onFileShareResult(long fileHandle, String fileName, int result) {
		callback.onFileShareResult(new SteamUGCHandle(fileHandle),
				fileName, SteamResult.byValue(result));
	}

	public void onPublishFileResult(long publishedFileID, boolean needsToAcceptWLA, int result) {
		callback.onPublishFileResult(new SteamPublishedFileID(publishedFileID),
				needsToAcceptWLA, SteamResult.byValue(result));
	}
}
