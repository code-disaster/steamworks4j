package com.codedisaster.steamworks;

import java.nio.ByteBuffer;

final class SteamRemoteStorageNative {

	// @off

	/*JNI
		#include "SteamRemoteStorageCallback.h"
	*/

	static native long createCallback(SteamRemoteStorageCallbackAdapter javaCallback); /*
		return (intp) new SteamRemoteStorageCallback(env, javaCallback);
	*/

	static native boolean fileWrite(String file, ByteBuffer data,
									int bufferOffset, int bufferSize); /*
		return SteamRemoteStorage()->FileWrite(file, &data[bufferOffset], bufferSize);
	*/

	static native int fileRead(String file, ByteBuffer buffer,
							   int bufferOffset, int bufferSize); /*
		return (jint) SteamRemoteStorage()->FileRead(file, &buffer[bufferOffset], bufferSize);
	*/

	static native long fileWriteAsync(long callback, String file, ByteBuffer data,
									  int bufferOffset, int bufferSize); /*
		SteamRemoteStorageCallback* cb = (SteamRemoteStorageCallback*) callback;
		SteamAPICall_t handle = SteamRemoteStorage()->FileWriteAsync(file, &data[bufferOffset], (uint32) bufferSize);
		cb->onFileWriteAsyncCompleteCall.Set(handle, cb, &SteamRemoteStorageCallback::onFileWriteAsyncComplete);
		return handle;
	*/

	static native long fileReadAsync(long callback, String file, int offset, int toRead); /*
		SteamRemoteStorageCallback* cb = (SteamRemoteStorageCallback*) callback;
		SteamAPICall_t handle = SteamRemoteStorage()->FileReadAsync(file, (uint32) offset, (uint32) toRead);
        cb->onFileReadAsyncCompleteCall.Set(handle, cb, &SteamRemoteStorageCallback::onFileReadAsyncComplete);
		return handle;
	*/

	static native boolean fileReadAsyncComplete(long readCall, ByteBuffer buffer,
												long bufferOffset, int toRead); /*
		return SteamRemoteStorage()->FileReadAsyncComplete((SteamAPICall_t) readCall, &buffer[bufferOffset], (uint32) toRead);
	*/

	static native boolean fileForget(String file); /*
		return SteamRemoteStorage()->FileForget(file);
	*/

	static native boolean fileDelete(String file); /*
		return SteamRemoteStorage()->FileDelete(file);
	*/

	static native long fileShare(long callback, String file); /*
		SteamRemoteStorageCallback* cb = (SteamRemoteStorageCallback*) callback;
		SteamAPICall_t handle = SteamRemoteStorage()->FileShare(file);
		cb->onFileShareResultCall.Set(handle, cb, &SteamRemoteStorageCallback::onFileShareResult);
		return handle;
	*/

	static native boolean setSyncPlatforms(String file, int remoteStoragePlatform); /*
		return SteamRemoteStorage()->SetSyncPlatforms(file, (ERemoteStoragePlatform) remoteStoragePlatform);
	*/

	static native long fileWriteStreamOpen(String file); /*
		return SteamRemoteStorage()->FileWriteStreamOpen(file);
	*/

	static native boolean fileWriteStreamWriteChunk(long stream, ByteBuffer data,
													int bufferOffset, int length); /*
		return SteamRemoteStorage()->FileWriteStreamWriteChunk((UGCFileWriteStreamHandle_t) stream, &data[bufferOffset], length);
	*/

	static native boolean fileWriteStreamClose(long stream); /*
		return SteamRemoteStorage()->FileWriteStreamClose((UGCFileWriteStreamHandle_t) stream);
	*/

	static native boolean fileWriteStreamCancel(long stream); /*
		return SteamRemoteStorage()->FileWriteStreamCancel((UGCFileWriteStreamHandle_t) stream);
	*/

	static native boolean fileExists(String file); /*
		return SteamRemoteStorage()->FileExists(file);
	*/

	static native boolean filePersisted(String file); /*
		return SteamRemoteStorage()->FilePersisted(file);
	*/

	static native int getFileSize(String file); /*
		return SteamRemoteStorage()->GetFileSize(file);
	*/

	static native long getFileTimestamp(String file); /*
		return SteamRemoteStorage()->GetFileTimestamp(file);
	*/

	static native int getSyncPlatforms(String file); /*
		return SteamRemoteStorage()->GetSyncPlatforms(file);
	*/

	static native int getFileCount(); /*
		return SteamRemoteStorage()->GetFileCount();
	*/

	static native String getFileNameAndSize(int index, int[] sizes); /*
		return env->NewStringUTF(SteamRemoteStorage()->GetFileNameAndSize(index, &sizes[0]));
	*/

	static native boolean getQuota(long[] totalBytes, long[] availableBytes); /*
		return SteamRemoteStorage()->GetQuota((uint64*) &totalBytes[0], (uint64*) &availableBytes[0]);
	*/

	static native boolean isCloudEnabledForAccount(); /*
		return SteamRemoteStorage()->IsCloudEnabledForAccount();
	*/

	static native boolean isCloudEnabledForApp(); /*
		return SteamRemoteStorage()->IsCloudEnabledForApp();
	*/

