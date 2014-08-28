package com.codedisaster.steamworks;

abstract class SteamInterface {

	protected final long pointer;

	SteamInterface(long pointer) {
		this.pointer = pointer;
	}

}
