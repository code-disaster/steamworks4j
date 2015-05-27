package com.codedisaster.steamworks;

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
		super(SteamAPI.getSteamUserStatsPointer(), createCallback(new SteamUserStatsCallbackAdapter(callback)));
	}

	public boolean requestCurrentStats() {
		return requestCurrentStats(pointer);
	}

	public int getStatI(String name, int defaultValue) {
		int[] values = new int[1];
		if (getStat(pointer, name, values)) {
			return values[0];
		}
		return defaultValue;
	}

	public boolean setStatI(String name, int value) {
		return setStat(pointer, name, value);
	}

	public float getStatF(String name, float defaultValue) {
		float[] values = new float[1];
		if (getStat(pointer, name, values)) {
			return values[0];
		}
		return defaultValue;
	}

	public boolean setStatF(String name, float value) {
		return setStat(pointer, name, value);
	}

	public boolean isAchieved(String name, boolean defaultValue) {
		boolean[] achieved = new boolean[1];
		if (getAchievement(pointer, name, achieved)) {
			return achieved[0];
		}
		return defaultValue;
	}

	public boolean setAchievement(String name) {
		return setAchievement(pointer, name);
	}

	public boolean clearAchievement(String name) {
		return clearAchievement(pointer, name);
	}

	public boolean storeStats() {
		return storeStats(pointer);
	}

	public boolean indicateAchievementProgress(String name, int curProgress, int maxProgress) {
		return indicateAchievementProgress(pointer, name, curProgress, maxProgress);
	}

	public int getNumAchievements() {
		return getNumAchievements(pointer);
	}

	public String getAchievementName(int index) {
		return getAchievementName(pointer, index);
	}

	public boolean resetAllStats(boolean achievementsToo) {
		return resetAllStats(pointer, achievementsToo);
	}

	public SteamAPICall findOrCreateLeaderboard(String leaderboardName,
												LeaderboardSortMethod leaderboardSortMethod,
												LeaderboardDisplayType leaderboardDisplayType) {

		return new SteamAPICall(findOrCreateLeaderboard(pointer, callback, leaderboardName,
				leaderboardSortMethod.ordinal(), leaderboardDisplayType.ordinal()));
	}

	public SteamAPICall findLeaderboard(String leaderboardName) {
		return new SteamAPICall(findLeaderboard(pointer, callback, leaderboardName));
	}

	public String getLeaderboardName(SteamLeaderboardHandle leaderboard) {
		return getLeaderboardName(pointer, leaderboard.handle);
	}

	public int getLeaderboardEntryCount(SteamLeaderboardHandle leaderboard) {
		return getLeaderboardEntryCount(pointer, leaderboard.handle);
	}

	public LeaderboardSortMethod getLeaderboardSortMethod(SteamLeaderboardHandle leaderboard) {
		return LeaderboardSortMethod.values()[getLeaderboardSortMethod(pointer, leaderboard.handle)];
	}

	public LeaderboardDisplayType getLeaderboardDisplayType(SteamLeaderboardHandle leaderboard) {
		return LeaderboardDisplayType.values()[getLeaderboardDisplayType(pointer, leaderboard.handle)];
	}

	public SteamAPICall downloadLeaderboardEntries(SteamLeaderboardHandle leaderboard,
												   LeaderboardDataRequest leaderboardDataRequest,
												   int rangeStart,
												   int rangeEnd) {

		return new SteamAPICall(downloadLeaderboardEntries(pointer, callback, leaderboard.handle,
				leaderboardDataRequest.ordinal(), rangeStart, rangeEnd));
	}

	public boolean getDownloadedLeaderboardEntry(SteamLeaderboardEntriesHandle leaderboardEntries,
												 int index, SteamLeaderboardEntry entry) {

		return getDownloadedLeaderboardEntry(pointer, leaderboardEntries.handle, index, entry);
	}

	public SteamAPICall uploadLeaderboardScore(SteamLeaderboardHandle leaderboard,
											   LeaderboardUploadScoreMethod method,
											   int score) {

		return new SteamAPICall(uploadLeaderboardScore(pointer, callback, leaderboard.handle, method.ordinal(), score));
	}

	// @off

	/*JNI
		#include <steam_api.h>
		#include "SteamUserStatsCallback.h"
	*/

	static private native long createCallback(SteamUserStatsCallbackAdapter javaCallback); /*
		return (long) new SteamUserStatsCallback(env, javaCallback);
	*/

	static private native boolean requestCurrentStats(long pointer); /*
		ISteamUserStats* stats = (ISteamUserStats*) pointer;
		return stats->RequestCurrentStats();
	*/

	static private native boolean getStat(long pointer, String name, int[] value); /*
		ISteamUserStats* stats = (ISteamUserStats*) pointer;
		return stats->GetStat(name, &((int32*) value)[0]);
	*/

	static private native boolean setStat(long pointer, String name, int value); /*
		ISteamUserStats* stats = (ISteamUserStats*) pointer;
		return stats->SetStat(name, (int32) value);
	*/

	static private native boolean getStat(long pointer, String name, float[] value); /*
		ISteamUserStats* stats = (ISteamUserStats*) pointer;
		return stats->GetStat(name, &value[0]);
	*/

	static private native boolean setStat(long pointer, String name, float value); /*
		ISteamUserStats* stats = (ISteamUserStats*) pointer;
		return stats->SetStat(name, value);
	*/

	static private native boolean getAchievement(long pointer, String name, boolean[] achieved); /*
		ISteamUserStats* stats = (ISteamUserStats*) pointer;
		return stats->GetAchievement(name, &achieved[0]);
	*/

	static private native boolean setAchievement(long pointer, String name); /*
		ISteamUserStats* stats = (ISteamUserStats*) pointer;
		return stats->SetAchievement(name);
	*/

	static private native boolean clearAchievement(long pointer, String name); /*
		ISteamUserStats* stats = (ISteamUserStats*) pointer;
		return stats->ClearAchievement(name);
	*/

	static private native boolean storeStats(long pointer); /*
		ISteamUserStats* stats = (ISteamUserStats*) pointer;
		return stats->StoreStats();
	*/

	static private native boolean indicateAchievementProgress(long pointer, String name,
															  int curProgress, int maxProgress); /*
		ISteamUserStats* stats = (ISteamUserStats*) pointer;
		return stats->IndicateAchievementProgress(name, curProgress, maxProgress);
	*/

	static private native int getNumAchievements(long pointer); /*
		ISteamUserStats* stats = (ISteamUserStats*) pointer;
		return stats->GetNumAchievements();
	*/

	static private native String getAchievementName(long pointer, int index); /*
		ISteamUserStats* stats = (ISteamUserStats*) pointer;
		jstring name = env->NewStringUTF(stats->GetAchievementName(index));
		return name;
	*/

	static private native boolean resetAllStats(long pointer, boolean achievementsToo); /*
		ISteamUserStats* stats = (ISteamUserStats*) pointer;
		return stats->ResetAllStats(achievementsToo);
	*/

	static private native long findOrCreateLeaderboard(long pointer, long callback, String leaderboardName,
													   int leaderboardSortMethod, int leaderboardDisplayType); /*
		ISteamUserStats* stats = (ISteamUserStats*) pointer;
		SteamUserStatsCallback* cb = (SteamUserStatsCallback*) callback;
		SteamAPICall_t handle = stats->FindOrCreateLeaderboard(leaderboardName,
			(ELeaderboardSortMethod) leaderboardSortMethod, (ELeaderboardDisplayType) leaderboardDisplayType);
		cb->onLeaderboardFindResultCall.Set(handle, cb, &SteamUserStatsCallback::onLeaderboardFindResult);
		return handle;
	*/

	static private native long findLeaderboard(long pointer, long callback, String leaderboardName); /*
		ISteamUserStats* stats = (ISteamUserStats*) pointer;
		SteamUserStatsCallback* cb = (SteamUserStatsCallback*) callback;
		SteamAPICall_t handle = stats->FindLeaderboard(leaderboardName);
		cb->onLeaderboardFindResultCall.Set(handle, cb, &SteamUserStatsCallback::onLeaderboardFindResult);
		return handle;
	*/

	static private native String getLeaderboardName(long pointer, long leaderboard); /*
		ISteamUserStats* stats = (ISteamUserStats*) pointer;
		jstring name = env->NewStringUTF(stats->GetLeaderboardName(leaderboard));
		return name;
	*/

	static private native int getLeaderboardEntryCount(long pointer, long leaderboard); /*
		ISteamUserStats* stats = (ISteamUserStats*) pointer;
		return stats->GetLeaderboardEntryCount(leaderboard);
	*/

	static private native int getLeaderboardSortMethod(long pointer, long leaderboard); /*
		ISteamUserStats* stats = (ISteamUserStats*) pointer;
		return stats->GetLeaderboardSortMethod(leaderboard);
	*/

	static private native int getLeaderboardDisplayType(long pointer, long leaderboard); /*
		ISteamUserStats* stats = (ISteamUserStats*) pointer;
		return stats->GetLeaderboardDisplayType(leaderboard);
	*/

	static private native long downloadLeaderboardEntries(long pointer, long callback, long leaderboard,
														  int leaderboardDataRequest, int rangeStart, int rangeEnd); /*

		ISteamUserStats* stats = (ISteamUserStats*) pointer;
		SteamUserStatsCallback* cb = (SteamUserStatsCallback*) callback;

		SteamAPICall_t handle = stats->DownloadLeaderboardEntries(leaderboard,
			(ELeaderboardDataRequest) leaderboardDataRequest, rangeStart, rangeEnd);

		cb->onLeaderboardScoresDownloadedCall.Set(handle, cb,
			&SteamUserStatsCallback::onLeaderboardScoresDownloaded);

		return handle;
	*/

	static private native boolean getDownloadedLeaderboardEntry(long pointer, long entries, int index,
																SteamLeaderboardEntry entry); /*

		ISteamUserStats* stats = (ISteamUserStats*) pointer;
		LeaderboardEntry_t result;

		if (stats->GetDownloadedLeaderboardEntry(entries, index, &result, NULL, 0)) {
			jclass clazz = env->GetObjectClass(entry);

			jfieldID field = env->GetFieldID(clazz, "steamIDUser", "J");
			env->SetLongField(entry, field, (jlong) result.m_steamIDUser.ConvertToUint64());

			field = env->GetFieldID(clazz, "globalRank", "I");
			env->SetIntField(entry, field, (jint) result.m_nGlobalRank);

			field = env->GetFieldID(clazz, "score", "I");
			env->SetLongField(entry, field, (jint) result.m_nScore);

			return true;
		}

		return false;

	*/

	static private native long uploadLeaderboardScore(long pointer, long callback,
													  long leaderboard, int method, int score); /*

		ISteamUserStats* stats = (ISteamUserStats*) pointer;
		SteamUserStatsCallback* cb = (SteamUserStatsCallback*) callback;

		SteamAPICall_t handle = stats->UploadLeaderboardScore(leaderboard,
			(ELeaderboardUploadScoreMethod) method, score, NULL, 0);

		cb->onLeaderboardScoreUploadedCall.Set(handle, cb,
			&SteamUserStatsCallback::onLeaderboardScoreUploaded);

		return handle;
	*/

}
