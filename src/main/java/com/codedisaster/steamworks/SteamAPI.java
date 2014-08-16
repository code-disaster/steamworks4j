package com.codedisaster.steamworks;

public class SteamAPI {

	// @off

	/*JNI
		#include <steam_api.h>

		static JavaVM* staticVM = 0;
	*/

	static public native boolean init(); /*
		return SteamAPI_Init();
	*/

	static public native void shutdown(); /*
		SteamAPI_Shutdown();
	*/

	static public native void runCallbacks(); /*
		SteamAPI_RunCallbacks();
	*/

	static public native boolean isSteamRunning(); /*
		return SteamAPI_IsSteamRunning();
	*/

	static public native long getSteamUserPointer(); /*
		return (long) SteamUser();
	*/

	static public native long getSteamUserStatsPointer(); /*
		return (long) SteamUserStats();
	*/

}
