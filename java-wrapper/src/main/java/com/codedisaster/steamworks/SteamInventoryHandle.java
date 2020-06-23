package com.codedisaster.steamworks;

public class SteamInventoryHandle extends SteamNativeIntHandle {

    public static final SteamInventoryHandle INVALID = new SteamInventoryHandle(-1);

    SteamInventoryHandle(int handle) {
        super(handle);
    }

    public static int[] mapToHandles(SteamInventoryHandle[] inventoryHandles) {
        int[] handlesAsInt = new int[inventoryHandles.length];

        for(int i = 0; i < inventoryHandles.length; i++) {
            handlesAsInt[i] = inventoryHandles[i].handle;
        }

        return handlesAsInt;
    }
}
