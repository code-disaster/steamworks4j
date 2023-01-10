package com.codedisaster.steamworks;

class SteamGameServerNetworkingNative {

	// @off

	/*JNI
		#include "SteamGameServerNetworkingCallback.h"
	*/

	static native long createCallback(Object javaCallback); /*
		return (intp) new SteamGameServerNetworkingCallback(env, javaCallback);
	*/

}
