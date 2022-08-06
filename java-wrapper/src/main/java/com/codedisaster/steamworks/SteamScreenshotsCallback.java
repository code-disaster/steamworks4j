package com.codedisaster.steamworks;

public interface SteamScreenshotsCallback {

	default void onScreenshotReady(SteamScreenshotHandle local, SteamResult result) {
	}

	default void onScreenshotRequested() {
	}

}
