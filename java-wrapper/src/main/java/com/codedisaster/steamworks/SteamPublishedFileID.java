package com.codedisaster.steamworks;

public class SteamPublishedFileID extends SteamNativeHandle {

	public SteamPublishedFileID(long id) {
		super(id);
	}
	
	public long getWorkshopItemId(){
		return this.handle;	
	}
	
}
