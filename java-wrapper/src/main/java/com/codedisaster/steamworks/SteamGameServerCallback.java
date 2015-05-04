package com.codedisaster.steamworks;

/**
 *
 * @author Francisco "Franz" Bischoff
 */
public interface SteamGameServerCallback {

    void onValidateAuthTicketResponse(SteamID steamID, SteamGameServer.EAuthSessionResponse authSessionResponse, SteamID OwnerSteamID);

    void onSteamServersConnected();

    void onSteamServerConnectFailure(SteamGameServer.EResult eResult);

    void onSteamServersDisconnected(SteamGameServer.EResult eResult);

    void onClientApprove(SteamID steamID, SteamID ownerSteamID);

    void onClientDeny(SteamID steamID, SteamGameServer.EDenyReason eDenyReason, String rgchOptionalText);

    void onClientKick(SteamID steamID, SteamGameServer.EDenyReason eDenyReason);

    void onClientGroupStatus(SteamID steamID, SteamID steamIDGroup, boolean bMember, boolean bOfficer);

    void onAssociateWithClanResult(SteamGameServer.EResult eResult);

    void onComputeNewPlayerCompatibilityResult(SteamGameServer.EResult eResult,
            int cPlayersThatDontLikeCandidate,
            int cPlayersThatCandidateDoesntLike,
            int cClanPlayersThatDontLikeCandidate,
            SteamID steamIDCandidate);
}
