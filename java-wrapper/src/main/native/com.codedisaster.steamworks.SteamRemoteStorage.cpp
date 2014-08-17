#include <com.codedisaster.steamworks.SteamRemoteStorage.h>

//@line:50

		#include <steam_api.h>
	static inline jboolean wrapped_Java_com_codedisaster_steamworks_SteamRemoteStorage_fileWrite
(JNIEnv* env, jclass clazz, jlong pointer, jstring obj_name, jobject obj_data, jint length, char* data, char* name) {

//@line:54

		ISteamRemoteStorage* storage = (ISteamRemoteStorage*) pointer;
		return storage->FileWrite(name, data, length);
	
}

JNIEXPORT jboolean JNICALL Java_com_codedisaster_steamworks_SteamRemoteStorage_fileWrite(JNIEnv* env, jclass clazz, jlong pointer, jstring obj_name, jobject obj_data, jint length) {
	char* data = (char*)(obj_data?env->GetDirectBufferAddress(obj_data):0);
	char* name = (char*)env->GetStringUTFChars(obj_name, 0);

	jboolean JNI_returnValue = wrapped_Java_com_codedisaster_steamworks_SteamRemoteStorage_fileWrite(env, clazz, pointer, obj_name, obj_data, length, data, name);

	env->ReleaseStringUTFChars(obj_name, name);

	return JNI_returnValue;
}

static inline jboolean wrapped_Java_com_codedisaster_steamworks_SteamRemoteStorage_fileRead
(JNIEnv* env, jclass clazz, jlong pointer, jstring obj_name, jobject obj_buffer, jint capacity, char* buffer, char* name) {

//@line:59

		ISteamRemoteStorage* storage = (ISteamRemoteStorage*) pointer;
		return storage->FileRead(name, buffer, capacity);
	
}

JNIEXPORT jboolean JNICALL Java_com_codedisaster_steamworks_SteamRemoteStorage_fileRead(JNIEnv* env, jclass clazz, jlong pointer, jstring obj_name, jobject obj_buffer, jint capacity) {
	char* buffer = (char*)(obj_buffer?env->GetDirectBufferAddress(obj_buffer):0);
	char* name = (char*)env->GetStringUTFChars(obj_name, 0);

	jboolean JNI_returnValue = wrapped_Java_com_codedisaster_steamworks_SteamRemoteStorage_fileRead(env, clazz, pointer, obj_name, obj_buffer, capacity, buffer, name);

	env->ReleaseStringUTFChars(obj_name, name);

	return JNI_returnValue;
}

static inline jboolean wrapped_Java_com_codedisaster_steamworks_SteamRemoteStorage_fileDelete
(JNIEnv* env, jclass clazz, jlong pointer, jstring obj_name, char* name) {

//@line:64

		ISteamRemoteStorage* storage = (ISteamRemoteStorage*) pointer;
		return storage->FileDelete(name);
	
}

JNIEXPORT jboolean JNICALL Java_com_codedisaster_steamworks_SteamRemoteStorage_fileDelete(JNIEnv* env, jclass clazz, jlong pointer, jstring obj_name) {
	char* name = (char*)env->GetStringUTFChars(obj_name, 0);

	jboolean JNI_returnValue = wrapped_Java_com_codedisaster_steamworks_SteamRemoteStorage_fileDelete(env, clazz, pointer, obj_name, name);

	env->ReleaseStringUTFChars(obj_name, name);

	return JNI_returnValue;
}

static inline jboolean wrapped_Java_com_codedisaster_steamworks_SteamRemoteStorage_fileExists
(JNIEnv* env, jclass clazz, jlong pointer, jstring obj_name, char* name) {

//@line:69

		ISteamRemoteStorage* storage = (ISteamRemoteStorage*) pointer;
		return storage->FileExists(name);
	
}

JNIEXPORT jboolean JNICALL Java_com_codedisaster_steamworks_SteamRemoteStorage_fileExists(JNIEnv* env, jclass clazz, jlong pointer, jstring obj_name) {
	char* name = (char*)env->GetStringUTFChars(obj_name, 0);

	jboolean JNI_returnValue = wrapped_Java_com_codedisaster_steamworks_SteamRemoteStorage_fileExists(env, clazz, pointer, obj_name, name);

	env->ReleaseStringUTFChars(obj_name, name);

	return JNI_returnValue;
}

static inline jint wrapped_Java_com_codedisaster_steamworks_SteamRemoteStorage_getFileSize
(JNIEnv* env, jclass clazz, jlong pointer, jstring obj_name, char* name) {

//@line:74

		ISteamRemoteStorage* storage = (ISteamRemoteStorage*) pointer;
		return storage->GetFileSize(name);
	
}

JNIEXPORT jint JNICALL Java_com_codedisaster_steamworks_SteamRemoteStorage_getFileSize(JNIEnv* env, jclass clazz, jlong pointer, jstring obj_name) {
	char* name = (char*)env->GetStringUTFChars(obj_name, 0);

	jint JNI_returnValue = wrapped_Java_com_codedisaster_steamworks_SteamRemoteStorage_getFileSize(env, clazz, pointer, obj_name, name);

	env->ReleaseStringUTFChars(obj_name, name);

	return JNI_returnValue;
}

JNIEXPORT jint JNICALL Java_com_codedisaster_steamworks_SteamRemoteStorage_getFileCount(JNIEnv* env, jclass clazz, jlong pointer) {


//@line:79

		ISteamRemoteStorage* storage = (ISteamRemoteStorage*) pointer;
		return storage->GetFileCount();
	

}

static inline jstring wrapped_Java_com_codedisaster_steamworks_SteamRemoteStorage_getFileNameAndSize
(JNIEnv* env, jclass clazz, jlong pointer, jint index, jintArray obj_sizes, int* sizes) {

//@line:84

		ISteamRemoteStorage* storage = (ISteamRemoteStorage*) pointer;
		jstring name = env->NewStringUTF(storage->GetFileNameAndSize(index, &sizes[0]));
		return name;
	
}

JNIEXPORT jstring JNICALL Java_com_codedisaster_steamworks_SteamRemoteStorage_getFileNameAndSize(JNIEnv* env, jclass clazz, jlong pointer, jint index, jintArray obj_sizes) {
	int* sizes = (int*)env->GetPrimitiveArrayCritical(obj_sizes, 0);

	jstring JNI_returnValue = wrapped_Java_com_codedisaster_steamworks_SteamRemoteStorage_getFileNameAndSize(env, clazz, pointer, index, obj_sizes, sizes);

	env->ReleasePrimitiveArrayCritical(obj_sizes, sizes, 0);

	return JNI_returnValue;
}

