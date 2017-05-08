package com.codedisaster.steamworks;

import java.nio.ByteBuffer;

/**
 *
 * @author Francisco "Franz" Bischoff
 */
public class SteamNetworking extends SteamInterface {

	public enum API {
		Client,
		Server
	}

	public enum P2PSend {
		Unreliable,
		UnreliableNoDelay,
		Reliable,
		ReliableWithBuffering,
	}

	public enum P2PSessionError {
		None,
		NotRunningApp,
		NoRightsToApp,
		DestinationNotLoggedIn,
		Timeout;

		private static final P2PSessionError[] values = values();

		public static P2PSessionError byOrdinal(int sessionError) {
			return values[sessionError];
		}
	}

	public static class P2PSessionState {
		byte connectionActive;
		byte connecting;
		byte sessionError;
		byte usingRelay;
		int bytesQueuedForSend;
		int packetsQueuedForSend;
		int remoteIP;
		short remotePort;

		public boolean isConnectionActive() {
			return connectionActive != 0;
		}

		public boolean isConnecting() {
			return connecting != 0;
		}

		public P2PSessionError getLastSessionError() {
			return P2PSessionError.byOrdinal(sessionError);
		}

		public boolean isUsingRelay() {
			return usingRelay != 0;
		}

		public int getBytesQueuedForSend() {
			return bytesQueuedForSend;
		}

		public int getPacketsQueuedForSend() {
			return packetsQueuedForSend;
		}

		public int getRemoteIP() {
			return remoteIP;
		}

		public short getRemotePort() {
			return remotePort;
		}
	}

	private final int[] tmpIntResult = new int[1];
	private final long[] tmpLongResult = new long[1];

	public SteamNetworking(SteamNetworkingCallback callback, API api) {
		super(api == API.Client ? SteamAPI.getSteamNetworkingPointer()
				: SteamGameServerAPI.getSteamGameServerNetworkingPointer(),
				createCallback(new SteamNetworkingCallbackAdapter(callback), api == API.Client));
	}

	public boolean sendP2PPacket(SteamID steamIDRemote, ByteBuffer data,
								 P2PSend sendType, int channel) throws SteamException {

		if (!data.isDirect()) {
			throw new SteamException("Direct buffer required!");
		}

		return sendP2PPacket(pointer, steamIDRemote.handle, data,
				data.position(), data.remaining(), sendType.ordinal(), channel);
	}

	public int isP2PPacketAvailable(int channel) {
		if (isP2PPacketAvailable(pointer, tmpIntResult, channel)) {
			return tmpIntResult[0];
		}
		return 0;
	}

	/**
	 * Read incoming packet data into a direct {@link ByteBuffer}.
	 *
	 * On success, returns the number of bytes received, and the <code>steamIDRemote</code> parameter contains the
	 * sender's ID.
	 */
	public int readP2PPacket(SteamID steamIDRemote, ByteBuffer dest, int channel) throws SteamException {

		if (!dest.isDirect()) {
			throw new SteamException("Direct buffer required!");
		}

		if (readP2PPacket(pointer, dest, dest.position(), dest.remaining(), tmpIntResult, tmpLongResult, channel)) {
			steamIDRemote.handle = tmpLongResult[0];
			return tmpIntResult[0];
		}

		return 0;
	}

	public boolean acceptP2PSessionWithUser(SteamID steamIDRemote) {
		return acceptP2PSessionWithUser(pointer, steamIDRemote.handle);
	}

	public boolean closeP2PSessionWithUser(SteamID steamIDRemote) {
		return closeP2PSessionWithUser(pointer, steamIDRemote.handle);
	}

	public boolean closeP2PChannelWithUser(SteamID steamIDRemote, int channel) {
		return closeP2PChannelWithUser(pointer, steamIDRemote.handle, channel);
	}

	public boolean getP2PSessionState(SteamID steamIDRemote, P2PSessionState connectionState) {
		return getP2PSessionState(pointer, steamIDRemote.handle, connectionState);
	}

	public boolean allowP2PPacketRelay(boolean allow) {
		return allowP2PPacketRelay(pointer, allow);
	}

	// @off

	/*JNI
		#include "SteamNetworkingCallback.h"
		#include "SteamGameServerNetworkingCallback.h"
	*/

	private static native long createCallback(SteamNetworkingCallbackAdapter javaCallback, boolean isClient); /*
		if (isClient) {
			return (intp) new SteamNetworkingCallback(env, javaCallback);
		} else {
			return (intp) new SteamGameServerNetworkingCallback(env, javaCallback);
		}
	*/

	private static native boolean sendP2PPacket(long pointer, long steamIDRemote, ByteBuffer data,
												int offset, int size, int sendType, int channel); /*

		ISteamNetworking* net = (ISteamNetworking*) pointer;
		return net->SendP2PPacket((uint64) steamIDRemote, &data[offset], size, (EP2PSend) sendType, channel);
	*/

	private static native boolean isP2PPacketAvailable(long pointer, int[] msgSize, int channel); /*
		ISteamNetworking* net = (ISteamNetworking*) pointer;
		return net->IsP2PPacketAvailable((uint32 *)msgSize, channel);
	*/

	private static native boolean readP2PPacket(long pointer, ByteBuffer dest, int offset, int size,
												int[] msgSizeInBytes, long[] steamIDRemote, int channel); /*

		ISteamNetworking* net = (ISteamNetworking*) pointer;
		CSteamID remote;
		if (net->ReadP2PPacket(&dest[offset], size, (uint32*) msgSizeInBytes, &remote, channel)) {
			steamIDRemote[0] = remote.ConvertToUint64();
			return true;
		}
		return false;
	*/

	private static native boolean acceptP2PSessionWithUser(long pointer, long steamIDRemote); /*
		ISteamNetworking* net = (ISteamNetworking*) pointer;
		return net->AcceptP2PSessionWithUser((uint64) steamIDRemote);
	*/

	private static native boolean closeP2PSessionWithUser(long pointer, long steamIDRemote); /*
		ISteamNetworking* net = (ISteamNetworking*) pointer;
		return net->CloseP2PSessionWithUser((uint64) steamIDRemote);
	*/

	private static native boolean closeP2PChannelWithUser(long pointer, long steamIDRemote, int channel); /*
		ISteamNetworking* net = (ISteamNetworking*) pointer;
		return net->CloseP2PChannelWithUser((uint64) steamIDRemote, channel);
	*/

	private static native boolean getP2PSessionState(long pointer, long steamIDRemote,
													 P2PSessionState connectionState); /*

		ISteamNetworking* net = (ISteamNetworking*) pointer;
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

	private static native boolean allowP2PPacketRelay(long pointer, boolean allow); /*
		ISteamNetworking* net = (ISteamNetworking*) pointer;
		return net->AllowP2PPacketRelay(allow);
	*/

}
