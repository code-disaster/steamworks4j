package com.codedisaster.steamworks;

public class SteamItemInstanceId extends SteamNativeHandle {

    public static final SteamItemInstanceId INVALID = new SteamItemInstanceId(~0);

    SteamItemInstanceId(long handle) {
        super(handle);
    }

    public boolean isValid() {
        return handle != INVALID.handle;
    }
}
