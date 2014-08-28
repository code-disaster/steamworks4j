package com.codedisaster.steamworks;

public class SteamRemoteStorageCallbackAdapter extends SteamCallbackAdapter<SteamRemoteStorageCallback> {

	SteamRemoteStorageCallbackAdapter(SteamRemoteStorageCallback callback) {
		super(callback);
	}

	public void onRemoteStorageFileShareResult(long fileHandle, String fileName, int result) {
		callback.onRemoteStorageFileShareResult(new SteamUGCHandle(fileHandle), fileName, SteamResult.byValue(result));
	}

}