	static native void setCloudEnabledForApp(boolean enabled); /*
		SteamRemoteStorage()->SetCloudEnabledForApp(enabled);
	*/

	static native long ugcDownload(long callback, long content, int priority); /*
		SteamRemoteStorageCallback* cb = (SteamRemoteStorageCallback*) callback;
		SteamAPICall_t handle = SteamRemoteStorage()->UGCDownload(content, priority);
		cb->onDownloadUGCResultCall.Set(handle, cb, &SteamRemoteStorageCallback::onDownloadUGCResult);
		return handle;
	*/

	static native boolean getUGCDownloadProgress(long content,
												 int[] bytesDownloaded, int[] bytesExpected); /*

		return SteamRemoteStorage()->GetUGCDownloadProgress((UGCHandle_t) content, (int32*) &bytesDownloaded[0], (int32*) &bytesExpected[0]);
	*/

	static native int ugcRead(long content, ByteBuffer buffer,
							  int bufferOffset, int dataToRead,
							  int offset, int action); /*

		return SteamRemoteStorage()->UGCRead(content, &buffer[bufferOffset], dataToRead, offset, (EUGCReadAction) action);
	*/

	static native int getCachedUGCCount(); /*
		return SteamRemoteStorage()->GetCachedUGCCount();
	*/

	static native long getCachedUGCHandle(int cachedContent); /*
		return SteamRemoteStorage()->GetCachedUGCHandle(cachedContent);
	*/

	static native long publishWorkshopFile(long callback,
										   String file, String previewFile, int consumerAppID,
										   String title, String description, int visibility, String[] tags,
										   int numTags, int workshopFileType); /*

		SteamParamStringArray_t arrayTags;
		arrayTags.m_ppStrings = (numTags > 0) ? new const char*[numTags] : NULL;
		arrayTags.m_nNumStrings = numTags;
		for (int t = 0; t < numTags; t++) {
			arrayTags.m_ppStrings[t] = env->GetStringUTFChars((jstring) env->GetObjectArrayElement(tags, t), 0);
		}

		SteamRemoteStorageCallback* cb = (SteamRemoteStorageCallback*) callback;

		SteamAPICall_t handle = SteamRemoteStorage()->PublishWorkshopFile(file, previewFile, consumerAppID, title, description,
			(ERemoteStoragePublishedFileVisibility) visibility, &arrayTags, (EWorkshopFileType) workshopFileType);

		cb->onPublishFileResultCall.Set(handle, cb, &SteamRemoteStorageCallback::onPublishFileResult);

		for (int t = 0; t < numTags; t++) {
			env->ReleaseStringUTFChars((jstring) env->GetObjectArrayElement(tags, t), arrayTags.m_ppStrings[t]);
		}
		delete[] arrayTags.m_ppStrings;

		return handle;
	*/

	static native long createPublishedFileUpdateRequest(long publishedFileID); /*
		return SteamRemoteStorage()->CreatePublishedFileUpdateRequest(publishedFileID);
	*/

	static native boolean updatePublishedFileFile(long updateHandle, String file); /*
		return SteamRemoteStorage()->UpdatePublishedFileFile(updateHandle, file);
	*/

	static native boolean updatePublishedFilePreviewFile(long updateHandle, String previewFile); /*
		return SteamRemoteStorage()->UpdatePublishedFilePreviewFile(updateHandle, previewFile);
	*/

	static native boolean updatePublishedFileTitle(long updateHandle, String title); /*
		return SteamRemoteStorage()->UpdatePublishedFileTitle(updateHandle, title);
	*/

	static native boolean updatePublishedFileDescription(long updateHandle, String description); /*
		return SteamRemoteStorage()->UpdatePublishedFileDescription(updateHandle, description);
	*/

	static native boolean updatePublishedFileVisibility(long updateHandle, int visibility); /*
		return SteamRemoteStorage()->UpdatePublishedFileVisibility(updateHandle, (ERemoteStoragePublishedFileVisibility) visibility);
	*/

	static native boolean updatePublishedFileTags(long updateHandle, String[] tags, int numTags); /*
		SteamParamStringArray_t arrayTags;
		arrayTags.m_ppStrings = (numTags > 0) ? new const char*[numTags] : NULL;
		arrayTags.m_nNumStrings = numTags;
		for (int t = 0; t < numTags; t++) {
			arrayTags.m_ppStrings[t] = env->GetStringUTFChars((jstring) env->GetObjectArrayElement(tags, t), 0);
		}

		bool result = SteamRemoteStorage()->UpdatePublishedFileTags(updateHandle, &arrayTags);

		for (int t = 0; t < numTags; t++) {
			env->ReleaseStringUTFChars((jstring) env->GetObjectArrayElement(tags, t), arrayTags.m_ppStrings[t]);
		}
		delete[] arrayTags.m_ppStrings;

		return result;
	*/

	static native long commitPublishedFileUpdate(long callback, long updateHandle); /*
		SteamRemoteStorageCallback* cb = (SteamRemoteStorageCallback*) callback;
		SteamAPICall_t handle = SteamRemoteStorage()->CommitPublishedFileUpdate(updateHandle);
		cb->onUpdatePublishedFileResultCall.Set(handle, cb, &SteamRemoteStorageCallback::onUpdatePublishedFileResult);
		return handle;
	*/

}
