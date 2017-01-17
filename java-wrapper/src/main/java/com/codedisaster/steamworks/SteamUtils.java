package com.codedisaster.steamworks;

import java.nio.ByteBuffer;

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

	private SteamUtilsCallbackAdapter callbackAdapter;

	public SteamUtils(SteamUtilsCallback callback) {
		super(SteamAPI.getSteamUtilsPointer());
		callbackAdapter = new SteamUtilsCallbackAdapter(callback);
		setCallback(createCallback(callbackAdapter));
	}

	public int getSecondsSinceAppActive() {
		return getSecondsSinceAppActive(pointer);
	}

	public int getSecondsSinceComputerActive() {
		return getSecondsSinceComputerActive(pointer);
	}

	public SteamUniverse getConnectedUniverse() {
		return SteamUniverse.byValue(getConnectedUniverse(pointer));
	}

	public int getServerRealTime() {
		return getServerRealTime(pointer);
	}

	public int getImageWidth(int image) {
		return getImageWidth(pointer, image);
	}

	public int getImageHeight(int image) {
		return getImageHeight(pointer, image);
	}

	public boolean getImageRGBA(int image, ByteBuffer dest, int destSize) throws SteamException {
		if (!dest.isDirect()) {
			throw new SteamException("Direct buffer required!");
		}
		return getImageRGBA(pointer, image, dest, destSize);
	}

	public int getAppID() {
		return getAppID(pointer);
	}

	public void setOverlayNotificationPosition(NotificationPosition position) {
		setOverlayNotificationPosition(pointer, position.ordinal());
	}

	public boolean isAPICallCompleted(SteamAPICall handle, boolean[] result) {
		return isAPICallCompleted(pointer, handle.handle, result);
	}

	public SteamAPICallFailure getAPICallFailureReason(SteamAPICall handle) {
		return SteamAPICallFailure.byValue(getAPICallFailureReason(pointer, handle.handle));
	}

	public void setWarningMessageHook(SteamAPIWarningMessageHook messageHook) {
		callbackAdapter.setWarningMessageHook(messageHook);
		enableWarningMessageHook(this.callback, messageHook != null);
	}

	public boolean isOverlayEnabled() {
		return isOverlayEnabled(pointer);
	}

	// @off

	/*JNI
		#include "SteamUtilsCallback.h"
	*/

	private static native long createCallback(SteamUtilsCallbackAdapter javaCallback); /*
		return (intp) new SteamUtilsCallback(env, javaCallback);
	*/

	private static native int getSecondsSinceAppActive(long pointer); /*
		ISteamUtils* utils = (ISteamUtils*) pointer;
		return utils->GetSecondsSinceAppActive();
	*/

	private static native int getSecondsSinceComputerActive(long pointer); /*
		ISteamUtils* utils = (ISteamUtils*) pointer;
		return utils->GetSecondsSinceComputerActive();
	*/

	private static native int getConnectedUniverse(long pointer); /*
		ISteamUtils* utils = (ISteamUtils*) pointer;
		return utils->GetConnectedUniverse();
	*/

	// This will overflow in year 2038.
	private static native int getServerRealTime(long pointer); /*
		ISteamUtils* utils = (ISteamUtils*) pointer;
		return utils->GetServerRealTime();
	*/

	private static native String getIPCountry(long pointer); /*
		ISteamUtils* utils = (ISteamUtils*) pointer;
		return env->NewStringUTF(utils->GetIPCountry());
	*/

	private static native int getImageWidth(long pointer, int image); /*
		ISteamUtils* utils = (ISteamUtils*) pointer;
		uint32 width, height;
		bool result = utils->GetImageSize(image, &width, &height);
		return result ? width : -1;
	*/

	private static native int getImageHeight(long pointer, int image); /*
		ISteamUtils* utils = (ISteamUtils*) pointer;
		uint32 width, height;
		bool result = utils->GetImageSize(image, &width, &height);
		return result ? height : -1;
	*/

	private static native boolean getImageRGBA(long pointer, int image, ByteBuffer dest, int destSize); /*
		ISteamUtils* utils = (ISteamUtils*) pointer;
		return utils->GetImageRGBA(image, (uint8*) dest, destSize);
	*/

	private static native int getAppID(long pointer); /*
		ISteamUtils* utils = (ISteamUtils*) pointer;
		return (AppId_t) utils->GetAppID();
	*/

	private static native void setOverlayNotificationPosition(long pointer, int position); /*
		ISteamUtils* utils = (ISteamUtils*) pointer;
		utils->SetOverlayNotificationPosition((ENotificationPosition) position);
	*/

	private static native boolean isAPICallCompleted(long pointer, long handle, boolean[] result); /*
		ISteamUtils* utils = (ISteamUtils*) pointer;
		return utils->IsAPICallCompleted((SteamAPICall_t) handle, &result[0]);
	*/

	private static native int getAPICallFailureReason(long pointer, long handle); /*
		ISteamUtils* utils = (ISteamUtils*) pointer;
		return utils->GetAPICallFailureReason((SteamAPICall_t) handle);
	*/

	private static native void enableWarningMessageHook(long callback, boolean enable); /*
		SteamUtilsCallback* cb = (SteamUtilsCallback*) callback;
		cb->enableWarningMessageHook(enable);
	*/

	private static native boolean isOverlayEnabled(long pointer); /*
		ISteamUtils* utils = (ISteamUtils*) pointer;
		return utils->IsOverlayEnabled();
	*/

}
