#include "SteamFriendsCallback.h"

SteamFriendsCallback::SteamFriendsCallback(JNIEnv* env, jobject callback)
	: SteamCallbackAdapter(env, callback)
	, m_CallbackPersonaStateChange(this, &SteamFriendsCallback::onPersonaStateChange)
	, m_CallbackGameOverlayActivated(this, &SteamFriendsCallback::onGameOverlayActivated)
	, m_CallbackGameLobbyJoinRequested(this, &SteamFriendsCallback::onGameLobbyJoinRequested)
	, m_CallbackAvatarImageLoaded(this, &SteamFriendsCallback::onAvatarImageLoaded) {

}

SteamFriendsCallback::~SteamFriendsCallback() {

}

void SteamFriendsCallback::onSetPersonaNameResponse(SetPersonaNameResponse_t* callback, bool error) {
	invokeCallback({
		callVoidMethod(env, "onSetPersonaNameResponse", "(ZZI)V",
		    callback->m_bSuccess, callback->m_bLocalSuccess, (jint) callback->m_result);
	});
}

void SteamFriendsCallback::onPersonaStateChange(PersonaStateChange_t* callback) {
	invokeCallback({
		callVoidMethod(env, "onPersonaStateChange", "(JI)V",
			(jlong) callback->m_ulSteamID, (jint) callback->m_nChangeFlags);
	});
}

void SteamFriendsCallback::onGameOverlayActivated(GameOverlayActivated_t* callback) {
	invokeCallback({
		callVoidMethod(env, "onGameOverlayActivated", "(Z)V", callback->m_bActive);
	});
}

void SteamFriendsCallback::onGameLobbyJoinRequested(GameLobbyJoinRequested_t* callback) {
	invokeCallback({
		callVoidMethod(env, "onGameLobbyJoinRequested", "(JJ)V",
			(jlong) callback->m_steamIDLobby.ConvertToUint64(),
			(jlong) callback->m_steamIDFriend.ConvertToUint64());
	});
}

void SteamFriendsCallback::onAvatarImageLoaded(AvatarImageLoaded_t* callback) {
	invokeCallback({
		callVoidMethod(env, "onAvatarImageLoaded", "(JIII)V",
			(jlong) callback->m_steamID.ConvertToUint64(), callback->m_iImage,
			callback->m_iWide, callback->m_iTall);
	});
}
