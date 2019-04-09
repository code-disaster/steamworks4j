package com.codedisaster.steamworks;

public class SteamGameServerAPI {

	public enum ServerMode {
		Invalid,
		NoAuthentication,
		Authentication,
		AuthenticationAndSecure
	}

	private static boolean isRunning = false;
	private static boolean isNativeAPILoaded = false;

	public static void loadLibraries() throws SteamException {
		loadLibraries(null);
	}
	
	public static void loadLibraries(String libraryPath) throws SteamException {

		if (isNativeAPILoaded) {
			return;
		}

		SteamAPI.loadLibraries(libraryPath);

		SteamSharedLibraryLoader.loadLibrary("steamworks4j-server", libraryPath);

		isNativeAPILoaded = true;
	}

	public static void skipLoadLibraries() {
		isNativeAPILoaded = true;
	}

	public static boolean init(int ip, short steamPort, short gamePort, short queryPort,
							   ServerMode serverMode, String versionString) throws SteamException {

		if (!isNativeAPILoaded) {
			throw new SteamException("Native server libraries not loaded.\nEnsure to call SteamGameServerAPI.loadLibraries() first!");
		}

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
