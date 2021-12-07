package com.codedisaster.steamworks;

final class SteamUGCNative {

	// @off

	/*JNI
		#include <steam_api.h>
		#include "SteamUGCCallback.h"
	*/

	static native long createCallback(SteamUGCCallbackAdapter javaCallback); /*
		return (intp) new SteamUGCCallback(env, javaCallback);
	*/

	static native long createQueryUserUGCRequest(int accountID, int listType,
												 int matchingType, int sortOrder,
												 int creatorAppID, int consumerAppID, int page); /*
		UGCQueryHandle_t query = SteamUGC()->CreateQueryUserUGCRequest(accountID, (EUserUGCList) listType,
			(EUGCMatchingUGCType) matchingType, (EUserUGCListSortOrder) sortOrder, creatorAppID, consumerAppID, page);
		return (intp) query;
	*/

	static native long createQueryAllUGCRequest(int queryType, int matchingType,
												int creatorAppID, int consumerAppID, int page); /*
		UGCQueryHandle_t query = SteamUGC()->CreateQueryAllUGCRequest((EUGCQuery) queryType,
			(EUGCMatchingUGCType) matchingType, creatorAppID, consumerAppID, page);
		return (intp) query;
	*/

	static native long createQueryUGCDetailsRequest(long[] publishedFileIDs, int numPublishedFileIDs); /*
		UGCQueryHandle_t query = SteamUGC()->CreateQueryUGCDetailsRequest((PublishedFileId_t*) publishedFileIDs, numPublishedFileIDs);
		return (intp) query;
	*/

	static native long sendQueryUGCRequest(long callback, long query); /*
		SteamUGCCallback* cb = (SteamUGCCallback*) callback;
		SteamAPICall_t handle = SteamUGC()->SendQueryUGCRequest(query);
		cb->onUGCQueryCompletedCall.Set(handle, cb, &SteamUGCCallback::onUGCQueryCompleted);
		return handle;
	*/

	static native boolean getQueryUGCResult(long query, int index, SteamUGCDetails details); /*
		SteamUGCDetails_t result;

		if (SteamUGC()->GetQueryUGCResult(query, index, &result)) {
			jclass clazz = env->GetObjectClass(details);

			jfieldID field = env->GetFieldID(clazz, "publishedFileID", "J");
			env->SetLongField(details, field, (jlong) result.m_nPublishedFileId);

			field = env->GetFieldID(clazz, "result", "I");
			env->SetIntField(details, field, (jint) result.m_eResult);

			field = env->GetFieldID(clazz, "fileType", "I");
			env->SetIntField(details, field, (jint) result.m_eFileType);

			jstring title = env->NewStringUTF(result.m_rgchTitle);
			field = env->GetFieldID(clazz, "title", "Ljava/lang/String;");
			env->SetObjectField(details, field, title);

			jstring description = env->NewStringUTF(result.m_rgchDescription);
			field = env->GetFieldID(clazz, "description", "Ljava/lang/String;");
			env->SetObjectField(details, field, description);

			field = env->GetFieldID(clazz, "ownerID", "J");
			env->SetLongField(details, field, (jlong) result.m_ulSteamIDOwner);

			field = env->GetFieldID(clazz, "timeCreated", "I");
			env->SetIntField(details, field, (jint) result.m_rtimeCreated);

			field = env->GetFieldID(clazz, "timeUpdated", "I");
			env->SetIntField(details, field, (jint) result.m_rtimeUpdated);

			field = env->GetFieldID(clazz, "tagsTruncated", "Z");
			env->SetBooleanField(details, field, (jboolean) result.m_bTagsTruncated);

			jstring tags = env->NewStringUTF(result.m_rgchTags);
			field = env->GetFieldID(clazz, "tags", "Ljava/lang/String;");
			env->SetObjectField(details, field, tags);

			field = env->GetFieldID(clazz, "fileHandle", "J");
			env->SetLongField(details, field, (jlong) result.m_hFile);

			field = env->GetFieldID(clazz, "previewFileHandle", "J");
			env->SetLongField(details, field, (jlong) result.m_hPreviewFile);

			jstring fileName = env->NewStringUTF(result.m_pchFileName);
			field = env->GetFieldID(clazz, "fileName", "Ljava/lang/String;");
			env->SetObjectField(details, field, fileName);

			field = env->GetFieldID(clazz, "fileSize", "I");
			env->SetIntField(details, field, (jint) result.m_nFileSize);

			field = env->GetFieldID(clazz, "previewFileSize", "I");
			env->SetIntField(details, field, (jint) result.m_nPreviewFileSize);

			jstring url = env->NewStringUTF(result.m_rgchURL);
			field = env->GetFieldID(clazz, "url", "Ljava/lang/String;");
			env->SetObjectField(details, field, url);

			field = env->GetFieldID(clazz, "votesUp", "I");
			env->SetIntField(details, field, (jint) result.m_unVotesUp);

			field = env->GetFieldID(clazz, "votesDown", "I");
			env->SetIntField(details, field, (jint) result.m_unVotesDown);

			return true;
		}

		return false;
	*/

