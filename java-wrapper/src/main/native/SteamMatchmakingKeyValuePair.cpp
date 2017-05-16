#include "SteamMatchmakingKeyValuePair.h"

int convertKeyValuePairArray(
	JNIEnv* env,
	jobjectArray sourcePairs, int32 sourceSize,
	MatchMakingKeyValuePair_t* targetPairs, int32 targetSize) {

	jclass clazz = env->FindClass("com/codedisaster/steamworks/SteamMatchmakingKeyValuePair");

	int32 size = sourceSize < targetSize ? sourceSize : targetSize;

	for (int32 i = 0; i < size; i++) {
		jobject sourcePair = env->GetObjectArrayElement(sourcePairs, i);
		MatchMakingKeyValuePair_t* targetPair = &targetPairs[i];

		jfieldID field = env->GetFieldID(clazz, "key", "Ljava/lang/String;");
		jstring source = (jstring) env->GetObjectField(sourcePair, field);
		const char* sourceChars = env->GetStringUTFChars(source, nullptr);
		strncpy(targetPair->m_szKey, sourceChars, sizeof(targetPair->m_szKey));
		env->ReleaseStringUTFChars(source, sourceChars);

		field = env->GetFieldID(clazz, "value", "Ljava/lang/String;");
		source = (jstring) env->GetObjectField(sourcePair, field);
		sourceChars = env->GetStringUTFChars(source, nullptr);
		strncpy(targetPair->m_szValue, sourceChars, sizeof(targetPair->m_szValue));
		env->ReleaseStringUTFChars(source, sourceChars);
	}

	return size;
}
