package com.codedisaster.steamworks;

public interface SteamWebAPIUserStatsCallback extends SteamWebAPIInterfaceCallback {

	void onGlobalAchievementPercentagesForApp(SteamWebAPIUserStats.AchievementPercentages result);

}
