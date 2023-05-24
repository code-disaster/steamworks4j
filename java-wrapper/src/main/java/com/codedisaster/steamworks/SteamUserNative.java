package com.codedisaster.steamworks;

import java.nio.ByteBuffer;

final class SteamUserNative {

	// @off

	/*JNI
		#include <steam_api.h>
		#include "SteamUserCallback.h"
	*/

	static native long createCallback(SteamUserCallbackAdapter javaCallback); /*
		return (intp) new SteamUserCallback(env, javaCallback);
	*/

	static native long getSteamID(); /*
		CSteamID steamID = SteamUser()->GetSteamID();
		return (int64) steamID.ConvertToUint64();
	*/

	static native int initiateGameConnection(ByteBuffer authBlob,
											 int bufferOffset, int bufferSize, long steamIDGameServer,
											 int serverIP, short serverPort, boolean secure); /*
		int bytesWritten = SteamUser()->InitiateGameConnection_DEPRECATED(&authBlob[bufferOffset], bufferSize,
			(uint64) steamIDGameServer, serverIP, serverPort, secure);
		return bytesWritten;
	*/

	static native void terminateGameConnection(int serverIP, short serverPort); /*
		SteamUser()->TerminateGameConnection_DEPRECATED(serverIP, serverPort);
	*/

	static native void startVoiceRecording(); /*
		SteamUser()->StartVoiceRecording();
	*/

	static native void stopVoiceRecording(); /*
		SteamUser()->StopVoiceRecording();
	*/

	static native int getAvailableVoice(int[] bytesAvailable); /*
		return SteamUser()->GetAvailableVoice((uint32*) bytesAvailable);
	*/

	static native int getVoice(ByteBuffer voiceData,
							   int bufferOffset, int bufferCapacity, int[] bytesWritten); /*
		return SteamUser()->GetVoice(true, &voiceData[bufferOffset], bufferCapacity, (uint32*) bytesWritten);
	*/

	static native int decompressVoice(ByteBuffer voiceData, int voiceBufferOffset,
									  int voiceBufferSize, ByteBuffer audioData, int audioBufferOffset,
									  int audioBufferCapacity, int[] bytesWritten, int desiredSampleRate); /*
		return SteamUser()->DecompressVoice(&voiceData[voiceBufferOffset], voiceBufferSize,
			&audioData[audioBufferOffset], audioBufferCapacity, (uint32*) bytesWritten, desiredSampleRate);
	*/

	static native int getVoiceOptimalSampleRate(); /*
		return (int) SteamUser()->GetVoiceOptimalSampleRate();
	*/

	static native int getAuthSessionTicket(ByteBuffer authTicket,
										   int bufferOffset, int bufferCapacity, int[] sizeInBytes); /*
		int ticket = SteamUser()->GetAuthSessionTicket(&authTicket[bufferOffset], bufferCapacity, (uint32*) sizeInBytes, nullptr);
		return ticket;
	*/

	static native int getAuthTicketForWebApi(); /*
		int ticket = SteamUser()->GetAuthTicketForWebApi(nullptr);
		return ticket;
	*/

	static native int beginAuthSession(ByteBuffer authTicket,
									   int bufferOffset, int bufferSize, long steamID); /*
		return SteamUser()->BeginAuthSession(&authTicket[bufferOffset], bufferSize, (uint64) steamID);
	*/

	static native void endAuthSession(long steamID); /*
		SteamUser()->EndAuthSession((uint64) steamID);
	*/

	static native void cancelAuthTicket(int authTicket); /*
		SteamUser()->CancelAuthTicket(authTicket);
	*/

	static native int userHasLicenseForApp(long steamID, int appID); /*
		return SteamUser()->UserHasLicenseForApp((uint64) steamID, (AppId_t) appID);
	*/

	static native long requestEncryptedAppTicket(long callback,
												 ByteBuffer dataToInclude, int bufferOffset, int bufferSize); /*
		SteamUserCallback* cb = (SteamUserCallback*) callback;
		SteamAPICall_t handle = SteamUser()->RequestEncryptedAppTicket(&dataToInclude[bufferOffset], bufferSize);
		cb->onRequestEncryptedAppTicketCall.Set(handle, cb, &SteamUserCallback::onRequestEncryptedAppTicket);
		return handle;
	*/

	static native boolean getEncryptedAppTicket(ByteBuffer ticket,
												int bufferOffset, int bufferCapacity, int[] sizeInBytes); /*
		return SteamUser()->GetEncryptedAppTicket(&ticket[bufferOffset], bufferCapacity, (uint32*) sizeInBytes);
	*/

	static native boolean isBehindNAT(); /*
		return SteamUser()->BIsBehindNAT();
	*/

	static native void advertiseGame(long steamID, int serverIP, short serverPort); /*
		SteamUser()->AdvertiseGame((uint64) steamID, serverIP, serverPort);
	*/

}
