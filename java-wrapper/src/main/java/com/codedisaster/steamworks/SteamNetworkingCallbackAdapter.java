package com.codedisaster.steamworks;

@SuppressWarnings("unused")
class SteamNetworkingCallbackAdapter extends SteamCallbackAdapter<SteamNetworkingCallback> {

	SteamNetworkingCallbackAdapter(SteamNetworkingCallback callback) {
		super(callback);
	}

	void onP2PSessionConnectFail(long steamIDRemote, int sessionError) {
		SteamID id = new SteamID(steamIDRemote);
		callback.onP2PSessionConnectFail(id, SteamNetworking.P2PSessionError.byOrdinal(sessionError));
	}

	void onP2PSessionRequest(long steamIDRemote) {
		SteamID id = new SteamID(steamIDRemote);
		callback.onP2PSessionRequest(id);
	}
}
