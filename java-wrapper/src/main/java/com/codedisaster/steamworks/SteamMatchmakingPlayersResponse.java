package com.codedisaster.steamworks;

public abstract class SteamMatchmakingPlayersResponse extends SteamInterface {

	protected SteamMatchmakingPlayersResponse() {
		super(~0L);
		callback = createProxy(this);
	}

	public abstract void addPlayerToList(String name, int score, float timePlayed);

	public abstract void playersFailedToRespond();

	public abstract void playersRefreshComplete();

	// @off

	/*JNI
		#include "SteamMatchmakingPlayersResponse.h"
	*/

	private static native long createProxy(SteamMatchmakingPlayersResponse javaCallback); /*
		return (intp) new SteamMatchmakingPlayersResponse(env, javaCallback);
	*/

}
