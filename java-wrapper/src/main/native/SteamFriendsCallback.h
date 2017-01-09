#pragma once

#include "SteamCallbackAdapter.h"
#include <steam_api.h>

class SteamFriendsCallback : public SteamCallbackAdapter {

public:
	SteamFriendsCallback(JNIEnv* env, jobject callback);
	~SteamFriendsCallback();

	void onSetPersonaNameResponse(SetPersonaNameResponse_t* callback, bool error);
	CCallResult<SteamFriendsCallback, SetPersonaNameResponse_t> onSetPersonaNameResponseCall;

	STEAM_CALLBACK(SteamFriendsCallback, onPersonaStateChange, PersonaStateChange_t, m_CallbackPersonaStateChange);
	STEAM_CALLBACK(SteamFriendsCallback, onGameOverlayActivated, GameOverlayActivated_t, m_CallbackGameOverlayActivated);
	STEAM_CALLBACK(SteamFriendsCallback, onGameLobbyJoinRequested, GameLobbyJoinRequested_t, m_CallbackGameLobbyJoinRequested);
	STEAM_CALLBACK(SteamFriendsCallback, onAvatarImageLoaded, AvatarImageLoaded_t, m_CallbackAvatarImageLoaded);
};
