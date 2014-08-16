package com.codedisaster.steamworks;

public enum SteamResult {

	OK(1),
	Fail(2);

	private int code;
	static private final SteamResult[] values = values();

	private SteamResult(int code) {
		this.code = code;
	}

	static public SteamResult byValue(int resultCode) {
		for (SteamResult v : values) {
			if (v.code == resultCode) {
				return v;
			}
		}
		return Fail;
	}
}
