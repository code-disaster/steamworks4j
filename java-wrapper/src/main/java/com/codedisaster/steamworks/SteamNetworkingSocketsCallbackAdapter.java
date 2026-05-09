package com.codedisaster.steamworks;

import com.codedisaster.steamworks.SteamNetworkingSockets.Connection;
import com.codedisaster.steamworks.SteamNetworkingSockets.ConnectionState;

class SteamNetworkingSocketsCallbackAdapter extends SteamCallbackAdapter<SteamNetworkingSocketsCallback> {

    SteamNetworkingSocketsCallbackAdapter(SteamNetworkingSocketsCallback callback) {
        super(callback);
    }

    void onConnectionStatusChanged(int connectionHandle, long steamID, int state, int prevState) {
        callback.onConnectionStatusChanged(new Connection(connectionHandle), new SteamID(steamID), ConnectionState.byValue(state), ConnectionState.byValue(prevState));
    }
}
