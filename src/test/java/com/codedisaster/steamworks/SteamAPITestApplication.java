package com.codedisaster.steamworks;

import java.util.Scanner;

public class SteamAPITestApplication {

	private SteamUserStats userStats;

	private SteamUserStatsCallback userStatsCallback = new SteamUserStatsCallback() {
		@Override
		public void onUserStatsReceived(long gameId, long userId, int result) {
			System.out.println("User stats received: gameId=" + gameId + ", userId=" + userId +
					", result=" + SteamResult.byValue(result).toString());

			int numAchievements = userStats.getNumAchievements();
			System.out.println("Num of achievements: " + numAchievements);

			for (int i = 0; i < numAchievements; i++) {
				String name = userStats.getAchievementName(i);
				boolean achieved = userStats.isAchieved(name, false);
				System.out.println("# " + i + " : name=" + name + ", achieved=" + (achieved ? "yes" : "no"));
			}
		}

		@Override
		public void onUserStatsStored(long gameId, int result) {
			System.out.println("User stats stored: gameId=" + gameId +
					", result=" + SteamResult.byValue(result).toString());
		}
	};

	class InputHandler implements Runnable {

		private volatile boolean alive;
		private Thread mainThread;
		private Scanner scanner;

		public InputHandler(Thread mainThread) {
			this.alive = true;
			this.mainThread = mainThread;
			this.scanner = new Scanner(System.in);
		}

		@Override
		public void run() {
			while (alive && mainThread.isAlive()) {

				if (scanner.hasNext()) {
					String input = scanner.next();

					if (input.startsWith("q")) {
						alive = false;
					}

					if (input.equals("1")) {
						userStats.requestCurrentStats();
					} else if (input.equals("2")) {
						userStats.storeStats();
					}
				}

			}
		}

		public boolean alive() {
			return alive;
		}
	}

	private boolean run(@SuppressWarnings("unused") String[] arguments) throws SteamException {

		System.out.println("Initialise Steam API ...");
		if (!SteamAPI.init()) {
			return false;
		}

		System.out.println("Register user stats callback ...");
		userStats = new SteamUserStats(SteamAPI.getSteamUserStatsPointer(), userStatsCallback);

		System.out.println("Requesting user stats ...");
		userStats.requestCurrentStats();

		InputHandler inputHandler = new InputHandler(Thread.currentThread());
		new Thread(inputHandler).start();

		while (inputHandler.alive() && SteamAPI.isSteamRunning()) {

			// process callbacks
			SteamAPI.runCallbacks();

			try {
				// sleep a little (Steam says it should poll at least 15 times/second)
				Thread.sleep(1000 / 15);
			} catch (InterruptedException e) {
				// ignore
			}
		}

		System.out.println("Shutting down Steam API ...");
		SteamAPI.shutdown();

		System.out.println("Bye!");
		return true;
	}

	public static void main(String[] arguments) {

		try {

			System.load(arguments[0] + "/libsteam_api.dylib");
			System.load(arguments[0] + "/libsteamworks4j.dylib");

			if (!new SteamAPITestApplication().run(arguments)) {
				System.exit(-1);
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

}
