package com.codedisaster.steamworks.test;

import com.codedisaster.steamworks.*;

import java.util.Scanner;

public abstract class SteamTestApp {

	protected SteamLibraryLoader libraryLoader;
	protected SteamUtils clientUtils;

	protected static final int MS_PER_TICK = 1000 / 15;

	private final SteamAPIWarningMessageHook clMessageHook = new SteamAPIWarningMessageHook() {
		@Override
		public void onWarningMessage(int severity, String message) {
			System.err.println("[client debug message] (" + severity + ") " + message);
		}
	};

	private final SteamUtilsCallback clUtilsCallback = new SteamUtilsCallback() {
		@Override
		public void onSteamShutdown() {
			System.err.println("Steam client requested to shut down!");
		}
	};

	private class InputHandler implements Runnable {

		private volatile boolean alive;
		private final Thread mainThread;
		private final Scanner scanner;

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

	private boolean runAsClient() throws SteamException {

		if (!SteamAPI.loadLibraries(libraryLoader)) {
			System.err.println("Failed to load native libraries");
		}

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

		libraryLoader = createLibraryLoader(arguments);

		try {

			if (!runAsClient()) {
				System.exit(-1);
			}

			System.out.println("Bye!");

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	private boolean runAsGameServer(boolean dedicated) throws SteamException {

		if (!SteamGameServerAPI.loadLibraries(libraryLoader)) {
			System.err.println("Failed to load native libraries");
			return false;
		}

		if (!dedicated) {

			System.out.println("Initialise Steam client API ...");

			if (!SteamAPI.init()) {
				SteamAPI.printDebugInfo(System.err);
				return false;
			}
		}

		System.out.println("Initialise Steam GameServer API ...");

		if (!SteamGameServerAPI.init((127 << 24) + 1, (short) 27016, (short) 27017,
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

		boolean dedicated = false;

		for (String arg : arguments) {
			if (arg.equals("--dedicated")) {
				dedicated = true;
				break;
			}
		}

		libraryLoader = createLibraryLoader(arguments);

		// development mode, read Steamworks libraries from ./sdk folder
		libraryLoader.setLibraryPath(getRedistributableFolder());

		try {

			if (!runAsGameServer(dedicated)) {
				System.exit(-1);
			}

			System.out.println("Bye!");

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	private static SteamLibraryLoader createLibraryLoader(String[] arguments) {

		SteamLibraryLoader loader = null;

		for (String arg : arguments) {
			if (arg.equals("--gdx")) {
				loader = new SteamLibraryLoaderGdx();
				break;
			}
		}

		if (loader == null) {
			loader = new SteamLibraryLoaderLwjgl3();
		}

		// development mode, read Steamworks libraries from ./sdk folder
		loader.setLibraryPath(getRedistributableFolder());

		return loader;
	}

	private static String getRedistributableFolder() {

		String osName = System.getProperty("os.name");
		String osArch  = System.getProperty("os.arch");

		if (osName.startsWith("Windows")) {
			if (osArch.contains("64")) {
				return "sdk/redistributable_bin/win64";
			} else {
				return "sdk/redistributable_bin";
			}
		} else if (osName.startsWith("Linux") || osName.startsWith("FreeBSD") || osName.startsWith("Unix")) {
			return "sdk/redistributable_bin/linux64";
		} else if (osName.startsWith("Mac OS X") || osName.startsWith("Darwin")) {
			return "sdk/redistributable_bin/osx";
		} else {
			throw new LinkageError("Unknown platform: " + osName);
		}
	}

}
