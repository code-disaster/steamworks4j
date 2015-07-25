package com.codedisaster.steamworks;

public class SteamAPI {

	static private boolean isRunning = false;

	static public boolean init() {
		return init(null);
	}

	static public boolean init(String pathToNativeLibraries) {
		boolean fromJar = pathToNativeLibraries == null || pathToNativeLibraries.endsWith(".jar");

		isRunning = SteamSharedLibraryLoader.extractAndLoadLibraries(fromJar, pathToNativeLibraries);

		isRunning = isRunning && nativeInit();

		return isRunning;
	}

	static public void shutdown() {
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

	static public native boolean restartAppIfNecessary(long appId); /*
		return SteamAPI_RestartAppIfNecessary(appId);
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

	static native long getSteamAppsPointer(); /*
		return (long) SteamApps();
	*/

	static native long getSteamFriendsPointer(); /*
		return (long) SteamFriends();
	*/

	static native long getSteamNetworkingPointer(); /*
		return (long) SteamNetworking();
	*/

	static native long getSteamRemoteStoragePointer(); /*
		return (long) SteamRemoteStorage();
	*/

	static native long getSteamUGCPointer(); /*
		return (long) SteamUGC();
	*/

	static native long getSteamUserPointer(); /*
		return (long) SteamUser();
	*/

	static native long getSteamUserStatsPointer(); /*
		return (long) SteamUserStats();
	*/

	static native long getSteamUtilsPointer(); /*
		return (long) SteamUtils();
	*/

}
