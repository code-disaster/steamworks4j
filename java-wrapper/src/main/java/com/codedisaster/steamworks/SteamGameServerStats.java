package com.codedisaster.steamworks;

/**
 *
 * @author Francisco "Franz" Bischoff
 */
public class SteamGameServerStats extends SteamInterface {

	public SteamGameServerStats(long pointer, SteamGameServerStatsCallback callback) {
		super(pointer);
		registerCallback(new SteamGameServerStatsCallbackAdapter(callback));
	}

	static void dispose() {
		registerCallback(null);
	}

	public SteamAPICall requestUserStats(SteamID steamIDUser) {
		return new SteamAPICall(requestUserStats(pointer, steamIDUser.handle));
	}

	public int getUserStatI(SteamID steamIDUser, String name, int defaultValue) {
		int[] values = new int[1];
		if (getUserStat(pointer, steamIDUser.handle, name, values)) {
			return values[0];
		}
		return defaultValue;
	}

	public float getUserStatF(SteamID steamIDUser, String name, float defaultValue) {
		float[] values = new float[1];
		if (getUserStat(pointer, steamIDUser.handle, name, values)) {
			return values[0];
		}
		return defaultValue;
	}

	public boolean getUserAchievement(SteamID steamIDUser, String name, boolean defaultValue) {
		boolean[] achieved = new boolean[1];
		if (getUserAchievement(pointer, steamIDUser.handle, name, achieved)) {
			return achieved[0];
		}
		return defaultValue;
	}

	public boolean setUserStatI(SteamID steamIDUser, String name, int value) {
		return setUserStat(pointer, steamIDUser.handle, name, value);
	}

	public boolean setUserStatF(SteamID steamIDUser, String name, float value) {
		return setUserStat(pointer, steamIDUser.handle, name, value);
	}

	public boolean updateUserAvgRateStat(SteamID steamIDUser, String name, float countThisSession, double sessionLength) {
		return updateUserAvgRateStat(pointer, steamIDUser.handle, name, countThisSession, sessionLength);
	}

	public boolean setUserAchievement(SteamID steamIDUser, String name) {
		return setUserAchievement(pointer, steamIDUser.handle, name);
	}

	public boolean clearUserAchievement(SteamID steamIDUser, String name) {
		return clearUserAchievement(pointer, steamIDUser.handle, name);
	}

	public SteamAPICall storeUserStats(SteamID steamIDUser) {
		return new SteamAPICall(storeUserStats(pointer, steamIDUser.handle));
	}

	// @off

	/*JNI
		#include <steam_gameserver.h>
		#include "SteamGameServerStatsCallback.h"

		static SteamGameServerStatsCallback* callback = NULL;
	*/

	static private native boolean registerCallback(SteamGameServerStatsCallbackAdapter javaCallback); /*
		if (callback != NULL) {
			delete callback;
			callback = NULL;
		}

		if (javaCallback != NULL) {
			callback = new SteamGameServerStatsCallback(env, javaCallback);
		}

		return callback != NULL;
	*/

	static private native long requestUserStats(long pointer, long steamIDUser); /*
		ISteamGameServerStats* stats = (ISteamGameServerStats*) pointer;
		return stats->RequestUserStats((uint64) steamIDUser);
	*/

	static private native boolean getUserStat(long pointer, long steamIDUser, String name, int[] value); /*
		ISteamGameServerStats* stats = (ISteamGameServerStats*) pointer;
		return stats->GetUserStat((uint64) steamIDUser, name, &((int32*) value)[0]);
	*/

	static private native boolean getUserStat(long pointer, long steamIDUser, String name, float[] value); /*
		ISteamGameServerStats* stats = (ISteamGameServerStats*) pointer;
		return stats->GetUserStat((uint64) steamIDUser, name, &value[0]);
	*/

	static private native boolean getUserAchievement(long pointer, long steamIDUser, String name, boolean[] achieved); /*
		ISteamGameServerStats* stats = (ISteamGameServerStats*) pointer;
		return stats->GetUserAchievement((uint64) steamIDUser, name, &achieved[0]);
	*/

	static private native boolean setUserStat(long pointer, long steamIDUser, String name, int value); /*
		ISteamGameServerStats* stats = (ISteamGameServerStats*) pointer;
		return stats->SetUserStat((uint64) steamIDUser, name, (int32) value);
	*/

	static private native boolean setUserStat(long pointer, long steamIDUser, String name, float value); /*
		ISteamGameServerStats* stats = (ISteamGameServerStats*) pointer;
		return stats->SetUserStat((uint64) steamIDUser, name, value);
	*/

	static private native boolean updateUserAvgRateStat(long pointer, long steamIDUser, String name, float countThisSession, double sessionLength); /*
		ISteamGameServerStats* stats = (ISteamGameServerStats*) pointer;
		return stats->UpdateUserAvgRateStat((uint64) steamIDUser, name, countThisSession, sessionLength);
	*/

	static private native boolean setUserAchievement(long pointer, long steamIDUser, String name); /*
		ISteamGameServerStats* stats = (ISteamGameServerStats*) pointer;
		return stats->SetUserAchievement((uint64) steamIDUser, name);
	*/

	static private native boolean clearUserAchievement(long pointer, long steamIDUser, String name); /*
		ISteamGameServerStats* stats = (ISteamGameServerStats*) pointer;
		return stats->ClearUserAchievement((uint64) steamIDUser, name);
	*/

	static private native long storeUserStats(long pointer, long steamIDUser); /*
		ISteamGameServerStats* stats = (ISteamGameServerStats*) pointer;
		return stats->StoreUserStats((uint64) steamIDUser);
	*/

}
