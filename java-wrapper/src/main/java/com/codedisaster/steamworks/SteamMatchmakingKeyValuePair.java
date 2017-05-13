package com.codedisaster.steamworks;

public class SteamMatchmakingKeyValuePair {

	private String key;
	private String value;

	public SteamMatchmakingKeyValuePair() {

	}

	public SteamMatchmakingKeyValuePair(String key, String value) {
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}

}
