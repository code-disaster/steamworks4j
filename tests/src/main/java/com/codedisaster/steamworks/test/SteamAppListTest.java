package com.codedisaster.steamworks.test;

import com.codedisaster.steamworks.SteamAppList;
import com.codedisaster.steamworks.SteamException;

public class SteamAppListTest extends SteamTestApp {

	private SteamAppList steamAppList;

	/**
	 * Volatile because reset happens on inputThread.
	 */
	private volatile boolean showCommands = true;

	private void showCommands() {
		if (showCommands) {
			System.out.println(
					"\n[ + Supported commands + ]\n\n" +
							"  getNumInstalledApps => int\n" +
							"  getInstalledApps => List<Long>\n" +
							"  getAppInstallDir <appId> => String\n" +
							"  quit\n" +
							"  exit\n"
			);
			showCommands = false;
		}
	}

	@Override
	protected void registerInterfaces() throws SteamException {
		steamAppList = new SteamAppList();
	}

	@Override
	protected void unregisterInterfaces() throws SteamException {
		steamAppList.dispose();
	}

	@Override
	protected void processUpdate() throws SteamException {
		showCommands();
	}

	@Override
	protected void processInput(String input) throws SteamException {
		switch (input) {
			case "getNumInstalledApps":
				System.out.println(">> count=" + steamAppList.getNumInstalledApps());
				return;
			case "getInstalledApps":
				int i = 0;
				for (long appId : steamAppList.getInstalledApps(steamAppList.getNumInstalledApps())) {
					System.out.println(">> appId=" + appId + ", index=" + i++);
				}
				return;
			default:
				try {
					final int idx = input.indexOf(' ');
					if (idx > 0 && "getAppInstallDir".equals(input.substring(0, idx))) {
						final long appId = Long.parseLong(input.substring(idx + 1));
						System.out.println(">> appId=" + appId + ", installDir=" + steamAppList.getAppInstallDir(appId));
					}
				} catch (SteamException e) {
					e.printStackTrace();
				} finally {
					showCommands = true;
				}
		}
	}

	public static void main(String[] arguments) {
		new SteamAppListTest().clientMain(arguments);
	}
}
