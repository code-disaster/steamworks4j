package com.codedisaster.steamworks;

import java.nio.ByteBuffer;

public class SteamInventory extends SteamInterface {
    public enum SteamItemFlags {
        NoTrade(1 << 0),
        Removed(1 << 8),
        Consumed(1 << 9);

        private final int bits;

        SteamItemFlags(int bits) {
            this.bits = bits;
        }

        static boolean isSet(SteamInventory.SteamItemFlags value, int bitMask) {
            return (value.bits & bitMask) == value.bits;
        }
    }

    public static class SteamItemDetails {
        private SteamItemInstanceId itemId;
        private int itemDefinition;
        private short quantity;
        private short flags;

        public SteamItemInstanceId getItemId() {
            return itemId;
        }

        public int getItemDefinition() {
            return itemDefinition;
        }

        public short getQuantity() {
            return quantity;
        }

        public short getFlags() {
            return flags;
        }
    }

    public SteamInventory(SteamInventoryCallback callback) {
        super(SteamAPI.getSteamInventoryPointer(), createCallback(new SteamInventoryCallbackAdapter(callback)));
    }

    public SteamResult getResultStatus(SteamInventoryHandle inventory) {
        return SteamResult.byValue(getResultStatus(pointer, inventory.handle));
    }

    public boolean getResultItems(SteamInventoryHandle inventory, SteamItemDetails[] itemDetails, int[] itemDetailsSize) {
        if (itemDetails.length < itemDetailsSize.length) {
            throw new IllegalArgumentException("Array size must be at least the same size as the supplied itemDetails");
        }

        return getResultItems(pointer, inventory.handle, itemDetails, itemDetailsSize);
    }

    // STEAM_OUT_STRING_COUNT( punValueBufferSizeOut ) String pchValueBuffer
    public boolean getResultItemProperty(SteamInventoryHandle inventory, int itemIndex, String propertyName, String valueBuffer, int[] valueBufferSizeOut) {
        return getResultItemProperty(pointer, inventory.handle, itemIndex, propertyName, valueBuffer, valueBufferSizeOut);
    }

    public int getResultTimestamp(SteamInventoryHandle inventory) {
        return getResultTimestamp(pointer, inventory.handle);
    }

    public boolean checkResultSteamID(SteamInventoryHandle inventory, SteamID steamIDExpected) {
        return checkResultSteamID(pointer, inventory.handle, steamIDExpected.handle);
    }

    public void destroyResult(SteamInventoryHandle inventory) {
        destroyResult(pointer, inventory.handle);
    }

    public boolean getAllItems(SteamInventoryHandle[] inventories) {
        return getAllItems(pointer, SteamInventoryHandle.mapToHandles(inventories));
    }

    // STEAM_ARRAY_COUNT( unCountInstanceIDs ) SteamItemInstanceId[] pInstanceIDs
    public boolean getItemsByID(SteamInventoryHandle[] inventories, SteamItemInstanceId[] instanceIDs, int countInstanceIDs) {
        return getItemsByID(pointer, SteamInventoryHandle.mapToHandles(inventories), instanceIDs, countInstanceIDs);
    }

    // STEAM_OUT_BUFFER_COUNT(punOutBufferSize) void *pOutBuffer
    public boolean serializeResult(SteamInventoryHandle inventory, ByteBuffer outBuffer) {
        return serializeResult(pointer, inventory.handle, outBuffer, outBuffer.position(), new int[]{outBuffer.remaining()});
    }

    // STEAM_BUFFER_COUNT(punOutBufferSize) void *pBuffer
    public boolean deserializeResult(SteamInventoryHandle[] inventories, ByteBuffer buffer) {
        return deserializeResult(pointer, SteamInventoryHandle.mapToHandles(inventories), buffer, buffer.position(), buffer.remaining(), false);
    }

