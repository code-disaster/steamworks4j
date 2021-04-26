package com.codedisaster.steamworks;

import java.nio.ByteBuffer;

final class SteamMatchmakingNative {

	// @off

	/*JNI
		#include "SteamMatchmakingCallback.h"
	*/

	static native long createCallback(SteamMatchmakingCallbackAdapter javaCallback); /*
		return (intp) new SteamMatchmakingCallback(env, javaCallback);
	*/

	static native int getFavoriteGameCount(); /*
		return SteamMatchmaking()->GetFavoriteGameCount();
	*/

	static native boolean getFavoriteGame(int game, int[] appID, int[] ip, short[] connPort,
										  short[] queryPort, int[] flags, int[] lastPlayedOnServer); /*
		return SteamMatchmaking()->GetFavoriteGame(game, (AppId_t*) appID, (uint32*) ip, (uint16*) connPort,
			(uint16*) queryPort, (uint32*) flags, (uint32*) lastPlayedOnServer);
	*/

	static native int addFavoriteGame(int appID, int ip, short connPort,
									  short queryPort, int flags, int lastPlayedOnServer); /*
		return SteamMatchmaking()->AddFavoriteGame((AppId_t) appID, ip, connPort, queryPort, flags, lastPlayedOnServer);
	*/

	static native boolean removeFavoriteGame(int appID, int ip,
											 short connPort, short queryPort, int flags); /*
		return SteamMatchmaking()->RemoveFavoriteGame((AppId_t) appID, ip, connPort, queryPort, flags);
	*/

	static native long requestLobbyList(long callback); /*
		SteamAPICall_t handle = SteamMatchmaking()->RequestLobbyList();
		SteamMatchmakingCallback* cb = (SteamMatchmakingCallback*) callback;
		cb->onLobbyMatchListCall.Set(handle, cb, &SteamMatchmakingCallback::onLobbyMatchList);
		return handle;
	*/

	static native void addRequestLobbyListStringFilter(String keyToMatch,
													   String valueToMatch, int comparisonType); /*
		SteamMatchmaking()->AddRequestLobbyListStringFilter(keyToMatch, valueToMatch, (ELobbyComparison) comparisonType);
	*/

	static native void addRequestLobbyListNumericalFilter(String keyToMatch,
														  int valueToMatch, int comparisonType); /*
		SteamMatchmaking()->AddRequestLobbyListNumericalFilter(keyToMatch, valueToMatch, (ELobbyComparison) comparisonType);
	*/

	static native void addRequestLobbyListNearValueFilter(String keyToMatch,
														  int valueToBeCloseTo); /*
		SteamMatchmaking()->AddRequestLobbyListNearValueFilter(keyToMatch, valueToBeCloseTo);
	*/

	static native void addRequestLobbyListFilterSlotsAvailable(int slotsAvailable); /*
		SteamMatchmaking()->AddRequestLobbyListFilterSlotsAvailable(slotsAvailable);
	*/

	static native void addRequestLobbyListDistanceFilter(int lobbyDistanceFilter); /*
		SteamMatchmaking()->AddRequestLobbyListDistanceFilter((ELobbyDistanceFilter) lobbyDistanceFilter);
	*/

	static native void addRequestLobbyListResultCountFilter(int maxResults); /*
		SteamMatchmaking()->AddRequestLobbyListResultCountFilter(maxResults);
	*/

	static native void addRequestLobbyListCompatibleMembersFilter(long steamIDLobby); /*
		SteamMatchmaking()->AddRequestLobbyListCompatibleMembersFilter((uint64) steamIDLobby);
	*/

	static native long getLobbyByIndex(int lobby); /*
		CSteamID steamID = SteamMatchmaking()->GetLobbyByIndex(lobby);
		return (int64) steamID.ConvertToUint64();
	*/

	static native long createLobby(long callback, int lobbyType, int maxMembers); /*
		SteamAPICall_t handle = SteamMatchmaking()->CreateLobby((ELobbyType) lobbyType, maxMembers);
		SteamMatchmakingCallback* cb = (SteamMatchmakingCallback*) callback;
		cb->onLobbyCreatedCall.Set(handle, cb, &SteamMatchmakingCallback::onLobbyCreated);
		return handle;
	*/

	static native long joinLobby(long callback, long steamIDLobby); /*
		SteamAPICall_t handle = SteamMatchmaking()->JoinLobby((uint64) steamIDLobby);
		SteamMatchmakingCallback* cb = (SteamMatchmakingCallback*) callback;
		cb->onLobbyEnterCall.Set(handle, cb, &SteamMatchmakingCallback::onLobbyEnter);
		return handle;
	*/

	static native void leaveLobby(long steamIDLobby); /*
		SteamMatchmaking()->LeaveLobby((uint64) steamIDLobby);
	*/

	static native boolean inviteUserToLobby(long steamIDLobby, long steamIDInvitee); /*
		return SteamMatchmaking()->InviteUserToLobby((uint64) steamIDLobby, (uint64) steamIDInvitee);
	*/

	static native int getNumLobbyMembers(long steamIDLobby); /*
		return SteamMatchmaking()->GetNumLobbyMembers((uint64) steamIDLobby);
	*/

	static native long getLobbyMemberByIndex(long steamIDLobby, int memberIndex); /*
		CSteamID steamID = SteamMatchmaking()->GetLobbyMemberByIndex((uint64) steamIDLobby, memberIndex);
		return (int64) steamID.ConvertToUint64();
	*/

