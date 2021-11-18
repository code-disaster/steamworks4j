package com.codedisaster.steamworks;

class SteamGameServerAPINative {

	// @off

	/*JNI
		 #include <steam_gameserver.h>
		 static JavaVM* staticVM = 0;
	*/

	static native boolean nativeInit(int ip, short gamePort, short queryPort,
									 int serverMode, String versionString); /*

		 if (env->GetJavaVM(&staticVM) != 0) {
			 return false;
		 }

		 return SteamGameServer_Init(ip, gamePort, queryPort,
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

}
