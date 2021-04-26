package com.codedisaster.steamworks;

import java.nio.ByteBuffer;

@SuppressWarnings({ "unused", "UnusedReturnValue" })
public class SteamMatchmaking extends SteamInterface {

	public enum LobbyType {
		Private,
		FriendsOnly,
		Public,
		Invisible,
		PrivateUnique
	}

	public enum LobbyComparison {
		EqualToOrLessThan(-2),
		LessThan(-1),
		Equal(0),
		GreaterThan(1),
		EqualToOrGreaterThan(2),
		NotEqual(3);

		private final int value;

		LobbyComparison(int value) {
			this.value = value;
		}
	}

	public enum LobbyDistanceFilter {
		Close,
		Default,
		Far,
		Worldwide
	}

	public enum ChatRoomEnterResponse {
		Success(1),
		DoesntExist(2),
		NotAllowed(3),
		Full(4),
		Error(5),
		Banned(6),
		Limited(7),
		ClanDisabled(8),
		CommunityBan(9),
		MemberBlockedYou(10),
		YouBlockedMember(11),
		RatelimitExceeded(15);

		private final int code;
		private static final ChatRoomEnterResponse[] values = values();

		ChatRoomEnterResponse(int code) {
			this.code = code;
		}

		static ChatRoomEnterResponse byValue(int code) {
			for (ChatRoomEnterResponse value : values) {
				if (value.code == code) {
					return value;
				}
			}
			return Error; // unknown enum, default to "Error"
		}
	}

	public enum ChatMemberStateChange {
		Entered(0x0001),
		Left(0x0002),
		Disconnected(0x0004),
		Kicked(0x0008),
		Banned(0x0010);

		private final int bits;

		ChatMemberStateChange(int bits) {
			this.bits = bits;
		}

		static boolean isSet(ChatMemberStateChange value, int bitMask) {
			return (value.bits & bitMask) == value.bits;
		}
	}

	public enum ChatEntryType {
		Invalid(0),
		ChatMsg(1),
		Typing(2),
		InviteGame(3),
		Emote(4),
		LeftConversation(6),
		Entered(7),
		WasKicked(8),
		WasBanned(9),
		Disconnected(10),
		HistoricalChat(11),
		Reserved1(12),
		Reserved2(13),
		LinkBlocked(14);

		private final int code;
		private static final ChatEntryType[] values = values();

		ChatEntryType(int code) {
			this.code = code;
		}

		static ChatEntryType byValue(int code) {
			for (ChatEntryType value : values) {
				if (value.code == code) {
					return value;
				}
			}
			return Invalid;
		}
	}

	public static class ChatEntry {

		private long steamIDUser;
		private int chatEntryType;

		public SteamID getSteamIDUser() {
			return new SteamID(steamIDUser);
		}

		public ChatEntryType getChatEntryType() {
			return ChatEntryType.byValue(chatEntryType);
		}
	}

	public SteamMatchmaking(SteamMatchmakingCallback callback) {
		super(SteamMatchmakingNative.createCallback(new SteamMatchmakingCallbackAdapter(callback)));
	}

	public int getFavoriteGameCount() {
		return SteamMatchmakingNative.getFavoriteGameCount();
	}

	public boolean getFavoriteGame(int game, int[] appID, int[] ip, short[] connPort,
								   short[] queryPort, int[] flags, int[] lastPlayedOnServer) {
		return SteamMatchmakingNative.getFavoriteGame(game, appID, ip, connPort, queryPort, flags, lastPlayedOnServer);
	}

	public int addFavoriteGame(int appID, int ip, short connPort, short queryPort, int flags, int lastPlayedOnServer) {
		return SteamMatchmakingNative.addFavoriteGame(appID, ip, connPort, queryPort, flags, lastPlayedOnServer);
	}

	public boolean removeFavoriteGame(int appID, int ip, short connPort, short queryPort, int flags) {
		return SteamMatchmakingNative.removeFavoriteGame(appID, ip, connPort, queryPort, flags);
	}

	public SteamAPICall requestLobbyList() {
		return new SteamAPICall(SteamMatchmakingNative.requestLobbyList(callback));
	}

