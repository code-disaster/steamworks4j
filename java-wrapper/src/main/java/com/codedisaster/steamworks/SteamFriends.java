package com.codedisaster.steamworks;

public class SteamFriends extends SteamInterface {

	public SteamFriends(long pointer) {
		super(pointer);
	}

	static void dispose() {
	}

	public String getPersonaName() {
		return getPersonaName(pointer);
	}

	public String getFriendPersonaName(SteamID steamID) {
		return getFriendPersonaName(pointer, steamID.handle);
	}

	public void activateGameOverlayToWebPage(String url) {
		activateGameOverlayToWebPage(pointer, url);
	}

	/*JNI
		#include <steam_api.h>
	*/

	static private native String getPersonaName(long pointer); /*
		ISteamFriends* friends = (ISteamFriends*) pointer;
		jstring name = env->NewStringUTF(friends->GetPersonaName());
		return name;
	*/

	static private native String getFriendPersonaName(long pointer, long steamID); /*
		ISteamFriends* friends = (ISteamFriends*) pointer;
		jstring name = env->NewStringUTF(friends->GetFriendPersonaName((uint64) steamID));
		return name;
	*/

	static private native void activateGameOverlayToWebPage(long pointer, String url); /*
		ISteamFriends* friends = (ISteamFriends*) pointer;
		return friends->ActivateGameOverlayToWebPage(url);
	*/

}
