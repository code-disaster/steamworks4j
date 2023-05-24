#pragma once

#include "SteamCallbackAdapter.h"
#include <steam_api.h>

class SteamUserCallback : public SteamCallbackAdapter {

public:
	SteamUserCallback(JNIEnv* env, jobject callback);
	~SteamUserCallback();

	STEAM_CALLBACK(SteamUserCallback, onAuthSessionTicket, GetAuthSessionTicketResponse_t, m_CallbackAuthSessionTicket);
	STEAM_CALLBACK(SteamUserCallback, onValidateAuthTicket, ValidateAuthTicketResponse_t, m_CallbackValidateAuthTicket);
	STEAM_CALLBACK(SteamUserCallback, onMicroTxnAuthorization, MicroTxnAuthorizationResponse_t, m_CallbackMicroTxnAuthorization);
	STEAM_CALLBACK(SteamUserCallback, onGetTicketForWebApi, GetTicketForWebApiResponse_t, m_CallbackGetTicketForWebApi);

    void onRequestEncryptedAppTicket(EncryptedAppTicketResponse_t* callback, bool error);
    CCallResult<SteamUserCallback, EncryptedAppTicketResponse_t> onRequestEncryptedAppTicketCall;
};
