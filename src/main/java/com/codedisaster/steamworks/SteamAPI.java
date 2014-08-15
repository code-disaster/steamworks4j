package com.codedisaster.steamworks;

public class SteamAPI {

	static public native boolean init(); /*
		return SteamAPI_Init();
	*/

	static public native void shutdown(); /*
		SteamAPI_Shutdown();
	*/

	static public native boolean isSteamRunning(); /*
		return SteamAPI_IsSteamRunning();
	*/

}
