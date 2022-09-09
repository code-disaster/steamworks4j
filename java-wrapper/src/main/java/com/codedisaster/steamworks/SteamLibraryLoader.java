package com.codedisaster.steamworks;

/**
 * See the steamworks4j-gdx and steamworks4j-lwjgl3 modules for sample
 * implementations of this interface.
 * <p>
 * The default implementation does nothing. This can be used to bypass
 * library loading altogether, and let the calling application manage
 * this task itself.
 */
public interface SteamLibraryLoader {

	default void setLibraryPath(String libraryPath) {

	}

	default boolean loadLibrary(String libraryName) {
		return true;
	}

}
