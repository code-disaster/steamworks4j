package com.codedisaster.steamworks;

@SuppressWarnings("unused")
public class SteamInventoryCallbackAdapter extends SteamCallbackAdapter<SteamInventoryCallback> {
    SteamInventoryCallbackAdapter(SteamInventoryCallback callback) {
        super(callback);
    }

    void onSteamInventoryResultReady(int handle, int result) {
        callback.onSteamInventoryResultReady(new SteamInventoryHandle(handle), SteamResult.byValue(result));
    }

    void onSteamInventoryFullUpdate(int handle) {
        callback.onSteamInventoryFullUpdate(new SteamInventoryHandle(handle));
    }

    void onSteamInventoryDefinitionUpdate() {
        callback.onSteamInventoryDefinitionUpdate();
    }

    void onSteamInventoryEligiblePromoItemDefIDs(int result, long steamID, int eligiblePromoItemDefs, boolean cachedData) {
        callback.onSteamInventoryEligiblePromoItemDefIDs(SteamResult.byValue(result), new SteamID(steamID), eligiblePromoItemDefs, cachedData);
    }

    void onSteamInventoryStartPurchaseResult(int result, long orderID, long transactionID) {
        callback.onSteamInventoryStartPurchaseResult(SteamResult.byValue(result), orderID, transactionID);
    }

    void onSteamInventoryRequestPricesResult(int result, String currency) {
        callback.onSteamInventoryRequestPricesResult(SteamResult.byValue(result), currency);
    }
}
