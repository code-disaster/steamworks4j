package com.codedisaster.steamworks;

import java.nio.ByteBuffer;

final class SteamScreenshotsNative {

	// @off

	/*JNI
		#include "SteamScreenshotsCallback.h"
	*/

	static native long createCallback(SteamScreenshotsCallbackAdapter javaCallback); /*
		return (intp) new SteamScreenshotsCallback(env, javaCallback);
	*/

	static native int writeScreenshot(ByteBuffer imageData,
									  int imageSize, int width, int height); /*

		return SteamScreenshots()->WriteScreenshot(imageData, imageSize, width, height);
	*/

	static native int addScreenshotToLibrary(String file,
											 String thumbnail, int width, int height); /*

		return SteamScreenshots()->AddScreenshotToLibrary(file, thumbnail, width, height);
	*/

	static native void triggerScreenshot(); /*
		SteamScreenshots()->TriggerScreenshot();
	*/

	static native void hookScreenshots(boolean hook); /*
		SteamScreenshots()->HookScreenshots(hook);
	*/

	static native boolean setLocation(int screenshot, String location); /*
		return SteamScreenshots()->SetLocation(screenshot, location);
	*/

	static native boolean tagUser(int screenshot, long steamID); /*
		return SteamScreenshots()->TagUser(screenshot, (uint64) steamID);
	*/

	static native boolean tagPublishedFile(int screenshot, long publishedFileID); /*
		return SteamScreenshots()->TagPublishedFile(screenshot, publishedFileID);
	*/

	static native boolean isScreenshotsHooked(); /*
		return SteamScreenshots()->IsScreenshotsHooked();
	*/

}
