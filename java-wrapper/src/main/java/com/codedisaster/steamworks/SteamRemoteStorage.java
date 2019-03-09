package com.codedisaster.steamworks;

import java.nio.ByteBuffer;

public class SteamRemoteStorage extends SteamInterface {

	public enum RemoteStoragePlatform {
		None(0),
		Windows(1),
		OSX(1 << 1),
		PS3(1 << 2),
		Linux(1 << 3),
		Reserved2(1 << 4),
		Android(1 << 5),

		All(0xffffffff);

		private final int mask;
		private static final RemoteStoragePlatform[] values = values();

		RemoteStoragePlatform(int mask) {
			this.mask = mask;
		}

		static RemoteStoragePlatform[] byMask(int mask) {
			int bits = Integer.bitCount(mask);
			RemoteStoragePlatform[] result = new RemoteStoragePlatform[bits];

			int idx = 0;
			for (RemoteStoragePlatform value : values) {
				if ((value.mask & mask) != 0) {
					result[idx++] = value;
				}
			}

			return result;
		}
	}

	public enum UGCReadAction {
		ContinueReadingUntilFinished,
		ContinueReading,
		Close
	}

	public enum PublishedFileVisibility {
		Public,
		FriendsOnly,
		Private
	}

	public enum WorkshopFileType {
		Community,
		Microtransaction,
		Collection,
		Art,
		Video,
		Screenshot,
		Game,
		Software,
		Concept,
		WebGuide,
		IntegratedGuide,
		Merch,
		ControllerBinding,
		SteamworksAccessInvite,
		SteamVideo,
		GameManagedItem;

		private static final WorkshopFileType[] values = values();

		static WorkshopFileType byOrdinal(int ordinal) {
			return values[ordinal];
		}
	}

	public SteamRemoteStorage(SteamRemoteStorageCallback callback) {
		super(SteamAPI.getSteamRemoteStoragePointer(), createCallback(new SteamRemoteStorageCallbackAdapter(callback)));
	}

	public boolean fileWrite(String file, ByteBuffer data) throws SteamException {
		if (!data.isDirect()) {
			throw new SteamException("Direct buffer required!");
		}
		return fileWrite(pointer, file, data, data.position(), data.remaining());
	}

	public boolean fileRead(String file, ByteBuffer buffer) throws SteamException {
		if (!buffer.isDirect()) {
			throw new SteamException("Direct buffer required!");
		}
		return fileRead(pointer, file, buffer, buffer.position(), buffer.remaining());
	}

	public SteamAPICall fileWriteAsync(String file, ByteBuffer data) throws SteamException {
		if (!data.isDirect()) {
			throw new SteamException("Direct buffer required!");
		}
		return new SteamAPICall(fileWriteAsync(pointer, callback, file, data, data.position(), data.remaining()));
	}

	public SteamAPICall fileReadAsync(String file, int offset, int toRead) {
		return new SteamAPICall(fileReadAsync(pointer, callback, file, offset, toRead));
	}

	public boolean fileReadAsyncComplete(SteamAPICall readCall, ByteBuffer buffer, int toRead) {
		return fileReadAsyncComplete(pointer, readCall.handle, buffer, buffer.position(), toRead);
	}

	public boolean fileForget(String file) {
		return fileForget(pointer, file);
	}

	public boolean fileDelete(String file) {
		return fileDelete(pointer, file);
	}

	public SteamAPICall fileShare(String file) {
		return new SteamAPICall(fileShare(pointer, callback, file));
	}

	public boolean setSyncPlatforms(String file, RemoteStoragePlatform remoteStoragePlatform) {
		return setSyncPlatforms(pointer, file, remoteStoragePlatform.mask);
	}

	public SteamUGCFileWriteStreamHandle fileWriteStreamOpen(String name) {
		return new SteamUGCFileWriteStreamHandle(fileWriteStreamOpen(pointer, name));
	}

	public boolean fileWriteStreamWriteChunk(SteamUGCFileWriteStreamHandle stream, ByteBuffer data) {
		return fileWriteStreamWriteChunk(pointer, stream.handle, data, data.position(), data.remaining());
	}

	public boolean fileWriteStreamClose(SteamUGCFileWriteStreamHandle stream) {
		return fileWriteStreamClose(pointer, stream.handle);
	}

	public boolean fileWriteStreamCancel(SteamUGCFileWriteStreamHandle stream) {
		return fileWriteStreamCancel(pointer, stream.handle);
	}

