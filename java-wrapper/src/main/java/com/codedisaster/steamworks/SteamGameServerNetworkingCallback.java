package com.codedisaster.steamworks;

/**
 *
 * @author Francisco "Franz" Bischoff
 */
public interface SteamGameServerNetworkingCallback {

    void onP2PSessionConnectFail(SteamID steamIDRemote, SteamGameServerNetworking.EP2PSessionError eP2PSessionError);

    void onP2PSessionRequest(SteamID steamIDRemote);
}
