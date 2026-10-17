package com.codedisaster.steamworks;

public interface SteamInputCallback {

	/**
	 * Reported after {@link SteamInput#enableDeviceCallbacks()}.
	 */
	default void onDeviceConnected(SteamInputHandle device) {
	}

	/**
	 * Reported after {@link SteamInput#enableDeviceCallbacks()}.
	 */
	default void onDeviceDisconnected(SteamInputHandle device) {
	}

	default void onConfigurationLoaded(int appID, SteamInputHandle device, SteamID mappingCreator,
									   int majorRevision, int minorRevision,
									   boolean usesSteamInputAPI, boolean usesGamepadAPI) {
	}

	/**
	 * Only reported after {@link SteamInput#enableActionEventCallbacks(boolean)}. Steam calls this
	 * from {@link SteamInput#runFrame()} or {@link SteamAPI#runCallbacks()}, whenever the action's
	 * state changes.
	 */
	default void onDigitalActionEvent(SteamInputHandle controller, SteamInputDigitalActionHandle action,
									  boolean state, boolean active) {
	}

	/**
	 * Only reported after {@link SteamInput#enableActionEventCallbacks(boolean)}. Steam calls this
	 * from {@link SteamInput#runFrame()} or {@link SteamAPI#runCallbacks()}, whenever the action's
	 * state changes.
	 */
	default void onAnalogActionEvent(SteamInputHandle controller, SteamInputAnalogActionHandle action,
									 SteamInput.SourceMode mode, float x, float y, boolean active) {
	}

	default void onGamepadSlotChange(int appID, SteamInputHandle device, SteamInput.InputType deviceType,
									 int oldGamepadSlot, int newGamepadSlot) {
	}

}
