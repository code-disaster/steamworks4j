package com.codedisaster.steamworks;

public class SteamGameServerHTTP extends SteamHTTP {

	public SteamGameServerHTTP(SteamHTTPCallback callback) {
		super(SteamGameServerAPI.getSteamGameServerHTTPPointer(), callback, false);
	}

}
