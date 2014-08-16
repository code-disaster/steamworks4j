package com.codedisaster.steamworks;

public interface SteamUserStatsCallback {

	void onUserStatsReceived(long gameId, long userId, int result);

	void onUserStatsStored(long gameId, int result);

}
