#pragma once

#include <jni.h>
#include <steam_api.h>

jobject convertGameServerItem(JNIEnv* env, const gameserveritem_t& server);
