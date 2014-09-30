package com.codedisaster.steamworks;

public class SteamID {
	private final long id;

	SteamID(long id) {
		this.id = id;
	}

	public int getAccountID() {
		return (int) (id % (1L << 32));
	}
}
