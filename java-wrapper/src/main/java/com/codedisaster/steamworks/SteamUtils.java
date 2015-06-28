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

	public SteamUtils() {
		super(SteamAPI.getSteamUtilsPointer());
		callbackAdapter = new SteamUtilsCallbackAdapter(new SteamUtilsCallback() {});
		setCallback(createCallback(callbackAdapter));
	}

	public int getImageWidth(int image) {
		return getImageWidth(pointer, image);
	}

	public int getImageHeight(int image) {
		return getImageHeight(pointer, image);
	}

	public boolean getImageRGBA(int image, ByteBuffer dest, int destSize) {
		return getImageRGBA(pointer, image, dest, destSize);
	}

	public long getAppID() {
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

	static private native long createCallback(SteamUtilsCallbackAdapter javaCallback); /*
		return (long) new SteamUtilsCallback(env, javaCallback);
	*/

	static private native int getImageWidth(long pointer, int image); /*
		ISteamUtils* utils = (ISteamUtils*) pointer;
		uint32 width, height;
		bool result = utils->GetImageSize(image, &width, &height);
		return result ? width : -1;
	*/

	static private native int getImageHeight(long pointer, int image); /*
		ISteamUtils* utils = (ISteamUtils*) pointer;
		uint32 width, height;
		bool result = utils->GetImageSize(image, &width, &height);
		return result ? height : -1;
	*/

	static private native boolean getImageRGBA(long pointer, int image, ByteBuffer dest, int destSize); /*
		ISteamUtils* utils = (ISteamUtils*) pointer;
		return utils->GetImageRGBA(image, (uint8*) dest, destSize);
	*/

	static private native long getAppID(long pointer); /*
		ISteamUtils* utils = (ISteamUtils*) pointer;
		return (int64) utils->GetAppID();
	*/

	static private native void setOverlayNotificationPosition(long pointer, int position); /*
		ISteamUtils* utils = (ISteamUtils*) pointer;
		utils->SetOverlayNotificationPosition((ENotificationPosition) position);
	*/

	static private native boolean isAPICallCompleted(long pointer, long handle, boolean[] result); /*
		ISteamUtils* utils = (ISteamUtils*) pointer;
		return utils->IsAPICallCompleted((SteamAPICall_t) handle, &result[0]);
	*/

	static private native int getAPICallFailureReason(long pointer, long handle); /*
		ISteamUtils* utils = (ISteamUtils*) pointer;
		return utils->GetAPICallFailureReason((SteamAPICall_t) handle);
	*/

	static private native void enableWarningMessageHook(long callback, boolean enable); /*
		SteamUtilsCallback* cb = (SteamUtilsCallback*) callback;
		cb->enableWarningMessageHook(enable);
	*/

	static private native boolean isOverlayEnabled(long pointer); /*
		ISteamUtils* utils = (ISteamUtils*) pointer;
		return utils->IsOverlayEnabled();
	*/

}
