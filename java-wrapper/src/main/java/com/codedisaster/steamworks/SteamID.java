package com.codedisaster.steamworks;

public class SteamID extends SteamNativeHandle {

	SteamID(long id) {
		super(id);
	}

	public int getAccountID() {
		return (int) (handle % (1L << 32));
	}
}
