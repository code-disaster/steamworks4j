package com.codedisaster.steamworks.test;

import com.codedisaster.steamworks.*;
import com.codedisaster.steamworks.SteamHTTP;
import com.codedisaster.steamworks.SteamWebAPIUserStats;

import java.util.Collection;

public class SteamWebAPITestApp extends SteamTestApp {

	private SteamUser clientUser;

	private SteamWebAPIPlayerService playerService;
	private SteamWebAPIUserStats userStats;

	private SteamUserCallback clientUserCallback = new SteamUserCallback() {
		@Override
		public void onValidateAuthTicketResponse(SteamID steamID, SteamAuth.AuthSessionResponse authSessionResponse, SteamID ownerSteamID) {

		}
	};

	private SteamWebAPIPlayerServiceCallback playerServiceCallback = new SteamWebAPIPlayerServiceCallback() {
		@Override
		public void onRecentlyPlayedGames(SteamWebAPIPlayerService.RecentlyPlayedGames result) {

			Collection<SteamWebAPIPlayerService.RecentlyPlayedGame> games = result.getGames();
			System.out.println("Recently played games received: " + games.size() + " of " + result.getTotalCount());

			for (SteamWebAPIPlayerService.RecentlyPlayedGame game : games) {
				System.out.println(String.format("- '%s', %.1fh in 2 weeks, %.1fh total",
						game.getName(),
						game.getPlaytimeTwoWeeks() / 60.0f,
						game.getPlaytimeForever() / 60.0f));
			}

		}

		@Override
		public void onWebAPIRequestFailed(String interfaceName, String errorMessage, SteamHTTP.HTTPStatusCode statusCode) {
			System.out.println("Request failed: '" + interfaceName + "', status code " + statusCode.name() + "\n" + errorMessage);
		}
	};

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

		@Override
		public void onWebAPIRequestFailed(String interfaceName, String errorMessage, SteamHTTP.HTTPStatusCode statusCode) {
			System.out.println("Request failed: '" + interfaceName + "', status code " + statusCode.name() + "\n" + errorMessage);
		}
	};

	@Override
	protected void registerInterfaces() {

		System.out.println("Register user ...");
		clientUser = new SteamUser(clientUserCallback);

		System.out.println("Register player service (web) ...");
		playerService = new SteamWebAPIPlayerService(playerServiceCallback, SteamHTTP.API.Client);

		System.out.println("Register user stats (web) ...");
		userStats = new SteamWebAPIUserStats(userStatsCallback, SteamHTTP.API.Client);
	}

	@Override
	protected void unregisterInterfaces() {
		clientUser.dispose();
		playerService.dispose();
		userStats.dispose();
	}

	@Override
	protected void processUpdate() throws SteamException {

	}

	@Override
	protected void processInput(String input) throws SteamException {

		if (input.startsWith("web key ")) {
			String key = input.substring("web key ".length());
			playerService.setWebAPIKey(key);
			userStats.setWebAPIKey(key);
		}

		if (input.startsWith("player recent")) {
			if (!playerService.getRecentlyPlayedGames(clientUser.getSteamID().getNativeHandle(), 0)) {
				System.out.println("getRecentlyPlayedGames(): send request failed.");
			}
		}

		if (input.startsWith("web achievements")) {
			if (!userStats.getGlobalAchievementPercentagesForApp(clientUtils.getAppID())) {
				System.out.println("getGlobalAchievementPercentagesForApp(): send request failed.");
			}
		}
	}

	public static void main(String[] arguments) {
		new SteamWebAPITestApp().clientMain(arguments);
	}

}
