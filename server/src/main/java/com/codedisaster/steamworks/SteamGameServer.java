package com.codedisaster.steamworks;

import java.nio.ByteBuffer;

@SuppressWarnings("unused")
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
		super(SteamGameServerNative.createCallback(new SteamGameServerCallbackAdapter(callback)));
	}

	public void setProduct(String product) {
		SteamGameServerNative.setProduct(product);
	}

	public void setGameDescription(String gameDescription) {
		SteamGameServerNative.setGameDescription(gameDescription);
	}

	public void setModDir(String modDir) {
		SteamGameServerNative.setModDir(modDir);
	}

	public void setDedicatedServer(boolean dedicated) {
		SteamGameServerNative.setDedicatedServer(dedicated);
	}

	public void logOn(String token) {
		SteamGameServerNative.logOn(token);
	}

	public void logOnAnonymous() {
		SteamGameServerNative.logOnAnonymous();
	}

	public void logOff() {
		SteamGameServerNative.logOff();
	}

	public boolean isLoggedOn() {
		return SteamGameServerNative.isLoggedOn();
	}

	public boolean isSecure() {
		return SteamGameServerNative.isSecure();
	}

	public SteamID getSteamID() {
		return new SteamID(SteamGameServerNative.getSteamID());
	}

	public boolean wasRestartRequested() {
		return SteamGameServerNative.wasRestartRequested();
	}

	public void setMaxPlayerCount(int playersMax) {
		SteamGameServerNative.setMaxPlayerCount(playersMax);
	}

	public void setBotPlayerCount(int botplayers) {
		SteamGameServerNative.setBotPlayerCount(botplayers);
	}

	public void setServerName(String serverName) {
		SteamGameServerNative.setServerName(serverName);
	}

	public void setMapName(String mapName) {
		SteamGameServerNative.setMapName(mapName);
	}

	public void setPasswordProtected(boolean passwordProtected) {
		SteamGameServerNative.setPasswordProtected(passwordProtected);
	}

	public void setSpectatorPort(short spectatorPort) {
		SteamGameServerNative.setSpectatorPort(spectatorPort);
	}

	public void setSpectatorServerName(String spectatorServerName) {
		SteamGameServerNative.setSpectatorServerName(spectatorServerName);
	}

	public void clearAllKeyValues() {
		SteamGameServerNative.clearAllKeyValues();
	}

	public void setKeyValue(String key, String value) {
		SteamGameServerNative.setKeyValue(key, value);
	}

	public void setGameTags(String gameTags) {
		SteamGameServerNative.setGameTags(gameTags);
	}

	public void setGameData(String gameData) {
		SteamGameServerNative.setGameData(gameData);
	}

	public void setRegion(String region) {
		SteamGameServerNative.setRegion(region);
	}

	@Deprecated
	public boolean sendUserConnectAndAuthenticate(int clientIP,
												  ByteBuffer authBlob,
												  SteamID steamIDUser) {

		long[] ids = new long[1];

		if (SteamGameServerNative.sendUserConnectAndAuthenticate(
				clientIP, authBlob, authBlob.position(), authBlob.remaining(), ids)) {
			steamIDUser.handle = ids[0];
			return true;
		}

		return false;
	}

	public SteamID createUnauthenticatedUserConnection() {
		return new SteamID(SteamGameServerNative.createUnauthenticatedUserConnection());
	}

	@Deprecated
	public void sendUserDisconnect(SteamID steamIDUser) {
		SteamGameServerNative.sendUserDisconnect(steamIDUser.handle);
	}

	public boolean updateUserData(SteamID steamIDUser, String playerName, int score) {
		return SteamGameServerNative.updateUserData(steamIDUser.handle, playerName, score);
	}

	public SteamAuthTicket getAuthSessionTicket(ByteBuffer authTicket, int[] sizeInBytes) throws SteamException {

		if (!authTicket.isDirect()) {
			throw new SteamException("Direct buffer required!");
		}

		int ticket = SteamGameServerNative.getAuthSessionTicket(authTicket,
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
				authTicket, authTicket.position(), authTicket.remaining(), steamID.handle);

		return SteamAuth.BeginAuthSessionResult.byOrdinal(result);
	}

	public void endAuthSession(SteamID steamID) {
		SteamGameServerNative.endAuthSession(steamID.handle);
	}

	public void cancelAuthTicket(SteamAuthTicket authTicket) {
		SteamGameServerNative.cancelAuthTicket((int) authTicket.handle);
	}

	public SteamAuth.UserHasLicenseForAppResult userHasLicenseForApp(SteamID steamID, int appID) {
		return SteamAuth.UserHasLicenseForAppResult.byOrdinal(
				SteamGameServerNative.userHasLicenseForApp(steamID.handle, appID));
	}

	public boolean requestUserGroupStatus(SteamID steamIDUser, SteamID steamIDGroup) {
		return SteamGameServerNative.requestUserGroupStatus(steamIDUser.handle, steamIDGroup.handle);
	}

	public int getPublicIP() {
		return SteamGameServerNative.getPublicIP();
	}

	public boolean handleIncomingPacket(ByteBuffer data, int srcIP, short srcPort) {
		return SteamGameServerNative.handleIncomingPacket(
				data, data.position(), data.remaining(), srcIP, srcPort);
	}

	public int getNextOutgoingPacket(ByteBuffer out, int[] netAdr, short[] port) {
		// todo: improve return values (buffers? dedicated data type?)
		return SteamGameServerNative.getNextOutgoingPacket(
				out, out.position(), out.remaining(), netAdr, port);
	}

	public SteamAPICall associateWithClan(SteamID steamIDClan) {
		return new SteamAPICall(SteamGameServerNative.associateWithClan(steamIDClan.handle));
	}

	public SteamAPICall computeNewPlayerCompatibility(SteamID steamIDNewPlayer) {
		return new SteamAPICall(SteamGameServerNative.computeNewPlayerCompatibility(steamIDNewPlayer.handle));
	}

}
