#pragma once

#include "SteamCallbackAdapter.h"
#include <steam_api.h>

class SteamMatchmakingCallback : public SteamCallbackAdapter {

public:
	SteamMatchmakingCallback(JNIEnv* env, jobject callback);
	~SteamMatchmakingCallback();

	STEAM_CALLBACK(SteamMatchmakingCallback, onLobbyInvite, LobbyInvite_t, m_CallbackLobbyInvite);
	STEAM_CALLBACK(SteamMatchmakingCallback, onLobbyKicked, LobbyKicked_t, m_CallbackLobbyKicked);
	STEAM_CALLBACK(SteamMatchmakingCallback, onLobbyDataUpdate, LobbyDataUpdate_t, m_CallbackLobbyDataUpdate);
	STEAM_CALLBACK(SteamMatchmakingCallback, onLobbyChatUpdate, LobbyChatUpdate_t, m_CallbackLobbyChatUpdate);
	STEAM_CALLBACK(SteamMatchmakingCallback, onLobbyChatMsg, LobbyChatMsg_t, m_CallbackLobbyChatMsg);

	void onLobbyMatchList(LobbyMatchList_t* callback, bool error);
	CCallResult<SteamMatchmakingCallback, LobbyMatchList_t> onLobbyMatchListCall;

	void onLobbyCreated(LobbyCreated_t* callback, bool error);
	CCallResult<SteamMatchmakingCallback, LobbyCreated_t> onLobbyCreatedCall;

	void onLobbyEnter(LobbyEnter_t* callback, bool error);
	CCallResult<SteamMatchmakingCallback, LobbyEnter_t> onLobbyEnterCall;
};
