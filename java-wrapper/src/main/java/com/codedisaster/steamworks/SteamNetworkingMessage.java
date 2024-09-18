package com.codedisaster.steamworks;

public class SteamNetworkingMessage {

    public byte[] payload;

    public int connectionHandle;

    public SteamNetworkingMessage() {

    }

    public byte[] getPayload() {
        return payload;
    }

    public long getConnectionHandle() {
        return connectionHandle;
    }
}
