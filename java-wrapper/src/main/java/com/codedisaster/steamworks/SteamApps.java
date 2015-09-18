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

	public String getAvailableGameLanguages() {
		return getAvailableGameLanguages(pointer);
	}

	public SteamID getAppOwner() {
		return new SteamID(getAppOwner(pointer));
	}

	public int getAppBuildId() {
		return getAppBuildId(pointer);
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

	static private native String getAvailableGameLanguages(long pointer); /*
        ISteamApps* apps = (ISteamApps*) pointer;
        jstring language = env->NewStringUTF(apps->GetAvailableGameLanguages());
        return language;
	*/

	static private native long getAppOwner(long pointer); /*
        ISteamApps* apps = (ISteamApps*) pointer;
		CSteamID steamID = apps->GetAppOwner();
		return (int64) steamID.ConvertToUint64();
	*/

	static private native int getAppBuildId(long pointer); /*
        ISteamApps* apps = (ISteamApps*) pointer;
        return apps->GetAppBuildId();
	*/

}