	static native String getLobbyData(long steamIDLobby, String key); /*
		jstring value = env->NewStringUTF(SteamMatchmaking()->GetLobbyData((uint64) steamIDLobby, key));
		return value;
	*/

	static native boolean setLobbyData(long steamIDLobby, String key, String value); /*
		return SteamMatchmaking()->SetLobbyData((uint64) steamIDLobby, key, value);
	*/

	static native String getLobbyMemberData(long steamIDLobby, long steamIDUser, String key); /*
		jstring value = env->NewStringUTF(SteamMatchmaking()->GetLobbyMemberData((uint64) steamIDLobby, (uint64) steamIDUser, key));
		return value;
	*/

	static native void setLobbyMemberData(long steamIDLobby, String key, String value); /*
		SteamMatchmaking()->SetLobbyMemberData((uint64) steamIDLobby, key, value);
	*/

	static native int getLobbyDataCount(long steamIDLobby); /*
		return SteamMatchmaking()->GetLobbyDataCount((uint64) steamIDLobby);
	*/

	static native boolean getLobbyDataByIndex(long steamIDLobby, int lobbyDataIndex,
											  SteamMatchmakingKeyValuePair keyValuePair); /*
		MatchMakingKeyValuePair_t result;
		bool success = SteamMatchmaking()->GetLobbyDataByIndex((uint64) steamIDLobby, lobbyDataIndex,
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

	static native boolean deleteLobbyData(long steamIDLobby, String key); /*
		return SteamMatchmaking()->DeleteLobbyData((uint64) steamIDLobby, key);
	*/

	static native boolean sendLobbyChatMsg(long steamIDLobby,
										   ByteBuffer data, int offset, int size); /*
		return SteamMatchmaking()->SendLobbyChatMsg((uint64) steamIDLobby, &data[offset], size);
	*/

	static native boolean sendLobbyChatMsg(long steamIDLobby, String message); /*
		int len = (int) strlen(message) + 1;
		return SteamMatchmaking()->SendLobbyChatMsg((uint64) steamIDLobby, message, len);
	*/

	static native int getLobbyChatEntry(long steamIDLobby, int chatID, SteamMatchmaking.ChatEntry chatEntry,
										ByteBuffer buffer, int offset, int size); /*
		CSteamID steamIDUser;
		EChatEntryType chatEntryType;
		int received = SteamMatchmaking()->GetLobbyChatEntry((uint64) steamIDLobby, chatID, &steamIDUser, &buffer[offset], size, &chatEntryType);

		if (received >= 0) {
			jclass clazz = env->GetObjectClass(chatEntry);

			jfieldID field = env->GetFieldID(clazz, "steamIDUser", "J");
			env->SetLongField(chatEntry, field, (jlong) steamIDUser.ConvertToUint64());

			field = env->GetFieldID(clazz, "chatEntryType", "I");
			env->SetIntField(chatEntry, field, (jint) chatEntryType);
		}

		return received;
	*/

	static native boolean requestLobbyData(long steamIDLobby); /*
		return SteamMatchmaking()->RequestLobbyData((uint64) steamIDLobby);
	*/

	static native void setLobbyGameServer(long steamIDLobby, int gameServerIP,
										  short gameServerPort, long steamIDGameServer); /*
		SteamMatchmaking()->SetLobbyGameServer((uint64) steamIDLobby, gameServerIP, gameServerPort, (uint64) steamIDGameServer);
	*/

	static native boolean getLobbyGameServer(long steamIDLobby, int[] gameServerIP,
											 short[] gameServerPort, long[] steamIDGameServer); /*
		CSteamID _steamIDGameServer;
		if (SteamMatchmaking()->GetLobbyGameServer((uint64) steamIDLobby, (uint32*) gameServerIP,
			(uint16*) gameServerPort, &_steamIDGameServer)) {
			*steamIDGameServer = (jlong) _steamIDGameServer.ConvertToUint64();
			return true;
		}
		return false;
	*/

	static native boolean setLobbyMemberLimit(long steamIDLobby, int maxMembers); /*
		return SteamMatchmaking()->SetLobbyMemberLimit((uint64) steamIDLobby, maxMembers);
	*/

	static native int getLobbyMemberLimit(long steamIDLobby); /*
		return SteamMatchmaking()->GetLobbyMemberLimit((uint64) steamIDLobby);
	*/

	static native boolean setLobbyType(long steamIDLobby, int lobbyType); /*
		return SteamMatchmaking()->SetLobbyType((uint64) steamIDLobby, (ELobbyType) lobbyType);
	*/

	static native boolean setLobbyJoinable(long steamIDLobby, boolean joinable); /*
		return SteamMatchmaking()->SetLobbyJoinable((uint64) steamIDLobby, joinable);
	*/

	static native long getLobbyOwner(long steamIDLobby); /*
		return (int64) SteamMatchmaking()->GetLobbyOwner((uint64) steamIDLobby).ConvertToUint64();
	*/

	static native boolean setLobbyOwner(long steamIDLobby, long steamIDNewOwner); /*
		return SteamMatchmaking()->SetLobbyOwner((uint64) steamIDLobby, (uint64) steamIDNewOwner);
	*/

	static native boolean setLinkedLobby(long steamIDLobby, long steamIDLobbyDependent); /*
		return SteamMatchmaking()->SetLinkedLobby((uint64) steamIDLobby, (uint64) steamIDLobbyDependent);
	*/

}
