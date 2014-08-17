package com.codedisaster.steamworks;

import com.badlogic.gdx.jnigen.JniGenSharedLibraryLoader;
import com.badlogic.gdx.jnigen.SharedLibraryFinder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.zip.ZipFile;

public class SteamSharedLibraryLoader extends JniGenSharedLibraryLoader {

	private String firstCrc;

	public SteamSharedLibraryLoader(String nativesJar) {
		super(nativesJar);

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
				return sharedLibName;
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
}
