package com.codedisaster.steamworks;

final class SteamUserStatsNative {

	// @off

	/*JNI
		#include <steam_api.h>
		#include "SteamUserStatsCallback.h"
	*/

	static native long createCallback(SteamUserStatsCallbackAdapter javaCallback); /*
		return (intp) new SteamUserStatsCallback(env, javaCallback);
	*/

	static native boolean requestCurrentStats(); /*
		return SteamUserStats()->RequestCurrentStats();
	*/

	static native boolean getStat(String name, int[] value); /*
		return SteamUserStats()->GetStat(name, &((int32*) value)[0]);
	*/

	static native boolean setStat(String name, int value); /*
		return SteamUserStats()->SetStat(name, (int32) value);
	*/

	static native boolean getStat(String name, float[] value); /*
		return SteamUserStats()->GetStat(name, &value[0]);
	*/

	static native boolean setStat(String name, float value); /*
		return SteamUserStats()->SetStat(name, value);
	*/

	static native boolean getAchievement(String name, boolean[] achieved); /*
		return SteamUserStats()->GetAchievement(name, &achieved[0]);
	*/

	static native boolean setAchievement(String name); /*
		return SteamUserStats()->SetAchievement(name);
	*/

	static native boolean clearAchievement(String name); /*
		return SteamUserStats()->ClearAchievement(name);
	*/

	static native boolean storeStats(); /*
		return SteamUserStats()->StoreStats();
	*/

	static native boolean indicateAchievementProgress(String name,
													  int curProgress, int maxProgress); /*
		return SteamUserStats()->IndicateAchievementProgress(name, curProgress, maxProgress);
	*/

	static native int getNumAchievements(); /*
		return SteamUserStats()->GetNumAchievements();
	*/

	static native String getAchievementName(int index); /*
		return env->NewStringUTF(SteamUserStats()->GetAchievementName(index));
	*/

	static native boolean resetAllStats(boolean achievementsToo); /*
		return SteamUserStats()->ResetAllStats(achievementsToo);
	*/

	static native long findOrCreateLeaderboard(long callback, String leaderboardName,
											   int leaderboardSortMethod, int leaderboardDisplayType); /*
		SteamUserStatsCallback* cb = (SteamUserStatsCallback*) callback;
		SteamAPICall_t handle = SteamUserStats()->FindOrCreateLeaderboard(leaderboardName,
			(ELeaderboardSortMethod) leaderboardSortMethod, (ELeaderboardDisplayType) leaderboardDisplayType);
		cb->onLeaderboardFindResultCall.Set(handle, cb, &SteamUserStatsCallback::onLeaderboardFindResult);
		return handle;
	*/

	static native long findLeaderboard(long callback, String leaderboardName); /*
		SteamUserStatsCallback* cb = (SteamUserStatsCallback*) callback;
		SteamAPICall_t handle = SteamUserStats()->FindLeaderboard(leaderboardName);
		cb->onLeaderboardFindResultCall.Set(handle, cb, &SteamUserStatsCallback::onLeaderboardFindResult);
		return handle;
	*/

	static native String getLeaderboardName(long leaderboard); /*
		return env->NewStringUTF(SteamUserStats()->GetLeaderboardName(leaderboard));
	*/

	static native int getLeaderboardEntryCount(long leaderboard); /*
		return SteamUserStats()->GetLeaderboardEntryCount(leaderboard);
	*/

	static native int getLeaderboardSortMethod(long leaderboard); /*
		return SteamUserStats()->GetLeaderboardSortMethod(leaderboard);
	*/

	static native int getLeaderboardDisplayType(long leaderboard); /*
		return SteamUserStats()->GetLeaderboardDisplayType(leaderboard);
	*/

	static native long downloadLeaderboardEntries(long callback, long leaderboard,
												  int leaderboardDataRequest, int rangeStart, int rangeEnd); /*

		SteamUserStatsCallback* cb = (SteamUserStatsCallback*) callback;

		SteamAPICall_t handle = SteamUserStats()->DownloadLeaderboardEntries(leaderboard,
			(ELeaderboardDataRequest) leaderboardDataRequest, rangeStart, rangeEnd);

		cb->onLeaderboardScoresDownloadedCall.Set(handle, cb,
			&SteamUserStatsCallback::onLeaderboardScoresDownloaded);

		return handle;
	*/

	static native long downloadLeaderboardEntriesForUsers(long callback, long leaderboard,
														  long[] users, int count); /*
		SteamUserStatsCallback* cb = (SteamUserStatsCallback*) callback;

		SteamAPICall_t handle = SteamUserStats()->DownloadLeaderboardEntriesForUsers(leaderboard, (CSteamID*)users, count);

		cb->onLeaderboardScoresDownloadedCall.Set(handle, cb,
			&SteamUserStatsCallback::onLeaderboardScoresDownloaded);

		return handle;
	*/