	static native String getQueryUGCPreviewURL(long query, int index); /*
		char url[1024];

		if (SteamUGC()->GetQueryUGCPreviewURL(query, index, url, 1024)) {
			return env->NewStringUTF(url);
		}

		return nullptr;
	*/

	static native String getQueryUGCMetadata(long query, int index); /*
		char metadata[k_cchDeveloperMetadataMax];

		if (SteamUGC()->GetQueryUGCMetadata(query, index, metadata, k_cchDeveloperMetadataMax)) {
			return env->NewStringUTF(metadata);
		}

		return nullptr;
	*/

	static native long getQueryUGCStatistic(long query, int index, int statType); /*
		uint64 statValue;

		if (SteamUGC()->GetQueryUGCStatistic(query, index, (EItemStatistic) statType, &statValue)) {
			return statValue;
		}

		return 0;
	*/

	static native int getQueryUGCNumAdditionalPreviews(long query, int index); /*
		return SteamUGC()->GetQueryUGCNumAdditionalPreviews(query, index);
	*/

	static native boolean getQueryUGCAdditionalPreview(long query, int index,
													   int previewIndex, SteamUGC.ItemAdditionalPreview previewData); /*
		char url[1024];
		char fileName[1024];
		EItemPreviewType type;

		bool success = SteamUGC()->GetQueryUGCAdditionalPreview(query, index, previewIndex, url, 1024, fileName, 1024, &type);

		if (success) {
			jclass clazz = env->GetObjectClass(previewData);

			jstring urlOrVideoID = env->NewStringUTF(url);
			jfieldID field = env->GetFieldID(clazz, "urlOrVideoID", "Ljava/lang/String;");
			env->SetObjectField(previewData, field, urlOrVideoID);

			jstring originalFileName = env->NewStringUTF(fileName);
			field = env->GetFieldID(clazz, "originalFileName", "Ljava/lang/String;");
			env->SetObjectField(previewData, field, originalFileName);

			field = env->GetFieldID(clazz, "previewType", "I");
			env->SetIntField(previewData, field, (jint) type);
		}

		return success;
	*/

	static native int getQueryUGCNumKeyValueTags(long query, int index); /*
		return SteamUGC()->GetQueryUGCNumKeyValueTags(query, index);
	*/

	static native boolean getQueryUGCKeyValueTag(long query, int index, int keyValueTagIndex, String[] keyAndValue); /*
		char key[1024];
		char value[1024];

		bool success = SteamUGC()->GetQueryUGCKeyValueTag(query, index, keyValueTagIndex, key, 1024, value, 1024);

		if (success) {
			env->SetObjectArrayElement(keyAndValue, 0, env->NewStringUTF(key));
			env->SetObjectArrayElement(keyAndValue, 1, env->NewStringUTF(value));
		}

		return success;
	*/

	static native boolean releaseQueryUserUGCRequest(long query); /*
		return SteamUGC()->ReleaseQueryUGCRequest(query);
	*/

	static native boolean addRequiredTag(long query, String tagName); /*
		return SteamUGC()->AddRequiredTag(query, tagName);
	*/

	static native boolean addExcludedTag(long query, String tagName); /*
		return SteamUGC()->AddExcludedTag(query, tagName);
	*/

