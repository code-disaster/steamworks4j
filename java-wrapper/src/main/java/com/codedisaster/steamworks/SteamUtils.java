package com.codedisaster.steamworks;

public class SteamUtils extends SteamInterface {

	enum NotificationPosition {
		TopLeft,
		TopRight,
		BottomLeft,
		BottomRight
	}

	public SteamUtils(long pointer) {
		super(pointer);
	}

	static void dispose() {
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