	static native boolean getDownloadedLeaderboardEntry(long entries, int index,
														SteamLeaderboardEntry entry,
														int[] details, int detailsMax); /*

		LeaderboardEntry_t result;

		if (SteamUserStats()->GetDownloadedLeaderboardEntry(entries, index, &result, details, detailsMax)) {
			jclass clazz = env->GetObjectClass(entry);

			jfieldID field = env->GetFieldID(clazz, "steamIDUser", "J");
			env->SetLongField(entry, field, (jlong) result.m_steamIDUser.ConvertToUint64());

			field = env->GetFieldID(clazz, "globalRank", "I");
			env->SetIntField(entry, field, (jint) result.m_nGlobalRank);

			field = env->GetFieldID(clazz, "score", "I");
			env->SetIntField(entry, field, (jint) result.m_nScore);

			field = env->GetFieldID(clazz, "details", "I");
			env->SetIntField(entry, field, (jint) result.m_cDetails);

			return true;
		}

		return false;

	*/

	static native boolean getDownloadedLeaderboardEntry(long entries, int index,
														SteamLeaderboardEntry entry); /*

		LeaderboardEntry_t result;

		if (SteamUserStats()->GetDownloadedLeaderboardEntry(entries, index, &result, NULL, 0)) {
			jclass clazz = env->GetObjectClass(entry);

			jfieldID field = env->GetFieldID(clazz, "steamIDUser", "J");
			env->SetLongField(entry, field, (jlong) result.m_steamIDUser.ConvertToUint64());

			field = env->GetFieldID(clazz, "globalRank", "I");
			env->SetIntField(entry, field, (jint) result.m_nGlobalRank);

			field = env->GetFieldID(clazz, "score", "I");
			env->SetIntField(entry, field, (jint) result.m_nScore);

			field = env->GetFieldID(clazz, "details", "I");
			env->SetIntField(entry, field, (jint) result.m_cDetails);

			return true;
		}

		return false;

	*/

	static native long uploadLeaderboardScore(long callback,
											  long leaderboard, int method, int score,
											  int[] scoreDetails, int scoreDetailsCount); /*

		SteamUserStatsCallback* cb = (SteamUserStatsCallback*) callback;

		SteamAPICall_t handle = SteamUserStats()->UploadLeaderboardScore(leaderboard,
			(ELeaderboardUploadScoreMethod) method, score, scoreDetails, scoreDetailsCount);

		cb->onLeaderboardScoreUploadedCall.Set(handle, cb,
			&SteamUserStatsCallback::onLeaderboardScoreUploaded);

		return handle;
	*/

	static native long uploadLeaderboardScore(long callback,
											  long leaderboard, int method, int score); /*

		SteamUserStatsCallback* cb = (SteamUserStatsCallback*) callback;

		SteamAPICall_t handle = SteamUserStats()->UploadLeaderboardScore(leaderboard,
			(ELeaderboardUploadScoreMethod) method, score, NULL, 0);

		cb->onLeaderboardScoreUploadedCall.Set(handle, cb,
			&SteamUserStatsCallback::onLeaderboardScoreUploaded);

		return handle;
	*/

	static native long getNumberOfCurrentPlayers(long callback); /*
		SteamUserStatsCallback* cb = (SteamUserStatsCallback*) callback;

		SteamAPICall_t handle = SteamUserStats()->GetNumberOfCurrentPlayers();

		cb->onNumberOfCurrentPlayersReceivedCall.Set(handle, cb, &SteamUserStatsCallback::onNumberOfCurrentPlayersReceived);

		return handle;
	*/

	static native long requestGlobalStats(long callback, int historyDays); /*
		SteamUserStatsCallback* cb = (SteamUserStatsCallback*) callback;

		SteamAPICall_t handle = SteamUserStats()->RequestGlobalStats(historyDays);

		cb->onGlobalStatsReceivedCall.Set(handle, cb, &SteamUserStatsCallback::onGlobalStatsReceived);

		return handle;
	*/

	static native boolean getGlobalStat(String name, long[] value); /*
		return SteamUserStats()->GetGlobalStat(name, &((int64*) value)[0]);
	*/

	static native boolean getGlobalStat(String name, double[] value); /*
		return SteamUserStats()->GetGlobalStat(name, &value[0]);
	*/

	static native int getGlobalStatHistory(String name, long[] values, int count); /*
		return SteamUserStats()->GetGlobalStatHistory(name, values, count * sizeof(int64));
	*/

	static native int getGlobalStatHistory(String name, double[] values, int count); /*
		return SteamUserStats()->GetGlobalStatHistory(name, values, count * sizeof(double));
	*/

}
