package com.codedisaster.steamworks;

import java.nio.ByteBuffer;

import com.codedisaster.steamworks.SteamMatchmaking.ChatEntry;

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
	
	public static class FavoriteGameEntry {

		private int appID;
		private int ip;
		private short connPort;
		private short queryPort;
		private int flags;
		private int lastPlayedOnServer;
		
		public int getAppID() {
			return appID;
		}
		
		public int getIP() {
			return ip;
		}
		
		public short getConnPort() {
			return connPort;
		}
		
		public short getQueryPort() {
			return queryPort;
		}
		
		public int getFlags() {
			return flags;
		}
		
		public int getLastPlayedOnServer() {
			return lastPlayedOnServer;
		}
	}
	
	public static class GameServerEntry {

		private long steamIDGameServer;
		private int gameServerIP;
		private short gameServerPort;

		public SteamID getSteamIDGameServer() {
			return new SteamID(steamIDGameServer);
		}

		public int getGameServerIP() {
			return gameServerIP;
		}
		
		public short getGameServerPort() {
			return gameServerPort;
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

	/**
	 * Returns the number of favorites servers the user has stored
	 */
	public int getFavoriteGameCount() {
		return getFavoriteGameCount(pointer);
	}
	
	/**
	 * Returns the details of the game server
	 * <br>count is of range [0, GetFavoriteGameCount())
	 * <br>ip, connPort are filled in the with IP:port of the game server
	 * <br>flags specify whether the game server was stored as an explicit favorite or in the history of connections
	 * <br>lastPlayedOnServer is filled in the with the Unix time the favorite was added
	 */
	public boolean getFavoriteGame(int count, FavoriteGameEntry favoriteGameEntry) {
		return getFavoriteGame(pointer, count, favoriteGameEntry);
	}
	
	/**
	 * Adds the game server to the local list; updates the time played of the server if it already exists in the list
	 */
	public int addFavoriteGame(int appID, int ip, short connPort, short queryPort, int flags, int lastPlayedOnServer) {
		return addFavoriteGame(pointer, appID, ip, connPort, queryPort, flags, lastPlayedOnServer);
	}
	
	/**
	 * Removes the game server from the local storage
	 * @return true if one was removed
	 */
	public boolean removeFavoriteGame(int appID, int ip, short connPort, short queryPort, int flags) {
		return removeFavoriteGame(pointer, appID, ip, connPort, queryPort, flags);
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
	
	/**
	 * Sets the game server associated with the lobby either the IP/Port or the steamID of the game server has to be valid, depending on how you want the clients to be able to connect
	 */
	public void setLobbyGameServer(SteamID steamIDLobby, int gameServerIP, short gameServerPort, SteamID steamIDGameServer) {
		setLobbyGameServer(pointer, steamIDLobby.handle, gameServerIP, gameServerPort, steamIDGameServer.handle);
	}
	
	/**
	 * Returns the details of a game server set in a lobby
	 * @return false if there is no game server set, or that lobby doesn't exist
	 */
	public boolean getLobbyGameServer(SteamID steamIDLobby, GameServerEntry gameServerEntry) {
		return getLobbyGameServer(pointer, steamIDLobby.handle, gameServerEntry);
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
	
	private static native int getFavoriteGameCount(long pointer); /*
	ISteamMatchmaking* matchmaking = (ISteamMatchmaking*) pointer;
	return matchmaking->GetFavoriteGameCount();
	*/
	
	private static native boolean getFavoriteGame(long pointer, int count, FavoriteGameEntry favoriteGameEntry); /*
	ISteamMatchmaking* matchmaking = (ISteamMatchmaking*) pointer;

	AppId_t appID;
	uint32 ip;
	uint16 connPort;
	uint16 queryPort;
	uint32 flags;
	uint32 lastPlayedOnServer;

	bool success = matchmaking->GetFavoriteGame(count, &appID, &ip, &connPort, &queryPort, &flags, &lastPlayedOnServer);

	if (success) {
		jclass clazz = env->GetObjectClass(favoriteGameEntry);

		jfieldID field = env->GetFieldID(clazz, "appID", "I");
		env->SetIntField(favoriteGameEntry, field, (jint) appID);

		field = env->GetFieldID(clazz, "ip", "I");
		env->SetIntField(favoriteGameEntry, field, (jint) ip);
		
		field = env->GetFieldID(clazz, "connPort", "S");
		env->SetShortField(favoriteGameEntry, field, (jshort) connPort);
		
		field = env->GetFieldID(clazz, "queryPort", "S");
		env->SetShortField(favoriteGameEntry, field, (jshort) queryPort);
		
		field = env->GetFieldID(clazz, "flags", "I");
		env->SetIntField(favoriteGameEntry, field, (jint) flags);
		
		field = env->GetFieldID(clazz, "lastPlayedOnServer", "I");
		env->SetIntField(favoriteGameEntry, field, (jint) lastPlayedOnServer);
	}
	
	return success;
	*/
	
	private static native int addFavoriteGame(long pointer, int appID, int ip, short connPort, short queryPort, int flags, int lastPlayedOnServer); /*
	ISteamMatchmaking* matchmaking = (ISteamMatchmaking*) pointer;
	return matchmaking->AddFavoriteGame((AppId_t) appID, ip, connPort, queryPort, flags, lastPlayedOnServer);
	*/
	
	private static native boolean removeFavoriteGame(long pointer, int appID, int ip, short connPort, short queryPort, int flags); /*
	ISteamMatchmaking* matchmaking = (ISteamMatchmaking*) pointer;
	return matchmaking->RemoveFavoriteGame((AppId_t) appID, ip, connPort, queryPort, flags);
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
	
	private static native void setLobbyGameServer(long pointer, long steamIDLobby, int gameServerIP, short gameServerPort, long steamIDGameServer); /*
	ISteamMatchmaking* matchmaking = (ISteamMatchmaking*) pointer;
	matchmaking->SetLobbyGameServer((uint64) steamIDLobby, gameServerIP, gameServerPort, (uint64) steamIDGameServer);
	*/
	
	private static native boolean getLobbyGameServer(long pointer, long steamIDLobby, GameServerEntry gameServerEntry); /*
	ISteamMatchmaking* matchmaking = (ISteamMatchmaking*) pointer;
	
	CSteamID steamIDGameServer;
	uint32 gameServerIP;
	uint16 gameServerPort;
	
	bool success = matchmaking->GetLobbyGameServer((uint64) steamIDLobby, &gameServerIP, &gameServerPort, &steamIDGameServer);

	if (success) {
		jclass clazz = env->GetObjectClass(gameServerEntry);

		jfieldID field = env->GetFieldID(clazz, "steamIDGameServer", "J");
		env->SetLongField(gameServerEntry, field, (jlong) steamIDGameServer.ConvertToUint64());

		field = env->GetFieldID(clazz, "gameServerIP", "I");
		env->SetIntField(gameServerEntry, field, (jint) gameServerIP);
		
		field = env->GetFieldID(clazz, "gameServerPort", "S");
		env->SetShortField(gameServerEntry, field, (jshort) gameServerPort);
	}
	
	return success;
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
