package com.codedisaster.steamworks;

import java.io.*;
import java.util.UUID;
import java.util.zip.*;

class SteamSharedLibraryLoader {

	private final String libraryPath;

	static boolean alreadyLoaded = false;
	static File librarySystemPath;

	private static final String extractSubFolder = "steamworks4j/";

	private SteamSharedLibraryLoader(String libraryPath) {
		this.libraryPath = libraryPath;
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

		String[] librarySystemNames = new String[libraryNames.length];

		for (int i = 0; i < libraryNames.length; i++) {
			if (isWindows) {
				librarySystemNames[i] = getLibNameWindows(libraryNames[i], is64Bit);
			} else if (isLinux) {
				librarySystemNames[i] = getLibNameLinux(libraryNames[i], is64Bit);
			} else if (isMac) {
				librarySystemNames[i] = getLibNameMac(libraryNames[i]);
			} else {
				throw new IOException("Unrecognized system architecture: " + osName + ", " + osArch);
			}
		}

		if (libraryPath == null) {

			String libraryCrc = ".nohash";
			CRC32 crc = new CRC32();

			for (String librarySystemName : librarySystemNames) {
				libraryCrc = crc(crc, getClass().getResourceAsStream("/" + librarySystemName));
			}

			librarySystemPath = discoverExtractLocation(
					extractSubFolder + libraryCrc, UUID.randomUUID().toString());

			if (librarySystemPath == null) {
				throw new IOException("Failed to create temp folder to extract native libraries");
			}

			librarySystemPath = librarySystemPath.getParentFile();
		} else {
			librarySystemPath = new File(libraryPath);
		}

		for (String librarySystemName : librarySystemNames) {

			String fullPath;

			if (libraryPath == null) {
				// extract library from Jar into temp folder
				fullPath = extractLibrary(librarySystemPath, librarySystemName);
			} else {
				// load native library directly from specified path
				fullPath = librarySystemPath + "/" + librarySystemName;
			}

			String absolutePath = new File(fullPath).getCanonicalPath();

			System.load(absolutePath);
		}
	}

	private String extractLibrary(File nativesPath, String sharedLibName) throws IOException {

		File nativeFile = new File(nativesPath, sharedLibName);

		InputStream input;
		ZipFile zip = null;

		if (libraryPath != null) {
			zip = new ZipFile(libraryPath);
			ZipEntry entry = zip.getEntry(sharedLibName);
			input = zip.getInputStream(entry);
		} else {
			input = SteamSharedLibraryLoader.class.getResourceAsStream("/" + sharedLibName);
		}

		if (input == null) {
			throw new IOException("Error extracting " + sharedLibName + " from " +
					(libraryPath != null ? libraryPath : "resources"));
		}

		/*
			Extracting the library may fail, for example because 'nativeFile' already exists and is in
			use by another process. In this case, we fail silently and just try to load the existing file.
		 */

		try {

			FileOutputStream output = new FileOutputStream(nativeFile);

			byte[] buffer = new byte[4096];
			while (true) {
				int length = input.read(buffer);
				if (length == -1) break;
				output.write(buffer, 0, length);
			}

			output.close();

		} catch (IOException e) {
			if (!nativeFile.exists()) {
				throw e;
			}
		}

		input.close();

		if (zip != null) {
			zip.close();
		}

		return nativeFile.getAbsolutePath();
	}

	private String crc(CRC32 crc, InputStream input) {
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

	static boolean loadLibraries(String libraryPath) throws SteamException {

		if (alreadyLoaded) {
			return true;
		}

		SteamSharedLibraryLoader loader = new SteamSharedLibraryLoader(libraryPath);

		try {
			loader.loadLibraries("steam_api", "steamworks4j");
		} catch (Throwable t) {
			throw new SteamException(t);
		}

		alreadyLoaded = true;
		return true;
	}

	private static File discoverExtractLocation(String folderName, String fileName) {

		File path;

		// Java tmpdir

		path = new File(System.getProperty("java.io.tmpdir") + "/" + folderName, fileName);
		if (canWrite(path)) {
			return path;
		}

		// NIO temp file

		try {
			File file = File.createTempFile(folderName, null);
			if (file.delete()) {
				// uses temp file path as destination folder
				path = new File(file, fileName);
				if (canWrite(path)) {
					return path;
				}
			}
		} catch (IOException ignored) {

		}

		// user home

		path = new File(System.getProperty("user.home") + "/." + folderName, fileName);
		if (canWrite(path)) {
			return path;
		}

		// working directory

		path = new File(".tmp/" + folderName, fileName);
		if (canWrite(path)) {
			return path;
		}

		return null;
	}

	private static boolean canWrite(File file) {

		File folder = file.getParentFile();

		if (file.exists()) {
			if (!file.canWrite() || !canExecute(file)) {
				return false;
			}
		} else {
			if (!folder.exists()) {
				if (!folder.mkdirs()) {
					return false;
				}
			}
			if (!folder.isDirectory()) {
				return false;
			}
		}

		File testFile = new File(folder, UUID.randomUUID().toString());

		try {
			new FileOutputStream(testFile).close();
			return canExecute(testFile);
		} catch (IOException e) {
			return false;
		} finally {
			testFile.delete();
		}
	}

	private static boolean canExecute(File file) {

		try {
			if (file.canExecute()) {
				return true;
			}

			if (file.setExecutable(true)) {
				return file.canExecute();
			}
		} catch (Exception ignored) {

		}

		return false;
	}

}
