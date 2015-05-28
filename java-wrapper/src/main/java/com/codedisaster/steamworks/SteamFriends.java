package com.codedisaster.steamworks;

public class SteamFriends extends SteamInterface {

	public enum PersonaState {
		Offline,
		Online,
		Busy,
		Away,
		Snooze,
		LookingToTrade,
		LookingToPlay;

		private static final PersonaState[] values = values();

		static PersonaState byOrdinal(int personaState) {
			return values[personaState];
		}
	}

	public enum FriendFlags {

		None(0x00),
		Blocked(0x01),
		FriendshipRequested(0x02),
		Immediate(0x04),
		ClanMember(0x08),
		OnGameServer(0x10),
		// HasPlayedWith(0x20),
		// FriendOfFriend(0x40),
		RequestingFriendship(0x80),
		RequestingInfo(0x100),
		Ignored(0x200),
		IgnoredFriend(0x400),
		Suggested(0x800),
		All(0xFFFF);

		private final int bits;

		FriendFlags(int bits) {
			this.bits = bits;
		}

		static int asBits(FriendFlags... friendFlags) {
			int bits = 0;
			for (FriendFlags flags : friendFlags) {
				bits |= flags.bits;
			}
			return bits;
		}
	}

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

	public PersonaState getPersonaState() {
		return PersonaState.byOrdinal(getPersonaState(pointer));
	}

	public String getPersonaName() {
		return getPersonaName(pointer);
	}

	public int getFriendCount(FriendFlags... friendFlags) {
		return getFriendCount(pointer, FriendFlags.asBits(friendFlags));
	}

	public SteamID getFriendByIndex(int friend, FriendFlags... friendFlags) {
		return new SteamID(getFriendByIndex(pointer, friend, FriendFlags.asBits(friendFlags)));
	}

	public PersonaState getFriendPersonaState(SteamID steamIDFriend) {
		return PersonaState.byOrdinal(getFriendPersonaState(pointer, steamIDFriend.handle));
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

	static private native int getPersonaState(long pointer); /*
		ISteamFriends* friends = (ISteamFriends*) pointer;
		return friends->GetPersonaState();
	*/

	static private native int getFriendCount(long pointer, int friendFlags); /*
		ISteamFriends* friends = (ISteamFriends*) pointer;
		return friends->GetFriendCount(friendFlags);
	*/

	static private native long getFriendByIndex(long pointer, int friendIndex, int friendFlags); /*
		ISteamFriends* friends = (ISteamFriends*) pointer;
		CSteamID id = friends->GetFriendByIndex(friendIndex, friendFlags);
		return id.ConvertToUint64();
	*/

	static private native int getFriendPersonaState(long pointer, long steamIDFriend); /*
		ISteamFriends* friends = (ISteamFriends*) pointer;
		return friends->GetFriendPersonaState((uint64) steamIDFriend);
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
