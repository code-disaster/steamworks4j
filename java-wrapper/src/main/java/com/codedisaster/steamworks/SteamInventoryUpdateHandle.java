package com.codedisaster.steamworks;

public class SteamInventoryUpdateHandle extends SteamNativeHandle {

    public static final SteamInventoryUpdateHandle INVALID = new SteamInventoryUpdateHandle(0xffffffffffffffffL);

    SteamInventoryUpdateHandle(long handle) {
        super(handle);
    }

    public boolean isValid() {
        return handle != INVALID.handle;
    }
}
