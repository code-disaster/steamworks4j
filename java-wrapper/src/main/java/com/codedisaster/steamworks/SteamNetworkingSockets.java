package com.codedisaster.steamworks;

import java.nio.ByteBuffer;

public class SteamNetworkingSockets extends SteamInterface {

    public static final int SEND_FLAG_UNRELIABLE = 0;
    public static final int SEND_FLAG_NO_NAGLE = 1;
    public static final int SEND_FLAG_NO_DELAY = 4;
    public static final int SEND_FLAG_RELIABLE = 8;
    public static final int SEND_FLAG_AUTO_RESTART = 32;


    public SteamNetworkingSockets() {
    }

    public int connectP2P(long steamID, int numVirtualPorts){
        return SteamNetworkingSocketsNative.connectP2P(steamID, numVirtualPorts);
    }

    public long createListenSocketP2P(int numVirtualPorts){
        return SteamNetworkingSocketsNative.createListenSocketP2P(numVirtualPorts);
    }

    /**
     * If handle is invalid returns {@link SteamResult#InvalidParam}<br>
     * If connection state is not appropriate returns {@link SteamResult#InvalidState}<br>
     *
     * otherwise,
     *
     * {@link SteamResult#OK}
     */
    public SteamResult acceptConnection(int connectionHandle){
        int result = SteamNetworkingSocketsNative.acceptConnection(connectionHandle);

        return SteamResult.byValue(result);
    }

    public boolean closeConnection(int connectionHandle, int reason, boolean linger){
        return SteamNetworkingSocketsNative.closeConnection(connectionHandle, reason, linger);
    }

    public boolean closeListenSocket(int socketHandle){
        return SteamNetworkingSocketsNative.closeListenSocket(socketHandle);
    }

    /**
     * k_EResultInvalidParam: invalid connection handle, or the individual message is too big. (See k_cbMaxSteamNetworkingSocketsMessageSizeSend)
     * k_EResultInvalidState: connection is in an invalid state
     * k_EResultNoConnection: connection has ended
     * k_EResultIgnored: You used k_nSteamNetworkingSend_NoDelay, and the message was dropped because we were not ready to send it.
     * k_EResultLimitExceeded: there was already too much data queued to be sent. (See k_ESteamNetworkingConfig_SendBufferSize)
     */
    public SteamResult sendMessageToConnection(int connectionHandle, byte[] data, int sendFlags){
        int result = SteamNetworkingSocketsNative.sendMessageToConnection(connectionHandle, ByteBuffer.wrap(data), data.length, sendFlags);

        return SteamResult.byValue(result);
    }

    /**
     * k_EResultInvalidParam: invalid connection handle
     * k_EResultInvalidState: connection is in an invalid state
     * k_EResultNoConnection: connection has ended
     * k_EResultIgnored: We weren't (yet) connected, so this operation has no effect.
     */
    public SteamResult flushMessages(int connectionHandle){
        int result = SteamNetworkingSocketsNative.flushMessages(connectionHandle);
        return SteamResult.byValue(result);
    }

    public SteamNetworkingMessage[] receiveMessagesOnConnection(int connectionHandle, int numMaxMessages){
        SteamNetworkingMessage[] messages = new SteamNetworkingMessage[numMaxMessages];

        for(int i = 0; i < messages.length;i++){
            messages[i] = new SteamNetworkingMessage();
        }

        SteamNetworkingSocketsNative.receiveMessagesOnConnection(connectionHandle, messages, numMaxMessages);

        return messages;
    }

    public int createPollGroup(){
        return SteamNetworkingSocketsNative.createPollGroup();
    }

    public boolean destroyPollGroup(int handle){
        return SteamNetworkingSocketsNative.destroyPollGroup(handle);
    }

    public boolean setConnectionPollGroup(int connectionHandle, int pollGroupHandle){
        return SteamNetworkingSocketsNative.setConnectionPollGroup(connectionHandle, pollGroupHandle);
    }

    public SteamNetworkingMessage[] receiveMessagesOnPollGroup(int pollGroupHandle, int numMaxMessages){
        SteamNetworkingMessage[] messages = new SteamNetworkingMessage[numMaxMessages];

        for(int i = 0; i < messages.length;i++){
            messages[i] = new SteamNetworkingMessage();
        }

        SteamNetworkingSocketsNative.receiveMessagesOnConnection(pollGroupHandle, messages, numMaxMessages);

        return messages;
    }

    /**
     * @return
     * enum ESteamNetworkingAvailability
     * {
     * 	// Negative values indicate a problem.
     * 	//
     * 	// In general, we will not automatically retry unless you take some action that
     * 	// depends on of requests this resource, such as querying the status, attempting
     * 	// to initiate a connection, receive a connection, etc.  If you do not take any
     * 	// action at all, we do not automatically retry in the background.
     * 	k_ESteamNetworkingAvailability_CannotTry = -102,		// A dependent resource is missing, so this service is unavailable.  (E.g. we cannot talk to routers because Internet is down or we don't have the network config.)
     * 	k_ESteamNetworkingAvailability_Failed = -101,			// We have tried for enough time that we would expect to have been successful by now.  We have never been successful
     * 	k_ESteamNetworkingAvailability_Previously = -100,		// We tried and were successful at one time, but now it looks like we have a problem
     *
     * 	k_ESteamNetworkingAvailability_Retrying = -10,		// We previously failed and are currently retrying
     *
     * 	// Not a problem, but not ready either
     * 	k_ESteamNetworkingAvailability_NeverTried = 1,		// We don't know because we haven't ever checked/tried
     * 	k_ESteamNetworkingAvailability_Waiting = 2,			// We're waiting on a dependent resource to be acquired.  (E.g. we cannot obtain a cert until we are logged into Steam.  We cannot measure latency to relays until we have the network config.)
     * 	k_ESteamNetworkingAvailability_Attempting = 3,		// We're actively trying now, but are not yet successful.
     *
     * 	k_ESteamNetworkingAvailability_Current = 100,			// Resource is online/available
     *
     *
     * 	k_ESteamNetworkingAvailability_Unknown = 0,			// Internal dummy/sentinel, or value is not applicable in this context
     * 	k_ESteamNetworkingAvailability__Force32bit = 0x7fffffff,
     * };
     */
    public int initAuthentication(){
        return SteamNetworkingSocketsNative.initAuthentication();
    }

    public int getAuthenticationStatus(){
        return SteamNetworkingSocketsNative.getAuthenticationStatus();
    }

}
