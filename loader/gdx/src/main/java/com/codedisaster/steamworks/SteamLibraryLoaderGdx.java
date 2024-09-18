package com.codedisaster.steamworks;

import com.badlogic.gdx.utils.SharedLibraryLoader;

import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SteamLibraryLoaderGdx implements SteamLibraryLoader {

	private Path libraryPath = null;

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

		/**
		 * The libGDX loader doesn't expose an option to load libraries from
		 * an external path. Let's do a simple workaround right here. It tries
		 * to call System.load() with an absolute path to the library.
		 * <p>
		 * If this fails, the original load-from-resource call is made.
		 * <p>
		 * Maybe not quite fit for production use.
		 */
		@Override
		public void load(String libraryName) {
			synchronized (SharedLibraryLoader.class) {
				if (isLoaded(libraryName)) return;
				if (libraryPath != null) {
					String platformName = mapLibraryName(libraryName);
					String absolutePath = libraryPath.resolve(platformName).toString();
					try {
						System.load(absolutePath);
						setLoaded(libraryName);
						return;
					} catch (Throwable ignore) {
					}
				}
				super.load(libraryName);
			}
		}

		/**
		 * Behind the scenes SharedLibraryLoader extracts libraries from resources
		 * into separate directories based on the CRC checksum of the files.
		 * <p>
		 * On macOS, when loading libsteamworks4j.dylib, libsteam_api.dylib needs
		 * to be in the same directory due to the install-name of libsteam_api.dylib.
		 * <p>
		 * This override forces all extracted libraries into the same directory,
		 * by re-using the CRC of the first library that has been loaded.
		 */
		@Override
		public String crc(InputStream input) {
			if (isMac && !previousCrc.isEmpty()) {
				closeQuietly(input);
				return previousCrc;
			}
			previousCrc = super.crc(input);
			return previousCrc;
		}

		private String previousCrc = "";
	};

	@Override
	public void setLibraryPath(String libraryPath) {
		this.libraryPath = Paths.get(libraryPath).toAbsolutePath();
	}

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
