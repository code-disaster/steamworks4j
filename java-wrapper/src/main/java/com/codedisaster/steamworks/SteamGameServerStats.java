package com.codedisaster.steamworks;

/**
 *
 * @author Francisco "Franz" Bischoff
 */
public class SteamGameServerStats extends SteamInterface {

    public SteamGameServerStats(long pointer, SteamGameServerStatsCallback callback) {
        super(pointer);
        registerCallback(new SteamGameServerStatsCallbackAdapter(callback));
    }

    static void dispose() {
        registerCallback(null);
    }

    // @off

    /*JNI
     #include <steam_gameserver.h>
     #include "SteamGameServerStatsCallback.h"

     static SteamGameServerStatsCallback* callback = NULL;
     */
    static private native boolean registerCallback(SteamGameServerStatsCallbackAdapter javaCallback); /*
     if (callback != NULL) {
     delete callback;
     callback = NULL;
     }

     if (javaCallback != NULL) {
     callback = new SteamGameServerStatsCallback(env, javaCallback);
     }

     return callback != NULL;
     */


    static public SteamAPICall requestUserStats(SteamID steamIDUser) {
        return new SteamAPICall(nativeRequestUserStats(steamIDUser.handle));
    }

    static private native long nativeRequestUserStats(long steamIDUser); /*
     return SteamGameServerStats()->RequestUserStats((uint64) steamIDUser);
     */


    static public boolean getUserStat(SteamID steamIDUser, String pchName, Integer pData) {
        return nativeGetUserStat(steamIDUser.handle, pchName, pData);
    }

    static private native boolean nativeGetUserStat(long steamIDUser, String pchName, Integer pData); /*
     return SteamGameServerStats()->GetUserStat((uint64)steamIDUser, pchName, (int32 *)pData);
     */


    static public boolean getUserStat(SteamID steamIDUser, String pchName, Float pData) {
        return nativeGetUserStat2(steamIDUser.handle, pchName, pData);
    }

    static private native boolean nativeGetUserStat2(long steamIDUser, String pchName, Float pData); /*
     return SteamGameServerStats()->GetUserStat((uint64)steamIDUser, pchName, (float *)pData);
     */


    static public boolean getUserAchievement(SteamID steamIDUser, String pchName, Boolean pbAchieved) {
        return nativeGetUserAchievement(steamIDUser.handle, pchName, pbAchieved);
    }

    static private native boolean nativeGetUserAchievement(long steamIDUser, String pchName, Boolean pData); /*
     return SteamGameServerStats()->GetUserAchievement((uint64)steamIDUser, pchName, (bool *)pData);
     */


    static public boolean setUserStat(SteamID steamIDUser, String pchName, int nData) {
        return nativeSetUserStat(steamIDUser.handle, pchName, nData);
    }

    static private native boolean nativeSetUserStat(long steamIDUser, String pchName, int pData); /*
     return SteamGameServerStats()->SetUserStat((uint64)steamIDUser, pchName, pData);
     */


    static public boolean setUserStat(SteamID steamIDUser, String pchName, float nData) {
        return nativeSetUserStat2(steamIDUser.handle, pchName, nData);
    }

    static private native boolean nativeSetUserStat2(long steamIDUser, String pchName, float pData); /*
     return SteamGameServerStats()->SetUserStat((uint64)steamIDUser, pchName, pData);
     */


    static public boolean updateUserAvgRateStat(SteamID steamIDUser, String pchName, float flCountThisSession, double dSessionLength) {
        return nativeUpdateUserAvgRateStat(steamIDUser.handle, pchName, flCountThisSession, dSessionLength);
    }

    static private native boolean nativeUpdateUserAvgRateStat(long steamIDUser, String pchName, float flCountThisSession, double dSessionLength); /*
     return SteamGameServerStats()->UpdateUserAvgRateStat((uint64)steamIDUser, pchName, flCountThisSession, dSessionLength);
     */


    static public boolean setUserAchievement(SteamID steamIDUser, String pchName) {
        return nativeSetUserAchievement(steamIDUser.handle, pchName);
    }

    static private native boolean nativeSetUserAchievement(long steamIDUser, String pchName); /*
     return SteamGameServerStats()->SetUserAchievement((uint64)steamIDUser, pchName);
     */


    static public boolean clearUserAchievement(SteamID steamIDUser, String pchName) {
        return nativeClearUserAchievement(steamIDUser.handle, pchName);
    }

    static private native boolean nativeClearUserAchievement(long steamIDUser, String pchName); /*
     return SteamGameServerStats()->ClearUserAchievement((uint64)steamIDUser, pchName);
     */


    static public SteamAPICall storeUserStats(SteamID steamIDUser) {
        return new SteamAPICall(nativeStoreUserStats(steamIDUser.handle));
    }

    static private native long nativeStoreUserStats(long steamIDUser); /*
     return SteamGameServerStats()->StoreUserStats((uint64) steamIDUser);
     */

}
