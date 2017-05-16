package com.codedisaster.steamworks;

public class SteamMatchmakingGameServerItem {

	SteamMatchmakingServerNetAdr netAdr = new SteamMatchmakingServerNetAdr();
	int ping;
	boolean hadSuccessfulResponse;
	boolean doNotRefresh;
	String gameDir;
	String map;
	String gameDescription;
	int appID;
	int players;
	int maxPlayers;
	int botPlayers;
	boolean password;
	boolean secure;
	int timeLastPlayed;
	int	serverVersion;

	String serverName;

	String gameTags;

	long steamID;

	public SteamMatchmakingGameServerItem() {

	}

	public SteamMatchmakingServerNetAdr getNetAdr() {
		return netAdr;
	}

	public int getPing() {
		return ping;
	}

	public boolean hadSuccessfulResponse() {
		return hadSuccessfulResponse;
	}

	public boolean doNotRefresh() {
		return doNotRefresh;
	}

	public String getGameDir() {
		return gameDir;
	}

	public String getMap() {
		return map;
	}

	public String getGameDescription() {
		return gameDescription;
	}

	public int getAppID() {
		return appID;
	}

	public int getPlayers() {
		return players;
	}

	public int getMaxPlayers() {
		return maxPlayers;
	}

	public int getBotPlayers() {
		return botPlayers;
	}

	public boolean hasPassword() {
		return password;
	}

	public boolean isSecure() {
		return secure;
	}

	public int getTimeLastPlayed() {
		return timeLastPlayed;
	}

	public int getServerVersion() {
		return serverVersion;
	}

	public String getServerName() {
		return serverName;
	}

	public String getGameTags() {
		return gameTags;
	}

	public SteamID getSteamID() {
		return new SteamID(steamID);
	}

}
