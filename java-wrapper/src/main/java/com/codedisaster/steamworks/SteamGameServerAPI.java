package com.codedisaster.steamworks;

/**
 *
 * @author Francisco "Franz" Bischoff
 */
public class SteamGameServerAPI {

	public enum ServerMode {
		Invalid,
		NoAuthentication,
		Authentication,
		AuthenticationAndSecure
	}

	static private boolean isRunning = false;

	static public boolean init(int ip, short steamPort, short gamePort, short queryPort,
							   ServerMode serverMode, String versionString) {

		isRunning = SteamSharedLibraryLoader.extractAndLoadLibraries() && nativeInit(
				ip, steamPort, gamePort, queryPort, serverMode.ordinal(), versionString);

		return isRunning;
	}

	static public void shutdown() {
		SteamGameServer.dispose();
		SteamGameServerStats.dispose();
		SteamNetworking.dispose();

		isRunning = false;
		nativeShutdown();
	}

	static public SteamID getSteamID() {
		return new SteamID(nativeGetSteamID());
	}

	// @off

	/*JNI
		 #include <steam_gameserver.h>

		 static JavaVM* staticVM = 0;
	*/

	static private native boolean nativeInit(int ip, short steamPort, short gamePort, short queryPort,
											 int serverMode, String versionString); /*

		 if (env->GetJavaVM(&staticVM) != 0) {
			 return false;
		 }

		 return SteamGameServer_Init(ip, steamPort, gamePort, queryPort,
			static_cast<EServerMode>(serverMode), versionString);
	*/

	static private native void nativeShutdown(); /*
		SteamGameServer_Shutdown();
	*/

	static public native void runCallbacks(); /*
		SteamGameServer_RunCallbacks();
	*/

	static public native boolean isSecure(); /*
		return SteamGameServer_BSecure();
	*/

	static private native long nativeGetSteamID(); /*
		return SteamGameServer_GetSteamID();
	*/

	static public native long getSteamGameServerPointer(); /*
		return (long) SteamGameServer();
	*/

	static public native long getSteamGameServerNetworkingPointer(); /*
		return (long) SteamGameServerNetworking();
	*/

	static public native long getSteamGameServerStatsPointer(); /*
		return (long) SteamGameServerStats();
	*/

}
