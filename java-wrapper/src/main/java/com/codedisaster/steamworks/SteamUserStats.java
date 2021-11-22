package com.codedisaster.steamworks;

@SuppressWarnings({ "unused", "UnusedReturnValue" })
public class SteamUserStats extends SteamInterface {

	public enum LeaderboardDataRequest {
		Global,
		GlobalAroundUser,
		Friends,
		Users
	}

	public enum LeaderboardDisplayType {
		None,
		Numeric,
		TimeSeconds,
		TimeMilliSeconds
	}

	public enum LeaderboardSortMethod {
		None,
		Ascending,
		Descending
	}

	public enum LeaderboardUploadScoreMethod {
		None,
		KeepBest,
		ForceUpdate
	}

	public SteamUserStats(SteamUserStatsCallback callback) {
		super(SteamUserStatsNative.createCallback(new SteamUserStatsCallbackAdapter(callback)));
	}

	public boolean requestCurrentStats() {
		return SteamUserStatsNative.requestCurrentStats();
	}

	public int getStatI(String name, int defaultValue) {
		int[] values = new int[1];
		if (SteamUserStatsNative.getStat(name, values)) {
			return values[0];
		}
		return defaultValue;
	}

	public boolean setStatI(String name, int value) {
		return SteamUserStatsNative.setStat(name, value);
	}

	public float getStatF(String name, float defaultValue) {
		float[] values = new float[1];
		if (SteamUserStatsNative.getStat(name, values)) {
			return values[0];
		}
		return defaultValue;
	}

	public boolean setStatF(String name, float value) {
		return SteamUserStatsNative.setStat(name, value);
	}

	public boolean isAchieved(String name, boolean defaultValue) {
		boolean[] achieved = new boolean[1];
		if (SteamUserStatsNative.getAchievement(name, achieved)) {
			return achieved[0];
		}
		return defaultValue;
	}

	public boolean setAchievement(String name) {
		return SteamUserStatsNative.setAchievement(name);
	}

	public boolean clearAchievement(String name) {
		return SteamUserStatsNative.clearAchievement(name);
	}

	public boolean storeStats() {
		return SteamUserStatsNative.storeStats();
	}

	public boolean indicateAchievementProgress(String name, int curProgress, int maxProgress) {
		return SteamUserStatsNative.indicateAchievementProgress(name, curProgress, maxProgress);
	}

	public int getNumAchievements() {
		return SteamUserStatsNative.getNumAchievements();
	}

	public String getAchievementName(int index) {
		return SteamUserStatsNative.getAchievementName(index);
	}

	public boolean resetAllStats(boolean achievementsToo) {
		return SteamUserStatsNative.resetAllStats(achievementsToo);
	}

	public SteamAPICall findOrCreateLeaderboard(String leaderboardName,
												LeaderboardSortMethod leaderboardSortMethod,
												LeaderboardDisplayType leaderboardDisplayType) {

		return new SteamAPICall(SteamUserStatsNative.findOrCreateLeaderboard(callback, leaderboardName,
				leaderboardSortMethod.ordinal(), leaderboardDisplayType.ordinal()));
	}

	public SteamAPICall findLeaderboard(String leaderboardName) {
		return new SteamAPICall(SteamUserStatsNative.findLeaderboard(callback, leaderboardName));
	}

	public String getLeaderboardName(SteamLeaderboardHandle leaderboard) {
		return SteamUserStatsNative.getLeaderboardName(leaderboard.handle);
	}

	public int getLeaderboardEntryCount(SteamLeaderboardHandle leaderboard) {
		return SteamUserStatsNative.getLeaderboardEntryCount(leaderboard.handle);
	}

	public LeaderboardSortMethod getLeaderboardSortMethod(SteamLeaderboardHandle leaderboard) {
		return LeaderboardSortMethod.values()[SteamUserStatsNative.getLeaderboardSortMethod(leaderboard.handle)];
	}

	public LeaderboardDisplayType getLeaderboardDisplayType(SteamLeaderboardHandle leaderboard) {
		return LeaderboardDisplayType.values()[SteamUserStatsNative.getLeaderboardDisplayType(leaderboard.handle)];
	}

	public SteamAPICall downloadLeaderboardEntries(SteamLeaderboardHandle leaderboard,
												   LeaderboardDataRequest leaderboardDataRequest,
												   int rangeStart,
												   int rangeEnd) {

		return new SteamAPICall(SteamUserStatsNative.downloadLeaderboardEntries(callback, leaderboard.handle,
				leaderboardDataRequest.ordinal(), rangeStart, rangeEnd));
	}

	public SteamAPICall downloadLeaderboardEntriesForUsers(SteamLeaderboardHandle leaderboard,
														   SteamID[] users) {

		int count = users.length;
		long[] handles = new long[count];

		for (int i = 0; i < count; i++) {
			handles[i] = users[i].handle;
		}

		return new SteamAPICall(SteamUserStatsNative.downloadLeaderboardEntriesForUsers(
				callback, leaderboard.handle, handles, count));
	}

	/**
	 * @param details The array size denotes the maximum number of details returned.
	 *                Check {@link SteamLeaderboardEntry#getNumDetails()} for the actual count. Can be null.
	 */
	public boolean getDownloadedLeaderboardEntry(SteamLeaderboardEntriesHandle leaderboardEntries,
												 int index, SteamLeaderboardEntry entry, int[] details) {

		return details == null
				? SteamUserStatsNative.getDownloadedLeaderboardEntry(leaderboardEntries.handle, index, entry)
				: SteamUserStatsNative.getDownloadedLeaderboardEntry(leaderboardEntries.handle, index, entry, details, details.length);
	}

	/**
	 * @param scoreDetails Can be null.
	 */
	public SteamAPICall uploadLeaderboardScore(SteamLeaderboardHandle leaderboard,
											   LeaderboardUploadScoreMethod method,
											   int score, int[] scoreDetails) {

		return new SteamAPICall(scoreDetails == null
				? SteamUserStatsNative.uploadLeaderboardScore(callback, leaderboard.handle, method.ordinal(), score)
				: SteamUserStatsNative.uploadLeaderboardScore(callback, leaderboard.handle, method.ordinal(), score, scoreDetails, scoreDetails.length));
	}

	public SteamAPICall getNumberOfCurrentPlayers() {
		return new SteamAPICall(SteamUserStatsNative.getNumberOfCurrentPlayers(callback));
	}

	public SteamAPICall requestGlobalStats(int historyDays) {
		return new SteamAPICall(SteamUserStatsNative.requestGlobalStats(callback, historyDays));
	}

	public long getGlobalStat(String name, long defaultValue) {
		long[] values = new long[1];
		if (SteamUserStatsNative.getGlobalStat(name, values)) {
			return values[0];
		}
		return defaultValue;
	}

	public double getGlobalStat(String name, double defaultValue) {
		double[] values = new double[1];
		if (SteamUserStatsNative.getGlobalStat(name, values)) {
			return values[0];
		}
		return defaultValue;
	}

	public int getGlobalStatHistory(String name, long[] data) {
		return SteamUserStatsNative.getGlobalStatHistory(name, data, data.length);
	}

	public int getGlobalStatHistory(String name, double[] data) {
		return SteamUserStatsNative.getGlobalStatHistory(name, data, data.length);
	}

}
