package com.codedisaster.steamworks;

import com.eclipsesource.json.*;

import java.nio.ByteBuffer;

abstract class SteamWebAPIInterface {

	protected interface RequestCallback {

		void onHTTPRequestCompleted(JsonObject jsonObject, long contextValue, SteamHTTP.HTTPStatusCode statusCode);

		void onHTTPRequestFailed(String responseString, long contextValue, SteamHTTP.HTTPStatusCode statusCode);

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
						String requestString = new String(requestBodyArray);

						if (statusCode == SteamHTTP.HTTPStatusCode.OK) {

							JsonValue json = Json.parse(requestString);
							JsonObject jsonObject = json.asObject();

							requestCallback.onHTTPRequestCompleted(jsonObject, contextValue, statusCode);

						} else {
							requestCallback.onHTTPRequestFailed(requestString, contextValue, statusCode);
						}

					} else {
						requestCallback.onHTTPRequestFailed("Failed to obtain HTTP response data.",
								contextValue, statusCode);
					}

				} catch (SteamException e) {
					// Steam API error
					requestCallback.onHTTPRequestFailed(e.getMessage(), contextValue, statusCode);
				} catch (RuntimeException e) {
					// JSON parse error
					requestCallback.onHTTPRequestFailed(e.getMessage(), contextValue, statusCode);
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
	protected String interfaceName;

	private String webAPIKey;

	protected void createHTTPInterface(String interfaceName, RequestCallback callback, SteamHTTP.API api) {
		http = new SteamHTTP(new HTTPCallback(callback), api);
		this.interfaceName = interfaceName;
	}

	public void dispose() {
		http.dispose();
	}

	public void setWebAPIKey(String key) {
		webAPIKey = (key != null && !key.isEmpty()) ? key : null;
	}

	protected SteamHTTPRequestHandle createHTTPRequest(SteamHTTP.HTTPMethod getOrPost,
													   String methodName, int version, long context) {

		// todo: possibly use StringBuilder

		String url = String.format(
				"https://api.steampowered.com/%s/%s/v%04d/?format=json",
				interfaceName, methodName, version);

		SteamHTTPRequestHandle request = http.createHTTPRequest(getOrPost, url);

		http.setHTTPRequestContextValue(request, context);

		if (webAPIKey != null) {
			http.setHTTPRequestGetOrPostParameter(request, "key", webAPIKey);
		}

		return request;
	}

	protected boolean sendHTTPRequest(SteamHTTPRequestHandle request) {

		SteamAPICall call = http.sendHTTPRequest(request);

		if (!call.isValid()) {
			http.releaseHTTPRequest(request);
		}

		return call.isValid();
	}

}
