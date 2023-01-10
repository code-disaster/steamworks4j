package com.codedisaster.steamworks;

class SteamGameServerStatsNative {

	// @off

	/*JNI
		#include "SteamGameServerStatsCallback.h"
	*/

	static native long createCallback(Object javaCallback); /*
		return (intp) new SteamGameServerStatsCallback(env, javaCallback);
	*/

	static native long requestUserStats(long steamIDUser); /*
		return SteamGameServerStats()->RequestUserStats((uint64) steamIDUser);
	*/

	static native boolean getUserStat(long steamIDUser, String name, int[] value); /*
		return SteamGameServerStats()->GetUserStat((uint64) steamIDUser, name, &((int32*) value)[0]);
	*/

	static native boolean getUserStat(long steamIDUser, String name, float[] value); /*
		return SteamGameServerStats()->GetUserStat((uint64) steamIDUser, name, &value[0]);
	*/

	static native boolean getUserAchievement(long steamIDUser, String name, boolean[] achieved); /*
		return SteamGameServerStats()->GetUserAchievement((uint64) steamIDUser, name, &achieved[0]);
	*/

	static native boolean setUserStat(long steamIDUser, String name, int value); /*
		return SteamGameServerStats()->SetUserStat((uint64) steamIDUser, name, (int32) value);
	*/

	static native boolean setUserStat(long steamIDUser, String name, float value); /*
		return SteamGameServerStats()->SetUserStat((uint64) steamIDUser, name, value);
	*/

	static native boolean updateUserAvgRateStat(long steamIDUser, String name, float countThisSession, double sessionLength); /*
		return SteamGameServerStats()->UpdateUserAvgRateStat((uint64) steamIDUser, name, countThisSession, sessionLength);
	*/

	static native boolean setUserAchievement(long steamIDUser, String name); /*
		return SteamGameServerStats()->SetUserAchievement((uint64) steamIDUser, name);
	*/

	static native boolean clearUserAchievement(long steamIDUser, String name); /*
		return SteamGameServerStats()->ClearUserAchievement((uint64) steamIDUser, name);
	*/

	static native long storeUserStats(long steamIDUser); /*
		return SteamGameServerStats()->StoreUserStats((uint64) steamIDUser);
	*/

}
