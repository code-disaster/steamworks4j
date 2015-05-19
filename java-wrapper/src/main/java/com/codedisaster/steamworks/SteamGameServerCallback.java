package com.codedisaster.steamworks;

/**
 *
 * @author Francisco "Franz" Bischoff
 */
public interface SteamGameServerCallback {

	void onValidateAuthTicketResponse(SteamID steamID,
									  SteamGameServer.AuthSessionResponse authSessionResponse,
									  SteamID ownerSteamID);

	void onSteamServersConnected();

	void onSteamServerConnectFailure(SteamResult result);

	void onSteamServersDisconnected(SteamResult result);

	void onClientApprove(SteamID steamID, SteamID ownerSteamID);

	void onClientDeny(SteamID steamID, SteamGameServer.DenyReason denyReason, String optionalText);

	void onClientKick(SteamID steamID, SteamGameServer.DenyReason denyReason);

	void onClientGroupStatus(SteamID steamID, SteamID steamIDGroup, boolean isMember, boolean isOfficer);

	void onAssociateWithClanResult(SteamResult result);

	void onComputeNewPlayerCompatibilityResult(SteamResult result,
											   int playersThatDontLikeCandidate,
											   int playersThatCandidateDoesntLike,
											   int clanPlayersThatDontLikeCandidate,
											   SteamID steamIDCandidate);
}
