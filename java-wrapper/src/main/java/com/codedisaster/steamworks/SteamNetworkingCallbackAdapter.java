package com.codedisaster.steamworks;

/**
 *
 * @author Francisco "Franz" Bischoff
 */
@SuppressWarnings("unused")
class SteamNetworkingCallbackAdapter extends SteamCallbackAdapter<SteamNetworkingCallback> {

	SteamNetworkingCallbackAdapter(SteamNetworkingCallback callback) {
		super(callback);
	}

	public void onP2PSessionConnectFail(long steamIDRemote, int sessionError) {
		SteamID id = new SteamID(steamIDRemote);
		callback.onP2PSessionConnectFail(id, SteamNetworking.P2PSessionError.byOrdinal(sessionError));
	}

	public void onP2PSessionRequest(long steamIDRemote) {
		SteamID id = new SteamID(steamIDRemote);
		callback.onP2PSessionRequest(id);
	}
}
