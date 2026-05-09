package com.codedisaster.steamworks;

import java.nio.ByteBuffer;

final class SteamNetworkingSocketsNative {

    // @off

    /*JNI
		#include <steam_api.h>
		#include "SteamNetworkingSocketsCallback.h"
		#include <iostream>
	*/

    static native long createCallback(SteamNetworkingSocketsCallbackAdapter javaCallback); /*
		return (intp) new SteamNetworkingSocketsCallback(env, javaCallback);
	*/

    public static native int connectP2P(long steamID, int virtualPort);/*
        SteamNetworkingIdentity identity;
        identity.m_eType = k_ESteamNetworkingIdentityType_SteamID;
        identity.SetSteamID64(steamID);

        HSteamNetConnection connection = SteamNetworkingSockets()->ConnectP2P(identity, virtualPort, 0, NULL);

        return connection;
    */

    public static native int createListenSocketP2P(int virtualPort);/*
        HSteamListenSocket socket = SteamNetworkingSockets()->CreateListenSocketP2P(virtualPort, 0, NULL);

        return socket;
    */

    public static native int acceptConnection(int netConnectionHandle);/*
        return SteamNetworkingSockets()->AcceptConnection(netConnectionHandle);
    */

    public static native boolean closeConnection(int netConnectionHandle, int reason, boolean linger);/*
        return SteamNetworkingSockets()->CloseConnection(netConnectionHandle, reason, NULL, linger);
    */

    public static native boolean closeListenSocket(int socketHandle);/*
        return SteamNetworkingSockets()->CloseListenSocket(socketHandle);
    */

    public static native int sendMessageToConnection(int netConnectionHandle, ByteBuffer data, int offset, int size, int sendFlags);/*
        return SteamNetworkingSockets()->SendMessageToConnection(netConnectionHandle, &data[offset], size, sendFlags, NULL);
    */

    public static native int receiveMessageOnConnection(int netConnectionHandle, ByteBuffer data, int offset, int size);/*

        SteamNetworkingMessage_t* messages[1];

        int messagesReceived = SteamNetworkingSockets()->ReceiveMessagesOnConnection((HSteamNetConnection)netConnectionHandle, messages, 1);
        if (messagesReceived <= 0 || !messages[0]) {
            return 0;
        }

        SteamNetworkingMessage_t* message = messages[0];
        if (message->m_cbSize > size) {
            message->Release();
            return -message->m_cbSize;
        }

        memcpy(&data[offset], message->m_pData, message->m_cbSize);

        int bytesWritten = message->m_cbSize;

        message->Release();

        return bytesWritten;
    */

    public static native int flushMessages(int connectionHandle);/*
        return SteamNetworkingSockets()->FlushMessagesOnConnection(connectionHandle);
    */

    public static native void enableSymmetricConnect();/*
        int32_t v = 1;
        SteamNetworkingUtils()->SetConfigValue(
            k_ESteamNetworkingConfig_SymmetricConnect,
            k_ESteamNetworkingConfig_Global,
            0,
            k_ESteamNetworkingConfig_Int32,
            &v
        );
    */
}
