#pragma once

#include "SteamCallbackAdapter.h"
#include <steam_api.h>

class SteamInputCallback : public SteamCallbackAdapter {

public:
	SteamInputCallback(JNIEnv* env, jobject callback);
	~SteamInputCallback();

	void enableActionEventCallbacks(bool enabled);
	void onActionEvent(SteamInputActionEvent_t* event);

	STEAM_CALLBACK(SteamInputCallback, onDeviceConnected, SteamInputDeviceConnected_t, m_CallbackDeviceConnected);
	STEAM_CALLBACK(SteamInputCallback, onDeviceDisconnected, SteamInputDeviceDisconnected_t, m_CallbackDeviceDisconnected);
	STEAM_CALLBACK(SteamInputCallback, onConfigurationLoaded, SteamInputConfigurationLoaded_t, m_CallbackConfigurationLoaded);
	STEAM_CALLBACK(SteamInputCallback, onGamepadSlotChange, SteamInputGamepadSlotChange_t, m_CallbackGamepadSlotChange);
};
