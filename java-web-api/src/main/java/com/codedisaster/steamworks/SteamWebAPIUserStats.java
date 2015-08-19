package com.codedisaster.steamworks;

import com.eclipsesource.json.*;

import java.nio.ByteBuffer;
import java.util.*;

public class SteamWebAPIUserStats {

	public static class AchievementPercentages {

		private List<Achievement> achievements;

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

		private String name;
		private double percent;

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

	private class Callback implements SteamHTTPCallback {

		private SteamWebAPIUserStatsCallback userCallback;

		private ByteBuffer requestBodyData;
		private byte[] requestBodyArray;

		private Callback(SteamWebAPIUserStatsCallback userCallback) {
			this.userCallback = userCallback;
		}

		@Override
		public void onHTTPRequestCompleted(SteamHTTPRequestHandle request,
										   long contextValue,
										   boolean requestSuccessful,
										   SteamHTTP.HTTPStatusCode statusCode,
										   int bodySize) {

			if (!requestSuccessful) {
				// todo: error callback
				http.releaseHTTPRequest(request);
				return;
			}

			// resize request buffers, if needed
			if (requestBodyData == null || requestBodyData.capacity() < bodySize) {
				requestBodyData = ByteBuffer.allocateDirect(bodySize);
				requestBodyArray = new byte[bodySize];
			}

			requestBodyData.clear();

			try {

				if (http.getHTTPResponseBodyData(request, requestBodyData)) {

					requestBodyData.get(requestBodyArray);

					JsonValue json = Json.parse(new String(requestBodyArray));
					JsonObject jsonObject = json.asObject();

					String jsonObjectName = jsonObject.names().get(0);

					if (jsonObjectName.equals("achievementpercentages")) {

						AchievementPercentages percentages = new AchievementPercentages(jsonObject);
						userCallback.onGlobalAchievementPercentagesForApp(percentages);

					} else {
						// todo: unknown result
					}

				} else {
					// todo: error callback
				}

				http.releaseHTTPRequest(request);

			} catch (SteamException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void onHTTPRequestHeadersReceived(SteamHTTPRequestHandle request, long contextValue) {

		}

		@Override
		public void onHTTPRequestDataReceived(SteamHTTPRequestHandle request, long contextValue,
											  int offset, int bytesReceived) {

		}
	}

	private SteamHTTP http;

	public SteamWebAPIUserStats(SteamWebAPIUserStatsCallback callback, SteamHTTP.API api) {
		SteamHTTPCallback httpCallback = new Callback(callback);
		http = new SteamHTTP(httpCallback, api);
	}

	public void dispose() {
		http.dispose();
	}

	public boolean getGlobalAchievementPercentagesForApp(long gameId) {

		SteamHTTPRequestHandle request = http.createHTTPRequest(SteamHTTP.HTTPMethod.GET,
				"https://api.steampowered.com/ISteamUserStats/GetGlobalAchievementPercentagesForApp/v0002/?format=json");

		http.setHTTPRequestGetOrPostParameter(request, "gameid", Long.toString(gameId));

		SteamAPICall call = http.sendHTTPRequest(request);

		if (!call.isValid()) {
			http.releaseHTTPRequest(request);
		}

		return call.isValid();
	}

}
