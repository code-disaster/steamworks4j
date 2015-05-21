package com.codedisaster.steamworks;

public class SteamAPI {

	static private boolean isRunning = false;

	static public boolean init() {
		isRunning = SteamSharedLibraryLoader.extractAndLoadLibraries() && nativeInit();
		return isRunning;
	}

	static public void shutdown() {
		SteamApps.dispose();
		SteamFriends.dispose();
		SteamNetworking.dispose();
		SteamRemoteStorage.dispose();
		SteamUGC.dispose();
		SteamUser.dispose();
		SteamUserStats.dispose();
		SteamUtils.dispose();

		isRunning = false;
		nativeShutdown();
	}

	static public boolean isSteamRunning() {
		return isRunning && isSteamRunningNative();
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

	static private native boolean isSteamRunningNative(); /*
		return SteamAPI_IsSteamRunning();
	*/

	static public native long getSteamAppsPointer(); /*
		return (long) SteamApps();
	*/

	static public native long getSteamFriendsPointer(); /*
		return (long) SteamFriends();
	*/

	static public native long getSteamNetworkingPointer(); /*
		return (long) SteamNetworking();
	*/

	static public native long getSteamUserPointer(); /*
		return (long) SteamUser();
	*/

	static public native long getSteamUtilsPointer(); /*
		return (long) SteamUtils();
	*/

	static public native long getSteamUserStatsPointer(); /*
		return (long) SteamUserStats();
	*/

	static public native long getSteamRemoteStoragePointer(); /*
		return (long) SteamRemoteStorage();
	*/

	static public native long getSteamUGCPointer(); /*
		return (long) SteamUGC();
	*/

}
