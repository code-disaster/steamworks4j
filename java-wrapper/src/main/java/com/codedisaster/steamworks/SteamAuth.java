package com.codedisaster.steamworks;

/**
 * Enums shared by SteamUser and SteamGameServer
 */
public class SteamAuth {

	public enum BeginAuthSessionResult {
		OK,
		InvalidTicket,
		DuplicateRequest,
		InvalidVersion,
		GameMismatch,
		ExpiredTicket;

		private static final BeginAuthSessionResult[] values = values();

		static BeginAuthSessionResult byOrdinal(int authSessionResponse) {
			return values[authSessionResponse];
		}
	}

	public enum AuthSessionResponse {
		OK,
		UserNotConnectedToSteam,
		NoLicenseOrExpired,
		VACBanned,
		LoggedInElseWhere,
		VACCheckTimedOut,
		AuthTicketCanceled,
		AuthTicketInvalidAlreadyUsed,
		AuthTicketInvalid,
		PublisherIssuedBan;

		private static final AuthSessionResponse[] values = values();

		static AuthSessionResponse byOrdinal(int authSessionResponse) {
			return values[authSessionResponse];
		}
	}

	public enum UserHasLicenseForAppResult {
		HasLicense,
		DoesNotHaveLicense,
		NoAuth;

		private static final UserHasLicenseForAppResult[] values = values();

		static UserHasLicenseForAppResult byOrdinal(int result) {
			return values[result];
		}
	}

}
