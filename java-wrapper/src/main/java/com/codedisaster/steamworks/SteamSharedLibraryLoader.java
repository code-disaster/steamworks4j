package com.codedisaster.steamworks;

import com.badlogic.gdx.jnigen.JniGenSharedLibraryLoader;
import com.badlogic.gdx.jnigen.SharedLibraryFinder;

import java.io.*;
import java.util.zip.ZipFile;

class SteamSharedLibraryLoader extends JniGenSharedLibraryLoader {

	private String firstCrc;

	private SharedLibraryFinder libraryFinder = new SharedLibraryFinder() {
		@Override
		public String getSharedLibraryNameWindows(String sharedLibName, boolean is64Bit, ZipFile nativesJar) {
			return sharedLibName + (is64Bit ? "64" : "") + ".dll";
		}

		@Override
		public String getSharedLibraryNameLinux(String sharedLibName, boolean is64Bit, ZipFile nativesJar) {
			return "lib" + sharedLibName + (is64Bit ? "64" : "") + ".so";
		}

		@Override
		public String getSharedLibraryNameMac(String sharedLibName, boolean is64Bit, ZipFile nativesJar) {
			return "lib" + sharedLibName + ".dylib";
		}

		@Override
		public String getSharedLibraryNameAndroid(String sharedLibName, ZipFile nativesJar) {
			return null;
		}
	};

	private static boolean alreadyLoaded = false;

	private SteamSharedLibraryLoader() {
		super();
	}

	private SteamSharedLibraryLoader(String nativesJar) {
		super(nativesJar);

		setSharedLibraryFinder(libraryFinder);

		if (nativesJar != null) {
			try {
				firstCrc = crc(new FileInputStream(new File(nativesJar)));
			} catch (FileNotFoundException e) {
				firstCrc = Integer.toHexString(nativesJar.hashCode());
			}
		}
	}

	private void loadLibraries(String libraryPath, boolean fromJar, String... libraryNames) throws IOException {
		String osName = System.getProperty("os.name");
		String osArch = System.getProperty("os.arch");

		boolean isWindows = osName.contains("Windows");
		boolean isLinux = osName.contains("Linux");
		boolean isMac = osName.contains("Mac");

		boolean is64Bit = osArch.equals("amd64") || osArch.equals("x86_64");

		for (String libraryName : libraryNames) {
			if (fromJar) {
				load(libraryName);
			} else {
				String fullName = "";

				if (isWindows) {
					fullName = libraryFinder.getSharedLibraryNameWindows(libraryName, is64Bit, null);
				}
				if (isLinux) {
					fullName = libraryFinder.getSharedLibraryNameLinux(libraryName, is64Bit, null);
				}
				if (isMac) {
					fullName = libraryFinder.getSharedLibraryNameMac(libraryName, is64Bit, null);
				}

				String fullPath = libraryPath + "/" + fullName;
				String absolutePath = new File(fullPath).getCanonicalPath();
				System.load(absolutePath);
			}
		}
	}

	@Override
	public String crc(InputStream input) {
		if (firstCrc != null) {
			return firstCrc;
		}
		firstCrc = super.crc(input);
		return firstCrc;
	}

	static boolean extractAndLoadLibraries(boolean fromJar, String libraryPath) {

		if (alreadyLoaded) {
			return true;
		}

		SteamSharedLibraryLoader loader;

		if (fromJar) {

			if (libraryPath == null) {

				libraryPath = System.getProperty("java.io.tmpdir") + "/steamworks4j/steamworks4j-natives.jar";

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

			}

			loader = new SteamSharedLibraryLoader(libraryPath);

		} else {

			loader = new SteamSharedLibraryLoader();

		}

		try {
			loader.loadLibraries(libraryPath, fromJar, "steam_api", "steamworks4j");
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		alreadyLoaded = true;
		return true;
	}
}
