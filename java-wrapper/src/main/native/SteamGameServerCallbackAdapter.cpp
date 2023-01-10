#include "SteamGameServerCallbackAdapter.h"

SteamGameServerCallbackAdapter::SteamGameServerCallbackAdapter(JNIEnv* env, jobject callback) {
	env->GetJavaVM(&m_vm);
	m_callback = env->NewGlobalRef(callback);
	m_callbackClass = 0;
}

SteamGameServerCallbackAdapter::SteamGameServerCallbackAdapter(JNIEnv* env, jclass callbackClass) {
	env->GetJavaVM(&m_vm);
	m_callback = 0;
	m_callbackClass = callbackClass;
}

SteamGameServerCallbackAdapter::~SteamGameServerCallbackAdapter() {
    if (m_callback != 0) {
        JNIEnv* env;
        bool attached = attachThread(&env);
        env->DeleteGlobalRef(m_callback);
        if (attached) {
            detachThread();
        }
    }
}

void SteamGameServerCallbackAdapter::attach(SteamInvokeCallbackFunction fn) const {
    JNIEnv* env;
    bool attached = attachThread(&env);
    fn(env);
    if (attached) {
        detachThread();
    }
}

bool SteamGameServerCallbackAdapter::attachThread(JNIEnv** env) const {
    jint status = m_vm->GetEnv((void**) env, JNI_VERSION_1_6);
    if (status == JNI_EDETACHED) {
    	m_vm->AttachCurrentThread((void**) env, NULL);
    	return true;
    }
    return false;
}

void SteamGameServerCallbackAdapter::detachThread() const {
	m_vm->DetachCurrentThread();
}

void SteamGameServerCallbackAdapter::callVoidMethod(JNIEnv* env, const char* method, const char* signature, ...) const {
	jclass clazz = env->GetObjectClass(m_callback);
	jclass ex = env->FindClass("com/codedisaster/steamworks/SteamException");
	if (clazz == 0) {
	    env->ThrowNew(ex, "Couldn't retrieve class for callback object.");
	} else {
		jmethodID methodID = env->GetMethodID(clazz, method, signature);
		if (methodID == 0) {
    	    env->ThrowNew(ex, "Couldn't retrieve callback method.");
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

void SteamGameServerCallbackAdapter::callStaticVoidMethod(JNIEnv* env, const char* method, const char* signature, ...) const {
	jmethodID methodID = env->GetStaticMethodID(m_callbackClass, method, signature);
	jclass ex = env->FindClass("com/codedisaster/steamworks/SteamException");
	if (methodID == 0) {
	    env->ThrowNew(ex, "Couldn't retrieve static callback method.");
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
