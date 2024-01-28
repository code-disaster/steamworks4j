package com.codedisaster.steamworks;

import java.nio.ByteBuffer;

final class SteamNetworkingSocketsNative {

    // @off

    /*JNI
		#include <steam_api.h>
	*/

    public static native int connectP2P(long steamID, int numVirtualPorts);/*
        SteamNetworkingIdentity identity;
        identity.m_eType = k_ESteamNetworkingIdentityType_SteamID;
        identity.SetSteamID64(steamID);

        HSteamNetConnection connection = SteamNetworkingSockets()->ConnectP2P(identity, numVirtualPorts, 0, NULL);

        return connection;
    */

    public static native int createListenSocketP2P(int numVirtualPorts);/*
        HSteamListenSocket socket = SteamNetworkingSockets()->CreateListenSocketP2P(numVirtualPorts, 0, NULL);

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

    public static native int sendMessageToConnection(int netConnectionHandle, ByteBuffer data, int dataLength, int sendFlags);/*
        return SteamNetworkingSockets()->SendMessageToConnection(netConnectionHandle, &data, dataLength, sendFlags, NULL);
    */

    public static native int receiveMessagesOnConnection(int netConnectionHandle, SteamNetworkingMessage[] messageBuffer, int maxMessages);/*

        if(maxMessages <= 0){
            return 0;
        }

        jsize arrayLength = env->GetArrayLength(messageBuffer);

        if(arrayLength < maxMessages){
            return 0;
        }

        SteamNetworkingMessage_t** ppOutMessages;

        int num = SteamNetworkingSockets()->ReceiveMessagesOnConnection(netConnectionHandle, ppOutMessages, maxMessages);

        for(int i = 0; i < num;i++){
            SteamNetworkingMessage_t* netMessage = ppOutMessages[i];

            jobject message = env->GetObjectArrayElement(messageBuffer, i);
            jclass clazz = env->GetObjectClass(message);

            jfieldID payloadField = env->GetFieldID(clazz, "payload", "[B");
            jfieldID connectionField = env->GetFieldID(clazz, "connectionHandle", "I");

            env->SetIntField(message, connectionField, netMessage->m_conn);

            jbyteArray javaByteArray = env->NewByteArray(netMessage->m_cbSize);

            env->SetByteArrayRegion(javaByteArray, 0, netMessage->m_cbSize, (const jbyte*) netMessage->m_pData);

            env->DeleteLocalRef(javaByteArray);
            env->DeleteLocalRef(message);

            netMessage->Release();
        }

        return num;
    */

    public static native int flushMessages(int connectionHandle);/*
        return SteamNetworkingSockets()->FlushMessagesOnConnection(connectionHandle);
    */

    public static native int createPollGroup(); /*
        HSteamNetPollGroup pollGroup = SteamNetworkingSockets()->CreatePollGroup();

        return pollGroup;
    */

    public static native boolean destroyPollGroup(int handle); /*
        return SteamNetworkingSockets()->DestroyPollGroup(handle);
    */

    public static native boolean setConnectionPollGroup(int connectionHandle, int pollGroupHandle); /*
        return SteamNetworkingSockets()->SetConnectionPollGroup(connectionHandle, pollGroupHandle);
    */

    public static native int receiveMessagesOnPollGroup(int pollGroupHandle, SteamNetworkingMessage[] messageBuffer, int maxMessages);/*
        if(maxMessages <= 0){
            return 0;
        }

        jsize arrayLength = env->GetArrayLength(messageBuffer);

        if(arrayLength < maxMessages){
            return 0;
        }

        SteamNetworkingMessage_t** ppOutMessages;

        int num = SteamNetworkingSockets()->ReceiveMessagesOnPollGroup(pollGroupHandle, ppOutMessages, maxMessages);

        for(int i = 0; i < num;i++){
            SteamNetworkingMessage_t* netMessage = ppOutMessages[i];

            jobject message = env->GetObjectArrayElement(messageBuffer, i);
            jclass clazz = env->GetObjectClass(message);

            jfieldID payloadField = env->GetFieldID(clazz, "payload", "[B");
            jfieldID connectionField = env->GetFieldID(clazz, "connectionHandle", "I");

            env->SetIntField(message, connectionField, netMessage->m_conn);

            jbyteArray javaByteArray = env->NewByteArray(netMessage->m_cbSize);

            env->SetByteArrayRegion(javaByteArray, 0, netMessage->m_cbSize, (const jbyte*) netMessage->m_pData);

            env->DeleteLocalRef(javaByteArray);
            env->DeleteLocalRef(message);

            netMessage->Release();
        }

        return num;
    */

    public static native int initAuthentication();/*
        return SteamNetworkingSockets()->InitAuthentication();
    */

    public static native int getAuthenticationStatus();/*
        return SteamNetworkingSockets()->GetAuthenticationStatus(NULL);
    */

}
