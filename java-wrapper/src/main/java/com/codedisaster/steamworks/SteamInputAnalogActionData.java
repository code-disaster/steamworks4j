package com.codedisaster.steamworks;

@SuppressWarnings("WeakerAccess")
public class SteamInputAnalogActionData {

	int mode;
	float x, y;
	boolean active;

	public SteamInput.SourceMode getMode() {
		return SteamInput.SourceMode.byOrdinal(mode);
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public boolean getActive() {
		return active;
	}

}
