#include "SteamUserCallback.h"

SteamUserCallback::SteamUserCallback(JNIEnv* env, jobject callback)
	: SteamCallbackAdapter(env, callback)
	, m_CallbackValidateAuthTicket(this, &SteamUserCallback::onValidateAuthTicketResponse) {

}

SteamUserCallback::~SteamUserCallback() {

}

void SteamUserCallback::onValidateAuthTicketResponse(ValidateAuthTicketResponse_t* callback) {
	invokeCallback({
		callVoidMethod(env, "onValidateAuthTicketResponse", "(JIJ)V",
			(jlong) callback->m_SteamID.ConvertToUint64(), (jint) callback->m_eAuthSessionResponse,
			(jlong) callback->m_OwnerSteamID.ConvertToUint64());
	});
}
