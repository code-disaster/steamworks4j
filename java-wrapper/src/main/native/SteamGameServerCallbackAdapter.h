#pragma once

#include <jni.h>

#ifdef MACOSX

    /**
        Some shady macro magic to "fake" lambda functions for OSX 10.6. The XCode compiler refuses
        to support C++11 functions (because of stdlib=libc++) when using -mmacosx-version-min=10.6.

        This nonsense can be removed if we bump to some reasonable minimum requirement.
    */

    #define invokeCallback(fn) \
        JNIEnv* env; bool attached = attachThread(&env); \
        fn \
        if (attached) detachThread();

    // dummy function pointer type to please the compiler, this workaround doesn't call attach()
    typedef void (* SteamInvokeCallbackFunction) (JNIEnv* env);

#else

    #include <functional>
    typedef std::function<void (JNIEnv* env)> SteamInvokeCallbackFunction;
    #define invokeCallback(fn) attach([&] (JNIEnv* env) fn)

#endif

class SteamGameServerCallbackAdapter {

public:
    virtual ~SteamGameServerCallbackAdapter();

protected:
    SteamGameServerCallbackAdapter(JNIEnv* env, jobject callback);
    SteamGameServerCallbackAdapter(JNIEnv* env, jclass callbackClass);

    void attach(SteamInvokeCallbackFunction fn) const;

	void callVoidMethod(JNIEnv* env, const char* method, const char* signature, ...) const;
	void callStaticVoidMethod(JNIEnv* env, const char* method, const char* signature, ...) const;

#ifndef MACOSX
private:
#endif
	bool attachThread(JNIEnv** env) const;
	void detachThread() const;

private:
    JavaVM* m_vm;
    jobject m_callback;
	jclass m_callbackClass;

};
