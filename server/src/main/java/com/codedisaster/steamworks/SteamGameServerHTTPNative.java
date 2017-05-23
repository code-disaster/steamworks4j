package com.codedisaster.steamworks;

class SteamGameServerHTTPNative {

	// @off

	/*JNI
		#include "SteamGameServerHTTPCallback.h"
	*/

	static native long createCallback(Object javaCallback); /*
		return (intp) new SteamGameServerHTTPCallback(env, javaCallback);
	*/

}
