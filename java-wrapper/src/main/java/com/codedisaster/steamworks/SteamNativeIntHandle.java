package com.codedisaster.steamworks;

public abstract class SteamNativeIntHandle {

	int handle;

	SteamNativeIntHandle(int handle) {
		this.handle = handle;
	}

	/**
	 * Returns the unsigned 32-bit value wrapped by this handle, cast to Java's signed int.
	 */
	public static <T extends SteamNativeIntHandle> int getNativeHandle(T handle) {
		return handle.handle;
	}

	@Override
	public int hashCode() {
		return Integer.valueOf(handle).hashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof SteamNativeIntHandle) {
			return handle == ((SteamNativeIntHandle) other).handle;
		}
		return false;
	}

	@Override
	public String toString() {
		return Integer.toHexString(handle);
	}

}
