#pragma once

#include "SteamCallbackAdapter.h"
#include <steam_api.h>

class SteamInventoryCallback : public SteamCallbackAdapter {

public:
	SteamInventoryCallback(JNIEnv* env, jobject callback);
	~SteamInventoryCallback();

	STEAM_CALLBACK(SteamInventoryCallback, onSteamInventoryResultReady, SteamInventoryResultReady_t, m_CallbackSteamInventoryResultReady);
	STEAM_CALLBACK(SteamInventoryCallback, onSteamInventoryFullUpdate, SteamInventoryFullUpdate_t, m_CallbackSteamInventoryFullUpdate);
	STEAM_CALLBACK(SteamInventoryCallback, onSteamInventoryDefinitionUpdate, SteamInventoryDefinitionUpdate_t, m_CallbackSteamInventoryDefinitionUpdate);

	void onSteamInventoryEligiblePromoItemDefIDs(SteamInventoryEligiblePromoItemDefIDs_t* callback, bool error);
	CCallResult<SteamInventoryCallback, SteamInventoryEligiblePromoItemDefIDs_t> onSteamInventoryEligiblePromoItemDefIDsCall;

	void onSteamInventoryStartPurchaseResult(SteamInventoryStartPurchaseResult_t* callback, bool error);
	CCallResult<SteamInventoryCallback, SteamInventoryStartPurchaseResult_t> onSteamInventoryStartPurchaseResultCall;

	void onSteamInventoryRequestPricesResult(SteamInventoryRequestPricesResult_t* callback, bool error);
	CCallResult<SteamInventoryCallback, SteamInventoryRequestPricesResult_t> onSteamInventoryRequestPricesResultCall;
};