	public void addRequestLobbyListStringFilter(String keyToMatch,
												String valueToMatch,
												LobbyComparison comparisonType) {
		SteamMatchmakingNative.addRequestLobbyListStringFilter(keyToMatch, valueToMatch, comparisonType.value);
	}

	public void addRequestLobbyListNumericalFilter(String keyToMatch,
												   int valueToMatch,
												   LobbyComparison comparisonType) {
		SteamMatchmakingNative.addRequestLobbyListNumericalFilter(keyToMatch, valueToMatch, comparisonType.value);
	}

	public void addRequestLobbyListNearValueFilter(String keyToMatch, int valueToBeCloseTo) {
		SteamMatchmakingNative.addRequestLobbyListNearValueFilter(keyToMatch, valueToBeCloseTo);
	}

	public void addRequestLobbyListFilterSlotsAvailable(int slotsAvailable) {
		SteamMatchmakingNative.addRequestLobbyListFilterSlotsAvailable(slotsAvailable);
	}

	public void addRequestLobbyListDistanceFilter(LobbyDistanceFilter lobbyDistanceFilter) {
		SteamMatchmakingNative.addRequestLobbyListDistanceFilter(lobbyDistanceFilter.ordinal());
	}

	public void addRequestLobbyListResultCountFilter(int maxResults) {
		SteamMatchmakingNative.addRequestLobbyListResultCountFilter(maxResults);
	}

	public void addRequestLobbyListCompatibleMembersFilter(SteamID steamIDLobby) {
		SteamMatchmakingNative.addRequestLobbyListCompatibleMembersFilter(steamIDLobby.handle);
	}

	public SteamID getLobbyByIndex(int lobby) {
		return new SteamID(SteamMatchmakingNative.getLobbyByIndex(lobby));
	}

	public SteamAPICall createLobby(LobbyType lobbyType, int maxMembers) {
		return new SteamAPICall(SteamMatchmakingNative.createLobby(callback, lobbyType.ordinal(), maxMembers));
	}

	public SteamAPICall joinLobby(SteamID steamIDLobby) {
		return new SteamAPICall(SteamMatchmakingNative.joinLobby(callback, steamIDLobby.handle));
	}

	public void leaveLobby(SteamID steamIDLobby) {
		SteamMatchmakingNative.leaveLobby(steamIDLobby.handle);
	}

	public boolean inviteUserToLobby(SteamID steamIDLobby, SteamID steamIDInvitee) {
		return SteamMatchmakingNative.inviteUserToLobby(steamIDLobby.handle, steamIDInvitee.handle);
	}

	public int getNumLobbyMembers(SteamID steamIDLobby) {
		return SteamMatchmakingNative.getNumLobbyMembers(steamIDLobby.handle);
	}

	public SteamID getLobbyMemberByIndex(SteamID steamIDLobby, int memberIndex) {
		return new SteamID(SteamMatchmakingNative.getLobbyMemberByIndex(steamIDLobby.handle, memberIndex));
	}

	public String getLobbyData(SteamID steamIDLobby, String key) {
		return SteamMatchmakingNative.getLobbyData(steamIDLobby.handle, key);
	}

	public boolean setLobbyData(SteamID steamIDLobby, String key, String value) {
		return SteamMatchmakingNative.setLobbyData(steamIDLobby.handle, key, value);
	}

	public boolean setLobbyData(SteamID steamIDLobby, SteamMatchmakingKeyValuePair keyValuePair) {
		return SteamMatchmakingNative.setLobbyData(steamIDLobby.handle, keyValuePair.getKey(), keyValuePair.getValue());
	}

	public String getLobbyMemberData(SteamID steamIDLobby, SteamID steamIDUser, String key) {
		return SteamMatchmakingNative.getLobbyMemberData(steamIDLobby.handle, steamIDUser.handle, key);
	}

	public void setLobbyMemberData(SteamID steamIDLobby, String key, String value) {
		SteamMatchmakingNative.setLobbyMemberData(steamIDLobby.handle, key, value);
	}

	public void setLobbyMemberData(SteamID steamIDLobby, SteamMatchmakingKeyValuePair keyValuePair) {
		SteamMatchmakingNative.setLobbyMemberData(steamIDLobby.handle, keyValuePair.getKey(), keyValuePair.getValue());
	}

