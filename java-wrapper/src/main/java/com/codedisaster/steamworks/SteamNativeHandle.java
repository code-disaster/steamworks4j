package com.codedisaster.steamworks;

abstract class SteamNativeHandle {

	long handle;

	SteamNativeHandle(long handle) {
		this.handle = handle;
	}

	@Deprecated
	public long getNativeHandle() {
		return handle;
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
