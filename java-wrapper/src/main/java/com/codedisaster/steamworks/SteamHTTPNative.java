package com.codedisaster.steamworks;

import java.nio.ByteBuffer;

final class SteamHTTPNative {

	// @off

	/*JNI
		#include "SteamHTTPCallback.h"
	*/

	static native long createCallback(SteamHTTPCallbackAdapter javaCallback); /*
		return (intp) new SteamHTTPCallback(env, javaCallback);
	*/

	static native long createHTTPRequest(boolean server, int requestMethod, String absoluteURL); /*
		ISteamHTTP* http = server ? SteamGameServerHTTP() : SteamHTTP();
		return http->CreateHTTPRequest((EHTTPMethod) requestMethod, absoluteURL);
	*/

	static native boolean setHTTPRequestContextValue(boolean server, long request, long contextValue); /*
		ISteamHTTP* http = server ? SteamGameServerHTTP() : SteamHTTP();
		return http->SetHTTPRequestContextValue((HTTPRequestHandle) request, (uint64) contextValue);
	*/

	static native boolean setHTTPRequestNetworkActivityTimeout(boolean server, long request,
															   int timeoutSeconds); /*

		ISteamHTTP* http = server ? SteamGameServerHTTP() : SteamHTTP();
		return http->SetHTTPRequestNetworkActivityTimeout((HTTPRequestHandle) request, (uint32) timeoutSeconds);
	*/

	static native boolean setHTTPRequestHeaderValue(boolean server, long request,
													String headerName, String headerValue); /*

		ISteamHTTP* http = server ? SteamGameServerHTTP() : SteamHTTP();
		return http->SetHTTPRequestHeaderValue((HTTPRequestHandle) request, headerName, headerValue);
	*/

	static native boolean setHTTPRequestGetOrPostParameter(boolean server, long request,
														   String paramName, String paramValue); /*

		ISteamHTTP* http = server ? SteamGameServerHTTP() : SteamHTTP();
		return http->SetHTTPRequestGetOrPostParameter((HTTPRequestHandle) request, paramName, paramValue);
	*/

	static native long sendHTTPRequest(boolean server, long callback, long request); /*
		ISteamHTTP* http = server ? SteamGameServerHTTP() : SteamHTTP();
		SteamAPICall_t handle;
		if (http->SendHTTPRequest((HTTPRequestHandle) request, &handle)) {
			SteamHTTPCallback* cb = (SteamHTTPCallback*) callback;
			cb->onHTTPRequestCompletedCall.Set(handle, cb, &SteamHTTPCallback::onHTTPRequestCompleted);
			return handle;
		}
		return 0;
	*/

	static native long sendHTTPRequestAndStreamResponse(boolean server, long request); /*
		ISteamHTTP* http = server ? SteamGameServerHTTP() : SteamHTTP();
		SteamAPICall_t handle;
		if (http->SendHTTPRequestAndStreamResponse((HTTPRequestHandle) request, &handle)) {
			return handle;
		}
		return 0;
	*/

	static native int getHTTPResponseHeaderSize(boolean server, long request, String headerName); /*
		ISteamHTTP* http = server ? SteamGameServerHTTP() : SteamHTTP();
		uint32 size;
		if (http->GetHTTPResponseHeaderSize((HTTPRequestHandle) request, headerName, &size)) {
			return size;
		}
		return 0;
	*/

	static native boolean getHTTPResponseHeaderValue(boolean server, long request, String headerName,
													 ByteBuffer value, int offset, int size); /*

		ISteamHTTP* http = server ? SteamGameServerHTTP() : SteamHTTP();
		return http->GetHTTPResponseHeaderValue((HTTPRequestHandle) request, headerName, (uint8*) &value[offset], size);
	*/

	static native int getHTTPResponseBodySize(boolean server, long request); /*
		ISteamHTTP* http = server ? SteamGameServerHTTP() : SteamHTTP();
		uint32 size;
		if (http->GetHTTPResponseBodySize((HTTPRequestHandle) request, &size)) {
			return size;
		}
		return 0;
	*/

	static native boolean getHTTPResponseBodyData(boolean server, long request,
												  ByteBuffer data, int offset, int size); /*

		ISteamHTTP* http = server ? SteamGameServerHTTP() : SteamHTTP();
		return http->GetHTTPResponseBodyData((HTTPRequestHandle) request, (uint8*) &data[offset], size);
	*/

	static native boolean getHTTPStreamingResponseBodyData(boolean server, long request, int bodyDataOffset,
														   ByteBuffer data, int offset, int size); /*

		ISteamHTTP* http = server ? SteamGameServerHTTP() : SteamHTTP();
		return http->GetHTTPStreamingResponseBodyData((HTTPRequestHandle) request, bodyDataOffset, (uint8*) &data[offset], size);
	*/

	static native boolean releaseHTTPRequest(boolean server, long request); /*
		ISteamHTTP* http = server ? SteamGameServerHTTP() : SteamHTTP();
		return http->ReleaseHTTPRequest((HTTPRequestHandle) request);
	*/

}
