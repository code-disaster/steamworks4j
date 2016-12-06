package com.codedisaster.steamworks;

public class SteamController extends SteamInterface {

	public enum Pad {
		Left,
		Right
	}

	public enum Source {
		None,
		LeftTrackpad,
		RightTrackpad,
		Joystick,
		ABXY,
		Switch,
		LeftTrigger,
		RightTrigger,
		Gyro
	}

	public enum SourceMode {
		None,
		Dpad,
		Buttons,
		FourButtons,
		AbsoluteMouse,
		RelativeMouse,
		JoystickMove,
		JoystickCamera,
		ScrollWheel,
		Trigger,
		TouchMenu,
		MouseJoystick,
		MouseRegion,
		RadialMenu,
		Switches;

		private static final SourceMode[] values = values();

		static SourceMode byOrdinal(int ordinal) {
			return values[ordinal];
		}
	}

	public enum ActionOrigin {
		None,
		A,
		B,
		X,
		Y,
		LeftBumper,
		RightBumper,
		LeftGrip,
		RightGrip,
		Start,
		Back,
		LeftPad_Touch,
		LeftPad_Swipe,
		LeftPad_Click,
		LeftPad_DPadNorth,
		LeftPad_DPadSouth,
		LeftPad_DPadWest,
		LeftPad_DPadEast,
		RightPad_Touch,
		RightPad_Swipe,
		RightPad_Click,
		RightPad_DPadNorth,
		RightPad_DPadSouth,
		RightPad_DPadWest,
		RightPad_DPadEast,
		LeftTrigger_Pull,
		LeftTrigger_Click,
		RightTrigger_Pull,
		RightTrigger_Click,
		LeftStick_Move,
		LeftStick_Click,
		LeftStick_DPadNorth,
		LeftStick_DPadSouth,
		LeftStick_DPadWest,
		LeftStick_DPadEast,
		Gyro_Move,
		Gyro_Pitch,
		Gyro_Yaw,
		Gyro_Roll;

		private static final ActionOrigin[] values = values();

		static ActionOrigin byOrdinal(int ordinal) {
			return values[ordinal];
		}
	}

	public static final int STEAM_CONTROLLER_MAX_COUNT = 16;
	public static final int STEAM_CONTROLLER_MAX_ANALOG_ACTIONS = 16;
	public static final int STEAM_CONTROLLER_MAX_DIGITAL_ACTIONS = 128;
	public static final int STEAM_CONTROLLER_MAX_ORIGINS = 8;

	public static final long STEAM_CONTROLLER_HANDLE_ALL_CONTROLLERS = 0xffffffffffffffffL;

	public static final float STEAM_CONTROLLER_MIN_ANALOG_ACTION_DATA = -1.0f;
	public static final float STEAM_CONTROLLER_MAX_ANALOG_ACTION_DATA = 1.0f;

	private long[] controllerHandles = new long[STEAM_CONTROLLER_MAX_COUNT];
	private int[] actionOrigins = new int[STEAM_CONTROLLER_MAX_ORIGINS];

	public SteamController() {
		super(SteamAPI.getSteamControllerPointer());
	}

	public boolean init() {
		return init(pointer);
	}

	public boolean shutdown() {
		return shutdown(pointer);
	}

	public void runFrame() {
		runFrame(pointer);
	}

	public int getConnectedControllers(SteamControllerHandle[] handlesOut) {
		if (handlesOut.length < STEAM_CONTROLLER_MAX_COUNT) {
			throw new IllegalArgumentException("Array size must be at least STEAM_CONTROLLER_MAX_COUNT");
		}

		int count = getConnectedControllers(pointer, controllerHandles);

		for (int i = 0; i < count; i++) {
			handlesOut[i] = new SteamControllerHandle(controllerHandles[i]);
		}

		return count;
	}

	public boolean showBindingPanel(SteamControllerHandle controller) {
		return showBindingPanel(pointer, controller.handle);
	}

