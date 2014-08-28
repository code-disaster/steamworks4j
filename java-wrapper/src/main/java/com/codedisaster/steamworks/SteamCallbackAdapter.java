package com.codedisaster.steamworks;

abstract class SteamCallbackAdapter<T> {

	protected T callback;

	SteamCallbackAdapter(T callback) {
		this.callback = callback;
	}
}