    // STEAM_ARRAY_COUNT(unArrayLength) int[] pArrayItemDefs, STEAM_ARRAY_COUNT(unArrayLength)
    public boolean generateItems(SteamInventoryHandle[] inventories, int[] arrayItemDefs, int[] arrayQuantity, int arrayLength) {
        return generateItems(pointer, SteamInventoryHandle.mapToHandles(inventories), arrayItemDefs, arrayQuantity, arrayLength);
    }

    public boolean grantPromoItems(SteamInventoryHandle[] inventories) {
        return grantPromoItems(pointer, SteamInventoryHandle.mapToHandles(inventories));
    }

    public boolean addPromoItem(SteamInventoryHandle[] inventories, int itemDef) {
        return addPromoItem(pointer, SteamInventoryHandle.mapToHandles(inventories), itemDef);
    }

    // STEAM_ARRAY_COUNT(unArrayLength) int[] pArrayItemDefs
    public boolean addPromoItems(SteamInventoryHandle[] inventories, int[] arrayItemDefs, int arrayLength) {
        return addPromoItems(pointer, SteamInventoryHandle.mapToHandles(inventories), arrayItemDefs, arrayLength);
    }

    public boolean consumeItem(SteamInventoryHandle[] inventories, SteamItemInstanceId itemConsume, int quantity) {
        return consumeItem(pointer, SteamInventoryHandle.mapToHandles(inventories), itemConsume, quantity);
    }

    // STEAM_ARRAY_COUNT(unArrayGenerateLength) int[] pArrayGenerate, STEAM_ARRAY_COUNT(unArrayGenerateLength) int[] punArrayGenerateQuantity
    // STEAM_ARRAY_COUNT(unArrayDestroyLength) SteamItemInstanceId[] pArrayDestroy, STEAM_ARRAY_COUNT(unArrayDestroyLength) int[] punArrayDestroyQuantity
    public boolean exchangeItems(SteamInventoryHandle[] inventories, int[] arrayGenerate, int[] arrayGenerateQuantity, int arrayGenerateLength,
                                 SteamItemInstanceId[] arrayDestroy, int[] arrayDestroyQuantity, int arrayDestroyLength) {
        return exchangeItems(pointer, SteamInventoryHandle.mapToHandles(inventories), arrayGenerate, arrayGenerateQuantity, arrayGenerateLength, arrayDestroy, arrayDestroyQuantity, arrayDestroyLength);
    }

    public boolean transferItemQuantity(SteamInventoryHandle[] inventories, SteamItemInstanceId itemIdSource, int quantity, SteamItemInstanceId itemIdDest) {
        return transferItemQuantity(pointer, SteamInventoryHandle.mapToHandles(inventories), itemIdSource, quantity, itemIdDest);
    }

    public void sendItemDropHeartbeat() {
        sendItemDropHeartbeat(pointer);
    }

    public boolean triggerItemDrop(SteamInventoryHandle[] inventories, int dropListDefinition) {
        return triggerItemDrop(pointer, SteamInventoryHandle.mapToHandles(inventories), dropListDefinition);
    }

    // STEAM_ARRAY_COUNT(nArrayGiveLength) SteamItemInstanceId[] pArrayGive, STEAM_ARRAY_COUNT(nArrayGiveLength) int[] pArrayGiveQuantity
    // STEAM_ARRAY_COUNT(nArrayGetLength) SteamItemInstanceId[] pArrayGet, STEAM_ARRAY_COUNT(nArrayGetLength) int[] pArrayGetQuantity
    public boolean tradeItems(SteamInventoryHandle[] inventories, SteamID steamIDTradePartner, SteamItemInstanceId[] arrayGive, int[] arrayGiveQuantity,
                              int arrayGiveLength, SteamItemInstanceId[] arrayGet, int[] arrayGetQuantity, int arrayGetLength) {
        return tradeItems(pointer, SteamInventoryHandle.mapToHandles(inventories), steamIDTradePartner.handle, arrayGive, arrayGiveQuantity, arrayGiveLength, arrayGet, arrayGetQuantity, arrayGetLength);
    }

