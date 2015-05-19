package com.codedisaster.steamworks;

/**
 *
 * @author Francisco "Franz" Bischoff
 */
public interface SteamGameServerNetworkingCallback {

	void onP2PSessionConnectFail(SteamID steamIDRemote, SteamGameServerNetworking.P2PSessionError sessionError);

	void onP2PSessionRequest(SteamID steamIDRemote);
}
