package com.codedisaster.steamworks;

public class SteamUserStats {

	private final long pointer;

	public SteamUserStats(long pointer, SteamUserStatsCallback callback) {
		this.pointer = pointer;
		registerCallback(callback);
	}

	static public void dispose() {
		registerCallback(null);
	}

	public boolean requestCurrentStats() {
		return requestCurrentStats(pointer);
	}

	public boolean getStat(String name, int[] value) {
		return getStat(pointer, name, value);
	}

	public boolean setStat(String name, int value) {
		return setStat(pointer, name, value);
	}

	public boolean getStat(String name, float[] value) {
		return getStat(pointer, name, value);
	}

	public boolean setStat(String name, float value) {
		return setStat(pointer, name, value);
	}

	public boolean storeStats() {
		return storeStats(pointer);
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

	static private native boolean storeStats(long pointer); /*
		ISteamUserStats* stats = (ISteamUserStats*) pointer;
		return stats->StoreStats();
	*/

}
