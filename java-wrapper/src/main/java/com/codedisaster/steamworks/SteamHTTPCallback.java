package com.codedisaster.steamworks;

public interface SteamHTTPCallback {

	void onHTTPRequestCompleted(SteamHTTPRequestHandle request,
								long contextValue,
								boolean requestSuccessful,
								SteamHTTP.HTTPStatusCode statusCode,
								int bodySize);

	void onHTTPRequestHeadersReceived(SteamHTTPRequestHandle request,
									  long contextValue);

	void onHTTPRequestDataReceived(SteamHTTPRequestHandle request,
								   long contextValue,
								   int offset,
								   int bytesReceived);

}
