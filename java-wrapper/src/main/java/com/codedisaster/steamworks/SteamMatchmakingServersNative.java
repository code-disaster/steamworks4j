package com.codedisaster.steamworks;

final class SteamMatchmakingServersNative {

	// @off

	/*JNI
		#include <steam_api.h>
		#include "SteamMatchmakingGameServerItem.h"
		#include "SteamMatchmakingKeyValuePair.h"
		#include "SteamMatchmakingPingResponse.h"
		#include "SteamMatchmakingPlayersResponse.h"
		#include "SteamMatchmakingRulesResponse.h"
		#include "SteamMatchmakingServerListResponse.h"

		#define MAX_FILTERS 16
	*/

	static native long requestInternetServerList(int appID,
												 SteamMatchmakingKeyValuePair[] filters, int count,
												 long requestServersResponse); /*

		MatchMakingKeyValuePair_t _pairs[MAX_FILTERS];
		MatchMakingKeyValuePair_t* pairs = _pairs;
		count = convertKeyValuePairArray(env, filters, count, pairs, MAX_FILTERS);

		SteamMatchmakingServerListResponse* response = (SteamMatchmakingServerListResponse*) requestServersResponse;
		return (jlong) SteamMatchmakingServers()->RequestInternetServerList(appID, &pairs, count, response);
	*/

	static native long requestLANServerList(int appID,
											long requestServersResponse); /*

		SteamMatchmakingServerListResponse* response = (SteamMatchmakingServerListResponse*) requestServersResponse;
		return (jlong) SteamMatchmakingServers()->RequestLANServerList(appID, response);
	*/

	static native long requestFriendsServerList(int appID,
												SteamMatchmakingKeyValuePair[] filters, int count,
												long requestServersResponse); /*

		MatchMakingKeyValuePair_t _pairs[MAX_FILTERS];
		MatchMakingKeyValuePair_t* pairs = _pairs;
		count = convertKeyValuePairArray(env, filters, count, pairs, MAX_FILTERS);

		SteamMatchmakingServerListResponse* response = (SteamMatchmakingServerListResponse*) requestServersResponse;
		return (jlong) SteamMatchmakingServers()->RequestFriendsServerList(appID, &pairs, count, response);
	*/

	static native long requestFavoritesServerList(int appID,
												  SteamMatchmakingKeyValuePair[] filters, int count,
												  long requestServersResponse); /*

		MatchMakingKeyValuePair_t _pairs[MAX_FILTERS];
		MatchMakingKeyValuePair_t* pairs = _pairs;
		count = convertKeyValuePairArray(env, filters, count, pairs, MAX_FILTERS);

		SteamMatchmakingServerListResponse* response = (SteamMatchmakingServerListResponse*) requestServersResponse;
		return (jlong) SteamMatchmakingServers()->RequestFavoritesServerList(appID, &pairs, count, response);
	*/

	static native long requestHistoryServerList(int appID,
												SteamMatchmakingKeyValuePair[] filters, int count,
												long requestServersResponse); /*

		MatchMakingKeyValuePair_t _pairs[MAX_FILTERS];
		MatchMakingKeyValuePair_t* pairs = _pairs;
		count = convertKeyValuePairArray(env, filters, count, pairs, MAX_FILTERS);

		SteamMatchmakingServerListResponse* response = (SteamMatchmakingServerListResponse*) requestServersResponse;
		return (jlong) SteamMatchmakingServers()->RequestHistoryServerList(appID, &pairs, count, response);
	*/

	static native long requestSpectatorServerList(int appID,
												  SteamMatchmakingKeyValuePair[] filters, int count,
												  long requestServersResponse); /*

		MatchMakingKeyValuePair_t _pairs[MAX_FILTERS];
		MatchMakingKeyValuePair_t* pairs = _pairs;
		count = convertKeyValuePairArray(env, filters, count, pairs, MAX_FILTERS);

		SteamMatchmakingServerListResponse* response = (SteamMatchmakingServerListResponse*) requestServersResponse;
		return (jlong) SteamMatchmakingServers()->RequestSpectatorServerList(appID, &pairs, count, response);
	*/

	static native void releaseRequest(long request); /*
		SteamMatchmakingServers()->ReleaseRequest((HServerListRequest) request);
	*/

	static native boolean getServerDetails(long request, int server,
										   SteamMatchmakingGameServerItem details); /*
		const gameserveritem_t* item = SteamMatchmakingServers()->GetServerDetails((HServerListRequest) request, server);
		if (item != nullptr) {
			convertGameServerItem(details, env, *item);
			return true;
		}
		return false;
	*/

	static native void cancelQuery(long request); /*
		SteamMatchmakingServers()->CancelQuery((HServerListRequest) request);
	*/

	static native void refreshQuery(long request); /*
		SteamMatchmakingServers()->RefreshQuery((HServerListRequest) request);
	*/

	static native boolean isRefreshing(long request); /*
		return SteamMatchmakingServers()->IsRefreshing((HServerListRequest) request);
	*/

	static native int getServerCount(long request); /*
		return SteamMatchmakingServers()->GetServerCount((HServerListRequest) request);
	*/

	static native void refreshServer(long request, int server); /*
		SteamMatchmakingServers()->RefreshServer((HServerListRequest) request, server);
	*/

	static native int pingServer(int ip, short port, long requestServersResponse); /*
		SteamMatchmakingPingResponse* response = (SteamMatchmakingPingResponse*) requestServersResponse;
		return SteamMatchmakingServers()->PingServer(ip, port, response);
	*/

	static native int playerDetails(int ip, short port, long requestServersResponse); /*
		SteamMatchmakingPlayersResponse* response = (SteamMatchmakingPlayersResponse*) requestServersResponse;
		return SteamMatchmakingServers()->PlayerDetails(ip, port, response);
	*/

	static native int serverRules(int ip, short port, long requestServersResponse); /*
		SteamMatchmakingRulesResponse* response = (SteamMatchmakingRulesResponse*) requestServersResponse;
		return SteamMatchmakingServers()->ServerRules(ip, port, response);
	*/

	static native void cancelServerQuery(int serverQuery); /*
		return SteamMatchmakingServers()->CancelServerQuery((HServerQuery) serverQuery);
	*/

}
