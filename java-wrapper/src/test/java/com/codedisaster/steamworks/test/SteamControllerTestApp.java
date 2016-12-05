package com.codedisaster.steamworks.test;

import com.codedisaster.steamworks.*;

public class SteamControllerTestApp extends SteamTestApp {

	private SteamController controller;
	private SteamControllerHandle[] controllerHandles = {};
	private int numControllers = 0;

	private SteamControllerMotionData motionData = new SteamControllerMotionData();
	private long motionDataLastTime = System.currentTimeMillis();

	@Override
	protected void registerInterfaces() {
		System.out.println("Register controller API ...");
		controller = new SteamController();
		controller.init();
	}

	@Override
	protected void unregisterInterfaces() {
		controller.shutdown();
	}

	@Override
	protected void processUpdate() throws SteamException {
		for (SteamControllerHandle handle : controllerHandles) {
			long time = System.currentTimeMillis();
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
			}
		}
	}

	@Override
	protected void processInput(String input) throws SteamException {
		if (input.equals("controller list")) {
			controllerHandles = new SteamControllerHandle[SteamController.STEAM_CONTROLLER_MAX_COUNT];
			numControllers = controller.getConnectedControllers(controllerHandles);

			System.out.println(numControllers + " controllers found");
			for (int i = 0; i < numControllers; i++) {
				System.out.println("  " + i + ": " + controllerHandles[i]);
			}
		} else if (input.startsWith("controller pulse ")) {
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
		}
	}

	public static void main(String[] arguments) {
		new SteamControllerTestApp().clientMain(arguments);
	}

}
