#pragma once

#include <jni.h>
#include <steam_api.h>

int convertKeyValuePairArray(
	JNIEnv* env,
	jobjectArray sourcePairs, int sourceSize,
	MatchMakingKeyValuePair_t* targetPairs, MatchMakingKeyValuePair_t** targetPairsTable, int targetSize);
