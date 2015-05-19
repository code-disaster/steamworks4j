package com.codedisaster.steamworks;

/**
 *
 * @author Francisco "Franz" Bischoff
 */
public interface SteamGameServerStatsCallback {

	void onStatsReceived(SteamResult result, SteamID steamIDUser);

	void onStatsStored(SteamResult result, SteamID steamIDUser);

	void onStatsUnloaded(SteamID steamIDUser);
}
