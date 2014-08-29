package com.codedisaster.steamworks;

public class SteamUGCDetails {

	/* Contains only a subset of SteamUGCDetails_t. */

	long publishedFileID;
	int result;
	long fileHandle;
	long previewFileHandle;

	public SteamPublishedFileID getPublishedFileID() {
		return new SteamPublishedFileID(publishedFileID);
	}

	public SteamResult getResult() {
		return SteamResult.byValue(result);
	}

	public SteamUGCHandle getFileHandle() {
		return new SteamUGCHandle(fileHandle);
	}

	public SteamUGCHandle getPreviewFileHandle() {
		return new SteamUGCHandle(previewFileHandle);
	}
}
