#include <com.codedisaster.steamworks.SteamUserStats.h>

//@line:80

		#include <steam_api.h>
		#include "SteamUserStatsCallback.h"

		static SteamUserStatsCallback* callback = NULL;
	JNIEXPORT jboolean JNICALL Java_com_codedisaster_steamworks_SteamUserStats_registerCallback(JNIEnv* env, jclass clazz, jobject javaCallback) {


//@line:87

		if (callback != NULL) {
			delete callback;
			callback = NULL;
		}

		if (javaCallback != NULL) {
			callback = new SteamUserStatsCallback(env, javaCallback);
		}

		return callback != NULL;
	

}

JNIEXPORT jboolean JNICALL Java_com_codedisaster_steamworks_SteamUserStats_requestCurrentStats(JNIEnv* env, jclass clazz, jlong pointer) {


//@line:100

		ISteamUserStats* stats = (ISteamUserStats*) pointer;
		return stats->RequestCurrentStats();
	

}

static inline jboolean wrapped_Java_com_codedisaster_steamworks_SteamUserStats_getStat__JLjava_lang_String_2_3I
(JNIEnv* env, jclass clazz, jlong pointer, jstring obj_name, jintArray obj_value, char* name, int* value) {

//@line:105

		ISteamUserStats* stats = (ISteamUserStats*) pointer;
		return stats->GetStat(name, &((int32*) value)[0]);
	
}

JNIEXPORT jboolean JNICALL Java_com_codedisaster_steamworks_SteamUserStats_getStat__JLjava_lang_String_2_3I(JNIEnv* env, jclass clazz, jlong pointer, jstring obj_name, jintArray obj_value) {
	char* name = (char*)env->GetStringUTFChars(obj_name, 0);
	int* value = (int*)env->GetPrimitiveArrayCritical(obj_value, 0);

	jboolean JNI_returnValue = wrapped_Java_com_codedisaster_steamworks_SteamUserStats_getStat__JLjava_lang_String_2_3I(env, clazz, pointer, obj_name, obj_value, name, value);

	env->ReleasePrimitiveArrayCritical(obj_value, value, 0);
	env->ReleaseStringUTFChars(obj_name, name);

	return JNI_returnValue;
}

static inline jboolean wrapped_Java_com_codedisaster_steamworks_SteamUserStats_setStat__JLjava_lang_String_2I
(JNIEnv* env, jclass clazz, jlong pointer, jstring obj_name, jint value, char* name) {

//@line:110

		ISteamUserStats* stats = (ISteamUserStats*) pointer;
		return stats->SetStat(name, (int32) value);
	
}

JNIEXPORT jboolean JNICALL Java_com_codedisaster_steamworks_SteamUserStats_setStat__JLjava_lang_String_2I(JNIEnv* env, jclass clazz, jlong pointer, jstring obj_name, jint value) {
	char* name = (char*)env->GetStringUTFChars(obj_name, 0);

	jboolean JNI_returnValue = wrapped_Java_com_codedisaster_steamworks_SteamUserStats_setStat__JLjava_lang_String_2I(env, clazz, pointer, obj_name, value, name);

	env->ReleaseStringUTFChars(obj_name, name);

	return JNI_returnValue;
}

static inline jboolean wrapped_Java_com_codedisaster_steamworks_SteamUserStats_getStat__JLjava_lang_String_2_3F
(JNIEnv* env, jclass clazz, jlong pointer, jstring obj_name, jfloatArray obj_value, char* name, float* value) {

//@line:115

		ISteamUserStats* stats = (ISteamUserStats*) pointer;
		return stats->GetStat(name, &value[0]);
	
}

JNIEXPORT jboolean JNICALL Java_com_codedisaster_steamworks_SteamUserStats_getStat__JLjava_lang_String_2_3F(JNIEnv* env, jclass clazz, jlong pointer, jstring obj_name, jfloatArray obj_value) {
	char* name = (char*)env->GetStringUTFChars(obj_name, 0);
	float* value = (float*)env->GetPrimitiveArrayCritical(obj_value, 0);

	jboolean JNI_returnValue = wrapped_Java_com_codedisaster_steamworks_SteamUserStats_getStat__JLjava_lang_String_2_3F(env, clazz, pointer, obj_name, obj_value, name, value);

	env->ReleasePrimitiveArrayCritical(obj_value, value, 0);
	env->ReleaseStringUTFChars(obj_name, name);

	return JNI_returnValue;
}

static inline jboolean wrapped_Java_com_codedisaster_steamworks_SteamUserStats_setStat__JLjava_lang_String_2F
(JNIEnv* env, jclass clazz, jlong pointer, jstring obj_name, jfloat value, char* name) {

//@line:120

		ISteamUserStats* stats = (ISteamUserStats*) pointer;
		return stats->SetStat(name, value);
	
}

JNIEXPORT jboolean JNICALL Java_com_codedisaster_steamworks_SteamUserStats_setStat__JLjava_lang_String_2F(JNIEnv* env, jclass clazz, jlong pointer, jstring obj_name, jfloat value) {
	char* name = (char*)env->GetStringUTFChars(obj_name, 0);

	jboolean JNI_returnValue = wrapped_Java_com_codedisaster_steamworks_SteamUserStats_setStat__JLjava_lang_String_2F(env, clazz, pointer, obj_name, value, name);

	env->ReleaseStringUTFChars(obj_name, name);

	return JNI_returnValue;
}

