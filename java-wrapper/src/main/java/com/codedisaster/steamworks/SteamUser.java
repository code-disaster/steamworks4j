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

	/**
	 * Starts the state machine for authenticating the game client with the game server
	 * 
	 * @param authBlob a pointer to empty memory that will be filled in with the authentication token. Should be at least 2048 bytes.
	 * @param steamIDGameServer the steamID of the game server, received from the game server by the client
	 * @param serverIP the IP address of the game server
	 * @param serverPort the port of the game server
	 * @param secure whether or not the client thinks that the game server is reporting itself as secure (i.e. VAC is running)
	 * 
	 * @return returns the number of bytes written to authTicket. If the return is 0, then the buffer passed in was too small, and the call has failed. The contents of authTicket should then be sent to the game server, for it to use to complete the authentication process.
	 */
	public int initiateGameConnection(Buffer authBlob, SteamID steamIDGameServer, int serverIP, short serverPort, boolean secure) throws SteamException {
		
		if (!authBlob.isDirect()) {
			throw new SteamException("Direct buffer required!");
		}
		
		int bytesWritten = initiateGameConnection(pointer, authBlob, authBlob.limit(), steamIDGameServer.handle, serverIP, serverPort, secure);
		
		if(bytesWritten > 0) {
			authBlob.limit(bytesWritten);
		}
		
		return bytesWritten;
	}
	
	/**
	 * Notify of disconnect. Needs to occur when the game client leaves the specified game server, needs to match with the InitiateGameConnection() call
	 */
	public void terminateGameConnection(int serverIP, short serverPort) {
		terminateGameConnection(pointer, serverIP, serverPort);
	}
	
	public SteamAuthTicket getAuthSessionTicket(Buffer authTicket, int[] sizeInBytes) throws SteamException {

		if (!authTicket.isDirect()) {
			throw new SteamException("Direct buffer required!");
		}

		int ticket = getAuthSessionTicket(pointer, authTicket, authTicket.capacity(), sizeInBytes);

		if (ticket != SteamAuthTicket.AuthTicketInvalid) {
			authTicket.limit(sizeInBytes[0]);
		}

		return new SteamAuthTicket(ticket);
	}

	public SteamAuth.BeginAuthSessionResult beginAuthSession(Buffer authTicket, SteamID steamID) throws SteamException {

		if (!authTicket.isDirect()) {
			throw new SteamException("Direct buffer required!");
		}

		int result = beginAuthSession(pointer, authTicket, authTicket.limit(), steamID.handle);
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
	
	/**
	 * returns true if this users looks like they are behind a NAT device. Only valid once the user has connected to steam (i.e a SteamServersConnected_t has been issued) and may not catch all forms of NAT.
	 */
	public boolean isBehindNAT() {
		return isBehindNAT(pointer);
	}
	
	/**
	 * Set data to be replicated to friends so that they can join your game
	 * @param steamIDGameServer - the steamID of the game server, received from the game server by the client
	 * @param serverIP the IP address of the game server
	 * @param serverPort the port of the game server
	 */
	public void advertiseGame(SteamID steamIDGameServer, int serverIP, short serverPort) {
		advertiseGame(pointer, steamIDGameServer.handle, serverIP, serverPort);
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
	
	private static native int initiateGameConnection(long pointer, Buffer authBlob, int authBlobSize, long steamIDGameServer, int serverIP, short serverPort, boolean secure); /*
		ISteamUser* user = (ISteamUser*) pointer;
		int bytesWritten = user->InitiateGameConnection(authBlob, authBlobSize, (uint64) steamIDGameServer, serverIP, serverPort, secure);
		return bytesWritten;
	*/
	
	private static native void terminateGameConnection(long pointer, int serverIP, short serverPort); /*
		ISteamUser* user = (ISteamUser*) pointer;
		user->TerminateGameConnection(serverIP, serverPort);
	*/

	private static native int getAuthSessionTicket(long pointer, Buffer authTicket, int capacityInBytes, int[] sizeInBytes); /*
		ISteamUser* user = (ISteamUser*) pointer;
		int ticket = user->GetAuthSessionTicket(authTicket, capacityInBytes, (uint32*) sizeInBytes);
		return ticket;
	*/

	private static native int beginAuthSession(long pointer, Buffer authTicket, int authTicketSizeInBytes, long steamID); /*
		ISteamUser* user = (ISteamUser*) pointer;
		return user->BeginAuthSession(authTicket, authTicketSizeInBytes, (uint64) steamID);
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
	
	private static native boolean isBehindNAT(long pointer); /*
		ISteamUser* user = (ISteamUser*) pointer;
		return user->BIsBehindNAT();
	*/
	
	private static native void advertiseGame(long pointer, long steamID, int serverIP, short serverPort); /*
		ISteamUser* user = (ISteamUser*) pointer;
		user->AdvertiseGame((uint64) steamID, serverIP, serverPort);
	*/
}
