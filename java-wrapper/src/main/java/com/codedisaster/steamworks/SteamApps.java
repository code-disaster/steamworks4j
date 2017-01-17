package com.codedisaster.steamworks;

public class SteamApps extends SteamInterface {

	public SteamApps() {
		super(SteamAPI.getSteamAppsPointer());
	}

	public boolean isSubscribedApp(int appID) {
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

	private static native boolean isSubscribedApp(long pointer, int appID); /*
		ISteamApps* apps = (ISteamApps*) pointer;
		return apps->BIsSubscribedApp((AppId_t) appID);
	*/

	private static native String getCurrentGameLanguage(long pointer); /*
        ISteamApps* apps = (ISteamApps*) pointer;
        jstring language = env->NewStringUTF(apps->GetCurrentGameLanguage());
        return language;
    */

	private static native String getAvailableGameLanguages(long pointer); /*
        ISteamApps* apps = (ISteamApps*) pointer;
        jstring language = env->NewStringUTF(apps->GetAvailableGameLanguages());
        return language;
	*/

	private static native long getAppOwner(long pointer); /*
        ISteamApps* apps = (ISteamApps*) pointer;
		CSteamID steamID = apps->GetAppOwner();
		return (int64) steamID.ConvertToUint64();
	*/

	private static native int getAppBuildId(long pointer); /*
        ISteamApps* apps = (ISteamApps*) pointer;
        return apps->GetAppBuildId();
	*/

}