	public SteamControllerActionSetHandle getActionSetHandle(String actionSetName) {
		return new SteamControllerActionSetHandle(getActionSetHandle(pointer, actionSetName));
	}

	public void activateActionSet(SteamControllerHandle controller, SteamControllerActionSetHandle actionSet) {
		activateActionSet(pointer, controller.handle, actionSet.handle);
	}

	public SteamControllerActionSetHandle getCurrentActionSet(SteamControllerHandle controller) {
		return new SteamControllerActionSetHandle(getCurrentActionSet(pointer, controller.handle));
	}

	public SteamControllerDigitalActionHandle getDigitalActionHandle(String actionName) {
		return new SteamControllerDigitalActionHandle(getDigitalActionHandle(pointer, actionName));
	}

	public void getDigitalActionData(SteamControllerHandle controller,
									 SteamControllerDigitalActionHandle digitalAction,
									 SteamControllerDigitalActionData digitalActionData) {

		getDigitalActionData(pointer, controller.handle, digitalAction.handle, digitalActionData);
	}

	public int getDigitalActionOrigins(SteamControllerHandle controller,
									   SteamControllerActionSetHandle actionSet,
									   SteamControllerDigitalActionHandle digitalAction,
									   ActionOrigin[] originsOut) {

		if (originsOut.length < STEAM_CONTROLLER_MAX_ORIGINS) {
			throw new IllegalArgumentException("Array size must be at least STEAM_CONTROLLER_MAX_ORIGINS");
		}

		int count = getDigitalActionOrigins(pointer, controller.handle,
				actionSet.handle, digitalAction.handle, actionOrigins);

		for (int i = 0; i < count; i++) {
			originsOut[i] = ActionOrigin.byOrdinal(actionOrigins[i]);
		}

		return count;
	}

	public SteamControllerAnalogActionHandle getAnalogActionHandle(String actionName) {
		return new SteamControllerAnalogActionHandle(getAnalogActionHandle(pointer, actionName));
	}

	public void getAnalogActionData(SteamControllerHandle controller,
									SteamControllerAnalogActionHandle analogAction,
									SteamControllerAnalogActionData analoglActionData) {

		getAnalogActionData(pointer, controller.handle, analogAction.handle, analoglActionData);
	}

	public int getAnalogActionOrigins(SteamControllerHandle controller,
									  SteamControllerActionSetHandle actionSet,
									  SteamControllerAnalogActionHandle analogAction,
									  ActionOrigin[] originsOut) {

		if (originsOut.length < STEAM_CONTROLLER_MAX_ORIGINS) {
			throw new IllegalArgumentException("Array size must be at least STEAM_CONTROLLER_MAX_ORIGINS");
		}

		int count = getAnalogActionOrigins(pointer, controller.handle,
				actionSet.handle, analogAction.handle, actionOrigins);

		for (int i = 0; i < count; i++) {
			originsOut[i] = ActionOrigin.byOrdinal(actionOrigins[i]);
		}

		return count;
	}

	public void stopAnalogActionMomentum(SteamControllerHandle controller,
										 SteamControllerAnalogActionHandle analogAction) {

		stopAnalogActionMomentum(pointer, controller.handle, analogAction.handle);
	}

	public void triggerHapticPulse(SteamControllerHandle controller, Pad targetPad, int durationMicroSec) {
		triggerHapticPulse(pointer, controller.handle, targetPad.ordinal(), durationMicroSec);
	}

	public void triggerRepeatedHapticPulse(SteamControllerHandle controller, Pad targetPad,
										   int durationMicroSec, int offMicroSec, int repeat, int flags) {

		triggerRepeatedHapticPulse(pointer, controller.handle, targetPad.ordinal(),
				durationMicroSec, offMicroSec, repeat, flags);
	}

	public int getGamepadIndexForController(SteamControllerHandle controller) {
		return getGamepadIndexForController(pointer, controller.handle);
	}

	public SteamControllerHandle getControllerForGamepadIndex(int index) {
		return new SteamControllerHandle(getControllerForGamepadIndex(pointer, index));
	}

