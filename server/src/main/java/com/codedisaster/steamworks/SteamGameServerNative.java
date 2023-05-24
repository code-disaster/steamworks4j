package com.codedisaster.steamworks;

import java.nio.ByteBuffer;

class SteamGameServerNative {

	// @off

	/*JNI
		#include "SteamGameServerCallback.h"
	*/

	static native long createCallback(Object javaCallback); /*
		return (intp) new SteamGameServerCallback(env, javaCallback);
	*/

	static native void setProduct(String product); /*
		SteamGameServer()->SetProduct(product);
	*/

	static native void setGameDescription(String gameDescription); /*
		SteamGameServer()->SetGameDescription(gameDescription);
	*/

	static native void setModDir(String modDir); /*
		SteamGameServer()->SetModDir(modDir);
	*/

	static native void setDedicatedServer(boolean dedicated); /*
		SteamGameServer()->SetDedicatedServer(dedicated);
	*/

	static native void logOn(String token); /*
		SteamGameServer()->LogOn(token);
	*/

	static native void logOnAnonymous(); /*
		SteamGameServer()->LogOnAnonymous();
	*/

	static native void logOff(); /*
		SteamGameServer()->LogOff();
	*/

	static native boolean isLoggedOn(); /*
		return SteamGameServer()->BLoggedOn();
	*/

	static native boolean isSecure(); /*
		return SteamGameServer()->BSecure();
	*/

	static native long getSteamID(); /*
		return SteamGameServer()->GetSteamID().ConvertToUint64();
	*/

	static native boolean wasRestartRequested(); /*
		return SteamGameServer()->WasRestartRequested();
	*/

	static native void setMaxPlayerCount(int playersMax); /*
		SteamGameServer()->SetMaxPlayerCount(playersMax);
	*/

	static native void setBotPlayerCount(int botplayers); /*
		SteamGameServer()->SetBotPlayerCount(botplayers);
	*/

	static native void setServerName(String serverName); /*
		SteamGameServer()->SetServerName(serverName);
	*/

	static native void setMapName(String mapName); /*
		SteamGameServer()->SetMapName(mapName);
	*/

	static native void setPasswordProtected(boolean passwordProtected); /*
		SteamGameServer()->SetPasswordProtected(passwordProtected);
	*/

	static native void setSpectatorPort(short spectatorPort); /*
		SteamGameServer()->SetSpectatorPort(spectatorPort);
	*/

	static native void setSpectatorServerName(String spectatorServerName); /*
		SteamGameServer()->SetSpectatorServerName(spectatorServerName);
	*/

	static native void clearAllKeyValues(); /*
		SteamGameServer()->ClearAllKeyValues();
	*/

	static native void setKeyValue(String key, String value); /*
		SteamGameServer()->SetKeyValue(key, value);
	*/

	static native void setGameTags(String gameTags); /*
		SteamGameServer()->SetGameTags(gameTags);
	*/

	static native void setGameData(String gameData); /*
		SteamGameServer()->SetGameData(gameData);
	*/

	static native void setRegion(String region); /*
		SteamGameServer()->SetRegion(region);
	*/

	static native boolean sendUserConnectAndAuthenticate(int clientIP, ByteBuffer authBlob,
														 int offset, int size, long[] steamIDUser); /*
		CSteamID user;
		if (SteamGameServer()->SendUserConnectAndAuthenticate_DEPRECATED(clientIP, &authBlob[offset], size, &user)) {
			steamIDUser[0] = user.ConvertToUint64();
			return true;
		}
		return false;
	*/

	static native long createUnauthenticatedUserConnection(); /*
		return SteamGameServer()->CreateUnauthenticatedUserConnection().ConvertToUint64();
	*/

	static native void sendUserDisconnect(long steamIDUser); /*
		SteamGameServer()->SendUserDisconnect_DEPRECATED((uint64) steamIDUser);
	*/

	static native boolean updateUserData(long steamIDUser, String playerName, int score); /*
		return SteamGameServer()->BUpdateUserData((uint64) steamIDUser, playerName, score);
	*/

	static native int getAuthSessionTicket(ByteBuffer authTicket,
										   int offset, int size, int[] sizeInBytes); /*
		int ticket = SteamGameServer()->GetAuthSessionTicket(&authTicket[offset], size, (uint32*) sizeInBytes, nullptr);
		return ticket;
	*/

	static native int beginAuthSession(ByteBuffer authTicket,
									   int offset, int size, long steamID); /*
		return SteamGameServer()->BeginAuthSession(&authTicket[offset], size, (uint64) steamID);
	*/

	static native void endAuthSession(long steamID); /*
		SteamGameServer()->EndAuthSession((uint64) steamID);
	*/

	static native void cancelAuthTicket(int authTicket); /*
		SteamGameServer()->CancelAuthTicket(authTicket);
	*/

	static native int userHasLicenseForApp(long steamID, int appID); /*
		return SteamGameServer()->UserHasLicenseForApp((uint64) steamID, (AppId_t) appID);
	*/

	static native boolean requestUserGroupStatus(long steamIDUser, long steamIDGroup); /*
		return SteamGameServer()->RequestUserGroupStatus((uint64) steamIDUser, (uint64) steamIDGroup);
	*/

	static native int getPublicIP(); /*
		return (jint) SteamGameServer()->GetPublicIP().m_unIPv4;
	*/

	static native boolean handleIncomingPacket(ByteBuffer data,
											   int offset, int size, int srcIP, short srcPort); /*
		return SteamGameServer()->HandleIncomingPacket(&data[offset], size, srcIP, srcPort);
	*/

	static native int getNextOutgoingPacket(ByteBuffer out,
											int offset, int size, int[] netAdr, short[] port); /*
		return SteamGameServer()->GetNextOutgoingPacket(&out[offset], size, (uint32*) netAdr, (uint16*) port);
	*/

	static native long associateWithClan(long steamIDClan); /*
		return SteamGameServer()->AssociateWithClan((uint64) steamIDClan);
	*/

	static native long computeNewPlayerCompatibility(long steamIDNewPlayer); /*
		return SteamGameServer()->ComputeNewPlayerCompatibility((uint64) steamIDNewPlayer);
	*/

}