    public boolean loadItemDefinitions() {
        return loadItemDefinitions(pointer);
    }

    // STEAM_OUT_ARRAY_COUNT(punItemDefIDsArraySize,List of item definition IDs) int[] pItemDefIDs,
    // STEAM_DESC(Size of array is passed in and actual size used is returned in this param) int[] punItemDefIDsArraySize
    public boolean getItemDefinitionIDs(int[] itemDefIDs, int[] itemDefIDsArraySize) {
        return getItemDefinitionIDs(pointer, itemDefIDs, itemDefIDsArraySize);
    }

    // STEAM_OUT_STRING_COUNT(punValueBufferSizeOut) String pchValueBuffer
    public boolean getItemDefinitionProperty(int itemDefinition, String propertyName, String valueBuffer, int[] valueBufferSizeOut) {
        return getItemDefinitionProperty(pointer, itemDefinition, propertyName, valueBuffer, valueBufferSizeOut);
    }

    public SteamAPICall requestEligiblePromoItemDefinitionsIDs(SteamID steamID) {
        return new SteamAPICall(requestEligiblePromoItemDefinitionsIDs(pointer, callback, steamID.handle));
    }

    // STEAM_OUT_ARRAY_COUNT(punItemDefIDsArraySize,List of item definition IDs) int[] pItemDefIDs,
    // STEAM_DESC(Size of array is passed in and actual size used is returned in this param) int[] punItemDefIDsArraySiz
    public boolean getEligiblePromoItemDefinitionIDs(SteamID steamID, int[] itemDefIDs, int[] itemDefIDsArraySize) {
        return getEligiblePromoItemDefinitionIDs(pointer, steamID.handle, itemDefIDs, itemDefIDsArraySize);
    }

    // STEAM_ARRAY_COUNT(unArrayLength) int[] pArrayItemDefs, STEAM_ARRAY_COUNT(unArrayLength) int[] punArrayQuantity
    public SteamAPICall startPurchase(int[] arrayItemDefs, int[] arrayQuantity, int arrayLength) {
        return new SteamAPICall(startPurchase(pointer, callback, arrayItemDefs, arrayQuantity, arrayLength));
    }

    public SteamAPICall requestPrices() {
        return new SteamAPICall(requestPrices(pointer, callback));
    }

    public int getNumItemsWithPrices() {
        return getNumItemsWithPrices(pointer);
    }

    // STEAM_ARRAY_COUNT(unArrayLength) STEAM_OUT_ARRAY_COUNT(pArrayItemDefs, Items with prices) int[] pArrayItemDefs,
    // STEAM_ARRAY_COUNT(unArrayLength) STEAM_OUT_ARRAY_COUNT(pPrices, List of prices for the given item defs) long[] pCurrentPrices,
    // STEAM_ARRAY_COUNT(unArrayLength) STEAM_OUT_ARRAY_COUNT(pPrices, List of prices for the given item defs) long[] pBasePrices
    public boolean getItemsWithPrices(int[] arrayItemDefs, long[] currentPrices, long[] basePrices, int arrayLength) {
        return getItemsWithPrices(pointer, arrayItemDefs, currentPrices, basePrices, arrayLength);
    }

    public boolean getItemPrice(int itemDefinition, long[] currentPrice, long[] basePrice) {
        return getItemPrice(pointer, itemDefinition, currentPrice, basePrice);
    }

    public SteamInventoryUpdateHandle startUpdateProperties() {
        return new SteamInventoryUpdateHandle(startUpdateProperties(pointer));
    }

    public boolean removeProperty(SteamInventoryUpdateHandle updateHandle, SteamItemInstanceId itemID, String propertyName) {
        return removeProperty(pointer, updateHandle.handle, itemID, propertyName);
    }

