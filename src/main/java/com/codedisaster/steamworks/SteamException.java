package com.codedisaster.steamworks;

public class SteamException extends Exception {

	public SteamException() {
		super();
	}

	public SteamException(String message) {
		super(message);
	}

	public SteamException(String message, Throwable throwable) {
		super(message, throwable);
	}

	public SteamException(Throwable throwable) {
		super(throwable);
	}

}
