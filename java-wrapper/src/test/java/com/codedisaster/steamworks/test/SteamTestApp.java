package com.codedisaster.steamworks.test;

import com.codedisaster.steamworks.*;

import java.util.Scanner;

public abstract class SteamTestApp {

	protected SteamUtils clientUtils;

	protected static final int MS_PER_TICK = 1000 / 15;

	private SteamAPIWarningMessageHook clMessageHook = new SteamAPIWarningMessageHook() {
		@Override
		public void onWarningMessage(int severity, String message) {
			System.err.println("[client debug message] (" + severity + ") " + message);
		}
	};

	private SteamUtilsCallback clUtilsCallback = new SteamUtilsCallback() {
		@Override
		public void onSteamShutdown() {
			System.err.println("Steam client requested to shut down!");
		}
	};

	private class InputHandler implements Runnable {

		private volatile boolean alive;
		private Thread mainThread;
		private Scanner scanner;

		public InputHandler(Thread mainThread) {
			this.alive = true;
			this.mainThread = mainThread;

			this.scanner = new Scanner(System.in);
			scanner.useDelimiter("[\r\n\t]");
		}

		@Override
		public void run() {
			try {
				while (alive && mainThread.isAlive()) {
					if (scanner.hasNext()) {
						String input = scanner.next();
						if (input.equals("quit") || input.equals("exit")) {
							alive = false;
						} else {
							try {
								processInput(input);
							} catch (NumberFormatException e) {
								e.printStackTrace();
							}
						}
					}
				}
			} catch (SteamException e) {
				e.printStackTrace();
			}
		}

		public boolean alive() {
			return alive;
		}
	}

	protected abstract void registerInterfaces();

	protected abstract void unregisterInterfaces();

	protected abstract void processUpdate() throws SteamException;

	protected abstract void processInput(String input) throws SteamException;

	private boolean runAsClient(@SuppressWarnings("unused") String[] arguments) throws SteamException {

		System.out.println("Initialise Steam client API ...");

		if (!SteamAPI.init("../java-wrapper/src/main/resources")) {
			return false;
		}

		SteamAPI.printDebugInfo(System.out);

		registerInterfaces();

		clientUtils = new SteamUtils(clUtilsCallback);
		clientUtils.setWarningMessageHook(clMessageHook);

		InputHandler inputHandler = new InputHandler(Thread.currentThread());

		Thread inputThread = new Thread(inputHandler);
		inputThread.start();

		while (inputHandler.alive() && SteamAPI.isSteamRunning()) {

			SteamAPI.runCallbacks();

			processUpdate();

			try {
				// sleep a little (Steam says it should poll at least 15 times/second)
				Thread.sleep(MS_PER_TICK);
			} catch (InterruptedException e) {
				// ignore
			}
		}

		System.out.println("Shutting down Steam client API ...");

		try {
			inputThread.join();
		} catch (InterruptedException e) {
			throw new SteamException(e);
		}

		clientUtils.dispose();

		unregisterInterfaces();

		SteamAPI.shutdown();

		return true;
	}

	protected void clientMain(String[] arguments) {
		try {

			if (!runAsClient(arguments)) {
				System.exit(-1);
			}

			System.out.println("Bye!");

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

}