	static native boolean setReturnOnlyIDs(long query, boolean returnOnlyIDs); /*
		return SteamUGC()->SetReturnOnlyIDs(query, returnOnlyIDs);
	*/

	static native boolean setReturnKeyValueTags(long query, boolean returnKeyValueTags); /*
		return SteamUGC()->SetReturnKeyValueTags(query, returnKeyValueTags);
	*/

	static native boolean setReturnLongDescription(long query, boolean returnLongDescription); /*
		return SteamUGC()->SetReturnLongDescription(query, returnLongDescription);
	*/

	static native boolean setReturnMetadata(long query, boolean returnMetadata); /*
		return SteamUGC()->SetReturnMetadata(query, returnMetadata);
	*/

	static native boolean setReturnChildren(long query, boolean returnChildren); /*
		return SteamUGC()->SetReturnChildren(query, returnChildren);
	*/

	static native boolean setReturnAdditionalPreviews(long query, boolean returnAdditionalPreviews); /*
		return SteamUGC()->SetReturnAdditionalPreviews(query, returnAdditionalPreviews);
	*/

	static native boolean setReturnTotalOnly(long query, boolean returnTotalOnly); /*
		return SteamUGC()->SetReturnTotalOnly(query, returnTotalOnly);
	*/

	static native boolean setReturnPlaytimeStats(long query, int days); /*
		return SteamUGC()->SetReturnPlaytimeStats(query, (uint32) days);
	*/

	static native boolean setLanguage(long query, String language); /*
		return SteamUGC()->SetLanguage(query, language);
	*/

	static native boolean setAllowCachedResponse(long query, int maxAgeSeconds); /*
		return SteamUGC()->SetAllowCachedResponse(query, maxAgeSeconds);
	*/

	static native boolean setCloudFileNameFilter(long query, String matchCloudFileName); /*
		return SteamUGC()->SetCloudFileNameFilter(query, matchCloudFileName);
	*/

	static native boolean setMatchAnyTag(long query, boolean matchAnyTag); /*
		return SteamUGC()->SetMatchAnyTag(query, matchAnyTag);
	*/

	static native boolean setSearchText(long query, String searchText); /*
		return SteamUGC()->SetSearchText(query, searchText);
	*/

	static native boolean setRankedByTrendDays(long query, int days); /*
		return SteamUGC()->SetRankedByTrendDays(query, days);
	*/

	static native boolean addRequiredKeyValueTag(long query, String key, String value); /*
		return SteamUGC()->AddRequiredKeyValueTag(query, key, value);
	*/

	static native long requestUGCDetails(long callback, long publishedFileID, int maxAgeSeconds); /*
		SteamUGCCallback* cb = (SteamUGCCallback*) callback;
		SteamAPICall_t handle = SteamUGC()->RequestUGCDetails(publishedFileID, maxAgeSeconds);
		cb->onRequestUGCDetailsCall.Set(handle, cb, &SteamUGCCallback::onRequestUGCDetails);
		return handle;
	*/

	static native long createItem(long callback, int consumerAppID, int fileType); /*
		SteamUGCCallback* cb = (SteamUGCCallback*) callback;
		SteamAPICall_t handle = SteamUGC()->CreateItem(consumerAppID, (EWorkshopFileType) fileType);
		cb->onCreateItemCall.Set(handle, cb, &SteamUGCCallback::onCreateItem);
		return handle;
	*/

	static native long startItemUpdate(int consumerAppID, long publishedFileID); /*
		return SteamUGC()->StartItemUpdate(consumerAppID, publishedFileID);
	*/

	static native boolean setItemTitle(long update, String title); /*
		return SteamUGC()->SetItemTitle(update, title);
	*/

	static native boolean setItemDescription(long update, String description); /*
		return SteamUGC()->SetItemDescription(update, description);
	*/

	static native boolean setItemUpdateLanguage(long update, String language); /*
		return SteamUGC()->SetItemUpdateLanguage(update, language);
	*/

	static native boolean setItemMetadata(long update, String metaData); /*
		return SteamUGC()->SetItemMetadata(update, metaData);
	*/

	static native boolean setItemVisibility(long update, int visibility); /*
		return SteamUGC()->SetItemVisibility(update, (ERemoteStoragePublishedFileVisibility) visibility);
	*/

