package com.codedisaster.steamworks;

public interface SteamUserStatsCallback {

	default void onUserStatsReceived(long gameId, SteamID steamIDUser, SteamResult result) {
	}

	default void onUserStatsStored(long gameId, SteamResult result) {
	}

	default void onUserStatsUnloaded(SteamID steamIDUser) {
	}

	default void onUserAchievementStored(long gameId,
										 boolean isGroupAchievement,
										 String achievementName,
										 int curProgress,
										 int maxProgress) {
	}

	default void onLeaderboardFindResult(SteamLeaderboardHandle leaderboard, boolean found) {
	}

	default void onLeaderboardScoresDownloaded(SteamLeaderboardHandle leaderboard,
											   SteamLeaderboardEntriesHandle entries,
											   int numEntries) {
	}

	default void onLeaderboardScoreUploaded(boolean success,
											SteamLeaderboardHandle leaderboard,
											int score,
											boolean scoreChanged,
											int globalRankNew,
											int globalRankPrevious) {
	}

	default void onNumberOfCurrentPlayersReceived(boolean success, int players) {
	}

	default void onGlobalStatsReceived(long gameId, SteamResult result) {
	}

}
