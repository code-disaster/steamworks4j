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
	 * This constructor is package-private to not invite user applications to persist and recover ID values
	 * manually. Instead, Steamworks API functions should always be used to obtain valid steam IDs.
	 * <p>
	 * If you need to serialize IDs, e.g. for client/server communication, use the static functions
	 * {@link SteamNativeHandle#getNativeHandle(SteamNativeHandle)} and {@link SteamID#createFromNativeHandle(long)}
	 * instead.
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

	/**
	 * Creates a SteamID instance from a long value previously obtained by
	 * {@link SteamNativeHandle#getNativeHandle(SteamNativeHandle)}.
	 */
	public static SteamID createFromNativeHandle(long id) {
		return new SteamID(id);
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
