package com.codedisaster.steamworks;

public interface SteamScreenshotsCallback {

	void onScreenshotReady(SteamScreenshotHandle local, SteamResult result);

	void onScreenshotRequested();

}
