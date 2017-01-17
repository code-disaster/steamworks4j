package com.codedisaster.steamworks;

abstract class SteamInterface {

	protected final long pointer;
	protected long callback;

	SteamInterface(long pointer) {
		this(pointer, 0L);
	}

	SteamInterface(long pointer, long callback) {
		if (pointer == 0L) {
			throw new RuntimeException("Steam interface created with null pointer." +
							" Always check result of SteamAPI.init(), or with SteamAPI.isSteamRunning()!");
		}
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

	protected static native void deleteCallback(long callback); /*
		delete (SteamCallbackAdapter*) callback;
	*/

}
