package com.codedisaster.steamworks;

public interface SteamRemoteStorageCallback {

	void onFileShareResult(SteamUGCHandle fileHandle, String fileName, SteamResult result);

	void onPublishFileResult(SteamPublishedFileID publishedFileID, boolean needsToAcceptWLA, SteamResult result);

}
