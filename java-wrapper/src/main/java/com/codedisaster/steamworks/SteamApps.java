package com.codedisaster.steamworks;

public class SteamApps extends SteamInterface {

	public SteamApps(long pointer) {
		super(pointer);
	}

	static void dispose() {
	}

	public boolean isSubscribedApp(long appID) {
		return isSubscribedApp(pointer, appID);
	}

	// @off

	/*JNI
		#include <steam_api.h>
	*/

	static private native boolean isSubscribedApp(long pointer, long appID); /*
		ISteamApps* apps = (ISteamApps*) pointer;
		return apps->BIsSubscribedApp((AppId_t) appID);
	*/

}
