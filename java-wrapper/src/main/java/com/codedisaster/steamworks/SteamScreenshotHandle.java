package com.codedisaster.steamworks;

public class SteamScreenshotHandle extends SteamNativeIntHandle {

	public static final SteamScreenshotHandle INVALID = new SteamScreenshotHandle(0);

	SteamScreenshotHandle(int handle) {
		super(handle);
	}

}
