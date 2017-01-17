package com.codedisaster.steamworks;

import java.util.Collection;

public class SteamFriends extends SteamInterface {

	public enum FriendRelationship {
		None,
		Blocked,
		Recipient,
		Friend,
		RequestInitiator,
		Ignored,
		IgnoredFriend,
		Suggested_DEPRECATED,
		Max;

		private static final FriendRelationship[] values = values();

		static FriendRelationship byOrdinal(int friendRelationship) {
			return values[friendRelationship];
		}
	}

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
		// Suggested(0x800),
		ChatMember(0x1000),
		All(0xFFFF);

		private final int bits;

		FriendFlags(int bits) {
			this.bits = bits;
		}

		static int asBits(Collection<FriendFlags> friendFlags) {
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

	public enum OverlayDialog {

		Friends("Friends"),
		Community("Community"),
		Players("Players"),
		Settings("Settings"),
		OfficialGameGroup("OfficialGameGroup"),
		Stats("Stats"),
		Achievements("Achievements");

		private final String id;

		OverlayDialog(String id) {
			this.id = id;
		}
	}

	public enum OverlayToUserDialog {

		SteamID("steamid"),
		Chat("chat"),
		JoinTrade("jointrade"),
		Stats("stats"),
		Achievements("achievements"),
		FriendAdd("friendadd"),
		FriendRemove("friendremove"),
		FriendRequestAccept("friendrequestaccept"),
		FriendRequestIgnore("friendrequestignore");

		private final String id;

		OverlayToUserDialog(String id) {
			this.id = id;
		}
	}

	public enum OverlayToStoreFlag {

		None,
		AddToCart,
		AddToCartAndShow
	}

	public SteamFriends(SteamFriendsCallback callback) {
		super(SteamAPI.getSteamFriendsPointer(), createCallback(new SteamFriendsCallbackAdapter(callback)));
	}

	public String getPersonaName() {
		return getPersonaName(pointer);
	}

	public SteamAPICall setPersonaName(String personaName) {
		return new SteamAPICall(setPersonaName(pointer, callback, personaName));
	}

	public PersonaState getPersonaState() {
		return PersonaState.byOrdinal(getPersonaState(pointer));
	}

	public int getFriendCount(FriendFlags friendFlag) {
		return getFriendCount(pointer, friendFlag.bits);
	}

	public int getFriendCount(Collection<FriendFlags> friendFlags) {
		return getFriendCount(pointer, FriendFlags.asBits(friendFlags));
	}

	public SteamID getFriendByIndex(int friend, FriendFlags friendFlag) {
		return new SteamID(getFriendByIndex(pointer, friend, friendFlag.bits));
	}

	public SteamID getFriendByIndex(int friend, Collection<FriendFlags> friendFlags) {
		return new SteamID(getFriendByIndex(pointer, friend, FriendFlags.asBits(friendFlags)));
	}

	public FriendRelationship getFriendRelationship(SteamID steamIDFriend) {
		return FriendRelationship.byOrdinal(getFriendRelationship(pointer, steamIDFriend.handle));
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

	public void activateGameOverlay(OverlayDialog dialog) {
		activateGameOverlay(pointer, dialog.id);
	}

	public void activateGameOverlayToUser(OverlayToUserDialog dialog, SteamID steamID) {
		activateGameOverlayToUser(pointer, dialog.id, steamID.handle);
	}

	public void activateGameOverlayToWebPage(String url) {
		activateGameOverlayToWebPage(pointer, url);
	}

	public void activateGameOverlayToStore(int appID, OverlayToStoreFlag flag) {
		activateGameOverlayToStore(pointer, appID, flag.ordinal());
	}

	public void activateGameOverlayInviteDialog(SteamID steamIDLobby) {
		activateGameOverlayInviteDialog(pointer, steamIDLobby.handle);
	}

	public boolean setRichPresence(String key, String value) {
		return setRichPresence(pointer, key, value != null ? value : "");
	}

	public void clearRichPresence() {
		clearRichPresence(pointer);
	}

	public String getFriendRichPresence(SteamID steamIDFriend, String key) {
		return getFriendRichPresence(pointer, steamIDFriend.handle, key);
	}

	public int getFriendRichPresenceKeyCount(SteamID steamIDFriend) {
		return getFriendRichPresenceKeyCount(pointer, steamIDFriend.handle);
	}

	public String getFriendRichPresenceKeyByIndex(SteamID steamIDFriend, int index) {
		return getFriendRichPresenceKeyByIndex(pointer, steamIDFriend.handle, index);
	}

	public void requestFriendRichPresence(SteamID steamIDFriend) {
		requestFriendRichPresence(pointer, steamIDFriend.handle);
	}

	public boolean inviteUserToGame(SteamID steamIDFriend, String connectString) {
		return inviteUserToGame(pointer, steamIDFriend.handle, connectString);
	}

	// @off

	/*JNI
		#include "SteamFriendsCallback.h"
	*/

	private static native long createCallback(SteamFriendsCallbackAdapter javaCallback); /*
		return (intp) new SteamFriendsCallback(env, javaCallback);
	*/

	private static native String getPersonaName(long pointer); /*
		ISteamFriends* friends = (ISteamFriends*) pointer;
		jstring name = env->NewStringUTF(friends->GetPersonaName());
		return name;
	*/

	private static native long setPersonaName(long pointer, long callback, String personaName); /*
		ISteamFriends* friends = (ISteamFriends*) pointer;
		SteamFriendsCallback* cb = (SteamFriendsCallback*) callback;
		SteamAPICall_t handle = friends->SetPersonaName(personaName);
		cb->onSetPersonaNameResponseCall.Set(handle, cb, &SteamFriendsCallback::onSetPersonaNameResponse);
		return handle;
	*/

	private static native int getPersonaState(long pointer); /*
		ISteamFriends* friends = (ISteamFriends*) pointer;
		return friends->GetPersonaState();
	*/

	private static native int getFriendCount(long pointer, int friendFlags); /*
		ISteamFriends* friends = (ISteamFriends*) pointer;
		return friends->GetFriendCount(friendFlags);
	*/

	private static native long getFriendByIndex(long pointer, int friendIndex, int friendFlags); /*
		ISteamFriends* friends = (ISteamFriends*) pointer;
		CSteamID id = friends->GetFriendByIndex(friendIndex, friendFlags);
		return id.ConvertToUint64();
	*/

	private static native int getFriendRelationship(long pointer, long steamIDFriend); /*
		ISteamFriends* friends = (ISteamFriends*) pointer;
		return friends->GetFriendRelationship((uint64) steamIDFriend);
	*/

	private static native int getFriendPersonaState(long pointer, long steamIDFriend); /*
		ISteamFriends* friends = (ISteamFriends*) pointer;
		return friends->GetFriendPersonaState((uint64) steamIDFriend);
	*/

	private static native String getFriendPersonaName(long pointer, long steamID); /*
		ISteamFriends* friends = (ISteamFriends*) pointer;
		jstring name = env->NewStringUTF(friends->GetFriendPersonaName((uint64) steamID));
		return name;
	*/

	private static native int getSmallFriendAvatar(long pointer, long steamID); /*
		ISteamFriends* friends = (ISteamFriends*) pointer;
		return friends->GetSmallFriendAvatar((uint64) steamID);
	*/

	private static native int getMediumFriendAvatar(long pointer, long steamID); /*
		ISteamFriends* friends = (ISteamFriends*) pointer;
		return friends->GetMediumFriendAvatar((uint64) steamID);
	*/

	private static native int getLargeFriendAvatar(long pointer, long steamID); /*
		ISteamFriends* friends = (ISteamFriends*) pointer;
		return friends->GetLargeFriendAvatar((uint64) steamID);
	*/

	private static native boolean requestUserInformation(long pointer, long steamID, boolean requireNameOnly); /*
		ISteamFriends* friends = (ISteamFriends*) pointer;
		return friends->RequestUserInformation((uint64) steamID, requireNameOnly);
	*/

	private static native void activateGameOverlay(long pointer, String dialog); /*
		ISteamFriends* friends = (ISteamFriends*) pointer;
		return friends->ActivateGameOverlay(dialog);
	*/

	private static native void activateGameOverlayToUser(long pointer, String dialog, long steamID); /*
		ISteamFriends* friends = (ISteamFriends*) pointer;
		return friends->ActivateGameOverlayToUser(dialog, (uint64) steamID);
	*/

	private static native void activateGameOverlayToWebPage(long pointer, String url); /*
		ISteamFriends* friends = (ISteamFriends*) pointer;
		return friends->ActivateGameOverlayToWebPage(url);
	*/

	private static native void activateGameOverlayToStore(long pointer, int appID, int flag); /*
		ISteamFriends* friends = (ISteamFriends*) pointer;
		return friends->ActivateGameOverlayToStore((AppId_t) appID, (EOverlayToStoreFlag) flag);
	*/

	private static native void activateGameOverlayInviteDialog(long pointer, long steamIDLobby); /*
		ISteamFriends* friends = (ISteamFriends*) pointer;
		return friends->ActivateGameOverlayInviteDialog((uint64) steamIDLobby);
	*/

	private static native boolean setRichPresence(long pointer, String key, String value); /*
		ISteamFriends* friends = (ISteamFriends*) pointer;
		return friends->SetRichPresence(key, value);
	*/

	private static native void clearRichPresence(long pointer); /*
		ISteamFriends* friends = (ISteamFriends*) pointer;
		friends->ClearRichPresence();
	*/

	private static native String getFriendRichPresence(long pointer, long steamIDFriend, String key); /*
		ISteamFriends* friends = (ISteamFriends*) pointer;
		return env->NewStringUTF(friends->GetFriendRichPresence((uint64) steamIDFriend, key));
	*/

	private static native int getFriendRichPresenceKeyCount(long pointer, long steamIDFriend); /*
		ISteamFriends* friends = (ISteamFriends*) pointer;
		return friends->GetFriendRichPresenceKeyCount((uint64) steamIDFriend);
	*/

	private static native String getFriendRichPresenceKeyByIndex(long pointer, long steamIDFriend, int index); /*
		ISteamFriends* friends = (ISteamFriends*) pointer;
		return env->NewStringUTF(friends->GetFriendRichPresenceKeyByIndex((uint64) steamIDFriend, index));
	*/

	private static native void requestFriendRichPresence(long pointer, long steamIDFriend); /*
		ISteamFriends* friends = (ISteamFriends*) pointer;
		friends->RequestFriendRichPresence((uint64) steamIDFriend);
	*/

	private static native boolean inviteUserToGame(long pointer, long steamIDFriend, String connectString); /*
		ISteamFriends* friends = (ISteamFriends*) pointer;
		return friends->InviteUserToGame((uint64) steamIDFriend, connectString);
	*/

}
