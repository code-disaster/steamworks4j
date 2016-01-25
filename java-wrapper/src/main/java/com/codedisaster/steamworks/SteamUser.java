package com.codedisaster.steamworks;

import java.nio.Buffer;

public class SteamUser extends SteamInterface {

	public SteamUser(SteamUserCallback callback) {
		super(SteamAPI.getSteamUserPointer(),
				createCallback(new SteamUserCallbackAdapter(callback)));
	}

	public SteamID getSteamID() {
		return new SteamID(getSteamID(pointer));
	}

	public int getAuthSessionTicket(Buffer authTicket) {
		int[] sizeInBytes = new int[1];
		int ticket = getAuthSessionTicket(pointer, authTicket, authTicket.capacity(), sizeInBytes);
		authTicket.limit(sizeInBytes[0]);
		return ticket;
	}

	public SteamAuth.BeginAuthSessionResult beginAuthSession(Buffer authTicket, SteamID steamID) {
		int result = beginAuthSession(pointer, authTicket, authTicket.limit(), steamID.handle);
		return SteamAuth.BeginAuthSessionResult.byOrdinal(result);
	}

	public void endAuthSession(SteamID steamID) {
		endAuthSession(pointer, steamID.handle);
	}

	public void cancelAuthTicket(int authTicket) {
		cancelAuthTicket(pointer, authTicket);
	}

	public SteamAuth.UserHasLicenseForAppResult userHasLicenseForApp(SteamID steamID, long appID) {
		return SteamAuth.UserHasLicenseForAppResult.byOrdinal(userHasLicenseForApp(pointer, steamID.handle, appID));
	}

	// @off

	/*JNI
		#include "SteamUserCallback.h"
	*/

	static private native long createCallback(SteamUserCallbackAdapter javaCallback); /*
		return (intp) new SteamUserCallback(env, javaCallback);
	*/

	static private native long getSteamID(long pointer); /*
		ISteamUser* user = (ISteamUser*) pointer;
		CSteamID steamID = user->GetSteamID();
		return (int64) steamID.ConvertToUint64();
	*/

	static private native int getAuthSessionTicket(long pointer, Buffer authTicket, int capacityInBytes, int[] sizeInBytes); /*
		ISteamUser* user = (ISteamUser*) pointer;
		int ticket = user->GetAuthSessionTicket(authTicket, capacityInBytes, (uint32*) sizeInBytes);
		return ticket;
	*/

	static private native int beginAuthSession(long pointer, Buffer authTicket, int authTicketSizeInBytes, long steamID); /*
		ISteamUser* user = (ISteamUser*) pointer;
		return user->BeginAuthSession(authTicket, authTicketSizeInBytes, (uint64) steamID);
	*/

	static private native void endAuthSession(long pointer, long steamID); /*
		ISteamUser* user = (ISteamUser*) pointer;
		user->EndAuthSession((uint64) steamID);
	*/

	static private native void cancelAuthTicket(long pointer, int authTicket); /*
		ISteamUser* user = (ISteamUser*) pointer;
		user->CancelAuthTicket(authTicket);
	*/

	static private native int userHasLicenseForApp(long pointer, long steamID, long appID); /*
		ISteamUser* user = (ISteamUser*) pointer;
		return user->UserHasLicenseForApp((uint64) steamID, (AppId_t) appID);
	*/

}
