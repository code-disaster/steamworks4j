package com.codedisaster.steamworks;

import java.nio.ByteBuffer;

public class SteamHTTP extends SteamInterface {

	public enum API {
		Client,
		Server
	}

	public enum HTTPMethod {
		Invalid,
		GET,
		HEAD,
		POST,
		PUT,
		DELETE,
		OPTIONS
	}

	public enum HTTPStatusCode {
		Invalid(0),

		Continue(100),
		SwitchingProtocols(101),

		OK(200),
		Created(201),
		Accepted(202),
		NonAuthoritative(203),
		NoContent(204),
		ResetContent(205),
		PartialContent(206),

		MultipleChoices(300),
		MovedPermanently(301),
		Found(302),
		SeeOther(303),
		NotModified(304),
		UseProxy(305),
		TemporaryRedirect(307),

		BadRequest(400),
		Unauthorized(401),
		PaymentRequired(402),
		Forbidden(403),
		NotFound(404),
		MethodNotAllowed(405),
		NotAcceptable(406),
		ProxyAuthRequired(407),
		RequestTimeout(408),
		Conflict(409),
		Gone(410),
		LengthRequired(411),
		PreconditionFailed(412),
		RequestEntityTooLarge(413),
		RequestURITooLong(414),
		UnsupportedMediaType(415),
		RequestedRangeNotSatisfiable(416),
		ExpectationFailed(417),
		Unknown4xx(418),
		TooManyRequests(429),

		InternalServerError(500),
		NotImplemented(501),
		BadGateway(502),
		ServiceUnavailable(503),
		GatewayTimeout(504),
		HTTPVersionNotSupported(505),
		Unknown5xx(599);

		private final int code;
		private static final HTTPStatusCode[] values = values();

		HTTPStatusCode(int code) {
			this.code = code;
		}

		static HTTPStatusCode byValue(int statusCode) {
			for (HTTPStatusCode value : values) {
				if (value.code == statusCode) {
					return value;
				}
			}
			return Invalid;
		}
	}

	public SteamHTTP(SteamHTTPCallback callback, API api) {
		super(api == API.Client ? SteamAPI.getSteamHTTPPointer()
				: SteamGameServerAPI.getSteamGameServerHTTPPointer(),
				createCallback(new SteamHTTPCallbackAdapter(callback), api == API.Client));
	}

	public SteamHTTPRequestHandle createHTTPRequest(HTTPMethod requestMethod, String absoluteURL) {
		return new SteamHTTPRequestHandle(createHTTPRequest(pointer, requestMethod.ordinal(), absoluteURL));
	}

	public boolean setHTTPRequestContextValue(SteamHTTPRequestHandle request, long contextValue) {
		return setHTTPRequestContextValue(pointer, request.handle, contextValue);
	}

	public boolean setHTTPRequestNetworkActivityTimeout(SteamHTTPRequestHandle request, int timeoutSeconds) {
		return setHTTPRequestNetworkActivityTimeout(pointer, request.handle, timeoutSeconds);
	}

	public boolean setHTTPRequestHeaderValue(SteamHTTPRequestHandle request,
											 String headerName, String headerValue) {

		return setHTTPRequestHeaderValue(pointer, request.handle, headerName, headerValue);
	}

	public boolean setHTTPRequestGetOrPostParameter(SteamHTTPRequestHandle request,
													String paramName, String paramValue) {

		return setHTTPRequestGetOrPostParameter(pointer, request.handle, paramName, paramValue);
	}

	public SteamAPICall sendHTTPRequest(SteamHTTPRequestHandle request) {
		return new SteamAPICall(sendHTTPRequest(pointer, callback, request.handle));
	}

	public SteamAPICall sendHTTPRequestAndStreamResponse(SteamHTTPRequestHandle request) {
		return new SteamAPICall(sendHTTPRequestAndStreamResponse(pointer, request.handle));
	}

	public int getHTTPResponseHeaderSize(SteamHTTPRequestHandle request, String headerName) {
		return getHTTPResponseHeaderSize(pointer, request.handle, headerName);
	}

	public boolean getHTTPResponseHeaderValue(SteamHTTPRequestHandle request, String headerName, ByteBuffer value) throws SteamException {

		if (!value.isDirect()) {
			throw new SteamException("Direct buffer required!");
		}

		int offset = value.position();
		int capacity = value.limit() - offset;

		return getHTTPResponseHeaderValue(pointer, request.handle, headerName, value, offset, capacity);
	}

	public int getHTTPResponseBodySize(SteamHTTPRequestHandle request) {
		return getHTTPResponseBodySize(pointer, request.handle);
	}

	public boolean getHTTPResponseBodyData(SteamHTTPRequestHandle request, ByteBuffer data) throws SteamException {

		if (!data.isDirect()) {
			throw new SteamException("Direct buffer required!");
		}

		int offset = data.position();
		int capacity = data.limit() - offset;

		return getHTTPResponseBodyData(pointer, request.handle, data, offset, capacity);
	}

