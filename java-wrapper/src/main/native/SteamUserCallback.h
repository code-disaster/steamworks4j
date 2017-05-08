#pragma once

#include "SteamCallbackAdapter.h"
#include <steam_api.h>

class SteamUserCallback : public SteamCallbackAdapter {

public:
	SteamUserCallback(JNIEnv* env, jobject callback);
	~SteamUserCallback();

	STEAM_CALLBACK(SteamUserCallback, onValidateAuthTicket, ValidateAuthTicketResponse_t, m_CallbackValidateAuthTicket);
	STEAM_CALLBACK(SteamUserCallback, onMicroTxnAuthorization, MicroTxnAuthorizationResponse_t, m_CallbackMicroTxnAuthorization);

    void onRequestEncryptedAppTicket(EncryptedAppTicketResponse_t* callback, bool error);
    CCallResult<SteamUserCallback, EncryptedAppTicketResponse_t> onRequestEncryptedAppTicketCall;
};