	public boolean fileExists(String file) {
		return fileExists(pointer, file);
	}

	public boolean filePersisted(String file) {
		return filePersisted(pointer, file);
	}

	public int getFileSize(String file) {
		return getFileSize(pointer, file);
	}

	public long getFileTimestamp(String file) {
		return getFileTimestamp(pointer, file);
	}

	public RemoteStoragePlatform[] getSyncPlatforms(String file) {
		int mask = getSyncPlatforms(pointer, file);
		return RemoteStoragePlatform.byMask(mask);
	}

	public int getFileCount() {
		return getFileCount(pointer);
	}

	public String getFileNameAndSize(int index, int[] sizes) {
		return getFileNameAndSize(pointer, index, sizes);
	}

	public boolean getQuota(long[] totalBytes, long[] availableBytes) {
		return getQuota(pointer, totalBytes, availableBytes);
	}

	public boolean isCloudEnabledForAccount() {
		return isCloudEnabledForAccount(pointer);
	}

	public boolean isCloudEnabledForApp() {
		return isCloudEnabledForApp(pointer);
	}

	public void setCloudEnabledForApp(boolean enabled) {
		setCloudEnabledForApp(pointer, enabled);
	}

	public SteamAPICall ugcDownload(SteamUGCHandle fileHandle, int priority) {
		return new SteamAPICall(ugcDownload(pointer, callback, fileHandle.handle, priority));
	}

	public boolean getUGCDownloadProgress(SteamUGCHandle fileHandle, int[] bytesDownloaded, int[] bytesExpected) {
		return getUGCDownloadProgress(pointer, fileHandle.handle, bytesDownloaded, bytesExpected);
	}

	public int ugcRead(SteamUGCHandle fileHandle, ByteBuffer buffer, int dataToRead, int offset, UGCReadAction action) {
		return ugcRead(pointer, fileHandle.handle, buffer, buffer.position(), dataToRead, offset, action.ordinal());
	}

	public int getCachedUGCCount() {
		return getCachedUGCCount(pointer);
	}

	public SteamUGCHandle getCachedUGCHandle(int cachedContent) {
		return new SteamUGCHandle(getCachedUGCHandle(pointer, cachedContent));
	}

	public SteamAPICall publishWorkshopFile(String file, String previewFile,
										   int consumerAppID, String title, String description,
										   PublishedFileVisibility visibility, String[] tags,
										   WorkshopFileType workshopFileType) {

		return new SteamAPICall(publishWorkshopFile(pointer, callback, file, previewFile, consumerAppID, title,
				description, visibility.ordinal(), tags, tags != null ? tags.length : 0, workshopFileType.ordinal()));
	}

	public SteamPublishedFileUpdateHandle createPublishedFileUpdateRequest(SteamPublishedFileID publishedFileID) {
		return new SteamPublishedFileUpdateHandle(createPublishedFileUpdateRequest(pointer, publishedFileID.handle));
	}

	public boolean updatePublishedFileFile(SteamPublishedFileUpdateHandle updateHandle, String file) {
		return updatePublishedFileFile(pointer, updateHandle.handle, file);
	}

	public boolean updatePublishedFilePreviewFile(SteamPublishedFileUpdateHandle updateHandle, String previewFile) {
		return updatePublishedFilePreviewFile(pointer, updateHandle.handle, previewFile);
	}

	public boolean updatePublishedFileTitle(SteamPublishedFileUpdateHandle updateHandle, String title) {
		return updatePublishedFileTitle(pointer, updateHandle.handle, title);
	}

	public boolean updatePublishedFileDescription(SteamPublishedFileUpdateHandle updateHandle, String description) {
		return updatePublishedFileDescription(pointer, updateHandle.handle, description);
	}

	public boolean updatePublishedFileVisibility(SteamPublishedFileUpdateHandle updateHandle,
												 PublishedFileVisibility visibility) {

		return updatePublishedFileVisibility(pointer, updateHandle.handle, visibility.ordinal());
	}

	public boolean updatePublishedFileTags(SteamPublishedFileUpdateHandle updateHandle, String[] tags) {
		return updatePublishedFileTags(pointer, updateHandle.handle, tags, tags != null ? tags.length : 0);
	}

	public SteamAPICall commitPublishedFileUpdate(SteamPublishedFileUpdateHandle updateHandle) {
		return new SteamAPICall(commitPublishedFileUpdate(pointer, callback, updateHandle.handle));
	}

	// @off

