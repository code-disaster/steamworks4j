package com.codedisaster.steamworks;

@SuppressWarnings("unused")
class SteamUserStatsCallbackAdapter extends SteamCallbackAdapter<SteamUserStatsCallback> {

	SteamUserStatsCallbackAdapter(SteamUserStatsCallback callback) {
		super(callback);
	}

	public void onUserStatsReceived(long gameId, long steamIDUser, int result) {
		callback.onUserStatsReceived(gameId, new SteamID(steamIDUser), SteamResult.byValue(result));
	}

	public void onUserStatsStored(long gameId, int result) {
		callback.onUserStatsStored(gameId, SteamResult.byValue(result));
	}

	public void onUserStatsUnloaded(long steamIDUser) {
		callback.onUserStatsUnloaded(new SteamID(steamIDUser));
	}

	public void onUserAchievementStored(long gameId, boolean isGroupAchievement, String achievementName,
										int curProgress, int maxProgress) {
		callback.onUserAchievementStored(gameId, isGroupAchievement, achievementName, curProgress, maxProgress);
	}

	public void onLeaderboardFindResult(long handle, boolean found) {
		callback.onLeaderboardFindResult(new SteamLeaderboardHandle(handle), found);
	}

	public void onLeaderboardScoresDownloaded(long handle, long entries, int numEntries) {
		callback.onLeaderboardScoresDownloaded(new SteamLeaderboardHandle(handle),
				new SteamLeaderboardEntriesHandle(entries), numEntries);
	}

	public void onLeaderboardScoreUploaded(boolean success, long handle, int score, boolean changed,
										   int globalRankNew, int globalRankPrevious) {
		callback.onLeaderboardScoreUploaded(success, new SteamLeaderboardHandle(handle), score, changed,
				globalRankNew, globalRankPrevious);
	}

}
