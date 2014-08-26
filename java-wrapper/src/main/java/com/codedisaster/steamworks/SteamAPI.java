package com.codedisaster.steamworks;

import java.io.*;

public class SteamAPI {

	static public boolean init() {
		if (!loadLibraries()) {
			return false;
		}
		return nativeInit();
	}

	static public void shutdown() {
		SteamRemoteStorage.dispose();
		SteamUserStats.dispose();
		SteamUGC.dispose();
		nativeShutdown();
	}

	static private boolean loadLibraries() {

		String libraryPath = System.getProperty("java.io.tmpdir") + "/steamworks4j/steamworks4j-natives.jar";

		File libraryDirectory = new File(libraryPath).getParentFile();
		if (!libraryDirectory.exists()) {
			if (!libraryDirectory.mkdirs()) {
				return false;
			}
		}

		try {

			InputStream input = SteamAPI.class.getResourceAsStream("/steamworks4j-natives.jar");
			FileOutputStream output = new FileOutputStream(new File(libraryPath));

			byte[] cache = new byte[4096];
			int length;

			do {
				length = input.read(cache);
				if (length > 0) {
					output.write(cache, 0, length);
				}
			} while (length > 0);

			input.close();
			output.close();

		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		SteamSharedLibraryLoader loader = new SteamSharedLibraryLoader(libraryPath);

		loader.load("steam_api");
		loader.load("steamworks4j");

		return true;
	}

	// @off

	/*JNI
		#include <steam_api.h>

		static JavaVM* staticVM = 0;
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

	static public native boolean isSteamRunning(); /*
		return SteamAPI_IsSteamRunning();
	*/

	static public native long getSteamUserStatsPointer(); /*
		return (long) SteamUserStats();
	*/

	static public native long getSteamRemoteStoragePointer(); /*
		return (long) SteamRemoteStorage();
	*/

	static public native long getSteamUGCPointer(); /*
		return (long) SteamUGC();
	*/

}