	/*JNI
		#include "SteamRemoteStorageCallback.h"
	*/

	private static native long createCallback(SteamRemoteStorageCallbackAdapter javaCallback); /*
		return (intp) new SteamRemoteStorageCallback(env, javaCallback);
	*/

	private static native boolean fileWrite(long pointer, String file, ByteBuffer data,
											int bufferOffset, int bufferSize); /*
		ISteamRemoteStorage* storage = (ISteamRemoteStorage*) pointer;
		return storage->FileWrite(file, &data[bufferOffset], bufferSize);
	*/

	private static native boolean fileRead(long pointer, String file, ByteBuffer buffer,
										   int bufferOffset, int bufferSize); /*
		ISteamRemoteStorage* storage = (ISteamRemoteStorage*) pointer;
		return storage->FileRead(file, &buffer[bufferOffset], bufferSize);
	*/

	private static native long fileWriteAsync(long pointer, long callback, String file, ByteBuffer data,
											  int bufferOffset, int bufferSize); /*
		ISteamRemoteStorage* storage = (ISteamRemoteStorage*) pointer;
		SteamRemoteStorageCallback* cb = (SteamRemoteStorageCallback*) callback;
		SteamAPICall_t handle = storage->FileWriteAsync(file, &data[bufferOffset], (uint32) bufferSize);
		cb->onFileWriteAsyncCompleteCall.Set(handle, cb, &SteamRemoteStorageCallback::onFileWriteAsyncComplete);
		return handle;
	*/

	private static native long fileReadAsync(long pointer, long callback, String file, int offset, int toRead); /*
		ISteamRemoteStorage* storage = (ISteamRemoteStorage*) pointer;
		SteamRemoteStorageCallback* cb = (SteamRemoteStorageCallback*) callback;
		SteamAPICall_t handle = storage->FileReadAsync(file, (uint32) offset, (uint32) toRead);
        cb->onFileReadAsyncCompleteCall.Set(handle, cb, &SteamRemoteStorageCallback::onFileReadAsyncComplete);
		return handle;
	*/

	private static native boolean fileReadAsyncComplete(long pointer, long readCall, ByteBuffer buffer,
														long bufferOffset, int toRead); /*
		ISteamRemoteStorage* storage = (ISteamRemoteStorage*) pointer;
		return storage->FileReadAsyncComplete((SteamAPICall_t) readCall, &buffer[bufferOffset], (uint32) toRead);
	*/

	private static native boolean fileForget(long pointer, String file); /*
		ISteamRemoteStorage* storage = (ISteamRemoteStorage*) pointer;
		return storage->FileForget(file);
	*/

	private static native boolean fileDelete(long pointer, String file); /*
		ISteamRemoteStorage* storage = (ISteamRemoteStorage*) pointer;
		return storage->FileDelete(file);
	*/

	private static native long fileShare(long pointer, long callback, String file); /*
		ISteamRemoteStorage* storage = (ISteamRemoteStorage*) pointer;
		SteamRemoteStorageCallback* cb = (SteamRemoteStorageCallback*) callback;
		SteamAPICall_t handle = storage->FileShare(file);
		cb->onFileShareResultCall.Set(handle, cb, &SteamRemoteStorageCallback::onFileShareResult);
		return handle;
	*/

	private static native boolean setSyncPlatforms(long pointer, String file, int remoteStoragePlatform); /*
		ISteamRemoteStorage* storage = (ISteamRemoteStorage*) pointer;
		return storage->SetSyncPlatforms(file, (ERemoteStoragePlatform) remoteStoragePlatform);
	*/

	private static native long fileWriteStreamOpen(long pointer, String file); /*
		ISteamRemoteStorage* storage = (ISteamRemoteStorage*) pointer;
		return storage->FileWriteStreamOpen(file);
	*/

	private static native boolean fileWriteStreamWriteChunk(long pointer, long stream, ByteBuffer data,
															int bufferOffset, int length); /*
		ISteamRemoteStorage* storage = (ISteamRemoteStorage*) pointer;
		return storage->FileWriteStreamWriteChunk((UGCFileWriteStreamHandle_t) stream, &data[bufferOffset], length);
	*/

	private static native boolean fileWriteStreamClose(long pointer, long stream); /*
		ISteamRemoteStorage* storage = (ISteamRemoteStorage*) pointer;
		return storage->FileWriteStreamClose((UGCFileWriteStreamHandle_t) stream);
	*/

