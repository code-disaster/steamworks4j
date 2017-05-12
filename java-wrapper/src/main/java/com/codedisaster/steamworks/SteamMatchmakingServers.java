package com.codedisaster.steamworks;

public class SteamMatchmakingServers extends SteamInterface {

	public SteamMatchmakingServers() {
		super(SteamAPI.getSteamMatchmakingServersPointer());
	}

	public SteamServerQuery pingServer(int ip, short port, SteamMatchmakingPingResponse requestServersResponse) {
		return new SteamServerQuery(pingServer(pointer, ip, port, requestServersResponse.pointer));
	}

	public void cancelServerQuery(SteamServerQuery serverQuery) {
		cancelServerQuery(pointer, serverQuery.handle);
	}

	// @off

	/*JNI
		#include <steam_api.h>
	*/

	private static native int pingServer(long pointer, int ip, short port, long requestServersResponse); /*
		ISteamMatchmakingServers* servers = (ISteamMatchmakingServers*) pointer;
		ISteamMatchmakingPingResponse* response = (ISteamMatchmakingPingResponse*) requestServersResponse;
		return servers->PingServer(ip, port, response);
	*/

	private static native void cancelServerQuery(long pointer, int serverQuery); /*
		ISteamMatchmakingServers* servers = (ISteamMatchmakingServers*) pointer;
		return servers->CancelServerQuery((HServerQuery) serverQuery);
	*/

}
