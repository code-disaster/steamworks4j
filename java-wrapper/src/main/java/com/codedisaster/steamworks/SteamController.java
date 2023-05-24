package com.codedisaster.steamworks;

@SuppressWarnings({ "unused", "UnusedReturnValue" })
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
		LeftBumper,
		RightBumper,
		Gyro,
		CenterTrackpad,
		RightJoystick,
		DPad,
		Key,
		Mouse,
		LeftGyro
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
		SingleButton,
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
		SteamV2_LeftGrip_Lower,
		SteamV2_LeftGrip_Upper,
		SteamV2_RightGrip_Lower,
		SteamV2_RightGrip_Upper,
		SteamV2_LeftBumper_Pressure,
		SteamV2_RightBumper_Pressure,
		SteamV2_LeftGrip_Pressure,
		SteamV2_RightGrip_Pressure,
		SteamV2_LeftGrip_Upper_Pressure,
		SteamV2_RightGrip_Upper_Pressure,
		SteamV2_Start,
		SteamV2_Back,
		SteamV2_LeftPad_Touch,
		SteamV2_LeftPad_Swipe,
		SteamV2_LeftPad_Click,
		SteamV2_LeftPad_Pressure,
		SteamV2_LeftPad_DPadNorth,
		SteamV2_LeftPad_DPadSouth,
		SteamV2_LeftPad_DPadWest,
		SteamV2_LeftPad_DPadEast,
		SteamV2_RightPad_Touch,
		SteamV2_RightPad_Swipe,
		SteamV2_RightPad_Click,
		SteamV2_RightPad_Pressure,
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
		SteamV2_Gyro_Roll,

		Switch_A,
		Switch_B,
		Switch_X,
		Switch_Y,
		Switch_LeftBumper,
		Switch_RightBumper,
		Switch_Plus,
		Switch_Minus,
		Switch_Capture,
		Switch_LeftTrigger_Pull,
		Switch_LeftTrigger_Click,
		Switch_RightTrigger_Pull,
		Switch_RightTrigger_Click,
		Switch_LeftStick_Move,
		Switch_LeftStick_Click,
		Switch_LeftStick_DPadNorth,
		Switch_LeftStick_DPadSouth,
		Switch_LeftStick_DPadWest,
		Switch_LeftStick_DPadEast,
		Switch_RightStick_Move,
		Switch_RightStick_Click,
		Switch_RightStick_DPadNorth,
		Switch_RightStick_DPadSouth,
		Switch_RightStick_DPadWest,
		Switch_RightStick_DPadEast,
		Switch_DPad_North,
		Switch_DPad_South,
		Switch_DPad_West,
		Switch_DPad_East,
		Switch_ProGyro_Move,
		Switch_ProGyro_Pitch,
		Switch_ProGyro_Yaw,
		Switch_ProGyro_Roll,
		Switch_RightGyro_Move,
		Switch_RightGyro_Pitch,
		Switch_RightGyro_Yaw,
		Switch_RightGyro_Roll,
		Switch_LeftGyro_Move,
		Switch_LeftGyro_Pitch,
		Switch_LeftGyro_Yaw,
		Switch_LeftGyro_Roll,
		Switch_LeftGrip_Lower,
		Switch_LeftGrip_Upper,
		Switch_RightGrip_Lower,
		Switch_RightGrip_Upper,

		PS4_DPad_Move,
		XBoxOne_DPad_Move,
		XBox360_DPad_Move,
		Switch_DPad_Move,

		PS5_X,
		PS5_Circle,
		PS5_Triangle,
		PS5_Square,
		PS5_LeftBumper,
		PS5_RightBumper,
		PS5_Option,
		PS5_Create,
		PS5_Mute,
		PS5_LeftPad_Touch,
		PS5_LeftPad_Swipe,
		PS5_LeftPad_Click,
		PS5_LeftPad_DPadNorth,
		PS5_LeftPad_DPadSouth,
		PS5_LeftPad_DPadWest,
		PS5_LeftPad_DPadEast,
		PS5_RightPad_Touch,
		PS5_RightPad_Swipe,
		PS5_RightPad_Click,
		PS5_RightPad_DPadNorth,
		PS5_RightPad_DPadSouth,
		PS5_RightPad_DPadWest,
		PS5_RightPad_DPadEast,
		PS5_CenterPad_Touch,
		PS5_CenterPad_Swipe,
		PS5_CenterPad_Click,
		PS5_CenterPad_DPadNorth,
		PS5_CenterPad_DPadSouth,
		PS5_CenterPad_DPadWest,
		PS5_CenterPad_DPadEast,
		PS5_LeftTrigger_Pull,
		PS5_LeftTrigger_Click,
		PS5_RightTrigger_Pull,
		PS5_RightTrigger_Click,
		PS5_LeftStick_Move,
		PS5_LeftStick_Click,
		PS5_LeftStick_DPadNorth,
		PS5_LeftStick_DPadSouth,
		PS5_LeftStick_DPadWest,
		PS5_LeftStick_DPadEast,
		PS5_RightStick_Move,
		PS5_RightStick_Click,
		PS5_RightStick_DPadNorth,
		PS5_RightStick_DPadSouth,
		PS5_RightStick_DPadWest,
		PS5_RightStick_DPadEast,
		PS5_DPad_Move,
		PS5_DPad_North,
		PS5_DPad_South,
		PS5_DPad_West,
		PS5_DPad_East,
		PS5_Gyro_Move,
		PS5_Gyro_Pitch,
		PS5_Gyro_Yaw,
		PS5_Gyro_Roll,

		XBoxOne_LeftGrip_Lower,
		XBoxOne_LeftGrip_Upper,
		XBoxOne_RightGrip_Lower,
		XBoxOne_RightGrip_Upper,
		XBoxOne_Share,

		SteamDeck_A,
		SteamDeck_B,
		SteamDeck_X,
		SteamDeck_Y,
		SteamDeck_L1,
		SteamDeck_R1,
		SteamDeck_Menu,
		SteamDeck_View,
		SteamDeck_LeftPad_Touch,
		SteamDeck_LeftPad_Swipe,
		SteamDeck_LeftPad_Click,
		SteamDeck_LeftPad_DPadNorth,
		SteamDeck_LeftPad_DPadSouth,
		SteamDeck_LeftPad_DPadWest,
		SteamDeck_LeftPad_DPadEast,
		SteamDeck_RightPad_Touch,
		SteamDeck_RightPad_Swipe,
		SteamDeck_RightPad_Click,
		SteamDeck_RightPad_DPadNorth,
		SteamDeck_RightPad_DPadSouth,
		SteamDeck_RightPad_DPadWest,
		SteamDeck_RightPad_DPadEast,
		SteamDeck_L2_SoftPull,
		SteamDeck_L2,
		SteamDeck_R2_SoftPull,
		SteamDeck_R2,
		SteamDeck_LeftStick_Move,
		SteamDeck_L3,
		SteamDeck_LeftStick_DPadNorth,
		SteamDeck_LeftStick_DPadSouth,
		SteamDeck_LeftStick_DPadWest,
		SteamDeck_LeftStick_DPadEast,
		SteamDeck_LeftStick_Touch,
		SteamDeck_RightStick_Move,
		SteamDeck_R3,
		SteamDeck_RightStick_DPadNorth,
		SteamDeck_RightStick_DPadSouth,
		SteamDeck_RightStick_DPadWest,
		SteamDeck_RightStick_DPadEast,
		SteamDeck_RightStick_Touch,
		SteamDeck_L4,
		SteamDeck_R4,
		SteamDeck_L5,
		SteamDeck_R5,
		SteamDeck_DPad_Move,
		SteamDeck_DPad_North,
		SteamDeck_DPad_South,
		SteamDeck_DPad_West,
		SteamDeck_DPad_East,
		SteamDeck_Gyro_Move,
		SteamDeck_Gyro_Pitch,
		SteamDeck_Gyro_Yaw,
		SteamDeck_Gyro_Roll,
		SteamDeck_Reserved1,
		SteamDeck_Reserved2,
		SteamDeck_Reserved3,
		SteamDeck_Reserved4,
		SteamDeck_Reserved5,
		SteamDeck_Reserved6,
		SteamDeck_Reserved7,
		SteamDeck_Reserved8,
		SteamDeck_Reserved9,
		SteamDeck_Reserved10,
		SteamDeck_Reserved11,
		SteamDeck_Reserved12,
		SteamDeck_Reserved13,
		SteamDeck_Reserved14,
		SteamDeck_Reserved15,
		SteamDeck_Reserved16,
		SteamDeck_Reserved17,
		SteamDeck_Reserved18,
		SteamDeck_Reserved19,
		SteamDeck_Reserved20,

		Switch_JoyConButton_N,
		Switch_JoyConButton_E,
		Switch_JoyConButton_S,
		Switch_JoyConButton_W,

		PS5_LeftGrip,
		PS5_RightGrip,
		PS5_LeftFn,
		PS5_RightFn;

		private static final ActionOrigin[] values = values();

		static ActionOrigin byOrdinal(int ordinal) {
			return values[ordinal];
		}
	}

	public enum XboxOrigin {
		A,
		B,
		X,
		Y,
		LeftBumper,
		RightBumper,
		Menu,
		View,
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
		RightStick_Move,
		RightStick_Click,
		RightStick_DPadNorth,
		RightStick_DPadSouth,
		RightStick_DPadWest,
		RightStick_DPadEast,
		DPad_North,
		DPad_South,
		DPad_West,
		DPad_East
	}

	public enum InputType {
		Unknown,
		SteamController,
		XBox360Controller,
		XBoxOneController,
		GenericGamepad,
		PS4Controller,
		AppleMFiController,
		AndroidController,
		SwitchJoyConPair,
		SwitchJoyConSingle,
		SwitchProController,
		MobileTouch,
		PS3Controller,
		PS5Controller;

		private static final InputType[] values = values();

		static InputType byOrdinal(int ordinal) {
			return values[ordinal];
		}
	}

	public enum LEDFlag {
		SetColor,
		RestoreUserDefault
	}

	public static final int STEAM_CONTROLLER_MAX_COUNT = 16;
	public static final int STEAM_CONTROLLER_MAX_ANALOG_ACTIONS = 24;
	public static final int STEAM_CONTROLLER_MAX_DIGITAL_ACTIONS = 256;
	public static final int STEAM_CONTROLLER_MAX_ORIGINS = 8;

	public static final long STEAM_CONTROLLER_HANDLE_ALL_CONTROLLERS = 0xffffffffffffffffL;

	public static final float STEAM_CONTROLLER_MIN_ANALOG_ACTION_DATA = -1.0f;
	public static final float STEAM_CONTROLLER_MAX_ANALOG_ACTION_DATA = 1.0f;

	private final long[] controllerHandles = new long[STEAM_CONTROLLER_MAX_COUNT];
	private final int[] actionOrigins = new int[STEAM_CONTROLLER_MAX_ORIGINS];

	public SteamController() {
		super(-1);
	}

	public boolean init() {
		return SteamControllerNative.init();
	}

	public boolean shutdown() {
		return SteamControllerNative.shutdown();
	}

	public void runFrame() {
		SteamControllerNative.runFrame();
	}

	public int getConnectedControllers(SteamControllerHandle[] handlesOut) {
		if (handlesOut.length < STEAM_CONTROLLER_MAX_COUNT) {
			throw new IllegalArgumentException("Array size must be at least STEAM_CONTROLLER_MAX_COUNT");
		}

		int count = SteamControllerNative.getConnectedControllers(controllerHandles);

		for (int i = 0; i < count; i++) {
			handlesOut[i] = new SteamControllerHandle(controllerHandles[i]);
		}

		return count;
	}

	public boolean showBindingPanel(SteamControllerHandle controller) {
		return SteamControllerNative.showBindingPanel(controller.handle);
	}

	public SteamControllerActionSetHandle getActionSetHandle(String actionSetName) {
		return new SteamControllerActionSetHandle(SteamControllerNative.getActionSetHandle(actionSetName));
	}

	public void activateActionSet(SteamControllerHandle controller, SteamControllerActionSetHandle actionSet) {
		SteamControllerNative.activateActionSet(controller.handle, actionSet.handle);
	}

	public SteamControllerActionSetHandle getCurrentActionSet(SteamControllerHandle controller) {
		return new SteamControllerActionSetHandle(SteamControllerNative.getCurrentActionSet(controller.handle));
	}

	public SteamControllerDigitalActionHandle getDigitalActionHandle(String actionName) {
		return new SteamControllerDigitalActionHandle(SteamControllerNative.getDigitalActionHandle(actionName));
	}

	public void getDigitalActionData(SteamControllerHandle controller,
									 SteamControllerDigitalActionHandle digitalAction,
									 SteamControllerDigitalActionData digitalActionData) {

		SteamControllerNative.getDigitalActionData(controller.handle, digitalAction.handle, digitalActionData);
	}

	public int getDigitalActionOrigins(SteamControllerHandle controller,
									   SteamControllerActionSetHandle actionSet,
									   SteamControllerDigitalActionHandle digitalAction,
									   ActionOrigin[] originsOut) {

		if (originsOut.length < STEAM_CONTROLLER_MAX_ORIGINS) {
			throw new IllegalArgumentException("Array size must be at least STEAM_CONTROLLER_MAX_ORIGINS");
		}

		int count = SteamControllerNative.getDigitalActionOrigins(controller.handle,
				actionSet.handle, digitalAction.handle, actionOrigins);

		for (int i = 0; i < count; i++) {
			originsOut[i] = ActionOrigin.byOrdinal(actionOrigins[i]);
		}

		return count;
	}

	public SteamControllerAnalogActionHandle getAnalogActionHandle(String actionName) {
		return new SteamControllerAnalogActionHandle(SteamControllerNative.getAnalogActionHandle(actionName));
	}

	public void getAnalogActionData(SteamControllerHandle controller,
									SteamControllerAnalogActionHandle analogAction,
									SteamControllerAnalogActionData analoglActionData) {

		SteamControllerNative.getAnalogActionData(controller.handle, analogAction.handle, analoglActionData);
	}

	public int getAnalogActionOrigins(SteamControllerHandle controller,
									  SteamControllerActionSetHandle actionSet,
									  SteamControllerAnalogActionHandle analogAction,
									  ActionOrigin[] originsOut) {

		if (originsOut.length < STEAM_CONTROLLER_MAX_ORIGINS) {
			throw new IllegalArgumentException("Array size must be at least STEAM_CONTROLLER_MAX_ORIGINS");
		}

		int count = SteamControllerNative.getAnalogActionOrigins(controller.handle,
				actionSet.handle, analogAction.handle, actionOrigins);

		for (int i = 0; i < count; i++) {
			originsOut[i] = ActionOrigin.byOrdinal(actionOrigins[i]);
		}

		return count;
	}

	public void stopAnalogActionMomentum(SteamControllerHandle controller,
										 SteamControllerAnalogActionHandle analogAction) {

		SteamControllerNative.stopAnalogActionMomentum(controller.handle, analogAction.handle);
	}

	public void triggerHapticPulse(SteamControllerHandle controller, Pad targetPad, int durationMicroSec) {
		SteamControllerNative.triggerHapticPulse(controller.handle, targetPad.ordinal(), durationMicroSec);
	}

	public void triggerRepeatedHapticPulse(SteamControllerHandle controller, Pad targetPad,
										   int durationMicroSec, int offMicroSec, int repeat, int flags) {

		SteamControllerNative.triggerRepeatedHapticPulse(controller.handle, targetPad.ordinal(),
				durationMicroSec, offMicroSec, repeat, flags);
	}

	public void triggerVibration(SteamControllerHandle controller, short leftSpeed, short rightSpeed) {
		SteamControllerNative.triggerVibration(controller.handle, leftSpeed, rightSpeed);
	}

	public void setLEDColor(SteamControllerHandle controller, int colorR, int colorG, int colorB, LEDFlag flags) {
		SteamControllerNative.setLEDColor(controller.handle, (byte) (colorR & 0xff),
				(byte) (colorG & 0xff), (byte) (colorB & 0xff), flags.ordinal());
	}

	public int getGamepadIndexForController(SteamControllerHandle controller) {
		return SteamControllerNative.getGamepadIndexForController(controller.handle);
	}

	public SteamControllerHandle getControllerForGamepadIndex(int index) {
		return new SteamControllerHandle(SteamControllerNative.getControllerForGamepadIndex(index));
	}

	public void getMotionData(SteamControllerHandle controller, SteamControllerMotionData motionData) {
		SteamControllerNative.getMotionData(controller.handle, motionData.data);
	}

	public String getStringForActionOrigin(ActionOrigin origin) {
		return SteamControllerNative.getStringForActionOrigin(origin.ordinal());
	}

	public String getGlyphForActionOrigin(ActionOrigin origin) {
		return SteamControllerNative.getGlyphForActionOrigin(origin.ordinal());
	}

	public InputType getInputTypeForHandle(SteamControllerHandle controller) {
		return InputType.byOrdinal(SteamControllerNative.getInputTypeForHandle(controller.handle));
	}

}