	private static native boolean fileWriteStreamCancel(long pointer, long stream); /*
		ISteamRemoteStorage* storage = (ISteamRemoteStorage*) pointer;
		return storage->FileWriteStreamCancel((UGCFileWriteStreamHandle_t) stream);
	*/

	private static native boolean fileExists(long pointer, String file); /*
		ISteamRemoteStorage* storage = (ISteamRemoteStorage*) pointer;
		return storage->FileExists(file);
	*/

	private static native boolean filePersisted(long pointer, String file); /*
		ISteamRemoteStorage* storage = (ISteamRemoteStorage*) pointer;
		return storage->FilePersisted(file);
	*/

	private static native int getFileSize(long pointer, String file); /*
		ISteamRemoteStorage* storage = (ISteamRemoteStorage*) pointer;
		return storage->GetFileSize(file);
	*/

	private static native long getFileTimestamp(long pointer, String file); /*
		ISteamRemoteStorage* storage = (ISteamRemoteStorage*) pointer;
		return storage->GetFileTimestamp(file);
	*/

	private static native int getSyncPlatforms(long pointer, String file); /*
		ISteamRemoteStorage* storage = (ISteamRemoteStorage*) pointer;
		return storage->GetSyncPlatforms(file);
	*/

	private static native int getFileCount(long pointer); /*
		ISteamRemoteStorage* storage = (ISteamRemoteStorage*) pointer;
		return storage->GetFileCount();
	*/

	private static native String getFileNameAndSize(long pointer, int index, int[] sizes); /*
		ISteamRemoteStorage* storage = (ISteamRemoteStorage*) pointer;
		jstring name = env->NewStringUTF(storage->GetFileNameAndSize(index, &sizes[0]));
		return name;
	*/

	private static native boolean getQuota(long pointer, long[] totalBytes, long[] availableBytes); /*
		ISteamRemoteStorage* storage = (ISteamRemoteStorage*) pointer;
		return storage->GetQuota((uint64*) &totalBytes[0], (uint64*) &availableBytes[0]);
	*/

	private static native boolean isCloudEnabledForAccount(long pointer); /*
		ISteamRemoteStorage* storage = (ISteamRemoteStorage*) pointer;
		return storage->IsCloudEnabledForAccount();
	*/

	private static native boolean isCloudEnabledForApp(long pointer); /*
		ISteamRemoteStorage* storage = (ISteamRemoteStorage*) pointer;
		return storage->IsCloudEnabledForApp();
	*/

	private static native void setCloudEnabledForApp(long pointer, boolean enabled); /*
		ISteamRemoteStorage* storage = (ISteamRemoteStorage*) pointer;
		storage->SetCloudEnabledForApp(enabled);
	*/

	private static native long ugcDownload(long pointer, long callback, long content, int priority); /*
		ISteamRemoteStorage* storage = (ISteamRemoteStorage*) pointer;
		SteamRemoteStorageCallback* cb = (SteamRemoteStorageCallback*) callback;
		SteamAPICall_t handle = storage->UGCDownload(content, priority);
		cb->onDownloadUGCResultCall.Set(handle, cb, &SteamRemoteStorageCallback::onDownloadUGCResult);
		return handle;
	*/

	private static native boolean getUGCDownloadProgress(long pointer, long content,
														 int[] bytesDownloaded, int[] bytesExpected); /*

		ISteamRemoteStorage* storage = (ISteamRemoteStorage*) pointer;
		return storage->GetUGCDownloadProgress((UGCHandle_t) content, (int32*) &bytesDownloaded[0], (int32*) &bytesExpected[0]);
	*/

	private static native int ugcRead(long pointer, long content, ByteBuffer buffer,
									  int bufferOffset, int dataToRead,
									  int offset, int action); /*

		ISteamRemoteStorage* storage = (ISteamRemoteStorage*) pointer;
		return storage->UGCRead(content, &buffer[bufferOffset], dataToRead, offset, (EUGCReadAction) action);
	*/

	private static native int getCachedUGCCount(long pointer); /*
		ISteamRemoteStorage* storage = (ISteamRemoteStorage*) pointer;
		return storage->GetCachedUGCCount();
	*/

	private static native long getCachedUGCHandle(long pointer, int cachedContent); /*
		ISteamRemoteStorage* storage = (ISteamRemoteStorage*) pointer;
		return storage->GetCachedUGCHandle(cachedContent);
	*/

