package com.codedisaster.steamworks;

public interface SteamNetworkingSocketsCallback {

    void onConnectionStatusChanged(int connectionHandle, long steamID, int connectionState, int prevState);

    public static enum ConnectionState {
        /**
         * Indicates an error condition in the API. The specified connection doesn't exist or has already been closed.
         */
        NONE(0),

        /**
         * The process of establishing a connection is ongoing. This includes basic authentication, key exchange, etc.
         * For client-side connections, this means the connection attempt is in progress.
         * For server-side connections, it means the connection is ready to be accepted.
         * Unreliable packets sent now are likely to be dropped.
         */
        CONNECTING(1),

        /**
         * The connection is in the process of finding a route (for certain connection types).
         * Unreliable messages are likely to be discarded during this state.
         */
        FINDING_ROUTE(2),

        /**
         * A stable connection has been established. Reliable data sent before closing will be flushed and acknowledged.
         */
        CONNECTED(3),

        /**
         * The connection has been closed by the peer but not yet closed locally. The connection still exists,
         * and any inbound messages can be retrieved before closing it.
         */
        CLOSED_BY_PEER(4),

        /**
         * A local disruption in the connection (e.g., timeout, local internet connection issue) has been detected.
         * The connection still exists, but attempts to send messages will fail.
         */
        PROBLEM_DETECTED_LOCALLY(5);

        private final int value;

        ConnectionState(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static ConnectionState fromValue(int value) {
            for (ConnectionState state : values()) {
                if (state.value == value) {
                    return state;
                }
            }
            return null; // or throw an exception
        }
    }


}
