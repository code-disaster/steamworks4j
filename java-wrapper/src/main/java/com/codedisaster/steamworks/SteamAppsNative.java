package com.codedisaster.steamworks;

final class SteamAppsNative {

	// @off

	/*JNI
		#include <steam_api.h>
	*/

	static native boolean isSubscribed(); /*
		return SteamApps()->BIsSubscribed();
	*/

	static native boolean isLowViolence(); /*
		return SteamApps()->BIsLowViolence();
	*/

	static native boolean isCybercafe(); /*
		return SteamApps()->BIsCybercafe();
	*/

	static native boolean isVACBanned(); /*
		return SteamApps()->BIsVACBanned();
	*/

	static native String getCurrentGameLanguage(); /*
        return env->NewStringUTF(SteamApps()->GetCurrentGameLanguage());
	*/

	static native String getAvailableGameLanguages(); /*
        return env->NewStringUTF(SteamApps()->GetAvailableGameLanguages());
	*/

	static native boolean isSubscribedApp(int appID); /*
		return SteamApps()->BIsSubscribedApp((AppId_t) appID);
	*/

	static native boolean isDlcInstalled(int appID); /*
		return SteamApps()->BIsDlcInstalled((AppId_t) appID);
	*/

	static native int getEarliestPurchaseUnixTime(int appID); /*
		return SteamApps()->GetEarliestPurchaseUnixTime((AppId_t) appID);
	*/

	static native boolean isSubscribedFromFreeWeekend(); /*
		return SteamApps()->BIsSubscribedFromFreeWeekend();
	*/

	static native int getDLCCount(); /*
		return SteamApps()->GetDLCCount();
	*/

	static native void installDLC(int appID); /*
		SteamApps()->InstallDLC((AppId_t) appID);
	*/

	static native void uninstallDLC(int appID); /*
		SteamApps()->UninstallDLC((AppId_t) appID);
    */

	static native long getAppOwner(); /*
		CSteamID steamID = SteamApps()->GetAppOwner();
		return (int64) steamID.ConvertToUint64();
	*/

	static native int getAppBuildId(); /*
        return SteamApps()->GetAppBuildId();
	*/

	static native boolean setDlcContext(int appID); /*
		return SteamApps()->SetDlcContext((AppId_t) appID);
	*/

}
