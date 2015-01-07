#include "SteamUserStatsCallback.h"

SteamUserStatsCallback::SteamUserStatsCallback(JNIEnv* env, jobject callback)
    : SteamCallbackAdapter(env, callback)
    , m_CallbackUserStatsReceived(this, &SteamUserStatsCallback::onUserStatsReceived)
    , m_CallbackUserStatsStored(this, &SteamUserStatsCallback::onUserStatsStored) {

}

SteamUserStatsCallback::~SteamUserStatsCallback() {

}

void SteamUserStatsCallback::onUserStatsReceived(UserStatsReceived_t* callback) {
	invokeCallback({
        callVoidMethod(env, "onUserStatsReceived", "(JJI)V", (jlong) callback->m_nGameID,
            (jlong) callback->m_steamIDUser.ConvertToUint64(), (jint) callback->m_eResult);
	});
}

void SteamUserStatsCallback::onUserStatsStored(UserStatsStored_t* callback) {
	invokeCallback({
    	callVoidMethod(env, "onUserStatsStored", "(JI)V", (jlong) callback->m_nGameID, (jint) callback->m_eResult);
    });
}

void SteamUserStatsCallback::onLeaderboardFindResult(LeaderboardFindResult_t* callback, bool error) {
	invokeCallback({
        callVoidMethod(env, "onLeaderboardFindResult", "(JZ)V", (jlong) callback->m_hSteamLeaderboard,
            (jboolean) (callback->m_bLeaderboardFound != 0u));
	});
}

void SteamUserStatsCallback::onLeaderboardScoresDownloaded(LeaderboardScoresDownloaded_t* callback, bool error) {
	invokeCallback({
        callVoidMethod(env, "onLeaderboardScoresDownloaded", "(JJI)V", (jlong) callback->m_hSteamLeaderboard,
            (jlong) callback->m_hSteamLeaderboardEntries, (jint) callback->m_cEntryCount);
	});
}

void SteamUserStatsCallback::onLeaderboardScoreUploaded(LeaderboardScoreUploaded_t* callback, bool error) {
	invokeCallback({
        callVoidMethod(env, "onLeaderboardScoreUploaded", "(ZJIZII)V", (jboolean) (callback->m_bSuccess != 0),
            (jlong) callback->m_hSteamLeaderboard, (jint) callback->m_nScore,
            (jboolean) (callback->m_bScoreChanged != 0), (jint) callback->m_nGlobalRankNew,
            (jint) callback->m_nGlobalRankPrevious);
	});
}
