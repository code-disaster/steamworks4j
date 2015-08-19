package com.codedisaster.steamworks;

import com.eclipsesource.json.*;

import java.nio.ByteBuffer;

abstract class SteamWebAPIInterface {

	protected interface RequestCallback {
		void onHTTPRequestCompleted(JsonObject jsonObject);
	}

	private class HTTPCallback implements SteamHTTPCallback {

		private RequestCallback requestCallback;

		private ByteBuffer requestBodyData;
		private byte[] requestBodyArray;

		private HTTPCallback(RequestCallback requestCallback) {
			this.requestCallback = requestCallback;
		}

		@Override
		public void onHTTPRequestCompleted(SteamHTTPRequestHandle request,
										   long contextValue,
										   boolean requestSuccessful,
										   SteamHTTP.HTTPStatusCode statusCode,
										   int bodySize) {

			try {

				if (!requestSuccessful) {
					// todo: error callback
					return;
				}

				// resize request buffers, if needed
				if (requestBodyData == null || requestBodyData.capacity() < bodySize) {
					requestBodyData = ByteBuffer.allocateDirect(bodySize);
					requestBodyArray = new byte[bodySize];
				}

				requestBodyData.clear();

				try {

					if (http.getHTTPResponseBodyData(request, requestBodyData)) {

						requestBodyData.get(requestBodyArray);

						JsonValue json = Json.parse(new String(requestBodyArray));
						JsonObject jsonObject = json.asObject();

						requestCallback.onHTTPRequestCompleted(jsonObject);

					} else {
						// todo: error callback
					}

				} catch (SteamException e) {
					e.printStackTrace();
				}

			} finally {
				// always release request
				http.releaseHTTPRequest(request);
			}
		}

		@Override
		public void onHTTPRequestHeadersReceived(SteamHTTPRequestHandle request, long contextValue) {

		}

		@Override
		public void onHTTPRequestDataReceived(SteamHTTPRequestHandle request, long contextValue,
											  int offset, int bytesReceived) {

		}
	}

	protected SteamHTTP http;

	protected void createHTTPInterface(RequestCallback callback, SteamHTTP.API api) {
		http = new SteamHTTP(new HTTPCallback(callback), api);
	}

	public void dispose() {
		http.dispose();
	}

	protected SteamHTTPRequestHandle createHTTPRequest(SteamHTTP.HTTPMethod getOrPost,
													   String interfaceName, String methodName, int version) {

		// todo: possibly use StringBuilder

		String url = String.format(
				"https://api.steampowered.com/%s/%s/v%04d/?format=json",
				interfaceName, methodName, version);

		return http.createHTTPRequest(getOrPost, url);
	}

	protected boolean sendHTTPRequest(SteamHTTPRequestHandle request) {

		SteamAPICall call = http.sendHTTPRequest(request);

		if (!call.isValid()) {
			http.releaseHTTPRequest(request);
		}

		return call.isValid();
	}

}
