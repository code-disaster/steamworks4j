package com.codedisaster.steamworks;

public interface SteamUserCallback {

	void onAuthSessionTicket(SteamAuthTicket authTicket, SteamResult result);

	void onValidateAuthTicket(SteamID steamID,
							  SteamAuth.AuthSessionResponse authSessionResponse,
							  SteamID ownerSteamID);

	void onMicroTxnAuthorization(int appID, long orderID, boolean authorized);

	void onEncryptedAppTicket(SteamResult result);

}
