package com.codedisaster.steamworks.test;

import com.codedisaster.steamworks.*;

import static com.codedisaster.steamworks.SteamNativeHandle.getNativeHandle;

public class SteamInputTest extends SteamTestApp {

	private SteamInput input;
	private SteamInputHandle[] inputHandles = {};
	private int numInputs = 0;

	private final SteamInputMotionData motionData = new SteamInputMotionData();

	private SteamInputActionSetHandle setHandle;

	private SteamInputDigitalActionHandle digitalActionHandle;
	private final SteamInputDigitalActionData digitalActionData = new SteamInputDigitalActionData();
	private boolean digitalActionPressed;

	private SteamInputAnalogActionHandle analogActionHandle;
	private final SteamInputAnalogActionData analogActionData = new SteamInputAnalogActionData();

	private final SteamInputDualSenseTriggerEffect triggerEffect = new SteamInputDualSenseTriggerEffect();

	private final SteamInput.ActionOrigin[] origins = new SteamInput.ActionOrigin[SteamInput.STEAM_INPUT_MAX_ORIGINS];

	private final SteamInputCallback inputCallback = new SteamInputCallback() {
		@Override
		public void onDeviceConnected(SteamInputHandle device) {
			System.out.println("Input device connected: " + device + " (" + input.getInputTypeForHandle(device) + ")");
		}

		@Override
		public void onDeviceDisconnected(SteamInputHandle device) {
			System.out.println("Input device disconnected: " + device);
		}

		@Override
		public void onConfigurationLoaded(int appID, SteamInputHandle device, SteamID mappingCreator,
										  int majorRevision, int minorRevision,
										  boolean usesSteamInputAPI, boolean usesGamepadAPI) {
			System.out.println("Input configuration loaded: app=" + appID + ", device=" + device
					+ ", revision=" + majorRevision + "." + minorRevision
					+ ", steamInputAPI=" + usesSteamInputAPI + ", gamepadAPI=" + usesGamepadAPI);
		}

		@Override
		public void onDigitalActionEvent(SteamInputHandle controller, SteamInputDigitalActionHandle action,
										 boolean state, boolean active) {
			System.out.println("Digital action event: " + controller + ", action " + action
					+ ", state=" + state + ", active=" + active);
		}

		@Override
		public void onAnalogActionEvent(SteamInputHandle controller, SteamInputAnalogActionHandle action,
										SteamInput.SourceMode mode, float x, float y, boolean active) {
			System.out.println("Analog action event: " + controller + ", action " + action
					+ ", " + mode + " " + x + ", " + y + ", active=" + active);
		}

		@Override
		public void onGamepadSlotChange(int appID, SteamInputHandle device, SteamInput.InputType deviceType,
										int oldGamepadSlot, int newGamepadSlot) {
			System.out.println("Gamepad slot change: device=" + device + " (" + deviceType + "), "
					+ oldGamepadSlot + " -> " + newGamepadSlot);
		}
	};