	public int getLobbyDataCount(SteamID steamIDLobby) {
		return SteamMatchmakingNative.getLobbyDataCount(steamIDLobby.handle);
	}

	public boolean getLobbyDataByIndex(SteamID steamIDLobby, int lobbyDataIndex,
									   SteamMatchmakingKeyValuePair keyValuePair) {
		return SteamMatchmakingNative.getLobbyDataByIndex(steamIDLobby.handle, lobbyDataIndex, keyValuePair);
	}

	public boolean deleteLobbyData(SteamID steamIDLobby, String key) {
		return SteamMatchmakingNative.deleteLobbyData(steamIDLobby.handle, key);
	}

	/**
	 * Sends chat message from a direct {@link ByteBuffer}.
	 */
	public boolean sendLobbyChatMsg(SteamID steamIDLobby, ByteBuffer data) throws SteamException {

		if (!data.isDirect()) {
			throw new SteamException("Direct buffer required!");
		}

		return SteamMatchmakingNative.sendLobbyChatMsg(steamIDLobby.handle, data, data.position(), data.remaining());
	}

	public boolean sendLobbyChatMsg(SteamID steamIDLobby, String data) {
		return SteamMatchmakingNative.sendLobbyChatMsg(steamIDLobby.handle, data);
	}

	/**
	 * Read incoming chat entry into a {@link com.codedisaster.steamworks.SteamMatchmaking.ChatEntry} structure,
	 * and a direct {@link ByteBuffer}.
	 */
	public int getLobbyChatEntry(SteamID steamIDLobby, int chatID,
								 ChatEntry chatEntry, ByteBuffer dest) throws SteamException {

		if (!dest.isDirect()) {
			throw new SteamException("Direct buffer required!");
		}

		return SteamMatchmakingNative.getLobbyChatEntry(steamIDLobby.handle, chatID, chatEntry,
				dest, dest.position(), dest.remaining());
	}

	public boolean requestLobbyData(SteamID steamIDLobby) {
		return SteamMatchmakingNative.requestLobbyData(steamIDLobby.handle);
	}

	public void setLobbyGameServer(SteamID steamIDLobby, int gameServerIP,
								   short gameServerPort, SteamID steamIDGameServer) {
		SteamMatchmakingNative.setLobbyGameServer(steamIDLobby.handle, gameServerIP, gameServerPort, steamIDGameServer.handle);
	}

	public boolean getLobbyGameServer(SteamID steamIDLobby, int[] gameServerIP,
									  short[] gameServerPort, SteamID steamIDGameServer) {
		long[] id = new long[1];

		if (SteamMatchmakingNative.getLobbyGameServer(steamIDLobby.handle, gameServerIP, gameServerPort, id)) {
			steamIDGameServer.handle = id[0];
			return true;
		}

		return false;
	}

	public boolean setLobbyMemberLimit(SteamID steamIDLobby, int maxMembers) {
		return SteamMatchmakingNative.setLobbyMemberLimit(steamIDLobby.handle, maxMembers);
	}

	public int getLobbyMemberLimit(SteamID steamIDLobby) {
		return SteamMatchmakingNative.getLobbyMemberLimit(steamIDLobby.handle);
	}

	public boolean setLobbyType(SteamID steamIDLobby, LobbyType lobbyType) {
		return SteamMatchmakingNative.setLobbyType(steamIDLobby.handle, lobbyType.ordinal());
	}

	public boolean setLobbyJoinable(SteamID steamIDLobby, boolean joinable) {
		return SteamMatchmakingNative.setLobbyJoinable(steamIDLobby.handle, joinable);
	}

	public SteamID getLobbyOwner(SteamID steamIDLobby) {
		return new SteamID(SteamMatchmakingNative.getLobbyOwner(steamIDLobby.handle));
	}

	public boolean setLobbyOwner(SteamID steamIDLobby, SteamID steamIDNewOwner) {
		return SteamMatchmakingNative.setLobbyOwner(steamIDLobby.handle, steamIDNewOwner.handle);
	}

	public boolean setLinkedLobby(SteamID steamIDLobby, SteamID steamIDLobbyDependent) {
		return SteamMatchmakingNative.setLinkedLobby(steamIDLobby.handle, steamIDLobbyDependent.handle);
	}

}
