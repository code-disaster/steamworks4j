#include "SteamCallbackAdapter.h"

SteamCallbackAdapter::SteamCallbackAdapter(JNIEnv* env, jobject callback) {
	env->GetJavaVM(&m_vm);
	m_callback = env->NewGlobalRef(callback);
	m_callbackClass = 0;
}

SteamCallbackAdapter::SteamCallbackAdapter(JNIEnv* env, jclass callbackClass) {
	env->GetJavaVM(&m_vm);
	m_callback = 0;
	m_callbackClass = callbackClass;
}

SteamCallbackAdapter::~SteamCallbackAdapter() {
    if (m_callback != 0) {
        JNIEnv* env = attachThread();
        env->DeleteGlobalRef(m_callback);
        detachThread();
    }
}

JNIEnv* SteamCallbackAdapter::attachThread() const {
	JNIEnv* env;
	m_vm->AttachCurrentThread((void**) &env, NULL);
	return env;
}

void SteamCallbackAdapter::detachThread() const {
	m_vm->DetachCurrentThread();
}

void SteamCallbackAdapter::callVoidMethod(JNIEnv* env, const char* method, const char* signature, ...) const {
	jclass clazz = env->GetObjectClass(m_callback);
	if (clazz == 0) {
		//logDebug("JNI: error getting class for callback object\n");
	} else {
		jmethodID methodID = env->GetMethodID(clazz, method, signature);
		if (methodID == 0) {
			//logDebug("JNI: can't find method %s%s\n", method, signature);
		} else {
			va_list args;
			va_start(args, signature);
			env->CallVoidMethodV(m_callback, methodID, args);
			va_end(args);

			jthrowable ex = env->ExceptionOccurred();
			if (ex != NULL) {
				env->ExceptionDescribe();
				env->ExceptionClear();
				env->DeleteLocalRef(ex);
			}
		}
		env->DeleteLocalRef(clazz);
	}
}

void SteamCallbackAdapter::callStaticVoidMethod(JNIEnv* env, const char* method, const char* signature, ...) const {
	jmethodID methodID = env->GetStaticMethodID(m_callbackClass, method, signature);
	if (methodID == 0) {
		//logDebug("JNI: can't find static method %s%s\n", method, signature);
	} else {
		va_list args;
		va_start(args, signature);
		env->CallStaticVoidMethodV(m_callbackClass, methodID, args);
		va_end(args);

		jthrowable ex = env->ExceptionOccurred();
		if (ex != NULL) {
			env->ExceptionDescribe();
			env->ExceptionClear();
			env->DeleteLocalRef(ex);
		}
	}
}
