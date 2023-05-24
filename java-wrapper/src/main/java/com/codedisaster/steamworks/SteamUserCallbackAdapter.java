package com.codedisaster.steamworks;

@SuppressWarnings("unused")
class SteamUserCallbackAdapter extends SteamCallbackAdapter<SteamUserCallback> {

	SteamUserCallbackAdapter(SteamUserCallback callback) {
		super(callback);
	}

	void onAuthSessionTicket(long authTicket, int result) {
		callback.onAuthSessionTicket(new SteamAuthTicket(authTicket), SteamResult.byValue(result));
	}

	void onValidateAuthTicket(long steamID, int authSessionResponse, long ownerSteamID) {
		callback.onValidateAuthTicket(new SteamID(steamID),
				SteamAuth.AuthSessionResponse.byOrdinal(authSessionResponse), new SteamID(ownerSteamID));
	}

	void onMicroTxnAuthorization(int appID, long orderID, boolean authorized) {
		callback.onMicroTxnAuthorization(appID, orderID, authorized);
	}

	void onEncryptedAppTicket(int result) {
		callback.onEncryptedAppTicket(SteamResult.byValue(result));
	}

	void onGetTicketForWebApi(long authTicket, int result, byte[] ticketData) {
		callback.onGetTicketForWebApi(new SteamAuthTicket(authTicket), SteamResult.byValue(result), ticketData);
	}

}
