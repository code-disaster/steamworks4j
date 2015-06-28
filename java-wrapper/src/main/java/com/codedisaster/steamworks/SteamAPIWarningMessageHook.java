package com.codedisaster.steamworks;

public interface SteamAPIWarningMessageHook {
	void onWarningMessage(int severity, String message);
}
