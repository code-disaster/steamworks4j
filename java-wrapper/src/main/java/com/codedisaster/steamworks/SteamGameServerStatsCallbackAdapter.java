package com.codedisaster.steamworks;

/**
 *
 * @author Francisco "Franz" Bischoff
 */
@SuppressWarnings("unused")
class SteamGameServerStatsCallbackAdapter extends SteamCallbackAdapter<SteamGameServerStatsCallback> {

	SteamGameServerStatsCallbackAdapter(SteamGameServerStatsCallback callback) {
		super(callback);
	}

	public void onStatsReceived(int result, long steamIDUser) {
		callback.onStatsReceived(SteamResult.byValue(result), new SteamID(steamIDUser));
	}

	public void onStatsStored(int result, long steamIDUser) {
		callback.onStatsStored(SteamResult.byValue(result), new SteamID(steamIDUser));
	}
	
	public void onStatsUnloaded(long steamIDUser) {
		callback.onStatsUnloaded(new SteamID(steamIDUser));
	}
}
