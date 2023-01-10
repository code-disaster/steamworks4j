package com.codedisaster.steamworks;

import java.nio.ByteBuffer;

public class SteamEncryptedAppTicket extends SteamInterface {

	public static final int SymmetricKeyLen = 32;

	public static boolean loadLibraries(SteamLibraryLoader loader) {
		boolean success = loader.loadLibrary("sdkencryptedappticket");
		success = success && loader.loadLibrary("steamworks4j-encryptedappticket");
		return success;
	}

	public SteamEncryptedAppTicket() {
		super(~0L);
	}

	public boolean decryptTicket(ByteBuffer ticketEncrypted, ByteBuffer ticketDecrypted,
								 byte[] key, int[] ticketDecryptedOutputSize) throws SteamException {

		checkBuffer(ticketEncrypted);
		checkBuffer(ticketDecrypted);
		checkArray(key, SymmetricKeyLen);

		return SteamEncryptedAppTicketNative.decryptTicket(
				ticketEncrypted, ticketEncrypted.position(), ticketEncrypted.remaining(),
				ticketDecrypted, ticketDecrypted.position(), ticketDecrypted.remaining(),
				key, SymmetricKeyLen, ticketDecryptedOutputSize);
	}

	public boolean isTicketForApp(ByteBuffer ticketDecrypted, int appID) throws SteamException {

		checkBuffer(ticketDecrypted);

		return SteamEncryptedAppTicketNative.isTicketForApp(
				ticketDecrypted, ticketDecrypted.position(), ticketDecrypted.remaining(), appID);
	}

	public int getTicketIssueTime(ByteBuffer ticketDecrypted) throws SteamException {

		checkBuffer(ticketDecrypted);

		return SteamEncryptedAppTicketNative.getTicketIssueTime(
				ticketDecrypted, ticketDecrypted.position(), ticketDecrypted.remaining());
	}

	public SteamID getTicketSteamID(ByteBuffer ticketDecrypted) throws SteamException {

		checkBuffer(ticketDecrypted);

		return new SteamID(SteamEncryptedAppTicketNative.getTicketSteamID(
				ticketDecrypted, ticketDecrypted.position(), ticketDecrypted.remaining()));
	}

	public int getTicketAppID(ByteBuffer ticketDecrypted) throws SteamException {

		checkBuffer(ticketDecrypted);

		return SteamEncryptedAppTicketNative.getTicketAppID(
				ticketDecrypted, ticketDecrypted.position(), ticketDecrypted.remaining());
	}

	public boolean userOwnsAppInTicket(ByteBuffer ticketDecrypted, int appID) throws SteamException {

		checkBuffer(ticketDecrypted);

		return SteamEncryptedAppTicketNative.userOwnsAppInTicket(
				ticketDecrypted, ticketDecrypted.position(), ticketDecrypted.remaining(), appID);
	}

	public boolean userIsVacBanned(ByteBuffer ticketDecrypted) throws SteamException {

		checkBuffer(ticketDecrypted);

		return SteamEncryptedAppTicketNative.userIsVacBanned(
				ticketDecrypted, ticketDecrypted.position(), ticketDecrypted.remaining());
	}

	public int getUserVariableData(ByteBuffer ticketDecrypted, ByteBuffer userData) throws SteamException {

		checkBuffer(ticketDecrypted);
		checkBuffer(userData);

		return SteamEncryptedAppTicketNative.getUserVariableData(
				ticketDecrypted, ticketDecrypted.position(), ticketDecrypted.remaining(),
				userData, userData.position(), userData.remaining());
	}

}