	static native boolean setItemTags(long update, String[] tags, int numTags); /*
		SteamParamStringArray_t arrayTags;
		arrayTags.m_ppStrings = (numTags > 0) ? new const char*[numTags] : NULL;
		arrayTags.m_nNumStrings = numTags;
		for (int t = 0; t < numTags; t++) {
			arrayTags.m_ppStrings[t] = env->GetStringUTFChars((jstring) env->GetObjectArrayElement(tags, t), 0);
		}

		bool result = SteamUGC()->SetItemTags(update, &arrayTags);

		for (int t = 0; t < numTags; t++) {
			env->ReleaseStringUTFChars((jstring) env->GetObjectArrayElement(tags, t), arrayTags.m_ppStrings[t]);
		}
		delete[] arrayTags.m_ppStrings;

		return result;
	*/

	static native boolean setItemContent(long update, String contentFolder); /*
		return SteamUGC()->SetItemContent(update, contentFolder);
	*/

	static native boolean setItemPreview(long update, String previewFile); /*
		return SteamUGC()->SetItemPreview(update, previewFile);
	*/

	static native boolean removeItemKeyValueTags(long update, String key); /*
		return SteamUGC()->RemoveItemKeyValueTags(update, key);
	*/

	static native boolean addItemKeyValueTag(long update, String key, String value); /*
		return SteamUGC()->AddItemKeyValueTag(update, key, value);
	*/

	static native long submitItemUpdate(long callback, long update, String changeNote); /*
		SteamUGCCallback* cb = (SteamUGCCallback*) callback;
		if (changeNote[0] == '\0') {
			changeNote = nullptr;
		}
		SteamAPICall_t handle = SteamUGC()->SubmitItemUpdate(update, changeNote);
		cb->onSubmitItemUpdateCall.Set(handle, cb, &SteamUGCCallback::onSubmitItemUpdate);
		return handle;
	*/

	static native int getItemUpdateProgress(long update, long[] bytesProcessedAndTotal); /*
		uint64* values = (uint64*) bytesProcessedAndTotal;
		return SteamUGC()->GetItemUpdateProgress(update, &values[0], &values[1]);
	*/

	static native long setUserItemVote(long callback, long publishedFileID, boolean voteUp); /*
		SteamUGCCallback* cb = (SteamUGCCallback*) callback;
		SteamAPICall_t handle = SteamUGC()->SetUserItemVote(publishedFileID, voteUp);
		cb->onSetUserItemVoteCall.Set(handle, cb, &SteamUGCCallback::onSetUserItemVote);
		return handle;
	*/

	static native long getUserItemVote(long callback, long publishedFileID); /*
		SteamUGCCallback* cb = (SteamUGCCallback*) callback;
		SteamAPICall_t handle = SteamUGC()->GetUserItemVote(publishedFileID);
		cb->onGetUserItemVoteCall.Set(handle, cb, &SteamUGCCallback::onGetUserItemVote);
		return handle;
	*/

	static native long addItemToFavorites(long callback, int appID, long publishedFileID); /*
		SteamUGCCallback* cb = (SteamUGCCallback*) callback;
		SteamAPICall_t handle = SteamUGC()->AddItemToFavorites(appID, publishedFileID);
		cb->onUserFavoriteItemsListChangedCall.Set(handle, cb, &SteamUGCCallback::onUserFavoriteItemsListChanged);
		return handle;
	*/

	static native long removeItemFromFavorites(long callback, int appID, long publishedFileID); /*
		SteamUGCCallback* cb = (SteamUGCCallback*) callback;
		SteamAPICall_t handle = SteamUGC()->RemoveItemFromFavorites(appID, publishedFileID);
		cb->onUserFavoriteItemsListChangedCall.Set(handle, cb, &SteamUGCCallback::onUserFavoriteItemsListChanged);
		return handle;
	*/

	static native long subscribeItem(long callback, long publishedFileID); /*
		SteamUGCCallback* cb = (SteamUGCCallback*) callback;
		SteamAPICall_t handle = SteamUGC()->SubscribeItem(publishedFileID);
		cb->onSubscribeItemCall.Set(handle, cb, &SteamUGCCallback::onSubscribeItem);
		return handle;
	 */

