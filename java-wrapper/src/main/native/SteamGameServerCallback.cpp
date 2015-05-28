#include "SteamGameServerCallback.h"

SteamGameServerCallback::SteamGameServerCallback(JNIEnv* env, jobject callback)
	: SteamCallbackAdapter(env, callback)
	, m_CallbackValidateAuthTicket(this, &SteamGameServerCallback::onValidateAuthTicketResponse)
	, m_CallbackSteamServersConnected(this, &SteamGameServerCallback::onSteamServersConnected)
	, m_CallbackSteamServerConnectFailure(this, &SteamGameServerCallback::onSteamServerConnectFailure)
	, m_CallbackSteamServersDisconnected(this, &SteamGameServerCallback::onSteamServersDisconnected)
	, m_CallbackClientApprove(this, &SteamGameServerCallback::onClientApprove)
	, m_CallbackClientDeny(this, &SteamGameServerCallback::onClientDeny)
	, m_CallbackClientKick(this, &SteamGameServerCallback::onClientKick)
	, m_CallbackClientGroupStatus(this, &SteamGameServerCallback::onClientGroupStatus)
	, m_CallbackAssociateWithClanResult(this, &SteamGameServerCallback::onAssociateWithClanResult)
	, m_CallbackComputeNewPlayerCompatibilityResult(this, &SteamGameServerCallback::onComputeNewPlayerCompatibilityResult) {

}

SteamGameServerCallback::~SteamGameServerCallback() {

}

void SteamGameServerCallback::onValidateAuthTicketResponse(ValidateAuthTicketResponse_t* callback) {
	invokeCallback({
		callVoidMethod(env, "onValidateAuthTicketResponse", "(JIJ)V",
			(jlong) callback->m_SteamID.ConvertToUint64(), (jint) callback->m_eAuthSessionResponse,
			(jlong) callback->m_OwnerSteamID.ConvertToUint64());
	});
}

void SteamGameServerCallback::onSteamServersConnected(SteamServersConnected_t* callback) {
	invokeCallback({
		callVoidMethod(env, "onSteamServersConnected", "()V");
	});
}

void SteamGameServerCallback::onSteamServerConnectFailure(SteamServerConnectFailure_t* callback) {
	invokeCallback({
		callVoidMethod(env, "onSteamServerConnectFailure", "(I)V",
			(jint) callback->m_eResult);
	});
}

void SteamGameServerCallback::onSteamServersDisconnected(SteamServersDisconnected_t* callback) {
	invokeCallback({
		callVoidMethod(env, "onSteamServersDisconnected", "(I)V",
			(jint) callback->m_eResult);
	});
}

void SteamGameServerCallback::onClientApprove(GSClientApprove_t* callback) {
	invokeCallback({
		callVoidMethod(env, "onClientApprove", "(JJ)V",
			(jlong) callback->m_SteamID.ConvertToUint64(), (jlong) callback->m_OwnerSteamID.ConvertToUint64());
	});
}

void SteamGameServerCallback::onClientDeny(GSClientDeny_t* callback) {
	invokeCallback({
		callVoidMethod(env, "onClientDeny", "(JI[char)V",
			(jlong) callback->m_SteamID.ConvertToUint64(), (jint) callback->m_eDenyReason, (jcharArray) callback->m_rgchOptionalText);
	});
}

void SteamGameServerCallback::onClientKick(GSClientKick_t* callback) {
	invokeCallback({
		callVoidMethod(env, "onClientKick", "(JI)V",
			(jlong) callback->m_SteamID.ConvertToUint64(), (jint) callback->m_eDenyReason);
	});
}

void SteamGameServerCallback::onClientGroupStatus(GSClientGroupStatus_t* callback) {
	invokeCallback({
		callVoidMethod(env, "onClientGroupStatus", "(JJZZ)V",
			(jlong) callback->m_SteamIDUser.ConvertToUint64(), (jlong) callback->m_SteamIDGroup.ConvertToUint64(),
			(jboolean) callback->m_bMember, (jboolean) callback->m_bOfficer);
	});
}

void SteamGameServerCallback::onAssociateWithClanResult(AssociateWithClanResult_t* callback) {
	invokeCallback({
		callVoidMethod(env, "onAssociateWithClanResult", "(I)V",
			(jint) callback->m_eResult);
	});
}

void SteamGameServerCallback::onComputeNewPlayerCompatibilityResult(ComputeNewPlayerCompatibilityResult_t* callback) {
	invokeCallback({
		callVoidMethod(env, "onComputeNewPlayerCompatibilityResult", "(IIIIJ)V",
			(jint) callback->m_eResult, (jint) callback->m_cPlayersThatDontLikeCandidate,
			(jint) callback->m_cPlayersThatCandidateDoesntLike, (jint) callback->m_cClanPlayersThatDontLikeCandidate,
			(jlong) callback->m_SteamIDCandidate.ConvertToUint64());
	});
}