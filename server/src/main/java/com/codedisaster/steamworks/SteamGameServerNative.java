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

    static native void setProduct(long pointer, String product); /*
        ISteamGameServer* server = (ISteamGameServer*) pointer;
        server->SetProduct(product);
    */

    static native void setGameDescription(long pointer, String gameDescription); /*
        ISteamGameServer* server = (ISteamGameServer*) pointer;
        server->SetGameDescription(gameDescription);
    */

    static native void setModDir(long pointer, String modDir); /*
        ISteamGameServer* server = (ISteamGameServer*) pointer;
        server->SetModDir(modDir);
    */

    static native void setDedicatedServer(long pointer, boolean dedicated); /*
        ISteamGameServer* server = (ISteamGameServer*) pointer;
        server->SetDedicatedServer(dedicated);
    */

    static native void logOn(long pointer, String token); /*
        ISteamGameServer* server = (ISteamGameServer*) pointer;
        server->LogOn(token);
    */

    static native void logOnAnonymous(long pointer); /*
        ISteamGameServer* server = (ISteamGameServer*) pointer;
        server->LogOnAnonymous();
    */

    static native void logOff(long pointer); /*
        ISteamGameServer* server = (ISteamGameServer*) pointer;
        server->LogOff();
    */

    static native boolean isLoggedOn(long pointer); /*
        ISteamGameServer* server = (ISteamGameServer*) pointer;
        return server->BLoggedOn();
    */

    static native boolean isSecure(long pointer); /*
        ISteamGameServer* server = (ISteamGameServer*) pointer;
        return server->BSecure();
    */

    static native long getSteamID(long pointer); /*
        ISteamGameServer* server = (ISteamGameServer*) pointer;
        return server->GetSteamID().ConvertToUint64();
    */

    static native boolean wasRestartRequested(long pointer); /*
        ISteamGameServer* server = (ISteamGameServer*) pointer;
        return server->WasRestartRequested();
    */

    static native void setMaxPlayerCount(long pointer, int playersMax); /*
        ISteamGameServer* server = (ISteamGameServer*) pointer;
        server->SetMaxPlayerCount(playersMax);
    */

    static native void setBotPlayerCount(long pointer, int botplayers); /*
        ISteamGameServer* server = (ISteamGameServer*) pointer;
        server->SetBotPlayerCount(botplayers);
    */

    static native void setServerName(long pointer, String serverName); /*
        ISteamGameServer* server = (ISteamGameServer*) pointer;
        server->SetServerName(serverName);
    */

    static native void setMapName(long pointer, String mapName); /*
        ISteamGameServer* server = (ISteamGameServer*) pointer;
        server->SetMapName(mapName);
    */

    static native void setPasswordProtected(long pointer, boolean passwordProtected); /*
        ISteamGameServer* server = (ISteamGameServer*) pointer;
        server->SetPasswordProtected(passwordProtected);
    */

    static native void setSpectatorPort(long pointer, short spectatorPort); /*
        ISteamGameServer* server = (ISteamGameServer*) pointer;
        server->SetSpectatorPort(spectatorPort);
    */

    static native void setSpectatorServerName(long pointer, String spectatorServerName); /*
        ISteamGameServer* server = (ISteamGameServer*) pointer;
        server->SetSpectatorServerName(spectatorServerName);
    */

    static native void clearAllKeyValues(long pointer); /*
        ISteamGameServer* server = (ISteamGameServer*) pointer;
        server->ClearAllKeyValues();
    */

    static native void setKeyValue(long pointer, String key, String value); /*
        ISteamGameServer* server = (ISteamGameServer*) pointer;
        server->SetKeyValue(key, value);
    */

    static native void setGameTags(long pointer, String gameTags); /*
        ISteamGameServer* server = (ISteamGameServer*) pointer;
        server->SetGameTags(gameTags);
    */

    static native void setGameData(long pointer, String gameData); /*
        ISteamGameServer* server = (ISteamGameServer*) pointer;
        server->SetGameData(gameData);
    */

    static native void setRegion(long pointer, String region); /*
        ISteamGameServer* server = (ISteamGameServer*) pointer;
        server->SetRegion(region);
    */

    static native boolean sendUserConnectAndAuthenticate(long pointer, int clientIP, ByteBuffer authBlob,
                                                         int offset, int size, long[] steamIDUser); /*
        ISteamGameServer* server = (ISteamGameServer*) pointer;
        CSteamID user;
        if (server->SendUserConnectAndAuthenticate(clientIP, &authBlob[offset], size, &user)) {
            steamIDUser[0] = user.ConvertToUint64();
            return true;
        }
        return false;
    */

    static native long createUnauthenticatedUserConnection(long pointer); /*
        ISteamGameServer* server = (ISteamGameServer*) pointer;
        return server->CreateUnauthenticatedUserConnection().ConvertToUint64();
    */

    static native void sendUserDisconnect(long pointer, long steamIDUser); /*
        ISteamGameServer* server = (ISteamGameServer*) pointer;
        server->SendUserDisconnect((uint64) steamIDUser);
    */

    static native boolean updateUserData(long pointer, long steamIDUser, String playerName, int score); /*
        ISteamGameServer* server = (ISteamGameServer*) pointer;
        return server->BUpdateUserData((uint64) steamIDUser, playerName, score);
    */

    static native int getAuthSessionTicket(long pointer, ByteBuffer authTicket,
                                           int offset, int size, int[] sizeInBytes); /*
        ISteamGameServer* server = (ISteamGameServer*) pointer;
        int ticket = server->GetAuthSessionTicket(&authTicket[offset], size, (uint32*) sizeInBytes);
        return ticket;
    */

    static native int beginAuthSession(long pointer, ByteBuffer authTicket,
                                       int offset, int size, long steamID); /*
        ISteamGameServer* server = (ISteamGameServer*) pointer;
        return server->BeginAuthSession(&authTicket[offset], size, (uint64) steamID);
    */

    static native void endAuthSession(long pointer, long steamID); /*
        ISteamGameServer* server = (ISteamGameServer*) pointer;
        server->EndAuthSession((uint64) steamID);
    */

    static native void cancelAuthTicket(long pointer, int authTicket); /*
        ISteamGameServer* server = (ISteamGameServer*) pointer;
        server->CancelAuthTicket(authTicket);
    */

    static native int userHasLicenseForApp(long pointer, long steamID, int appID); /*
        ISteamGameServer* server = (ISteamGameServer*) pointer;
        return server->UserHasLicenseForApp((uint64) steamID, (AppId_t) appID);
    */

    static native boolean requestUserGroupStatus(long pointer, long steamIDUser, long steamIDGroup); /*
        ISteamGameServer* server = (ISteamGameServer*) pointer;
        return server->RequestUserGroupStatus((uint64) steamIDUser, (uint64) steamIDGroup);
    */

    static native int getPublicIP(long pointer); /*
        ISteamGameServer* server = (ISteamGameServer*) pointer;
        return server->GetPublicIP();
    */

    static native boolean handleIncomingPacket(long pointer, ByteBuffer data,
                                               int offset, int size, int srcIP, short srcPort); /*
        ISteamGameServer* server = (ISteamGameServer*) pointer;
        return server->HandleIncomingPacket(&data[offset], size, srcIP, srcPort);
    */

    static native int getNextOutgoingPacket(long pointer, ByteBuffer out,
                                            int offset, int size, int[] netAdr, short[] port); /*
        ISteamGameServer* server = (ISteamGameServer*) pointer;
        return server->GetNextOutgoingPacket(&out[offset], size, (uint32*) netAdr, (uint16*) port);
    */

    static native void enableHeartbeats(long pointer, boolean active); /*
        ISteamGameServer* server = (ISteamGameServer*) pointer;
        server->EnableHeartbeats(active);
    */

    static native void setHeartbeatInterval(long pointer, int heartbeatInterval); /*
        ISteamGameServer* server = (ISteamGameServer*) pointer;
        server->SetHeartbeatInterval(heartbeatInterval);
    */

    static native void forceHeartbeat(long pointer); /*
        ISteamGameServer* server = (ISteamGameServer*) pointer;
        server->ForceHeartbeat();
    */

    static native long associateWithClan(long pointer, long steamIDClan); /*
        ISteamGameServer* server = (ISteamGameServer*) pointer;
        return server->AssociateWithClan((uint64) steamIDClan);
    */

    static native long computeNewPlayerCompatibility(long pointer, long steamIDNewPlayer); /*
        ISteamGameServer* server = (ISteamGameServer*) pointer;
        return server->ComputeNewPlayerCompatibility((uint64) steamIDNewPlayer);
    */

}
