package com.codedisaster.steamworks.test;

import com.codedisaster.steamworks.*;

import java.nio.ByteBuffer;

public class SteamHTTPTestApp extends SteamTestApp {

	private SteamHTTP http;

	private SteamHTTPCallback httpCallback = new SteamHTTPCallback() {
		@Override
		public void onHTTPRequestCompleted(SteamHTTPRequestHandle request, long contextValue, boolean requestSuccessful,
										   SteamHTTP.HTTPStatusCode statusCode, int bodySize) {

			System.out.println("HTTP request completed: " + (requestSuccessful ? "successful" : "failed") +
					", status code=" + statusCode.toString() + ", body size=" + bodySize);
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

					String result = new String(dest);
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
		http = new SteamHTTP(httpCallback, SteamHTTP.API.Client);
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
		}
	}

	public static void main(String[] arguments) {
		new SteamHTTPTestApp().clientMain(arguments);
	}

}
