package com.codedisaster.steamworks;

public class SteamMatchmaking extends SteamInterface {

	public enum LobbyType {
		Private,
		FriendsOnly,
		Public,
		Invisible
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
		YouBlockedMember(11);

		private final int code;
		private static final ChatRoomEnterResponse[] values = values();

		ChatRoomEnterResponse(int code) {
			this.code = code;
		}

		static ChatRoomEnterResponse byCode(int code) {
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

		static ChatEntryType byCode(int code) {
			for (ChatEntryType value : values) {
				if (value.code == code) {
					return value;
				}
			}
			return Invalid;
		}
	}

	public SteamMatchmaking(SteamMatchmakingCallback callback) {
		super(SteamAPI.getSteamMatchmakingPointer(), createCallback(new SteamMatchmakingCallbackAdapter(callback)));
	}

	public SteamAPICall requestLobbyList() {
		return new SteamAPICall(requestLobbyList(pointer));
	}

	public void addRequestLobbyListStringFilter(String keyToMatch,
												String valueToMatch,
												LobbyComparison comparisonType) {
		addRequestLobbyListStringFilter(pointer, keyToMatch, valueToMatch, comparisonType.value);
	}

	public void addRequestLobbyListNumericalFilter(String keyToMatch,
												   int valueToMatch,
												   LobbyComparison comparisonType) {
		addRequestLobbyListNumericalFilter(pointer, keyToMatch, valueToMatch, comparisonType.value);
	}

	public void addRequestLobbyListNearValueFilter(String keyToMatch, int valueToBeCloseTo) {
		addRequestLobbyListNearValueFilter(pointer, keyToMatch, valueToBeCloseTo);
	}

	public void addRequestLobbyListFilterSlotsAvailable(int slotsAvailable) {
		addRequestLobbyListFilterSlotsAvailable(pointer, slotsAvailable);
	}

	public void addRequestLobbyListDistanceFilter(LobbyDistanceFilter lobbyDistanceFilter) {
		addRequestLobbyListDistanceFilter(pointer, lobbyDistanceFilter.ordinal());
	}

	public void addRequestLobbyListResultCountFilter(int maxResults) {
		addRequestLobbyListResultCountFilter(pointer, maxResults);
	}

	public void addRequestLobbyListCompatibleMembersFilter(SteamID steamIDLobby) {
		addRequestLobbyListCompatibleMembersFilter(pointer, steamIDLobby.handle);
	}

	// @off

	/*JNI
		#include "SteamMatchmakingCallback.h"
	*/

	private static native long createCallback(SteamMatchmakingCallbackAdapter javaCallback); /*
		return (long) new SteamMatchmakingCallback(env, javaCallback);
	*/

	private static native long requestLobbyList(long pointer); /*
		ISteamMatchmaking* matchmaking = (ISteamMatchmaking*) pointer;
		return (long) matchmaking->RequestLobbyList();
	*/

	private static native void addRequestLobbyListStringFilter(long pointer, String keyToMatch,
															   String valueToMatch, int comparisonType); /*
		ISteamMatchmaking* matchmaking = (ISteamMatchmaking*) pointer;
		matchmaking->AddRequestLobbyListStringFilter(keyToMatch, valueToMatch, (ELobbyComparison) comparisonType);
	*/

	private static native void addRequestLobbyListNumericalFilter(long pointer, String keyToMatch,
																  int valueToMatch, int comparisonType); /*
		ISteamMatchmaking* matchmaking = (ISteamMatchmaking*) pointer;
		matchmaking->AddRequestLobbyListNumericalFilter(keyToMatch, valueToMatch, (ELobbyComparison) comparisonType);
	*/

	private static native void addRequestLobbyListNearValueFilter(long pointer, String keyToMatch,
																  int valueToBeCloseTo); /*
		ISteamMatchmaking* matchmaking = (ISteamMatchmaking*) pointer;
		matchmaking->AddRequestLobbyListNearValueFilter(keyToMatch, valueToBeCloseTo);
	*/

	private static native void addRequestLobbyListFilterSlotsAvailable(long pointer, int slotsAvailable); /*
		ISteamMatchmaking* matchmaking = (ISteamMatchmaking*) pointer;
		matchmaking->AddRequestLobbyListFilterSlotsAvailable(slotsAvailable);
	*/

	private static native void addRequestLobbyListDistanceFilter(long pointer, int lobbyDistanceFilter); /*
		ISteamMatchmaking* matchmaking = (ISteamMatchmaking*) pointer;
		matchmaking->AddRequestLobbyListDistanceFilter((ELobbyDistanceFilter) lobbyDistanceFilter);
	*/

	private static native void addRequestLobbyListResultCountFilter(long pointer, int maxResults); /*
		ISteamMatchmaking* matchmaking = (ISteamMatchmaking*) pointer;
		matchmaking->AddRequestLobbyListResultCountFilter(maxResults);
	*/

	private static native void addRequestLobbyListCompatibleMembersFilter(long pointer, long steamIDLobby); /*
		ISteamMatchmaking* matchmaking = (ISteamMatchmaking*) pointer;
		matchmaking->AddRequestLobbyListCompatibleMembersFilter((uint64) steamIDLobby);
	*/

}
