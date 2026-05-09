package com.codedisaster.steamworks;

import com.codedisaster.steamworks.SteamNetworkingSockets.Connection;
import com.codedisaster.steamworks.SteamNetworkingSockets.ConnectionState;

public interface SteamNetworkingSocketsCallback {

    void onConnectionStatusChanged(Connection connection, SteamID steamID, ConnectionState state, ConnectionState prevState);

}
