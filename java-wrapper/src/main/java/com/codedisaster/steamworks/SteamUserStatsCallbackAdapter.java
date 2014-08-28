package com.codedisaster.steamworks;

class SteamUserStatsCallbackAdapter extends SteamCallbackAdapter<SteamUserStatsCallback> {

	SteamUserStatsCallbackAdapter(SteamUserStatsCallback callback) {
		super(callback);
	}

	public void onUserStatsReceived(long gameId, long userId, int result) {
		callback.onUserStatsReceived(gameId, userId, SteamResult.byValue(result));
	}

	public void onUserStatsStored(long gameId, int result) {
		callback.onUserStatsStored(gameId, SteamResult.byValue(result));
	}

}
