package com.codedisaster.steamworks;

public interface SteamFriendsCallback {

	void onPersonaStateChange(SteamID steamID, SteamFriends.PersonaChange change);

	void onGameLobbyJoinRequested(SteamID steamIDLobby, SteamID steamIDFriend);
}
