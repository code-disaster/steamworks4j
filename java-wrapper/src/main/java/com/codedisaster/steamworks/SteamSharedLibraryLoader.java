package com.codedisaster.steamworks;

import java.io.*;
import java.util.zip.CRC32;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

class SteamSharedLibraryLoader {

	private final String libraryPath;
	private final boolean fromJar;

	private String libraryCrc;

	private static boolean alreadyLoaded = false;

	private SteamSharedLibraryLoader(String libraryPath, boolean fromJar) {
		this.libraryPath = libraryPath;
		this.fromJar = fromJar;

		if (libraryPath != null && fromJar) {
			try {
				libraryCrc = crc(new FileInputStream(new File(libraryPath)));
			} catch (FileNotFoundException e) {
				libraryCrc = Integer.toHexString(libraryPath.hashCode());
			}
		}
	}

	private String getLibNameWindows(String sharedLibName, boolean is64Bit) {
		return sharedLibName + (is64Bit ? "64" : "") + ".dll";
	}

	private String getLibNameLinux(String sharedLibName, boolean is64Bit) {
		return "lib" + sharedLibName + (is64Bit ? "64" : "") + ".so";
	}

	private String getLibNameMac(String sharedLibName) {
		return "lib" + sharedLibName + ".dylib";
	}

	private void loadLibraries(String... libraryNames) throws IOException {
		String osName = System.getProperty("os.name");
		String osArch = System.getProperty("os.arch");

		boolean isWindows = osName.contains("Windows");
		boolean isLinux = osName.contains("Linux");
		boolean isMac = osName.contains("Mac");

		boolean is64Bit = osArch.equals("amd64") || osArch.equals("x86_64");

		for (String libraryName : libraryNames) {

			String librarySystemName = "";

			if (isWindows) {
				librarySystemName = getLibNameWindows(libraryName, is64Bit);
			}
			if (isLinux) {
				librarySystemName = getLibNameLinux(libraryName, is64Bit);
			}
			if (isMac) {
				librarySystemName = getLibNameMac(libraryName);
			}

			String fullPath;

			if (fromJar) {
				// extract library from Jar into temp folder
				fullPath = extractLibrary(librarySystemName);
			} else {
				// load native library directly from specified path
				fullPath = libraryPath + "/" + librarySystemName;
			}

			String absolutePath = new File(fullPath).getCanonicalPath();
			System.load(absolutePath);
		}
	}

	private String extractLibrary(String sharedLibName) throws IOException {

		File nativesPath = new File(System.getProperty("java.io.tmpdir") + "/steamworks4j/" + libraryCrc);
		File nativeFile = new File(nativesPath, sharedLibName);

		if (!nativesPath.exists()) {
			if (!nativesPath.mkdirs()) {
				throw new IOException("Error creating temp folder: " + nativesPath.getCanonicalPath());
			}
		}

		ZipFile zip = new ZipFile(libraryPath);
		ZipEntry entry = zip.getEntry(sharedLibName);
		InputStream input = zip.getInputStream(entry);

		if (input == null) {
			throw new IOException("Error extracting " + sharedLibName + " from " + libraryPath);
		}

		FileOutputStream output = new FileOutputStream(nativeFile);
		byte[] buffer = new byte[4096];
		while (true) {
			int length = input.read(buffer);
			if (length == -1) break;
			output.write(buffer, 0, length);
		}
		input.close();
		output.close();

		return nativeFile.getAbsolutePath();
	}

	private String crc(InputStream input) {
		CRC32 crc = new CRC32();
		byte[] buffer = new byte[4096];
		try {
			while (true) {
				int length = input.read(buffer);
				if (length == -1) break;
				crc.update(buffer, 0, length);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				input.close();
			} catch (IOException ignored) {
			}
		}
		return Long.toHexString(crc.getValue());
	}

	static boolean extractAndLoadLibraries(boolean fromJar, String libraryPath) {

		if (alreadyLoaded) {
			return true;
		}

		SteamSharedLibraryLoader loader;

		if (fromJar) {

			if (libraryPath == null) {

				// if no library path is specified, extract steamworks4j-natives.jar from
				// resource path into temporary folder

				libraryPath = System.getProperty("java.io.tmpdir") + "/steamworks4j/steamworks4j-natives.jar";

				File libraryDirectory = new File(libraryPath).getParentFile();
				if (!libraryDirectory.exists()) {
					if (!libraryDirectory.mkdirs()) {
						return false;
					}
				}

				try {

					InputStream input = SteamSharedLibraryLoader.class.getResourceAsStream("/steamworks4j-natives.jar");
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

		}

		loader = new SteamSharedLibraryLoader(libraryPath, fromJar);

		try {
			loader.loadLibraries("steam_api", "steamworks4j");
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		alreadyLoaded = true;
		return true;
	}
}
