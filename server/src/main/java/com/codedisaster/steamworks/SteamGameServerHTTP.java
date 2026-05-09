package com.codedisaster.steamworks;

public class SteamGameServerHTTP extends SteamHTTP {

	public SteamGameServerHTTP(SteamHTTPCallback callback) {
		super(true, SteamGameServerHTTPNative.createCallback(new SteamHTTPCallbackAdapter(callback)));
	}

}
