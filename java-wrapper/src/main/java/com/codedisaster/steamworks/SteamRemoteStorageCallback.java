package com.codedisaster.steamworks;

public interface SteamRemoteStorageCallback {

	void onRemoteStorageFileShareResult(SteamUGCHandle fileHandle, String fileName, SteamResult result);

}