	public void getMotionData(SteamControllerHandle controller, SteamControllerMotionData motionData) {
		getMotionData(pointer, controller.handle, motionData.data);
	}

	public boolean showDigitalActionOrigins(SteamControllerHandle controller,
											SteamControllerDigitalActionHandle digitalActionHandle,
											float scale, float xPosition, float yPosition) {

		return showDigitalActionOrigins(pointer, controller.handle,
				digitalActionHandle.handle, scale, xPosition, yPosition);
	}

	public boolean showAnalogActionOrigins(SteamControllerHandle controller,
										   SteamControllerAnalogActionHandle analogActionHandle,
										   float scale, float xPosition, float yPosition) {

		return showAnalogActionOrigins(pointer, controller.handle,
				analogActionHandle.handle, scale, xPosition, yPosition);
	}

	// @off

	/*JNI
		#include "isteamcontroller.h"
	*/

	private static native boolean init(long pointer); /*
		ISteamController* controller = (ISteamController*) pointer;
		return controller->Init();
	*/

	private static native boolean shutdown(long pointer); /*
		ISteamController* controller = (ISteamController*) pointer;
		return controller->Shutdown();
	*/

	private static native void runFrame(long pointer); /*
		ISteamController* controller = (ISteamController*) pointer;
		controller->RunFrame();
	*/

	private static native int getConnectedControllers(long pointer, long[] handlesOut); /*
		ISteamController* controller = (ISteamController*) pointer;
		return controller->GetConnectedControllers((ControllerHandle_t*) handlesOut);
	*/

	private static native boolean showBindingPanel(long pointer, long controllerHandle); /*
		ISteamController* controller = (ISteamController*) pointer;
		return controller->ShowBindingPanel((ControllerHandle_t) controllerHandle);
	*/

	private static native long getActionSetHandle(long pointer, String actionSetName); /*
		ISteamController* controller = (ISteamController*) pointer;
		return controller->GetActionSetHandle(actionSetName);
	*/

	private static native void activateActionSet(long pointer, long controllerHandle, long actionSetHandle); /*
		ISteamController* controller = (ISteamController*) pointer;
		controller->ActivateActionSet((ControllerHandle_t) controllerHandle, (ControllerActionSetHandle_t) actionSetHandle);
	*/

	private static native long getCurrentActionSet(long pointer, long controllerHandle); /*
		ISteamController* controller = (ISteamController*) pointer;
		return controller->GetCurrentActionSet((ControllerHandle_t) controllerHandle);
	*/

	private static native long getDigitalActionHandle(long pointer, String actionName); /*
		ISteamController* controller = (ISteamController*) pointer;
		return controller->GetDigitalActionHandle(actionName);
	*/

	private static native void getDigitalActionData(long pointer,
													long controllerHandle,
													long digitalActionHandle,
													SteamControllerDigitalActionData digitalActionData); /*

		ISteamController* controller = (ISteamController*) pointer;
		ControllerDigitalActionData_t result = controller->GetDigitalActionData(
			(ControllerHandle_t) controllerHandle, (ControllerDigitalActionHandle_t) digitalActionHandle);

		{
			jclass clazz = env->GetObjectClass(digitalActionData);

			jfieldID field = env->GetFieldID(clazz, "state", "Z");
			env->SetBooleanField(digitalActionData, field, (jboolean) result.bState);

			field = env->GetFieldID(clazz, "active", "Z");
			env->SetBooleanField(digitalActionData, field, (jboolean) result.bActive);
		}
	*/

	private static native int getDigitalActionOrigins(long pointer,
													  long controllerHandle,
													  long actionSetHandle,
													  long digitalActionHandle,
													  int[] originsOut); /*

		ISteamController* controller = (ISteamController*) pointer;
		return controller->GetDigitalActionOrigins((ControllerHandle_t) controllerHandle,
			(ControllerActionSetHandle_t) actionSetHandle, (ControllerDigitalActionHandle_t) digitalActionHandle,
			(EControllerActionOrigin*) originsOut);
	*/

