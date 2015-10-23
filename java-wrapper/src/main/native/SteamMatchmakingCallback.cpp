#include "SteamMatchmakingCallback.h"

SteamMatchmakingCallback::SteamMatchmakingCallback(JNIEnv* env, jobject callback)
	: SteamCallbackAdapter(env, callback)
	, m_CallbackLobbyInvite(this, &SteamMatchmakingCallback::onLobbyInvite)
	, m_CallbackLobbyKicked(this, &SteamMatchmakingCallback::onLobbyKicked)
	, m_CallbackLobbyDataUpdate(this, &SteamMatchmakingCallback::onLobbyDataUpdate)
	, m_CallbackLobbyChatUpdate(this, &SteamMatchmakingCallback::onLobbyChatUpdate)
	, m_CallbackLobbyChatMsg(this, &SteamMatchmakingCallback::onLobbyChatMsg) {

}

SteamMatchmakingCallback::~SteamMatchmakingCallback() {

}

void SteamMatchmakingCallback::onLobbyMatchList(LobbyMatchList_t* callback, bool error) {
	invokeCallback({
		callVoidMethod(env, "onLobbyMatchList", "(I)V", (jint) callback->m_nLobbiesMatching);
	});
}

void SteamMatchmakingCallback::onLobbyCreated(LobbyCreated_t* callback, bool error) {
	invokeCallback({
		callVoidMethod(env, "onLobbyCreated", "(IJ)V",
			(jint) callback->m_eResult, (jlong) callback->m_ulSteamIDLobby);
	});
}

void SteamMatchmakingCallback::onLobbyEnter(LobbyEnter_t* callback, bool error) {
	invokeCallback({
		callVoidMethod(env, "onLobbyEnter", "(JIZI)V", (jlong) callback->m_ulSteamIDLobby,
			(jint) callback->m_rgfChatPermissions, callback->m_bLocked,
			(jint) callback->m_EChatRoomEnterResponse);
	});
}

void SteamMatchmakingCallback::onLobbyInvite(LobbyInvite_t* callback) {
	invokeCallback({
		callVoidMethod(env, "onLobbyInvite", "(JJJ)V", (jlong) callback->m_ulSteamIDUser,
			(jlong) callback->m_ulSteamIDLobby, (jlong) callback->m_ulGameID);
	});
}

void SteamMatchmakingCallback::onLobbyKicked(LobbyKicked_t* callback) {
	invokeCallback({
		callVoidMethod(env, "onLobbyKicked", "(JJZ)V", (jlong) callback->m_ulSteamIDLobby,
			(jlong) callback->m_ulSteamIDAdmin, callback->m_bKickedDueToDisconnect);
	});
}

void SteamMatchmakingCallback::onLobbyDataUpdate(LobbyDataUpdate_t* callback) {
	invokeCallback({
		callVoidMethod(env, "onLobbyDataUpdate", "(JJZ)V", (jlong) callback->m_ulSteamIDLobby,
			(jlong) callback->m_ulSteamIDMember, callback->m_bSuccess);
	});
}

void SteamMatchmakingCallback::onLobbyChatUpdate(LobbyChatUpdate_t* callback) {
	invokeCallback({
		callVoidMethod(env, "onLobbyChatUpdate", "(JJJI)V", (jlong) callback->m_ulSteamIDLobby,
			(jlong) callback->m_ulSteamIDUserChanged, (jlong) callback->m_ulSteamIDMakingChange,
			(jint) callback->m_rgfChatMemberStateChange);
	});
}

void SteamMatchmakingCallback::onLobbyChatMsg(LobbyChatMsg_t* callback) {
	invokeCallback({
		callVoidMethod(env, "onLobbyChatMessage", "(JJII)V", (jlong) callback->m_ulSteamIDLobby,
			(jlong) callback->m_ulSteamIDUser, (jint) callback->m_eChatEntryType,
			(jint) callback->m_iChatID);
	});
}
