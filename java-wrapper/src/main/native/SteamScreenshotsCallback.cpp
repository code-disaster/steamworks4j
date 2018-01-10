#include "SteamScreenshotsCallback.h"
#include "SteamUtils.h"

SteamScreenshotsCallback::SteamScreenshotsCallback(JNIEnv* env, jobject callback)
    : SteamCallbackAdapter(env, callback)
	, m_CallbackScreenshotReady(this, &SteamScreenshotsCallback::onScreenshotReady)
	, m_CallbackScreenshotRequested(this, &SteamScreenshotsCallback::onScreenshotRequested) {

}

SteamScreenshotsCallback::~SteamScreenshotsCallback() {

}

void SteamScreenshotsCallback::onScreenshotReady(ScreenshotReady_t* callback) {
    invokeCallback({
        callVoidMethod(env, "onScreenshotReady", "(II)V",
            (jint) callback->m_hLocal, (jint) callback->m_eResult);
    });
}

void SteamScreenshotsCallback::onScreenshotRequested(ScreenshotRequested_t* callback) {
    invokeCallback({
        callVoidMethod(env, "onScreenshotRequested", "()V");
    });
}
