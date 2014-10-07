package com.codedisaster.steamworks;

public class SteamFriends extends SteamInterface {

	public SteamFriends(long pointer) {
		super(pointer);
	}

	static void dispose() {
	}

	public void activateGameOverlayToWebPage(String url) {
		activateGameOverlayToWebPage(pointer, url);
	}

	/*JNI
		#include <steam_api.h>
	*/

	static private native void activateGameOverlayToWebPage(long pointer, String url); /*
		ISteamFriends* friends = (ISteamFriends*) pointer;
		return friends->ActivateGameOverlayToWebPage(url);
	*/

}
