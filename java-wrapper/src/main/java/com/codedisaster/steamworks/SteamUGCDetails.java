package com.codedisaster.steamworks;

public class SteamUGCDetails {

	/* Contains only a subset of SteamUGCDetails_t. */

	long publishedFileID;
	int result;
	String title;
	String description;
	long fileHandle;
	long previewFileHandle;
	String fileName;
	int votesUp;
	int votesDown;
	long ownerID;
	int timeCreated;
	int timeUpdated;

	public SteamPublishedFileID getPublishedFileID() {
		return new SteamPublishedFileID(publishedFileID);
	}

	public SteamResult getResult() {
		return SteamResult.byValue(result);
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public SteamUGCHandle getFileHandle() {
		return new SteamUGCHandle(fileHandle);
	}

	public SteamUGCHandle getPreviewFileHandle() {
		return new SteamUGCHandle(previewFileHandle);
	}

	public String getFileName() {
		return fileName;
	}

	public int getVotesUp() {
		return votesUp;
	}

	public int getVotesDown() {
		return votesDown;
	}
	
	public SteamID getOwnerID() {
		return new SteamID(ownerID);
	}
	
	public int getTimeCreated() {
		return timeCreated;
	}

	public int getTimeUpdated() {
		return timeUpdated;
	}
	
}
