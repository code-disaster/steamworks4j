package com.codedisaster.steamworks;

class SteamGameServerAPINative {

	// @off

	/*JNI
		 #include <steam_gameserver.h>
		 static JavaVM* staticVM = 0;
	*/

	static native boolean nativeInit(int ip, short steamPort, short gamePort, short queryPort,
											 int serverMode, String versionString); /*

		 if (env->GetJavaVM(&staticVM) != 0) {
			 return false;
		 }

		 return SteamGameServer_Init(ip, steamPort, gamePort, queryPort,
			static_cast<EServerMode>(serverMode), versionString);
	*/

	static native void nativeShutdown(); /*
		SteamGameServer_Shutdown();
	*/

	static native void runCallbacks(); /*
		SteamGameServer_RunCallbacks();
	*/

	static native boolean isSecure(); /*
		return SteamGameServer_BSecure();
	*/

	static native long nativeGetSteamID(); /*
		return SteamGameServer_GetSteamID();
	*/

	static native long getSteamGameServerPointer(); /*
		return (intp) SteamGameServer();
	*/

	static native long getSteamGameServerStatsPointer(); /*
		return (intp) SteamGameServerStats();
	*/

	static native long getSteamGameServerHTTPPointer(); /*
		return (intp) SteamGameServerHTTP();
	*/

}
