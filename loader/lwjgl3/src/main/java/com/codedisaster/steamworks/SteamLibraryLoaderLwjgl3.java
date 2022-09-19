package com.codedisaster.steamworks;

import org.lwjgl.system.Library;
import org.lwjgl.system.Platform;

public class SteamLibraryLoaderLwjgl3 implements SteamLibraryLoader {

	@Override
	public void setLibraryPath(String libraryPath) {
		System.setProperty("org.lwjgl.librarypath", libraryPath);
	}

	@Override
	public boolean loadLibrary(String libraryName) {

		Platform os = Platform.get();
		Platform.Architecture arch = Platform.getArchitecture();

		// On Windows 64-bit, Steam libraries are suffixed with "64".

		if (os == Platform.WINDOWS && arch == Platform.Architecture.X64) {
			libraryName = libraryName + "64";
		}

		// Let LWJGL3 do its magic

		try {
			Library.loadSystem("com.codedisaster.steamworks", libraryName);
		} catch (Throwable t) {
			return false;
		}

		return true;
	}

}
