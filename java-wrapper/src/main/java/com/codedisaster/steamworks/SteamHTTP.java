package com.codedisaster.steamworks;

import java.nio.ByteBuffer;

@SuppressWarnings({ "unused", "UnusedReturnValue" })
public class SteamHTTP extends SteamInterface {

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
		PermanentRedirect(308),

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

		/*public int getValue() {
			return code;
		}

		public */
		static HTTPStatusCode byValue(int statusCode) {
			int from = 0;
			int to = values.length - 1;

			// Performs a simple binary search. This only works as long as the enum values
			// are sorted by status code. With the current size of 44 values, this needs
			// up to six (log2(n+1)) iterations until the result is found.

			while (from <= to) {
				int idx = (from + to) / 2;
				HTTPStatusCode value = values[idx];
				if (statusCode < value.code) {
					to = idx - 1;
				} else if (statusCode > value.code) {
					from = idx + 1;
				} else {
					return value;
				}
			}

			return Invalid;
		}
	}

	private final boolean isServer;

	public SteamHTTP(SteamHTTPCallback callback) {
		this(false, SteamHTTPNative.createCallback(new SteamHTTPCallbackAdapter(callback)));
	}

	SteamHTTP(boolean isServer, long callback) {
		super(callback);
		this.isServer = isServer;
	}

	public SteamHTTPRequestHandle createHTTPRequest(HTTPMethod requestMethod, String absoluteURL) {
		return new SteamHTTPRequestHandle(SteamHTTPNative.createHTTPRequest(isServer, requestMethod.ordinal(), absoluteURL));
	}

	public boolean setHTTPRequestContextValue(SteamHTTPRequestHandle request, long contextValue) {
		return SteamHTTPNative.setHTTPRequestContextValue(isServer, request.handle, contextValue);
	}

	public boolean setHTTPRequestNetworkActivityTimeout(SteamHTTPRequestHandle request, int timeoutSeconds) {
		return SteamHTTPNative.setHTTPRequestNetworkActivityTimeout(isServer, request.handle, timeoutSeconds);
	}

	public boolean setHTTPRequestHeaderValue(SteamHTTPRequestHandle request,
											 String headerName, String headerValue) {

		return SteamHTTPNative.setHTTPRequestHeaderValue(isServer, request.handle, headerName, headerValue);
	}

	public boolean setHTTPRequestGetOrPostParameter(SteamHTTPRequestHandle request,
													String paramName, String paramValue) {

		return SteamHTTPNative.setHTTPRequestGetOrPostParameter(isServer, request.handle, paramName, paramValue);
	}

	public SteamAPICall sendHTTPRequest(SteamHTTPRequestHandle request) {
		return new SteamAPICall(SteamHTTPNative.sendHTTPRequest(isServer, callback, request.handle));
	}

	public SteamAPICall sendHTTPRequestAndStreamResponse(SteamHTTPRequestHandle request) {
		return new SteamAPICall(SteamHTTPNative.sendHTTPRequestAndStreamResponse(isServer, request.handle));
	}

	public int getHTTPResponseHeaderSize(SteamHTTPRequestHandle request, String headerName) {
		return SteamHTTPNative.getHTTPResponseHeaderSize(isServer, request.handle, headerName);
	}

	public boolean getHTTPResponseHeaderValue(SteamHTTPRequestHandle request, String headerName,
											  ByteBuffer value) throws SteamException {

		if (!value.isDirect()) {
			throw new SteamException("Direct buffer required!");
		}

		return SteamHTTPNative.getHTTPResponseHeaderValue(isServer, request.handle, headerName,
				value, value.position(), value.remaining());
	}

	public int getHTTPResponseBodySize(SteamHTTPRequestHandle request) {
		return SteamHTTPNative.getHTTPResponseBodySize(isServer, request.handle);
	}

	public boolean getHTTPResponseBodyData(SteamHTTPRequestHandle request, ByteBuffer data) throws SteamException {

		if (!data.isDirect()) {
			throw new SteamException("Direct buffer required!");
		}

		return SteamHTTPNative.getHTTPResponseBodyData(isServer, request.handle, data, data.position(), data.remaining());
	}

	public boolean getHTTPStreamingResponseBodyData(SteamHTTPRequestHandle request, int bodyDataOffset,
													ByteBuffer data) throws SteamException {

		if (!data.isDirect()) {
			throw new SteamException("Direct buffer required!");
		}

		return SteamHTTPNative.getHTTPStreamingResponseBodyData(isServer, request.handle, bodyDataOffset,
				data, data.position(), data.remaining());
	}

	public boolean releaseHTTPRequest(SteamHTTPRequestHandle request) {
		return SteamHTTPNative.releaseHTTPRequest(isServer, request.handle);
	}

}
