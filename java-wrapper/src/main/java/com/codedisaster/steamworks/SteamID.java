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
	 *
	 * If you need to serialize IDs, e.g. for client/server communication, use the static functions
	 * {@link SteamID#getNativeHandle(SteamID)} and {@link SteamID#createFromNativeHandle(long)} instead.
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
	 * Creates a SteamID instance from a long value previously obtained by {@link SteamID#getNativeHandle(SteamID)}.
	 */
	public static SteamID createFromNativeHandle(long id) {
		return new SteamID(id);
	}

	/**
	 * Returns the unsigned 64-bit value wrapped by CSteamID, cast to Java's signed long. This value can
	 * be serialized and later restored with {@link SteamID#createFromNativeHandle(long)}.
	 */
	public static long getNativeHandle(SteamID steamID) {
		return steamID.handle;
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
