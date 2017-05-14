package com.codedisaster.steamworks;

public class SteamServerQuery extends SteamNativeIntHandle {

	public static final SteamServerQuery INVALID = new SteamServerQuery(0xffffffff);

	SteamServerQuery(int handle) {
		super(handle);
	}

}
