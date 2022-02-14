package com.codedisaster.steamworks;

import java.nio.ByteBuffer;

@SuppressWarnings({ "unused", "UnusedReturnValue" })
public class SteamNetworking extends SteamInterface {

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

	private final boolean isServer;
	private final int[] tmpIntResult = new int[1];
	private final long[] tmpLongResult = new long[1];

	public SteamNetworking(SteamNetworkingCallback callback) {
		this(false, SteamNetworkingNative.createCallback(new SteamNetworkingCallbackAdapter(callback)));
	}

	SteamNetworking(boolean isServer, long callback) {
		super(callback);
		this.isServer = isServer;
	}

	public boolean sendP2PPacket(SteamID steamIDRemote, ByteBuffer data,
								 P2PSend sendType, int channel) throws SteamException {

		if (!data.isDirect()) {
			throw new SteamException("Direct buffer required!");
		}

		return SteamNetworkingNative.sendP2PPacket(isServer, steamIDRemote.handle, data,
				data.position(), data.remaining(), sendType.ordinal(), channel);
	}

	public boolean isP2PPacketAvailable(int channel, int[] msgSize) {
		return SteamNetworkingNative.isP2PPacketAvailable(isServer, msgSize, channel);
	}

	/**
	 * Read incoming packet data into a direct {@link ByteBuffer}.
	 * <p>
	 * On success, returns the number of bytes received, and the <code>steamIDRemote</code> parameter contains the
	 * sender's ID.
	 */
	public int readP2PPacket(SteamID steamIDRemote, ByteBuffer dest, int channel) throws SteamException {

		if (!dest.isDirect()) {
			throw new SteamException("Direct buffer required!");
		}

		if (SteamNetworkingNative.readP2PPacket(isServer, dest, dest.position(), dest.remaining(), tmpIntResult, tmpLongResult, channel)) {
			steamIDRemote.handle = tmpLongResult[0];
			return tmpIntResult[0];
		}

		return 0;
	}

	public boolean acceptP2PSessionWithUser(SteamID steamIDRemote) {
		return SteamNetworkingNative.acceptP2PSessionWithUser(isServer, steamIDRemote.handle);
	}

	public boolean closeP2PSessionWithUser(SteamID steamIDRemote) {
		return SteamNetworkingNative.closeP2PSessionWithUser(isServer, steamIDRemote.handle);
	}

	public boolean closeP2PChannelWithUser(SteamID steamIDRemote, int channel) {
		return SteamNetworkingNative.closeP2PChannelWithUser(isServer, steamIDRemote.handle, channel);
	}

	public boolean getP2PSessionState(SteamID steamIDRemote, P2PSessionState connectionState) {
		return SteamNetworkingNative.getP2PSessionState(isServer, steamIDRemote.handle, connectionState);
	}

	public boolean allowP2PPacketRelay(boolean allow) {
		return SteamNetworkingNative.allowP2PPacketRelay(isServer, allow);
	}

}
