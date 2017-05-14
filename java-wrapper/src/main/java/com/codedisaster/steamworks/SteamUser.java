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

<<<<<<< HEAD
	public int initiateGameConnection(ByteBuffer authBlob, SteamID steamIDGameServer,
									  int serverIP, short serverPort, boolean secure) throws SteamException {

		if (!authBlob.isDirect()) {
			throw new SteamException("Direct buffer required!");
		}

		int bytesWritten = initiateGameConnection(pointer, authBlob, authBlob.position(), authBlob.remaining(),
				steamIDGameServer.handle, serverIP, serverPort, secure);

		if (bytesWritten > 0) {
			authBlob.limit(bytesWritten);
		}

		return bytesWritten;
	}

	public void terminateGameConnection(int serverIP, short serverPort) {
		terminateGameConnection(pointer, serverIP, serverPort);
	}

	public SteamAuthTicket getAuthSessionTicket(ByteBuffer authTicket, int[] sizeInBytes) throws SteamException {
=======
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
>>>>>>> refs/remotes/origin/Multiplayer

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
<<<<<<< HEAD

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

	public boolean isBehindNAT() {
		return isBehindNAT(pointer);
	}

=======
	
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
>>>>>>> refs/remotes/origin/Multiplayer
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

	private static native int initiateGameConnection(long pointer, ByteBuffer authBlob,
													 int bufferOffset, int bufferSize, long steamIDGameServer,
													 int serverIP, short serverPort, boolean secure); /*
		ISteamUser* user = (ISteamUser*) pointer;
		int bytesWritten = user->InitiateGameConnection(&authBlob[bufferOffset], bufferSize,
			(uint64) steamIDGameServer, serverIP, serverPort, secure);
		return bytesWritten;
	*/

	private static native void terminateGameConnection(long pointer, int serverIP, short serverPort); /*
		ISteamUser* user = (ISteamUser*) pointer;
		user->TerminateGameConnection(serverIP, serverPort);
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
<<<<<<< HEAD

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

	private static native boolean isBehindNAT(long pointer); /*
		ISteamUser* user = (ISteamUser*) pointer;
		return user->BIsBehindNAT();
	*/

	private static native void advertiseGame(long pointer, long steamID, int serverIP, short serverPort); /*
		ISteamUser* user = (ISteamUser*) pointer;
		user->AdvertiseGame((uint64) steamID, serverIP, serverPort);
	*/

=======
	
	private static native boolean isBehindNAT(long pointer); /*
		ISteamUser* user = (ISteamUser*) pointer;
		return user->BIsBehindNAT();
	*/
	
	private static native void advertiseGame(long pointer, long steamID, int serverIP, short serverPort); /*
		ISteamUser* user = (ISteamUser*) pointer;
		user->AdvertiseGame((uint64) steamID, serverIP, serverPort);
	*/
>>>>>>> refs/remotes/origin/Multiplayer
}
