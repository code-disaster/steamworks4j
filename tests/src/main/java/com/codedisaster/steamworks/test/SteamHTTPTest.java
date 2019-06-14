package com.codedisaster.steamworks.test;

import com.codedisaster.steamworks.*;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public class SteamHTTPTest extends SteamTestApp {

	private SteamHTTP http;

	private SteamHTTPCallback httpCallback = new SteamHTTPCallback() {
		@Override
		public void onHTTPRequestCompleted(SteamHTTPRequestHandle request, long contextValue, boolean requestSuccessful,
										   SteamHTTP.HTTPStatusCode statusCode, int bodySize) {

			System.out.println("HTTP request completed: " + (requestSuccessful ? "successful" : "failed") +
					", status code=" + statusCode.toString() + ", body size=" + bodySize);

			ByteBuffer bodyData = ByteBuffer.allocateDirect(bodySize);

			try {

				if (http.getHTTPResponseBodyData(request, bodyData)) {

					byte[] dest = new byte[bodyData.limit()];
					bodyData.get(dest);

					String result = new String(dest, Charset.defaultCharset());
					System.out.println("=== begin result:\n" + result + "\n=== end result");

				} else {
					System.out.println("- failed reading request data!");
				}

			} catch (SteamException e) {
				e.printStackTrace();
			}

			System.out.println("- releasing request");
			http.releaseHTTPRequest(request);
		}

		@Override
		public void onHTTPRequestHeadersReceived(SteamHTTPRequestHandle request, long contextValue) {
			System.out.println("HTTP request headers received.");
		}

		@Override
		public void onHTTPRequestDataReceived(SteamHTTPRequestHandle request, long contextValue,
											  int offset, int bytesReceived) {

			System.out.println("HTTP request data received: offset=" + offset + ", bytes=" + bytesReceived);

			ByteBuffer bodyData = ByteBuffer.allocateDirect(bytesReceived);

			try {

				if (http.getHTTPStreamingResponseBodyData(request, offset, bodyData)) {

					byte[] dest = new byte[bodyData.limit()];
					bodyData.get(dest);

					String result = new String(dest, Charset.defaultCharset());
					System.out.println("=== begin result:\n" + result + "\n=== end result");

				} else {
					System.out.println("- failed reading request data!");
				}

			} catch (SteamException e) {
				e.printStackTrace();
			}

			System.out.println("- releasing request");
			http.releaseHTTPRequest(request);
		}
	};

	@Override
	protected void registerInterfaces() {

		System.out.println("Register http ...");
		http = new SteamHTTP(httpCallback);
	}

	@Override
	protected void unregisterInterfaces() {
		http.dispose();
	}

	@Override
	protected void processUpdate() throws SteamException {

	}

	@Override
	protected void processInput(String input) throws SteamException {

		if (input.startsWith("http api")) {
			SteamHTTPRequestHandle request = http.createHTTPRequest(SteamHTTP.HTTPMethod.GET,
					"https://api.steampowered.com/ISteamWebAPIUtil/GetSupportedAPIList/v0001/?format=json");

			SteamAPICall call = http.sendHTTPRequestAndStreamResponse(request);

			if (!call.isValid()) {
				System.out.println("http api: send request failed.");
			}
		} else if (input.startsWith("http achievements")) {
			SteamHTTPRequestHandle request = http.createHTTPRequest(SteamHTTP.HTTPMethod.GET,
					"https://api.steampowered.com/ISteamUserStats/GetGlobalAchievementPercentagesForApp/v0002/?format=json");

			http.setHTTPRequestGetOrPostParameter(request, "gameid", Long.toString(clientUtils.getAppID()));

			SteamAPICall call = http.sendHTTPRequest(request);

			if (!call.isValid()) {
				System.out.println("http api: send request failed.");
			}
		}
	}

	public static void main(String[] arguments) {
		new SteamHTTPTest().clientMain(arguments);
	}

	/*static {
		// "unit test" for lookup HTTP status code by value
		SteamHTTP.HTTPStatusCode[] values = SteamHTTP.HTTPStatusCode.values();
		for (SteamHTTP.HTTPStatusCode value : values) {
			int request = value.getValue();
			SteamHTTP.HTTPStatusCode result = SteamHTTP.HTTPStatusCode.byValue(request);
			if (result != value) {
				throw new IllegalStateException();
			}
		}

		// test some out of range values
		final int[] codes = { -1, 0, 199, 9001 };
		for (int code : codes) {
			SteamHTTP.HTTPStatusCode result = SteamHTTP.HTTPStatusCode.byValue(code);
			if (result != SteamHTTP.HTTPStatusCode.Invalid) {
				throw new IllegalStateException();
			}
		}
	}*/

}
