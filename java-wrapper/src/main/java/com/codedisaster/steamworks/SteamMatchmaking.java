package com.codedisaster.steamworks;

import java.nio.ByteBuffer;

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

	public static class ChatEntry {

		private long steamIDUser;
		private int chatEntryType;

		public SteamID getSteamIDUser() {
			return new SteamID(steamIDUser);
		}

		public ChatEntryType getChatEntryType() {
			return ChatEntryType.byCode(chatEntryType);
		}
	}

	public SteamMatchmaking(SteamMatchmakingCallback callback) {
		super(SteamAPI.getSteamMatchmakingPointer(), createCallback(new SteamMatchmakingCallbackAdapter(callback)));
	}

	public SteamAPICall requestLobbyList() {
		return new SteamAPICall(requestLobbyList(pointer, callback));
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

	public SteamID getLobbyByIndex(int lobby) {
		return new SteamID(getLobbyByIndex(pointer, lobby));
	}

	public SteamAPICall createLobby(LobbyType lobbyType, int maxMembers) {
		return new SteamAPICall(createLobby(pointer, callback, lobbyType.ordinal(), maxMembers));
	}

	public SteamAPICall joinLobby(SteamID steamIDLobby) {
		return new SteamAPICall(joinLobby(pointer, callback, steamIDLobby.handle));
	}

	public void leaveLobby(SteamID steamIDLobby) {
		leaveLobby(pointer, steamIDLobby.handle);
	}

	public boolean inviteUserToLobby(SteamID steamIDLobby, SteamID steamIDInvitee) {
		return inviteUserToLobby(pointer, steamIDLobby.handle, steamIDInvitee.handle);
	}

	public int getNumLobbyMembers(SteamID steamIDLobby) {
		return getNumLobbyMembers(pointer, steamIDLobby.handle);
	}

	public SteamID getLobbyMemberByIndex(SteamID steamIDLobby, int memberIndex) {
		return new SteamID(getLobbyMemberByIndex(pointer, steamIDLobby.handle, memberIndex));
	}

	public String getLobbyData(SteamID steamIDLobby, String key) {
		return getLobbyData(pointer, steamIDLobby.handle, key);
	}

	public boolean setLobbyData(SteamID steamIDLobby, String key, String value) {
		return setLobbyData(pointer, steamIDLobby.handle, key, value);
	}

	public boolean setLobbyData(SteamID steamIDLobby, SteamMatchMakingKeyValuePair keyValuePair) {
		return setLobbyData(pointer, steamIDLobby.handle, keyValuePair.getKey(), keyValuePair.getValue());
	}

	public int getLobbyDataCount(SteamID steamIDLobby) {
		return getLobbyDataCount(pointer, steamIDLobby.handle);
	}

	public boolean getLobbyDataByIndex(SteamID steamIDLobby, int lobbyDataIndex,
									   SteamMatchMakingKeyValuePair keyValuePair) {
		return getLobbyDataByIndex(pointer, steamIDLobby.handle, lobbyDataIndex, keyValuePair);
	}

	public boolean deleteLobbyData(SteamID steamIDLobby, String key) {
		return deleteLobbyData(pointer, steamIDLobby.handle, key);
	}

	/**
	 * Sends chat message from a direct {@link ByteBuffer}.
	 *
	 * The message data sent ranges from <code>ByteBuffer.position()</code> to <code>ByteBuffer.limit()</code>.
	 */
	public boolean sendLobbyChatMsg(SteamID steamIDLobby, ByteBuffer data) throws SteamException {
		int offset = data.position();
		int size = data.limit() - offset;
		return sendLobbyChatMsg(steamIDLobby, data, offset, size);
	}

	/**
	 * Sends chat message from a direct {@link ByteBuffer}.
	 *
	 * This function ignores the buffer's internal position and limit properties.
	 */
	public boolean sendLobbyChatMsg(SteamID steamIDLobby, ByteBuffer data, int offset, int size) throws SteamException {

		if (!data.isDirect()) {
			throw new SteamException("Direct buffer required!");
		}

		if (data.capacity() < offset + size) {
			throw new SteamException("Buffer capacity exceeded!");
		}

		return sendLobbyChatMsg(pointer, steamIDLobby.handle, data, offset, size);
	}

	public boolean sendLobbyChatMsg(SteamID steamIDLobby, String data) {
		return sendLobbyChatMsg(pointer, steamIDLobby.handle, data);
	}

	/**
	 * Read incoming chat entry into a {@link com.codedisaster.steamworks.SteamMatchmaking.ChatEntry} structure,
	 * and a direct {@link ByteBuffer}.
	 *
	 * The message data is stored starting at <code>ByteBuffer.position()</code>, up to <code>ByteBuffer.limit()</code>.
	 * On return, the buffer limit is set to <code>ByteBuffer.position()</code> plus the number of bytes received.
	 */
	public int getLobbyChatEntry(SteamID steamIDLobby, int chatID, ChatEntry chatEntry,
								 ByteBuffer dest) throws SteamException {

		int offset = dest.position();
		int capacity = dest.limit() - offset;

		return getLobbyChatEntry(steamIDLobby, chatID, chatEntry, dest, offset, capacity);
	}

	/**
	 * Read incoming chat entry into a {@link com.codedisaster.steamworks.SteamMatchmaking.ChatEntry} structure,
	 * and a direct {@link ByteBuffer}.
	 *
	 * This function ignores the buffer's internal position and limit properties. On return, the buffer position is set
	 * to <code>offset</code>, the buffer limit is set to <code>offset</code> plus the number of bytes received.
	 */
	public int getLobbyChatEntry(SteamID steamIDLobby, int chatID, ChatEntry chatEntry,
								 ByteBuffer dest, int offset, int capacity) throws SteamException {

		if (!dest.isDirect()) {
			throw new SteamException("Direct buffer required!");
		}

		if (dest.capacity() < offset + capacity) {
			throw new SteamException("Buffer capacity exceeded!");
		}

		int bytesRead = getLobbyChatEntry(pointer, steamIDLobby.handle, chatID, chatEntry, dest, offset, capacity);

		if (bytesRead >= 0) {
			dest.position(offset);
			dest.limit(offset + bytesRead);
		}

		return bytesRead;
	}

	public boolean requestLobbyData(SteamID steamIDLobby) {
		return requestLobbyData(pointer, steamIDLobby.handle);
	}

	public boolean setLobbyMemberLimit(SteamID steamIDLobby, int maxMembers) {
		return setLobbyMemberLimit(pointer, steamIDLobby.handle, maxMembers);
	}

	public boolean getLobbyMemberLimit(SteamID steamIDLobby) {
		return getLobbyMemberLimit(pointer, steamIDLobby.handle);
	}

	public boolean setLobbyType(SteamID steamIDLobby, LobbyType lobbyType) {
		return setLobbyType(pointer, steamIDLobby.handle, lobbyType.ordinal());
	}

	public boolean setLobbyJoinable(SteamID steamIDLobby, boolean joinable) {
		return setLobbyJoinable(pointer, steamIDLobby.handle, joinable);
	}

	public SteamID getLobbyOwner(SteamID steamIDLobby) {
		return new SteamID(getLobbyOwner(pointer, steamIDLobby.handle));
	}

	public boolean setLobbyOwner(SteamID steamIDLobby, SteamID steamIDNewOwner) {
		return setLobbyOwner(pointer, steamIDLobby.handle, steamIDNewOwner.handle);
	}

	public boolean setLinkedLobby(SteamID steamIDLobby, SteamID steamIDLobbyDependent) {
		return setLinkedLobby(pointer, steamIDLobby.handle, steamIDLobbyDependent.handle);
	}

	// @off

	/*JNI
		#include "SteamMatchmakingCallback.h"
	*/

	private static native long createCallback(SteamMatchmakingCallbackAdapter javaCallback); /*
		return (intp) new SteamMatchmakingCallback(env, javaCallback);
	*/

	private static native long requestLobbyList(long pointer, long callback); /*
		ISteamMatchmaking* matchmaking = (ISteamMatchmaking*) pointer;
		SteamAPICall_t handle = matchmaking->RequestLobbyList();
		SteamMatchmakingCallback* cb = (SteamMatchmakingCallback*) callback;
		cb->onLobbyMatchListCall.Set(handle, cb, &SteamMatchmakingCallback::onLobbyMatchList);
		return handle;
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

	private static native long getLobbyByIndex(long pointer, int lobby); /*
		ISteamMatchmaking* matchmaking = (ISteamMatchmaking*) pointer;
		CSteamID steamID = matchmaking->GetLobbyByIndex(lobby);
		return (int64) steamID.ConvertToUint64();
	*/

	private static native long createLobby(long pointer, long callback, int lobbyType, int maxMembers); /*
		ISteamMatchmaking* matchmaking = (ISteamMatchmaking*) pointer;
		SteamAPICall_t handle = matchmaking->CreateLobby((ELobbyType) lobbyType, maxMembers);
		SteamMatchmakingCallback* cb = (SteamMatchmakingCallback*) callback;
		cb->onLobbyCreatedCall.Set(handle, cb, &SteamMatchmakingCallback::onLobbyCreated);
		return handle;
	*/

	private static native long joinLobby(long pointer, long callback, long steamIDLobby); /*
		ISteamMatchmaking* matchmaking = (ISteamMatchmaking*) pointer;
		SteamAPICall_t handle = matchmaking->JoinLobby((uint64) steamIDLobby);
		SteamMatchmakingCallback* cb = (SteamMatchmakingCallback*) callback;
		cb->onLobbyEnterCall.Set(handle, cb, &SteamMatchmakingCallback::onLobbyEnter);
		return handle;
	*/

	private static native void leaveLobby(long pointer, long steamIDLobby); /*
		ISteamMatchmaking* matchmaking = (ISteamMatchmaking*) pointer;
		matchmaking->LeaveLobby((uint64) steamIDLobby);
	*/

	private static native boolean inviteUserToLobby(long pointer, long steamIDLobby, long steamIDInvitee); /*
		ISteamMatchmaking* matchmaking = (ISteamMatchmaking*) pointer;
		return matchmaking->InviteUserToLobby((uint64) steamIDLobby, (uint64) steamIDInvitee);
	*/

	private static native int getNumLobbyMembers(long pointer, long steamIDLobby); /*
		ISteamMatchmaking* matchmaking = (ISteamMatchmaking*) pointer;
		return matchmaking->GetNumLobbyMembers((uint64) steamIDLobby);
	*/

	private static native long getLobbyMemberByIndex(long pointer, long steamIDLobby, int memberIndex); /*
		ISteamMatchmaking* matchmaking = (ISteamMatchmaking*) pointer;
		CSteamID steamID = matchmaking->GetLobbyMemberByIndex((uint64) steamIDLobby, memberIndex);
		return (int64) steamID.ConvertToUint64();
	*/

	private static native String getLobbyData(long pointer, long steamIDLobby, String key); /*
		ISteamMatchmaking* matchmaking = (ISteamMatchmaking*) pointer;
		jstring value = env->NewStringUTF(matchmaking->GetLobbyData((uint64) steamIDLobby, key));
		return value;
	*/

	private static native boolean setLobbyData(long pointer, long steamIDLobby, String key, String value); /*
		ISteamMatchmaking* matchmaking = (ISteamMatchmaking*) pointer;
		return matchmaking->SetLobbyData((uint64) steamIDLobby, key, value);
	*/

	private static native int getLobbyDataCount(long pointer, long steamIDLobby); /*
		ISteamMatchmaking* matchmaking = (ISteamMatchmaking*) pointer;
		return matchmaking->GetLobbyDataCount((uint64) steamIDLobby);
	*/

	private static native boolean getLobbyDataByIndex(long pointer, long steamIDLobby, int lobbyDataIndex,
													  SteamMatchMakingKeyValuePair keyValuePair); /*
		ISteamMatchmaking* matchmaking = (ISteamMatchmaking*) pointer;
		MatchMakingKeyValuePair_t result;
		bool success = matchmaking->GetLobbyDataByIndex((uint64) steamIDLobby, lobbyDataIndex,
			result.m_szKey, 256, result.m_szValue, 256);
		if (success) {
			jclass clazz = env->GetObjectClass(keyValuePair);

			jstring key = env->NewStringUTF(result.m_szKey);
			jfieldID field = env->GetFieldID(clazz, "key", "Ljava/lang/String;");
			env->SetObjectField(keyValuePair, field, key);

			jstring value = env->NewStringUTF(result.m_szValue);
			field = env->GetFieldID(clazz, "value", "Ljava/lang/String;");
			env->SetObjectField(keyValuePair, field, value);
		}
		return success;
	*/

	private static native boolean deleteLobbyData(long pointer, long steamIDLobby, String key); /*
		ISteamMatchmaking* matchmaking = (ISteamMatchmaking*) pointer;
		return matchmaking->DeleteLobbyData((uint64) steamIDLobby, key);
	*/

	private static native boolean sendLobbyChatMsg(long pointer, long steamIDLobby,
												   ByteBuffer data, int offset, int size); /*
		ISteamMatchmaking* matchmaking = (ISteamMatchmaking*) pointer;
		return matchmaking->SendLobbyChatMsg((uint64) steamIDLobby, &data[offset], size);
	*/

	private static native boolean sendLobbyChatMsg(long pointer, long steamIDLobby, String message); /*
		ISteamMatchmaking* matchmaking = (ISteamMatchmaking*) pointer;
		int len = (int) strlen(message) + 1;
		return matchmaking->SendLobbyChatMsg((uint64) steamIDLobby, message, len);
	*/

	private static native int getLobbyChatEntry(long pointer, long steamIDLobby, int chatID, ChatEntry chatEntry,
												ByteBuffer buffer, int offset, int size); /*
		ISteamMatchmaking* matchmaking = (ISteamMatchmaking*) pointer;

		CSteamID steamIDUser;
		EChatEntryType chatEntryType;
		int received = matchmaking->GetLobbyChatEntry((uint64) steamIDLobby, chatID, &steamIDUser, &buffer[offset], size, &chatEntryType);

		if (received >= 0) {
			jclass clazz = env->GetObjectClass(chatEntry);

			jfieldID field = env->GetFieldID(clazz, "steamIDUser", "J");
			env->SetLongField(chatEntry, field, (jlong) steamIDUser.ConvertToUint64());

			field = env->GetFieldID(clazz, "chatEntryType", "I");
			env->SetIntField(chatEntry, field, (jint) chatEntryType);
		}

		return received;
	*/

	private static native boolean requestLobbyData(long pointer, long steamIDLobby); /*
		ISteamMatchmaking* matchmaking = (ISteamMatchmaking*) pointer;
		return matchmaking->RequestLobbyData((uint64) steamIDLobby);
	*/

	private static native boolean setLobbyMemberLimit(long pointer, long steamIDLobby, int maxMembers); /*
		ISteamMatchmaking* matchmaking = (ISteamMatchmaking*) pointer;
		return matchmaking->SetLobbyMemberLimit((uint64) steamIDLobby, maxMembers);
	*/

	private static native boolean getLobbyMemberLimit(long pointer, long steamIDLobby); /*
		ISteamMatchmaking* matchmaking = (ISteamMatchmaking*) pointer;
		return matchmaking->GetLobbyMemberLimit((uint64) steamIDLobby);
	*/

	private static native boolean setLobbyType(long pointer, long steamIDLobby, int lobbyType); /*
		ISteamMatchmaking* matchmaking = (ISteamMatchmaking*) pointer;
		return matchmaking->SetLobbyType((uint64) steamIDLobby, (ELobbyType) lobbyType);
	*/

	private static native boolean setLobbyJoinable(long pointer, long steamIDLobby, boolean joinable); /*
		ISteamMatchmaking* matchmaking = (ISteamMatchmaking*) pointer;
		return matchmaking->SetLobbyJoinable((uint64) steamIDLobby, joinable);
	*/

	private static native long getLobbyOwner(long pointer, long steamIDLobby); /*
		ISteamMatchmaking* matchmaking = (ISteamMatchmaking*) pointer;
		return (int64) matchmaking->GetLobbyOwner((uint64) steamIDLobby).ConvertToUint64();
	*/

	private static native boolean setLobbyOwner(long pointer, long steamIDLobby, long steamIDNewOwner); /*
		ISteamMatchmaking* matchmaking = (ISteamMatchmaking*) pointer;
		return matchmaking->SetLobbyOwner((uint64) steamIDLobby, (uint64) steamIDNewOwner);
	*/

	private static native boolean setLinkedLobby(long pointer, long steamIDLobby, long steamIDLobbyDependent); /*
		ISteamMatchmaking* matchmaking = (ISteamMatchmaking*) pointer;
		return matchmaking->SetLinkedLobby((uint64) steamIDLobby, (uint64) steamIDLobbyDependent);
	*/

}
