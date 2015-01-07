#pragma once

#include "SteamCallbackAdapter.h"
#include <steam_api.h>

class SteamUserStatsCallback : public SteamCallbackAdapter {

public:
    SteamUserStatsCallback(JNIEnv* env, jobject callback);
    ~SteamUserStatsCallback();

    STEAM_CALLBACK(SteamUserStatsCallback, onUserStatsReceived, UserStatsReceived_t, m_CallbackUserStatsReceived);
	STEAM_CALLBACK(SteamUserStatsCallback, onUserStatsStored, UserStatsStored_t, m_CallbackUserStatsStored);

	void onLeaderboardFindResult(LeaderboardFindResult_t* callback, bool error);
	CCallResult<SteamUserStatsCallback, LeaderboardFindResult_t> onLeaderboardFindResultCall;

	void onLeaderboardScoresDownloaded(LeaderboardScoresDownloaded_t* callback, bool error);
	CCallResult<SteamUserStatsCallback, LeaderboardScoresDownloaded_t> onLeaderboardScoresDownloadedCall;

	void onLeaderboardScoreUploaded(LeaderboardScoreUploaded_t* callback, bool error);
	CCallResult<SteamUserStatsCallback, LeaderboardScoreUploaded_t> onLeaderboardScoreUploadedCall;
};
