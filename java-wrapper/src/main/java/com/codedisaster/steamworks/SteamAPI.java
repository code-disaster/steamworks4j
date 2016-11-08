package com.codedisaster.steamworks;

import java.io.PrintStream;

public class SteamAPI {

	static private boolean isRunning = false;

	static public boolean init() throws SteamException {
		return init(null);
	}

	static public boolean init(String libraryPath) throws SteamException {

		SteamSharedLibraryLoader.loadLibraries(libraryPath);

		isRunning = nativeInit();

		return isRunning;
	}

	static public void shutdown() {
		isRunning = false;
		nativeShutdown();
	}

	static public boolean isSteamRunning() {
		return isRunning && isSteamRunningNative();
	}

	public static void printDebugInfo(PrintStream stream) {
		if (SteamSharedLibraryLoader.alreadyLoaded) {
			stream.println("  shared libraries loaded from: " + SteamSharedLibraryLoader.librarySystemPath);
		} else {
			stream.println("  shared libraries not loaded");
		}
		stream.println("  Steam API initialized: " + isRunning);
		stream.println("  Steam client active: " + isSteamRunning());
	}

	// @off

	/*JNI
		#include <steam_api.h>

		static JavaVM* staticVM = 0;
	*/

	static public native boolean restartAppIfNecessary(int appId); /*
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
		return (intp) SteamApps();
	*/

	static native long getSteamFriendsPointer(); /*
		return (intp) SteamFriends();
	*/

	static native long getSteamMatchmakingPointer(); /*
		return (intp) SteamMatchmaking();
	*/

	static native long getSteamNetworkingPointer(); /*
		return (intp) SteamNetworking();
	*/

	static native long getSteamRemoteStoragePointer(); /*
		return (intp) SteamRemoteStorage();
	*/

	static native long getSteamHTTPPointer(); /*
		return (intp) SteamHTTP();
	*/

	static native long getSteamUGCPointer(); /*
		return (intp) SteamUGC();
	*/

	static native long getSteamUserPointer(); /*
		return (intp) SteamUser();
	*/

	static native long getSteamUserStatsPointer(); /*
		return (intp) SteamUserStats();
	*/

	static native long getSteamUtilsPointer(); /*
		return (intp) SteamUtils();
	*/

}
