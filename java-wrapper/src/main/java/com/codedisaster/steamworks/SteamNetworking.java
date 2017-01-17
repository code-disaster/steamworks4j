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

	private final int[] tmpIntResult = new int[1];
	private final long[] tmpLongResult = new long[1];

	public SteamNetworking(SteamNetworkingCallback callback, API api) {
		super(api == API.Client ? SteamAPI.getSteamNetworkingPointer()
				: SteamGameServerAPI.getSteamGameServerNetworkingPointer(),
				createCallback(new SteamNetworkingCallbackAdapter(callback), api == API.Client));
	}

	/**
	 * Sends packet data from a direct {@link ByteBuffer}.
	 *
	 * The packet data sent ranges from <code>ByteBuffer.position()</code> to <code>ByteBuffer.limit()</code>.
	 */
	public boolean sendP2PPacket(SteamID steamIDRemote, ByteBuffer data,
								 P2PSend sendType, int channel) throws SteamException {

		int offset = data.position();
		int size = data.limit() - offset;
		return sendP2PPacket(steamIDRemote, data, offset, size, sendType, channel);
	}

	/**
	 * Sends packet data from a direct {@link ByteBuffer}.
	 *
	 * This function ignores the buffer's internal position and limit properties.
	 */
	public boolean sendP2PPacket(SteamID steamIDRemote, ByteBuffer data, int offset, int size,
								 P2PSend sendType, int channel) throws SteamException {

		if (!data.isDirect()) {
			throw new SteamException("Direct buffer required!");
		}

		if (data.capacity() < offset + size) {
			throw new SteamException("Buffer capacity exceeded!");
		}

		return sendP2PPacket(pointer, steamIDRemote.handle, data, offset, size, sendType.ordinal(), channel);
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
	 * The packet data is stored starting at <code>ByteBuffer.position()</code>, up to <code>ByteBuffer.limit()</code>.
	 * On return, the buffer limit is set to <code>ByteBuffer.position()</code> plus the number of bytes received.
	 *
	 * On success, returns the number of bytes received, and the <code>steamIDRemote</code> parameter contains the
	 * sender's ID.
	 */
	public int readP2PPacket(SteamID steamIDRemote, ByteBuffer dest, int channel) throws SteamException {
		int offset = dest.position();
		int capacity = dest.limit() - offset;
		return readP2PPacket(steamIDRemote, dest, offset, capacity, channel);
	}

	/**
	 * Read incoming packet data into a direct {@link ByteBuffer}.
	 *
	 * This function ignores the buffer's internal position and limit properties. On return, the buffer position is set
	 * to <code>offset</code>, the buffer limit is set to <code>offset</code> plus the number of bytes received.
	 *
	 * On success, returns the number of bytes received, and the <code>steamIDRemote</code> parameter contains the
	 * sender's ID.
	 */
	public int readP2PPacket(SteamID steamIDRemote, ByteBuffer dest,
							 int offset, int capacity, int channel) throws SteamException {

		if (!dest.isDirect()) {
			throw new SteamException("Direct buffer required!");
		}

		if (dest.capacity() < offset + capacity) {
			throw new SteamException("Buffer capacity exceeded!");
		}

		if (readP2PPacket(pointer, dest, offset, capacity, tmpIntResult, tmpLongResult, channel)) {
			dest.position(offset);
			dest.limit(offset + tmpIntResult[0]);
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

	private static native boolean readP2PPacket(long pointer, ByteBuffer dest, int offset, int capacity,
												int[] msgSizeInBytes, long[] steamIDRemote, int channel); /*

		ISteamNetworking* net = (ISteamNetworking*) pointer;
		CSteamID remote;
		if (net->ReadP2PPacket(&dest[offset], capacity, (uint32*) msgSizeInBytes, &remote, channel)) {
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

	// [@code-disaster] note: removed GetP2PSessionState(), won't work that way

	private static native boolean allowP2PPacketRelay(long pointer, boolean allow); /*
		ISteamNetworking* net = (ISteamNetworking*) pointer;
		return net->AllowP2PPacketRelay(allow);
	*/

}
