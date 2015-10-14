package com.codedisaster.steamworks;

public interface SteamGameServerStatsCallback {

	void onStatsReceived(SteamResult result, SteamID steamIDUser);

	void onStatsStored(SteamResult result, SteamID steamIDUser);

	void onStatsUnloaded(SteamID steamIDUser);
}