	private static native long publishWorkshopFile(long pointer, long callback,
												   String file, String previewFile, int consumerAppID,
												   String title, String description, int visibility, String[] tags,
												   int numTags, int workshopFileType); /*

		SteamParamStringArray_t arrayTags;
		arrayTags.m_ppStrings = (numTags > 0) ? new const char*[numTags] : NULL;
		arrayTags.m_nNumStrings = numTags;
		for (int t = 0; t < numTags; t++) {
			arrayTags.m_ppStrings[t] = env->GetStringUTFChars((jstring) env->GetObjectArrayElement(tags, t), 0);
		}

		ISteamRemoteStorage* storage = (ISteamRemoteStorage*) pointer;
		SteamRemoteStorageCallback* cb = (SteamRemoteStorageCallback*) callback;

		SteamAPICall_t handle = storage->PublishWorkshopFile(file, previewFile, consumerAppID, title, description,
			(ERemoteStoragePublishedFileVisibility) visibility, &arrayTags, (EWorkshopFileType) workshopFileType);

		cb->onPublishFileResultCall.Set(handle, cb, &SteamRemoteStorageCallback::onPublishFileResult);

		for (int t = 0; t < numTags; t++) {
			env->ReleaseStringUTFChars((jstring) env->GetObjectArrayElement(tags, t), arrayTags.m_ppStrings[t]);
		}
		delete[] arrayTags.m_ppStrings;

		return handle;
	*/

	private static native long createPublishedFileUpdateRequest(long pointer, long publishedFileID); /*
		ISteamRemoteStorage* storage = (ISteamRemoteStorage*) pointer;
		return storage->CreatePublishedFileUpdateRequest(publishedFileID);
	*/

	private static native boolean updatePublishedFileFile(long pointer, long updateHandle, String file); /*
		ISteamRemoteStorage* storage = (ISteamRemoteStorage*) pointer;
		return storage->UpdatePublishedFileFile(updateHandle, file);
	*/

	private static native boolean updatePublishedFilePreviewFile(long pointer, long updateHandle, String previewFile); /*
		ISteamRemoteStorage* storage = (ISteamRemoteStorage*) pointer;
		return storage->UpdatePublishedFilePreviewFile(updateHandle, previewFile);
	*/

	private static native boolean updatePublishedFileTitle(long pointer, long updateHandle, String title); /*
		ISteamRemoteStorage* storage = (ISteamRemoteStorage*) pointer;
		return storage->UpdatePublishedFileTitle(updateHandle, title);
	*/

	private static native boolean updatePublishedFileDescription(long pointer, long updateHandle, String description); /*
		ISteamRemoteStorage* storage = (ISteamRemoteStorage*) pointer;
		return storage->UpdatePublishedFileDescription(updateHandle, description);
	*/

	private static native boolean updatePublishedFileVisibility(long pointer, long updateHandle, int visibility); /*
		ISteamRemoteStorage* storage = (ISteamRemoteStorage*) pointer;
		return storage->UpdatePublishedFileVisibility(updateHandle, (ERemoteStoragePublishedFileVisibility) visibility);
	*/

	private static native boolean updatePublishedFileTags(long pointer, long updateHandle, String[] tags, int numTags); /*
		SteamParamStringArray_t arrayTags;
		arrayTags.m_ppStrings = (numTags > 0) ? new const char*[numTags] : NULL;
		arrayTags.m_nNumStrings = numTags;
		for (int t = 0; t < numTags; t++) {
			arrayTags.m_ppStrings[t] = env->GetStringUTFChars((jstring) env->GetObjectArrayElement(tags, t), 0);
		}

		ISteamRemoteStorage* storage = (ISteamRemoteStorage*) pointer;
		bool result = storage->UpdatePublishedFileTags(updateHandle, &arrayTags);

		for (int t = 0; t < numTags; t++) {
			env->ReleaseStringUTFChars((jstring) env->GetObjectArrayElement(tags, t), arrayTags.m_ppStrings[t]);
		}
		delete[] arrayTags.m_ppStrings;

		return result;
	*/

	private static native long commitPublishedFileUpdate(long pointer, long callback, long updateHandle); /*
		ISteamRemoteStorage* storage = (ISteamRemoteStorage*) pointer;
		SteamRemoteStorageCallback* cb = (SteamRemoteStorageCallback*) callback;
		SteamAPICall_t handle = storage->CommitPublishedFileUpdate(updateHandle);
		cb->onUpdatePublishedFileResultCall.Set(handle, cb, &SteamRemoteStorageCallback::onUpdatePublishedFileResult);
		return handle;
	*/

}
