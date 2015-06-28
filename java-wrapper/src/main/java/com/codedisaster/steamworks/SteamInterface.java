package com.codedisaster.steamworks;

abstract class SteamInterface {

	protected final long pointer;
	protected long callback;

	SteamInterface(long pointer) {
		this(pointer, 0l);
	}

	SteamInterface(long pointer, long callback) {
		this.pointer = pointer;
		this.callback = callback;
	}

	void setCallback(long callback) {
		this.callback = callback;
	}

	public void dispose() {
		deleteCallback(callback);
	}

	/*JNI
		#include "SteamCallbackAdapter.h"
	*/

	static protected native void deleteCallback(long callback); /*
		delete (SteamCallbackAdapter*) callback;
	*/

}
