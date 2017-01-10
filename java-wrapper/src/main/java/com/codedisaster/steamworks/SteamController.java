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
		Gyro,
		CenterTrackpad,
		RightJoystick,
		DPad
	}

	public enum SourceMode {
		None,
		Dpad,
		Buttons,
		FourButtons,
		AbsoluteMouse,
		RelativeMouse,
		JoystickMove,
		JoystickMouse,
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
		Gyro_Roll,

		PS4_X,
		PS4_Circle,
		PS4_Triangle,
		PS4_Square,
		PS4_LeftBumper,
		PS4_RightBumper,
		PS4_Options,
		PS4_Share,
		PS4_LeftPad_Touch,
		PS4_LeftPad_Swipe,
		PS4_LeftPad_Click,
		PS4_LeftPad_DPadNorth,
		PS4_LeftPad_DPadSouth,
		PS4_LeftPad_DPadWest,
		PS4_LeftPad_DPadEast,
		PS4_RightPad_Touch,
		PS4_RightPad_Swipe,
		PS4_RightPad_Click,
		PS4_RightPad_DPadNorth,
		PS4_RightPad_DPadSouth,
		PS4_RightPad_DPadWest,
		PS4_RightPad_DPadEast,
		PS4_CenterPad_Touch,
		PS4_CenterPad_Swipe,
		PS4_CenterPad_Click,
		PS4_CenterPad_DPadNorth,
		PS4_CenterPad_DPadSouth,
		PS4_CenterPad_DPadWest,
		PS4_CenterPad_DPadEast,
		PS4_LeftTrigger_Pull,
		PS4_LeftTrigger_Click,
		PS4_RightTrigger_Pull,
		PS4_RightTrigger_Click,
		PS4_LeftStick_Move,
		PS4_LeftStick_Click,
		PS4_LeftStick_DPadNorth,
		PS4_LeftStick_DPadSouth,
		PS4_LeftStick_DPadWest,
		PS4_LeftStick_DPadEast,
		PS4_RightStick_Move,
		PS4_RightStick_Click,
		PS4_RightStick_DPadNorth,
		PS4_RightStick_DPadSouth,
		PS4_RightStick_DPadWest,
		PS4_RightStick_DPadEast,
		PS4_DPad_North,
		PS4_DPad_South,
		PS4_DPad_West,
		PS4_DPad_East,
		PS4_Gyro_Move,
		PS4_Gyro_Pitch,
		PS4_Gyro_Yaw,
		PS4_Gyro_Roll,

		XBoxOne_A,
		XBoxOne_B,
		XBoxOne_X,
		XBoxOne_Y,
		XBoxOne_LeftBumper,
		XBoxOne_RightBumper,
		XBoxOne_Menu,
		XBoxOne_View,
		XBoxOne_LeftTrigger_Pull,
		XBoxOne_LeftTrigger_Click,
		XBoxOne_RightTrigger_Pull,
		XBoxOne_RightTrigger_Click,
		XBoxOne_LeftStick_Move,
		XBoxOne_LeftStick_Click,
		XBoxOne_LeftStick_DPadNorth,
		XBoxOne_LeftStick_DPadSouth,
		XBoxOne_LeftStick_DPadWest,
		XBoxOne_LeftStick_DPadEast,
		XBoxOne_RightStick_Move,
		XBoxOne_RightStick_Click,
		XBoxOne_RightStick_DPadNorth,
		XBoxOne_RightStick_DPadSouth,
		XBoxOne_RightStick_DPadWest,
		XBoxOne_RightStick_DPadEast,
		XBoxOne_DPad_North,
		XBoxOne_DPad_South,
		XBoxOne_DPad_West,
		XBoxOne_DPad_East,

		XBox360_A,
		XBox360_B,
		XBox360_X,
		XBox360_Y,
		XBox360_LeftBumper,
		XBox360_RightBumper,
		XBox360_Start,
		XBox360_Back,
		XBox360_LeftTrigger_Pull,
		XBox360_LeftTrigger_Click,
		XBox360_RightTrigger_Pull,
		XBox360_RightTrigger_Click,
		XBox360_LeftStick_Move,
		XBox360_LeftStick_Click,
		XBox360_LeftStick_DPadNorth,
		XBox360_LeftStick_DPadSouth,
		XBox360_LeftStick_DPadWest,
		XBox360_LeftStick_DPadEast,
		XBox360_RightStick_Move,
		XBox360_RightStick_Click,
		XBox360_RightStick_DPadNorth,
		XBox360_RightStick_DPadSouth,
		XBox360_RightStick_DPadWest,
		XBox360_RightStick_DPadEast,
		XBox360_DPad_North,
		XBox360_DPad_South,
		XBox360_DPad_West,
		XBox360_DPad_East,

		SteamV2_A,
		SteamV2_B,
		SteamV2_X,
		SteamV2_Y,
		SteamV2_LeftBumper,
		SteamV2_RightBumper,
		SteamV2_LeftGrip,
		SteamV2_RightGrip,
		SteamV2_Start,
		SteamV2_Back,
		SteamV2_LeftPad_Touch,
		SteamV2_LeftPad_Swipe,
		SteamV2_LeftPad_Click,
		SteamV2_LeftPad_DPadNorth,
		SteamV2_LeftPad_DPadSouth,
		SteamV2_LeftPad_DPadWest,
		SteamV2_LeftPad_DPadEast,
		SteamV2_RightPad_Touch,
		SteamV2_RightPad_Swipe,
		SteamV2_RightPad_Click,
		SteamV2_RightPad_DPadNorth,
		SteamV2_RightPad_DPadSouth,
		SteamV2_RightPad_DPadWest,
		SteamV2_RightPad_DPadEast,
		SteamV2_LeftTrigger_Pull,
		SteamV2_LeftTrigger_Click,
		SteamV2_RightTrigger_Pull,
		SteamV2_RightTrigger_Click,
		SteamV2_LeftStick_Move,
		SteamV2_LeftStick_Click,
		SteamV2_LeftStick_DPadNorth,
		SteamV2_LeftStick_DPadSouth,
		SteamV2_LeftStick_DPadWest,
		SteamV2_LeftStick_DPadEast,
		SteamV2_Gyro_Move,
		SteamV2_Gyro_Pitch,
		SteamV2_Gyro_Yaw,
		SteamV2_Gyro_Roll;

		private static final ActionOrigin[] values = values();

		static ActionOrigin byOrdinal(int ordinal) {
			return values[ordinal];
		}
	}

	public enum LEDFlag {
		SetColor,
		RestoreUserDefault
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

	public void triggerVibration(SteamControllerHandle controller, short leftSpeed, short rightSpeed) {
		triggerVibration(pointer, controller.handle, leftSpeed, rightSpeed);
	}

	public void setLEDColor(SteamControllerHandle controller, int colorR, int colorG, int colorB, LEDFlag flags) {
		setLEDColor(pointer, controller.handle, (byte) (colorR & 0xff),
				(byte) (colorG & 0xff), (byte) (colorB & 0xff), flags.ordinal());
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

	public String getStringForActionOrigin(ActionOrigin origin) {
		return getStringForActionOrigin(pointer, origin.ordinal());
	}

	public String getGlyphForActionOrigin(ActionOrigin origin) {
		return getGlyphForActionOrigin(pointer, origin.ordinal());
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

	private static native void triggerVibration(long pointer,
												long controllerHandle,
												short leftSpeed,
												short rightSpeed); /*

		ISteamController* controller = (ISteamController*) pointer;
		controller->TriggerVibration((ControllerHandle_t) controllerHandle,
			(unsigned short) leftSpeed, (unsigned short) rightSpeed);
	*/

	private static native void setLEDColor(long pointer, long controllerHandle,
										   byte colorR, byte colorG, byte colorB, int flags); /*

		ISteamController* controller = (ISteamController*) pointer;
		controller->SetLEDColor((ControllerHandle_t) controllerHandle,
			colorR, colorG, colorB, (unsigned int) flags);
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

	private static native String getStringForActionOrigin(long pointer, int origin); /*
		ISteamController* controller = (ISteamController*) pointer;
		return env->NewStringUTF(controller->GetStringForActionOrigin((EControllerActionOrigin) origin));
	*/

	private static native String getGlyphForActionOrigin(long pointer, int origin ); /*
		ISteamController* controller = (ISteamController*) pointer;
		return env->NewStringUTF(controller->GetGlyphForActionOrigin((EControllerActionOrigin) origin));
	*/

}
