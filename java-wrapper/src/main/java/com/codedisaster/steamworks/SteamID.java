package com.codedisaster.steamworks;

public class SteamID extends SteamNativeHandle {

	private static final long InvalidSteamID = getInvalidSteamID();

	public SteamID() {
		super(InvalidSteamID);
	}

	public SteamID(SteamID steamID) {
		super(steamID.handle);
	}

	/**
	 * This constructor is package-private to not invite user applications to persist and recover
	 * ID values. Instead, Steamworks API functions should always be used to obtain valid steam IDs.
	 */
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
