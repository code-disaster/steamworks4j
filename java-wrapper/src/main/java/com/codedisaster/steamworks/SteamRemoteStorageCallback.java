package com.codedisaster.steamworks;

public interface SteamRemoteStorageCallback {

	default void onFileShareResult(SteamUGCHandle fileHandle, String fileName, SteamResult result) {
	}

	default void onDownloadUGCResult(SteamUGCHandle fileHandle, SteamResult result) {
	}

	default void onPublishFileResult(SteamPublishedFileID publishedFileID, boolean needsToAcceptWLA, SteamResult result) {
	}

	default void onUpdatePublishedFileResult(SteamPublishedFileID publishedFileID, boolean needsToAcceptWLA, SteamResult result) {
	}

	default void onPublishedFileSubscribed(SteamPublishedFileID publishedFileID, int appID) {
	}

	default void onPublishedFileUnsubscribed(SteamPublishedFileID publishedFileID, int appID) {
	}

	default void onPublishedFileDeleted(SteamPublishedFileID publishedFileID, int appID) {
	}

	default void onFileWriteAsyncComplete(SteamResult result) {
	}

	default void onFileReadAsyncComplete(SteamAPICall fileReadAsync, SteamResult result, int offset, int read) {
	}

}
