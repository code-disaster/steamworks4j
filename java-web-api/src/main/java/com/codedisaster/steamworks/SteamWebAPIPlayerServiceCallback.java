package com.codedisaster.steamworks;

public interface SteamWebAPIPlayerServiceCallback extends SteamWebAPIInterfaceCallback {

	void onRecentlyPlayedGames(SteamWebAPIPlayerService.RecentlyPlayedGames result);

}
