package com.codedisaster.steamworks;

@SuppressWarnings("WeakerAccess")
public class SteamUGCDetails {

	/* Contains only a subset of SteamUGCDetails_t. */

	long publishedFileID;
	int result;
	int fileType;
	// creatorAppID
	// consumerAppID
	String title;
	String description;
	long ownerID;
	int timeCreated;
	int timeUpdated;
	// timeAddedToUserList
	// visibility
	// banned
	// acceptedForUse
	boolean tagsTruncated;
	String tags;
	long fileHandle;
	long previewFileHandle;
	String fileName;
	int fileSize;
	int previewFileSize;
	String url;
	int votesUp;
	int votesDown;
	// score
	// numChildren

	public SteamPublishedFileID getPublishedFileID() {
		return new SteamPublishedFileID(publishedFileID);
	}

	public SteamResult getResult() {
		return SteamResult.byValue(result);
	}

	public SteamRemoteStorage.WorkshopFileType getFileType() {
		return SteamRemoteStorage.WorkshopFileType.byOrdinal(fileType);
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
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

	public boolean areTagsTruncated() {
		return tagsTruncated;
	}

	public String getTags() {
		return tags;
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

	public int getFileSize() {
		return fileSize;
	}

	public int getPreviewFileSize() {
		return previewFileSize;
	}

	public String getURL() {
		return url;
	}

	public int getVotesUp() {
		return votesUp;
	}

	public int getVotesDown() {
		return votesDown;
	}

}
