package com.codedisaster.steamworks;

@SuppressWarnings("unused")
class SteamUserCallbackAdapter extends SteamCallbackAdapter<SteamUserCallback> {

	SteamUserCallbackAdapter(SteamUserCallback callback) {
		super(callback);
	}

	void onValidateAuthTicketResponse(long steamID, int authSessionResponse, long ownerSteamID) {
		callback.onValidateAuthTicketResponse(new SteamID(steamID),
				SteamAuth.AuthSessionResponse.byOrdinal(authSessionResponse), new SteamID(ownerSteamID));
	}

}
