#pragma once

#include <jni.h>
#include <steam_api.h>

void convertGameServerItem(jobject item, JNIEnv* env, const gameserveritem_t& server);
jobject createGameServerItem(JNIEnv* env, const gameserveritem_t& server);
