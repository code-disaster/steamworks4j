package com.codedisaster.steamworks;

public interface SteamHTTPCallback {

	default void onHTTPRequestCompleted(SteamHTTPRequestHandle request,
										long contextValue,
										boolean requestSuccessful,
										SteamHTTP.HTTPStatusCode statusCode,
										int bodySize) {
	}

	default void onHTTPRequestHeadersReceived(SteamHTTPRequestHandle request,
											  long contextValue) {
	}

	default void onHTTPRequestDataReceived(SteamHTTPRequestHandle request,
										   long contextValue,
										   int offset,
										   int bytesReceived) {
	}

}
