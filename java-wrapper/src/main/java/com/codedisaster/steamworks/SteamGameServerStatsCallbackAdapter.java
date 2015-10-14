package com.codedisaster.steamworks;

@SuppressWarnings("unused")
class SteamGameServerStatsCallbackAdapter extends SteamCallbackAdapter<SteamGameServerStatsCallback> {

	SteamGameServerStatsCallbackAdapter(SteamGameServerStatsCallback callback) {
		super(callback);
	}

	void onStatsReceived(int result, long steamIDUser) {
		callback.onStatsReceived(SteamResult.byValue(result), new SteamID(steamIDUser));
	}

	void onStatsStored(int result, long steamIDUser) {
		callback.onStatsStored(SteamResult.byValue(result), new SteamID(steamIDUser));
	}
	
	void onStatsUnloaded(long steamIDUser) {
		callback.onStatsUnloaded(new SteamID(steamIDUser));
	}
}