	@Override
	protected void registerInterfaces() {
		System.out.println("Register input API ...");
		input = new SteamInput(inputCallback);
		input.init(true);
		input.enableDeviceCallbacks();

		String manifest = System.getProperty("steamworks4j.inputManifest");
		if (manifest != null) {
			System.out.println("  manifest '" + manifest + "': " + input.setInputActionManifestFilePath(manifest));
		}

		try {
			processInput("i list");
		} catch (SteamException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void unregisterInterfaces() {
		input.shutdown();
		input.dispose();
	}

	@Override
	protected void processUpdate() throws SteamException {
		input.runFrame();

		if (setHandle == null || getNativeHandle(setHandle) == 0) {
			return;
		}

		for (int i = 0; i < numInputs; i++) {
			SteamInputHandle handle = inputHandles[i];
			input.activateActionSet(handle, setHandle);

			if (digitalActionHandle != null) {
				input.getDigitalActionData(handle, digitalActionHandle, digitalActionData);
				boolean pressed = digitalActionData.getActive() && digitalActionData.getState();
				if (pressed != digitalActionPressed) {
					System.out.println("  digital action " + (pressed ? "pressed" : "released"));
					digitalActionPressed = pressed;
				}
			}

			if (analogActionHandle != null) {
				input.getAnalogActionData(handle, analogActionHandle, analogActionData);
				if (analogActionData.getActive()) {
					float x = analogActionData.getX();
					float y = analogActionData.getY();
					if (Math.abs(x) > 0.001f || Math.abs(y) > 0.001f) {
						System.out.println("  analog action: " + x + ", " + y + ", " + analogActionData.getMode());
					}
				}
			}
		}
	}

	@Override
	protected void processInput(String command) throws SteamException {
		if (command.equals("i list")) {
			inputHandles = new SteamInputHandle[SteamInput.STEAM_INPUT_MAX_COUNT];
			numInputs = input.getConnectedControllers(inputHandles);

			System.out.println(numInputs + " input devices found");
			for (int i = 0; i < numInputs; i++) {
				System.out.println("  " + i + ": " + inputHandles[i] + " (" + input.getInputTypeForHandle(inputHandles[i])
						+ ", gamepad index " + input.getGamepadIndexForController(inputHandles[i]) + ")");
			}
		} else if (command.startsWith("i set ")) {
			String setName = command.substring("i set ".length());
			setHandle = input.getActionSetHandle(setName);
			System.out.println("  handle for set '" + setName + "': " + getNativeHandle(setHandle));
		} else if (command.startsWith("i layer ")) {
			SteamInputActionSetHandle layer = input.getActionSetHandle(command.substring("i layer ".length()));
			for (int i = 0; i < numInputs; i++) {
				input.activateActionSetLayer(inputHandles[i], layer);
			}
		} else if (command.equals("i nolayers")) {
			for (int i = 0; i < numInputs; i++) {
				input.deactivateAllActionSetLayers(inputHandles[i]);
			}
		} else if (command.startsWith("i d action ")) {
			String actionName = command.substring("i d action ".length());
			digitalActionHandle = input.getDigitalActionHandle(actionName);
			System.out.println("  handle for digital '" + actionName + "': " + getNativeHandle(digitalActionHandle));
		} else if (command.startsWith("i a action ")) {
			String actionName = command.substring("i a action ".length());
			analogActionHandle = input.getAnalogActionHandle(actionName);
			System.out.println("  handle for analog '" + actionName + "': " + getNativeHandle(analogActionHandle));
		} else if (command.equals("i origins") && numInputs > 0 && setHandle != null && digitalActionHandle != null) {
			int count = input.getDigitalActionOrigins(inputHandles[0], setHandle, digitalActionHandle, origins);
			for (int i = 0; i < count; i++) {
				System.out.println("  " + origins[i] + ": " + input.getStringForActionOrigin(origins[i])
						+ ", png=" + input.getGlyphPNGForActionOrigin(origins[i], SteamInput.GlyphSize.Medium,
						SteamInput.GlyphStyle.flags(SteamInput.GlyphStyle.Light))
						+ ", svg=" + input.getGlyphSVGForActionOrigin(origins[i], 0));
			}
		} else if (command.startsWith("i rumble ") && numInputs > 0) {
			String[] params = command.substring("i rumble ".length()).split(" ");
			input.triggerVibration(inputHandles[0], (short) Integer.parseInt(params[0]), (short) Integer.parseInt(params[1]));
		} else if (command.startsWith("i led ") && numInputs > 0) {
			String[] params = command.substring("i led ".length()).split(" ");
			input.setLEDColor(inputHandles[0], Integer.parseInt(params[0]), Integer.parseInt(params[1]),
					Integer.parseInt(params[2]), SteamInput.LEDFlag.SetColor);
		} else if (command.equals("i motion") && numInputs > 0) {
			input.getMotionData(inputHandles[0], motionData);
			System.out.println("  rotQuat=" + motionData.getRotQuatX() + ", " + motionData.getRotQuatY() + ", "
					+ motionData.getRotQuatZ() + ", " + motionData.getRotQuatW()
					+ " posAccel=" + motionData.getPosAccelX() + ", " + motionData.getPosAccelY() + ", "
					+ motionData.getPosAccelZ()
					+ " rotVel=" + motionData.getRotVelX() + ", " + motionData.getRotVelY() + ", "
					+ motionData.getRotVelZ());
		} else if (command.equals("i events on") || command.equals("i events off")) {
			input.enableActionEventCallbacks(command.endsWith("on"));
		} else if (command.startsWith("i trigger ") && numInputs > 0) {
			String mode = command.substring("i trigger ".length());
			SteamInputDualSenseTriggerEffect.Trigger r2 = SteamInputDualSenseTriggerEffect.Trigger.R2;
			if (mode.equals("weapon")) {
				triggerEffect.reset().setWeapon(r2, 3, 6, 8);
			} else if (mode.equals("vibration")) {
				triggerEffect.reset().setVibration(r2, 2, 8, 40);
			} else if (mode.equals("slope")) {
				triggerEffect.reset().setSlopeFeedback(r2, 0, 9, 1, 8);
			} else {
				triggerEffect.reset().setOff(r2);
			}
			input.setDualSenseTriggerEffect(inputHandles[0], triggerEffect);
		} else if (command.equals("i panel") && numInputs > 0) {
			System.out.println("  binding panel: " + input.showBindingPanel(inputHandles[0]));
		}
	}

	public static void main(String[] arguments) {
		new SteamInputTest().clientMain(arguments);
	}

}
