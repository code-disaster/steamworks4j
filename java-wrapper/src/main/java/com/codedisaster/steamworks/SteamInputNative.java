package com.codedisaster.steamworks;

final class SteamInputNative {

	// @off

	/*JNI
		#include "SteamInputCallback.h"
		#include "isteamdualsense.h"
		#include <string.h>

		static_assert(sizeof(ScePadTriggerEffectCommandData) == 48,
			"SteamInputDualSenseTriggerEffect.PARAM_SIZE must match ScePadTriggerEffectCommandData");

		static jstring newStringOrNull(JNIEnv* env, const char* value) {
			return value != NULL ? env->NewStringUTF(value) : NULL;
		}
	*/

	static native long createCallback(SteamInputCallbackAdapter javaCallback); /*
		return (intp) new SteamInputCallback(env, javaCallback);
	*/

	static native void enableActionEventCallbacks(long callback, boolean enabled); /*
		SteamInputCallback* cb = (SteamInputCallback*) callback;
		cb->enableActionEventCallbacks(enabled);
	*/

	static native boolean init(boolean explicitlyCallRunFrame); /*
		return SteamInput()->Init(explicitlyCallRunFrame);
	*/

	static native boolean shutdown(); /*
		return SteamInput()->Shutdown();
	*/

	static native boolean setInputActionManifestFilePath(String inputActionManifestAbsolutePath); /*
		return SteamInput()->SetInputActionManifestFilePath(inputActionManifestAbsolutePath);
	*/

	static native void runFrame(); /*
		SteamInput()->RunFrame();
	*/

	static native boolean waitForData(boolean waitForever, int timeout); /*
		return SteamInput()->BWaitForData(waitForever, (uint32) timeout);
	*/

	static native boolean newDataAvailable(); /*
		return SteamInput()->BNewDataAvailable();
	*/

	static native int getConnectedControllers(long[] handlesOut); /*
		return SteamInput()->GetConnectedControllers((InputHandle_t*) handlesOut);
	*/

	static native void enableDeviceCallbacks(); /*
		SteamInput()->EnableDeviceCallbacks();
	*/

	static native long getActionSetHandle(String actionSetName); /*
		return SteamInput()->GetActionSetHandle(actionSetName);
	*/

	static native void activateActionSet(long inputHandle, long actionSetHandle); /*
		SteamInput()->ActivateActionSet((InputHandle_t) inputHandle, (InputActionSetHandle_t) actionSetHandle);
	*/

	static native long getCurrentActionSet(long inputHandle); /*
		return SteamInput()->GetCurrentActionSet((InputHandle_t) inputHandle);
	*/

	static native void activateActionSetLayer(long inputHandle, long actionSetLayerHandle); /*
		SteamInput()->ActivateActionSetLayer((InputHandle_t) inputHandle, (InputActionSetHandle_t) actionSetLayerHandle);
	*/

	static native void deactivateActionSetLayer(long inputHandle, long actionSetLayerHandle); /*
		SteamInput()->DeactivateActionSetLayer((InputHandle_t) inputHandle, (InputActionSetHandle_t) actionSetLayerHandle);
	*/

	static native void deactivateAllActionSetLayers(long inputHandle); /*
		SteamInput()->DeactivateAllActionSetLayers((InputHandle_t) inputHandle);
	*/

	static native int getActiveActionSetLayers(long inputHandle, long[] handlesOut); /*
		return SteamInput()->GetActiveActionSetLayers((InputHandle_t) inputHandle, (InputActionSetHandle_t*) handlesOut);
	*/

	static native long getDigitalActionHandle(String actionName); /*
		return SteamInput()->GetDigitalActionHandle(actionName);
	*/

	static native void getDigitalActionData(long inputHandle,
											long digitalActionHandle,
											SteamInputDigitalActionData digitalActionData); /*

		InputDigitalActionData_t result = SteamInput()->GetDigitalActionData(
			(InputHandle_t) inputHandle, (InputDigitalActionHandle_t) digitalActionHandle);

		{
			jclass clazz = env->GetObjectClass(digitalActionData);

			jfieldID field = env->GetFieldID(clazz, "state", "Z");
			env->SetBooleanField(digitalActionData, field, (jboolean) result.bState);

			field = env->GetFieldID(clazz, "active", "Z");
			env->SetBooleanField(digitalActionData, field, (jboolean) result.bActive);
		}
	*/

