#include <com.codedisaster.steamworks.SteamAPI.h>

//@line:30

		#include <steam_api.h>

		static JavaVM* staticVM = 0;
	JNIEXPORT jboolean JNICALL Java_com_codedisaster_steamworks_SteamAPI_nativeInit(JNIEnv* env, jclass clazz) {


//@line:36

		if (env->GetJavaVM(&staticVM) != 0) {
			return false;
		}

		return SteamAPI_Init();
	

}

JNIEXPORT void JNICALL Java_com_codedisaster_steamworks_SteamAPI_nativeShutdown(JNIEnv* env, jclass clazz) {


//@line:44

		SteamAPI_Shutdown();
	

}

JNIEXPORT void JNICALL Java_com_codedisaster_steamworks_SteamAPI_runCallbacks(JNIEnv* env, jclass clazz) {


//@line:48

		SteamAPI_RunCallbacks();
	

}

JNIEXPORT jboolean JNICALL Java_com_codedisaster_steamworks_SteamAPI_isSteamRunning(JNIEnv* env, jclass clazz) {


//@line:52

		return SteamAPI_IsSteamRunning();
	

}

JNIEXPORT jlong JNICALL Java_com_codedisaster_steamworks_SteamAPI_getSteamUserStatsPointer(JNIEnv* env, jclass clazz) {


//@line:56

		return (long) SteamUserStats();
	

}

JNIEXPORT jlong JNICALL Java_com_codedisaster_steamworks_SteamAPI_getSteamRemoteStoragePointer(JNIEnv* env, jclass clazz) {


//@line:60

		return (long) SteamRemoteStorage();
	

}

