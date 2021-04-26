package com.codedisaster.steamworks;

final class SteamControllerNative {

	// @off

	/*JNI
		#include <steam_api.h>
		#include "isteamcontroller.h"
	*/

	static native boolean init(); /*
		return SteamController()->Init();
	*/

	static native boolean shutdown(); /*
		return SteamController()->Shutdown();
	*/

	static native void runFrame(); /*
		SteamController()->RunFrame();
	*/

	static native int getConnectedControllers(long[] handlesOut); /*
		return SteamController()->GetConnectedControllers((ControllerHandle_t*) handlesOut);
	*/

	static native boolean showBindingPanel(long controllerHandle); /*
		return SteamController()->ShowBindingPanel((ControllerHandle_t) controllerHandle);
	*/

	static native long getActionSetHandle(String actionSetName); /*
		return SteamController()->GetActionSetHandle(actionSetName);
	*/

	static native void activateActionSet(long controllerHandle, long actionSetHandle); /*
		SteamController()->ActivateActionSet((ControllerHandle_t) controllerHandle, (ControllerActionSetHandle_t) actionSetHandle);
	*/

	static native long getCurrentActionSet(long controllerHandle); /*
		return SteamController()->GetCurrentActionSet((ControllerHandle_t) controllerHandle);
	*/

	static native long getDigitalActionHandle(String actionName); /*
		return SteamController()->GetDigitalActionHandle(actionName);
	*/

	static native void getDigitalActionData(long controllerHandle,
											long digitalActionHandle,
											SteamControllerDigitalActionData digitalActionData); /*

		ControllerDigitalActionData_t result = SteamController()->GetDigitalActionData(
			(ControllerHandle_t) controllerHandle, (ControllerDigitalActionHandle_t) digitalActionHandle);

		{
			jclass clazz = env->GetObjectClass(digitalActionData);

			jfieldID field = env->GetFieldID(clazz, "state", "Z");
			env->SetBooleanField(digitalActionData, field, (jboolean) result.bState);

			field = env->GetFieldID(clazz, "active", "Z");
			env->SetBooleanField(digitalActionData, field, (jboolean) result.bActive);
		}
	*/

	static native int getDigitalActionOrigins(long controllerHandle,
											  long actionSetHandle,
											  long digitalActionHandle,
											  int[] originsOut); /*

		return SteamController()->GetDigitalActionOrigins((ControllerHandle_t) controllerHandle,
			(ControllerActionSetHandle_t) actionSetHandle, (ControllerDigitalActionHandle_t) digitalActionHandle,
			(EControllerActionOrigin*) originsOut);
	*/

	static native long getAnalogActionHandle(String actionName); /*
		return SteamController()->GetAnalogActionHandle(actionName);
	*/

	static native void getAnalogActionData(long controllerHandle,
										   long analogActionHandle,
										   SteamControllerAnalogActionData analogActionData); /*

		ControllerAnalogActionData_t result = SteamController()->GetAnalogActionData(
			(ControllerHandle_t) controllerHandle, (ControllerAnalogActionHandle_t) analogActionHandle);

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

	static native int getAnalogActionOrigins(long controllerHandle,
											 long actionSetHandle,
											 long analogActionHandle,
											 int[] originsOut); /*

		return SteamController()->GetAnalogActionOrigins((ControllerHandle_t) controllerHandle,
			(ControllerActionSetHandle_t) actionSetHandle, (ControllerAnalogActionHandle_t) analogActionHandle,
			(EControllerActionOrigin*) originsOut);
	*/

	static native void stopAnalogActionMomentum(long controllerHandle,
												long analogActionHandle); /*

		SteamController()->StopAnalogActionMomentum((ControllerHandle_t) controllerHandle,
			(ControllerAnalogActionHandle_t) analogActionHandle);
	*/

	static native void triggerHapticPulse(long controllerHandle,
										  int targetPad,
										  int durationMicroSec); /*

		SteamController()->TriggerHapticPulse((ControllerHandle_t) controllerHandle,
			(ESteamControllerPad) targetPad, (unsigned short) durationMicroSec);
	*/

	static native void triggerRepeatedHapticPulse(long controllerHandle,
												  int targetPad,
												  int durationMicroSec,
												  int offMicroSec,
												  int repeat,
												  int flags); /*

		SteamController()->TriggerRepeatedHapticPulse((ControllerHandle_t) controllerHandle,
				(ESteamControllerPad) targetPad, (unsigned short) durationMicroSec,
				(unsigned short) offMicroSec, (unsigned short) repeat, (unsigned int) flags);
	*/

	static native void triggerVibration(long controllerHandle,
										short leftSpeed,
										short rightSpeed); /*

		SteamController()->TriggerVibration((ControllerHandle_t) controllerHandle,
			(unsigned short) leftSpeed, (unsigned short) rightSpeed);
	*/

	static native void setLEDColor(long controllerHandle,
								   byte colorR, byte colorG, byte colorB, int flags); /*

		SteamController()->SetLEDColor((ControllerHandle_t) controllerHandle,
			colorR, colorG, colorB, (unsigned int) flags);
	*/

	static native int getGamepadIndexForController(long controllerHandle); /*
		return SteamController()->GetGamepadIndexForController((ControllerHandle_t) controllerHandle);
	*/

	static native long getControllerForGamepadIndex(int index); /*
		return SteamController()->GetControllerForGamepadIndex(index);
	*/

	static native void getMotionData(long controllerHandle, float[] motionData); /*
		ControllerMotionData_t data = SteamController()->GetMotionData((ControllerHandle_t) controllerHandle);

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

	static native String getStringForActionOrigin(int origin); /*
		return env->NewStringUTF(SteamController()->GetStringForActionOrigin((EControllerActionOrigin) origin));
	*/

	static native String getGlyphForActionOrigin(int origin); /*
		return env->NewStringUTF(SteamController()->GetGlyphForActionOrigin((EControllerActionOrigin) origin));
	*/

	static native int getInputTypeForHandle(long controllerHandle); /*
		return SteamController()->GetInputTypeForHandle((ControllerHandle_t) controllerHandle);
	*/

}
