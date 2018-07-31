package com.codedisaster.steamworks.test;

import com.codedisaster.steamworks.*;
import com.codedisaster.steamworks.test.mixin.FriendsMixin;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import static com.codedisaster.steamworks.SteamNativeHandle.getNativeHandle;

public class SteamMatchmakingTest extends SteamTestApp {

	private FriendsMixin friends;
	private SteamMatchmaking matchmaking;
	private Map<Long, SteamID> lobbies = new HashMap<Long, SteamID>();

	private static final String LobbyDataKey = "[test-key]";
	private static final String LobbyDataValue = "[test-value]";

	private SteamMatchmakingCallback matchmakingCallback = new SteamMatchmakingCallback() {

		private final ByteBuffer chatMessage = ByteBuffer.allocateDirect(4096);
		private final SteamMatchmaking.ChatEntry chatEntry = new SteamMatchmaking.ChatEntry();
		private final Charset messageCharset = Charset.forName("UTF-8");

		@Override
		public void onFavoritesListChanged(int ip, int queryPort, int connPort, int appID, int flags, boolean add, int accountID) {
			System.out.println("[onFavoritesListChanged]");
		}

		@Override
		public void onLobbyInvite(SteamID steamIDUser, SteamID steamIDLobby, long gameID) {
			System.out.println("Lobby invite received for " + steamIDLobby);
			System.out.println("  - from user: " + steamIDUser.getAccountID());
			System.out.println("  - for game: " + gameID);

			System.out.println("  - auto-joining lobby ...");

			lobbies.put(getNativeHandle(steamIDLobby), steamIDLobby);
			matchmaking.joinLobby(steamIDLobby);
		}

		@Override
		public void onLobbyEnter(SteamID steamIDLobby, int chatPermissions, boolean blocked, SteamMatchmaking.ChatRoomEnterResponse response) {
			System.out.println("Lobby entered: " + steamIDLobby);
			System.out.println("  - response: " + response);

			int numMembers = matchmaking.getNumLobbyMembers(steamIDLobby);
			System.out.println("  - " + numMembers + " members in lobby");
			for (int i = 0; i < numMembers; i++) {
				SteamID member = matchmaking.getLobbyMemberByIndex(steamIDLobby, i);
				System.out.println("    - " + i + ": accountID=" + member.getAccountID());
			}
		}

		@Override
		public void onLobbyDataUpdate(SteamID steamIDLobby, SteamID steamIDMember, boolean success) {
			System.out.println("Lobby data update for " + steamIDLobby);
			System.out.println("  - member: " + steamIDMember.getAccountID());
			System.out.println("  - success: " + success);
		}

		@Override
		public void onLobbyChatUpdate(SteamID steamIDLobby, SteamID steamIDUserChanged, SteamID steamIDMakingChange, SteamMatchmaking.ChatMemberStateChange stateChange) {
			System.out.println("Lobby chat update for " + steamIDLobby);
			System.out.println("  - user changed: " + steamIDUserChanged.getAccountID());
			System.out.println("  - made by user: " + steamIDMakingChange.getAccountID());
			System.out.println("  - state changed: " + stateChange.name());
		}

		@Override
		public void onLobbyChatMessage(SteamID steamIDLobby, SteamID steamIDUser, SteamMatchmaking.ChatEntryType entryType, int chatID) {
			System.out.println("Lobby chat message for " + steamIDLobby);
			System.out.println("  - from user: " + steamIDUser.getAccountID());
			System.out.println("  - chat entry type: " + entryType);
			System.out.println("  - chat id: #" + chatID);

			try {

				int size = matchmaking.getLobbyChatEntry(steamIDLobby, chatID, chatEntry, chatMessage);
				System.out.println("Lobby chat message #" + chatID + " has " + size + " bytes");

				byte[] bytes = new byte[size];
				chatMessage.get(bytes);

				String message = new String(bytes, messageCharset);

				System.out.println("  - from user: " + chatEntry.getSteamIDUser().getAccountID());
				System.out.println("  - chat entry type: " + chatEntry.getChatEntryType());
				System.out.println("  - content: \"" + message + "\"");

			} catch (SteamException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void onLobbyGameCreated(SteamID steamIDLobby, SteamID steamIDGameServer, int ip, short port) {
			System.out.println("[onLobbyGameCreated]");
		}

		@Override
		public void onLobbyMatchList(int lobbiesMatching) {
			System.out.println("Found " + lobbiesMatching + " matching lobbies.");

			lobbies.clear();
			for (int i = 0; i < lobbiesMatching; i++) {
				SteamID lobby = matchmaking.getLobbyByIndex(i);
				lobbies.put(getNativeHandle(lobby), lobby);
			}

			listLobbies();
		}

		@Override
		public void onLobbyKicked(SteamID steamIDLobby, SteamID steamIDAdmin, boolean kickedDueToDisconnect) {
			System.out.println("Kicked from lobby: " + steamIDLobby);
			System.out.println("  - by user (admin): " + steamIDAdmin.getAccountID());
			System.out.println("  - kicked due to disconnect: " + (kickedDueToDisconnect ? "yes" : "no"));
		}

		@Override
		public void onLobbyCreated(SteamResult result, SteamID steamIDLobby) {
			System.out.println("Lobby created: " + steamIDLobby);
			System.out.println("  - result: " + result.name());
			if (result == SteamResult.OK) {
				lobbies.put(getNativeHandle(steamIDLobby), steamIDLobby);
				matchmaking.setLobbyData(steamIDLobby, LobbyDataKey, LobbyDataValue);
			}
		}

		@Override
		public void onFavoritesListAccountsUpdated(SteamResult result) {
			System.out.println("[onFavoritesListAccountsUpdated]");
		}
	};

	private void listLobbies() {
		for (Map.Entry<Long, SteamID> lobby : lobbies.entrySet()) {
			System.out.print("  " + Long.toHexString(lobby.getKey()) + ": ");
			if (lobby.getValue().isValid()) {
				int members = matchmaking.getNumLobbyMembers(lobby.getValue());
				System.out.println(members + " members");
			} else {
				System.err.println("invalid SteamID!");
			}
		}
	}

	@Override
	protected void registerInterfaces() {
		friends = new FriendsMixin();
		matchmaking = new SteamMatchmaking(matchmakingCallback);
	}

	@Override
	protected void unregisterInterfaces() {
		friends.dispose();
		matchmaking.dispose();
	}

	@Override
	protected void processUpdate() throws SteamException {

	}

	@Override
	protected void processInput(String input) throws SteamException {

		if (input.equals("lobby list")) {
			listLobbies();
		} else if (input.startsWith("lobby request ")) {
			String[] parameters = input.substring("lobby request ".length()).split(" ");
			int limit = Integer.parseInt(parameters[0]);
			System.out.println("  - requesting up to " + limit + " lobbies");
			matchmaking.addRequestLobbyListResultCountFilter(limit);
			matchmaking.addRequestLobbyListStringFilter(LobbyDataKey, LobbyDataValue, SteamMatchmaking.LobbyComparison.Equal);
			matchmaking.requestLobbyList();
		} else if (input.startsWith("lobby create ")) {
			int maxMembers = Integer.parseInt(input.substring("lobby create ".length()));
			System.out.println("  creating lobby for " + maxMembers + " players.");
			matchmaking.createLobby(SteamMatchmaking.LobbyType.Public, maxMembers);
		} else if (input.startsWith("lobby join ")) {
			long id = Long.parseLong(input.substring("lobby join ".length()), 16);
			if (lobbies.containsKey(id)) {
				System.out.println("  joining lobby " + Long.toHexString(id));
				matchmaking.joinLobby(lobbies.get(id));
			} else {
				System.err.println("No lobby found: " + Long.toHexString(id));
			}
		} else if (input.startsWith("lobby leave ")) {
			long id = Long.parseLong(input.substring("lobby leave ".length()), 16);
			if (lobbies.containsKey(id)) {
				System.out.println("  leaving lobby " + Long.toHexString(id));
				matchmaking.leaveLobby(lobbies.get(id));
				lobbies.remove(id);
			} else {
				System.err.println("No lobby found: " + Long.toHexString(id));
			}
		} else if (input.startsWith("lobby invite ")) {
			String[] ids = input.substring("lobby invite ".length()).split(" ");
			if (ids.length == 2) {
				long lobbyID = Long.parseLong(ids[0], 16);
				int playerAccountID = Integer.parseInt(ids[1]);
				if (lobbies.containsKey(lobbyID)) {
					System.out.println("  inviting player " + playerAccountID + " to lobby " + Long.toHexString(lobbyID));
					if (friends.isFriendAccountID(playerAccountID)) {
						matchmaking.inviteUserToLobby(lobbies.get(lobbyID), friends.getFriendSteamID(playerAccountID));
					} else {
						System.err.println("No player (friend) found: " + playerAccountID);
					}
				} else {
					System.err.println("No lobby found: " + Long.toHexString(lobbyID));
				}
			} else {
				System.err.println("Expecting: 'lobby invite <lobbyID> <userID>'");
			}
		} else if (input.startsWith("lobby data ")) {
			long id = Long.parseLong(input.substring("lobby data ".length()), 16);
			if (lobbies.containsKey(id)) {
				SteamID steamIDLobby = lobbies.get(id);
				int count = matchmaking.getLobbyDataCount(steamIDLobby);
				System.out.println("  " + count + " lobby data for " + Long.toHexString(id));
				SteamMatchmakingKeyValuePair pair = new SteamMatchmakingKeyValuePair();
				for (int i = 0; i < count; i++) {
					if (matchmaking.getLobbyDataByIndex(steamIDLobby, i, pair)) {
						System.out.println("  - " + pair.getKey() + " : " + pair.getValue());
					} else {
						System.err.println("Error retrieving lobby data #" + i);
					}
				}
			} else {
				System.err.println("No lobby found: " + Long.toHexString(id));
			}
		} else if (input.startsWith("lobby chat ")) {
			String[] content = input.substring("lobby chat ".length()).split(" ");
			if (content.length == 2) {
				long lobbyID = Long.parseLong(content[0], 16);
				String message = content[1];
				if (lobbies.containsKey(lobbyID)) {
					System.out.println("  sending message \"" + message + "\"");
					matchmaking.sendLobbyChatMsg(lobbies.get(lobbyID), message);
				} else {
					System.err.println("No lobby found: " + Long.toHexString(lobbyID));
				}
			} else {
				System.err.println("Expecting: 'lobby chat <lobbyID> <message>'");
			}
		} else if (input.startsWith("get lobby member data ")) {
			String[] content = input.substring("get lobby member data ".length()).split(" ");
			if (content.length == 3) {
				SteamID lobbyId = SteamID.createFromNativeHandle(Long.parseLong(content[0], 16));
				SteamID userId = SteamID.createFromNativeHandle(Long.parseLong(content[1]));
				String key = content[2];
				String value = matchmaking.getLobbyMemberData(lobbyId, userId, key);
				System.out.println(String.format("Member data for userId:%s in lobbyId:%s for key:%s has value:%s",
					userId.toString(), lobbyId.toString(), key, value));
			} else {
				System.out.println("Expecting: 'get lobby member data <lobbyId> <userId> <key>'");
			}
		} else if (input.startsWith("set lobby member data ")) {
			String[] content = input.substring("set lobby member data ".length()).split(" ");
			if (content.length == 3) {
				SteamID lobbyId = SteamID.createFromNativeHandle(Long.parseLong(content[0], 16));
				String key = content[1];
				String value = content[2];
				matchmaking.setLobbyMemberData(lobbyId, key, value);
			} else {
				System.out.println("Expecting: 'set lobby member data <lobbyId> <key> <value>'");
			}
		}

		friends.processInput(input);
	}

	public static void main(String[] arguments) {
		new SteamMatchmakingTest().clientMain(arguments);
	}

}
