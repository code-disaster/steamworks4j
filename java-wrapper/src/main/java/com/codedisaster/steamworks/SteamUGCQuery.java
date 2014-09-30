package com.codedisaster.steamworks;

public class SteamUGCQuery {
	final long handle;

	public SteamUGCQuery(long handle) {
		this.handle = handle;
	}

	public boolean isValid() {
		return handle != -1;
	}
}
