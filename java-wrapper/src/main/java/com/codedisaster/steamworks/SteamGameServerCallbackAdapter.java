package com.codedisaster.steamworks;

/**
 *
 * @author Francisco "Franz" Bischoff
 */
@SuppressWarnings("unused")
class SteamGameServerCallbackAdapter extends SteamCallbackAdapter<SteamGameServerCallback> {

	SteamGameServerCallbackAdapter(SteamGameServerCallback callback) {
		super(callback);
	}

	public void onValidateAuthTicketResponse(long steamID,
											 int authSessionResponse,
											 long ownerSteamID) {
		callback.onValidateAuthTicketResponse(new SteamID(steamID),
				SteamGameServer.AuthSessionResponse.byOrdinal(authSessionResponse), new SteamID(ownerSteamID));
	}

	public void onSteamServersConnected() {
		callback.onSteamServersConnected();
	}

	public void onSteamServerConnectFailure(int result) {
		callback.onSteamServerConnectFailure(SteamResult.byValue(result));
	}

	public void onSteamServersDisconnected(int result) {
		callback.onSteamServersDisconnected(SteamResult.byValue(result));
	}

	public void onClientApprove(long steamID, long ownerSteamID) {
		callback.onClientApprove(new SteamID(steamID), new SteamID(ownerSteamID));
	}

	public void onClientDeny(long steamID, int denyReason, String optionalText) {
		callback.onClientDeny(new SteamID(steamID), SteamGameServer.DenyReason.byOrdinal(denyReason), optionalText);
	}

	public void onClientKick(long steamID, int denyReason) {
		callback.onClientKick(new SteamID(steamID), SteamGameServer.DenyReason.byOrdinal(denyReason));
	}

	public void onClientGroupStatus(long steamID, long steamIDGroup, boolean isMember, boolean isOfficer) {
		callback.onClientGroupStatus(new SteamID(steamID), new SteamID(steamIDGroup), isMember, isOfficer);
	}

	public void onAssociateWithClanResult(int result) {
		callback.onAssociateWithClanResult(SteamResult.byValue(result));
	}

	public void onComputeNewPlayerCompatibilityResult(int result,
													  int playersThatDontLikeCandidate,
													  int playersThatCandidateDoesntLike,
													  int clanPlayersThatDontLikeCandidate,
													  long steamIDCandidate) {
		callback.onComputeNewPlayerCompatibilityResult(SteamResult.byValue(result), playersThatDontLikeCandidate,
				playersThatCandidateDoesntLike, clanPlayersThatDontLikeCandidate, new SteamID(steamIDCandidate));
	}
}
