package com.codedisaster.steamworks;

public class SteamAuthTicket extends SteamNativeHandle {

	static final long AuthTicketInvalid = 0;

	SteamAuthTicket(long handle) {
		super(handle);
	}

	public boolean isValid() {
		return handle != AuthTicketInvalid;
	}

}
