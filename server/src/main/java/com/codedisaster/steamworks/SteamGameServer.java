package com.codedisaster.steamworks;

import java.nio.ByteBuffer;

public class SteamGameServer extends SteamInterface {

    public enum DenyReason {
        Invalid,
        InvalidVersion,
        Generic,
        NotLoggedOn,
        NoLicense,
        Cheater,
        LoggedInElseWhere,
        UnknownText,
        IncompatibleAnticheat,
        MemoryCorruption,
        IncompatibleSoftware,
        SteamConnectionLost,
        SteamConnectionError,
        SteamResponseTimedOut,
        SteamValidationStalled,
        SteamOwnerLeftGuestUser;

        private static final DenyReason[] values = values();

        static DenyReason byOrdinal(int denyReason) {
            return values[denyReason];
        }
    }

    public SteamGameServer(SteamGameServerCallback callback) {
        super(SteamGameServerAPINative.getSteamGameServerPointer(),
                SteamGameServerNative.createCallback(new SteamGameServerCallbackAdapter(callback)));
    }

    public void setProduct(String product) {
        SteamGameServerNative.setProduct(pointer, product);
    }

    public void setGameDescription(String gameDescription) {
        SteamGameServerNative.setGameDescription(pointer, gameDescription);
    }

    public void setModDir(String modDir) {
        SteamGameServerNative.setModDir(pointer, modDir);
    }

    public void setDedicatedServer(boolean dedicated) {
        SteamGameServerNative.setDedicatedServer(pointer, dedicated);
    }

    public void logOn(String token) {
        SteamGameServerNative.logOn(pointer, token);
    }

    public void logOnAnonymous() {
        SteamGameServerNative.logOnAnonymous(pointer);
    }

    public void logOff() {
        SteamGameServerNative.logOff(pointer);
    }

    public boolean isLoggedOn() {
        return SteamGameServerNative.isLoggedOn(pointer);
    }

    public boolean isSecure() {
        return SteamGameServerNative.isSecure(pointer);
    }

    public SteamID getSteamID() {
        return new SteamID(SteamGameServerNative.getSteamID(pointer));
    }

    public boolean wasRestartRequested() {
        return SteamGameServerNative.wasRestartRequested(pointer);
    }

    public void setMaxPlayerCount(int playersMax) {
        SteamGameServerNative.setMaxPlayerCount(pointer, playersMax);
    }

    public void setBotPlayerCount(int botplayers) {
        SteamGameServerNative.setBotPlayerCount(pointer, botplayers);
    }

    public void setServerName(String serverName) {
        SteamGameServerNative.setServerName(pointer, serverName);
    }

    public void setMapName(String mapName) {
        SteamGameServerNative.setMapName(pointer, mapName);
    }

    public void setPasswordProtected(boolean passwordProtected) {
        SteamGameServerNative.setPasswordProtected(pointer, passwordProtected);
    }

    public void setSpectatorPort(short spectatorPort) {
        SteamGameServerNative.setSpectatorPort(pointer, spectatorPort);
    }

    public void setSpectatorServerName(String spectatorServerName) {
        SteamGameServerNative.setSpectatorServerName(pointer, spectatorServerName);
    }

    public void clearAllKeyValues() {
        SteamGameServerNative.clearAllKeyValues(pointer);
    }

    public void setKeyValue(String key, String value) {
        SteamGameServerNative.setKeyValue(pointer, key, value);
    }

    public void setGameTags(String gameTags) {
        SteamGameServerNative.setGameTags(pointer, gameTags);
    }

    public void setGameData(String gameData) {
        SteamGameServerNative.setGameData(pointer, gameData);
    }

    public void setRegion(String region) {
        SteamGameServerNative.setRegion(pointer, region);
    }

    public boolean sendUserConnectAndAuthenticate(int clientIP,
                                                  ByteBuffer authBlob,
                                                  SteamID steamIDUser) {

        long[] ids = new long[1];

        if (SteamGameServerNative.sendUserConnectAndAuthenticate(
                pointer, clientIP, authBlob, authBlob.position(), authBlob.remaining(), ids)) {
            steamIDUser.handle = ids[0];
            return true;
        }

        return false;
    }

