package com.codedisaster.steamworks;

public interface SteamGameServerCallback {

	void onValidateAuthTicketResponse(SteamID steamID,
									  SteamAuth.AuthSessionResponse authSessionResponse,
									  SteamID ownerSteamID);

	void onSteamServersConnected();

	void onSteamServerConnectFailure(SteamResult result, boolean stillRetrying);

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