	public boolean getHTTPStreamingResponseBodyData(SteamHTTPRequestHandle request, int bodyDataOffset,
													ByteBuffer data) throws SteamException {

		if (!data.isDirect()) {
			throw new SteamException("Direct buffer required!");
		}

		int offset = data.position();
		int capacity = data.limit() - offset;

		return getHTTPStreamingResponseBodyData(pointer, request.handle, bodyDataOffset, data, offset, capacity);
	}

	public boolean releaseHTTPRequest(SteamHTTPRequestHandle request) {
		return releaseHTTPRequest(pointer, request.handle);
	}

	// @off

	/*JNI
		#include "SteamHTTPCallback.h"
		#include "SteamGameServerHTTPCallback.h"
	*/

	private static native long createCallback(SteamHTTPCallbackAdapter javaCallback, boolean isClient); /*
		if (isClient) {
			return (intp) new SteamHTTPCallback(env, javaCallback);
		} else {
			return (intp) new SteamGameServerHTTPCallback(env, javaCallback);
		}
	*/

	private static native long createHTTPRequest(long pointer, int requestMethod, String absoluteURL); /*
		ISteamHTTP* http = (ISteamHTTP*) pointer;
		return http->CreateHTTPRequest((EHTTPMethod) requestMethod, absoluteURL);
	*/

	private static native boolean setHTTPRequestContextValue(long pointer, long request, long contextValue); /*
		ISteamHTTP* http = (ISteamHTTP*) pointer;
		return http->SetHTTPRequestContextValue((HTTPRequestHandle) request, (uint64) contextValue);
	*/

	private static native boolean setHTTPRequestNetworkActivityTimeout(long pointer, long request,
																	   int timeoutSeconds); /*

		ISteamHTTP* http = (ISteamHTTP*) pointer;
		return http->SetHTTPRequestNetworkActivityTimeout((HTTPRequestHandle) request, (uint32) timeoutSeconds);
	*/

	private static native boolean setHTTPRequestHeaderValue(long pointer, long request,
											 				String headerName, String headerValue); /*

		ISteamHTTP* http = (ISteamHTTP*) pointer;
		return http->SetHTTPRequestHeaderValue((HTTPRequestHandle) request, headerName, headerValue);
	*/

	private static native boolean setHTTPRequestGetOrPostParameter(long pointer, long request,
																   String paramName, String paramValue); /*

		ISteamHTTP* http = (ISteamHTTP*) pointer;
		return http->SetHTTPRequestGetOrPostParameter((HTTPRequestHandle) request, paramName, paramValue);
	*/

	private static native long sendHTTPRequest(long pointer, long callback, long request); /*
		ISteamHTTP* http = (ISteamHTTP*) pointer;
		SteamAPICall_t handle;
		if (http->SendHTTPRequest((HTTPRequestHandle) request, &handle)) {
			SteamHTTPCallback* cb = (SteamHTTPCallback*) callback;
			cb->onHTTPRequestCompletedCall.Set(handle, cb, &SteamHTTPCallback::onHTTPRequestCompleted);
			return handle;
		}
		return 0;
	*/

	private static native long sendHTTPRequestAndStreamResponse(long pointer, long request); /*
		ISteamHTTP* http = (ISteamHTTP*) pointer;
		SteamAPICall_t handle;
		if (http->SendHTTPRequestAndStreamResponse((HTTPRequestHandle) request, &handle)) {
			return handle;
		}
		return 0;
	*/

	private static native int getHTTPResponseHeaderSize(long pointer, long request, String headerName); /*
		ISteamHTTP* http = (ISteamHTTP*) pointer;
		uint32 size;
		if (http->GetHTTPResponseHeaderSize((HTTPRequestHandle) request, headerName, &size)) {
			return size;
		}
		return 0;
	*/

	private static native boolean getHTTPResponseHeaderValue(long pointer, long request, String headerName,
															 ByteBuffer value, int offset, int capacity); /*

		ISteamHTTP* http = (ISteamHTTP*) pointer;
		return http->GetHTTPResponseHeaderValue((HTTPRequestHandle) request, headerName, (uint8*) &value[offset], capacity);
	*/

	private static native int getHTTPResponseBodySize(long pointer, long request); /*
		ISteamHTTP* http = (ISteamHTTP*) pointer;
		uint32 size;
		if (http->GetHTTPResponseBodySize((HTTPRequestHandle) request, &size)) {
			return size;
		}
		return 0;
	*/

	private static native boolean getHTTPResponseBodyData(long pointer, long request,
														  ByteBuffer data, int offset, int capacity); /*

		ISteamHTTP* http = (ISteamHTTP*) pointer;
		return http->GetHTTPResponseBodyData((HTTPRequestHandle) request, (uint8*) &data[offset], capacity);
	*/

	private static native boolean getHTTPStreamingResponseBodyData(long pointer, long request, int bodyDataOffset,
																   ByteBuffer data, int offset, int capacity); /*

		ISteamHTTP* http = (ISteamHTTP*) pointer;
		return http->GetHTTPStreamingResponseBodyData((HTTPRequestHandle) request, bodyDataOffset, (uint8*) &data[offset], capacity);
	*/

	private static native boolean releaseHTTPRequest(long pointer, long request); /*
		ISteamHTTP* http = (ISteamHTTP*) pointer;
		return http->ReleaseHTTPRequest((HTTPRequestHandle) request);
	*/

}
