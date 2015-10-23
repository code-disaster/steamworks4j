#include "SteamFriendsCallback.h"

SteamFriendsCallback::SteamFriendsCallback(JNIEnv* env, jobject callback)
	: SteamCallbackAdapter(env, callback)
	, m_CallbackPersonaStateChange(this, &SteamFriendsCallback::onPersonaStateChange)
	, m_CallbackGameLobbyJoinRequested(this, &SteamFriendsCallback::onGameLobbyJoinRequested) {

}

SteamFriendsCallback::~SteamFriendsCallback() {

}

void SteamFriendsCallback::onPersonaStateChange(PersonaStateChange_t* callback) {
	invokeCallback({
		callVoidMethod(env, "onPersonaStateChange", "(JI)V",
			(jlong) callback->m_ulSteamID, (jint) callback->m_nChangeFlags);
	});
}

void SteamFriendsCallback::onGameLobbyJoinRequested(GameLobbyJoinRequested_t* callback) {
	invokeCallback({
		callVoidMethod(env, "onGameLobbyJoinRequested", "(JJ)V",
			(jlong) callback->m_steamIDLobby.ConvertToUint64(),
			(jlong) callback->m_steamIDFriend.ConvertToUint64());
	});
}
