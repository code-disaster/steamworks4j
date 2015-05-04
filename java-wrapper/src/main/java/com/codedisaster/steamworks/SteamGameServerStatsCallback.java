package com.codedisaster.steamworks;

/**
 *
 * @author Francisco "Franz" Bischoff
 */
public interface SteamGameServerStatsCallback {

    void onStatsReceived(SteamGameServer.EResult eResult, SteamID steamIDUser);

    void onStatsStored(SteamGameServer.EResult eResult, SteamID steamIDUser);

    void onStatsUnloaded(SteamID steamIDUser);
}
