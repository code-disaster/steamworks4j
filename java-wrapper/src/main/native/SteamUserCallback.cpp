#include "SteamUserCallback.h"

SteamUserCallback::SteamUserCallback(JNIEnv* env, jobject callback)
	: SteamCallbackAdapter(env, callback)
	, m_CallbackAuthSessionTicket(this, &SteamUserCallback::onAuthSessionTicket)
	, m_CallbackValidateAuthTicket(this, &SteamUserCallback::onValidateAuthTicket)
	, m_CallbackMicroTxnAuthorization(this, &SteamUserCallback::onMicroTxnAuthorization) {

}

SteamUserCallback::~SteamUserCallback() {

}

void SteamUserCallback::onAuthSessionTicket(GetAuthSessionTicketResponse_t* callback) {
	invokeCallback({
		callVoidMethod(env, "onAuthSessionTicket", "(JI)V",
			(jlong) callback->m_hAuthTicket, (jint) callback->m_eResult);
	});
}

void SteamUserCallback::onValidateAuthTicket(ValidateAuthTicketResponse_t* callback) {
	invokeCallback({
		callVoidMethod(env, "onValidateAuthTicket", "(JIJ)V",
			(jlong) callback->m_SteamID.ConvertToUint64(), (jint) callback->m_eAuthSessionResponse,
			(jlong) callback->m_OwnerSteamID.ConvertToUint64());
	});
}

void SteamUserCallback::onMicroTxnAuthorization(MicroTxnAuthorizationResponse_t* callback) {
	invokeCallback({
		callVoidMethod(env, "onMicroTxnAuthorization", "(IJZ)V",
			(jint) callback->m_unAppID, (jlong) callback->m_ulOrderID,
			(jboolean) callback->m_bAuthorized);
	});
}

void SteamUserCallback::onRequestEncryptedAppTicket(EncryptedAppTicketResponse_t* callback, bool error) {
    invokeCallback({
        callVoidMethod(env, "onEncryptedAppTicket", "(I)V", (jint) callback->m_eResult);
    });
}
