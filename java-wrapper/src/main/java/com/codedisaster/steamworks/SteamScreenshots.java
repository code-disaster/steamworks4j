package com.codedisaster.steamworks;

import java.nio.ByteBuffer;

@SuppressWarnings("unused")
public class SteamScreenshots extends SteamInterface {

	public SteamScreenshots(SteamScreenshotsCallback callback) {
		super(SteamScreenshotsNative.createCallback(new SteamScreenshotsCallbackAdapter(callback)));
	}

	public SteamScreenshotHandle writeScreenshot(ByteBuffer rgb, int width, int height) {
		return new SteamScreenshotHandle(SteamScreenshotsNative.writeScreenshot(rgb, rgb.remaining(), width, height));
	}

	public SteamScreenshotHandle addScreenshotToLibrary(String file, String thumbnail, int width, int height) {
		return new SteamScreenshotHandle(SteamScreenshotsNative.addScreenshotToLibrary(file, thumbnail, width, height));
	}

	public void triggerScreenshot() {
		SteamScreenshotsNative.triggerScreenshot();
	}

	public void hookScreenshots(boolean hook) {
		SteamScreenshotsNative.hookScreenshots(hook);
	}

	public boolean setLocation(SteamScreenshotHandle screenshot, String location) {
		return SteamScreenshotsNative.setLocation(screenshot.handle, location);
	}

	public boolean tagUser(SteamScreenshotHandle screenshot, SteamID steamID) {
		return SteamScreenshotsNative.tagUser(screenshot.handle, steamID.handle);
	}

	public boolean tagPublishedFile(SteamScreenshotHandle screenshot, SteamPublishedFileID publishedFileID) {
		return SteamScreenshotsNative.tagPublishedFile(screenshot.handle, publishedFileID.handle);
	}

	public boolean isScreenshotsHooked() {
		return SteamScreenshotsNative.isScreenshotsHooked();
	}

}
