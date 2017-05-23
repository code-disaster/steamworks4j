package com.codedisaster.steamworks;

class SteamGameServerStatsNative {

	// @off

	/*JNI
		#include "SteamGameServerStatsCallback.h"
	*/

	static native long createCallback(Object javaCallback); /*
		return (intp) new SteamGameServerStatsCallback(env, javaCallback);
	*/

	static native long requestUserStats(long pointer, long steamIDUser); /*
		ISteamGameServerStats* stats = (ISteamGameServerStats*) pointer;
		return stats->RequestUserStats((uint64) steamIDUser);
	*/

	static native boolean getUserStat(long pointer, long steamIDUser, String name, int[] value); /*
		ISteamGameServerStats* stats = (ISteamGameServerStats*) pointer;
		return stats->GetUserStat((uint64) steamIDUser, name, &((int32*) value)[0]);
	*/

	static native boolean getUserStat(long pointer, long steamIDUser, String name, float[] value); /*
		ISteamGameServerStats* stats = (ISteamGameServerStats*) pointer;
		return stats->GetUserStat((uint64) steamIDUser, name, &value[0]);
	*/

	static native boolean getUserAchievement(long pointer, long steamIDUser, String name, boolean[] achieved); /*
		ISteamGameServerStats* stats = (ISteamGameServerStats*) pointer;
		return stats->GetUserAchievement((uint64) steamIDUser, name, &achieved[0]);
	*/

	static native boolean setUserStat(long pointer, long steamIDUser, String name, int value); /*
		ISteamGameServerStats* stats = (ISteamGameServerStats*) pointer;
		return stats->SetUserStat((uint64) steamIDUser, name, (int32) value);
	*/

	static native boolean setUserStat(long pointer, long steamIDUser, String name, float value); /*
		ISteamGameServerStats* stats = (ISteamGameServerStats*) pointer;
		return stats->SetUserStat((uint64) steamIDUser, name, value);
	*/

	static native boolean updateUserAvgRateStat(long pointer, long steamIDUser, String name, float countThisSession, double sessionLength); /*
		ISteamGameServerStats* stats = (ISteamGameServerStats*) pointer;
		return stats->UpdateUserAvgRateStat((uint64) steamIDUser, name, countThisSession, sessionLength);
	*/

	static native boolean setUserAchievement(long pointer, long steamIDUser, String name); /*
		ISteamGameServerStats* stats = (ISteamGameServerStats*) pointer;
		return stats->SetUserAchievement((uint64) steamIDUser, name);
	*/

	static native boolean clearUserAchievement(long pointer, long steamIDUser, String name); /*
		ISteamGameServerStats* stats = (ISteamGameServerStats*) pointer;
		return stats->ClearUserAchievement((uint64) steamIDUser, name);
	*/

	static native long storeUserStats(long pointer, long steamIDUser); /*
		ISteamGameServerStats* stats = (ISteamGameServerStats*) pointer;
		return stats->StoreUserStats((uint64) steamIDUser);
	*/

}
