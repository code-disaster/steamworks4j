package com.codedisaster.steamworks;

@SuppressWarnings("unused")
class SteamUserCallbackAdapter extends SteamCallbackAdapter<SteamUserCallback> {

	SteamUserCallbackAdapter(SteamUserCallback callback) {
		super(callback);
	}

	void onValidateAuthTicket(long steamID, int authSessionResponse, long ownerSteamID) {
		callback.onValidateAuthTicket(new SteamID(steamID),
				SteamAuth.AuthSessionResponse.byOrdinal(authSessionResponse), new SteamID(ownerSteamID));
	}

	void onMicroTxnAuthorization(int appID, long orderID, boolean authorized) {
		callback.onMicroTxnAuthorization(appID, orderID, authorized);
	}

}
