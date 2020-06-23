#include "SteamInventoryCallback.h"

SteamInventoryCallback::SteamInventoryCallback(JNIEnv* env, jobject callback)
	: SteamCallbackAdapter(env, callback)
	, m_CallbackSteamInventoryResultReady(this, &SteamInventoryCallback::onSteamInventoryResultReady)
	, m_CallbackSteamInventoryFullUpdate(this, &SteamInventoryCallback::onSteamInventoryFullUpdate)
	, m_CallbackSteamInventoryDefinitionUpdate(this, &SteamInventoryCallback::onSteamInventoryDefinitionUpdate) {

}

SteamInventoryCallback::~SteamInventoryCallback() {

}

void SteamInventoryCallback::onSteamInventoryResultReady(SteamInventoryResultReady_t* callback) {
	invokeCallback({
		callVoidMethod(env, "onSteamInventoryResultReady", "(II)V",
			(jint) callback->m_handle, (jint) callback->m_result);
	});
}

void SteamInventoryCallback::onSteamInventoryFullUpdate(SteamInventoryFullUpdate_t* callback) {
	invokeCallback({
		callVoidMethod(env, "onSteamInventoryFullUpdate", "(I)V",
			(jint) callback->m_handle);
	});
}

void SteamInventoryCallback::onSteamInventoryDefinitionUpdate(SteamInventoryDefinitionUpdate_t* callback) {
	invokeCallback({
		callVoidMethod(env, "onSteamInventoryDefinitionUpdate", "()V");
	});
}

void SteamInventoryCallback::onSteamInventoryEligiblePromoItemDefIDs(SteamInventoryEligiblePromoItemDefIDs_t* callback, bool error) {
	invokeCallback({
		callVoidMethod(env, "onSteamInventoryEligiblePromoItemDefIDs", "(IJIZ)V",
		    (jint) callback->m_result, (jlong) callback->m_steamID.ConvertToUint64(), callback->m_numEligiblePromoItemDefs, callback->m_bCachedData);
	});
}

void SteamInventoryCallback::onSteamInventoryStartPurchaseResult(SteamInventoryStartPurchaseResult_t* callback, bool error) {
	invokeCallback({
		callVoidMethod(env, "onSteamInventoryStartPurchaseResult", "(IJJ)V",
			(jint) callback->m_result, (jlong) callback->m_ulOrderID, (jlong) callback->m_ulTransID);
	});
}

void SteamInventoryCallback::onSteamInventoryRequestPricesResult(SteamInventoryRequestPricesResult_t* callback, bool error) {
	invokeCallback({
		callVoidMethod(env, "onSteamInventoryRequestPricesResult", "(ILjava/lang/String;)V",
		    (jint) callback->m_result, env->NewStringUTF(callback->m_rgchCurrency));
	});
}
