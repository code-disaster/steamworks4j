package com.codedisaster.steamworks;

import com.badlogic.gdx.utils.SharedLibraryLoader;

public class SteamLibraryLoaderGdx implements SteamLibraryLoader {

	private final SharedLibraryLoader gdxLoader = new SharedLibraryLoader() {

		/**
		 * Overrides this function because the name mapping is
		 * different to what libGDX uses.
		 */
		@Override
		public String mapLibraryName(String libraryName) {
			if (isWindows) {
				return super.mapLibraryName(libraryName);
			} else if (isLinux) {
				return "lib" + libraryName + ".so";
			} else if (isMac) {
				return "lib" + libraryName + ".dylib";
			}
			return libraryName;
		}
	};

	@Override
	public boolean loadLibrary(String libraryName) {

		try {
			gdxLoader.load(libraryName);
		} catch (Throwable t) {
			return false;
		}

		return true;
	}

}
