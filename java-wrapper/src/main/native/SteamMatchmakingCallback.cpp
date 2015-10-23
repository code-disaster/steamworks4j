#include "SteamMatchmakingCallback.h"

SteamMatchmakingCallback::SteamMatchmakingCallback(JNIEnv* env, jobject callback)
	: SteamCallbackAdapter(env, callback) {

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

void SteamMatchmakingCallback::onLobbyInvite(LobbyInvite_t* callback, bool error) {
	invokeCallback({
		callVoidMethod(env, "onLobbyInvite", "(JJJ)V", (jlong) callback->m_ulSteamIDUser,
			(jlong) callback->m_ulSteamIDLobby, (jlong) callback->m_ulGameID);
	});
}

void SteamMatchmakingCallback::onLobbyChatUpdate(LobbyChatUpdate_t* callback, bool error) {
	invokeCallback({
		callVoidMethod(env, "onLobbyChatUpdate", "(JJJI)V", (jlong) callback->m_ulSteamIDLobby,
			(jlong) callback->m_ulSteamIDUserChanged, (jlong) callback->m_ulSteamIDMakingChange,
			(jint) callback->m_rgfChatMemberStateChange);
	});
}
