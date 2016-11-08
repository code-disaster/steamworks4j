#include "SteamUserCallback.h"

SteamUserCallback::SteamUserCallback(JNIEnv* env, jobject callback)
	: SteamCallbackAdapter(env, callback)
	, m_CallbackValidateAuthTicket(this, &SteamUserCallback::onValidateAuthTicket) {

}

SteamUserCallback::~SteamUserCallback() {

}

void SteamUserCallback::onValidateAuthTicket(ValidateAuthTicketResponse_t* callback) {
	invokeCallback({
		callVoidMethod(env, "onValidateAuthTicket", "(JIJ)V",
			(jlong) callback->m_SteamID.ConvertToUint64(), (jint) callback->m_eAuthSessionResponse,
			(jlong) callback->m_OwnerSteamID.ConvertToUint64());
	});
}
