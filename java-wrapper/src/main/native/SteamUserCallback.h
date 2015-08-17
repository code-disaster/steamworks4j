#pragma once

#include "SteamCallbackAdapter.h"
#include <steam_api.h>

class SteamUserCallback : public SteamCallbackAdapter {

public:
	SteamUserCallback(JNIEnv* env, jobject callback);
	~SteamUserCallback();

	STEAM_CALLBACK(SteamUserCallback, onValidateAuthTicketResponse, ValidateAuthTicketResponse_t, m_CallbackValidateAuthTicket);
};
