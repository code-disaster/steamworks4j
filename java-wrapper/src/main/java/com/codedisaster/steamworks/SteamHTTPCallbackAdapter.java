package com.codedisaster.steamworks;

@SuppressWarnings("unused")
class SteamHTTPCallbackAdapter extends SteamCallbackAdapter<SteamHTTPCallback> {

	SteamHTTPCallbackAdapter(SteamHTTPCallback callback) {
		super(callback);
	}

	void onHTTPRequestCompleted(long request, long contextValue, boolean requestSuccessful,
								int statusCode, int bodySize) {

		callback.onHTTPRequestCompleted(new SteamHTTPRequestHandle(request), contextValue,
				requestSuccessful, SteamHTTP.HTTPStatusCode.byValue(statusCode), bodySize);
	}

	void onHTTPRequestHeadersReceived(long request, long contextValue) {
		callback.onHTTPRequestHeadersReceived(new SteamHTTPRequestHandle(request), contextValue);
	}

	void onHTTPRequestDataReceived(long request, long contextValue, int offset, int bytesReceived) {
		callback.onHTTPRequestDataReceived(new SteamHTTPRequestHandle(request), contextValue, offset, bytesReceived);
	}

}
