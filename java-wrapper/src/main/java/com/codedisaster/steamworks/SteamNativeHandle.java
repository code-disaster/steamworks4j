package com.codedisaster.steamworks;

public abstract class SteamNativeHandle {

	long handle;

	SteamNativeHandle(long handle) {
		this.handle = handle;
	}

	/**
	 * Returns the unsigned 64-bit value wrapped by this handle, cast to Java's signed long.
	 *
	 * In most cases this value should not be stored and used by the user application. See
	 * {@link SteamID#createFromNativeHandle(long)} for a possible use case.
	 */
	public static <T extends SteamNativeHandle> long getNativeHandle(T handle) {
		return handle.handle;
	}

	@Override
	public int hashCode() {
		return Long.valueOf(handle).hashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof SteamNativeHandle) {
			return handle == ((SteamNativeHandle) other).handle;
		}
		return false;
	}

	@Override
	public String toString() {
		return Long.toHexString(handle);
	}
}
