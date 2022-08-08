package com.codedisaster.steamworks;

@SuppressWarnings("unused")
public class SteamApps extends SteamInterface {

	public SteamApps() {

	}

	public boolean isSubscribed() {
		return SteamAppsNative.isSubscribed();
	}

	public boolean isLowViolence() {
		return SteamAppsNative.isLowViolence();
	}

	public boolean isCybercafe() {
		return SteamAppsNative.isCybercafe();
	}

	public boolean isVACBanned() {
		return SteamAppsNative.isVACBanned();
	}

	public String getCurrentGameLanguage() {
		return SteamAppsNative.getCurrentGameLanguage();
	}

	public String getAvailableGameLanguages() {
		return SteamAppsNative.getAvailableGameLanguages();
	}

	public boolean isSubscribedApp(int appID) {
		return SteamAppsNative.isSubscribedApp(appID);
	}

	public boolean isDlcInstalled(int appID) {
		return SteamAppsNative.isDlcInstalled(appID);
	}

	public int getEarliestPurchaseUnixTime(int appID) {
		return SteamAppsNative.getEarliestPurchaseUnixTime(appID);
	}

	public boolean isSubscribedFromFreeWeekend() {
		return SteamAppsNative.isSubscribedFromFreeWeekend();
	}

	public int getDLCCount() {
		return SteamAppsNative.getDLCCount();
	}

	public void installDLC(int appID) {
		SteamAppsNative.installDLC(appID);
	}

	public void uninstallDLC(int appID) {
		SteamAppsNative.uninstallDLC(appID);
	}

	public SteamID getAppOwner() {
		return new SteamID(SteamAppsNative.getAppOwner());
	}

	public int getAppBuildId() {
		return SteamAppsNative.getAppBuildId();
	}

	public boolean setDlcContext(int appID) {
		return SteamAppsNative.setDlcContext(appID);
	}

}