	private static native long getAnalogActionHandle(long pointer, String actionName); /*
		ISteamController* controller = (ISteamController*) pointer;
		return controller->GetAnalogActionHandle(actionName);
	*/

	private static native void getAnalogActionData(long pointer,
												   long controllerHandle,
												   long analogActionHandle,
												   SteamControllerAnalogActionData analogActionData); /*

		ISteamController* controller = (ISteamController*) pointer;
		ControllerAnalogActionData_t result = controller->GetAnalogActionData(
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

	private static native int getAnalogActionOrigins(long pointer,
													 long controllerHandle,
													 long actionSetHandle,
													 long analogActionHandle,
													 int[] originsOut); /*

		ISteamController* controller = (ISteamController*) pointer;
		return controller->GetAnalogActionOrigins((ControllerHandle_t) controllerHandle,
			(ControllerActionSetHandle_t) actionSetHandle, (ControllerAnalogActionHandle_t) analogActionHandle,
			(EControllerActionOrigin*) originsOut);
	*/

	private static native void stopAnalogActionMomentum(long pointer,
														long controllerHandle,
														long analogActionHandle); /*

		ISteamController* controller = (ISteamController*) pointer;
		controller->StopAnalogActionMomentum((ControllerHandle_t) controllerHandle,
			(ControllerAnalogActionHandle_t) analogActionHandle);
	*/

	private static native void triggerHapticPulse(long pointer,
												  long controllerHandle,
												  int targetPad,
												  int durationMicroSec); /*

		ISteamController* controller = (ISteamController*) pointer;
		controller->TriggerHapticPulse((ControllerHandle_t) controllerHandle,
			(ESteamControllerPad) targetPad, (unsigned short) durationMicroSec);
	*/

	private static native void triggerRepeatedHapticPulse(long pointer,
														  long controllerHandle,
														  int targetPad,
														  int durationMicroSec,
														  int offMicroSec,
														  int repeat,
														  int flags); /*

		ISteamController* controller = (ISteamController*) pointer;
		controller->TriggerRepeatedHapticPulse((ControllerHandle_t) controllerHandle,
				(ESteamControllerPad) targetPad, (unsigned short) durationMicroSec,
				(unsigned short) offMicroSec, (unsigned short) repeat, (unsigned int) flags);
	*/

	private static native int getGamepadIndexForController(long pointer, long controllerHandle); /*
		ISteamController* controller = (ISteamController*) pointer;
		return controller->GetGamepadIndexForController((ControllerHandle_t) controllerHandle);
	*/

	private static native long getControllerForGamepadIndex(long pointer, int index); /*
		ISteamController* controller = (ISteamController*) pointer;
		return controller->GetControllerForGamepadIndex(index);
	*/

	private static native void getMotionData(long pointer, long controllerHandle, float[] motionData); /*
		ISteamController* controller = (ISteamController*) pointer;
		ControllerMotionData_t data = controller->GetMotionData((ControllerHandle_t) controllerHandle);

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

	private static native boolean showDigitalActionOrigins(long pointer, long controllerHandle,
														   long digitalActionHandle, float scale,
														   float xPosition, float yPosition); /*

		ISteamController* controller = (ISteamController*) pointer;
		return controller->ShowDigitalActionOrigins((ControllerHandle_t) controllerHandle,
			(ControllerDigitalActionHandle_t) digitalActionHandle, scale, xPosition, yPosition);
	*/

	private static native boolean showAnalogActionOrigins(long pointer, long controllerHandle,
														  long analogActionHandle, float scale,
														  float xPosition, float yPosition); /*

		ISteamController* controller = (ISteamController*) pointer;
		return controller->ShowAnalogActionOrigins((ControllerHandle_t) controllerHandle,
			(ControllerAnalogActionHandle_t) analogActionHandle, scale, xPosition, yPosition);
	*/

}
