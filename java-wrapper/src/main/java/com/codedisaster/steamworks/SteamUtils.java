package com.codedisaster.steamworks;

public class SteamUtils extends SteamInterface {

	public SteamUtils(long pointer) {
		super(pointer);
	}

	static void dispose() {
	}

	public long getAppID() {
		return getAppID(pointer);
	}

	// @off

	/*JNI
		#include <steam_api.h>
	*/

	static private native long getAppID(long pointer); /*
		ISteamUtils* utils = (ISteamUtils*) pointer;
		return (int64) utils->GetAppID();
	*/

}
