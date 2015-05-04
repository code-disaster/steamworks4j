package com.codedisaster.steamworks;

/**
 *
 * @author Francisco "Franz" Bischoff
 */
@SuppressWarnings("unused")
class SteamGameServerNetworkingCallbackAdapter extends SteamCallbackAdapter<SteamGameServerNetworkingCallback> {

    SteamGameServerNetworkingCallbackAdapter(SteamGameServerNetworkingCallback callback) {
        super(callback);
    }

    public void onP2PSessionConnectFail(long steamIDRemote, SteamGameServerNetworking.EP2PSessionError eP2PSessionError) {
        SteamID id = new SteamID(steamIDRemote);
        callback.onP2PSessionConnectFail(id, eP2PSessionError);
    }

    public void onP2PSessionRequest(long steamIDRemote) {
        SteamID id = new SteamID(steamIDRemote);
        callback.onP2PSessionRequest(id);
    }
}
