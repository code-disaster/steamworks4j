package com.codedisaster.steamworks;

public class SteamGameServerNetworking extends SteamNetworking {

	SteamGameServerNetworking(SteamNetworkingCallback callback) {
		super(SteamGameServerAPI.getSteamGameServerNetworkingPointer(), callback, false);
	}

}
