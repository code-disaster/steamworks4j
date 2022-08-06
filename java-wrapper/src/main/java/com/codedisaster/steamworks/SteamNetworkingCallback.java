package com.codedisaster.steamworks;

public interface SteamNetworkingCallback {

	default void onP2PSessionConnectFail(SteamID steamIDRemote, SteamNetworking.P2PSessionError sessionError) {
	}

	default void onP2PSessionRequest(SteamID steamIDRemote) {
	}

}
