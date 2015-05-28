package com.codedisaster.steamworks;

import com.badlogic.gdx.jnigen.JniGenSharedLibraryLoader;
import com.badlogic.gdx.jnigen.SharedLibraryFinder;

import java.io.*;
import java.util.zip.ZipFile;

class SteamSharedLibraryLoader extends JniGenSharedLibraryLoader {

	private String firstCrc;
	private static boolean alreadyLoaded = false;

	private SteamSharedLibraryLoader(String nativesJar) {
		super(nativesJar);

		// custom library finder
		setSharedLibraryFinder(new SharedLibraryFinder() {
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
		});

		if (nativesJar != null) {
			try {
				firstCrc = crc(new FileInputStream(new File(nativesJar)));
			} catch (FileNotFoundException e) {
				firstCrc = Integer.toHexString(nativesJar.hashCode());
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

	static boolean extractAndLoadLibraries() {

		if (alreadyLoaded) {
			return true;
		}

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

		alreadyLoaded = true;
		return true;
	}
}
