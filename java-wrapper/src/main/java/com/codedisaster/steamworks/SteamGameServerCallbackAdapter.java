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

    public void onValidateAuthTicketResponse(long steamID, SteamGameServer.EAuthSessionResponse authSessionResponse, long ownerSteamID) {
        callback.onValidateAuthTicketResponse(new SteamID(steamID), authSessionResponse, new SteamID(ownerSteamID));
    }

    public void onSteamServersConnected() {
        callback.onSteamServersConnected();
    }

    public void onSteamServerConnectFailure(SteamGameServer.EResult eResult) {
        callback.onSteamServerConnectFailure(eResult);
    }

    public void onSteamServersDisconnected(SteamGameServer.EResult eResult) {
        callback.onSteamServersDisconnected(eResult);
    }

    public void onClientApprove(long steamID, long ownerSteamID) {
        callback.onClientApprove(new SteamID(steamID), new SteamID(ownerSteamID));
    }

    public void onClientDeny(long steamID, SteamGameServer.EDenyReason eDenyReason, String rgchOptionalText) {
        callback.onClientDeny(new SteamID(steamID), eDenyReason, rgchOptionalText);
    }

    public void onClientKick(long steamID, SteamGameServer.EDenyReason eDenyReason) {
        callback.onClientKick(new SteamID(steamID), eDenyReason);
    }

    public void onClientGroupStatus(long steamID, long steamIDGroup, boolean bMember, boolean bOfficer) {
        callback.onClientGroupStatus(new SteamID(steamID), new SteamID(steamIDGroup), bMember, bOfficer);
    }

    public void onAssociateWithClanResult(SteamGameServer.EResult eResult) {
        callback.onAssociateWithClanResult(eResult);
    }

    public void onComputeNewPlayerCompatibilityResult(SteamGameServer.EResult eResult,
            int cPlayersThatDontLikeCandidate,
            int cPlayersThatCandidateDoesntLike,
            int cClanPlayersThatDontLikeCandidate,
            long steamIDCandidate) {
        callback.onComputeNewPlayerCompatibilityResult(eResult, cPlayersThatDontLikeCandidate,
                cPlayersThatCandidateDoesntLike, cClanPlayersThatDontLikeCandidate, new SteamID(steamIDCandidate));
    }
}
