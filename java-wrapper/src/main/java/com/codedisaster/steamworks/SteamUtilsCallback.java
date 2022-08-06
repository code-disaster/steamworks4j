package com.codedisaster.steamworks;

public interface SteamUtilsCallback {

	default void onSteamShutdown() {
	}

	default void onFloatingGamepadTextInputDismissed() {
	}

}
