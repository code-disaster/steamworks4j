package com.codedisaster.steamworks;

public class SteamAPI {

	static public boolean init() {
		return nativeInit();
	}

	static public void shutdown() {
		SteamUserStats.dispose();
		SteamRemoteStorage.dispose();
		nativeShutdown();
	}

	// @off

	/*JNI
		#include <steam_api.h>

		static JavaVM* staticVM = 0;
	*/

	static private native boolean nativeInit(); /*
		if (env->GetJavaVM(&staticVM) != 0) {
			return false;
		}

		return SteamAPI_Init();
	*/

	static private native void nativeShutdown(); /*
		SteamAPI_Shutdown();
	*/

	static public native void runCallbacks(); /*
		SteamAPI_RunCallbacks();
	*/

	static public native boolean isSteamRunning(); /*
		return SteamAPI_IsSteamRunning();
	*/

	static public native long getSteamUserStatsPointer(); /*
		return (long) SteamUserStats();
	*/

	static public native long getSteamRemoteStoragePointer(); /*
		return (long) SteamRemoteStorage();
	*/

}
