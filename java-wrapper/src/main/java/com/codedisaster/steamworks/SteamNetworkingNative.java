package com.codedisaster.steamworks;

import java.nio.ByteBuffer;

final class SteamNetworkingNative {

	// @off

	/*JNI
		#include "SteamNetworkingCallback.h"
	*/

	static native long createCallback(SteamNetworkingCallbackAdapter javaCallback); /*
		return (intp) new SteamNetworkingCallback(env, javaCallback);
	*/

	static native boolean sendP2PPacket(boolean server, long steamIDRemote, ByteBuffer data,
										int offset, int size, int sendType, int channel); /*

		ISteamNetworking* net = server ? SteamGameServerNetworking() : SteamNetworking();
		return net->SendP2PPacket((uint64) steamIDRemote, &data[offset], size, (EP2PSend) sendType, channel);
	*/

	static native boolean isP2PPacketAvailable(boolean server, int[] msgSize, int channel); /*
		ISteamNetworking* net = server ? SteamGameServerNetworking() : SteamNetworking();
		return net->IsP2PPacketAvailable((uint32 *)msgSize, channel);
	*/

	static native boolean readP2PPacket(boolean server, ByteBuffer dest, int offset, int size,
										int[] msgSizeInBytes, long[] steamIDRemote, int channel); /*

		ISteamNetworking* net = server ? SteamGameServerNetworking() : SteamNetworking();
		CSteamID remote;
		if (net->ReadP2PPacket(&dest[offset], size, (uint32*) msgSizeInBytes, &remote, channel)) {
			steamIDRemote[0] = remote.ConvertToUint64();
			return true;
		}
		return false;
	*/

	static native boolean acceptP2PSessionWithUser(boolean server, long steamIDRemote); /*
		ISteamNetworking* net = server ? SteamGameServerNetworking() : SteamNetworking();
		return net->AcceptP2PSessionWithUser((uint64) steamIDRemote);
	*/

	static native boolean closeP2PSessionWithUser(boolean server, long steamIDRemote); /*
		ISteamNetworking* net = server ? SteamGameServerNetworking() : SteamNetworking();
		return net->CloseP2PSessionWithUser((uint64) steamIDRemote);
	*/

	static native boolean closeP2PChannelWithUser(boolean server, long steamIDRemote, int channel); /*
		ISteamNetworking* net = server ? SteamGameServerNetworking() : SteamNetworking();
		return net->CloseP2PChannelWithUser((uint64) steamIDRemote, channel);
	*/

	static native boolean getP2PSessionState(boolean server, long steamIDRemote,
											 SteamNetworking.P2PSessionState connectionState); /*

		ISteamNetworking* net = server ? SteamGameServerNetworking() : SteamNetworking();
		P2PSessionState_t result;

		if (net->GetP2PSessionState((uint64) steamIDRemote, &result)) {
			jclass clazz = env->GetObjectClass(connectionState);

			jfieldID field = env->GetFieldID(clazz, "connectionActive", "B");
			env->SetByteField(connectionState, field, (jbyte) result.m_bConnectionActive);

			field = env->GetFieldID(clazz, "connecting", "B");
			env->SetByteField(connectionState, field, (jbyte) result.m_bConnecting);

			field = env->GetFieldID(clazz, "sessionError", "B");
			env->SetByteField(connectionState, field, (jbyte) result.m_eP2PSessionError);

			field = env->GetFieldID(clazz, "usingRelay", "B");
			env->SetByteField(connectionState, field, (jbyte) result.m_bUsingRelay);

			field = env->GetFieldID(clazz, "bytesQueuedForSend", "I");
			env->SetIntField(connectionState, field, result.m_nBytesQueuedForSend);

			field = env->GetFieldID(clazz, "packetsQueuedForSend", "I");
			env->SetIntField(connectionState, field, result.m_nPacketsQueuedForSend);

			field = env->GetFieldID(clazz, "remoteIP", "I");
			env->SetIntField(connectionState, field, (jint) result.m_nRemoteIP);

			field = env->GetFieldID(clazz, "remotePort", "S");
			env->SetShortField(connectionState, field, (jshort) result.m_nRemotePort);

			return true;
        }

        return false;
	*/

	static native boolean allowP2PPacketRelay(boolean server, boolean allow); /*
		ISteamNetworking* net = server ? SteamGameServerNetworking() : SteamNetworking();
		return net->AllowP2PPacketRelay(allow);
	*/

}
