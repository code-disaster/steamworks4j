package com.codedisaster.steamworks;

import java.io.*;

/**
 *
 * @author Francisco "Franz" Bischoff
 */
public class SteamGameServerAPI {

    public enum EServerMode {

        eServerModeInvalid, // DO NOT USE          
        eServerModeNoAuthentication, // Don't authenticate user logins and don't list on the server list   
        eServerModeAuthentication, // Authenticate users, list on the server list, don't run VAC on clients that connect   
        eServerModeAuthenticationAndSecure, // Authenticate users, list on the server list and VAC protect clients
    }

    static private boolean isRunning = false;

    static public boolean init(int unIP, short usSteamPort, short usGamePort, short usQueryPort,
            EServerMode eServerMode, String pchVersionString) {
        isRunning = loadLibraries() && nativeInit(unIP, usSteamPort, usGamePort, usQueryPort, eServerMode.ordinal(), pchVersionString);
        return isRunning;
    }

    static public void shutdown() {
        SteamGameServer.dispose();
//        SteamGameServerUtils.dispose();
//        SteamGameServerNetworking.dispose(); 
//        SteamGameServerStats.dispose(); 
//        SteamGameServerHTTP.dispose();
//        SteamGameServerInventory.dispose();

        nativeShutdown();
        isRunning = false;
    }

    static private boolean loadLibraries() {

        String libraryPath = System.getProperty("java.io.tmpdir") + "/steamworks4j/steamworks4j-natives.jar";

        File libraryDirectory = new File(libraryPath).getParentFile();
        if (!libraryDirectory.exists()) {
            if (!libraryDirectory.mkdirs()) {
                return false;
            }
        }

        try {

            InputStream input = SteamAPI.class.getResourceAsStream("/steamworks4j-natives.jar");
            FileOutputStream output = new FileOutputStream(new File(libraryPath));

            byte[] cache = new byte[4096];
            int length;

            do {
                length = input.read(cache);
                if (length > 0) {
                    output.write(cache, 0, length);
                }
            } while (length > 0);

            input.close();
            output.close();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        SteamSharedLibraryLoader loader = new SteamSharedLibraryLoader(libraryPath);

        loader.load("steam_api");
        loader.load("steamworks4j");

        return true;
    }

    static public SteamID getSteamID() {
        SteamID id = new SteamID(nativeGetSteamID());
        return id;
    }

    // @off

    /*JNI
     #include <steam_gameserver.h>

     static JavaVM* staticVM = 0;
     */
    static private native boolean nativeInit(int unIP, short usSteamPort, short usGamePort, short usQueryPort,
            int eServerMode, String pchVersionString); /*
     if (env->GetJavaVM(&staticVM) != 0) {
     return false;
     }
   
     return SteamGameServer_Init(unIP, usSteamPort, usGamePort, usQueryPort, static_cast<EServerMode>(eServerMode), pchVersionString);
     */


    static private native void nativeShutdown(); /*
     SteamGameServer_Shutdown();
     */


    static public native void runCallbacks(); /*
     SteamGameServer_RunCallbacks();
     */


    static public native boolean bSecure(); /*
     return SteamGameServer_BSecure();
     */


    static private native long nativeGetSteamID(); /*
     return SteamGameServer_GetSteamID();
     */


    static public native int getHSteamPipe(); /*
     return SteamGameServer_GetHSteamPipe();
     */

}
