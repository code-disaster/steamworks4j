package com.codedisaster.steamworks.test;

import com.codedisaster.steamworks.*;
import com.codedisaster.steamworks.SteamHTTP;
import com.codedisaster.steamworks.SteamWebAPIUserStats;

import java.util.Collection;

public class SteamWebAPITestApp extends SteamTestApp {

	private SteamWebAPIUserStats userStats;

	private SteamWebAPIUserStatsCallback userStatsCallback = new SteamWebAPIUserStatsCallback() {
		@Override
		public void onGlobalAchievementPercentagesForApp(SteamWebAPIUserStats.AchievementPercentages result) {

			Collection<SteamWebAPIUserStats.Achievement> achievements = result.getAchievements();
			System.out.println("Global achievement percentages received: " + achievements.size());

			for (SteamWebAPIUserStats.Achievement achievement : achievements) {
				System.out.println("- '" + achievement.getName() + "', " +
						((int) achievement.getPercent()) + "%");
			}

		}
	};

	@Override
	protected void registerInterfaces() {

		System.out.println("Register user stats (web) ...");
		userStats = new SteamWebAPIUserStats(userStatsCallback, SteamHTTP.API.Client);
	}

	@Override
	protected void unregisterInterfaces() {
		userStats.dispose();
	}

	@Override
	protected void processUpdate() throws SteamException {

	}

	@Override
	protected void processInput(String input) throws SteamException {

		if (input.startsWith("web achievements")) {
			if (!userStats.getGlobalAchievementPercentagesForApp(clientUtils.getAppID())) {
				System.out.println("http achievements: send request failed.");
			}
		}
	}

	public static void main(String[] arguments) {
		new SteamWebAPITestApp().clientMain(arguments);
	}

}