    public boolean setProperty(SteamInventoryUpdateHandle updateHandle, SteamItemInstanceId itemID, String propertyName, String value) {
        return setProperty(pointer, updateHandle.handle, itemID, propertyName, value);
    }

    public boolean setProperty(SteamInventoryUpdateHandle updateHandle, SteamItemInstanceId itemID, String propertyName, boolean value) {
        return setProperty(pointer, updateHandle.handle, itemID, propertyName, value);
    }

    public boolean setProperty(SteamInventoryUpdateHandle updateHandle, SteamItemInstanceId itemID, String propertyName, long value) {
        return setProperty(pointer, updateHandle.handle, itemID, propertyName, value);
    }

    public boolean setProperty(SteamInventoryUpdateHandle updateHandle, SteamItemInstanceId itemID, String propertyName, float value) {
        return setProperty(pointer, updateHandle.handle, itemID, propertyName, value);
    }

    public boolean submitUpdateProperties(SteamInventoryUpdateHandle updateHandle, SteamInventoryHandle[] inventories) {
        return submitUpdateProperties(pointer, updateHandle.handle, SteamInventoryHandle.mapToHandles(inventories));
    }

    public boolean inspectItem(SteamInventoryHandle[] inventories, String itemToken) {
        return inspectItem(pointer, SteamInventoryHandle.mapToHandles(inventories), itemToken);
    }

    // @off

	/*JNI
		#include "SteamInventoryCallback.h"
	*/

    private static native long createCallback(SteamInventoryCallbackAdapter javaCallback); /*
		return (intp) new SteamInventoryCallback(env, javaCallback);
	*/

    private static native int getResultStatus(long pointer, int resultHandle); /*
		ISteamInventory* inventory = (ISteamInventory*) pointer;
		return inventory->GetResultStatus((SteamInventoryResult_t) resultHandle);
	*/

    private static native boolean getResultItems(long pointer, int resultHandle, SteamItemDetails[] itemDetails, int[] itemDetailsSize); /*
		ISteamInventory* inventory = (ISteamInventory*) pointer;
		return inventory->GetResultItems((SteamInventoryResult_t) resultHandle, (SteamItemDetails_t*) itemDetails, (uint32*) itemDetailsSize);
	*/

    private static native boolean getResultItemProperty(long pointer, int resultHandle, int itemIndex, String propertyName, String valueBuffer, int[] valueBufferSizeOut); /*
		ISteamInventory* inventory = (ISteamInventory*) pointer;
		return inventory->GetResultItemProperty((SteamInventoryResult_t) resultHandle, itemIndex, propertyName, valueBuffer, (uint32*) valueBufferSizeOut);
	*/

    private static native int getResultTimestamp(long pointer, int resultHandle); /*
		ISteamInventory* inventory = (ISteamInventory*) pointer;
		return inventory->GetResultTimestamp((SteamInventoryResult_t) resultHandle);
	*/

    private static native boolean checkResultSteamID(long pointer, int resultHandle, long steamIDExpected); /*
		ISteamInventory* inventory = (ISteamInventory*) pointer;
		return inventory->CheckResultSteamID((SteamInventoryResult_t) resultHandle, (uint64) steamIDExpected);
	*/

    private static native void destroyResult(long pointer, int resultHandle); /*
		ISteamInventory* inventory = (ISteamInventory*) pointer;
		return inventory->DestroyResult((SteamInventoryResult_t) resultHandle);
	*/

    private static native boolean getAllItems(long pointer, int[] resultHandles); /*
		ISteamInventory* inventory = (ISteamInventory*) pointer;
		return inventory->GetAllItems((SteamInventoryResult_t*) resultHandles);
	*/

