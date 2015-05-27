package com.codedisaster.steamworks;

import java.nio.ByteBuffer;

public class SteamUtils extends SteamInterface {

	public enum NotificationPosition {
		TopLeft,
		TopRight,
		BottomLeft,
		BottomRight
	}

	public SteamUtils() {
		super(SteamAPI.getSteamUtilsPointer());
	}

	public int getImageWidth(int image) {
		return getImageWidth(pointer, image);
	}

	public int getImageHeight(int image) {
		return getImageHeight(pointer, image);
	}

	public boolean getImageRGBA(int image, ByteBuffer dest, int destSize) {
		return getImageRGBA(pointer, image, dest, destSize);
	}

	public long getAppID() {
		return getAppID(pointer);
	}

	public void setOverlayNotificationPosition(NotificationPosition position) {
		setOverlayNotificationPosition(pointer, position.ordinal());
	}

	public boolean isOverlayEnabled() {
		return isOverlayEnabled(pointer);
	}

	// @off

	/*JNI
		#include <steam_api.h>
	*/

	static private native int getImageWidth(long pointer, int image); /*
		ISteamUtils* utils = (ISteamUtils*) pointer;
		uint32 width, height;
		bool result = utils->GetImageSize(image, &width, &height);
		return result ? width : -1;
	*/

	static private native int getImageHeight(long pointer, int image); /*
		ISteamUtils* utils = (ISteamUtils*) pointer;
		uint32 width, height;
		bool result = utils->GetImageSize(image, &width, &height);
		return result ? height : -1;
	*/

	static private native boolean getImageRGBA(long pointer, int image, ByteBuffer dest, int destSize); /*
		ISteamUtils* utils = (ISteamUtils*) pointer;
		return utils->GetImageRGBA(image, (uint8*) dest, destSize);
	*/

	static private native long getAppID(long pointer); /*
		ISteamUtils* utils = (ISteamUtils*) pointer;
		return (int64) utils->GetAppID();
	*/

	static private native void setOverlayNotificationPosition(long pointer, int position); /*
		ISteamUtils* utils = (ISteamUtils*) pointer;
		utils->SetOverlayNotificationPosition((ENotificationPosition) position);
	*/

	static private native boolean isOverlayEnabled(long pointer); /*
		ISteamUtils* utils = (ISteamUtils*) pointer;
		return utils->IsOverlayEnabled();
	*/

}
