package com.codedisaster.steamworks;

import java.io.*;
import java.util.UUID;
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

		File extractLocation = discoverExtractLocation(
				"steamworks4j/" + libraryCrc, UUID.randomUUID().toString());

		if (extractLocation != null) {
			extractLocation = extractLocation.getParentFile();
		} else {
			throw new IOException();
		}

		for (String libraryName : libraryNames) {

			String librarySystemName = "";

			if (isWindows) {
				librarySystemName = getLibNameWindows(libraryName, is64Bit);
			} else if (isLinux) {
				librarySystemName = getLibNameLinux(libraryName, is64Bit);
			} else if (isMac) {
				librarySystemName = getLibNameMac(libraryName);
			}

			String fullPath;

			if (fromJar) {
				// extract library from Jar into temp folder
				fullPath = extractLibrary(extractLocation, librarySystemName);
			} else {
				// load native library directly from specified path
				fullPath = libraryPath + "/" + librarySystemName;
			}

			String absolutePath = new File(fullPath).getCanonicalPath();
			System.load(absolutePath);
		}
	}

	private String extractLibrary(File nativesPath, String sharedLibName) throws IOException {

		File nativeFile = new File(nativesPath, sharedLibName);

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

		output.close();
		zip.close();

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

				File extractLocation = discoverExtractLocation("steamworks4j", "steamworks4j-natives.jar");
				if (extractLocation == null) {
					return false;
				}

				libraryPath = extractLocation.getPath();

				try {

					InputStream input = SteamSharedLibraryLoader.class.getResourceAsStream("/steamworks4j-natives.jar");
					if (input == null) {
						return false;
					}

					FileOutputStream output = new FileOutputStream(extractLocation);

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

	private static File discoverExtractLocation(String folderName, String fileName) {

		// Java tmpdir

		File path = new File(System.getProperty("java.io.tmpdir") + "/" + folderName, fileName);
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

		// $home

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
