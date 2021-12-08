package com.codedisaster.steamworks;

final class SteamFriendsNative {

	// @off

	/*JNI
		#include <steam_api.h>
		#include "SteamFriendsCallback.h"
	*/

	static native long createCallback(SteamFriendsCallbackAdapter javaCallback); /*
		return (intp) new SteamFriendsCallback(env, javaCallback);
	*/

	static native String getPersonaName(); /*
		jstring name = env->NewStringUTF(SteamFriends()->GetPersonaName());
		return name;
	*/

	static native long setPersonaName(long callback, String personaName); /*
		SteamFriendsCallback* cb = (SteamFriendsCallback*) callback;
		SteamAPICall_t handle = SteamFriends()->SetPersonaName(personaName);
		cb->onSetPersonaNameResponseCall.Set(handle, cb, &SteamFriendsCallback::onSetPersonaNameResponse);
		return handle;
	*/

	static native int getPersonaState(); /*
		return SteamFriends()->GetPersonaState();
	*/

	static native int getFriendCount(int friendFlags); /*
		return SteamFriends()->GetFriendCount(friendFlags);
	*/

	static native long getFriendByIndex(int friendIndex, int friendFlags); /*
		CSteamID id = SteamFriends()->GetFriendByIndex(friendIndex, friendFlags);
		return id.ConvertToUint64();
	*/

	static native int getFriendRelationship(long steamIDFriend); /*
		return SteamFriends()->GetFriendRelationship((uint64) steamIDFriend);
	*/

	static native int getFriendPersonaState(long steamIDFriend); /*
		return SteamFriends()->GetFriendPersonaState((uint64) steamIDFriend);
	*/

	static native String getFriendPersonaName(long steamIDFriend); /*
		jstring name = env->NewStringUTF(SteamFriends()->GetFriendPersonaName((uint64) steamIDFriend));
		return name;
	*/

	static native boolean getFriendGamePlayed(long steamIDFriend,
											  SteamFriends.FriendGameInfo friendGameInfo); /*

		FriendGameInfo_t result;
		bool success = SteamFriends()->GetFriendGamePlayed((uint64) steamIDFriend, &result);
		if (success) {
			jclass clazz = env->GetObjectClass(friendGameInfo);

			jfieldID field = env->GetFieldID(clazz, "gameID", "J");
			env->SetLongField(friendGameInfo, field, (jlong) result.m_gameID.ToUint64());

			field = env->GetFieldID(clazz, "gameIP", "I");
			env->SetIntField(friendGameInfo, field, (jint) result.m_unGameIP);

			field = env->GetFieldID(clazz, "gamePort", "S");
			env->SetShortField(friendGameInfo, field, (jshort) result.m_usGamePort);

			field = env->GetFieldID(clazz, "queryPort", "S");
			env->SetShortField(friendGameInfo, field, (jshort) result.m_usQueryPort);

			field = env->GetFieldID(clazz, "steamIDLobby", "J");
			env->SetLongField(friendGameInfo, field, (jlong) result.m_steamIDLobby.ConvertToUint64());
		}
		return success;
	*/

	static native void setInGameVoiceSpeaking(long steamID, boolean speaking); /*
		return SteamFriends()->SetInGameVoiceSpeaking((uint64) steamID, speaking);
	*/

	static native int getSmallFriendAvatar(long steamID); /*
		return SteamFriends()->GetSmallFriendAvatar((uint64) steamID);
	*/

	static native int getMediumFriendAvatar(long steamID); /*
		return SteamFriends()->GetMediumFriendAvatar((uint64) steamID);
	*/

	static native int getLargeFriendAvatar(long steamID); /*
		return SteamFriends()->GetLargeFriendAvatar((uint64) steamID);
	*/

	static native boolean requestUserInformation(long steamID, boolean requireNameOnly); /*
		return SteamFriends()->RequestUserInformation((uint64) steamID, requireNameOnly);
	*/

	static native void activateGameOverlay(String dialog); /*
		return SteamFriends()->ActivateGameOverlay(dialog);
	*/

	static native void activateGameOverlayToUser(String dialog, long steamID); /*
		return SteamFriends()->ActivateGameOverlayToUser(dialog, (uint64) steamID);
	*/

	static native void activateGameOverlayToWebPage(String url, int mode); /*
		return SteamFriends()->ActivateGameOverlayToWebPage(url, (EActivateGameOverlayToWebPageMode) mode);
	*/

	static native void activateGameOverlayToStore(int appID, int flag); /*
		return SteamFriends()->ActivateGameOverlayToStore((AppId_t) appID, (EOverlayToStoreFlag) flag);
	*/

	static native void setPlayedWith(long steamIDUserPlayedWith); /*
		SteamFriends()->SetPlayedWith((uint64) steamIDUserPlayedWith);
	*/

	static native void activateGameOverlayInviteDialog(long steamIDLobby); /*
		return SteamFriends()->ActivateGameOverlayInviteDialog((uint64) steamIDLobby);
	*/

	static native boolean setRichPresence(String key, String value); /*
		return SteamFriends()->SetRichPresence(key, value);
	*/

	static native void clearRichPresence(); /*
		SteamFriends()->ClearRichPresence();
	*/

	static native String getFriendRichPresence(long steamIDFriend, String key); /*
		return env->NewStringUTF(SteamFriends()->GetFriendRichPresence((uint64) steamIDFriend, key));
	*/

	static native int getFriendRichPresenceKeyCount(long steamIDFriend); /*
		return SteamFriends()->GetFriendRichPresenceKeyCount((uint64) steamIDFriend);
	*/

	static native String getFriendRichPresenceKeyByIndex(long steamIDFriend, int index); /*
		return env->NewStringUTF(SteamFriends()->GetFriendRichPresenceKeyByIndex((uint64) steamIDFriend, index));
	*/

	static native void requestFriendRichPresence(long steamIDFriend); /*
		SteamFriends()->RequestFriendRichPresence((uint64) steamIDFriend);
	*/

	static native boolean inviteUserToGame(long steamIDFriend, String connectString); /*
		return SteamFriends()->InviteUserToGame((uint64) steamIDFriend, connectString);
	*/

	static native int getCoplayFriendCount(); /*
		return SteamFriends()->GetCoplayFriendCount();
	*/

	static native long getCoplayFriend(int index); /*
		return SteamFriends()->GetCoplayFriend(index).ConvertToUint64();
	*/

	static native int getFriendCoplayTime(long steamIDFriend); /*
		return SteamFriends()->GetFriendCoplayTime((uint64) steamIDFriend);
	*/

	static native int getFriendCoplayGame(long steamIDFriend); /*
		return (jint) SteamFriends()->GetFriendCoplayGame((uint64) steamIDFriend);
	*/

}
