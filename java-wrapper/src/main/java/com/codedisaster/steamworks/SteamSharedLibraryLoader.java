package com.codedisaster.steamworks;

import java.io.*;
import java.util.UUID;

class SteamSharedLibraryLoader {

	enum PLATFORM {
		Windows,
		Linux,
		MacOS
	}

	private static PLATFORM OS;
	private static boolean IS_64_BIT;

	private static final String SHARED_LIBRARY_EXTRACT_DIRECTORY = System.getProperty(
			"com.codedisaster.steamworks.SharedLibraryExtractDirectory", "steamworks4j");

	private static final String SHARED_LIBRARY_EXTRACT_PATH = System.getProperty(
			"com.codedisaster.steamworks.SharedLibraryExtractPath", null);

	private static final String SDK_REDISTRIBUTABLE_BIN_PATH = System.getProperty(
			"com.codedisaster.steamworks.SDKRedistributableBinPath", "sdk/redistributable_bin");

	private static final String SDK_LIBRARY_PATH = System.getProperty(
			"com.codedisaster.steamworks.SDKLibraryPath", "sdk/public/steam/lib");

	static final boolean DEBUG = Boolean.parseBoolean(System.getProperty(
			"com.codedisaster.steamworks.Debug", "false"));

	{
		String osName = System.getProperty("os.name");
		String osArch = System.getProperty("os.arch");

		if (osName.contains("Windows")) {
			OS = PLATFORM.Windows;
		} else if (osName.contains("Linux")) {
			OS = PLATFORM.Linux;
		} else if (osName.contains("Mac")) {
			OS = PLATFORM.MacOS;
		} else {
			throw new RuntimeException("Unknown host architecture: " + osName + ", " + osArch);
		}

		IS_64_BIT = osArch.equals("amd64") || osArch.equals("x86_64");
	}

	public String getSdkLibraryPathMaster() {

		switch (OS) {
			case Windows:
				new Windows().getSdkLibraryPath();
				break;
			case Linux:
				new Linux().getSdkLibraryPath();
				break;
			case MacOS:
				new Macos().getSdkLibraryPath();
				break;
			default:
				return null;
		}
		return null;

	}

	private static String getPlatformLibName(String libName) {
		switch (OS) {
			case Windows:
				return libName + (IS_64_BIT ? "64" : "") + ".dll";
			case Linux:
				return "lib" + libName + ".so";
			case MacOS:
				return "lib" + libName + ".dylib";
		}

		throw new RuntimeException("Unknown host architecture");
	}

	static String getSdkRedistributableBinPath() {
		File path;
		switch (OS) {
			case Windows:
				path = new File(SDK_REDISTRIBUTABLE_BIN_PATH, IS_64_BIT ? "win64" : "");
				break;
			case Linux:
				path = new File(SDK_REDISTRIBUTABLE_BIN_PATH, "linux64");
				break;
			case MacOS:
				path = new File(SDK_REDISTRIBUTABLE_BIN_PATH, "osx");
				break;
			default:
				return null;
		}

		return path.exists() ? path.getPath() : null;
	}
	abstract class LibraryPath
	{
		abstract String getSdkLibraryPath();
	}
	class Windows extends LibraryPath
	{
		File path;
		String getSdkLibraryPath()
		{
			path = new File(SDK_LIBRARY_PATH, IS_64_BIT ? "win64" : "win32");
			return  path.toString();
		}
	}
	class Linux extends LibraryPath
	{
		File path;
		String getSdkLibraryPath()
		{
			path = new File(SDK_LIBRARY_PATH, "linux64");
			return  path.toString();
		}
	}
	class Macos extends LibraryPath
	{
		File path;
		String getSdkLibraryPath()
		{
			path = new File(SDK_LIBRARY_PATH, "osx");
			return  path.toString();
		}
	}

//	static String getSdkLibraryPath() {
//		File path;
//		switch (OS) {
//			case Windows:
//				path = new File(SDK_LIBRARY_PATH, IS_64_BIT ? "win64" : "win32");
//				break;
//			case Linux:
//				path = new File(SDK_LIBRARY_PATH, "linux64");
//				break;
//			case MacOS:
//				path = new File(SDK_LIBRARY_PATH, "osx");
//				break;
//			default:
//				return null;
//		}
//
//		return path.exists() ? path.getPath() : null;
//	}

	static void loadLibrary(String libraryName, String libraryPath) throws SteamException {
		extractionProcess obj = new extractionProcess();
		try {
			String librarySystemName = getPlatformLibName(libraryName);

			File librarySystemPath = discoverExtractLocation(
					SHARED_LIBRARY_EXTRACT_DIRECTORY + "/" + Version.getVersion(), librarySystemName);

			if (libraryPath == null) {
				// extract library from resource
				obj.extractLibrary(librarySystemPath, librarySystemName);
			} else {
				// read library from given path
				File librarySourcePath = new File(libraryPath, librarySystemName);

				if (OS != PLATFORM.Windows) {
					// on MacOS & Linux, "extract" (copy) from source location
					obj.extractLibrary(librarySystemPath, librarySourcePath);
				} else {
					// on Windows, load the library from the source location
					librarySystemPath = librarySourcePath;
				}
			}

			String absolutePath = librarySystemPath.getCanonicalPath();
			System.load(absolutePath);
		} catch (IOException e) {
			throw new SteamException(e);
		}
	}

	private static File discoverExtractLocation(String folderName, String fileName) throws IOException {

		File path;

		// system property

		if (SHARED_LIBRARY_EXTRACT_PATH != null) {
			path = new File(SHARED_LIBRARY_EXTRACT_PATH, fileName);
			if (canWrite(path)) {
				return path;
			}
		}

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

		throw new IOException("No suitable extraction path found");
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
	//This is the new class created which consists of te methods focused on extracting libraries.
	//By implementing this, we are using the extract class refcatoring technique
	public static class extractionProcess
	{
		private void extractLibrary(File librarySystemPath, String librarySystemName) throws IOException {
			extractLibrary(librarySystemPath,
					SteamSharedLibraryLoader.class.getResourceAsStream("/" + librarySystemName));
		}

		private void extractLibrary(File librarySystemPath, File librarySourcePath) throws IOException {
			extractLibrary(librarySystemPath, new FileInputStream(librarySourcePath));
		}

		private void extractLibrary(File librarySystemPath, InputStream input) throws IOException {
			if (input != null) {
				try (FileOutputStream output = new FileOutputStream(librarySystemPath)) {
					byte[] buffer = new byte[4096];
					while (true) {
						int length = input.read(buffer);
						if (length == -1) break;
						output.write(buffer, 0, length);
					}
					output.close();
				} catch (IOException e) {
				/*
					Extracting the library may fail, for example because 'nativeFile' already exists and is in
					use by another process. In this case, we fail silently and just try to load the existing file.
				 */
					if (!librarySystemPath.exists()) {
						throw e;
					}
				} finally {
					input.close();
				}
			} else {
				throw new IOException("Failed to read input stream for " + librarySystemPath.getCanonicalPath());
			}
		}
	}

}
