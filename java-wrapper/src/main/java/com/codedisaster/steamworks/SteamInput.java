package com.codedisaster.steamworks;

@SuppressWarnings({ "unused", "UnusedReturnValue" })
public class SteamInput extends SteamInterface {

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
			return ordinal >= 0 && ordinal < values.length ? values[ordinal] : None;
		}
	}

	public enum ActionOrigin {
		None,
		SteamController_A,
		SteamController_B,
		SteamController_X,
		SteamController_Y,
		SteamController_LeftBumper,
		SteamController_RightBumper,
		SteamController_LeftGrip,
		SteamController_RightGrip,
		SteamController_Start,
		SteamController_Back,
		SteamController_LeftPad_Touch,
		SteamController_LeftPad_Swipe,
		SteamController_LeftPad_Click,
		SteamController_LeftPad_DPadNorth,
		SteamController_LeftPad_DPadSouth,
		SteamController_LeftPad_DPadWest,
		SteamController_LeftPad_DPadEast,
		SteamController_RightPad_Touch,
		SteamController_RightPad_Swipe,
		SteamController_RightPad_Click,
		SteamController_RightPad_DPadNorth,
		SteamController_RightPad_DPadSouth,
		SteamController_RightPad_DPadWest,
		SteamController_RightPad_DPadEast,
		SteamController_LeftTrigger_Pull,
		SteamController_LeftTrigger_Click,
		SteamController_RightTrigger_Pull,
		SteamController_RightTrigger_Click,
		SteamController_LeftStick_Move,
		SteamController_LeftStick_Click,
		SteamController_LeftStick_DPadNorth,
		SteamController_LeftStick_DPadSouth,
		SteamController_LeftStick_DPadWest,
		SteamController_LeftStick_DPadEast,
		SteamController_Gyro_Move,
		SteamController_Gyro_Pitch,
		SteamController_Gyro_Yaw,
		SteamController_Gyro_Roll,
		SteamController_Reserved0,
		SteamController_Reserved1,
		SteamController_Reserved2,
		SteamController_Reserved3,
		SteamController_Reserved4,
		SteamController_Reserved5,
		SteamController_Reserved6,
		SteamController_Reserved7,
		SteamController_Reserved8,
		SteamController_Reserved9,
		SteamController_Reserved10,
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
		PS4_DPad_Move,
		PS4_Reserved1,
		PS4_Reserved2,
		PS4_Reserved3,
		PS4_Reserved4,
		PS4_Reserved5,
		PS4_Reserved6,
		PS4_Reserved7,
		PS4_Reserved8,
		PS4_Reserved9,
		PS4_Reserved10,
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
		XBoxOne_DPad_Move,
		XBoxOne_LeftGrip_Lower,
		XBoxOne_LeftGrip_Upper,
		XBoxOne_RightGrip_Lower,
		XBoxOne_RightGrip_Upper,
		XBoxOne_Share,
		XBoxOne_Reserved6,
		XBoxOne_Reserved7,
		XBoxOne_Reserved8,
		XBoxOne_Reserved9,
		XBoxOne_Reserved10,
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
		XBox360_DPad_Move,
		XBox360_Reserved1,
		XBox360_Reserved2,
		XBox360_Reserved3,
		XBox360_Reserved4,
		XBox360_Reserved5,
		XBox360_Reserved6,
		XBox360_Reserved7,
		XBox360_Reserved8,
		XBox360_Reserved9,
		XBox360_Reserved10,
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
		Switch_DPad_Move,
		Switch_Reserved1,
		Switch_Reserved2,
		Switch_Reserved3,
		Switch_Reserved4,
		Switch_Reserved5,
		Switch_Reserved6,
		Switch_Reserved7,
		Switch_Reserved8,
		Switch_Reserved9,
		Switch_Reserved10,
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
		Switch_JoyConButton_N,
		Switch_JoyConButton_E,
		Switch_JoyConButton_S,
		Switch_JoyConButton_W,
		Switch_Reserved15,
		Switch_Reserved16,
		Switch_Reserved17,
		Switch_Reserved18,
		Switch_Reserved19,
		Switch_Reserved20,
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
		PS5_DPad_North,
		PS5_DPad_South,
		PS5_DPad_West,
		PS5_DPad_East,
		PS5_Gyro_Move,
		PS5_Gyro_Pitch,
		PS5_Gyro_Yaw,
		PS5_Gyro_Roll,
		PS5_DPad_Move,
		PS5_LeftGrip,
		PS5_RightGrip,
		PS5_LeftFn,
		PS5_RightFn,
		PS5_Reserved5,
		PS5_Reserved6,
		PS5_Reserved7,
		PS5_Reserved8,
		PS5_Reserved9,
		PS5_Reserved10,
		PS5_Reserved11,
		PS5_Reserved12,
		PS5_Reserved13,
		PS5_Reserved14,
		PS5_Reserved15,
		PS5_Reserved16,
		PS5_Reserved17,
		PS5_Reserved18,
		PS5_Reserved19,
		PS5_Reserved20,
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
		Horipad_M1,
		Horipad_M2,
		Horipad_L4,
		Horipad_R4,
		LenovoLegionGo_A,
		LenovoLegionGo_B,
		LenovoLegionGo_X,
		LenovoLegionGo_Y,
		LenovoLegionGo_LB,
		LenovoLegionGo_RB,
		LenovoLegionGo_Menu,
		LenovoLegionGo_View,
		LenovoLegionGo_LeftPad_Touch,
		LenovoLegionGo_LeftPad_Swipe,
		LenovoLegionGo_LeftPad_Click,
		LenovoLegionGo_LeftPad_DPadNorth,
		LenovoLegionGo_LeftPad_DPadSouth,
		LenovoLegionGo_LeftPad_DPadWest,
		LenovoLegionGo_LeftPad_DPadEast,
		LenovoLegionGo_RightPad_Touch,
		LenovoLegionGo_RightPad_Swipe,
		LenovoLegionGo_RightPad_Click,
		LenovoLegionGo_RightPad_DPadNorth,
		LenovoLegionGo_RightPad_DPadSouth,
		LenovoLegionGo_RightPad_DPadWest,
		LenovoLegionGo_RightPad_DPadEast,
		LenovoLegionGo_LT_SoftPull,
		LenovoLegionGo_LT,
		LenovoLegionGo_RT_SoftPull,
		LenovoLegionGo_RT,
		LenovoLegionGo_LeftStick_Move,
		LenovoLegionGo_LS,
		LenovoLegionGo_LeftStick_DPadNorth,
		LenovoLegionGo_LeftStick_DPadSouth,
		LenovoLegionGo_LeftStick_DPadWest,
		LenovoLegionGo_LeftStick_DPadEast,
		LenovoLegionGo_RightStick_Move,
		LenovoLegionGo_RS,
		LenovoLegionGo_RightStick_DPadNorth,
		LenovoLegionGo_RightStick_DPadSouth,
		LenovoLegionGo_RightStick_DPadWest,
		LenovoLegionGo_RightStick_DPadEast,
		LenovoLegionGo_Y1,
		LenovoLegionGo_Y2,
		LenovoLegionGo_DPad_Move,
		LenovoLegionGo_DPad_North,
		LenovoLegionGo_DPad_South,
		LenovoLegionGo_DPad_West,
		LenovoLegionGo_DPad_East,
		LenovoLegionGo_Gyro_Move,
		LenovoLegionGo_Gyro_Pitch,
		LenovoLegionGo_Gyro_Yaw,
		LenovoLegionGo_Gyro_Roll,
		LenovoLegionGo_Reserved1,
		LenovoLegionGo_Reserved2,
		LenovoLegionGo_Reserved3,
		LenovoLegionGo_Reserved4,
		LenovoLegionGo_Reserved5,
		LenovoLegionGo_Reserved6,
		LenovoLegionGo_Reserved7,
		LenovoLegionGo_Reserved8,
		LenovoLegionGo_Reserved9,
		LenovoLegionGo_Reserved10,
		LenovoLegionGo_Reserved11,
		LenovoLegionGo_Reserved12,
		LenovoLegionGo_Reserved13,
		LenovoLegionGo_Reserved14,
		LenovoLegionGo_Reserved15,
		LenovoLegionGo_Reserved16,
		LenovoLegionGo_Reserved17,
		LenovoLegionGo_Reserved18,
		LenovoLegionGo_Reserved19,
		LenovoLegionGo_Reserved20,
		Generic_L4,
		Generic_R4,
		Generic_L5,
		Generic_R5,
		Generic_PL,
		Generic_PR,
		Generic_C,
		Generic_Z,
		Generic_MISC1,
		Generic_MISC2,
		Generic_MISC3,
		Generic_MISC4,
		Generic_MISC5,
		Generic_MISC6,
		Generic_MISC7,
		Generic_MISC8,
		Switch2_A,
		Switch2_B,
		Switch2_X,
		Switch2_Y,
		Switch2_LeftBumper,
		Switch2_RightBumper,
		Switch2_Plus,
		Switch2_Minus,
		Switch2_Capture,
		Switch2_LeftTrigger_Pull,
		Switch2_LeftTrigger_Click,
		Switch2_RightTrigger_Pull,
		Switch2_RightTrigger_Click,
		Switch2_LeftStick_Move,
		Switch2_LeftStick_Click,
		Switch2_LeftStick_DPadNorth,
		Switch2_LeftStick_DPadSouth,
		Switch2_LeftStick_DPadWest,
		Switch2_LeftStick_DPadEast,
		Switch2_RightStick_Move,
		Switch2_RightStick_Click,
		Switch2_RightStick_DPadNorth,
		Switch2_RightStick_DPadSouth,
		Switch2_RightStick_DPadWest,
		Switch2_RightStick_DPadEast,
		Switch2_DPad_Move,
		Switch2_DPad_North,
		Switch2_DPad_South,
		Switch2_DPad_West,
		Switch2_DPad_East,
		Switch2_ProGyro_Move,
		Switch2_ProGyro_Pitch,
		Switch2_ProGyro_Yaw,
		Switch2_ProGyro_Roll,
		Switch2_GL,
		Switch2_GR,
		Switch2_C,
		Switch2_Reserved1,
		Switch2_Reserved2,
		Switch2_Reserved3,
		Switch2_Reserved4,
		Switch2_Reserved5,
		Switch2_Reserved6,
		Switch2_Reserved7,
		Switch2_Reserved8,
		Switch2_Reserved9,
		Switch2_Reserved10,
		SteamController2026_A,
		SteamController2026_B,
		SteamController2026_X,
		SteamController2026_Y,
		SteamController2026_L1,
		SteamController2026_R1,
		SteamController2026_Menu,
		SteamController2026_View,
		SteamController2026_LeftPad_Touch,
		SteamController2026_LeftPad_Swipe,
		SteamController2026_LeftPad_Click,
		SteamController2026_LeftPad_DPadNorth,
		SteamController2026_LeftPad_DPadSouth,
		SteamController2026_LeftPad_DPadWest,
		SteamController2026_LeftPad_DPadEast,
		SteamController2026_RightPad_Touch,
		SteamController2026_RightPad_Swipe,
		SteamController2026_RightPad_Click,
		SteamController2026_RightPad_DPadNorth,
		SteamController2026_RightPad_DPadSouth,
		SteamController2026_RightPad_DPadWest,
		SteamController2026_RightPad_DPadEast,
		SteamController2026_L2_SoftPull,
		SteamController2026_L2,
		SteamController2026_R2_SoftPull,
		SteamController2026_R2,
		SteamController2026_LeftStick_Move,
		SteamController2026_L3,
		SteamController2026_LeftStick_DPadNorth,
		SteamController2026_LeftStick_DPadSouth,
		SteamController2026_LeftStick_DPadWest,
		SteamController2026_LeftStick_DPadEast,
		SteamController2026_LeftStick_Touch,
		SteamController2026_RightStick_Move,
		SteamController2026_R3,
		SteamController2026_RightStick_DPadNorth,
		SteamController2026_RightStick_DPadSouth,
		SteamController2026_RightStick_DPadWest,
		SteamController2026_RightStick_DPadEast,
		SteamController2026_RightStick_Touch,
		SteamController2026_L4,
		SteamController2026_R4,
		SteamController2026_L5,
		SteamController2026_R5,
		SteamController2026_DPad_Move,
		SteamController2026_DPad_North,
		SteamController2026_DPad_South,
		SteamController2026_DPad_West,
		SteamController2026_DPad_East,
		SteamController2026_Gyro_Move,
		SteamController2026_Gyro_Pitch,
		SteamController2026_Gyro_Yaw,
		SteamController2026_Gyro_Roll,
		SteamController2026_LGrip,
		SteamController2026_RGrip,
		SteamController2026_Reserved1,
		SteamController2026_Reserved2,
		SteamController2026_Reserved3,
		SteamController2026_Reserved4,
		SteamController2026_Reserved5,
		SteamController2026_Reserved6,
		SteamController2026_Reserved7,
		SteamController2026_Reserved8,
		SteamController2026_Reserved9,
		SteamController2026_Reserved10,
		SteamController2026_Reserved11,
		SteamController2026_Reserved12,
		SteamController2026_Reserved13,
		SteamController2026_Reserved14,
		SteamController2026_Reserved15,
		SteamController2026_Reserved16,
		SteamController2026_Reserved17,
		SteamController2026_Reserved18,
		SteamController2026_Reserved19,
		SteamController2026_Reserved20,
		SteamFrameController_A_Click,
		SteamFrameController_A_Touch,
		SteamFrameController_B_Click,
		SteamFrameController_B_Touch,
		SteamFrameController_X_Click,
		SteamFrameController_X_Touch,
		SteamFrameController_Y_Click,
		SteamFrameController_Y_Touch,
		SteamFrameController_LeftTrigger_Pull,
		SteamFrameController_LeftTrigger_Click,
		SteamFrameController_LeftTrigger_Touch,
		SteamFrameController_RightTrigger_Pull,
		SteamFrameController_RightTrigger_Click,
		SteamFrameController_RightTrigger_Touch,
		SteamFrameController_Menu_Click,
		SteamFrameController_Menu_Touch,
		SteamFrameController_View_Click,
		SteamFrameController_View_Touch,
		SteamFrameController_LeftBumper_Click,
		SteamFrameController_LeftBumper_Touch,
		SteamFrameController_RightBumper_Click,
		SteamFrameController_RightBumper_Touch,
		SteamFrameController_LeftStick_Move,
		SteamFrameController_LeftStick_Click,
		SteamFrameController_LeftStick_DPadNorth,
		SteamFrameController_LeftStick_DPadSouth,
		SteamFrameController_LeftStick_DPadWest,
		SteamFrameController_LeftStick_DPadEast,
		SteamFrameController_LeftStick_Touch,
		SteamFrameController_RightStick_Move,
		SteamFrameController_RightStick_Click,
		SteamFrameController_RightStick_DPadNorth,
		SteamFrameController_RightStick_DPadSouth,
		SteamFrameController_RightStick_DPadWest,
		SteamFrameController_RightStick_DPadEast,
		SteamFrameController_RightStick_Touch,
		SteamFrameController_DPad_Move,
		SteamFrameController_DPad_North,
		SteamFrameController_DPad_South,
		SteamFrameController_DPad_West,
		SteamFrameController_DPad_East,
		SteamFrameController_DPad_Touch,
		SteamFrameController_LeftGyro_Move,
		SteamFrameController_LeftGyro_Pitch,
		SteamFrameController_LeftGyro_Yaw,
		SteamFrameController_LeftGyro_Roll,
		SteamFrameController_RightGyro_Move,
		SteamFrameController_RightGyro_Pitch,
		SteamFrameController_RightGyro_Yaw,
		SteamFrameController_RightGyro_Roll,
		SteamFrameController_LeftGrip_Pull,
		SteamFrameController_LeftGrip_Click,
		SteamFrameController_LeftGrip_Touch,
		SteamFrameController_RightGrip_Pull,
		SteamFrameController_RightGrip_Click,
		SteamFrameController_RightGrip_Touch,
		SteamFrameController_LeftThumbrest_Touch,
		SteamFrameController_RightThumbrest_Touch,
		SteamFrameController_Reserved1,
		SteamFrameController_Reserved2,
		SteamFrameController_Reserved3,
		SteamFrameController_Reserved4,
		SteamFrameController_Reserved5,
		SteamFrameController_Reserved6,
		SteamFrameController_Reserved7,
		SteamFrameController_Reserved8,
		SteamFrameController_Reserved9,
		SteamFrameController_Reserved10,
		SteamFrameController_Reserved11,
		SteamFrameController_Reserved12,
		SteamFrameController_Reserved13,
		SteamFrameController_Reserved14,
		SteamFrameController_Reserved15,
		SteamFrameController_Reserved16,
		SteamFrameController_Reserved17,
		SteamFrameController_Reserved18,
		SteamFrameController_Reserved19,
		SteamFrameController_Reserved20;

		private static final ActionOrigin[] values = values();

		static ActionOrigin byOrdinal(int ordinal) {
			return ordinal >= 0 && ordinal < values.length ? values[ordinal] : None;
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

	public enum ControllerPad {
		Left,
		Right,
		Both,
		GripLeft,
		GripRight,
		GripBoth
	}

	public enum HapticLocation {
		Left(1),
		Right(1 << 1),
		Both(1 | 1 << 1);

		private final int bits;

		HapticLocation(int bits) {
			this.bits = bits;
		}
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
		PS5Controller,
		SteamDeckController,
		SteamOSHandheld,
		Switch2ProController,
		SteamController2026,
		SteamFrameControllerPair;

		private static final InputType[] values = values();

		static InputType byOrdinal(int ordinal) {
			return ordinal >= 0 && ordinal < values.length ? values[ordinal] : Unknown;
		}
	}

	public enum ConfigurationEnableType {
		Playstation(0x0001),
		Xbox(0x0002),
		Generic(0x0004),
		Switch(0x0008);

		private final int bits;

		ConfigurationEnableType(int bits) {
			this.bits = bits;
		}

		public boolean isSet(int settings) {
			return (settings & bits) == bits;
		}
	}

	public enum LEDFlag {
		SetColor,
		RestoreUserDefault
	}

	public enum GlyphSize {
		Small,
		Medium,
		Large
	}

	public enum GlyphStyle {
		Knockout(0x0),
		Light(0x1),
		Dark(0x2),
		NeutralColorABXY(0x10),
		SolidABXY(0x20);

		private final int bits;

		GlyphStyle(int bits) {
			this.bits = bits;
		}

		public static int flags(GlyphStyle... styles) {
			int flags = 0;
			for (GlyphStyle style : styles) {
				flags |= style.bits;
			}
			return flags;
		}
	}

	public static final int STEAM_INPUT_MAX_COUNT = 16;
	public static final int STEAM_INPUT_MAX_ANALOG_ACTIONS = 24;
	public static final int STEAM_INPUT_MAX_DIGITAL_ACTIONS = 256;
	public static final int STEAM_INPUT_MAX_ORIGINS = 8;
	public static final int STEAM_INPUT_MAX_ACTIVE_LAYERS = 16;

	public static final long STEAM_INPUT_HANDLE_ALL_CONTROLLERS = 0xffffffffffffffffL;

	public static final float STEAM_INPUT_MIN_ANALOG_ACTION_DATA = -1.0f;
	public static final float STEAM_INPUT_MAX_ANALOG_ACTION_DATA = 1.0f;

	private final long[] inputHandles = new long[STEAM_INPUT_MAX_COUNT];
	private final long[] layerHandles = new long[STEAM_INPUT_MAX_ACTIVE_LAYERS];
	private final int[] actionOrigins = new int[STEAM_INPUT_MAX_ORIGINS];

	public SteamInput() {
		super(0L);
	}

	public SteamInput(SteamInputCallback callback) {
		super(SteamInputNative.createCallback(new SteamInputCallbackAdapter(callback)));
	}

	public boolean init(boolean explicitlyCallRunFrame) {
		return SteamInputNative.init(explicitlyCallRunFrame);
	}

	public boolean shutdown() {
		return SteamInputNative.shutdown();
	}

	public boolean setInputActionManifestFilePath(String inputActionManifestAbsolutePath) {
		return SteamInputNative.setInputActionManifestFilePath(inputActionManifestAbsolutePath);
	}

	public void runFrame() {
		SteamInputNative.runFrame();
	}

	public boolean waitForData(boolean waitForever, int timeout) {
		return SteamInputNative.waitForData(waitForever, timeout);
	}

	public boolean newDataAvailable() {
		return SteamInputNative.newDataAvailable();
	}

	public int getConnectedControllers(SteamInputHandle[] handlesOut) {
		if (handlesOut.length < STEAM_INPUT_MAX_COUNT) {
			throw new IllegalArgumentException("Array size must be at least STEAM_INPUT_MAX_COUNT");
		}

		int count = SteamInputNative.getConnectedControllers(inputHandles);

		for (int i = 0; i < count; i++) {
			handlesOut[i] = new SteamInputHandle(inputHandles[i]);
		}

		return count;
	}

	public void enableDeviceCallbacks() {
		SteamInputNative.enableDeviceCallbacks();
	}

	public void enableActionEventCallbacks(boolean enabled) {
		if (callback == 0) {
			throw new IllegalStateException("SteamInput was created without a SteamInputCallback");
		}
		SteamInputNative.enableActionEventCallbacks(callback, enabled);
	}

	public SteamInputActionSetHandle getActionSetHandle(String actionSetName) {
		return new SteamInputActionSetHandle(SteamInputNative.getActionSetHandle(actionSetName));
	}

	public void activateActionSet(SteamInputHandle input, SteamInputActionSetHandle actionSet) {
		SteamInputNative.activateActionSet(input.handle, actionSet.handle);
	}

	public SteamInputActionSetHandle getCurrentActionSet(SteamInputHandle input) {
		return new SteamInputActionSetHandle(SteamInputNative.getCurrentActionSet(input.handle));
	}

	public void activateActionSetLayer(SteamInputHandle input, SteamInputActionSetHandle actionSetLayer) {
		SteamInputNative.activateActionSetLayer(input.handle, actionSetLayer.handle);
	}

	public void deactivateActionSetLayer(SteamInputHandle input, SteamInputActionSetHandle actionSetLayer) {
		SteamInputNative.deactivateActionSetLayer(input.handle, actionSetLayer.handle);
	}

	public void deactivateAllActionSetLayers(SteamInputHandle input) {
		SteamInputNative.deactivateAllActionSetLayers(input.handle);
	}

	public int getActiveActionSetLayers(SteamInputHandle input, SteamInputActionSetHandle[] handlesOut) {
		if (handlesOut.length < STEAM_INPUT_MAX_ACTIVE_LAYERS) {
			throw new IllegalArgumentException("Array size must be at least STEAM_INPUT_MAX_ACTIVE_LAYERS");
		}

		int count = SteamInputNative.getActiveActionSetLayers(input.handle, layerHandles);

		for (int i = 0; i < count; i++) {
			handlesOut[i] = new SteamInputActionSetHandle(layerHandles[i]);
		}

		return count;
	}

	public SteamInputDigitalActionHandle getDigitalActionHandle(String actionName) {
		return new SteamInputDigitalActionHandle(SteamInputNative.getDigitalActionHandle(actionName));
	}

	public void getDigitalActionData(SteamInputHandle input,
									 SteamInputDigitalActionHandle digitalAction,
									 SteamInputDigitalActionData digitalActionData) {

		SteamInputNative.getDigitalActionData(input.handle, digitalAction.handle, digitalActionData);
	}

	public int getDigitalActionOrigins(SteamInputHandle input,
									   SteamInputActionSetHandle actionSet,
									   SteamInputDigitalActionHandle digitalAction,
									   ActionOrigin[] originsOut) {

		if (originsOut.length < STEAM_INPUT_MAX_ORIGINS) {
			throw new IllegalArgumentException("Array size must be at least STEAM_INPUT_MAX_ORIGINS");
		}

		int count = SteamInputNative.getDigitalActionOrigins(input.handle,
				actionSet.handle, digitalAction.handle, actionOrigins);

		for (int i = 0; i < count; i++) {
			originsOut[i] = ActionOrigin.byOrdinal(actionOrigins[i]);
		}

		return count;
	}

	public String getStringForDigitalActionName(SteamInputDigitalActionHandle digitalAction) {
		return SteamInputNative.getStringForDigitalActionName(digitalAction.handle);
	}

	public SteamInputAnalogActionHandle getAnalogActionHandle(String actionName) {
		return new SteamInputAnalogActionHandle(SteamInputNative.getAnalogActionHandle(actionName));
	}

	public void getAnalogActionData(SteamInputHandle input,
									SteamInputAnalogActionHandle analogAction,
									SteamInputAnalogActionData analogActionData) {

		SteamInputNative.getAnalogActionData(input.handle, analogAction.handle, analogActionData);
	}

	public int getAnalogActionOrigins(SteamInputHandle input,
									  SteamInputActionSetHandle actionSet,
									  SteamInputAnalogActionHandle analogAction,
									  ActionOrigin[] originsOut) {

		if (originsOut.length < STEAM_INPUT_MAX_ORIGINS) {
			throw new IllegalArgumentException("Array size must be at least STEAM_INPUT_MAX_ORIGINS");
		}

		int count = SteamInputNative.getAnalogActionOrigins(input.handle,
				actionSet.handle, analogAction.handle, actionOrigins);

		for (int i = 0; i < count; i++) {
			originsOut[i] = ActionOrigin.byOrdinal(actionOrigins[i]);
		}

		return count;
	}

	public String getGlyphPNGForActionOrigin(ActionOrigin origin, GlyphSize size, int flags) {
		return SteamInputNative.getGlyphPNGForActionOrigin(origin.ordinal(), size.ordinal(), flags);
	}

	public String getGlyphSVGForActionOrigin(ActionOrigin origin, int flags) {
		return SteamInputNative.getGlyphSVGForActionOrigin(origin.ordinal(), flags);
	}

	public String getGlyphForActionOriginLegacy(ActionOrigin origin) {
		return SteamInputNative.getGlyphForActionOriginLegacy(origin.ordinal());
	}

	public String getStringForActionOrigin(ActionOrigin origin) {
		return SteamInputNative.getStringForActionOrigin(origin.ordinal());
	}

	public String getStringForAnalogActionName(SteamInputAnalogActionHandle analogAction) {
		return SteamInputNative.getStringForAnalogActionName(analogAction.handle);
	}

	public void stopAnalogActionMomentum(SteamInputHandle input, SteamInputAnalogActionHandle analogAction) {
		SteamInputNative.stopAnalogActionMomentum(input.handle, analogAction.handle);
	}

	public void getMotionData(SteamInputHandle input, SteamInputMotionData motionData) {
		SteamInputNative.getMotionData(input.handle, motionData.data);
	}

	public void triggerVibration(SteamInputHandle input, short leftSpeed, short rightSpeed) {
		SteamInputNative.triggerVibration(input.handle, leftSpeed, rightSpeed);
	}

	public void triggerVibrationExtended(SteamInputHandle input, short leftSpeed, short rightSpeed,
										 short leftTriggerSpeed, short rightTriggerSpeed) {

		SteamInputNative.triggerVibrationExtended(input.handle, leftSpeed, rightSpeed,
				leftTriggerSpeed, rightTriggerSpeed);
	}

	public void triggerSimpleHapticEvent(SteamInputHandle input, HapticLocation location, int intensity,
										 int gainDB, int otherIntensity, int otherGainDB) {

		SteamInputNative.triggerSimpleHapticEvent(input.handle, location.bits, (byte) (intensity & 0xff),
				(byte) gainDB, (byte) (otherIntensity & 0xff), (byte) otherGainDB);
	}

	public void setLEDColor(SteamInputHandle input, int colorR, int colorG, int colorB, LEDFlag flags) {
		SteamInputNative.setLEDColor(input.handle, (byte) (colorR & 0xff),
				(byte) (colorG & 0xff), (byte) (colorB & 0xff), flags.ordinal());
	}

	public void legacyTriggerHapticPulse(SteamInputHandle input, ControllerPad targetPad, int durationMicroSec) {
		SteamInputNative.legacyTriggerHapticPulse(input.handle, targetPad.ordinal(), durationMicroSec);
	}

	public void legacyTriggerRepeatedHapticPulse(SteamInputHandle input, ControllerPad targetPad,
												 int durationMicroSec, int offMicroSec, int repeat, int flags) {

		SteamInputNative.legacyTriggerRepeatedHapticPulse(input.handle, targetPad.ordinal(),
				durationMicroSec, offMicroSec, repeat, flags);
	}

	public boolean showBindingPanel(SteamInputHandle input) {
		return SteamInputNative.showBindingPanel(input.handle);
	}

	public InputType getInputTypeForHandle(SteamInputHandle input) {
		return InputType.byOrdinal(SteamInputNative.getInputTypeForHandle(input.handle));
	}

	public SteamInputHandle getControllerForGamepadIndex(int index) {
		return new SteamInputHandle(SteamInputNative.getControllerForGamepadIndex(index));
	}

	public int getGamepadIndexForController(SteamInputHandle input) {
		return SteamInputNative.getGamepadIndexForController(input.handle);
	}

	public String getStringForXboxOrigin(XboxOrigin origin) {
		return SteamInputNative.getStringForXboxOrigin(origin.ordinal());
	}

	public String getGlyphForXboxOrigin(XboxOrigin origin) {
		return SteamInputNative.getGlyphForXboxOrigin(origin.ordinal());
	}

	public ActionOrigin getActionOriginFromXboxOrigin(SteamInputHandle input, XboxOrigin origin) {
		return ActionOrigin.byOrdinal(SteamInputNative.getActionOriginFromXboxOrigin(
				input.handle, origin.ordinal()
		));
	}

	public ActionOrigin translateActionOrigin(InputType destinationInputType, ActionOrigin sourceOrigin) {
		return ActionOrigin.byOrdinal(SteamInputNative.translateActionOrigin(
				destinationInputType.ordinal(), sourceOrigin.ordinal()));
	}

	public boolean getDeviceBindingRevision(SteamInputHandle input, int[] revisionOut) {
		if (revisionOut.length < 2) {
			throw new IllegalArgumentException("Array size must be at least 2");
		}

		return SteamInputNative.getDeviceBindingRevision(input.handle, revisionOut);
	}

	public int getRemotePlaySessionID(SteamInputHandle input) {
		return SteamInputNative.getRemotePlaySessionID(input.handle);
	}

	public void setDualSenseTriggerEffect(SteamInputHandle input, SteamInputDualSenseTriggerEffect effect) {
		SteamInputNative.setDualSenseTriggerEffect(input.handle, effect.triggerMask, effect.modes, effect.params);
	}

	public int getSessionInputConfigurationSettings() {
		return SteamInputNative.getSessionInputConfigurationSettings();
	}

}
