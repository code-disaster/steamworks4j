package com.codedisaster.steamworks;

public class SteamFriends extends SteamInterface {

	public enum PersonaChange {

		Name(0x0001),
		Status(0x0002),
		ComeOnline(0x0004),
		GoneOffline(0x0008),
		GamePlayed(0x0010),
		GameServer(0x0020),
		Avatar(0x0040),
		JoinedSource(0x0080),
		LeftSource(0x0100),
		RelationshipChanged(0x0200),
		NameFirstSet(0x0400),
		FacebookInfo(0x0800),
		Nickname(0x1000),
		SteamLevel(0x2000);

		private final int bits;

		PersonaChange(int bits) {
			this.bits = bits;
		}

		static boolean isSet(PersonaChange value, int bitMask) {
			return (value.bits & bitMask) == value.bits;
		}
	}

	public SteamFriends(long pointer, SteamFriendsCallback callback) {
		super(pointer);
		registerCallback(new SteamFriendsCallbackAdapter(callback));
	}

	static void dispose() {
		registerCallback(null);
	}

	public String getPersonaName() {
		return getPersonaName(pointer);
	}

	public String getFriendPersonaName(SteamID steamID) {
		return getFriendPersonaName(pointer, steamID.handle);
	}

	public int getSmallFriendAvatar(SteamID steamID) {
		return getSmallFriendAvatar(pointer, steamID.handle);
	}

	public int getMediumFriendAvatar(SteamID steamID) {
		return getMediumFriendAvatar(pointer, steamID.handle);
	}

	public int getLargeFriendAvatar(SteamID steamID) {
		return getLargeFriendAvatar(pointer, steamID.handle);
	}

	public boolean requestUserInformation(SteamID steamID, boolean requireNameOnly) {
		return requestUserInformation(pointer, steamID.handle, requireNameOnly);
	}

	public void activateGameOverlayToWebPage(String url) {
		activateGameOverlayToWebPage(pointer, url);
	}

	/*JNI
		#include <steam_api.h>
		#include "SteamFriendsCallback.h"

		static SteamFriendsCallback* callback = NULL;
	*/

	static private native boolean registerCallback(SteamFriendsCallbackAdapter javaCallback); /*
		if (callback != NULL) {
			delete callback;
			callback = NULL;
		}

		if (javaCallback != NULL) {
			callback = new SteamFriendsCallback(env, javaCallback);
		}

		return callback != NULL;
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

	static private native int getSmallFriendAvatar(long pointer, long steamID); /*
		ISteamFriends* friends = (ISteamFriends*) pointer;
		return friends->GetSmallFriendAvatar((uint64) steamID);
	*/

	static private native int getMediumFriendAvatar(long pointer, long steamID); /*
		ISteamFriends* friends = (ISteamFriends*) pointer;
		return friends->GetMediumFriendAvatar((uint64) steamID);
	*/

	static private native int getLargeFriendAvatar(long pointer, long steamID); /*
		ISteamFriends* friends = (ISteamFriends*) pointer;
		return friends->GetLargeFriendAvatar((uint64) steamID);
	*/

	static private native boolean requestUserInformation(long pointer, long steamID, boolean requireNameOnly); /*
		ISteamFriends* friends = (ISteamFriends*) pointer;
		return friends->RequestUserInformation((uint64) steamID, requireNameOnly);
	*/

	static private native void activateGameOverlayToWebPage(long pointer, String url); /*
		ISteamFriends* friends = (ISteamFriends*) pointer;
		return friends->ActivateGameOverlayToWebPage(url);
	*/

}
