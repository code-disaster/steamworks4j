package com.codedisaster.steamworks.test;

import com.codedisaster.steamworks.*;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Expects a binary file <i>encryptedappticket.key</i> in the working directory. The file should contain
 * exactly {@link SteamEncryptedAppTicket#SymmetricKeyLen} bytes, which match the private key for the
 * AppID specified in <i>steam_appid.txt</i>.
 */
public class SteamEncryptedAppTicketTest extends SteamTestApp {

	private SteamUser user;
	private SteamEncryptedAppTicket encryptedAppTicket;

	private ByteBuffer ticketEncrypt = ByteBuffer.allocateDirect(1024);
	private ByteBuffer ticketDecrypt = ByteBuffer.allocateDirect(1024);

	private static final byte[] privateKey = new byte[SteamEncryptedAppTicket.SymmetricKeyLen];

	private SteamUserCallback userCallback = new SteamUserCallback() {
		@Override
		public void onAuthSessionTicket(SteamAuthTicket authTicket, SteamResult result) {

		}

		@Override
		public void onValidateAuthTicket(SteamID steamID,
										 SteamAuth.AuthSessionResponse authSessionResponse,
										 SteamID ownerSteamID) {

		}

		@Override
		public void onMicroTxnAuthorization(int appID, long orderID, boolean authorized) {

		}

		@Override
		public void onEncryptedAppTicket(SteamResult result) {
			System.out.println("app ticket encrypted: " + result.name());

			try {
				int[] ticketSize = new int[1];
				ticketEncrypt.clear();
				ticketDecrypt.clear();

				if (user.getEncryptedAppTicket(ticketEncrypt, ticketSize)) {
					System.out.println("encrypted app ticket size: " + ticketSize[0]);
					ticketEncrypt.limit(ticketSize[0]);

					if (encryptedAppTicket.decryptTicket(ticketEncrypt, ticketDecrypt, privateKey, ticketSize)) {
						System.out.println("decrypted app ticket size: " + ticketSize[0]);
						ticketDecrypt.limit(ticketSize[0]);

						ByteBuffer dataIncluded = ByteBuffer.allocateDirect(100);
						int dataLength = encryptedAppTicket.getUserVariableData(ticketDecrypt, dataIncluded);
						if (dataLength > 0) {
							dataIncluded.limit(dataLength);
							byte[] userData = new byte[dataLength];
							dataIncluded.get(userData);
							String userString = new String(userData, Charset.defaultCharset());
							System.out.println("included user data: " + dataLength + " bytes, '" + userString + "'");
						}
					} else {
						System.err.println("failed to decrypt app ticket");
					}
				} else {
					System.err.println("failed to get encrypted app ticket");
				}
			} catch (SteamException e) {
				e.printStackTrace();
			}
		}
	};

	@Override
	protected void registerInterfaces() throws SteamException {
		if (!SteamEncryptedAppTicket.loadLibraries(libraryLoader)) {
			throw new SteamException("Failed to load native libraries");
		}
		user = new SteamUser(userCallback);
		encryptedAppTicket = new SteamEncryptedAppTicket();
	}

	@Override
	protected void unregisterInterfaces() throws SteamException {
		user.dispose();
		encryptedAppTicket.dispose();
	}

	@Override
	protected void processUpdate() throws SteamException {

	}

	@Override
	protected void processInput(String input) throws SteamException {
		if (input.equals("encrypt")) {
			byte[] ident = "Hello World!".getBytes(Charset.defaultCharset());
			ByteBuffer dataToInclude = ByteBuffer.allocateDirect(ident.length);
			dataToInclude.put(ident);
			dataToInclude.flip();
			user.requestEncryptedAppTicket(dataToInclude);
		}
	}

	public static void main(String[] arguments) {

		try {
			byte[] key = Files.readAllBytes(Paths.get("encryptedappticket.key"));
			System.arraycopy(key, 0, privateKey, 0, Math.min(key.length, privateKey.length));
		} catch (IOException e) {
			System.err.println("failed to read encrypted app ticket key");
		}

		new SteamEncryptedAppTicketTest().clientMain(arguments);
	}

}
