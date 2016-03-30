package com.codedisaster.steamworks;

@SuppressWarnings("unused")
class SteamUtilsCallbackAdapter extends SteamCallbackAdapter<SteamUtilsCallback> {

	private SteamAPIWarningMessageHook messageHook;

	SteamUtilsCallbackAdapter(SteamUtilsCallback callback) {
		super(callback);
	}

	void setWarningMessageHook(SteamAPIWarningMessageHook messageHook) {
		this.messageHook = messageHook;
	}

	void onWarningMessage(int severity, String message) {
		if (messageHook != null) {
			messageHook.onWarningMessage(severity, message);
		}
	}

	void onSteamShutdown() {
		callback.onSteamShutdown();
	}

}
