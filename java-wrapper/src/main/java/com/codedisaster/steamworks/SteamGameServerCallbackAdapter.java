package com.codedisaster.steamworks;

@SuppressWarnings("unused")
class SteamGameServerCallbackAdapter extends SteamCallbackAdapter<SteamGameServerCallback> {

	SteamGameServerCallbackAdapter(SteamGameServerCallback callback) {
		super(callback);
	}

	void onValidateAuthTicketResponse(long steamID, int authSessionResponse, long ownerSteamID) {
		callback.onValidateAuthTicketResponse(new SteamID(steamID),
				SteamAuth.AuthSessionResponse.byOrdinal(authSessionResponse), new SteamID(ownerSteamID));
	}

	void onSteamServersConnected() {
		callback.onSteamServersConnected();
	}

	void onSteamServerConnectFailure(int result, boolean stillRetrying) {
		callback.onSteamServerConnectFailure(SteamResult.byValue(result), stillRetrying);
	}

	void onSteamServersDisconnected(int result) {
		callback.onSteamServersDisconnected(SteamResult.byValue(result));
	}

	void onClientApprove(long steamID, long ownerSteamID) {
		callback.onClientApprove(new SteamID(steamID), new SteamID(ownerSteamID));
	}

	void onClientDeny(long steamID, int denyReason, String optionalText) {
		callback.onClientDeny(new SteamID(steamID), SteamGameServer.DenyReason.byOrdinal(denyReason), optionalText);
	}

	void onClientKick(long steamID, int denyReason) {
		callback.onClientKick(new SteamID(steamID), SteamGameServer.DenyReason.byOrdinal(denyReason));
	}

	void onClientGroupStatus(long steamID, long steamIDGroup, boolean isMember, boolean isOfficer) {
		callback.onClientGroupStatus(new SteamID(steamID), new SteamID(steamIDGroup), isMember, isOfficer);
	}

	void onAssociateWithClanResult(int result) {
		callback.onAssociateWithClanResult(SteamResult.byValue(result));
	}

	void onComputeNewPlayerCompatibilityResult(int result,
											   int playersThatDontLikeCandidate,
											   int playersThatCandidateDoesntLike,
											   int clanPlayersThatDontLikeCandidate,
											   long steamIDCandidate) {
		callback.onComputeNewPlayerCompatibilityResult(SteamResult.byValue(result), playersThatDontLikeCandidate,
				playersThatCandidateDoesntLike, clanPlayersThatDontLikeCandidate, new SteamID(steamIDCandidate));
	}
}
