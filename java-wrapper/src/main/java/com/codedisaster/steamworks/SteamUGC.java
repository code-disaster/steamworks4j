package com.codedisaster.steamworks;

public class SteamUGC {

	private final long pointer;

	public SteamUGC(long pointer, SteamUGCCallback callback) {
		this.pointer = pointer;
		registerCallback(new SteamUGCCallbackAdapter(callback));
	}

	static void dispose() {
		registerCallback(null);
	}

	/*JNI
		#include <steam_api.h>
		#include "SteamUGCCallback.h"

		static SteamUGCCallback* callback = NULL;
	*/

	static private native boolean registerCallback(SteamUGCCallbackAdapter javaCallback); /*
		if (callback != NULL) {
			delete callback;
			callback = NULL;
		}

		if (javaCallback != NULL) {
			callback = new SteamUGCCallback(env, javaCallback);
		}

		return callback != NULL;
	*/

}
