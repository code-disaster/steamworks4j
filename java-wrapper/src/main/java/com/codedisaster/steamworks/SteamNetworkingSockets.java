package com.codedisaster.steamworks;

import java.nio.ByteBuffer;

public class SteamNetworkingSockets extends SteamInterface {

    public static final class Connection {
        private final int handle;

        public Connection(int handle) {
            this.handle = handle;
        }

        public boolean isValid() {
            return handle != 0;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Connection)) return false;

            Connection that = (Connection) o;
            return handle == that.handle;
        }

        @Override
        public int hashCode() {
            return handle;
        }

        @Override
        public String toString() {
            return Integer.toHexString(handle);
        }
    }

    public static final class Socket {
        private final int handle;

        public Socket(int handle) {
            this.handle = handle;
        }

        public boolean isValid() {
            return handle != 0;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Connection)) return false;

            Connection that = (Connection) o;
            return handle == that.handle;
        }

        @Override
        public int hashCode() {
            return handle;
        }

        @Override
        public String toString() {
            return Integer.toHexString(handle);
        }
    }

    public enum ConnectionState {
        /**
         * Dummy value used to indicate an error condition in the API.
         * Specified connection doesn't exist or has already been closed.
         */
        None(0),

        /**
         * We are trying to establish whether peers can talk to each other,
         * whether they WANT to talk to each other, perform basic auth,
         * and exchange crypt keys.
         *
         * <ul>
         *   <li>For connections on the "client" side (initiated locally):
         *   We're in the process of trying to establish a connection.
         *   Depending on the connection type, we might not know who they are.
         *   Note that it is not possible to tell if we are waiting on the
         *   network to complete handshake packets, or for the application layer
         *   to accept the connection.</li>
         *   <li>For connections on the "server" side (accepted through listen socket):
         *   We have completed some basic handshake and the client has presented
         *   some proof of identity.  The connection is ready to be accepted
         *   using AcceptConnection().</li>
         * </ul>
         *
         * In either case, any unreliable packets sent now are almost certain
         * to be dropped.  Attempts to receive packets are guaranteed to fail.
         * You may send messages if the send mode allows for them to be queued.
         * but if you close the connection before the connection is actually
         * established, any queued messages will be discarded immediately.
         * (We will not attempt to flush the queue and confirm delivery to the
         * remote host, which ordinarily happens when a connection is closed.)
         */
        Connecting(1),

        /**
         * Some connection types use a back channel or trusted 3rd party
         * for earliest communication.  If the server accepts the connection,
         * then these connections switch into the rendezvous state.  During this
         * state, we still have not yet established an end-to-end route (through
         * the relay network), and so if you send any messages unreliable, they
         * are going to be discarded.
         */
        FindingRoute(2),

        /**
         * We've received communications from our peer (and we know
         * who they are) and are all good.  If you close the connection now,
         * we will make our best effort to flush out any reliable sent data that
         * has not been acknowledged by the peer.  (But note that this happens
         * from within the application process, so unlike a TCP connection, you are
         * not totally handing it off to the operating system to deal with it.)
         */
        Connected(3),

        /**
         * Connection has been closed by our peer, but not closed locally.
         * The connection still exists from an API perspective.  You must close the
         * handle to free up resources.  If there are any messages in the inbound queue,
         * you may retrieve them.  Otherwise, nothing may be done with the connection
         * except to close it.
         * <p>
         * This state is similar to CLOSE_WAIT in the TCP state machine.
         */
        ClosedByPeer(4),

        /**
         * A disruption in the connection has been detected locally.  (E.g. timeout,
         * local internet connection disrupted, etc.)
         * <p>
         * The connection still exists from an API perspective.  You must close the
         * handle to free up resources.
         * <p>
         * Attempts to send further messages will fail.  Any remaining received messages
         * in the queue are available.
         */
        ProblemDetectedLocally(5);

        private final int value;
        private static final ConnectionState[] values = values();

        ConnectionState(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static ConnectionState byValue(int value) {
            for (ConnectionState state : values) {
                if (state.value == value) {
                    return state;
                }
            }
            return None;
        }
    }

    /**
     * Flags used to set options for message sending.
     * This is not an enum, because flags are naturally combined with the or operator.
     */
    public interface SendFlags {
        /**
         * Send the message unreliably. Can be lost.  Messages *can* be larger than a
         * single MTU (UDP packet), but there is no retransmission, so if any piece
         * of the message is lost, the entire message will be dropped.
         * <p>
         * The sending API does have some knowledge of the underlying connection, so
         * if there is no NAT-traversal accomplished or there is a recognized adjustment
         * happening on the connection, the packet will be batched until the connection
         * is open again.
         * <p>
         * Migration note: This is not exactly the same as k_EP2PSendUnreliable!  You
         * probably want k_ESteamNetworkingSendType_UnreliableNoNagle
         */
        int Unreliable = 0;

        /**
         * Disable Nagle's algorithm.
         * By default, Nagle's algorithm is applied to all outbound messages.  This means
         * that the message will NOT be sent immediately, in case further messages are
         * sent soon after you send this, which can be grouped together.  Any time there
         * is enough buffered data to fill a packet, the packets will be pushed out immediately,
         * but partially-full packets not be sent until the Nagle timer expires.  See
         * ISteamNetworkingSockets::FlushMessagesOnConnection, ISteamNetworkingMessages::FlushMessagesToUser
         * <p>
         * NOTE: Don't just send every message without Nagle because you want packets to get there
         * quicker.  Make sure you understand the problem that Nagle is solving before disabling it.
         * If you are sending small messages, often many at the same time, then it is very likely that
         * it will be more efficient to leave Nagle enabled.  A typical proper use of this flag is
         * when you are sending what you know will be the last message sent for a while (e.g. the last
         * in the server simulation tick to a particular client), and you use this flag to flush all
         * messages.
         */
        int NoNagle = 1;

        /**
         * Send a message unreliably, bypassing Nagle's algorithm for this message and any messages
         * currently pending on the Nagle timer.  This is equivalent to using k_ESteamNetworkingSend_Unreliable
         * and then immediately flushing the messages using ISteamNetworkingSockets::FlushMessagesOnConnection
         * or ISteamNetworkingMessages::FlushMessagesToUser.  (But using this flag is more efficient since you
         * only make one API call.)
         */
        int UnreliableNoNagle = Unreliable | NoNagle;

        /**
         * If the message cannot be sent very soon (because the connection is still doing some initial
         * handshaking, route negotiations, etc), then just drop it.  This is only applicable for unreliable
         * messages.  Using this flag on reliable messages is invalid.
         */
        int NoDelay = 4;

        /**
         * Send an unreliable message, but if it cannot be sent relatively quickly, just drop it instead of queuing it.
         * This is useful for messages that are not useful if they are excessively delayed, such as voice data.
         * NOTE: The Nagle algorithm is not used, and if the message is not dropped, any messages waiting on the
         * Nagle timer are immediately flushed.
         * <p>
         * A message will be dropped under the following circumstances:
         * - the connection is not fully connected.  (E.g. the "Connecting" or "FindingRoute" states)
         * - there is a sufficiently large number of messages queued up already such that the current message
         *   will not be placed on the wire in the next ~200ms or so.
         * <p>
         * If a message is dropped for these reasons, k_EResultIgnored will be returned.
         */
        int UnreliableNoDelay = Unreliable | NoDelay | NoNagle;

        /**
         * Reliable message send. Can send up to k_cbMaxSteamNetworkingSocketsMessageSizeSend bytes in a single message.
         * Does fragmentation/re-assembly of messages under the hood, as well as a sliding window for
         * efficient sends of large chunks of data.
         * <p>
         * The Nagle algorithm is used.  See notes on k_ESteamNetworkingSendType_Unreliable for more details.
         * See k_ESteamNetworkingSendType_ReliableNoNagle, ISteamNetworkingSockets::FlushMessagesOnConnection,
         * ISteamNetworkingMessages::FlushMessagesToUser
         * <p>
         * Migration note: This is NOT the same as k_EP2PSendReliable, it's more like k_EP2PSendReliableWithBuffering
         */
        int Reliable = 8;

        /**
         * Send a message reliably, but bypass Nagle's algorithm.
         * <p>
         * Migration note: This is equivalent to k_EP2PSendReliable
         */
        int ReliableNoNagle = Reliable | NoNagle;

        /**
         * By default, message sending is queued, and the work of encryption and talking to
         * the operating system sockets, etc is done on a service thread.  This is usually a
         * a performance win when messages are sent from the "main thread".  However, if this
         * flag is set, and data is ready to be sent immediately (either from this message
         * or earlier queued data), then that work will be done in the current thread, before
         * the current call returns.  If data is not ready to be sent (due to rate limiting
         * or Nagle), then this flag has no effect.
         * <p>
         * This is an advanced flag used to control performance at a very low level.  For
         * most applications running on modern hardware with more than one CPU core, doing
         * the work of sending on a service thread will yield the best performance.  Only
         * use this flag if you have a really good reason and understand what you are doing.
         * Otherwise you will probably just make performance worse.
         */
        int UseCurrentThread = 16;

        /**
         * When sending a message using ISteamNetworkingMessages, automatically re-establish
         * a broken session, without returning k_EResultNoConnection.  Without this flag,
         * if you attempt to send a message, and the session was proactively closed by the
         * peer, or an error occurred that disrupted communications, then you must close the
         * session using ISteamNetworkingMessages::CloseSessionWithUser before attempting to
         * send another message.  (Or you can simply add this flag and retry.)  In this way,
         * the disruption cannot go unnoticed, and a more clear order of events can be
         * ascertained. This is especially important when reliable messages are used, since
         * if the connection is disrupted, some of those messages will not have been delivered,
         * and it is in general not possible to know which.  Although a
         * SteamNetworkingMessagesSessionFailed_t callback will be posted when an error occurs
         * to notify you that a failure has happened, callbacks are asynchronous, so it is not
         * possible to tell exactly when it happened.  And because the primary purpose of
         * ISteamNetworkingMessages is to be like UDP, there is no notification when a peer closes
         * the session.
         * <p>
         * If you are not using any reliable messages (e.g. you are using ISteamNetworkingMessages
         * exactly as a transport replacement for UDP-style datagrams only), you may not need to
         * know when an underlying connection fails, and so you may not need this notification.
         */
        int AutoRestartBrokenSession = 32;
    }

    public SteamNetworkingSockets(SteamNetworkingSocketsCallback callback) {
        super(SteamNetworkingSocketsNative.createCallback(new SteamNetworkingSocketsCallbackAdapter(callback)));
    }

    public Connection connectP2P(SteamID steamID, int virtualPort){
        int result = SteamNetworkingSocketsNative.connectP2P(steamID.handle, virtualPort);
        return new Connection(result);
    }

    public Socket createListenSocketP2P(int virtualPort){
        int result = SteamNetworkingSocketsNative.createListenSocketP2P(virtualPort);
        return new Socket(result);
    }

    /**
     * Accepts an incoming connection request.
     *
     * @param connection The handle of the connection to be accepted.
     * @return {@link SteamResult} indicating the result of the operation:
     * <ul>
     *   <li>{@link SteamResult#OK}: Connection successfully accepted.</li>
     *   <li>{@link SteamResult#InvalidParam}: The provided handle is invalid.</li>
     *   <li>{@link SteamResult#InvalidState}: The connection state is not appropriate for acceptance (e.g., not in a pending state).</li>
     * </ul>
     * This method communicates with the native SteamNetworkingSockets API to accept a connection.
     */
    public SteamResult acceptConnection(Connection connection) {
        int result = SteamNetworkingSocketsNative.acceptConnection(connection.handle);
        return SteamResult.byValue(result);
    }

    public boolean closeConnection(Connection connection, int reason, boolean linger){
        return SteamNetworkingSocketsNative.closeConnection(connection.handle, reason, linger);
    }

    public boolean closeListenSocket(Socket socket){
        return SteamNetworkingSocketsNative.closeListenSocket(socket.handle);
    }

    /**
     * Sends a message to a specified connection.
     *
     * @param connection The handle of the connection to which the message is sent.
     * @param data The byte buffer containing the message to be sent.
     * @param sendFlags Flags controlling how the message is sent, see {@link SendFlags}
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
    public SteamResult sendMessageToConnection(Connection connection, ByteBuffer data, int sendFlags) throws SteamException {
        if (!data.isDirect()) {
            throw new SteamException("Direct buffer required!");
        }

        int result = SteamNetworkingSocketsNative.sendMessageToConnection(connection.handle, data, data.position(), data.remaining(), sendFlags);
        return SteamResult.byValue(result);
    }


    /**
     * Flushes messages for a specified connection.
     *
     * @param connection The handle of the connection to flush messages for.
     * @return {@link SteamResult} indicating the result of the operation. Possible values include:
     * <ul>
     *   <li>{@link SteamResult#InvalidParam}: Invalid connection handle.</li>
     *   <li>{@link SteamResult#InvalidState}: Connection is in an invalid state.</li>
     *   <li>{@link SteamResult#NoConnection}: Connection has ended.</li>
     *   <li>{@link SteamResult#Ignored}: No effective operation as the connection was not yet established.</li>
     * </ul>
     * This method communicates with the native SteamNetworkingSockets API to perform the operation.
     */
    public SteamResult flushMessages(Connection connection) {
        int result = SteamNetworkingSocketsNative.flushMessages(connection.handle);
        return SteamResult.byValue(result);
    }

    /**
     * Attempts to receive a single pending message from a SteamNetworkingSockets connection.
     * <p>
     * This method is <b>non-blocking</b>. If no message is currently available, it returns {@code 0}.
     * At most one message is received per invocation.
     * </p>
     *
     * <p>
     * The message payload is copied into the provided {@link ByteBuffer} starting at the buffer’s
     * current {@link ByteBuffer#position()}, and up to {@link ByteBuffer#remaining()} bytes.
     * The buffer <b>must</b> be a {@linkplain ByteBuffer#isDirect() direct buffer}.
     * </p>
     *
     * <p>
     * If the buffer is too small to hold the incoming message, the message is dropped and a
     * {@link SteamException} is thrown. The exception message includes the required buffer size.
     * </p>
     *
     * <p>
     * On success, the buffer position is advanced by the number of bytes written.
     * </p>
     *
     * @param connection The connection handle to receive data from.
     * @param data A direct {@link ByteBuffer} into which the message payload will be written.
     *
     * @return The number of bytes received in the buffer, or {@code 0} if no message is currently available.
     *
     * @throws SteamException
     *     If {@code data} is not a direct buffer, or if the incoming message exceeds
     *     the buffer's remaining capacity.
     *
     * @implNote
     *     The underlying native message is always released before this method returns.
     *     To fully drain the receive queue, callers should invoke this method repeatedly
     *     until it returns {@code 0}, optionally enforcing a per-frame processing limit.
     */
    public int receiveMessageOnConnection(Connection connection, ByteBuffer data) throws SteamException {
        if (!data.isDirect()) {
            throw new SteamException("Direct buffer required!");
        }

        int bytesWritten = SteamNetworkingSocketsNative.receiveMessageOnConnection(connection.handle, data, data.position(), data.remaining());
        if (bytesWritten < 0) {
            throw new SteamException("Buffer Overflow, bytes received: " + (-bytesWritten) + " bytes remaining: " + data.remaining());
        }

        return bytesWritten;
    }

    /**
     * Helper method to globally enable k_ESteamNetworkingConfig_SymmetricConnect
     * Useful to avoid split brain scenarios for P2P matchmaking
     */
    public void enableSymmetricConnect() {
        SteamNetworkingSocketsNative.enableSymmetricConnect();
    }
}
