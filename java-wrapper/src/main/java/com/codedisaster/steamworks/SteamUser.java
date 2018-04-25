package com.codedisaster.steamworks;

import java.nio.ByteBuffer;

public class SteamUser extends SteamInterface {

	public enum VoiceResult {
		OK,
		NotInitialized,
		NotRecording,
		NoData,
		BufferTooSmall,
		DataCorrupted,
		Restricted,
		UnsupportedCodec,
		ReceiverOutOfDate,
		ReceiverDidNotAnswer;

		private static final VoiceResult[] values = values();

		static VoiceResult byOrdinal(int voiceResult) {
			return values[voiceResult];
		}
	}

	public SteamUser(SteamUserCallback callback) {
		super(SteamAPI.getSteamUserPointer(),
				createCallback(new SteamUserCallbackAdapter(callback)));
	}

	public SteamID getSteamID() {
		return new SteamID(getSteamID(pointer));
	}

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

	public void startVoiceRecording() {
		startVoiceRecording(pointer);
	}

	public void stopVoiceRecording() {
		stopVoiceRecording(pointer);
	}

	public VoiceResult getAvailableVoice(int[] bytesAvailable) {
		int result = getAvailableVoice(pointer, bytesAvailable);

		return VoiceResult.byOrdinal(result);
	}

	public VoiceResult getVoice(ByteBuffer voiceData, int[] bytesWritten) throws SteamException {

		if (!voiceData.isDirect()) {
			throw new SteamException("Direct buffer required!");
		}

		int result = getVoice(pointer, voiceData, voiceData.position(), voiceData.remaining(), bytesWritten);

		return VoiceResult.byOrdinal(result);
	}

	public VoiceResult decompressVoice(ByteBuffer voiceData, ByteBuffer audioData, int[] bytesWritten, int desiredSampleRate) throws SteamException {

		if (!voiceData.isDirect()) {
			throw new SteamException("Direct buffer required!");
		}

		if (!audioData.isDirect()) {
			throw new SteamException("Direct buffer required!");
		}

		int result = decompressVoice(pointer,
				voiceData, voiceData.position(), voiceData.remaining(),
				audioData, audioData.position(), audioData.remaining(),
				bytesWritten, desiredSampleRate);

		return VoiceResult.byOrdinal(result);
	}

	public int getVoiceOptimalSampleRate() {
		return getVoiceOptimalSampleRate(pointer);
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

	public boolean isBehindNAT() {
		return isBehindNAT(pointer);
	}

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

	private static native void startVoiceRecording(long pointer); /*
		ISteamUser* user = (ISteamUser*) pointer;
		user->StartVoiceRecording();
	*/

	private static native void stopVoiceRecording(long pointer); /*
		ISteamUser* user = (ISteamUser*) pointer;
		user->StopVoiceRecording();
	*/

	private static native int getAvailableVoice(long pointer, int[] bytesAvailable); /*
		ISteamUser* user = (ISteamUser*) pointer;
		return user->GetAvailableVoice((uint32*) bytesAvailable);
	*/

	private static native int getVoice(long pointer, ByteBuffer voiceData,
										int bufferOffset, int bufferCapacity, int[] bytesWritten); /*
		ISteamUser* user = (ISteamUser*) pointer;
		return user->GetVoice(true, &voiceData[bufferOffset], bufferCapacity, (uint32*) bytesWritten);
	*/

	private static native int decompressVoice(long pointer, ByteBuffer voiceData, int voiceBufferOffset,
											   int voiceBufferSize, ByteBuffer audioData, int audioBufferOffset,
											   int audioBufferCapacity, int[] bytesWritten, int desiredSampleRate); /*
		ISteamUser* user = (ISteamUser*) pointer;
		return user->DecompressVoice(&voiceData[voiceBufferOffset], voiceBufferSize,
			&audioData[audioBufferOffset], audioBufferCapacity, (uint32*) bytesWritten, desiredSampleRate);
	*/

	private static native int getVoiceOptimalSampleRate(long pointer); /*
		ISteamUser* user = (ISteamUser*) pointer;
		return (int) user->GetVoiceOptimalSampleRate();
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

	private static native boolean isBehindNAT(long pointer); /*
		ISteamUser* user = (ISteamUser*) pointer;
		return user->BIsBehindNAT();
	*/

	private static native void advertiseGame(long pointer, long steamID, int serverIP, short serverPort); /*
		ISteamUser* user = (ISteamUser*) pointer;
		user->AdvertiseGame((uint64) steamID, serverIP, serverPort);
	*/

}
