package com.codedisaster.steamworks;

public abstract class SteamMatchmakingServerListResponse extends SteamInterface {

	public enum Response {
		ServerResponded,
		ServerFailedToRespond,
		NoServersListedOnMasterServer;

		private static final Response[] values = values();

		static Response byOrdinal(int ordinal) {
			return values[ordinal];
		}
	}

	protected SteamMatchmakingServerListResponse() {
		super(~0L);
		callback = createProxy(this);
	}

	public abstract void serverResponded(SteamServerListRequest request, int server);

	void serverResponded(long request, int server) {
		serverResponded(new SteamServerListRequest(request), server);
	}

	public abstract void serverFailedToRespond(SteamServerListRequest request, int server);

	void serverFailedToRespond(long request, int server) {
		serverFailedToRespond(new SteamServerListRequest(request), server);
	}

	public abstract void refreshComplete(SteamServerListRequest request, Response response);

	void refreshComplete(long request, int response) {
		refreshComplete(new SteamServerListRequest(request), Response.byOrdinal(response));
	}

	// @off

	/*JNI
		#include "SteamMatchmakingServerListResponse.h"
	*/

	private static native long createProxy(SteamMatchmakingServerListResponse javaCallback); /*
		return (intp) new SteamMatchmakingServerListResponse(env, javaCallback);
	*/

}
