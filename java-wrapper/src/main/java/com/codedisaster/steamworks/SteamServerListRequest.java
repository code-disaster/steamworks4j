package com.codedisaster.steamworks;

public class SteamServerListRequest extends SteamNativeHandle {

	SteamServerListRequest(long handle) {
		super(handle);
	}

	public boolean isValid() {
		return handle != 0L;
	}

}
