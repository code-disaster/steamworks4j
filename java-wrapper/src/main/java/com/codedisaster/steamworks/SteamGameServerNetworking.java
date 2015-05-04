package com.codedisaster.steamworks;

/**
 *
 * @author Francisco "Franz" Bischoff
 */
public class SteamGameServerNetworking extends SteamInterface {

    public enum EP2PSessionError {

        k_EP2PSessionErrorNone,
        k_EP2PSessionErrorNotRunningApp, // target is not running the same game
        k_EP2PSessionErrorNoRightsToApp, // local user doesn't own the app that is running
        k_EP2PSessionErrorDestinationNotLoggedIn, // target user isn't connected to Steam
        k_EP2PSessionErrorTimeout, // target isn't responding, perhaps not calling AcceptP2PSessionWithUser()
        // corporate firewalls can also block this (NAT traversal is not firewall traversal)
        // make sure that UDP ports 3478, 4379, and 4380 are open in an outbound direction
        k_EP2PSessionErrorMax
    };

    public SteamGameServerNetworking(long pointer, SteamGameServerNetworkingCallback callback) {
        super(pointer);
        registerCallback(new SteamGameServerNetworkingCallbackAdapter(callback));
    }

    static void dispose() {
        registerCallback(null);
    }

    // @off

    /*JNI
     #include <steam_gameserver.h>
     #include "SteamGameServerNetworkingCallback.h"

     static SteamGameServerNetworkingCallback* callback = NULL;
     */
    static private native boolean registerCallback(SteamGameServerNetworkingCallbackAdapter javaCallback); /*
     if (callback != NULL) {
     delete callback;
     callback = NULL;
     }

     if (javaCallback != NULL) {
     callback = new SteamGameServerNetworkingCallback(env, javaCallback);
     }

     return callback != NULL;
     */

    // bool  SendP2PPacket (CSteamID steamIDRemote, const void *pubData, uint32 cubData, EP2PSend eP2PSendType, int nChannel=0)

    static public native boolean isP2PPacketAvailable(Integer pcubMsgSize, int nChannel); /*
     return SteamGameServerNetworking()->IsP2PPacketAvailable((uint32 *)pcubMsgSize, nChannel);
     */

// bool  ReadP2PPacket (void *pubDest, uint32 cubDest, uint32 *pcubMsgSize, CSteamID *psteamIDRemote, int nChannel=0)

    static public boolean acceptP2PSessionWithUser(SteamID steamIDRemote) {
        return nativeAcceptP2PSessionWithUser(steamIDRemote.handle);
    }

    static private native boolean nativeAcceptP2PSessionWithUser(long steamIDRemote); /*
     return SteamGameServerNetworking()->AcceptP2PSessionWithUser((uint64)steamIDRemote);
     */


    static public boolean closeP2PSessionWithUser(SteamID steamIDRemote) {
        return nativeCloseP2PSessionWithUser(steamIDRemote.handle);
    }

    static private native boolean nativeCloseP2PSessionWithUser(long steamIDRemote); /*
     return SteamGameServerNetworking()->CloseP2PSessionWithUser((uint64)steamIDRemote);
     */


    static public boolean closeP2PChannelWithUser(SteamID steamIDRemote, int nChannel) {
        return nativeCloseP2PChannelWithUser(steamIDRemote.handle, nChannel);
    }

    static private native boolean nativeCloseP2PChannelWithUser(long steamIDRemote, int nChannel); /*
     return SteamGameServerNetworking()->CloseP2PChannelWithUser((uint64)steamIDRemote, nChannel);
     */


    static public boolean getP2PSessionState(SteamID steamIDRemote, Integer pConnectionState) {
        return nativeGetP2PSessionState(steamIDRemote.handle, pConnectionState);
    }

    static private native boolean nativeGetP2PSessionState(long steamIDRemote, Integer pConnectionState); /*
     return SteamGameServerNetworking()->GetP2PSessionState((uint64)steamIDRemote, (P2PSessionState_t *)pConnectionState);
     */


    static public native boolean allowP2PPacketRelay(boolean bAllow); /*
     return SteamGameServerNetworking()->AllowP2PPacketRelay(bAllow);
     */

}