	static native long unsubscribeItem(long callback, long publishedFileID); /*
		SteamUGCCallback* cb = (SteamUGCCallback*) callback;
		SteamAPICall_t handle = SteamUGC()->UnsubscribeItem(publishedFileID);
		cb->onUnsubscribeItemCall.Set(handle, cb, &SteamUGCCallback::onUnsubscribeItem);
		return handle;
	 */

	static native int getNumSubscribedItems(); /*
		return SteamUGC()->GetNumSubscribedItems();
	*/

	static native int getSubscribedItems(long[] files, int maxEntries); /*
		return SteamUGC()->GetSubscribedItems((PublishedFileId_t*) files, maxEntries);
	*/

	static native int getItemState(long publishedFileID); /*
		return SteamUGC()->GetItemState(publishedFileID);
	*/

	static native boolean getItemInstallInfo(long publishedFileID, SteamUGC.ItemInstallInfo installInfo); /*
		char folder[1024];
		uint64 sizeOnDisk;
		uint32 timeStamp;

		if (SteamUGC()->GetItemInstallInfo(publishedFileID, &sizeOnDisk, folder, 1024, &timeStamp)) {

			jclass clzz = env->GetObjectClass(installInfo);

			jstring folderString = env->NewStringUTF(folder);
			jfieldID field = env->GetFieldID(clzz, "folder", "Ljava/lang/String;");
			env->SetObjectField(installInfo, field, folderString);

			field = env->GetFieldID(clzz, "sizeOnDisk", "I");
			env->SetIntField(installInfo, field, (jint) sizeOnDisk);

			return true;
		}

		return false;
	*/

	static native boolean getItemDownloadInfo(long publishedFileID,
											  long[] bytesDownloadedAndTotal); /*

		uint64* values = (uint64*) bytesDownloadedAndTotal;
		return SteamUGC()->GetItemDownloadInfo(publishedFileID, &values[0], &values[1]);
	*/

	static native long deleteItem(long callback, long publishedFileID); /*
		SteamUGCCallback* cb = (SteamUGCCallback*) callback;
		SteamAPICall_t handle = SteamUGC()->DeleteItem(publishedFileID);
		cb->onDeleteItemCall.Set(handle, cb, &SteamUGCCallback::onDeleteItem);
		return handle;
	*/

	static native boolean downloadItem(long publishedFileID, boolean highPriority); /*
		return SteamUGC()->DownloadItem(publishedFileID, highPriority);
	*/

	static native boolean initWorkshopForGameServer(int workshopDepotID, String folder); /*
		return SteamUGC()->BInitWorkshopForGameServer(workshopDepotID, folder);
	*/

	static native void suspendDownloads(boolean suspend); /*
		SteamUGC()->SuspendDownloads(suspend);
	*/

	static native long startPlaytimeTracking(long callback, long[] publishedFileIDs, int numPublishedFileIDs); /*
		SteamUGCCallback* cb = (SteamUGCCallback*) callback;
		SteamAPICall_t handle = SteamUGC()->StartPlaytimeTracking((PublishedFileId_t*) publishedFileIDs, numPublishedFileIDs);
		cb->onStartPlaytimeTrackingCall.Set(handle, cb, &SteamUGCCallback::onStartPlaytimeTracking);
		return handle;
	*/

	static native long stopPlaytimeTracking(long callback, long[] publishedFileIDs, int numPublishedFileIDs); /*
		SteamUGCCallback* cb = (SteamUGCCallback*) callback;
		SteamAPICall_t handle = SteamUGC()->StopPlaytimeTracking((PublishedFileId_t*) publishedFileIDs, numPublishedFileIDs);
		cb->onStopPlaytimeTrackingCall.Set(handle, cb, &SteamUGCCallback::onStopPlaytimeTracking);
		return handle;
	*/

	static native long stopPlaytimeTrackingForAllItems(long callback); /*
		SteamUGCCallback* cb = (SteamUGCCallback*) callback;
		SteamAPICall_t handle = SteamUGC()->StopPlaytimeTrackingForAllItems();
		cb->onStopPlaytimeTrackingForAllItemsCall.Set(handle, cb, &SteamUGCCallback::onStopPlaytimeTrackingForAllItems);
		return handle;
	*/

}
