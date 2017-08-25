package com.codedisaster.steamworks;

import java.nio.ByteBuffer;
import java.util.List;

import static com.codedisaster.steamworks.SteamTypeUtils.UINT32_SIZE;
import static com.codedisaster.steamworks.SteamTypeUtils.unwrapUint32;

public class SteamAppList extends SteamInterface {

	/**
	 * Sentinel returned from {@link #getAppInstallDir(long, int, Object, Object)} if the app lacks permission.
	 */
	private static final String QUERY_INSTALL_DIR_NO_PERMISSION = "(NO_PERMISSION)";

	/**
	 * Sentinel returned from {@link #getAppInstallDir(long, int, Object, Object)} if an error occurred.
	 */
	private static final String QUERY_INSTALL_DIR_ERROR = "(ERROR)";

	public SteamAppList() {
		super(SteamAPI.getSteamAppListPointer());
	}

	/**
	 * Looks up installation directory for a given app ID. Please note that {@code appId} value higher than
	 * {@link SteamTypeUtils#UINT32_MAX_VALUE} or lower than {@link SteamTypeUtils#UINT32_MIN_VALUE} is considered
	 * invalid and will cause an exception to be thrown.
	 *
	 * @param appId app ID for which to lookup the installation directory
	 * @return path where the app is/will be installed
	 * @throws SteamException if current app does not have permission or user does not own the app or app id does not
	 * exist
	 */
	public String getAppInstallDir(long appId) throws SteamException {
		final String path = getAppInstallDir(pointer, unwrapUint32(appId), QUERY_INSTALL_DIR_NO_PERMISSION,
				QUERY_INSTALL_DIR_ERROR);
		if (path == QUERY_INSTALL_DIR_NO_PERMISSION) {
			throw new SteamException("Does not have permission to call getAppInstallDir");
		} else if (path == QUERY_INSTALL_DIR_ERROR) {
			throw new SteamException("Could not get install dir for appId " + appId);
		} else {
			return path;
		}
	}

	/**
	 * @return number of currently installed apps
	 */
	public int getNumInstalledApps() {
		return getNumInstalledApps(pointer);
	}

	/**
	 * Returns list of app IDs as {@code long}s.
	 * <p>
	 * Even though app ID is originally declared as {@code unsigned 32 bit wide int}, since Java does not have unsigned
	 * types, the value is upcasted to the nearest larger type capable of holding the value verbatim - {@code long}.
	 *
	 * @param maxAppIdCount fetch at most these many app IDs
	 * @return list of app IDs
	 */
	public List<Long> getInstalledApps(int maxAppIdCount) {
		final InstalledAppsList list = new InstalledAppsList(maxAppIdCount);
		final int count = getInstalledApps(pointer, list.buffer, maxAppIdCount);
		list.buffer.limit(count * UINT32_SIZE);
		return list;
	}

	// @off

	/*JNI
		#include <steam_api.h>
	*/

	private static native String getAppInstallDir(long pointer, int appId, Object noPermSentinel, Object errSentinel); /*
		// TODO: should be platform's MAX_PATH
		char path[1024];
		int len;

		ISteamAppList* appList = (ISteamAppList*) pointer;

		len = appList->GetAppInstallDir(appId, path, sizeof(path));

		if (len == -1) { // means we do not have permission for the call
			return (jstring) noPermSentinel;
		} else if (len == 0) { // means something went wrong (appId does not exist etc.)
			return (jstring) errSentinel;
		} else {
			return env->NewStringUTF(path);
		}
	*/

	private static native int getInstalledApps(long pointer, ByteBuffer bb, int size); /*
		ISteamAppList* appList = (ISteamAppList*) pointer;
		// jnigen resolves DirectByteBuffer address automatically
		return appList->GetInstalledApps((AppId_t *) bb, size);
	*/

	private static native int getNumInstalledApps(long pointer); /*
		ISteamAppList* appList = (ISteamAppList*) pointer;
		return appList->GetNumInstalledApps();
	*/

}

