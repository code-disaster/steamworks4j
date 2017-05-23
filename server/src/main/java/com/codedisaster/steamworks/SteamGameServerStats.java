package com.codedisaster.steamworks;

public class SteamGameServerStats extends SteamInterface {

	public SteamGameServerStats(SteamGameServerStatsCallback callback) {
		super(SteamGameServerAPINative.getSteamGameServerStatsPointer(),
				SteamGameServerStatsNative.createCallback(new SteamGameServerStatsCallbackAdapter(callback)));
	}

	public SteamAPICall requestUserStats(SteamID steamIDUser) {
		return new SteamAPICall(SteamGameServerStatsNative.requestUserStats(pointer, steamIDUser.handle));
	}

	public int getUserStatI(SteamID steamIDUser, String name, int defaultValue) {
		int[] values = new int[1];
		if (SteamGameServerStatsNative.getUserStat(pointer, steamIDUser.handle, name, values)) {
			return values[0];
		}
		return defaultValue;
	}

	public float getUserStatF(SteamID steamIDUser, String name, float defaultValue) {
		float[] values = new float[1];
		if (SteamGameServerStatsNative.getUserStat(pointer, steamIDUser.handle, name, values)) {
			return values[0];
		}
		return defaultValue;
	}

	public boolean getUserAchievement(SteamID steamIDUser, String name, boolean defaultValue) {
		boolean[] achieved = new boolean[1];
		if (SteamGameServerStatsNative.getUserAchievement(pointer, steamIDUser.handle, name, achieved)) {
			return achieved[0];
		}
		return defaultValue;
	}

	public boolean setUserStatI(SteamID steamIDUser, String name, int value) {
		return SteamGameServerStatsNative.setUserStat(pointer, steamIDUser.handle, name, value);
	}

	public boolean setUserStatF(SteamID steamIDUser, String name, float value) {
		return SteamGameServerStatsNative.setUserStat(pointer, steamIDUser.handle, name, value);
	}

	public boolean updateUserAvgRateStat(SteamID steamIDUser, String name, float countThisSession, double sessionLength) {
		return SteamGameServerStatsNative.updateUserAvgRateStat(
				pointer, steamIDUser.handle, name, countThisSession, sessionLength);
	}

	public boolean setUserAchievement(SteamID steamIDUser, String name) {
		return SteamGameServerStatsNative.setUserAchievement(pointer, steamIDUser.handle, name);
	}

	public boolean clearUserAchievement(SteamID steamIDUser, String name) {
		return SteamGameServerStatsNative.clearUserAchievement(pointer, steamIDUser.handle, name);
	}

	public SteamAPICall storeUserStats(SteamID steamIDUser) {
		return new SteamAPICall(SteamGameServerStatsNative.storeUserStats(pointer, steamIDUser.handle));
	}

}
