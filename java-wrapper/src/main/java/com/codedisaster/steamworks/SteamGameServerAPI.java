package com.codedisaster.steamworks;

public class SteamGameServerAPI {

	public enum ServerMode {
		Invalid,
		NoAuthentication,
		Authentication,
		AuthenticationAndSecure
	}

	private static boolean isRunning = false;

	public static boolean init(int ip, short steamPort, short gamePort, short queryPort,
							   ServerMode serverMode, String versionString) throws SteamException {

		return init(null, ip, steamPort, gamePort, queryPort, serverMode, versionString);
	}

	public static boolean init(String libraryPath,
							   int ip, short steamPort, short gamePort, short queryPort,
							   ServerMode serverMode, String versionString) throws SteamException {

		SteamSharedLibraryLoader.loadLibraries(libraryPath);

		isRunning = nativeInit(ip, steamPort, gamePort, queryPort, serverMode.ordinal(), versionString);

		return isRunning;
	}

	public static void shutdown() {
		isRunning = false;
		nativeShutdown();
	}

	public static SteamID getSteamID() {
		return new SteamID(nativeGetSteamID());
	}

	// @off

	/*JNI
		 #include <steam_gameserver.h>

		 static JavaVM* staticVM = 0;
	*/

	private static native boolean nativeInit(int ip, short steamPort, short gamePort, short queryPort,
											 int serverMode, String versionString); /*

		 if (env->GetJavaVM(&staticVM) != 0) {
			 return false;
		 }

		 return SteamGameServer_Init(ip, steamPort, gamePort, queryPort,
			static_cast<EServerMode>(serverMode), versionString);
	*/

	private static native void nativeShutdown(); /*
		SteamGameServer_Shutdown();
	*/

	public static native void runCallbacks(); /*
		SteamGameServer_RunCallbacks();
	*/

	public static native boolean isSecure(); /*
		return SteamGameServer_BSecure();
	*/

	private static native long nativeGetSteamID(); /*
		return SteamGameServer_GetSteamID();
	*/

	static native long getSteamGameServerPointer(); /*
		return (intp) SteamGameServer();
	*/

	static native long getSteamGameServerNetworkingPointer(); /*
		return (intp) SteamGameServerNetworking();
	*/

	static native long getSteamGameServerStatsPointer(); /*
		return (intp) SteamGameServerStats();
	*/

	static native long getSteamGameServerHTTPPointer(); /*
		return (intp) SteamGameServerHTTP();
	*/

}
