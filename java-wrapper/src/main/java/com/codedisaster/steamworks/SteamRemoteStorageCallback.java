package com.codedisaster.steamworks;

public interface SteamRemoteStorageCallback {

	void onFileShareResult(SteamUGCHandle fileHandle, String fileName, SteamResult result);

	void onDownloadUGCResult(SteamUGCHandle fileHandle, SteamResult result);

	void onPublishFileResult(SteamPublishedFileID publishedFileID, boolean needsToAcceptWLA, SteamResult result);

	void onUpdatePublishedFileResult(SteamPublishedFileID publishedFileID, boolean needsToAcceptWLA, SteamResult result);

	void onPublishedFileSubscribed(SteamPublishedFileID publishedFileID, int appID);

	void onPublishedFileUnsubscribed(SteamPublishedFileID publishedFileID, int appID);

	void onPublishedFileDeleted(SteamPublishedFileID publishedFileID, int appID);

	void onFileWriteAsyncComplete(SteamResult result);

	void onFileReadAsyncComplete(SteamAPICall fileReadAsync, SteamResult result, int offset, int read);

}