    public SteamID createUnauthenticatedUserConnection() {
        return new SteamID(SteamGameServerNative.createUnauthenticatedUserConnection(pointer));
    }

    public void sendUserDisconnect(SteamID steamIDUser) {
        SteamGameServerNative.sendUserDisconnect(pointer, steamIDUser.handle);
    }

    public boolean updateUserData(SteamID steamIDUser, String playerName, int score) {
        return SteamGameServerNative.updateUserData(pointer, steamIDUser.handle, playerName, score);
    }

    public SteamAuthTicket getAuthSessionTicket(ByteBuffer authTicket, int[] sizeInBytes) throws SteamException {

        if (!authTicket.isDirect()) {
            throw new SteamException("Direct buffer required!");
        }

        int ticket = SteamGameServerNative.getAuthSessionTicket(pointer, authTicket,
                authTicket.position(), authTicket.remaining(), sizeInBytes);

        if (ticket != SteamAuthTicket.AuthTicketInvalid) {
            // TODO: this doesn't match the rest of the API
            authTicket.limit(authTicket.position() + sizeInBytes[0]);
        }

        return new SteamAuthTicket(ticket);
    }

    public SteamAuth.BeginAuthSessionResult beginAuthSession(ByteBuffer authTicket, SteamID steamID) throws SteamException {

        if (!authTicket.isDirect()) {
            throw new SteamException("Direct buffer required!");
        }

        int result = SteamGameServerNative.beginAuthSession(
                pointer, authTicket, authTicket.position(), authTicket.remaining(), steamID.handle);

        return SteamAuth.BeginAuthSessionResult.byOrdinal(result);
    }

    public void endAuthSession(SteamID steamID) {
        SteamGameServerNative.endAuthSession(pointer, steamID.handle);
    }

    public void cancelAuthTicket(SteamAuthTicket authTicket) {
        SteamGameServerNative.cancelAuthTicket(pointer, (int) authTicket.handle);
    }

    public SteamAuth.UserHasLicenseForAppResult userHasLicenseForApp(SteamID steamID, int appID) {
        return SteamAuth.UserHasLicenseForAppResult.byOrdinal(
                SteamGameServerNative.userHasLicenseForApp(pointer, steamID.handle, appID));
    }

    public boolean requestUserGroupStatus(SteamID steamIDUser, SteamID steamIDGroup) {
        return SteamGameServerNative.requestUserGroupStatus(pointer, steamIDUser.handle, steamIDGroup.handle);
    }

    public int getPublicIP() {
        return SteamGameServerNative.getPublicIP(pointer);
    }

    public boolean handleIncomingPacket(ByteBuffer data, int srcIP, short srcPort) {
        return SteamGameServerNative.handleIncomingPacket(
                pointer, data, data.position(), data.remaining(), srcIP, srcPort);
    }

    public int getNextOutgoingPacket(ByteBuffer out, int[] netAdr, short[] port) {
        // todo: improve return values (buffers? dedicated data type?)
        return SteamGameServerNative.getNextOutgoingPacket(
                pointer, out, out.position(), out.remaining(), netAdr, port);
    }

    public void enableHeartbeats(boolean active) {
        SteamGameServerNative.enableHeartbeats(pointer, active);
    }

    public void setHeartbeatInterval(int heartbeatInterval) {
        SteamGameServerNative.setHeartbeatInterval(pointer, heartbeatInterval);
    }

    public void forceHeartbeat() {
        SteamGameServerNative.forceHeartbeat(pointer);
    }

    public SteamAPICall associateWithClan(SteamID steamIDClan) {
        return new SteamAPICall(SteamGameServerNative.associateWithClan(pointer, steamIDClan.handle));
    }

    public SteamAPICall computeNewPlayerCompatibility(SteamID steamIDNewPlayer) {
        return new SteamAPICall(SteamGameServerNative.computeNewPlayerCompatibility(pointer, steamIDNewPlayer.handle));
    }

}
