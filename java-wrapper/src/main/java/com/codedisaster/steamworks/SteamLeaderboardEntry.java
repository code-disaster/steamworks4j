package com.codedisaster.steamworks;

public class SteamLeaderboardEntry {

	/* Contains only a subset of LeaderboardEntry_t. */

	long steamIDUser;
	int globalRank;
	int score;

	public SteamID getSteamIDUser() {
		return new SteamID(steamIDUser);
	}

	public int getGlobalRank() {
		return globalRank;
	}

	public int getScore() {
		return score;
	}

}
