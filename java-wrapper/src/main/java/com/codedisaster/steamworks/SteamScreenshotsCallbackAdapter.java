package com.codedisaster.steamworks;

@SuppressWarnings("unused")
public class SteamScreenshotsCallbackAdapter extends SteamCallbackAdapter<SteamScreenshotsCallback> {

	SteamScreenshotsCallbackAdapter(SteamScreenshotsCallback callback) {
		super(callback);
	}

	void onScreenshotReady(int local, int result) {
		callback.onScreenshotReady(new SteamScreenshotHandle(local), SteamResult.byValue(result));
	}

	void onScreenshotRequested() {
		callback.onScreenshotRequested();
	}

}
