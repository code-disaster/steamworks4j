package com.codedisaster.steamworks;

import java.io.PrintStream;

public class SteamAPI {

	private static boolean isRunning = false;
	private static boolean isNativeAPILoaded = false;

	public static boolean loadLibraries(SteamLibraryLoader loader) {

		if (!isNativeAPILoaded) {
			isNativeAPILoaded = loader.loadLibrary("steam_api");
			isNativeAPILoaded = isNativeAPILoaded && loader.loadLibrary("steamworks4j");
		}

		return isNativeAPILoaded;
	}

	public static boolean restartAppIfNecessary(int appId) throws SteamException {

		if (!isNativeAPILoaded) {
			throw new SteamException("Native libraries not loaded.\nEnsure to call SteamAPI.loadLibraries() first!");
		}

		return nativeRestartAppIfNecessary(appId);
	}

	public static boolean init() throws SteamException {

		if (!isNativeAPILoaded) {
			throw new SteamException("Native libraries not loaded.\nEnsure to call SteamAPI.loadLibraries() first!");
		}

		isRunning = nativeInit();

		return isRunning;
	}

	public static void shutdown() {
		isRunning = false;
		nativeShutdown();
	}

	/**
	 * According to the documentation, SteamAPI_IsSteamRunning() should "only be used in very
	 * specific cases". Also, there seems to be an issue with leaking OS resources on Mac OS X.
	 * For these reasons, the default behaviour of this function has been changed to <i>not</i>
	 * call the native function.
	 *
	 * @see SteamAPI#isSteamRunning(boolean)
	 */
	public static boolean isSteamRunning() {
		return isSteamRunning(false);
	}

	public static boolean isSteamRunning(boolean checkNative) {
		return isRunning && (!checkNative || isSteamRunningNative());
	}

	public static void printDebugInfo(PrintStream stream) {
		stream.println("  Steam API initialized: " + isRunning);
		stream.println("  Steam client active: " + isSteamRunning());
	}

	static boolean isIsNativeAPILoaded() {
		return isNativeAPILoaded;
	}

	// @off

	/*JNI
		#include <steam_api.h>

		static JavaVM* staticVM = 0;
	*/

	private static native boolean nativeRestartAppIfNecessary(int appId); /*
		return SteamAPI_RestartAppIfNecessary(appId);
	*/

	public static native void releaseCurrentThreadMemory(); /*
		SteamAPI_ReleaseCurrentThreadMemory();
	*/

	private static native boolean nativeInit(); /*
		if (env->GetJavaVM(&staticVM) != 0) {
			return false;
		}

		return SteamAPI_Init();
	*/

	private static native void nativeShutdown(); /*
		SteamAPI_Shutdown();
	*/

	public static native void runCallbacks(); /*
		SteamAPI_RunCallbacks();
	*/

	private static native boolean isSteamRunningNative(); /*
		return SteamAPI_IsSteamRunning();
	*/

}
