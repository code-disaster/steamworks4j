package com.codedisaster.steamworks;

import java.nio.ByteBuffer;

class SteamEncryptedAppTicketNative {

	// @off

	/*JNI
		#include <steamencryptedappticket.h>
	*/

	static native boolean decryptTicket(ByteBuffer ticketEncrypted, int encryptedOffset, int encryptedSize,
										ByteBuffer ticketDecrypted, int decryptedOffset, int decryptedSize,
										byte[] key, int keyLength, int[] decryptedLength); /*

		decryptedLength[0] = decryptedSize;

		return SteamEncryptedAppTicket_BDecryptTicket((uint8*) &ticketEncrypted[encryptedOffset], encryptedSize,
			(uint8*) &ticketDecrypted[decryptedOffset], (uint32*) &decryptedLength[0], (uint8*) key, keyLength);
	*/

	static native boolean isTicketForApp(ByteBuffer ticketDecrypted,
										 int bufferOffset, int bufferSize, int appID); /*

		return SteamEncryptedAppTicket_BIsTicketForApp(
			(uint8*) &ticketDecrypted[bufferOffset], bufferSize, (AppId_t) appID);
	*/

	static native int getTicketIssueTime(ByteBuffer ticketDecrypted,
										 int bufferOffset, int bufferSize); /*

		return SteamEncryptedAppTicket_GetTicketIssueTime(
			(uint8*) &ticketDecrypted[bufferOffset], bufferSize);
	*/

	static native long getTicketSteamID(ByteBuffer ticketDecrypted,
										int bufferOffset, int bufferSize); /*

		CSteamID steamID;

		SteamEncryptedAppTicket_GetTicketSteamID(
			(uint8*) &ticketDecrypted[bufferOffset], bufferSize, &steamID);

		return (int64) steamID.ConvertToUint64();
	*/

	static native int getTicketAppID(ByteBuffer ticketDecrypted,
									 int bufferOffset, int bufferSize); /*

		return SteamEncryptedAppTicket_GetTicketAppID(
			(uint8*) &ticketDecrypted[bufferOffset], bufferSize);
	*/

	static native boolean userOwnsAppInTicket(ByteBuffer ticketDecrypted,
											  int bufferOffset, int bufferSize, int appID); /*
		return SteamEncryptedAppTicket_BUserOwnsAppInTicket(
			(uint8*) &ticketDecrypted[bufferOffset], bufferSize, (AppId_t) appID);
	*/

	static native boolean userIsVacBanned(ByteBuffer ticketDecrypted,
										  int bufferOffset, int bufferSize); /*

		return SteamEncryptedAppTicket_BUserIsVacBanned(
			(uint8*) &ticketDecrypted[bufferOffset], bufferSize);
	*/

	static native int getUserVariableData(ByteBuffer ticketDecrypted, int decryptedOffset, int decryptedSize,
										  ByteBuffer userData, int userDataOffset, int userDataSize); /*

		uint32 size = 0;

		const uint8* data = SteamEncryptedAppTicket_GetUserVariableData(
			(uint8*) &ticketDecrypted[decryptedOffset], decryptedSize, &size);

		if (size > 0) {
			uint32 capacity = (uint32) userDataSize;
			memcpy(&userData[userDataOffset], data, size < capacity ? size : capacity);
		}

		return size;
	*/

}
