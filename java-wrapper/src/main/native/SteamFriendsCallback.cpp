#include "SteamFriendsCallback.h"

SteamFriendsCallback::SteamFriendsCallback(JNIEnv* env, jobject callback)
	: SteamCallbackAdapter(env, callback)
	, m_CallbackPersonaStateChange(this, &SteamFriendsCallback::onPersonaStateChange) {

}

SteamFriendsCallback::~SteamFriendsCallback() {

}

void SteamFriendsCallback::onPersonaStateChange(PersonaStateChange_t* callback) {
	invokeCallback({
		callVoidMethod(env, "onPersonaStateChange", "(JI)V",
			(jlong) callback->m_ulSteamID, (jint) callback->m_nChangeFlags);
	});
}