    private static native boolean getItemsByID(long pointer, int[] resultHandles, SteamItemInstanceId[] instanceIDs, int countInstanceIDs); /*
		ISteamInventory* inventory = (ISteamInventory*) pointer;
		return inventory->GetItemsByID((SteamInventoryResult_t*) resultHandles, (SteamItemInstanceID_t*) instanceIDs, countInstanceIDs);
	*/

    private static native boolean serializeResult(long pointer, int resultHandle, ByteBuffer outBuffer, int offset, int[] outBufferSize); /*
		ISteamInventory* inventory = (ISteamInventory*) pointer;
		return inventory->SerializeResult((SteamInventoryResult_t) resultHandle, &outBuffer[offset], (uint32*) outBufferSize);
	*/

    private static native boolean deserializeResult(long pointer, int[] resultHandles, ByteBuffer buffer, int offset, int bufferSize, boolean reserved); /*
		ISteamInventory* inventory = (ISteamInventory*) pointer;
		return inventory->DeserializeResult((SteamInventoryResult_t*) resultHandles, &buffer[offset], bufferSize, reserved);
	*/

    private static native boolean generateItems(long pointer, int[] resultHandles, int[] arrayItemDefs, int[] arrayQuantity, int arrayLength); /*
		ISteamInventory* inventory = (ISteamInventory*) pointer;
		return inventory->GenerateItems((SteamInventoryResult_t*) resultHandles, (SteamItemDef_t*) arrayItemDefs, (uint32*) arrayQuantity, arrayLength);
	*/

    private static native boolean grantPromoItems(long pointer, int[] resultHandles); /*
		ISteamInventory* inventory = (ISteamInventory*) pointer;
		return inventory->GrantPromoItems((SteamInventoryResult_t*) resultHandles);
	*/

    private static native boolean addPromoItem(long pointer, int[] resultHandles, int itemDef); /*
		ISteamInventory* inventory = (ISteamInventory*) pointer;
		return inventory->AddPromoItem((SteamInventoryResult_t*) resultHandles, itemDef);
	*/

    private static native boolean addPromoItems(long pointer, int[] resultHandles, int[] arrayItemDefs, int arrayLength); /*
		ISteamInventory* inventory = (ISteamInventory*) pointer;
		return inventory->AddPromoItems((SteamInventoryResult_t*) resultHandles, (SteamItemDef_t*) arrayItemDefs, arrayLength);
	*/

    private static native boolean consumeItem(long pointer, int[] resultHandles, SteamItemInstanceId itemConsume, int quantity); /*
		ISteamInventory* inventory = (ISteamInventory*) pointer;
		return inventory->ConsumeItem((SteamInventoryResult_t*) resultHandles, (SteamItemInstanceID_t) itemConsume, quantity);
	*/

    private static native boolean exchangeItems(long pointer, int[] resultHandles, int[] arrayGenerate, int[] arrayGenerateQuantity, int arrayGenerateLength,
                                 SteamItemInstanceId[] arrayDestroy, int[] arrayDestroyQuantity, int arrayDestroyLength); /*
		ISteamInventory* inventory = (ISteamInventory*) pointer;
		return inventory->ExchangeItems((SteamInventoryResult_t*) resultHandles, (SteamItemDef_t*) arrayGenerate, (uint32*) arrayGenerateQuantity, arrayGenerateLength, (SteamItemInstanceID_t*) arrayDestroy, (uint32*) arrayDestroyQuantity, arrayDestroyLength);
	*/

    private static native boolean transferItemQuantity(long pointer, int[] resultHandles, SteamItemInstanceId itemIdSource, int quantity, SteamItemInstanceId itemIdDest); /*
		ISteamInventory* inventory = (ISteamInventory*) pointer;
		return inventory->TransferItemQuantity((SteamInventoryResult_t*) resultHandles, (SteamItemInstanceID_t) itemIdSource, quantity, (SteamItemInstanceID_t) itemIdDest);
	*/

    private static native void sendItemDropHeartbeat(long pointer); /*
		ISteamInventory* inventory = (ISteamInventory*) pointer;
		return inventory->SendItemDropHeartbeat();
	*/

