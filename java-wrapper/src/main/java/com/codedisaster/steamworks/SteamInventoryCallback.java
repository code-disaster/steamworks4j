package com.codedisaster.steamworks;

public interface SteamInventoryCallback {
    void onSteamInventoryResultReady(SteamInventoryHandle inventory, SteamResult result);

    void onSteamInventoryFullUpdate(SteamInventoryHandle inventory);

    void onSteamInventoryDefinitionUpdate();

    void onSteamInventoryEligiblePromoItemDefIDs(SteamResult result, SteamID steamID, int eligiblePromoItemDefs, boolean cachedData);

    void onSteamInventoryStartPurchaseResult(SteamResult result, long orderID, long transactionID);

    void onSteamInventoryRequestPricesResult(SteamResult result, String currency);
}
