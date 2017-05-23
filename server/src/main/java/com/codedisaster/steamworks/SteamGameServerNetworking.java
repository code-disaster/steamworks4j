package com.codedisaster.steamworks;

public class SteamGameServerNetworking extends SteamNetworking {

	SteamGameServerNetworking(SteamNetworkingCallback callback) {
		super(SteamGameServerAPINative.getSteamGameServerNetworkingPointer(),
				SteamGameServerNetworkingNative.createCallback(new SteamNetworkingCallbackAdapter(callback)));
	}

}
