package com.codedisaster.steamworks;

public interface SteamFriendsCallback {

	void onPersonaStateChange(SteamID steamID, SteamFriends.PersonaChange change);

}
