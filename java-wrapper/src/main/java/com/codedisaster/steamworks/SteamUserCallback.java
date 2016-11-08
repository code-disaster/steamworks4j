package com.codedisaster.steamworks;

public interface SteamUserCallback {

	void onValidateAuthTicket(SteamID steamID,
							  SteamAuth.AuthSessionResponse authSessionResponse,
							  SteamID ownerSteamID);

	void onMicroTxnAuthorization(int appID, long orderID, boolean authorized);

}
