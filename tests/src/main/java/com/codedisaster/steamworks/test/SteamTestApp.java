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

			this.scanner = new Scanner(System.in, "UTF-8");
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

	protected abstract void registerInterfaces() throws SteamException;

	protected abstract void unregisterInterfaces() throws SteamException;

	protected abstract void processUpdate() throws SteamException;

	protected abstract void processInput(String input) throws SteamException;

	private boolean runAsClient(@SuppressWarnings("unused") String[] arguments) throws SteamException {

		System.out.println("Load native libraries ...");

		SteamAPI.loadLibraries();

		System.out.println("Initialise Steam client API ...");

		if (!SteamAPI.init()) {
			SteamAPI.printDebugInfo(System.err);
			return false;
		}

		SteamAPI.printDebugInfo(System.out);

		registerInterfaces();

		clientUtils = new SteamUtils(clUtilsCallback);
		clientUtils.setWarningMessageHook(clMessageHook);

		// doesn't make much sense here, as normally you would call this before
		// SteamAPI.init() with your (kn)own app ID
		if (SteamAPI.restartAppIfNecessary(clientUtils.getAppID())) {
			System.out.println("SteamAPI_RestartAppIfNecessary returned 'false'");
		}

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

		// development mode, read Steamworks libraries from ./sdk folder
		System.setProperty("com.codedisaster.steamworks.Debug", "true");

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

	private boolean runAsGameServer(String[] arguments) throws SteamException {

		boolean dedicated = false;

		for (String arg : arguments) {
			if (arg.equals("--dedicated")) {
				dedicated = true;
			}
		}

		System.out.println("Load native libraries ...");

		SteamGameServerAPI.loadLibraries();

		if (!dedicated) {

			System.out.println("Initialise Steam client API ...");

			if (!SteamAPI.init()) {
				SteamAPI.printDebugInfo(System.err);
				return false;
			}
		}

		System.out.println("Initialise Steam GameServer API ...");

		if (!SteamGameServerAPI.init((127 << 24) + 1, (short) 27015, (short) 27016, (short) 27017,
				SteamGameServerAPI.ServerMode.NoAuthentication, "0.0.1")) {
			System.err.println("SteamGameServerAPI.init() failed");
			return false;
		}

		registerInterfaces();

		InputHandler inputHandler = new InputHandler(Thread.currentThread());

		Thread inputThread = new Thread(inputHandler);
		inputThread.start();

		while (inputHandler.alive()) {

			if (!dedicated) {
				SteamAPI.runCallbacks();
			}

			SteamGameServerAPI.runCallbacks();

			processUpdate();

			try {
				// sleep a little (Steam says it should poll at least 15 times/second)
				Thread.sleep(MS_PER_TICK);
			} catch (InterruptedException e) {
				// ignore
			}
		}

		System.out.println("Shutting down Steam GameServer API ...");

		try {
			inputThread.join();
		} catch (InterruptedException e) {
			throw new SteamException(e);
		}

		unregisterInterfaces();

		SteamGameServerAPI.shutdown();

		if (!dedicated) {
			System.out.println("Shutting down Steam client API ...");
			SteamAPI.shutdown();
		}

		return true;
	}

	protected void serverMain(String[] arguments) {

		// development mode, read Steamworks libraries from ./sdk folder
		System.setProperty("com.codedisaster.steamworks.Debug", "true");

		try {

			if (!runAsGameServer(arguments)) {
				System.exit(-1);
			}

			System.out.println("Bye!");

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

}
