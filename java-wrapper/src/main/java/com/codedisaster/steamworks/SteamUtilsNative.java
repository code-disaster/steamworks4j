package com.codedisaster.steamworks;

import java.nio.ByteBuffer;

final class SteamUtilsNative {

	// @off

	/*JNI
		#include "SteamUtilsCallback.h"
	*/

	static native long createCallback(SteamUtilsCallbackAdapter javaCallback); /*
		return (intp) new SteamUtilsCallback(env, javaCallback);
	*/

	static native int getSecondsSinceAppActive(); /*
		return SteamUtils()->GetSecondsSinceAppActive();
	*/

	static native int getSecondsSinceComputerActive(); /*
		return SteamUtils()->GetSecondsSinceComputerActive();
	*/

	static native int getConnectedUniverse(); /*
		return SteamUtils()->GetConnectedUniverse();
	*/

	// This will overflow in year 2038.
	static native int getServerRealTime(); /*
		return SteamUtils()->GetServerRealTime();
	*/

	static native String getIPCountry(); /*
		return env->NewStringUTF(SteamUtils()->GetIPCountry());
	*/

	static native int getImageWidth(int image); /*
		uint32 width, height;
		bool result = SteamUtils()->GetImageSize(image, &width, &height);
		return result ? width : -1;
	*/

	static native int getImageHeight(int image); /*
		uint32 width, height;
		bool result = SteamUtils()->GetImageSize(image, &width, &height);
		return result ? height : -1;
	*/

	static native boolean getImageSize(int image, int[] size); /*
		return SteamUtils()->GetImageSize(image, (uint32*) &size[0], (uint32*) &size[1]);
	*/

	static native boolean getImageRGBA(int image, ByteBuffer dest,
											   int bufferOffset, int bufferSize); /*
		return SteamUtils()->GetImageRGBA(image, (uint8*) &dest[bufferOffset], bufferSize);
	*/

	static native int getAppID(); /*
		return (AppId_t) SteamUtils()->GetAppID();
	*/

	static native void setOverlayNotificationPosition(int position); /*
		SteamUtils()->SetOverlayNotificationPosition((ENotificationPosition) position);
	*/

	static native boolean isAPICallCompleted(long handle, boolean[] result); /*
		return SteamUtils()->IsAPICallCompleted((SteamAPICall_t) handle, &result[0]);
	*/

	static native int getAPICallFailureReason(long handle); /*
		return SteamUtils()->GetAPICallFailureReason((SteamAPICall_t) handle);
	*/

	static native void enableWarningMessageHook(long callback, boolean enable); /*
		SteamUtilsCallback* cb = (SteamUtilsCallback*) callback;
		cb->enableWarningMessageHook(enable);
	*/

	static native boolean isOverlayEnabled(); /*
		return SteamUtils()->IsOverlayEnabled();
	*/

	static native boolean isSteamInBigPictureMode(); /*
		return SteamUtils()->IsSteamInBigPictureMode();
	*/

	static native boolean isSteamChinaLauncher(); /*
		return SteamUtils()->IsSteamChinaLauncher();
	*/

	static native boolean isSteamRunningOnSteamDeck(); /*
		return SteamUtils()->IsSteamRunningOnSteamDeck();
	*/

	static native boolean showFloatingGamepadTextInput(int keyboardMode,
													   int textFieldXPosition, int textFieldYPosition,
													   int textFieldWidth, int textFieldHeight); /*
		return SteamUtils()->ShowFloatingGamepadTextInput((EFloatingGamepadTextInputMode) keyboardMode,
			textFieldXPosition, textFieldYPosition, textFieldWidth, textFieldHeight);
	*/

	static native void setGameLauncherMode(boolean isLauncherMode); /*
		SteamUtils()->SetGameLauncherMode(isLauncherMode);
	*/

	static native boolean dismissFloatingGamepadTextInput(); /*
		return SteamUtils()->DismissFloatingGamepadTextInput();
	*/

}
