package com.codedisaster.steamworks;

import java.nio.ByteBuffer;

@SuppressWarnings("unused")
public class SteamUtils extends SteamInterface {

	public enum SteamAPICallFailure {
		None(-1),
		SteamGone(0),
		NetworkFailure(1),
		InvalidHandle(2),
		MismatchedCallback(3);

		private final int code;
		private static final SteamAPICallFailure[] values = values();

		SteamAPICallFailure(int code) {
			this.code = code;
		}

		static SteamAPICallFailure byValue(int code) {
			for (SteamAPICallFailure value : values) {
				if (value.code == code) {
					return value;
				}
			}
			return None;
		}
	}

	public enum NotificationPosition {
		TopLeft,
		TopRight,
		BottomLeft,
		BottomRight
	}

	public enum FloatingGamepadTextInputMode {
		ModeSingleLine,
		ModeMultipleLines,
		ModeEmail,
		ModeNumeric,
	}

	private final SteamUtilsCallbackAdapter callbackAdapter;

	public SteamUtils(SteamUtilsCallback callback) {
		callbackAdapter = new SteamUtilsCallbackAdapter(callback);
		setCallback(SteamUtilsNative.createCallback(callbackAdapter));
	}

	public int getSecondsSinceAppActive() {
		return SteamUtilsNative.getSecondsSinceAppActive();
	}

	public int getSecondsSinceComputerActive() {
		return SteamUtilsNative.getSecondsSinceComputerActive();
	}

	public SteamUniverse getConnectedUniverse() {
		return SteamUniverse.byValue(SteamUtilsNative.getConnectedUniverse());
	}

	public int getServerRealTime() {
		return SteamUtilsNative.getServerRealTime();
	}

	public int getImageWidth(int image) {
		return SteamUtilsNative.getImageWidth(image);
	}

	public int getImageHeight(int image) {
		return SteamUtilsNative.getImageHeight(image);
	}

	public boolean getImageSize(int image, int[] size) {
		return SteamUtilsNative.getImageSize(image, size);
	}

	public boolean getImageRGBA(int image, ByteBuffer dest) throws SteamException {
		checkBuffer(dest);
		return SteamUtilsNative.getImageRGBA(image, dest, dest.position(), dest.remaining());
	}

	public int getAppID() {
		return SteamUtilsNative.getAppID();
	}

	public void setOverlayNotificationPosition(NotificationPosition position) {
		SteamUtilsNative.setOverlayNotificationPosition(position.ordinal());
	}

	public boolean isAPICallCompleted(SteamAPICall handle, boolean[] result) {
		return SteamUtilsNative.isAPICallCompleted(handle.handle, result);
	}

	public SteamAPICallFailure getAPICallFailureReason(SteamAPICall handle) {
		return SteamAPICallFailure.byValue(SteamUtilsNative.getAPICallFailureReason(handle.handle));
	}

	public void setWarningMessageHook(SteamAPIWarningMessageHook messageHook) {
		callbackAdapter.setWarningMessageHook(messageHook);
		SteamUtilsNative.enableWarningMessageHook(this.callback, messageHook != null);
	}

	public boolean isOverlayEnabled() {
		return SteamUtilsNative.isOverlayEnabled();
	}

	public boolean isSteamInBigPictureMode() {
		return SteamUtilsNative.isSteamInBigPictureMode();
	}

	public boolean isSteamChinaLauncher() {
		return SteamUtilsNative.isSteamChinaLauncher();
	}

	public boolean isSteamRunningOnSteamDeck() {
		return SteamUtilsNative.isSteamRunningOnSteamDeck();
	}

	public boolean showFloatingGamepadTextInput(FloatingGamepadTextInputMode keyboardMode,
												int textFieldXPosition, int textFieldYPosition,
												int textFieldWidth, int textFieldHeight) {
		return SteamUtilsNative.showFloatingGamepadTextInput(keyboardMode.ordinal(),
				textFieldXPosition, textFieldYPosition, textFieldWidth, textFieldHeight);
	}

	public void setGameLauncherMode(boolean isLauncherMode) {
		SteamUtilsNative.setGameLauncherMode(isLauncherMode);
	}

	public boolean dismissFloatingGamepadTextInput() {
		return SteamUtilsNative.dismissFloatingGamepadTextInput();
	}

}
