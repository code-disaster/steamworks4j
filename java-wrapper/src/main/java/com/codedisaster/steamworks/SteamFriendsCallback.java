package com.codedisaster.steamworks;

public interface SteamFriendsCallback {

	default void onSetPersonaNameResponse(boolean success, boolean localSuccess, SteamResult result) {
	}

	default void onPersonaStateChange(SteamID steamID, SteamFriends.PersonaChange change) {
	}

	default void onGameOverlayActivated(boolean active, boolean userInitiated, int appID) {
	}

	default void onGameLobbyJoinRequested(SteamID steamIDLobby, SteamID steamIDFriend) {
	}

	default void onAvatarImageLoaded(SteamID steamID, int image, int width, int height) {
	}

	default void onFriendRichPresenceUpdate(SteamID steamIDFriend, int appID) {
	}

	default void onGameRichPresenceJoinRequested(SteamID steamIDFriend, String connect) {
	}

	default void onGameServerChangeRequested(String server, String password) {
	}

}
