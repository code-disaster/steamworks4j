package com.codedisaster.steamworks.test;

import com.codedisaster.steamworks.*;

import java.nio.ByteBuffer;

public class SteamMatchmakingServerTest extends SteamTestApp {

	private SteamMatchmakingServers servers;
	private SteamServerListRequest serverListRequest;

	private SteamMatchmakingServerListResponse serverListResponse;

	@Override
	protected void registerInterfaces() throws SteamException {
		servers = new SteamMatchmakingServers();

		serverListResponse = new SteamMatchmakingServerListResponse() {
			@Override
			public void serverResponded(SteamServerListRequest request, int server) {
				System.out.println("server responded: " + server);
			}

			@Override
			public void serverFailedToRespond(SteamServerListRequest request, int server) {
				System.out.println("server failed to respond: " + server);
			}

			@Override
			public void refreshComplete(SteamServerListRequest request, Response response) {
				System.out.println("server list refresh complete: " + response.name());
			}
		};
	}

	@Override
	protected void unregisterInterfaces() throws SteamException {
		if (serverListRequest != null && serverListRequest.isValid()) {
			servers.releaseRequest(serverListRequest);
		}
		servers.dispose();
	}

	@Override
	protected void processUpdate() throws SteamException {

	}

	@Override
	protected void processInput(String input) throws SteamException {
		if (input.equals("request")) {
			SteamMatchmakingKeyValuePair[] filters = {
					new SteamMatchmakingKeyValuePair("gamedir", "spacewar"),
					new SteamMatchmakingKeyValuePair("secure", "1")
			};
			serverListRequest = servers.requestInternetServerList(clientUtils.getAppID(), filters, serverListResponse);
			if (!serverListRequest.isValid()) {
				System.err.println("request failed, null return value");
			}
		}
	}

	public static void main(String[] arguments) {
		new SteamMatchmakingServerTest().clientMain(arguments);
	}

}
