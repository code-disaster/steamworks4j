package com.codedisaster.steamworks;

public interface SteamUserStatsCallback {

	void onUserStatsReceived(long gameId, long userId, SteamResult result);

	void onUserStatsStored(long gameId, SteamResult result);

}
