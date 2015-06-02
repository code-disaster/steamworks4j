package com.codedisaster.steamworks;

public class SteamID extends SteamNativeHandle {

	private static long InvalidSteamID = getInvalidSteamID();

	public SteamID() {
		super(InvalidSteamID);
	}

	public SteamID(SteamID steamID) {
		super(steamID.handle);
	}

	SteamID(long id) {
		super(id);
	}

	public boolean isValid() {
		return isValid(handle);
	}

	public int getAccountID() {
		return (int) (handle % (1L << 32));
	}

	// @off

	/*JNI
		#include <steam_api.h>
	*/

	private static native boolean isValid(long handle); /*
		return CSteamID((uint64) handle).IsValid();
	*/

	private static native long getInvalidSteamID(); /*
		return k_steamIDNil.ConvertToUint64();
	*/

}
