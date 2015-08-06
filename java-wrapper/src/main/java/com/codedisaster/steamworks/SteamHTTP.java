package com.codedisaster.steamworks;

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



	public SteamAPICall sendHTTPRequest(SteamHTTPRequestHandle request) {
		return new SteamAPICall(sendHTTPRequest(pointer, request.handle));
	}

	public SteamAPICall sendHTTPRequestAndStreamResponse(SteamHTTPRequestHandle request) {
		return new SteamAPICall(sendHTTPRequestAndStreamResponse(pointer, request.handle));
	}

	// @off

	/*JNI
		#include "SteamHTTPCallback.h"
	*/

	private static native long createCallback(SteamHTTPCallbackAdapter javaCallback, boolean isClient); /*
		if (isClient) {
			return (long) new SteamHTTPCallback(env, javaCallback);
		} else {
			//return (long) new SteamGameServerHTTPCallback(env, javaCallback);
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


	private static native long sendHTTPRequest(long pointer, long request); /*
		ISteamHTTP* http = (ISteamHTTP*) pointer;
		SteamAPICall_t handle;
		if (http->SendHTTPRequest((HTTPRequestHandle) request, &handle)) {
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

}
