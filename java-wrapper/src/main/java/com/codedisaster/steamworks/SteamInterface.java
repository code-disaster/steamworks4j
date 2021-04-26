package com.codedisaster.steamworks;

import java.nio.Buffer;

abstract class SteamInterface {

	protected long callback;

	SteamInterface() {
		this(0L);
	}

	SteamInterface(long callback) {
		this.callback = callback;
	}

	void setCallback(long callback) {
		this.callback = callback;
	}

	public void dispose() {
		deleteCallback(callback);
	}

	void checkBuffer(Buffer buffer) throws SteamException {
		if (!buffer.isDirect()) {
			throw new SteamException("Direct buffer required.");
		}
	}

	void checkArray(byte[] array, int length) throws SteamException {
		if (array.length < length) {
			throw new SteamException("Array too small, " + array.length + " found but " + length + " expected.");
		}
	}

	/*JNI
		#include "SteamCallbackAdapter.h"
	*/

	protected static native void deleteCallback(long callback); /*
		delete (SteamCallbackAdapter*) callback;
	*/

}
