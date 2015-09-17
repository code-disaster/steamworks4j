package com.codedisaster.steamworks;

public class SteamApps extends SteamInterface {

	public SteamApps() {
		super(SteamAPI.getSteamAppsPointer());
	}

	public boolean isSubscribedApp(long appID) {
		return isSubscribedApp(pointer, appID);
	}
    
    public String getCurrentGameLanguage() {
        return getCurrentGameLanguage(pointer);
    }

	// @off

	/*JNI
		#include <steam_api.h>
	*/

	static private native boolean isSubscribedApp(long pointer, long appID); /*
		ISteamApps* apps = (ISteamApps*) pointer;
		return apps->BIsSubscribedApp((AppId_t) appID);
	*/
    
    static private native String getCurrentGameLanguage(long pointer); /*
        ISteamApps* apps = (ISteamApps*) pointer;
        jstring language = env->NewStringUTF(apps->GetCurrentGameLanguage());
        return language;
    */
}