    private static native boolean triggerItemDrop(long pointer, int[] resultHandles, int dropListDefinition); /*
		ISteamInventory* inventory = (ISteamInventory*) pointer;
		return inventory->TriggerItemDrop((SteamInventoryResult_t*) resultHandles, dropListDefinition);
	*/

    private static native boolean tradeItems(long pointer, int[] resultHandles, long steamIDTradePartner, SteamItemInstanceId[] arrayGive, int[] arrayGiveQuantity,
                              int arrayGiveLength, SteamItemInstanceId[] arrayGet, int[] arrayGetQuantity, int arrayGetLength); /*
		ISteamInventory* inventory = (ISteamInventory*) pointer;
		return inventory->TradeItems((SteamInventoryResult_t*) resultHandles, (uint64) steamIDTradePartner, (SteamItemInstanceID_t*) arrayGive, (uint32*) arrayGiveQuantity, arrayGiveLength, (SteamItemInstanceID_t*) arrayGet, (uint32*) arrayGetQuantity, arrayGetLength);
	*/

    private static native boolean loadItemDefinitions(long pointer); /*
		ISteamInventory* inventory = (ISteamInventory*) pointer;
		return inventory->LoadItemDefinitions();
	*/

    private static native boolean getItemDefinitionIDs(long pointer, int[] itemDefIDs, int[] itemDefIDsArraySize); /*
		ISteamInventory* inventory = (ISteamInventory*) pointer;
		return inventory->GetItemDefinitionIDs((SteamItemDef_t*) itemDefIDs, (uint32*) itemDefIDsArraySize);
	*/

    private static native boolean getItemDefinitionProperty(long pointer, int itemDefinition, String propertyName, String valueBuffer, int[] valueBufferSizeOut); /*
		ISteamInventory* inventory = (ISteamInventory*) pointer;
		return inventory->GetItemDefinitionProperty((SteamItemDef_t) itemDefinition, propertyName, valueBuffer, (uint32*) valueBufferSizeOut);
	*/

    private static native long requestEligiblePromoItemDefinitionsIDs(long pointer, long callback, long steamID); /*
		ISteamInventory* inventory = (ISteamInventory*) pointer;
		SteamInventoryCallback* cb = (SteamInventoryCallback*) callback;
		SteamAPICall_t handle = inventory->RequestEligiblePromoItemDefinitionsIDs((uint64) steamID);
		cb->onSteamInventoryEligiblePromoItemDefIDsCall.Set(handle, cb, &SteamInventoryCallback::onSteamInventoryEligiblePromoItemDefIDs);
		return handle;
	*/

    private static native boolean getEligiblePromoItemDefinitionIDs(long pointer, long steamID, int[] itemDefIDs, int[] itemDefIDsArraySize); /*
		ISteamInventory* inventory = (ISteamInventory*) pointer;
		return inventory->GetEligiblePromoItemDefinitionIDs((uint64) steamID, (SteamItemDef_t*) itemDefIDs, (uint32*)itemDefIDsArraySize);
	*/

    private static native long startPurchase(long pointer, long callback, int[] arrayItemDefs, int[] arrayQuantity, int arrayLength); /*
		ISteamInventory* inventory = (ISteamInventory*) pointer;
		SteamInventoryCallback* cb = (SteamInventoryCallback*) callback;
		SteamAPICall_t handle = inventory->StartPurchase((SteamItemDef_t*) arrayItemDefs, (uint32*) arrayQuantity, arrayLength);
		cb->onSteamInventoryStartPurchaseResultCall.Set(handle, cb, &SteamInventoryCallback::onSteamInventoryStartPurchaseResult);
		return handle;
	*/

