package com.codedisaster.steamworks;

@SuppressWarnings("unused")
public class SteamMatchmakingServers extends SteamInterface {

	public SteamMatchmakingServers() {
		super(-1);
	}

	public SteamServerListRequest requestInternetServerList(int appID, SteamMatchmakingKeyValuePair[] filters,
															SteamMatchmakingServerListResponse requestServersResponse) {

		return new SteamServerListRequest(SteamMatchmakingServersNative.requestInternetServerList(appID,
				filters, filters.length, requestServersResponse.callback));
	}

	public SteamServerListRequest requestLANServerList(int appID,
													   SteamMatchmakingServerListResponse requestServersResponse) {

		return new SteamServerListRequest(SteamMatchmakingServersNative.requestLANServerList(appID, requestServersResponse.callback));
	}

	public SteamServerListRequest requestFriendsServerList(int appID, SteamMatchmakingKeyValuePair[] filters,
														   SteamMatchmakingServerListResponse requestServersResponse) {

		return new SteamServerListRequest(SteamMatchmakingServersNative.requestFriendsServerList(appID,
				filters, filters.length, requestServersResponse.callback));
	}

	public SteamServerListRequest requestFavoritesServerList(int appID, SteamMatchmakingKeyValuePair[] filters,
															 SteamMatchmakingServerListResponse requestServersResponse) {

		return new SteamServerListRequest(SteamMatchmakingServersNative.requestFavoritesServerList(appID,
				filters, filters.length, requestServersResponse.callback));
	}

	public SteamServerListRequest requestHistoryServerList(int appID, SteamMatchmakingKeyValuePair[] filters,
														   SteamMatchmakingServerListResponse requestServersResponse) {

		return new SteamServerListRequest(SteamMatchmakingServersNative.requestHistoryServerList(appID,
				filters, filters.length, requestServersResponse.callback));
	}

	public SteamServerListRequest requestSpectatorServerList(int appID, SteamMatchmakingKeyValuePair[] filters,
															 SteamMatchmakingServerListResponse requestServersResponse) {

		return new SteamServerListRequest(SteamMatchmakingServersNative.requestSpectatorServerList(appID,
				filters, filters.length, requestServersResponse.callback));
	}

	public void releaseRequest(SteamServerListRequest request) {
		SteamMatchmakingServersNative.releaseRequest(request.handle);
	}

	public boolean getServerDetails(SteamServerListRequest request, int server, SteamMatchmakingGameServerItem details) {
		return SteamMatchmakingServersNative.getServerDetails(request.handle, server, details);
	}

	public void cancelQuery(SteamServerListRequest request) {
		SteamMatchmakingServersNative.cancelQuery(request.handle);
	}

	public void refreshQuery(SteamServerListRequest request) {
		SteamMatchmakingServersNative.refreshQuery(request.handle);
	}

	public boolean isRefreshing(SteamServerListRequest request) {
		return SteamMatchmakingServersNative.isRefreshing(request.handle);
	}

	public int getServerCount(SteamServerListRequest request) {
		return SteamMatchmakingServersNative.getServerCount(request.handle);
	}

	public void refreshServer(SteamServerListRequest request, int server) {
		SteamMatchmakingServersNative.refreshServer(request.handle, server);
	}

	public SteamServerQuery pingServer(int ip, short port, SteamMatchmakingPingResponse requestServersResponse) {
		return new SteamServerQuery(SteamMatchmakingServersNative.pingServer(ip, port, requestServersResponse.callback));
	}

	public SteamServerQuery playerDetails(int ip, short port, SteamMatchmakingPlayersResponse requestServersResponse) {
		return new SteamServerQuery(SteamMatchmakingServersNative.playerDetails(ip, port, requestServersResponse.callback));
	}

	public SteamServerQuery serverRules(int ip, short port, SteamMatchmakingRulesResponse requestServersResponse) {
		return new SteamServerQuery(SteamMatchmakingServersNative.serverRules(ip, port, requestServersResponse.callback));
	}

	public void cancelServerQuery(SteamServerQuery serverQuery) {
		SteamMatchmakingServersNative.cancelServerQuery(serverQuery.handle);
	}

}
