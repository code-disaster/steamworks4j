package com.codedisaster.steamworks;

@SuppressWarnings("unused")
class SteamInputCallbackAdapter extends SteamCallbackAdapter<SteamInputCallback> {

	SteamInputCallbackAdapter(SteamInputCallback callback) {
		super(callback);
	}

	void onDeviceConnected(long device) {
		callback.onDeviceConnected(new SteamInputHandle(device));
	}

	void onDeviceDisconnected(long device) {
		callback.onDeviceDisconnected(new SteamInputHandle(device));
	}

	void onConfigurationLoaded(int appID, long device, long mappingCreator, int majorRevision, int minorRevision,
							   boolean usesSteamInputAPI, boolean usesGamepadAPI) {
		callback.onConfigurationLoaded(appID, new SteamInputHandle(device), new SteamID(mappingCreator),
				majorRevision, minorRevision, usesSteamInputAPI, usesGamepadAPI);
	}

	void onDigitalActionEvent(long controller, long action, boolean state, boolean active) {
		callback.onDigitalActionEvent(new SteamInputHandle(controller), new SteamInputDigitalActionHandle(action),
				state, active);
	}

	void onAnalogActionEvent(long controller, long action, int mode, float x, float y, boolean active) {
		callback.onAnalogActionEvent(new SteamInputHandle(controller), new SteamInputAnalogActionHandle(action),
				SteamInput.SourceMode.byOrdinal(mode), x, y, active);
	}

	void onGamepadSlotChange(int appID, long device, int deviceType, int oldGamepadSlot, int newGamepadSlot) {
		callback.onGamepadSlotChange(appID, new SteamInputHandle(device), SteamInput.InputType.byOrdinal(deviceType),
				oldGamepadSlot, newGamepadSlot);
	}

}
