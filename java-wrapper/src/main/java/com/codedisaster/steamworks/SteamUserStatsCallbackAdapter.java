package com.codedisaster.steamworks;

@SuppressWarnings("unused")
class SteamUserStatsCallbackAdapter extends SteamCallbackAdapter<SteamUserStatsCallback> {

	SteamUserStatsCallbackAdapter(SteamUserStatsCallback callback) {
		super(callback);
	}

	void onUserStatsReceived(long gameId, long steamIDUser, int result) {
		callback.onUserStatsReceived(gameId, new SteamID(steamIDUser), SteamResult.byValue(result));
	}

	void onUserStatsStored(long gameId, int result) {
		callback.onUserStatsStored(gameId, SteamResult.byValue(result));
	}

	void onUserStatsUnloaded(long steamIDUser) {
		callback.onUserStatsUnloaded(new SteamID(steamIDUser));
	}

	void onUserAchievementStored(long gameId, boolean isGroupAchievement, String achievementName,
								 int curProgress, int maxProgress) {
		callback.onUserAchievementStored(gameId, isGroupAchievement, achievementName, curProgress, maxProgress);
	}

	void onLeaderboardFindResult(long handle, boolean found) {
		callback.onLeaderboardFindResult(new SteamLeaderboardHandle(handle), found);
	}

	void onLeaderboardScoresDownloaded(long handle, long entries, int numEntries) {
		callback.onLeaderboardScoresDownloaded(new SteamLeaderboardHandle(handle),
				new SteamLeaderboardEntriesHandle(entries), numEntries);
	}

	void onLeaderboardScoreUploaded(boolean success, long handle, int score, boolean changed,
									int globalRankNew, int globalRankPrevious) {
		callback.onLeaderboardScoreUploaded(success, new SteamLeaderboardHandle(handle), score, changed,
				globalRankNew, globalRankPrevious);
	}

	void onGlobalStatsReceived(long gameId, int result) {
		callback.onGlobalStatsReceived(gameId, SteamResult.byValue(result));
	}

}
