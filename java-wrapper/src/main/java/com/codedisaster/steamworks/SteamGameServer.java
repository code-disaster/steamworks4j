package com.codedisaster.steamworks;

import java.nio.Buffer;

/**
 *
 * @author Francisco "Franz" Bischoff
 */
public class SteamGameServer extends SteamInterface {

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

	public SteamGameServer(long pointer, SteamGameServerCallback callback) {
		super(pointer);
		registerCallback(new SteamGameServerCallbackAdapter(callback));
	}

	static void dispose() {
		registerCallback(null);
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

	public int getAuthSessionTicket(Buffer authTicket) {
		int[] sizeInBytes = new int[1];
		int ticket = getAuthSessionTicket(pointer, authTicket, authTicket.capacity(), sizeInBytes);
		authTicket.limit(sizeInBytes[0]);
		return ticket;
	}

	public BeginAuthSessionResult beginAuthSession(Buffer authTicket, SteamID steamID) {
		int result = beginAuthSession(pointer, authTicket, authTicket.limit(), steamID.handle);
		return BeginAuthSessionResult.byOrdinal(result);
	}

	public void endAuthSession(SteamID steamID) {
		endAuthSession(pointer, steamID.handle);
	}

	public void cancelAuthTicket(int authTicket) {
		cancelAuthTicket(pointer, authTicket);
	}

	public UserHasLicenseForAppResult userHasLicenseForApp(SteamID steamID, long appID) {
		return UserHasLicenseForAppResult.byOrdinal(userHasLicenseForApp(pointer, steamID.handle, appID));
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
		#include <steam_gameserver.h>
		#include "SteamGameServerCallback.h"

		static SteamGameServerCallback* callback = NULL;
	*/

	static private native boolean registerCallback(SteamGameServerCallbackAdapter javaCallback); /*
		if (callback != NULL) {
			delete callback;
			callback = NULL;
		}

		if (javaCallback != NULL) {
			callback = new SteamGameServerCallback(env, javaCallback);
		}

		return callback != NULL;
	*/

	static private native void setProduct(long pointer, String product); /*
		ISteamGameServer* server = (ISteamGameServer*) pointer;
		server->SetProduct(product);
	*/

	static private native void setGameDescription(long pointer, String gameDescription); /*
		ISteamGameServer* server = (ISteamGameServer*) pointer;
		server->SetGameDescription(gameDescription);
	*/

	static private native void setModDir(long pointer, String modDir); /*
		ISteamGameServer* server = (ISteamGameServer*) pointer;
		server->SetModDir(modDir);
	*/

	static private native void setDedicatedServer(long pointer, boolean dedicated); /*
		ISteamGameServer* server = (ISteamGameServer*) pointer;
		server->SetDedicatedServer(dedicated);
	*/

	static private native void logOn(long pointer, String token); /*
		ISteamGameServer* server = (ISteamGameServer*) pointer;
		server->LogOn(token);
	*/

	static private native void logOnAnonymous(long pointer); /*
		ISteamGameServer* server = (ISteamGameServer*) pointer;
		server->LogOnAnonymous();
	*/

	static private native void logOff(long pointer); /*
		ISteamGameServer* server = (ISteamGameServer*) pointer;
		server->LogOff();
	*/

	static private native boolean isLoggedOn(long pointer); /*
		ISteamGameServer* server = (ISteamGameServer*) pointer;
		return server->BLoggedOn();
	*/

	static private native boolean isSecure(long pointer); /*
		ISteamGameServer* server = (ISteamGameServer*) pointer;
		return server->BSecure();
	*/

	static private native long getSteamID(long pointer); /*
		ISteamGameServer* server = (ISteamGameServer*) pointer;
		return server->GetSteamID().ConvertToUint64();
	*/

	static private native boolean wasRestartRequested(long pointer); /*
		ISteamGameServer* server = (ISteamGameServer*) pointer;
		return server->WasRestartRequested();
	*/

	static private native void setMaxPlayerCount(long pointer, int playersMax); /*
		ISteamGameServer* server = (ISteamGameServer*) pointer;
		server->SetMaxPlayerCount(playersMax);
	*/

	static private native void setBotPlayerCount(long pointer, int botplayers); /*
		ISteamGameServer* server = (ISteamGameServer*) pointer;
		server->SetBotPlayerCount(botplayers);
	*/

	static private native void setServerName(long pointer, String serverName); /*
		ISteamGameServer* server = (ISteamGameServer*) pointer;
		server->SetServerName(serverName);
	*/

	static private native void setMapName(long pointer, String mapName); /*
		ISteamGameServer* server = (ISteamGameServer*) pointer;
		server->SetMapName(mapName);
	*/

	static private native void setPasswordProtected(long pointer, boolean passwordProtected); /*
		ISteamGameServer* server = (ISteamGameServer*) pointer;
		server->SetPasswordProtected(passwordProtected);
	*/

	static private native void setSpectatorPort(long pointer, short spectatorPort); /*
		ISteamGameServer* server = (ISteamGameServer*) pointer;
		server->SetSpectatorPort(spectatorPort);
	*/

	static private native void setSpectatorServerName(long pointer, String spectatorServerName); /*
		ISteamGameServer* server = (ISteamGameServer*) pointer;
		server->SetSpectatorServerName(spectatorServerName);
	*/

	static private native void clearAllKeyValues(long pointer); /*
		ISteamGameServer* server = (ISteamGameServer*) pointer;
		server->ClearAllKeyValues();
	*/

	static private native void setKeyValue(long pointer, String key, String value); /*
		ISteamGameServer* server = (ISteamGameServer*) pointer;
		server->SetKeyValue(key, value);
	*/

	static private native void setGameTags(long pointer, String gameTags); /*
		ISteamGameServer* server = (ISteamGameServer*) pointer;
		server->SetGameTags(gameTags);
	*/

	static private native void setGameData(long pointer, String gameData); /*
		ISteamGameServer* server = (ISteamGameServer*) pointer;
		server->SetGameData(gameData);
	*/

	static private native void setRegion(long pointer, String region); /*
		ISteamGameServer* server = (ISteamGameServer*) pointer;
		server->SetRegion(region);
	*/

	static private native boolean sendUserConnectAndAuthenticate(long pointer, int clientIP,
																 Buffer authBlob, int authBlobSize, long[] steamIDUser); /*
		ISteamGameServer* server = (ISteamGameServer*) pointer;
		CSteamID user;
		if (server->SendUserConnectAndAuthenticate(clientIP, authBlob, authBlobSize, &user)) {
			steamIDUser[0] = user.ConvertToUint64();
			return true;
		}
		return false;
	*/

	static private native long createUnauthenticatedUserConnection(long pointer); /*
		ISteamGameServer* server = (ISteamGameServer*) pointer;
		return server->CreateUnauthenticatedUserConnection().ConvertToUint64();
	*/

	static private native void sendUserDisconnect(long pointer, long steamIDUser); /*
		ISteamGameServer* server = (ISteamGameServer*) pointer;
		server->SendUserDisconnect((uint64) steamIDUser);
	*/

	static private native boolean updateUserData(long pointer, long steamIDUser, String playerName, int score); /*
		ISteamGameServer* server = (ISteamGameServer*) pointer;
		return server->BUpdateUserData((uint64) steamIDUser, playerName, score);
	*/

	static private native int getAuthSessionTicket(long pointer, Buffer authTicket, int capacityInBytes, int[] sizeInBytes); /*
		ISteamGameServer* server = (ISteamGameServer*) pointer;
		int ticket = server->GetAuthSessionTicket(authTicket, capacityInBytes, (uint32*) sizeInBytes);
		return ticket;
	*/

	static private native int beginAuthSession(long pointer, Buffer authTicket, int authTicketSizeInBytes, long steamID); /*
		ISteamGameServer* server = (ISteamGameServer*) pointer;
		return server->BeginAuthSession(authTicket, authTicketSizeInBytes, (uint64) steamID);
	*/

	static private native void endAuthSession(long pointer, long steamID); /*
		ISteamGameServer* server = (ISteamGameServer*) pointer;
		server->EndAuthSession((uint64) steamID);
	*/

	static private native void cancelAuthTicket(long pointer, int authTicket); /*
		ISteamGameServer* server = (ISteamGameServer*) pointer;
		server->CancelAuthTicket(authTicket);
	*/

	static private native int userHasLicenseForApp(long pointer, long steamID, long appID); /*
		ISteamGameServer* server = (ISteamGameServer*) pointer;
		return server->UserHasLicenseForApp((uint64) steamID, (AppId_t) appID);
	*/

	static private native boolean requestUserGroupStatus(long pointer, long steamIDUser, long steamIDGroup); /*
		ISteamGameServer* server = (ISteamGameServer*) pointer;
		return server->RequestUserGroupStatus((uint64) steamIDUser, (uint64) steamIDGroup);
	*/

	static private native int getPublicIP(long pointer); /*
		ISteamGameServer* server = (ISteamGameServer*) pointer;
		return server->GetPublicIP();
	*/

	static private native boolean handleIncomingPacket(long pointer, Buffer data, int sizeInBytes, int srcIP, short srcPort); /*
		ISteamGameServer* server = (ISteamGameServer*) pointer;
		return server->HandleIncomingPacket(data, sizeInBytes, srcIP, srcPort);
	*/

	static private native int getNextOutgoingPacket(long pointer, Buffer out, int capacityInBytes, int[] netAdr, short[] port); /*
		ISteamGameServer* server = (ISteamGameServer*) pointer;
		return server->GetNextOutgoingPacket(out, capacityInBytes, (uint32*) netAdr, (uint16*) port);
	*/

	static private native void enableHeartbeats(long pointer, boolean active); /*
		ISteamGameServer* server = (ISteamGameServer*) pointer;
		server->EnableHeartbeats(active);
	*/

	static private native void setHeartbeatInterval(long pointer, int heartbeatInterval); /*
		ISteamGameServer* server = (ISteamGameServer*) pointer;
		server->SetHeartbeatInterval(heartbeatInterval);
	*/

	static private native void forceHeartbeat(long pointer); /*
		ISteamGameServer* server = (ISteamGameServer*) pointer;
		server->ForceHeartbeat();
	*/

	static private native long associateWithClan(long pointer, long steamIDClan); /*
		ISteamGameServer* server = (ISteamGameServer*) pointer;
		return server->AssociateWithClan((uint64) steamIDClan);
	*/

	static private native long computeNewPlayerCompatibility(long pointer, long steamIDNewPlayer); /*
		ISteamGameServer* server = (ISteamGameServer*) pointer;
		return server->ComputeNewPlayerCompatibility((uint64) steamIDNewPlayer);
	*/

}
