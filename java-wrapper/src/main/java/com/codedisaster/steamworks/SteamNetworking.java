package com.codedisaster.steamworks;

import java.nio.Buffer;

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

	public SteamNetworking(long pointer, SteamNetworkingCallback callback, API api) {
		super(pointer);
		registerCallback(new SteamNetworkingCallbackAdapter(callback), api == API.Client);
	}

	static void dispose() {
		registerCallback(null, true);
		registerCallback(null, false);
	}

	public boolean sendP2PPacket(SteamID steamIDRemote, Buffer data, P2PSend sendType, int channel) throws SteamException {
		if (!data.isDirect()) {
			throw new SteamException("Direct buffer required!");
		}
		return sendP2PPacket(pointer, steamIDRemote.handle, data, data.limit(), sendType.ordinal(), channel);
	}

	public int isP2PPacketAvailable(int channel) {
		int[] msgSize = new int[1];
		if (isP2PPacketAvailable(pointer, msgSize, channel)) {
			return msgSize[0];
		}
		return 0;
	}

	public SteamID readP2PPacket(Buffer dest, int channel) throws SteamException {
		if (!dest.isDirect()) {
			throw new SteamException("Direct buffer required!");
		}
		int[] msgSizeInBytes = new int[1];
		long[] steamIDRemote = new long[1];
		if (readP2PPacket(pointer, dest, dest.capacity(), msgSizeInBytes, steamIDRemote, channel)) {
			dest.position(0);
			dest.limit(msgSizeInBytes[0]);
			return new SteamID(steamIDRemote[0]);
		}
		return null;
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

		static SteamNetworkingCallback* clientCallback = NULL;
		static SteamGameServerNetworkingCallback* serverCallback = NULL;
	*/

	static private native boolean registerCallback(SteamNetworkingCallbackAdapter javaCallback, boolean isClient); /*
		if (isClient) {
			if (clientCallback != NULL) {
				delete clientCallback;
				clientCallback = NULL;
			}

			if (javaCallback != NULL) {
				clientCallback = new SteamNetworkingCallback(env, javaCallback);
			}

			return clientCallback != NULL;
		} else {
			if (serverCallback != NULL) {
				delete serverCallback;
				serverCallback = NULL;
			}

			if (javaCallback != NULL) {
				serverCallback = new SteamGameServerNetworkingCallback(env, javaCallback);
			}

			return serverCallback != NULL;
		}

	*/

	static private native boolean sendP2PPacket(long pointer, long steamIDRemote, Buffer data, int sizeInBytes, int sendType, int channel); /*
		ISteamNetworking* net = (ISteamNetworking*) pointer;
		return net->SendP2PPacket((uint64) steamIDRemote, data, sizeInBytes, (EP2PSend) sendType, channel);
	*/

	static private native boolean isP2PPacketAvailable(long pointer, int[] msgSize, int channel); /*
		ISteamNetworking* net = (ISteamNetworking*) pointer;
		return net->IsP2PPacketAvailable((uint32 *)msgSize, channel);
	*/

	static private native boolean readP2PPacket(long pointer, Buffer dest, int capacity, int[] msgSizeInBytes, long[] steamIDRemote, int channel); /*
		ISteamNetworking* net = (ISteamNetworking*) pointer;
		CSteamID remote;
		if (net->ReadP2PPacket(dest, capacity, (uint32*) msgSizeInBytes, &remote, channel)) {
			steamIDRemote[0] = remote.ConvertToUint64();
			return true;
		}
		return false;
	*/

	static private native boolean acceptP2PSessionWithUser(long pointer, long steamIDRemote); /*
		ISteamNetworking* net = (ISteamNetworking*) pointer;
		return net->AcceptP2PSessionWithUser((uint64) steamIDRemote);
	*/

	static private native boolean closeP2PSessionWithUser(long pointer, long steamIDRemote); /*
		ISteamNetworking* net = (ISteamNetworking*) pointer;
		return net->CloseP2PSessionWithUser((uint64) steamIDRemote);
	*/

	static private native boolean closeP2PChannelWithUser(long pointer, long steamIDRemote, int channel); /*
		ISteamNetworking* net = (ISteamNetworking*) pointer;
		return net->CloseP2PChannelWithUser((uint64) steamIDRemote, channel);
	*/

	// [@code-disaster] note: removed GetP2PSessionState(), won't work that way

	static private native boolean allowP2PPacketRelay(long pointer, boolean allow); /*
		ISteamNetworking* net = (ISteamNetworking*) pointer;
		return net->AllowP2PPacketRelay(allow);
	*/

}
