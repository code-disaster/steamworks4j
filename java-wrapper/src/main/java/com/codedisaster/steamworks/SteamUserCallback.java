package com.codedisaster.steamworks;

public interface SteamUserCallback {

	void onValidateAuthTicketResponse(SteamID steamID,
									  SteamAuth.AuthSessionResponse authSessionResponse,
									  SteamID ownerSteamID);

}
