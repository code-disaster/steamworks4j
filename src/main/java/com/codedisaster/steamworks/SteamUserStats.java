package com.codedisaster.steamworks;

public class SteamUserStats {

	private final long pointer;

	public SteamUserStats(long pointer, SteamUserStatsCallback callback) {
		this.pointer = pointer;
		registerCallback(callback);
	}

	static void dispose() {
		registerCallback(null);
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

	public int getNumAchievements() {
		return getNumAchievements(pointer);
	}

	public String getAchievementName(int index) {
		return getAchievementName(pointer, index);
	}

	/*JNI
		#include <steam_api.h>
		#include "SteamUserStatsCallback.h"

		static SteamUserStatsCallback* callback = NULL;
	*/

	static private native boolean registerCallback(SteamUserStatsCallback javaCallback); /*
		if (callback != NULL) {
			delete callback;
			callback = NULL;
		}

		if (javaCallback != NULL) {
			callback = new SteamUserStatsCallback(env, javaCallback);
		}

		return callback != NULL;
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

	static private native int getNumAchievements(long pointer); /*
		ISteamUserStats* stats = (ISteamUserStats*) pointer;
		return stats->GetNumAchievements();
	*/

	static private native String getAchievementName(long pointer, int index); /*
		ISteamUserStats* stats = (ISteamUserStats*) pointer;
		jstring name = env->NewStringUTF(stats->GetAchievementName(index));
		return name;
	*/
}
