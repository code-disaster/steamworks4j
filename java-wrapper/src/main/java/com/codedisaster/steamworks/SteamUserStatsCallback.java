package com.codedisaster.steamworks;

public interface SteamUserStatsCallback {

	void onUserStatsReceived(long gameId, SteamID steamIDUser, SteamResult result);

	void onUserStatsStored(long gameId, SteamResult result);

	void onUserStatsUnloaded(SteamID steamIDUser);

	void onUserAchievementStored(long gameId,
								 boolean isGroupAchievement,
								 String achievementName,
								 int curProgress,
								 int maxProgress);

	void onLeaderboardFindResult(SteamLeaderboardHandle leaderboard, boolean found);

	void onLeaderboardScoresDownloaded(SteamLeaderboardHandle leaderboard,
									   SteamLeaderboardEntriesHandle entries,
									   int numEntries);

	void onLeaderboardScoreUploaded(boolean success,
									SteamLeaderboardHandle leaderboard,
									int score,
									boolean scoreChanged,
									int globalRankNew,
									int globalRankPrevious);

	void onGlobalStatsReceived(long gameId, SteamResult result);

}
