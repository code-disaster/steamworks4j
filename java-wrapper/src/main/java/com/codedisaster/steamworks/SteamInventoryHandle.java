package com.codedisaster.steamworks;

import java.util.*;
import java.util.stream.Collectors;

public class SteamInventoryHandle extends SteamNativeIntHandle {
    public static final int INVALID_VALUE = -1;

    public static final SteamInventoryHandle INVALID = new SteamInventoryHandle(INVALID_VALUE);

    SteamInventoryHandle(int handle) {
        super(handle);
    }

    public static List<SteamInventoryHandle> mapToHandles(int[] handlesAsInt) {
        return Arrays.stream(handlesAsInt).filter(value -> value != INVALID_VALUE).boxed().map(SteamInventoryHandle::new).collect(Collectors.toList());
    }
}
