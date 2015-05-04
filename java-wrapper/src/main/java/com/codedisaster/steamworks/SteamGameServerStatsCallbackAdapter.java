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

    public void onStatsReceived(SteamGameServer.EResult eResult, long steamIDUser) {
        callback.onStatsReceived(eResult, new SteamID(steamIDUser));
    }

    public void onStatsStored(SteamGameServer.EResult eResult, long steamIDUser) {
        callback.onStatsStored(eResult, new SteamID(steamIDUser));
    }
    
        public void onStatsUnloaded(long steamIDUser) {
        callback.onStatsUnloaded(new SteamID(steamIDUser));
    }
}
