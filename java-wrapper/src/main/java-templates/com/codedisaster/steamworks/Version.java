package com.codedisaster.steamworks;

public final class Version {

	private static final String VERSION = "${project.version}";

	public static String getVersion() {
		return VERSION;
	}

}
