package com.codedisaster.steamworks;

import java.util.Collection;

@SuppressWarnings({ "unused", "UnusedReturnValue" })
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
		LookingToPlay,
		Invisible;

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
		Broadcast(0x0800),
		Nickname(0x1000),
		SteamLevel(0x2000),
		RichPresence(0x4000);

		private final int bits;

		PersonaChange(int bits) {
			this.bits = bits;
		}

		static boolean isSet(PersonaChange value, int bitMask) {
			return (value.bits & bitMask) == value.bits;
		}
	}

	public static class FriendGameInfo {

		private long gameID;
		private int gameIP;
		private short gamePort;
		private short queryPort;
		private long steamIDLobby;

		public long getGameID() {
			return gameID;
		}

		public int getGameIP() {
			return gameIP;
		}

		public short getGamePort() {
			return gamePort;
		}

		public short getQueryPort() {
			return queryPort;
		}

		public SteamID getSteamIDLobby() {
			return new SteamID(steamIDLobby);
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

	public enum OverlayToWebPageMode {
		Default,
		Modal
	}

	public SteamFriends(SteamFriendsCallback callback) {
		super(SteamFriendsNative.createCallback(new SteamFriendsCallbackAdapter(callback)));
	}

	public String getPersonaName() {
		return SteamFriendsNative.getPersonaName();
	}

	public SteamAPICall setPersonaName(String personaName) {
		return new SteamAPICall(SteamFriendsNative.setPersonaName(callback, personaName));
	}

	public PersonaState getPersonaState() {
		return PersonaState.byOrdinal(SteamFriendsNative.getPersonaState());
	}

	public int getFriendCount(FriendFlags friendFlag) {
		return SteamFriendsNative.getFriendCount(friendFlag.bits);
	}

	public int getFriendCount(Collection<FriendFlags> friendFlags) {
		return SteamFriendsNative.getFriendCount(FriendFlags.asBits(friendFlags));
	}

	public SteamID getFriendByIndex(int friend, FriendFlags friendFlag) {
		return new SteamID(SteamFriendsNative.getFriendByIndex(friend, friendFlag.bits));
	}

	public SteamID getFriendByIndex(int friend, Collection<FriendFlags> friendFlags) {
		return new SteamID(SteamFriendsNative.getFriendByIndex(friend, FriendFlags.asBits(friendFlags)));
	}

	public FriendRelationship getFriendRelationship(SteamID steamIDFriend) {
		return FriendRelationship.byOrdinal(SteamFriendsNative.getFriendRelationship(steamIDFriend.handle));
	}

	public PersonaState getFriendPersonaState(SteamID steamIDFriend) {
		return PersonaState.byOrdinal(SteamFriendsNative.getFriendPersonaState(steamIDFriend.handle));
	}

	public String getFriendPersonaName(SteamID steamIDFriend) {
		return SteamFriendsNative.getFriendPersonaName(steamIDFriend.handle);
	}

	public boolean getFriendGamePlayed(SteamID steamIDFriend, FriendGameInfo friendGameInfo) {
		return SteamFriendsNative.getFriendGamePlayed(steamIDFriend.handle, friendGameInfo);
	}

	public void setInGameVoiceSpeaking(SteamID steamID, boolean speaking) {
		SteamFriendsNative.setInGameVoiceSpeaking(steamID.handle, speaking);
	}

	public int getSmallFriendAvatar(SteamID steamID) {
		return SteamFriendsNative.getSmallFriendAvatar(steamID.handle);
	}

	public int getMediumFriendAvatar(SteamID steamID) {
		return SteamFriendsNative.getMediumFriendAvatar(steamID.handle);
	}

	public int getLargeFriendAvatar(SteamID steamID) {
		return SteamFriendsNative.getLargeFriendAvatar(steamID.handle);
	}

	public boolean requestUserInformation(SteamID steamID, boolean requireNameOnly) {
		return SteamFriendsNative.requestUserInformation(steamID.handle, requireNameOnly);
	}

	public void activateGameOverlay(OverlayDialog dialog) {
		SteamFriendsNative.activateGameOverlay(dialog.id);
	}

	public void activateGameOverlayToUser(OverlayToUserDialog dialog, SteamID steamID) {
		SteamFriendsNative.activateGameOverlayToUser(dialog.id, steamID.handle);
	}

	public void activateGameOverlayToWebPage(String url, OverlayToWebPageMode mode) {
		SteamFriendsNative.activateGameOverlayToWebPage(url, mode.ordinal());
	}

	public void activateGameOverlayToStore(int appID, OverlayToStoreFlag flag) {
		SteamFriendsNative.activateGameOverlayToStore(appID, flag.ordinal());
	}

	public void setPlayedWith(SteamID steamIDUserPlayedWith) {
		SteamFriendsNative.setPlayedWith(steamIDUserPlayedWith.handle);
	}

	public void activateGameOverlayInviteDialog(SteamID steamIDLobby) {
		SteamFriendsNative.activateGameOverlayInviteDialog(steamIDLobby.handle);
	}

	public boolean setRichPresence(String key, String value) {
		return SteamFriendsNative.setRichPresence(key, value != null ? value : "");
	}

	public void clearRichPresence() {
		SteamFriendsNative.clearRichPresence();
	}

	public String getFriendRichPresence(SteamID steamIDFriend, String key) {
		return SteamFriendsNative.getFriendRichPresence(steamIDFriend.handle, key);
	}

	public int getFriendRichPresenceKeyCount(SteamID steamIDFriend) {
		return SteamFriendsNative.getFriendRichPresenceKeyCount(steamIDFriend.handle);
	}

	public String getFriendRichPresenceKeyByIndex(SteamID steamIDFriend, int index) {
		return SteamFriendsNative.getFriendRichPresenceKeyByIndex(steamIDFriend.handle, index);
	}

	public void requestFriendRichPresence(SteamID steamIDFriend) {
		SteamFriendsNative.requestFriendRichPresence(steamIDFriend.handle);
	}

	public boolean inviteUserToGame(SteamID steamIDFriend, String connectString) {
		return SteamFriendsNative.inviteUserToGame(steamIDFriend.handle, connectString);
	}

	public int getCoplayFriendCount() {
		return SteamFriendsNative.getCoplayFriendCount();
	}

	public SteamID getCoplayFriend(int index) {
		return new SteamID(SteamFriendsNative.getCoplayFriend(index));
	}

	public int getFriendCoplayTime(SteamID steamIDFriend) {
		return SteamFriendsNative.getFriendCoplayTime(steamIDFriend.handle);
	}

	public int getFriendCoplayGame(SteamID steamIDFriend) {
		return SteamFriendsNative.getFriendCoplayGame(steamIDFriend.handle);
	}

}
