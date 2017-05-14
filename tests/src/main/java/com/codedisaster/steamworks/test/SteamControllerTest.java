package com.codedisaster.steamworks.test;

import com.codedisaster.steamworks.*;

import static com.codedisaster.steamworks.SteamNativeHandle.getNativeHandle;

public class SteamControllerTest extends SteamTestApp {

	private SteamController controller;
	private SteamControllerHandle[] controllerHandles = {};
	private int numControllers = 0;

	private SteamControllerMotionData motionData = new SteamControllerMotionData();
	private long motionDataLastTime = System.currentTimeMillis();

	private SteamControllerActionSetHandle setHandle;

	private SteamControllerDigitalActionHandle digitalActionHandle;
	private SteamControllerDigitalActionData digitalActionData = new SteamControllerDigitalActionData();

	private SteamControllerAnalogActionHandle analogActionHandle;
	private SteamControllerAnalogActionData analogActionData = new SteamControllerAnalogActionData();

	@Override
	protected void registerInterfaces() {
		System.out.println("Register controller API ...");
		controller = new SteamController();
		controller.init();

		try {
			processInput("c list");
		} catch (SteamException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void unregisterInterfaces() {
		controller.shutdown();
	}

	@Override
	protected void processUpdate() throws SteamException {

		if (setHandle == null || getNativeHandle(setHandle) == 0) {
			return;
		}

		for (int i = 0; i < numControllers; i++) {
			SteamControllerHandle handle = controllerHandles[i];
			controller.activateActionSet(handle, setHandle);

			/*long time = System.currentTimeMillis();
			if (time - motionDataLastTime > 1000) {
				controller.getMotionData(handle, motionData);

				System.out.println("  " + handle
								+ ": rotQuatX=" + motionData.getRotQuatX()
								+ ", rotQuatY=" + motionData.getRotQuatY()
								+ ", rotQuatZ=" + motionData.getRotQuatZ()
								+ ", rotQuatW=" + motionData.getRotQuatW()
								+ ": posAccelX=" + motionData.getPosAccelX()
								+ ", posAccelY=" + motionData.getPosAccelY()
								+ ", posAccelZ=" + motionData.getPosAccelZ()
								+ ": rotVelX=" + motionData.getRotVelX()
								+ ", rotVelY=" + motionData.getRotVelY()
								+ ", rotVelZ=" + motionData.getRotVelZ());

				motionDataLastTime = time;
			}*/

			if (digitalActionHandle != null) {
				controller.getDigitalActionData(handle, digitalActionHandle, digitalActionData);
				if (digitalActionData.getActive() && digitalActionData.getState()) {
					System.out.println("  digital action: " + getNativeHandle(digitalActionHandle));
				}
			}

			if (analogActionHandle != null) {
				controller.getAnalogActionData(handle, analogActionHandle, analogActionData);
				if (analogActionData.getActive()) {
					float x = analogActionData.getX();
					float y = analogActionData.getY();
					SteamController.SourceMode mode = analogActionData.getMode();
					if (Math.abs(x) > 0.0001f && Math.abs(y) > 0.001f) {
						System.out.println("  analog action: " + analogActionData.getX() +
								", " + analogActionData.getY() + ", " + mode.name());
					}
				}
			}
		}
	}

	@Override
	protected void processInput(String input) throws SteamException {
		if (input.equals("c list")) {
			controllerHandles = new SteamControllerHandle[SteamController.STEAM_CONTROLLER_MAX_COUNT];
			numControllers = controller.getConnectedControllers(controllerHandles);

			System.out.println(numControllers + " controllers found");
			for (int i = 0; i < numControllers; i++) {
				System.out.println("  " + i + ": " + controllerHandles[i]);
			}
		} else if (input.startsWith("c pulse ")) {
			String[] params = input.substring("controller pulse ".length()).split(" ");
			if (params.length > 1) {
				SteamController.Pad pad = "left".equals(params[0]) ? SteamController.Pad.Left : SteamController.Pad.Right;
				if (params.length == 2) {
					controller.triggerHapticPulse(controllerHandles[0], pad, Short.parseShort(params[1]));
				} else if (params.length == 4) {
					controller.triggerRepeatedHapticPulse(controllerHandles[0], pad, Short.parseShort(params[1]),
							Short.parseShort(params[2]), Short.parseShort(params[3]), 0);
				}
			}
		} else if (input.startsWith("c set ")) {
			String setName = input.substring("c set ".length());
			setHandle = controller.getActionSetHandle(setName);
			System.out.println("  handle for set '" + setName + "': " + getNativeHandle(setHandle));
		} else if (input.startsWith("c d action ")) {
			String actionName = input.substring("c d action ".length());
			digitalActionHandle = controller.getDigitalActionHandle(actionName);
			System.out.println("  handle for digital '" + actionName + "': " + getNativeHandle(digitalActionHandle));
		} else if (input.startsWith("c a action ")) {
			String actionName = input.substring("c a action ".length());
			analogActionHandle = controller.getAnalogActionHandle(actionName);
			System.out.println("  handle for analog '" + actionName + "': " + getNativeHandle(analogActionHandle));
		}
	}

	public static void main(String[] arguments) {
		new SteamControllerTest().clientMain(arguments);
	}

}
