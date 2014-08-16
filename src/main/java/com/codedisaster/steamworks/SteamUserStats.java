package com.codedisaster.steamworks;

public class SteamUserStats {

	private final long pointer;

	public SteamUserStats(long pointer) {
		this.pointer = pointer;
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

	/*JNI
		#include <steam_api.h>
	*/

	private native boolean getStat(long pointer, String name, int[] value); /*
		ISteamUserStats* stats = (ISteamUserStats*) pointer;
		return stats->GetStat(name, &((int32*) value)[0]);
	*/

	private native boolean setStat(long pointer, String name, int value); /*
		ISteamUserStats* stats = (ISteamUserStats*) pointer;
		return stats->SetStat(name, (int32) value);
	*/

	private native boolean getStat(long pointer, String name, float[] value); /*
		ISteamUserStats* stats = (ISteamUserStats*) pointer;
		return stats->GetStat(name, &value[0]);
	*/

	private native boolean setStat(long pointer, String name, float value); /*
		ISteamUserStats* stats = (ISteamUserStats*) pointer;
		return stats->SetStat(name, value);
	*/

}