static inline jboolean wrapped_Java_com_codedisaster_steamworks_SteamUserStats_getAchievement
(JNIEnv* env, jclass clazz, jlong pointer, jstring obj_name, jbooleanArray obj_achieved, char* name, bool* achieved) {

//@line:125

		ISteamUserStats* stats = (ISteamUserStats*) pointer;
		return stats->GetAchievement(name, &achieved[0]);
	
}

JNIEXPORT jboolean JNICALL Java_com_codedisaster_steamworks_SteamUserStats_getAchievement(JNIEnv* env, jclass clazz, jlong pointer, jstring obj_name, jbooleanArray obj_achieved) {
	char* name = (char*)env->GetStringUTFChars(obj_name, 0);
	bool* achieved = (bool*)env->GetPrimitiveArrayCritical(obj_achieved, 0);

	jboolean JNI_returnValue = wrapped_Java_com_codedisaster_steamworks_SteamUserStats_getAchievement(env, clazz, pointer, obj_name, obj_achieved, name, achieved);

	env->ReleasePrimitiveArrayCritical(obj_achieved, achieved, 0);
	env->ReleaseStringUTFChars(obj_name, name);

	return JNI_returnValue;
}

static inline jboolean wrapped_Java_com_codedisaster_steamworks_SteamUserStats_setAchievement
(JNIEnv* env, jclass clazz, jlong pointer, jstring obj_name, char* name) {

//@line:130

		ISteamUserStats* stats = (ISteamUserStats*) pointer;
		return stats->SetAchievement(name);
	
}

JNIEXPORT jboolean JNICALL Java_com_codedisaster_steamworks_SteamUserStats_setAchievement(JNIEnv* env, jclass clazz, jlong pointer, jstring obj_name) {
	char* name = (char*)env->GetStringUTFChars(obj_name, 0);

	jboolean JNI_returnValue = wrapped_Java_com_codedisaster_steamworks_SteamUserStats_setAchievement(env, clazz, pointer, obj_name, name);

	env->ReleaseStringUTFChars(obj_name, name);

	return JNI_returnValue;
}

static inline jboolean wrapped_Java_com_codedisaster_steamworks_SteamUserStats_clearAchievement
(JNIEnv* env, jclass clazz, jlong pointer, jstring obj_name, char* name) {

//@line:135

		ISteamUserStats* stats = (ISteamUserStats*) pointer;
		return stats->ClearAchievement(name);
	
}

JNIEXPORT jboolean JNICALL Java_com_codedisaster_steamworks_SteamUserStats_clearAchievement(JNIEnv* env, jclass clazz, jlong pointer, jstring obj_name) {
	char* name = (char*)env->GetStringUTFChars(obj_name, 0);

	jboolean JNI_returnValue = wrapped_Java_com_codedisaster_steamworks_SteamUserStats_clearAchievement(env, clazz, pointer, obj_name, name);

	env->ReleaseStringUTFChars(obj_name, name);

	return JNI_returnValue;
}

JNIEXPORT jboolean JNICALL Java_com_codedisaster_steamworks_SteamUserStats_storeStats(JNIEnv* env, jclass clazz, jlong pointer) {


//@line:140

		ISteamUserStats* stats = (ISteamUserStats*) pointer;
		return stats->StoreStats();
	

}

static inline jboolean wrapped_Java_com_codedisaster_steamworks_SteamUserStats_indicateAchievementProgress
(JNIEnv* env, jclass clazz, jlong pointer, jstring obj_name, jint curProgress, jint maxProgress, char* name) {

//@line:146

		ISteamUserStats* stats = (ISteamUserStats*) pointer;
		return stats->IndicateAchievementProgress(name, curProgress, maxProgress);
	
}

JNIEXPORT jboolean JNICALL Java_com_codedisaster_steamworks_SteamUserStats_indicateAchievementProgress(JNIEnv* env, jclass clazz, jlong pointer, jstring obj_name, jint curProgress, jint maxProgress) {
	char* name = (char*)env->GetStringUTFChars(obj_name, 0);

	jboolean JNI_returnValue = wrapped_Java_com_codedisaster_steamworks_SteamUserStats_indicateAchievementProgress(env, clazz, pointer, obj_name, curProgress, maxProgress, name);

	env->ReleaseStringUTFChars(obj_name, name);

	return JNI_returnValue;
}

JNIEXPORT jint JNICALL Java_com_codedisaster_steamworks_SteamUserStats_getNumAchievements(JNIEnv* env, jclass clazz, jlong pointer) {


//@line:151

		ISteamUserStats* stats = (ISteamUserStats*) pointer;
		return stats->GetNumAchievements();
	

}

JNIEXPORT jstring JNICALL Java_com_codedisaster_steamworks_SteamUserStats_getAchievementName(JNIEnv* env, jclass clazz, jlong pointer, jint index) {


//@line:156

		ISteamUserStats* stats = (ISteamUserStats*) pointer;
		jstring name = env->NewStringUTF(stats->GetAchievementName(index));
		return name;
	

}

JNIEXPORT jboolean JNICALL Java_com_codedisaster_steamworks_SteamUserStats_resetAllStats(JNIEnv* env, jclass clazz, jlong pointer, jboolean achievementsToo) {


//@line:162

		ISteamUserStats* stats = (ISteamUserStats*) pointer;
		return stats->ResetAllStats(achievementsToo);
	

}

