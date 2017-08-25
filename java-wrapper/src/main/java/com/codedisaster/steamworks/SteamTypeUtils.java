package com.codedisaster.steamworks;

public class SteamTypeUtils {

	public static final int UINT32_SIZE = Integer.SIZE / Byte.SIZE;
	public static final long UINT32_MAX_VALUE = 0xFFFFFFFFL;
	public static final long UINT32_MIN_VALUE = 0L;

	public static int unwrapUint32(long l) {
		if (l > UINT32_MAX_VALUE || l < UINT32_MIN_VALUE)
			throw new IllegalArgumentException("Provided long cannot be cast to uint32");

		return (int) l;
	}

	public static long wrapUint32(int i) {
		return UINT32_MAX_VALUE & i;
	}

}
