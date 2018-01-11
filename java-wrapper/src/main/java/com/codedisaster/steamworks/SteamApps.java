package com.codedisaster.steamworks;

public class SteamApps extends SteamInterface {

	public SteamApps() {
		super(SteamAPI.getSteamAppsPointer());
	}
	
	public boolean isSubscribed() {
		return isSubscribed(pointer);
	}

	public boolean isLowViolence() {
		return isLowViolence(pointer);
	}

	public boolean isCybercafe() {
		return isCybercafe(pointer);
	}

	public boolean isVACBanned() {
		return isVACBanned(pointer);
	}

	public boolean isSubscribed() {
		return isSubscribed(pointer);
	}

	public boolean isLowViolence() {
		return isLowViolence(pointer);
	}
	
	public boolean isDlcInstalled(int appID) {
		return isDlcInstalled(pointer, appID);
	}

	public int getEarliestPurchaseUnixTime(int appID) {
		return getEarliestPurchaseUnixTime(pointer, appID);
	}

	public boolean isSubscribedFromFreeWeekend() {
		return isSubscribedFromFreeWeekend(pointer);
	}

	public int getDLCCount() {
		return getDLCCount(pointer);
	}

	public void installDLC(int appID) {
		installDLC(pointer, appID);
	}

	public void uninstallDLC(int appID) {
		uninstallDLC(pointer, appID);
	}

	public boolean isCybercafe() {
		return isCybercafe(pointer);
	}

	public boolean isVACBanned() {
		return isVACBanned(pointer);
	}
	
	public String getCurrentGameLanguage() {
		return getCurrentGameLanguage(pointer);
	}

	public String getAvailableGameLanguages() {
		return getAvailableGameLanguages(pointer);
	}

	public boolean isSubscribedApp(int appID) {
		return isSubscribedApp(pointer, appID);
	}

	public boolean isDlcInstalled(int appID) {
		return isDlcInstalled(pointer, appID);
	}

	public int getEarliestPurchaseUnixTime(int appID) {
		return getEarliestPurchaseUnixTime(pointer, appID);
	}

	public boolean isSubscribedFromFreeWeekend() {
		return isSubscribedFromFreeWeekend(pointer);
	}

	public int getDLCCount() {
		return getDLCCount(pointer);
	}

	public void installDLC(int appID) {
		installDLC(pointer, appID);
	}

	public void uninstallDLC(int appID) {
		uninstallDLC(pointer, appID);
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
	
	private static native boolean isSubscribed(long pointer); /*
		ISteamApps* apps = (ISteamApps*) pointer;
		return apps->BIsSubscribed();
	*/

	private static native boolean isLowViolence(long pointer); /*
		ISteamApps* apps = (ISteamApps*) pointer;
		return apps->BIsLowViolence();
	*/

	private static native boolean isCybercafe(long pointer); /*
		ISteamApps* apps = (ISteamApps*) pointer;
		return apps->BIsCybercafe();
	*/

	private static native boolean isVACBanned(long pointer); /*
		ISteamApps* apps = (ISteamApps*) pointer;
		return apps->BIsVACBanned();
	*/

	private static native boolean isSubscribed(long pointer); /*
		ISteamApps* apps = (ISteamApps*) pointer;
		return apps->BIsSubscribed();
	*/

	private static native boolean isLowViolence(long pointer); /*
		ISteamApps* apps = (ISteamApps*) pointer;
		return apps->BIsLowViolence();
	*/

	private static native boolean isCybercafe(long pointer); /*
		ISteamApps* apps = (ISteamApps*) pointer;
		return apps->BIsCybercafe();
	*/

	private static native boolean isVACBanned(long pointer); /*
		ISteamApps* apps = (ISteamApps*) pointer;
		return apps->BIsVACBanned();
	*/
	
	private static native boolean isDlcInstalled(long pointer, int appID); /*
		ISteamApps* apps = (ISteamApps*) pointer;
		return apps->BIsDlcInstalled((AppId_t) appID);
	*/

	private static native int getEarliestPurchaseUnixTime(long pointer, int appID); /*
		ISteamApps* apps = (ISteamApps*) pointer;
		return apps->GetEarliestPurchaseUnixTime((AppId_t) appID);
	*/

	private static native boolean isSubscribedFromFreeWeekend(long pointer); /*
		ISteamApps* apps = (ISteamApps*) pointer;
		return apps->BIsSubscribedFromFreeWeekend();
	*/

	private static native int getDLCCount(long pointer); /*
		ISteamApps* apps = (ISteamApps*) pointer;
		return apps->GetDLCCount();
	*/

	private static native void installDLC(long pointer, int appID); /*
		ISteamApps* apps = (ISteamApps*) pointer;
		apps->InstallDLC((AppId_t) appID);
	*/

	private static native void uninstallDLC(long pointer, int appID); /*
		ISteamApps* apps = (ISteamApps*) pointer;
		apps->UninstallDLC((AppId_t) appID);
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

	private static native boolean isSubscribedApp(long pointer, int appID); /*
		ISteamApps* apps = (ISteamApps*) pointer;
		return apps->BIsSubscribedApp((AppId_t) appID);
	*/

	private static native boolean isDlcInstalled(long pointer, int appID); /*
		ISteamApps* apps = (ISteamApps*) pointer;
		return apps->BIsDlcInstalled((AppId_t) appID);
	*/

	private static native int getEarliestPurchaseUnixTime(long pointer, int appID); /*
		ISteamApps* apps = (ISteamApps*) pointer;
		return apps->GetEarliestPurchaseUnixTime((AppId_t) appID);
	*/

	private static native boolean isSubscribedFromFreeWeekend(long pointer); /*
		ISteamApps* apps = (ISteamApps*) pointer;
		return apps->BIsSubscribedFromFreeWeekend();
	*/

	private static native int getDLCCount(long pointer); /*
		ISteamApps* apps = (ISteamApps*) pointer;
		return apps->GetDLCCount();
	*/

	private static native void installDLC(long pointer, int appID); /*
		ISteamApps* apps = (ISteamApps*) pointer;
		apps->InstallDLC((AppId_t) appID);
	*/

	private static native void uninstallDLC(long pointer, int appID); /*
		ISteamApps* apps = (ISteamApps*) pointer;
		apps->UninstallDLC((AppId_t) appID);
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