	static native int getDigitalActionOrigins(long inputHandle,
											  long actionSetHandle,
											  long digitalActionHandle,
											  int[] originsOut); /*

		return SteamInput()->GetDigitalActionOrigins((InputHandle_t) inputHandle,
			(InputActionSetHandle_t) actionSetHandle, (InputDigitalActionHandle_t) digitalActionHandle,
			(EInputActionOrigin*) originsOut);
	*/

	static native String getStringForDigitalActionName(long digitalActionHandle); /*
		return newStringOrNull(env, SteamInput()->GetStringForDigitalActionName((InputDigitalActionHandle_t) digitalActionHandle));
	*/

	static native long getAnalogActionHandle(String actionName); /*
		return SteamInput()->GetAnalogActionHandle(actionName);
	*/

	static native void getAnalogActionData(long inputHandle,
										   long analogActionHandle,
										   SteamInputAnalogActionData analogActionData); /*

		InputAnalogActionData_t result = SteamInput()->GetAnalogActionData(
			(InputHandle_t) inputHandle, (InputAnalogActionHandle_t) analogActionHandle);

		{
			jclass clazz = env->GetObjectClass(analogActionData);

			jfieldID field = env->GetFieldID(clazz, "mode", "I");
			env->SetIntField(analogActionData, field, (jint) result.eMode);

			field = env->GetFieldID(clazz, "x", "F");
			env->SetFloatField(analogActionData, field, (jfloat) result.x);

			field = env->GetFieldID(clazz, "y", "F");
			env->SetFloatField(analogActionData, field, (jfloat) result.y);

			field = env->GetFieldID(clazz, "active", "Z");
			env->SetBooleanField(analogActionData, field, (jboolean) result.bActive);
		}
	*/

	static native int getAnalogActionOrigins(long inputHandle,
											 long actionSetHandle,
											 long analogActionHandle,
											 int[] originsOut); /*

		return SteamInput()->GetAnalogActionOrigins((InputHandle_t) inputHandle,
			(InputActionSetHandle_t) actionSetHandle, (InputAnalogActionHandle_t) analogActionHandle,
			(EInputActionOrigin*) originsOut);
	*/

	static native String getGlyphPNGForActionOrigin(int origin, int size, int flags); /*
		return newStringOrNull(env, SteamInput()->GetGlyphPNGForActionOrigin((EInputActionOrigin) origin,
			(ESteamInputGlyphSize) size, (uint32) flags));
	*/

	static native String getGlyphSVGForActionOrigin(int origin, int flags); /*
		return newStringOrNull(env, SteamInput()->GetGlyphSVGForActionOrigin((EInputActionOrigin) origin, (uint32) flags));
	*/

	static native String getGlyphForActionOriginLegacy(int origin); /*
		return newStringOrNull(env, SteamInput()->GetGlyphForActionOrigin_Legacy((EInputActionOrigin) origin));
	*/

	static native String getStringForActionOrigin(int origin); /*
		return newStringOrNull(env, SteamInput()->GetStringForActionOrigin((EInputActionOrigin) origin));
	*/

	static native String getStringForAnalogActionName(long analogActionHandle); /*
		return newStringOrNull(env, SteamInput()->GetStringForAnalogActionName((InputAnalogActionHandle_t) analogActionHandle));
	*/

	static native void stopAnalogActionMomentum(long inputHandle, long analogActionHandle); /*
		SteamInput()->StopAnalogActionMomentum((InputHandle_t) inputHandle, (InputAnalogActionHandle_t) analogActionHandle);
	*/

	static native void getMotionData(long inputHandle, float[] motionData); /*
		InputMotionData_t data = SteamInput()->GetMotionData((InputHandle_t) inputHandle);

		motionData[0] = data.rotQuatX;
		motionData[1] = data.rotQuatY;
		motionData[2] = data.rotQuatZ;
		motionData[3] = data.rotQuatW;

		motionData[4] = data.posAccelX;
		motionData[5] = data.posAccelY;
		motionData[6] = data.posAccelZ;

		motionData[7] = data.rotVelX;
		motionData[8] = data.rotVelY;
		motionData[9] = data.rotVelZ;
	*/

	static native void triggerVibration(long inputHandle, short leftSpeed, short rightSpeed); /*
		SteamInput()->TriggerVibration((InputHandle_t) inputHandle,
			(unsigned short) leftSpeed, (unsigned short) rightSpeed);
	*/

