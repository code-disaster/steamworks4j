#include "SteamInputCallback.h"

static SteamInputCallback* s_actionEventInstance = NULL;
static bool s_actionEventCallbackRegistered = false;

static void SteamInputActionEventCallback(SteamInputActionEvent_t* event) {
	if (s_actionEventInstance != NULL) {
		s_actionEventInstance->onActionEvent(event);
	}
}

SteamInputCallback::SteamInputCallback(JNIEnv* env, jobject callback)
	: SteamCallbackAdapter(env, callback)
	, m_CallbackDeviceConnected(this, &SteamInputCallback::onDeviceConnected)
	, m_CallbackDeviceDisconnected(this, &SteamInputCallback::onDeviceDisconnected)
	, m_CallbackConfigurationLoaded(this, &SteamInputCallback::onConfigurationLoaded)
	, m_CallbackGamepadSlotChange(this, &SteamInputCallback::onGamepadSlotChange) {

}

SteamInputCallback::~SteamInputCallback() {
	if (s_actionEventInstance == this) {
		s_actionEventInstance = NULL;
	}
}

void SteamInputCallback::enableActionEventCallbacks(bool enabled) {
	if (enabled) {
		s_actionEventInstance = this;
		// Steam supports one callback pointer, so the trampoline is registered once and stays registered.
		// Disabling only detaches the instance, which keeps us from relying on how Steam treats NULL.
		if (!s_actionEventCallbackRegistered) {
			SteamInput()->EnableActionEventCallbacks(&SteamInputActionEventCallback);
			s_actionEventCallbackRegistered = true;
		}
	} else if (s_actionEventInstance == this) {
		s_actionEventInstance = NULL;
	}
}

void SteamInputCallback::onActionEvent(SteamInputActionEvent_t* event) {
	if (event->eEventType == ESteamInputActionEventType_DigitalAction) {
		invokeCallback({
			callVoidMethod(env, "onDigitalActionEvent", "(JJZZ)V",
				(jlong) event->controllerHandle, (jlong) event->digitalAction.actionHandle,
				(jboolean) event->digitalAction.digitalActionData.bState,
				(jboolean) event->digitalAction.digitalActionData.bActive);
		});
	} else if (event->eEventType == ESteamInputActionEventType_AnalogAction) {
		invokeCallback({
			callVoidMethod(env, "onAnalogActionEvent", "(JJIFFZ)V",
				(jlong) event->controllerHandle, (jlong) event->analogAction.actionHandle,
				(jint) event->analogAction.analogActionData.eMode,
				(jdouble) event->analogAction.analogActionData.x,
				(jdouble) event->analogAction.analogActionData.y,
				(jboolean) event->analogAction.analogActionData.bActive);
		});
	}
}

void SteamInputCallback::onDeviceConnected(SteamInputDeviceConnected_t* callback) {
	invokeCallback({
		callVoidMethod(env, "onDeviceConnected", "(J)V", (jlong) callback->m_ulConnectedDeviceHandle);
	});
}

void SteamInputCallback::onDeviceDisconnected(SteamInputDeviceDisconnected_t* callback) {
	invokeCallback({
		callVoidMethod(env, "onDeviceDisconnected", "(J)V", (jlong) callback->m_ulDisconnectedDeviceHandle);
	});
}

void SteamInputCallback::onConfigurationLoaded(SteamInputConfigurationLoaded_t* callback) {
	invokeCallback({
		callVoidMethod(env, "onConfigurationLoaded", "(IJJIIZZ)V",
			(jint) callback->m_unAppID, (jlong) callback->m_ulDeviceHandle,
			(jlong) callback->m_ulMappingCreator.ConvertToUint64(),
			(jint) callback->m_unMajorRevision, (jint) callback->m_unMinorRevision,
			(jboolean) callback->m_bUsesSteamInputAPI, (jboolean) callback->m_bUsesGamepadAPI);
	});
}

void SteamInputCallback::onGamepadSlotChange(SteamInputGamepadSlotChange_t* callback) {
	invokeCallback({
		callVoidMethod(env, "onGamepadSlotChange", "(IJIII)V",
			(jint) callback->m_unAppID, (jlong) callback->m_ulDeviceHandle, (jint) callback->m_eDeviceType,
			(jint) callback->m_nOldGamepadSlot, (jint) callback->m_nNewGamepadSlot);
	});
}
