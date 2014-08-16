#pragma once

#include <jni.h>

class SteamCallbackAdapter {

protected:
    SteamCallbackAdapter(JNIEnv* env, jobject callback);
    SteamCallbackAdapter(JNIEnv* env, jclass callbackClass);

    virtual ~SteamCallbackAdapter();

	JNIEnv* attachThread() const;
	void detachThread() const;

	void callVoidMethod(JNIEnv* env, const char* method, const char* signature, ...) const;
	void callStaticVoidMethod(JNIEnv* env, const char* method, const char* signature, ...) const;

    JavaVM* m_vm;
    jobject m_callback;
	jclass m_callbackClass;

};
