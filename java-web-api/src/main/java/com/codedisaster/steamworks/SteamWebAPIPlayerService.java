package com.codedisaster.steamworks;

import com.eclipsesource.json.*;

import java.util.*;

public class SteamWebAPIPlayerService extends SteamWebAPIInterface {

	public static class RecentlyPlayedGames {

		private final int totalCount;
		private final List<RecentlyPlayedGame> games;

		private RecentlyPlayedGames(JsonObject json) {

			JsonObject response = json.get("response").asObject();

			totalCount = response.getInt("total_count", 0);

			JsonArray games = response.get("games").asArray();

			this.games = new ArrayList<RecentlyPlayedGame>(games.size());

			for (JsonValue game : games) {
				this.games.add(new RecentlyPlayedGame(game.asObject()));
			}
		}

		public int getTotalCount() {
			return totalCount;
		}

		public Collection<RecentlyPlayedGame> getGames() {
			return games;
		}
	}

	public static class RecentlyPlayedGame {

		private final long appId;
		private final String name;
		private final int playtimeTwoWeeks;
		private final int playtimeForever;
		private final String imageIconURL;
		private final String imageLogoURL;

		private RecentlyPlayedGame(JsonObject json) {
			appId = json.get("appid").asLong();
			name = json.get("name").asString();
			playtimeTwoWeeks = json.get("playtime_2weeks").asInt();
			playtimeForever = json.get("playtime_forever").asInt();
			imageIconURL = json.get("img_icon_url").asString();
			imageLogoURL = json.get("img_logo_url").asString();
		}

		public long getAppId() {
			return appId;
		}

		public String getName() {
			return name;
		}

		public int getPlaytimeTwoWeeks() {
			return playtimeTwoWeeks;
		}

		public int getPlaytimeForever() {
			return playtimeForever;
		}

		public String getImageIconURL() {
			return imageIconURL;
		}

		public String getImageLogoURL() {
			return imageLogoURL;
		}
	}

	private class RequestListener implements RequestCallback {

		private SteamWebAPIPlayerServiceCallback userCallback;

		private RequestListener(SteamWebAPIPlayerServiceCallback userCallback) {
			this.userCallback = userCallback;
		}

		@Override
		public void onHTTPRequestCompleted(JsonObject jsonObject, long context,
										   SteamHTTP.HTTPStatusCode statusCode) {

			if (context == GET_RECENTLY_PLAYED_GAMES) {

				RecentlyPlayedGames games = new RecentlyPlayedGames(jsonObject);
				userCallback.onRecentlyPlayedGames(games);

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

	private final static long GET_RECENTLY_PLAYED_GAMES = 1;

	public SteamWebAPIPlayerService(SteamWebAPIPlayerServiceCallback callback, SteamHTTP.API api) {
		createHTTPInterface("IPlayerService", new RequestListener(callback), api);
	}

	public boolean getRecentlyPlayedGames(long steamId, int count) {

		SteamHTTPRequestHandle request = createHTTPRequest(SteamHTTP.HTTPMethod.GET,
				"GetRecentlyPlayedGames", 1, GET_RECENTLY_PLAYED_GAMES);

		http.setHTTPRequestGetOrPostParameter(request, "steamid", Long.toString(steamId));
		http.setHTTPRequestGetOrPostParameter(request, "count", Long.toString(count));

		return sendHTTPRequest(request);
	}

}
