package com.codedisaster.steamworks;

import java.nio.ByteBuffer;

public class SteamRemoteStorage extends SteamInterface {

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

	public boolean fileWrite(String name, ByteBuffer data, int length) throws SteamException {
		if (!data.isDirect()) {
			throw new SteamException("Direct buffer required!");
		}
		return fileWrite(pointer, name, data, length);
	}

	public boolean fileRead(String name, ByteBuffer buffer, int capacity) throws SteamException {
		if (!buffer.isDirect()) {
			throw new SteamException("Direct buffer required!");
		}
		return fileRead(pointer, name, buffer, capacity);
	}

	public boolean fileDelete(String name) {
		return fileDelete(pointer, name);
	}

	public SteamAPICall fileShare(String name) {
		return new SteamAPICall(fileShare(pointer, callback, name));
	}

	public SteamUGCFileWriteStreamHandle fileWriteStreamOpen(String name) {
		return new SteamUGCFileWriteStreamHandle(fileWriteStreamOpen(pointer, name));
	}

	public boolean fileWriteStreamWriteChunk(SteamUGCFileWriteStreamHandle stream, ByteBuffer data, int length) {
		return fileWriteStreamWriteChunk(pointer, stream.handle, data, length);
	}

	public boolean fileWriteStreamClose(SteamUGCFileWriteStreamHandle stream) {
		return fileWriteStreamClose(pointer, stream.handle);
	}

	public boolean fileWriteStreamCancel(SteamUGCFileWriteStreamHandle stream) {
		return fileWriteStreamCancel(pointer, stream.handle);
	}

	public boolean fileExists(String name) {
		return fileExists(pointer, name);
	}

	public int getFileSize(String name) {
		return getFileSize(pointer, name);
	}

	public int getFileCount() {
		return getFileCount(pointer);
	}

	public String getFileNameAndSize(int index, int[] sizes) {
		return getFileNameAndSize(pointer, index, sizes);
	}

	public SteamAPICall ugcDownload(SteamUGCHandle fileHandle, int priority) {
		return new SteamAPICall(ugcDownload(pointer, callback, fileHandle.handle, priority));
	}

	public int ugcRead(SteamUGCHandle fileHandle, ByteBuffer buffer, int capacity, int offset, UGCReadAction action) {
		return ugcRead(pointer, fileHandle.handle, buffer, capacity, offset, action.ordinal());
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

	private static native boolean fileWrite(long pointer, String name, ByteBuffer data, int length); /*
		ISteamRemoteStorage* storage = (ISteamRemoteStorage*) pointer;
		return storage->FileWrite(name, data, length);
	*/

	private static native boolean fileRead(long pointer, String name, ByteBuffer buffer, int capacity); /*
		ISteamRemoteStorage* storage = (ISteamRemoteStorage*) pointer;
		return storage->FileRead(name, buffer, capacity);
	*/

	private static native boolean fileDelete(long pointer, String name); /*
		ISteamRemoteStorage* storage = (ISteamRemoteStorage*) pointer;
		return storage->FileDelete(name);
	*/

	private static native long fileShare(long pointer, long callback, String name); /*
		ISteamRemoteStorage* storage = (ISteamRemoteStorage*) pointer;
		SteamRemoteStorageCallback* cb = (SteamRemoteStorageCallback*) callback;
		SteamAPICall_t handle = storage->FileShare(name);
		cb->onFileShareResultCall.Set(handle, cb, &SteamRemoteStorageCallback::onFileShareResult);
		return handle;
	*/

	private static native long fileWriteStreamOpen(long pointer, String name); /*
		ISteamRemoteStorage* storage = (ISteamRemoteStorage*) pointer;
		return storage->FileWriteStreamOpen(name);
	*/

	private static native boolean fileWriteStreamWriteChunk(long pointer, long stream, ByteBuffer data, int length); /*
		ISteamRemoteStorage* storage = (ISteamRemoteStorage*) pointer;
		return storage->FileWriteStreamWriteChunk((UGCFileWriteStreamHandle_t) stream, data, length);
	*/

	private static native boolean fileWriteStreamClose(long pointer, long stream); /*
		ISteamRemoteStorage* storage = (ISteamRemoteStorage*) pointer;
		return storage->FileWriteStreamClose((UGCFileWriteStreamHandle_t) stream);
	*/

	private static native boolean fileWriteStreamCancel(long pointer, long stream); /*
		ISteamRemoteStorage* storage = (ISteamRemoteStorage*) pointer;
		return storage->FileWriteStreamCancel((UGCFileWriteStreamHandle_t) stream);
	*/

	private static native boolean fileExists(long pointer, String name); /*
		ISteamRemoteStorage* storage = (ISteamRemoteStorage*) pointer;
		return storage->FileExists(name);
	*/

	private static native int getFileSize(long pointer, String name); /*
		ISteamRemoteStorage* storage = (ISteamRemoteStorage*) pointer;
		return storage->GetFileSize(name);
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

	private static native long ugcDownload(long pointer, long callback, long content, int priority); /*
		ISteamRemoteStorage* storage = (ISteamRemoteStorage*) pointer;
		SteamRemoteStorageCallback* cb = (SteamRemoteStorageCallback*) callback;
		SteamAPICall_t handle = storage->UGCDownload(content, priority);
		cb->onDownloadUGCResultCall.Set(handle, cb, &SteamRemoteStorageCallback::onDownloadUGCResult);
		return handle;
	*/

	private static native int ugcRead(long pointer, long content, ByteBuffer buffer, int capacity,
									  int offset, int action); /*

		ISteamRemoteStorage* storage = (ISteamRemoteStorage*) pointer;
		return storage->UGCRead(content, buffer, capacity, offset, (EUGCReadAction) action);
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
