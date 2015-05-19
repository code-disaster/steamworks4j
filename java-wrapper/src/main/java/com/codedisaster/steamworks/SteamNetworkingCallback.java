package com.codedisaster.steamworks;

/**
 *
 * @author Francisco "Franz" Bischoff
 */
public interface SteamNetworkingCallback {

	void onP2PSessionConnectFail(SteamID steamIDRemote, SteamNetworking.P2PSessionError sessionError);

	void onP2PSessionRequest(SteamID steamIDRemote);
}
