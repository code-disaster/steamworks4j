package com.codedisaster.steamworks;

import java.nio.ByteBuffer;

@SuppressWarnings({ "unused", "UnusedReturnValue" })
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
		super(SteamUserNative.createCallback(new SteamUserCallbackAdapter(callback)));
	}

	public SteamID getSteamID() {
		return new SteamID(SteamUserNative.getSteamID());
	}

	@Deprecated
	public int initiateGameConnection(ByteBuffer authBlob, SteamID steamIDGameServer,
									  int serverIP, short serverPort, boolean secure) throws SteamException {

		if (!authBlob.isDirect()) {
			throw new SteamException("Direct buffer required!");
		}

		int bytesWritten = SteamUserNative.initiateGameConnection(authBlob, authBlob.position(), authBlob.remaining(),
				steamIDGameServer.handle, serverIP, serverPort, secure);

		if (bytesWritten > 0) {
			authBlob.limit(bytesWritten);
		}

		return bytesWritten;
	}

	@Deprecated
	public void terminateGameConnection(int serverIP, short serverPort) {
		SteamUserNative.terminateGameConnection(serverIP, serverPort);
	}

	public void startVoiceRecording() {
		SteamUserNative.startVoiceRecording();
	}

	public void stopVoiceRecording() {
		SteamUserNative.stopVoiceRecording();
	}

	public VoiceResult getAvailableVoice(int[] bytesAvailable) {
		int result = SteamUserNative.getAvailableVoice(bytesAvailable);

		return VoiceResult.byOrdinal(result);
	}

	public VoiceResult getVoice(ByteBuffer voiceData, int[] bytesWritten) throws SteamException {

		if (!voiceData.isDirect()) {
			throw new SteamException("Direct buffer required!");
		}

		int result = SteamUserNative.getVoice(voiceData, voiceData.position(), voiceData.remaining(), bytesWritten);

		return VoiceResult.byOrdinal(result);
	}

	public VoiceResult decompressVoice(ByteBuffer voiceData, ByteBuffer audioData, int[] bytesWritten, int desiredSampleRate) throws SteamException {

		if (!voiceData.isDirect()) {
			throw new SteamException("Direct buffer required!");
		}

		if (!audioData.isDirect()) {
			throw new SteamException("Direct buffer required!");
		}

		int result = SteamUserNative.decompressVoice(
				voiceData, voiceData.position(), voiceData.remaining(),
				audioData, audioData.position(), audioData.remaining(),
				bytesWritten, desiredSampleRate);

		return VoiceResult.byOrdinal(result);
	}

	public int getVoiceOptimalSampleRate() {
		return SteamUserNative.getVoiceOptimalSampleRate();
	}

	public SteamAuthTicket getAuthSessionTicket(ByteBuffer authTicket, int[] sizeInBytes) throws SteamException {

		if (!authTicket.isDirect()) {
			throw new SteamException("Direct buffer required!");
		}

		int ticket = SteamUserNative.getAuthSessionTicket(authTicket,
				authTicket.position(), authTicket.remaining(), sizeInBytes);

		if (ticket != SteamAuthTicket.AuthTicketInvalid) {
			authTicket.limit(sizeInBytes[0]);
		}

		return new SteamAuthTicket(ticket);
	}

	public SteamAuthTicket getAuthTicketForWebApi() {
		int ticket = SteamUserNative.getAuthTicketForWebApi();
		return new SteamAuthTicket(ticket);
	}

	public SteamAuth.BeginAuthSessionResult beginAuthSession(ByteBuffer authTicket, SteamID steamID) throws SteamException {

		if (!authTicket.isDirect()) {
			throw new SteamException("Direct buffer required!");
		}

		int result = SteamUserNative.beginAuthSession(authTicket,
				authTicket.position(), authTicket.remaining(), steamID.handle);

		return SteamAuth.BeginAuthSessionResult.byOrdinal(result);
	}

	public void endAuthSession(SteamID steamID) {
		SteamUserNative.endAuthSession(steamID.handle);
	}

	public void cancelAuthTicket(SteamAuthTicket authTicket) {
		SteamUserNative.cancelAuthTicket((int) authTicket.handle);
	}

	public SteamAuth.UserHasLicenseForAppResult userHasLicenseForApp(SteamID steamID, int appID) {
		return SteamAuth.UserHasLicenseForAppResult.byOrdinal(
				SteamUserNative.userHasLicenseForApp(steamID.handle, appID));
	}

	public SteamAPICall requestEncryptedAppTicket(ByteBuffer dataToInclude) throws SteamException {

		if (!dataToInclude.isDirect()) {
			throw new SteamException("Direct buffer required!");
		}

		return new SteamAPICall(SteamUserNative.requestEncryptedAppTicket(callback, dataToInclude,
				dataToInclude.position(), dataToInclude.remaining()));
	}

	public boolean getEncryptedAppTicket(ByteBuffer ticket, int[] sizeInBytes) throws SteamException {

		if (!ticket.isDirect()) {
			throw new SteamException("Direct buffer required!");
		}

		return SteamUserNative.getEncryptedAppTicket(ticket, ticket.position(), ticket.remaining(), sizeInBytes);
	}

	public boolean isBehindNAT() {
		return SteamUserNative.isBehindNAT();
	}

	public void advertiseGame(SteamID steamIDGameServer, int serverIP, short serverPort) {
		SteamUserNative.advertiseGame(steamIDGameServer.handle, serverIP, serverPort);
	}

}
