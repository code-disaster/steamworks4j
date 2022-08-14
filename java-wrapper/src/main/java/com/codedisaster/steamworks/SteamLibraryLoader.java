package com.codedisaster.steamworks;

public interface SteamLibraryLoader {

	default void configure(String libraryPath) {

	}

	/**
	 * The default implementation does nothing. This can be used to bypass
	 * library loading altogether, and let the calling application manage
	 * this task itself.
	 */
	default boolean loadLibrary(String libraryName) {
		return true;
	}

}
