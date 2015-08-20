package com.codedisaster.steamworks;

import com.eclipsesource.json.*;

import java.util.*;

public class SteamWebAPIUserStats extends SteamWebAPIInterface {

	public static class AchievementPercentages {

		private final List<Achievement> achievements;

		private AchievementPercentages(JsonObject json) {

			JsonArray achievements = json.get("achievementpercentages").asObject().get("achievements").asArray();

			this.achievements = new ArrayList<Achievement>(achievements.size());

			for (JsonValue achievement : achievements) {
				this.achievements.add(new Achievement(achievement.asObject()));
			}
		}

		public Collection<Achievement> getAchievements() {
			return achievements;
		}
	}

	public static class Achievement {

		private final String name;
		private final double percent;

		private Achievement(JsonObject json) {
			name = json.get("name").asString();
			percent = json.get("percent").asDouble();
		}

		public String getName() {
			return name;
		}

		public double getPercent() {
			return percent;
		}
	}

	private class RequestListener implements RequestCallback {

		private SteamWebAPIUserStatsCallback userCallback;

		private RequestListener(SteamWebAPIUserStatsCallback userCallback) {
			this.userCallback = userCallback;
		}

		@Override
		public void onHTTPRequestCompleted(JsonObject jsonObject, long context,
										   SteamHTTP.HTTPStatusCode statusCode) {

			if (context == GET_GLOBAL_ACHIEVEMENT_PERCENTAGES) {

				AchievementPercentages percentages = new AchievementPercentages(jsonObject);
				userCallback.onGlobalAchievementPercentagesForApp(percentages);

			} else {
				userCallback.onWebAPIRequestFailed(interfaceName,
						"Unknown method context (" + context + ")", statusCode);
			}

		}

		@Override
		public void onHTTPRequestFailed(String responseString, long contextValue,
										SteamHTTP.HTTPStatusCode statusCode) {

			userCallback.onWebAPIRequestFailed(interfaceName, responseString, statusCode);
		}
	}

	private static final long GET_GLOBAL_ACHIEVEMENT_PERCENTAGES = 1;

	public SteamWebAPIUserStats(SteamWebAPIUserStatsCallback callback, SteamHTTP.API api) {
		createHTTPInterface("ISteamUserStats", new RequestListener(callback), api);
	}

	public boolean getGlobalAchievementPercentagesForApp(long gameId) {

		SteamHTTPRequestHandle request = createHTTPRequest(SteamHTTP.HTTPMethod.GET,
				"GetGlobalAchievementPercentagesForApp", 2, GET_GLOBAL_ACHIEVEMENT_PERCENTAGES);

		http.setHTTPRequestGetOrPostParameter(request, "gameid", Long.toString(gameId));

		return sendHTTPRequest(request);
	}

}
