package com.codedisaster.steamworks;

import java.nio.Buffer;

/**
 *
 * @author Francisco "Franz" Bischoff
 */
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
		super(SteamGameServerAPI.getSteamGameServerPointer(),
				createCallback(new SteamGameServerCallbackAdapter(callback)));
	}

	public void setProduct(String product) {
		setProduct(pointer, product);
	}

	public void setGameDescription(String gameDescription) {
		setGameDescription(pointer, gameDescription);
	}

	public void setModDir(String modDir) {
		setModDir(pointer, modDir);
	}

	public void setDedicatedServer(boolean dedicated) {
		setDedicatedServer(pointer, dedicated);
	}

	public void logOn(String token) {
		logOn(pointer, token);
	}

	public void logOnAnonymous() {
		logOnAnonymous(pointer);
	}

	public void logOff() {
		logOff(pointer);
	}

	public boolean isLoggedOn() {
		return isLoggedOn(pointer);
	}

	public boolean isSecure() {
		return isSecure(pointer);
	}

	public SteamID getSteamID() {
		return new SteamID(getSteamID(pointer));
	}

	public boolean wasRestartRequested() {
		return wasRestartRequested(pointer);
	}

	public void setMaxPlayerCount(int playersMax) {
		setMaxPlayerCount(pointer, playersMax);
	}

	public void setBotPlayerCount(int botplayers) {
		setBotPlayerCount(pointer, botplayers);
	}

	public void setServerName(String serverName) {
		setServerName(pointer, serverName);
	}

	public void setMapName(String mapName) {
		setMapName(pointer, mapName);
	}

	public void setPasswordProtected(boolean passwordProtected) {
		setPasswordProtected(pointer, passwordProtected);
	}

	public void setSpectatorPort(short spectatorPort) {
		setSpectatorPort(pointer, spectatorPort);
	}

	public void setSpectatorServerName(String spectatorServerName) {
		setSpectatorServerName(pointer, spectatorServerName);
	}

	public void clearAllKeyValues() {
		clearAllKeyValues(pointer);
	}

	public void setKeyValue(String key, String value) {
		setKeyValue(pointer, key, value);
	}

	public void setGameTags(String gameTags) {
		setGameTags(pointer, gameTags);
	}

	public void setGameData(String gameData) {
		setGameData(pointer, gameData);
	}

	public void setRegion(String region) {
		setRegion(pointer, region);
	}

	public SteamID sendUserConnectAndAuthenticate(int clientIP,
												  Buffer authBlob,
												  int authBlobSize,
												  SteamID steamIDUser) {

		long[] ids = new long[1];
		if (sendUserConnectAndAuthenticate(pointer, clientIP, authBlob, authBlobSize, ids)) {
			return new SteamID(ids[0]);
		}
		return null;
	}

	public SteamID createUnauthenticatedUserConnection() {
		return new SteamID(createUnauthenticatedUserConnection(pointer));
	}

	public void sendUserDisconnect(SteamID steamIDUser) {
		sendUserDisconnect(pointer, steamIDUser.handle);
	}

	public boolean updateUserData(SteamID steamIDUser, String playerName, int score) {
		return updateUserData(pointer, steamIDUser.handle, playerName, score);
	}

	public SteamAuthTicket getAuthSessionTicket(Buffer authTicket, int[] sizeInBytes) throws SteamException {

		if (!authTicket.isDirect()) {
			throw new SteamException("Direct buffer required!");
		}

		int ticket = getAuthSessionTicket(pointer, authTicket, authTicket.capacity(), sizeInBytes);

		if (ticket != SteamAuthTicket.AuthTicketInvalid) {
			authTicket.limit(sizeInBytes[0]);
		}

		return new SteamAuthTicket(ticket);
	}

	public SteamAuth.BeginAuthSessionResult beginAuthSession(Buffer authTicket, SteamID steamID) throws SteamException {

		if (!authTicket.isDirect()) {
			throw new SteamException("Direct buffer required!");
		}

		int result = beginAuthSession(pointer, authTicket, authTicket.limit(), steamID.handle);
		return SteamAuth.BeginAuthSessionResult.byOrdinal(result);
	}

	public void endAuthSession(SteamID steamID) {
		endAuthSession(pointer, steamID.handle);
	}

	public void cancelAuthTicket(SteamAuthTicket authTicket) {
		cancelAuthTicket(pointer, (int) authTicket.handle);
	}

	public SteamAuth.UserHasLicenseForAppResult userHasLicenseForApp(SteamID steamID, int appID) {
		return SteamAuth.UserHasLicenseForAppResult.byOrdinal(userHasLicenseForApp(pointer, steamID.handle, appID));
	}

	public boolean requestUserGroupStatus(SteamID steamIDUser, SteamID steamIDGroup) {
		return requestUserGroupStatus(pointer, steamIDUser.handle, steamIDGroup.handle);
	}

	public int getPublicIP() {
		return getPublicIP(pointer);
	}

	public boolean handleIncomingPacket(Buffer data, int srcIP, short srcPort) {
		return handleIncomingPacket(pointer, data, data.limit(), srcIP, srcPort);
	}

	public int getNextOutgoingPacket(Buffer out, int[] netAdr, short[] port) {
		// todo: improve return values (buffers? dedicated data type?)
		return getNextOutgoingPacket(pointer, out, out.capacity(), netAdr, port);
	}

	public void enableHeartbeats(boolean active) {
		enableHeartbeats(pointer, active);
	}

	public void setHeartbeatInterval(int heartbeatInterval) {
		setHeartbeatInterval(pointer, heartbeatInterval);
	}

	public void forceHeartbeat() {
		forceHeartbeat(pointer);
	}

	public SteamAPICall associateWithClan(SteamID steamIDClan) {
		return new SteamAPICall(associateWithClan(pointer, steamIDClan.handle));
	}

	public SteamAPICall computeNewPlayerCompatibility(SteamID steamIDNewPlayer) {
		return new SteamAPICall(computeNewPlayerCompatibility(pointer, steamIDNewPlayer.handle));
	}

	// @off

	/*JNI
		#include "SteamGameServerCallback.h"
	*/

	private static native long createCallback(SteamGameServerCallbackAdapter javaCallback); /*
		return (intp) new SteamGameServerCallback(env, javaCallback);
	*/

	private static native void setProduct(long pointer, String product); /*
		ISteamGameServer* server = (ISteamGameServer*) pointer;
		server->SetProduct(product);
	*/

	private static native void setGameDescription(long pointer, String gameDescription); /*
		ISteamGameServer* server = (ISteamGameServer*) pointer;
		server->SetGameDescription(gameDescription);
	*/

	private static native void setModDir(long pointer, String modDir); /*
		ISteamGameServer* server = (ISteamGameServer*) pointer;
		server->SetModDir(modDir);
	*/

	private static native void setDedicatedServer(long pointer, boolean dedicated); /*
		ISteamGameServer* server = (ISteamGameServer*) pointer;
		server->SetDedicatedServer(dedicated);
	*/

	private static native void logOn(long pointer, String token); /*
		ISteamGameServer* server = (ISteamGameServer*) pointer;
		server->LogOn(token);
	*/

	private static native void logOnAnonymous(long pointer); /*
		ISteamGameServer* server = (ISteamGameServer*) pointer;
		server->LogOnAnonymous();
	*/

	private static native void logOff(long pointer); /*
		ISteamGameServer* server = (ISteamGameServer*) pointer;
		server->LogOff();
	*/

	private static native boolean isLoggedOn(long pointer); /*
		ISteamGameServer* server = (ISteamGameServer*) pointer;
		return server->BLoggedOn();
	*/

	private static native boolean isSecure(long pointer); /*
		ISteamGameServer* server = (ISteamGameServer*) pointer;
		return server->BSecure();
	*/

	private static native long getSteamID(long pointer); /*
		ISteamGameServer* server = (ISteamGameServer*) pointer;
		return server->GetSteamID().ConvertToUint64();
	*/

	private static native boolean wasRestartRequested(long pointer); /*
		ISteamGameServer* server = (ISteamGameServer*) pointer;
		return server->WasRestartRequested();
	*/

	private static native void setMaxPlayerCount(long pointer, int playersMax); /*
		ISteamGameServer* server = (ISteamGameServer*) pointer;
		server->SetMaxPlayerCount(playersMax);
	*/

	private static native void setBotPlayerCount(long pointer, int botplayers); /*
		ISteamGameServer* server = (ISteamGameServer*) pointer;
		server->SetBotPlayerCount(botplayers);
	*/

	private static native void setServerName(long pointer, String serverName); /*
		ISteamGameServer* server = (ISteamGameServer*) pointer;
		server->SetServerName(serverName);
	*/

	private static native void setMapName(long pointer, String mapName); /*
		ISteamGameServer* server = (ISteamGameServer*) pointer;
		server->SetMapName(mapName);
	*/

	private static native void setPasswordProtected(long pointer, boolean passwordProtected); /*
		ISteamGameServer* server = (ISteamGameServer*) pointer;
		server->SetPasswordProtected(passwordProtected);
	*/

	private static native void setSpectatorPort(long pointer, short spectatorPort); /*
		ISteamGameServer* server = (ISteamGameServer*) pointer;
		server->SetSpectatorPort(spectatorPort);
	*/

	private static native void setSpectatorServerName(long pointer, String spectatorServerName); /*
		ISteamGameServer* server = (ISteamGameServer*) pointer;
		server->SetSpectatorServerName(spectatorServerName);
	*/

	private static native void clearAllKeyValues(long pointer); /*
		ISteamGameServer* server = (ISteamGameServer*) pointer;
		server->ClearAllKeyValues();
	*/

	private static native void setKeyValue(long pointer, String key, String value); /*
		ISteamGameServer* server = (ISteamGameServer*) pointer;
		server->SetKeyValue(key, value);
	*/

	private static native void setGameTags(long pointer, String gameTags); /*
		ISteamGameServer* server = (ISteamGameServer*) pointer;
		server->SetGameTags(gameTags);
	*/

	private static native void setGameData(long pointer, String gameData); /*
		ISteamGameServer* server = (ISteamGameServer*) pointer;
		server->SetGameData(gameData);
	*/

	private static native void setRegion(long pointer, String region); /*
		ISteamGameServer* server = (ISteamGameServer*) pointer;
		server->SetRegion(region);
	*/

	private static native boolean sendUserConnectAndAuthenticate(long pointer, int clientIP,
																 Buffer authBlob, int authBlobSize, long[] steamIDUser); /*
		ISteamGameServer* server = (ISteamGameServer*) pointer;
		CSteamID user;
		if (server->SendUserConnectAndAuthenticate(clientIP, authBlob, authBlobSize, &user)) {
			steamIDUser[0] = user.ConvertToUint64();
			return true;
		}
		return false;
	*/

	private static native long createUnauthenticatedUserConnection(long pointer); /*
		ISteamGameServer* server = (ISteamGameServer*) pointer;
		return server->CreateUnauthenticatedUserConnection().ConvertToUint64();
	*/

	private static native void sendUserDisconnect(long pointer, long steamIDUser); /*
		ISteamGameServer* server = (ISteamGameServer*) pointer;
		server->SendUserDisconnect((uint64) steamIDUser);
	*/

	private static native boolean updateUserData(long pointer, long steamIDUser, String playerName, int score); /*
		ISteamGameServer* server = (ISteamGameServer*) pointer;
		return server->BUpdateUserData((uint64) steamIDUser, playerName, score);
	*/

	private static native int getAuthSessionTicket(long pointer, Buffer authTicket, int capacityInBytes, int[] sizeInBytes); /*
		ISteamGameServer* server = (ISteamGameServer*) pointer;
		int ticket = server->GetAuthSessionTicket(authTicket, capacityInBytes, (uint32*) sizeInBytes);
		return ticket;
	*/

	private static native int beginAuthSession(long pointer, Buffer authTicket, int authTicketSizeInBytes, long steamID); /*
		ISteamGameServer* server = (ISteamGameServer*) pointer;
		return server->BeginAuthSession(authTicket, authTicketSizeInBytes, (uint64) steamID);
	*/

	private static native void endAuthSession(long pointer, long steamID); /*
		ISteamGameServer* server = (ISteamGameServer*) pointer;
		server->EndAuthSession((uint64) steamID);
	*/

	private static native void cancelAuthTicket(long pointer, int authTicket); /*
		ISteamGameServer* server = (ISteamGameServer*) pointer;
		server->CancelAuthTicket(authTicket);
	*/

	private static native int userHasLicenseForApp(long pointer, long steamID, int appID); /*
		ISteamGameServer* server = (ISteamGameServer*) pointer;
		return server->UserHasLicenseForApp((uint64) steamID, (AppId_t) appID);
	*/

	private static native boolean requestUserGroupStatus(long pointer, long steamIDUser, long steamIDGroup); /*
		ISteamGameServer* server = (ISteamGameServer*) pointer;
		return server->RequestUserGroupStatus((uint64) steamIDUser, (uint64) steamIDGroup);
	*/

	private static native int getPublicIP(long pointer); /*
		ISteamGameServer* server = (ISteamGameServer*) pointer;
		return server->GetPublicIP();
	*/

	private static native boolean handleIncomingPacket(long pointer, Buffer data, int sizeInBytes, int srcIP, short srcPort); /*
		ISteamGameServer* server = (ISteamGameServer*) pointer;
		return server->HandleIncomingPacket(data, sizeInBytes, srcIP, srcPort);
	*/

	private static native int getNextOutgoingPacket(long pointer, Buffer out, int capacityInBytes, int[] netAdr, short[] port); /*
		ISteamGameServer* server = (ISteamGameServer*) pointer;
		return server->GetNextOutgoingPacket(out, capacityInBytes, (uint32*) netAdr, (uint16*) port);
	*/

	private static native void enableHeartbeats(long pointer, boolean active); /*
		ISteamGameServer* server = (ISteamGameServer*) pointer;
		server->EnableHeartbeats(active);
	*/

	private static native void setHeartbeatInterval(long pointer, int heartbeatInterval); /*
		ISteamGameServer* server = (ISteamGameServer*) pointer;
		server->SetHeartbeatInterval(heartbeatInterval);
	*/

	private static native void forceHeartbeat(long pointer); /*
		ISteamGameServer* server = (ISteamGameServer*) pointer;
		server->ForceHeartbeat();
	*/

	private static native long associateWithClan(long pointer, long steamIDClan); /*
		ISteamGameServer* server = (ISteamGameServer*) pointer;
		return server->AssociateWithClan((uint64) steamIDClan);
	*/

	private static native long computeNewPlayerCompatibility(long pointer, long steamIDNewPlayer); /*
		ISteamGameServer* server = (ISteamGameServer*) pointer;
		return server->ComputeNewPlayerCompatibility((uint64) steamIDNewPlayer);
	*/

}
