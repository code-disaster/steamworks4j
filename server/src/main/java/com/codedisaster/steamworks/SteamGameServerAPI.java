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

		if (libraryPath == null && SteamSharedLibraryLoader.DEBUG) {
			String sdkPath = SteamSharedLibraryLoader.getSdkRedistributableBinPath();
			SteamSharedLibraryLoader.loadLibrary("steam_api", sdkPath);
		} else {
			SteamSharedLibraryLoader.loadLibrary("steam_api", libraryPath);
		}

		SteamSharedLibraryLoader.loadLibrary("steamworks4j", libraryPath);

		isRunning = SteamGameServerAPINative.nativeInit(
				ip, steamPort, gamePort, queryPort, serverMode.ordinal(), versionString);

		return isRunning;
	}

	public static void shutdown() {
		isRunning = false;
		SteamGameServerAPINative.nativeShutdown();
	}

	public static void runCallbacks() {
		SteamGameServerAPINative.runCallbacks();
	}

	public static boolean isSecure() {
		return SteamGameServerAPINative.isSecure();
	}

	public static SteamID getSteamID() {
		return new SteamID(SteamGameServerAPINative.nativeGetSteamID());
	}

}
