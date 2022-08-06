package com.codedisaster.steamworks;

public interface SteamUserCallback {

	default void onAuthSessionTicket(SteamAuthTicket authTicket, SteamResult result) {
	}

	default void onValidateAuthTicket(SteamID steamID,
									  SteamAuth.AuthSessionResponse authSessionResponse,
									  SteamID ownerSteamID) {
	}

	default void onMicroTxnAuthorization(int appID, long orderID, boolean authorized) {
	}

	default void onEncryptedAppTicket(SteamResult result) {
	}

}
