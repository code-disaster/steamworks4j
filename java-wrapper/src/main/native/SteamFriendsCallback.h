#pragma once

#include "SteamCallbackAdapter.h"
#include <steam_api.h>

class SteamFriendsCallback : public SteamCallbackAdapter {

public:
	SteamFriendsCallback(JNIEnv* env, jobject callback);
	~SteamFriendsCallback();

	STEAM_CALLBACK(SteamFriendsCallback, onPersonaStateChange, PersonaStateChange_t, m_CallbackPersonaStateChange);
	STEAM_CALLBACK(SteamFriendsCallback, onGameLobbyJoinRequested, GameLobbyJoinRequested_t, m_CallbackGameLobbyJoinRequested);
};
