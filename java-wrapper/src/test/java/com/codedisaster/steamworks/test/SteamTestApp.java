package com.codedisaster.steamworks.test;

import com.codedisaster.steamworks.*;

import java.util.Scanner;

public abstract class SteamTestApp {

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
			while (alive && mainThread.isAlive()) {
				if (scanner.hasNext()) {
					String input = scanner.next();
					if (input.equals("quit") || input.equals("exit")) {
						alive = false;
					} else {
						processInput(input);
					}
				}
			}
		}

		public boolean alive() {
			return alive;
		}
	}

	protected abstract void registerInterfaces();

	protected abstract void processInput(String input);

	private boolean runAsClient(@SuppressWarnings("unused") String[] arguments) throws SteamException {

		System.out.println("Initialise Steam client API ...");

		if (!SteamAPI.init()) {
			return false;
		}

		registerInterfaces();

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

		System.out.println("Shutting down Steam client API ...");
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
