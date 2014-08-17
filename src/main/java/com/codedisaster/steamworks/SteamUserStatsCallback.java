package com.codedisaster.steamworks;

@SuppressWarnings("unused")
public interface SteamUserStatsCallback {

	void onUserStatsReceived(long gameId, long userId, int result);

	void onUserStatsStored(long gameId, int result);

}
