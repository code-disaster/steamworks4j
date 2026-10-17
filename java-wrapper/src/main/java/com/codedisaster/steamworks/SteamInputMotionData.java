package com.codedisaster.steamworks;

public class SteamInputMotionData {

	/**
	 * Using a plain array. The C++ API used a packed struct of floats.
	 * Wanted to avoid costly JNI calls.
	 */
	float[] data = new float[10];

	public float getRotQuatX() {
		return data[0];
	}

	public float getRotQuatY() {
		return data[1];
	}

	public float getRotQuatZ() {
		return data[2];
	}

	public float getRotQuatW() {
		return data[3];
	}

	public float getPosAccelX() {
		return data[4];
	}

	public float getPosAccelY() {
		return data[5];
	}

	public float getPosAccelZ() {
		return data[6];
	}

	public float getRotVelX() {
		return data[7];
	}

	public float getRotVelY() {
		return data[8];
	}

	public float getRotVelZ() {
		return data[9];
	}

}
