package com.codedisaster.steamworks;

public class SteamMatchMakingKeyValuePair {

	private String key;
	private String value;

	public SteamMatchMakingKeyValuePair() {

	}

	public SteamMatchMakingKeyValuePair(String key, String value) {
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
