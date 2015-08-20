package com.codedisaster.steamworks;

interface SteamWebAPIInterfaceCallback {

	void onWebAPIRequestFailed(String interfaceName, String errorMessage, SteamHTTP.HTTPStatusCode statusCode);

}
