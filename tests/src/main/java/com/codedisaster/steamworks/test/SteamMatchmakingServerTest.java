package com.codedisaster.steamworks.test;

import com.codedisaster.steamworks.*;

public class SteamMatchmakingServerTest extends SteamTestApp {

	private SteamMatchmakingServers servers;

	private SteamMatchmakingServerListResponse serverListResponse;

	@Override
	protected void registerInterfaces() throws SteamException {
		servers = new SteamMatchmakingServers();

		serverListResponse = new SteamMatchmakingServerListResponse() {
			@Override
			public void serverResponded(SteamServerListRequest request, int server) {
				SteamMatchmakingGameServerItem serverInfo = new SteamMatchmakingGameServerItem();
				if (servers.getServerDetails(request, server, serverInfo)) {
					printServerInfo(server, serverInfo, "responded");
				} else {
					System.err.println("failed to get server info for #" + server);
				}
			}

			@Override
			public void serverFailedToRespond(SteamServerListRequest request, int server) {
				SteamMatchmakingGameServerItem serverInfo = new SteamMatchmakingGameServerItem();
				if (servers.getServerDetails(request, server, serverInfo)) {
					printServerInfo(server, serverInfo, "failed to respond");
				} else {
					System.err.println("failed to get server info for #" + server);
				}
			}

			@Override
			public void refreshComplete(SteamServerListRequest request, Response response) {
				System.out.println("server list refresh complete: " + response.name());
				servers.releaseRequest(request);
			}

			private void printServerInfo(int server, SteamMatchmakingGameServerItem serverItem, String status) {
				System.out.println("server info for #" + server + " (" + status + ")");
				System.out.println("  name: " + serverItem.getServerName());
			}
		};
	}

	@Override
	protected void unregisterInterfaces() throws SteamException {
		serverListResponse.dispose();
		servers.dispose();
	}

	@Override
	protected void processUpdate() throws SteamException {

	}

	@Override
	protected void processInput(String input) throws SteamException {
		if (input.startsWith("request ")) {
			SteamMatchmakingKeyValuePair[] filters = {
					new SteamMatchmakingKeyValuePair("gamedir", "spacewar"),
					new SteamMatchmakingKeyValuePair("secure", "1")
			};
			SteamServerListRequest serverListRequest;
			String type = input.substring("request ".length());
			if (type.equals("lan")) {
				serverListRequest = servers.requestLANServerList(
						clientUtils.getAppID(), serverListResponse);
			} else if (type.equals("history")) {
				serverListRequest = servers.requestHistoryServerList(
						clientUtils.getAppID(), filters, serverListResponse);
			} else if (type.equals("friends")) {
				serverListRequest = servers.requestFriendsServerList(
						clientUtils.getAppID(), filters, serverListResponse);
			} else {
				serverListRequest = servers.requestInternetServerList(
						clientUtils.getAppID(), filters, serverListResponse);
			}
			if (!serverListRequest.isValid()) {
				System.err.println("request failed, null return value");
			}
		}
	}

	public static void main(String[] arguments) {
		new SteamMatchmakingServerTest().clientMain(arguments);
	}

}
