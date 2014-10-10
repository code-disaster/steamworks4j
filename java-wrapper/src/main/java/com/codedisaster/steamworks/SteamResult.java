package com.codedisaster.steamworks;

public enum SteamResult {

	/** Error codes. Values need to map directly to SDK error codes! */
	OK(1),
	Fail(2),

	/** If this is returned, we missed to "port" an Steam error code above. */
	UnknownErrorCode_NotImplementedByAPI(0);

	private int code;
	static private final SteamResult[] valuesLookupTable;

	private SteamResult(int code) {
		this.code = code;
	}

	static public SteamResult byValue(int resultCode) {
		if (resultCode < valuesLookupTable.length) {
			return valuesLookupTable[resultCode];
		} else {
			return UnknownErrorCode_NotImplementedByAPI;
		}
	}

	static {
		SteamResult[] values = values();
		int maxResultCode = 0;

		for (SteamResult value : values) {
			maxResultCode = Math.max(maxResultCode, value.code);
		}

		valuesLookupTable = new SteamResult[maxResultCode + 1];

		for (SteamResult value : values) {
			valuesLookupTable[value.code] = value;
		}
	}
}
