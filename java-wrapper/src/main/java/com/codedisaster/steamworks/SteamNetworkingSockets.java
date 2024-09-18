package com.codedisaster.steamworks;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

public class SteamNetworkingSockets extends SteamInterface {

    public static final int SEND_FLAG_UNRELIABLE = 0;
    public static final int SEND_FLAG_NO_NAGLE = 1;
    public static final int SEND_FLAG_NO_DELAY = 4;
    public static final int SEND_FLAG_RELIABLE = 8;
    public static final int SEND_FLAG_AUTO_RESTART = 32;

    public SteamNetworkingSockets(SteamNetworkingSocketsCallback callback) {
        super(SteamNetworkingSocketsNative.createCallback(callback));
    }

    public int connectP2P(long steamID, int numVirtualPorts){
        return SteamNetworkingSocketsNative.connectP2P(steamID, numVirtualPorts);
    }

    public int createListenSocketP2P(int numVirtualPorts){
        return SteamNetworkingSocketsNative.createListenSocketP2P(numVirtualPorts);
    }

    /**
     * Accepts an incoming connection request.
     *
     * @param connectionHandle The handle of the connection to be accepted.
     * @return {@link SteamResult} indicating the result of the operation:
     * <ul>
     *   <li>{@link SteamResult#OK}: Connection successfully accepted.</li>
     *   <li>{@link SteamResult#InvalidParam}: The provided handle is invalid.</li>
     *   <li>{@link SteamResult#InvalidState}: The connection state is not appropriate for acceptance (e.g., not in a pending state).</li>
     * </ul>
     * This method communicates with the native SteamNetworkingSockets API to accept a connection.
     */
    public SteamResult acceptConnection(int connectionHandle) {
        int result = SteamNetworkingSocketsNative.acceptConnection(connectionHandle);
        return SteamResult.byValue(result);
    }


    public boolean closeConnection(int connectionHandle, int reason, boolean linger){
        return SteamNetworkingSocketsNative.closeConnection(connectionHandle, reason, linger);
    }

    public boolean closeListenSocket(int socketHandle){
        return SteamNetworkingSocketsNative.closeListenSocket(socketHandle);
    }

    /**
     * Sends a message to a specified connection.
     *
     * @param connectionHandle The handle of the connection to which the message is sent.
     * @param data The byte array containing the message to be sent.
     * @param sendFlags Flags controlling how the message is sent.
     * @return {@link SteamResult} indicating the result of the operation. Possible values include:
     * <ul>
     *   <li>{@link SteamResult#InvalidParam}: Invalid connection handle, or the message size exceeds the maximum allowed limit (refer to {@code k_cbMaxSteamNetworkingSocketsMessageSizeSend}).</li>
     *   <li>{@link SteamResult#InvalidState}: Connection is in an invalid state.</li>
     *   <li>{@link SteamResult#NoConnection}: Connection has ended.</li>
     *   <li>{@link SteamResult#Ignored}: Message dropped due to usage of {@code k_nSteamNetworkingSend_NoDelay} and unavailability of immediate sending capacity.</li>
     *   <li>{@link SteamResult#LimitExceeded}: Queue limit for outgoing messages exceeded (refer to {@code k_ESteamNetworkingConfig_SendBufferSize}).</li>
     * </ul>
     * This method interfaces with the native SteamNetworkingSockets API for message transmission.
     */
    public SteamResult sendMessageToConnection(int connectionHandle, byte[] data, int sendFlags) {

        ByteBuffer buffer = ByteBuffer.allocateDirect(data.length);
        buffer.put(data);

        int result = SteamNetworkingSocketsNative.sendMessageToConnection(connectionHandle, buffer, data.length, sendFlags);
        return SteamResult.byValue(result);
    }


    /**
     * Flushes messages for a specified connection.
     *
     * @param connectionHandle The handle of the connection to flush messages for.
     * @return {@link SteamResult} indicating the result of the operation. Possible values include:
     * <ul>
     *   <li>{@link SteamResult#InvalidParam}: Invalid connection handle.</li>
     *   <li>{@link SteamResult#InvalidState}: Connection is in an invalid state.</li>
     *   <li>{@link SteamResult#NoConnection}: Connection has ended.</li>
     *   <li>{@link SteamResult#Ignored}: No effective operation as the connection was not yet established.</li>
     * </ul>
     * This method communicates with the native SteamNetworkingSockets API to perform the operation.
     */
    public SteamResult flushMessages(int connectionHandle) {
        int result = SteamNetworkingSocketsNative.flushMessages(connectionHandle);
        return SteamResult.byValue(result);
    }


    public int receiveMessagesOnConnection(int connectionHandle, SteamNetworkingMessage[] messages){
        return SteamNetworkingSocketsNative.receiveMessagesOnConnection(connectionHandle, messages, messages.length);
    }

    public int createPollGroup(){
        return SteamNetworkingSocketsNative.createPollGroup();
    }

    public boolean destroyPollGroup(int handle){
        return SteamNetworkingSocketsNative.destroyPollGroup(handle);
    }

    public boolean setConnectionPollGroup(int connectionHandle, int pollGroupHandle){
        return SteamNetworkingSocketsNative.setConnectionPollGroup(connectionHandle, pollGroupHandle);
    }

    public int receiveMessagesOnPollGroup(int pollGroupHandle, SteamNetworkingMessage[] messages){
        return SteamNetworkingSocketsNative.receiveMessagesOnPollGroup(pollGroupHandle, messages, messages.length);
    }

    public SteamNetworkingAvailability initAuthentication(){
        int state = SteamNetworkingSocketsNative.initAuthentication();
        return SteamNetworkingAvailability.fromValue(state);
    }

    public SteamNetworkingAvailability getAuthenticationStatus(){
        int state = SteamNetworkingSocketsNative.getAuthenticationStatus();
        return SteamNetworkingAvailability.fromValue(state);
    }

    public void initRelayNetworkAccess(){
        SteamNetworkingSocketsNative.initRelayNetworkAccess();
    }

    public SteamNetworkingAvailability getRelayNetworkStatus(){
        int state = SteamNetworkingSocketsNative.getRelayNetworkStatus();
        return SteamNetworkingAvailability.fromValue(state);
    }

    public enum SteamNetworkingAvailability {
        // Enum values
        CANNOT_TRY(-102),
        FAILED(-101),
        PREVIOUSLY(-100),
        RETRYING(-10),
        NEVER_TRIED(1),
        WAITING(2),
        ATTEMPTING(3),
        CURRENT(100),
        UNKNOWN(0);

        private final int value;

        // Static map to store the mapping from int values to enum constants
        private static final Map<Integer, SteamNetworkingAvailability> valueToEnumMap = new HashMap<>();

        // Static block to populate the map
        static {
            for (SteamNetworkingAvailability availability : values()) {
                valueToEnumMap.put(availability.value, availability);
            }
        }

        SteamNetworkingAvailability(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        // Function to get enum by its value
        public static SteamNetworkingAvailability fromValue(int value) {
            return valueToEnumMap.getOrDefault(value, UNKNOWN);
        }
    }


}
