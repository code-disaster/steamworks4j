package com.codedisaster.steamworks;

import java.nio.ByteBuffer;

public class SteamUser extends SteamInterface {

	public SteamUser(SteamUserCallback callback) {
		super(SteamAPI.getSteamUserPointer(),
				createCallback(new SteamUserCallbackAdapter(callback)));
	}

	public SteamID getSteamID() {
		return new SteamID(getSteamID(pointer));
	}

	public SteamAuthTicket getAuthSessionTicket(ByteBuffer authTicket, int[] sizeInBytes) throws SteamException {

		if (!authTicket.isDirect()) {
			throw new SteamException("Direct buffer required!");
		}

		int ticket = getAuthSessionTicket(pointer, authTicket,
				authTicket.position(), authTicket.remaining(), sizeInBytes);

		if (ticket != SteamAuthTicket.AuthTicketInvalid) {
			authTicket.limit(sizeInBytes[0]);
		}

		return new SteamAuthTicket(ticket);
	}

	public SteamAuth.BeginAuthSessionResult beginAuthSession(ByteBuffer authTicket, SteamID steamID) throws SteamException {

		if (!authTicket.isDirect()) {
			throw new SteamException("Direct buffer required!");
		}

		int result = beginAuthSession(pointer, authTicket,
				authTicket.position(), authTicket.remaining(), steamID.handle);

		return SteamAuth.BeginAuthSessionResult.byOrdinal(result);
	}

	public void endAuthSession(SteamID steamID) {
		endAuthSession(pointer, steamID.handle);
	}

	public void cancelAuthTicket(SteamAuthTicket authTicket) {
		cancelAuthTicket(pointer, (int) authTicket.handle);
	}

	public SteamAuth.UserHasLicenseForAppResult userHasLicenseForApp(SteamID steamID, int appID) {
		return SteamAuth.UserHasLicenseForAppResult.byOrdinal(userHasLicenseForApp(pointer, steamID.handle, appID));
	}

	public SteamAPICall requestEncryptedAppTicket(ByteBuffer dataToInclude) throws SteamException {

		if (!dataToInclude.isDirect()) {
			throw new SteamException("Direct buffer required!");
		}

		return new SteamAPICall(requestEncryptedAppTicket(pointer, callback, dataToInclude,
				dataToInclude.position(), dataToInclude.remaining()));
	}

	public boolean getEncryptedAppTicket(ByteBuffer ticket, int[] sizeInBytes) throws SteamException {

		if (!ticket.isDirect()) {
			throw new SteamException("Direct buffer required!");
		}

		return getEncryptedAppTicket(pointer, ticket, ticket.position(), ticket.remaining(), sizeInBytes);
	}

	// @off

	/*JNI
		#include "SteamUserCallback.h"
	*/

	private static native long createCallback(SteamUserCallbackAdapter javaCallback); /*
		return (intp) new SteamUserCallback(env, javaCallback);
	*/

	private static native long getSteamID(long pointer); /*
		ISteamUser* user = (ISteamUser*) pointer;
		CSteamID steamID = user->GetSteamID();
		return (int64) steamID.ConvertToUint64();
	*/

	private static native int getAuthSessionTicket(long pointer, ByteBuffer authTicket,
												   int bufferOffset, int bufferCapacity, int[] sizeInBytes); /*
		ISteamUser* user = (ISteamUser*) pointer;
		int ticket = user->GetAuthSessionTicket(&authTicket[bufferOffset], bufferCapacity, (uint32*) sizeInBytes);
		return ticket;
	*/

	private static native int beginAuthSession(long pointer, ByteBuffer authTicket,
											   int bufferOffset, int bufferSize, long steamID); /*
		ISteamUser* user = (ISteamUser*) pointer;
		return user->BeginAuthSession(&authTicket[bufferOffset], bufferSize, (uint64) steamID);
	*/

	private static native void endAuthSession(long pointer, long steamID); /*
		ISteamUser* user = (ISteamUser*) pointer;
		user->EndAuthSession((uint64) steamID);
	*/

	private static native void cancelAuthTicket(long pointer, int authTicket); /*
		ISteamUser* user = (ISteamUser*) pointer;
		user->CancelAuthTicket(authTicket);
	*/

	private static native int userHasLicenseForApp(long pointer, long steamID, int appID); /*
		ISteamUser* user = (ISteamUser*) pointer;
		return user->UserHasLicenseForApp((uint64) steamID, (AppId_t) appID);
	*/

	private static native long requestEncryptedAppTicket(long pointer, long callback,
														 ByteBuffer dataToInclude, int bufferOffset, int bufferSize); /*
		ISteamUser* user = (ISteamUser*) pointer;
		SteamUserCallback* cb = (SteamUserCallback*) callback;
		SteamAPICall_t handle = user->RequestEncryptedAppTicket(&dataToInclude[bufferOffset], bufferSize);
		cb->onRequestEncryptedAppTicketCall.Set(handle, cb, &SteamUserCallback::onRequestEncryptedAppTicket);
		return handle;
	*/

	private static native boolean getEncryptedAppTicket(long pointer, ByteBuffer ticket,
												   		int bufferOffset, int bufferCapacity, int[] sizeInBytes); /*
		ISteamUser* user = (ISteamUser*) pointer;
		return user->GetEncryptedAppTicket(&ticket[bufferOffset], bufferCapacity, (uint32*) sizeInBytes);
	*/

}