	static native void triggerVibrationExtended(long inputHandle, short leftSpeed, short rightSpeed,
												short leftTriggerSpeed, short rightTriggerSpeed); /*
		SteamInput()->TriggerVibrationExtended((InputHandle_t) inputHandle,
			(unsigned short) leftSpeed, (unsigned short) rightSpeed,
			(unsigned short) leftTriggerSpeed, (unsigned short) rightTriggerSpeed);
	*/

	static native void triggerSimpleHapticEvent(long inputHandle, int hapticLocation, byte intensity,
												byte gainDB, byte otherIntensity, byte otherGainDB); /*
		SteamInput()->TriggerSimpleHapticEvent((InputHandle_t) inputHandle,
			(EControllerHapticLocation) hapticLocation, (uint8) intensity, (char) gainDB,
			(uint8) otherIntensity, (char) otherGainDB);
	*/

	static native void setLEDColor(long inputHandle, byte colorR, byte colorG, byte colorB, int flags); /*
		SteamInput()->SetLEDColor((InputHandle_t) inputHandle,
			(uint8) colorR, (uint8) colorG, (uint8) colorB, (unsigned int) flags);
	*/

	static native void legacyTriggerHapticPulse(long inputHandle, int targetPad, int durationMicroSec); /*
		SteamInput()->Legacy_TriggerHapticPulse((InputHandle_t) inputHandle,
			(ESteamControllerPad) targetPad, (unsigned short) durationMicroSec);
	*/

	static native void legacyTriggerRepeatedHapticPulse(long inputHandle, int targetPad, int durationMicroSec,
														int offMicroSec, int repeat, int flags); /*
		SteamInput()->Legacy_TriggerRepeatedHapticPulse((InputHandle_t) inputHandle,
			(ESteamControllerPad) targetPad, (unsigned short) durationMicroSec,
			(unsigned short) offMicroSec, (unsigned short) repeat, (unsigned int) flags);
	*/

	static native boolean showBindingPanel(long inputHandle); /*
		return SteamInput()->ShowBindingPanel((InputHandle_t) inputHandle);
	*/

	static native int getInputTypeForHandle(long inputHandle); /*
		return SteamInput()->GetInputTypeForHandle((InputHandle_t) inputHandle);
	*/

	static native long getControllerForGamepadIndex(int index); /*
		return SteamInput()->GetControllerForGamepadIndex(index);
	*/

	static native int getGamepadIndexForController(long inputHandle); /*
		return SteamInput()->GetGamepadIndexForController((InputHandle_t) inputHandle);
	*/

	static native String getStringForXboxOrigin(int origin); /*
		return newStringOrNull(env, SteamInput()->GetStringForXboxOrigin((EXboxOrigin) origin));
	*/

	static native String getGlyphForXboxOrigin(int origin); /*
		return newStringOrNull(env, SteamInput()->GetGlyphForXboxOrigin((EXboxOrigin) origin));
	*/

	static native int getActionOriginFromXboxOrigin(long inputHandle, int origin); /*
		return SteamInput()->GetActionOriginFromXboxOrigin((InputHandle_t) inputHandle, (EXboxOrigin) origin);
	*/

	static native int translateActionOrigin(int destinationInputType, int sourceOrigin); /*
		return SteamInput()->TranslateActionOrigin((ESteamInputType) destinationInputType,
			(EInputActionOrigin) sourceOrigin);
	*/

	static native boolean getDeviceBindingRevision(long inputHandle, int[] revisionOut); /*
		return SteamInput()->GetDeviceBindingRevision((InputHandle_t) inputHandle, &revisionOut[0], &revisionOut[1]);
	*/

	static native int getRemotePlaySessionID(long inputHandle); /*
		return SteamInput()->GetRemotePlaySessionID((InputHandle_t) inputHandle);
	*/

	static native int getSessionInputConfigurationSettings(); /*
		return SteamInput()->GetSessionInputConfigurationSettings();
	*/

	static native void setDualSenseTriggerEffect(long inputHandle, int triggerMask, int[] modes, byte[] params); /*
		ScePadTriggerEffectParam param;
		memset(&param, 0, sizeof(param));

		param.triggerMask = (uint8_t) triggerMask;

		for (int i = 0; i < SCE_PAD_TRIGGER_EFFECT_TRIGGER_NUM; i++) {
			param.command[i].mode = (ScePadTriggerEffectMode) modes[i];
			memcpy(&param.command[i].commandData, params + i * sizeof(ScePadTriggerEffectCommandData),
				sizeof(ScePadTriggerEffectCommandData));
		}

		SteamInput()->SetDualSenseTriggerEffect((InputHandle_t) inputHandle, &param);
	*/

}