    private static native long requestPrices(long pointer, long callback); /*
		ISteamInventory* inventory = (ISteamInventory*) pointer;
		SteamInventoryCallback* cb = (SteamInventoryCallback*) callback;
		SteamAPICall_t handle = inventory->RequestPrices();
		cb->onSteamInventoryRequestPricesResultCall.Set(handle, cb, &SteamInventoryCallback::onSteamInventoryRequestPricesResult);
		return handle;
	*/

    private static native int getNumItemsWithPrices(long pointer); /*
		ISteamInventory* inventory = (ISteamInventory*) pointer;
		return inventory->GetNumItemsWithPrices();
	*/

    private static native boolean getItemsWithPrices(long pointer, int[] arrayItemDefs, long[] currentPrices, long[] basePrices, int arrayLength); /*
		ISteamInventory* inventory = (ISteamInventory*) pointer;
		return inventory->GetItemsWithPrices((SteamItemDef_t*) arrayItemDefs, (uint64*) currentPrices, (uint64*) basePrices, arrayLength);
	*/

    private static native boolean getItemPrice(long pointer, int itemDefinition, long[] currentPrice, long[] basePrice); /*
		ISteamInventory* inventory = (ISteamInventory*) pointer;
		return inventory->GetItemPrice((SteamItemDef_t) itemDefinition, (uint64*) currentPrice, (uint64*) basePrice);
	*/

    private static native long startUpdateProperties(long pointer); /*
		ISteamInventory* inventory = (ISteamInventory*) pointer;
		return inventory->StartUpdateProperties();
	*/

    private static native boolean removeProperty(long pointer, long updateHandle, SteamItemInstanceId itemID, String propertyName); /*
		ISteamInventory* inventory = (ISteamInventory*) pointer;
		return inventory->RemoveProperty((SteamInventoryUpdateHandle_t) updateHandle, (SteamItemInstanceID_t) itemID, propertyName);
	*/

    private static native boolean setProperty(long pointer, long updateHandle, SteamItemInstanceId itemID, String propertyName, String value); /*
		ISteamInventory* inventory = (ISteamInventory*) pointer;
		return inventory->SetProperty((SteamInventoryUpdateHandle_t) updateHandle, (SteamItemInstanceID_t) itemID, propertyName, (char*) value);
	*/

    private static native boolean setProperty(long pointer, long updateHandle, SteamItemInstanceId itemID, String propertyName, boolean value); /*
		ISteamInventory* inventory = (ISteamInventory*) pointer;
		return inventory->SetProperty((SteamInventoryUpdateHandle_t) updateHandle, (SteamItemInstanceID_t) itemID, propertyName, (bool) value);
	*/

    private static native boolean setProperty(long pointer, long updateHandle, SteamItemInstanceId itemID, String propertyName, long value); /*
		ISteamInventory* inventory = (ISteamInventory*) pointer;
		return inventory->SetProperty((SteamInventoryUpdateHandle_t) updateHandle, (SteamItemInstanceID_t) itemID, propertyName, (int64) value);
	*/

    private static native boolean setProperty(long pointer, long updateHandle, SteamItemInstanceId itemID, String propertyName, float value); /*
		ISteamInventory* inventory = (ISteamInventory*) pointer;
		return inventory->SetProperty((SteamInventoryUpdateHandle_t) updateHandle, (SteamItemInstanceID_t) itemID, propertyName, (float) value);
	*/

    private static native boolean submitUpdateProperties(long pointer, long updateHandle, int[] resultHandles); /*
		ISteamInventory* inventory = (ISteamInventory*) pointer;
		return inventory->SubmitUpdateProperties((SteamInventoryUpdateHandle_t) updateHandle, (SteamInventoryResult_t*) resultHandles);
	*/

    private static native boolean inspectItem(long pointer, int[] resultHandles, String itemToken); /*
		ISteamInventory* inventory = (ISteamInventory*) pointer;
		return inventory->InspectItem((SteamInventoryResult_t*) resultHandles, itemToken);
	*/
}
