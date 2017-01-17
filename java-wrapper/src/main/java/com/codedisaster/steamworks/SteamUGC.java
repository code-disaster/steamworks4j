package com.codedisaster.steamworks;

import java.util.Collection;
import java.util.EnumSet;

public class SteamUGC extends SteamInterface {

	public enum UserUGCList {
		Published,
		VotedOn,
		VotedUp,
		VotedDown,
		WillVoteLater,
		Favorited,
		Subscribed,
		UsedOrPlayed,
		Followed
	}

	public enum MatchingUGCType {
		Items(0),
		ItemsMtx(1),
		ItemsReadyToUse(2),
		Collections(3),
		Artwork(4),
		Videos(5),
		Screenshots(6),
		AllGuides(7),
		WebGuides(8),
		IntegratedGuides(9),
		UsableInGame(10),
		ControllerBindings(11),
		GameManagedItems(12),
		All(~0);

		private final int value;

		MatchingUGCType(int value) {
			this.value = value;
		}
	}

	public enum UserUGCListSortOrder {
		CreationOrderDesc,
		CreationOrderAsc,
		TitleAsc,
		LastUpdatedDesc,
		SubscriptionDateDesc,
		VoteScoreDesc,
		ForModeration
	}

	public enum UGCQueryType {
		RankedByVote,
		RankedByPublicationDate,
		AcceptedForGameRankedByAcceptanceDate,
		RankedByTrend,
		FavoritedByFriendsRankedByPublicationDate,
		CreatedByFriendsRankedByPublicationDate,
		RankedByNumTimesReported,
		CreatedByFollowedUsersRankedByPublicationDate,
		NotYetRated,
		RankedByTotalVotesAsc,
		RankedByVotesUp,
		RankedByTextSearch,
		RankedByTotalUniqueSubscriptions,
		RankedByPlaytimeTrend,
		RankedByTotalPlaytime,
		RankedByAveragePlaytimeTrend,
		RankedByLifetimeAveragePlaytime,
		RankedByPlaytimeSessionsTrend,
		RankedByLifetimePlaytimeSessions
	}

	public enum ItemUpdateStatus {
		Invalid,
		PreparingConfig,
		PreparingContent,
		UploadingContent,
		UploadingPreviewFile,
		CommittingChanges;

		private static final ItemUpdateStatus[] values = values();

		static ItemUpdateStatus byOrdinal(int value) {
			return values[value];
		}
	}

	public static class ItemUpdateInfo {
		long bytesProcessed;
		long bytesTotal;

		public long getBytesProcessed() {
			return bytesProcessed;
		}

		public long getBytesTotal() {
			return bytesTotal;
		}
	}

	public enum ItemState {
		None(0),
		Subscribed(1),
		LegacyItem(2),
		Installed(4),
		NeedsUpdate(8),
		Downloading(16),
		DownloadPending(32);

		private final int bits;
		private static final ItemState[] values = values();

		ItemState(int bits) {
			this.bits = bits;
		}

		static Collection<ItemState> fromBits(int bits) {
			EnumSet<ItemState> itemStates = EnumSet.noneOf(ItemState.class);

			for (ItemState itemState : values) {
				if ((bits & itemState.bits) == itemState.bits) {
					itemStates.add(itemState);
				}
			}

			return itemStates;
		}
	}

	public enum ItemStatistic {
		NumSubscriptions,
		NumFavorites,
		NumFollowers,
		NumUniqueSubscriptions,
		NumUniqueFavorites,
		NumUniqueFollowers,
		NumUniqueWebsiteViews,
		ReportScore,
		NumSecondsPlayed,
		NumPlaytimeSessions,
		NumComments
	}

	public enum ItemPreviewType {
		Image(0),
		YouTubeVideo(1),
		Sketchfab(2),
		EnvironmentMap_HorizontalCross(3),
		EnvironmentMap_LatLong(4),
		ReservedMax(255),

		UnknownPreviewType_NotImplementedByAPI(-1);

		private final int value;
		private static final ItemPreviewType[] values = values();

		ItemPreviewType(int value) {
			this.value = value;
		}

		static ItemPreviewType byValue(int value) {
			for (ItemPreviewType type : values) {
				if (type.value == value) {
					return type;
				}
			}
			return UnknownPreviewType_NotImplementedByAPI;
		}
	}

	public static class ItemInstallInfo {
		private String folder;
		private int sizeOnDisk;

		public String getFolder() {
			return folder;
		}

		public int getSizeOnDisk() {
			return sizeOnDisk;
		}
	}
	
	public static class ItemDownloadInfo {
		long bytesDownloaded;
		long bytesTotal;

		public long getBytesDownloaded() {
			return bytesDownloaded;
		}

		public long getBytesTotal() {
			return bytesTotal;
		}
	}

	public static class ItemAdditionalPreview {
		private String urlOrVideoID;
		private String originalFileName;
		private int previewType;

		public String getUrlOrVideoID() {
			return urlOrVideoID;
		}

		public String getOriginalFileName() {
			return originalFileName;
		}

		public ItemPreviewType getPreviewType() {
			return ItemPreviewType.byValue(previewType);
		}
	}

	public SteamUGC(SteamUGCCallback callback) {
		super(SteamAPI.getSteamUGCPointer(), createCallback(new SteamUGCCallbackAdapter(callback)));
	}

	public SteamUGCQuery createQueryUserUGCRequest(long accountID, UserUGCList listType,
												   MatchingUGCType matchingType, UserUGCListSortOrder sortOrder,
												   int creatorAppID, int consumerAppID, int page) {

		return new SteamUGCQuery(createQueryUserUGCRequest(pointer, accountID, listType.ordinal(),
				matchingType.value, sortOrder.ordinal(), creatorAppID, consumerAppID, page));
	}

	public SteamUGCQuery createQueryAllUGCRequest(UGCQueryType queryType, MatchingUGCType matchingType,
												  int creatorAppID, int consumerAppID, int page) {

		return new SteamUGCQuery(createQueryAllUGCRequest(pointer, queryType.ordinal(), matchingType.value,
				creatorAppID, consumerAppID, page));
	}

	public SteamUGCQuery createQueryUGCDetailsRequest(SteamPublishedFileID publishedFileID) {
		long[] fileIDs = new long[1];
		fileIDs[0] = publishedFileID.handle;
		return new SteamUGCQuery(createQueryUGCDetailsRequest(pointer, fileIDs, 1));
	}

	public SteamUGCQuery createQueryUGCDetailsRequest(Collection<SteamPublishedFileID> publishedFileIDs) {
		int size = publishedFileIDs.size();
		long[] fileIDs = new long[size];

		int index = 0;
		for (SteamPublishedFileID fileID : publishedFileIDs) {
			fileIDs[index++] = fileID.handle;
		}

		return new SteamUGCQuery(createQueryUGCDetailsRequest(pointer, fileIDs, size));
	}

	public SteamAPICall sendQueryUGCRequest(SteamUGCQuery query) {
		return new SteamAPICall(sendQueryUGCRequest(pointer, callback, query.handle));
	}

	public boolean getQueryUGCResult(SteamUGCQuery query, int index, SteamUGCDetails details) {
		return getQueryUGCResult(pointer, query.handle, index, details);
	}

	public String getQueryUGCPreviewURL(SteamUGCQuery query, int index) {
		return getQueryUGCPreviewURL(pointer, query.handle, index);
	}

	public String getQueryUGCMetadata(SteamUGCQuery query, int index) {
		return getQueryUGCMetadata(pointer, query.handle, index);
	}

	public long getQueryUGCStatistic(SteamUGCQuery query, int index, ItemStatistic statType) {
		return getQueryUGCStatistic(pointer, query.handle, index, statType.ordinal());
	}

	public int getQueryUGCNumAdditionalPreviews(SteamUGCQuery query, int index) {
		return getQueryUGCNumAdditionalPreviews(pointer, query.handle, index);
	}

	public boolean getQueryUGCAdditionalPreview(SteamUGCQuery query, int index, int previewIndex,
												ItemAdditionalPreview previewInfo) {

		return getQueryUGCAdditionalPreview(pointer, query.handle, index, previewIndex, previewInfo);
	}

	public int getQueryUGCNumKeyValueTags(SteamUGCQuery query, int index) {
		return getQueryUGCNumKeyValueTags(pointer, query.handle, index);
	}

	public boolean getQueryUGCKeyValueTag(SteamUGCQuery query, int index, int keyValueTagIndex, String[] keyAndValue) {
		return getQueryUGCKeyValueTag(pointer, query.handle, index, keyValueTagIndex, keyAndValue);
	}

	public boolean releaseQueryUserUGCRequest(SteamUGCQuery query) {
		return releaseQueryUserUGCRequest(pointer, query.handle);
	}

	public boolean addRequiredTag(SteamUGCQuery query, String tagName) {
		return addRequiredTag(pointer, query.handle, tagName);
	}

	public boolean addExcludedTag(SteamUGCQuery query, String tagName) {
		return addExcludedTag(pointer, query.handle, tagName);
	}

	public boolean setReturnOnlyIDs(SteamUGCQuery query, boolean returnOnlyIDs) {
		return setReturnOnlyIDs(pointer, query.handle, returnOnlyIDs);
	}

	public boolean setReturnKeyValueTags(SteamUGCQuery query, boolean returnKeyValueTags) {
		return setReturnKeyValueTags(pointer, query.handle, returnKeyValueTags);
	}

	public boolean setReturnLongDescription(SteamUGCQuery query, boolean returnLongDescription) {
		return setReturnLongDescription(pointer, query.handle, returnLongDescription);
	}

	public boolean setReturnMetadata(SteamUGCQuery query, boolean returnMetadata) {
		return setReturnMetadata(pointer, query.handle, returnMetadata);
	}

	public boolean setReturnChildren(SteamUGCQuery query, boolean returnChildren) {
		return setReturnChildren(pointer, query.handle, returnChildren);
	}

	public boolean setReturnAdditionalPreviews(SteamUGCQuery query, boolean returnAdditionalPreviews) {
		return setReturnAdditionalPreviews(pointer, query.handle, returnAdditionalPreviews);
	}

	public boolean setReturnTotalOnly(SteamUGCQuery query, boolean returnTotalOnly) {
		return setReturnTotalOnly(pointer, query.handle, returnTotalOnly);
	}

	public boolean setLanguage(SteamUGCQuery query, String language) {
		return setLanguage(pointer, query.handle, language);
	}

	public boolean setAllowCachedResponse(SteamUGCQuery query, int maxAgeSeconds) {
		return setAllowCachedResponse(pointer, query.handle, maxAgeSeconds);
	}

	public boolean setCloudFileNameFilter(SteamUGCQuery query, String matchCloudFileName) {
		return setCloudFileNameFilter(pointer, query.handle, matchCloudFileName);
	}

	public boolean setMatchAnyTag(SteamUGCQuery query, boolean matchAnyTag) {
		return setMatchAnyTag(pointer, query.handle, matchAnyTag);
	}

	public boolean setSearchText(SteamUGCQuery query, String searchText) {
		return setSearchText(pointer, query.handle, searchText);
	}

	public boolean setRankedByTrendDays(SteamUGCQuery query, int days) {
		return setRankedByTrendDays(pointer, query.handle, days);
	}

	public boolean addRequiredKeyValueTag(SteamUGCQuery query, String key, String value) {
		return addRequiredKeyValueTag(pointer, query.handle, key, value);
	}

	@Deprecated // API docs: use createQueryUGCDetailsRequest call instead
	public SteamAPICall requestUGCDetails(SteamPublishedFileID publishedFileID, int maxAgeSeconds) {
		return new SteamAPICall(requestUGCDetails(pointer, callback, publishedFileID.handle, maxAgeSeconds));
	}

	public SteamAPICall createItem(int consumerAppID, SteamRemoteStorage.WorkshopFileType fileType) {
		return new SteamAPICall(createItem(pointer, callback, consumerAppID, fileType.ordinal()));
	}

	public SteamUGCUpdateHandle startItemUpdate(int consumerAppID, SteamPublishedFileID publishedFileID) {
		return new SteamUGCUpdateHandle(startItemUpdate(pointer, consumerAppID, publishedFileID.handle));
	}

	public boolean setItemTitle(SteamUGCUpdateHandle update, String title) {
		return setItemTitle(pointer, update.handle, title);
	}

	public boolean setItemDescription(SteamUGCUpdateHandle update, String description) {
		return setItemDescription(pointer, update.handle, description);
	}

	public boolean setItemUpdateLanguage(SteamUGCUpdateHandle update, String language) {
		return setItemUpdateLanguage(pointer, update.handle, language);
	}

	public boolean setItemMetadata(SteamUGCUpdateHandle update, String metaData) {
		return setItemMetadata(pointer, update.handle, metaData);
	}

	public boolean setItemVisibility(SteamUGCUpdateHandle update,
									 SteamRemoteStorage.PublishedFileVisibility visibility) {

		return setItemVisibility(pointer, update.handle, visibility.ordinal());
	}

	public boolean setItemTags(SteamUGCUpdateHandle update, String[] tags) {
		return setItemTags(pointer, update.handle, tags, tags.length);
	}

	public boolean setItemContent(SteamUGCUpdateHandle update, String contentFolder) {
		return setItemContent(pointer, update.handle, contentFolder);
	}

	public boolean setItemPreview(SteamUGCUpdateHandle update, String previewFile) {
		return setItemPreview(pointer, update.handle, previewFile);
	}

	public boolean removeItemKeyValueTags(SteamUGCUpdateHandle update, String key) {
		return removeItemKeyValueTags(pointer, update.handle, key);
	}

	public boolean addItemKeyValueTag(SteamUGCUpdateHandle update, String key, String value) {
		return addItemKeyValueTag(pointer, update.handle, key, value);
	}

	public SteamAPICall submitItemUpdate(SteamUGCUpdateHandle update, String changeNote) {
		return new SteamAPICall(submitItemUpdate(pointer, callback, update.handle, changeNote));
	}

	public ItemUpdateStatus getItemUpdateProgress(SteamUGCUpdateHandle update, ItemUpdateInfo updateInfo) {
		long[] values = new long[2];
		ItemUpdateStatus status = ItemUpdateStatus.byOrdinal(getItemUpdateProgress(pointer, update.handle, values));
		updateInfo.bytesProcessed = values[0];
		updateInfo.bytesTotal = values[1];
		return status;
	}

	public SteamAPICall setUserItemVote(SteamPublishedFileID publishedFileID, boolean voteUp) {
		return new SteamAPICall(setUserItemVote(pointer, callback, publishedFileID.handle, voteUp));
	}

	public SteamAPICall getUserItemVote(SteamPublishedFileID publishedFileID) {
		return new SteamAPICall(getUserItemVote(pointer, callback, publishedFileID.handle));
	}

	public SteamAPICall addItemToFavorites(int appID, SteamPublishedFileID publishedFileID) {
		return new SteamAPICall(addItemToFavorites(pointer, callback, appID, publishedFileID.handle));
	}

	public SteamAPICall removeItemFromFavorites(int appID, SteamPublishedFileID publishedFileID) {
		return new SteamAPICall(removeItemFromFavorites(pointer, callback, appID, publishedFileID.handle));
	}

	public SteamAPICall subscribeItem(SteamPublishedFileID publishedFileID) {
		return new SteamAPICall(subscribeItem(pointer, callback, publishedFileID.handle));
	}

	public SteamAPICall unsubscribeItem(SteamPublishedFileID publishedFileID) {
		return new SteamAPICall(unsubscribeItem(pointer, callback, publishedFileID.handle));
	}

	public int getNumSubscribedItems() {
		return getNumSubscribedItems(pointer);
	}

	public int getSubscribedItems(SteamPublishedFileID[] publishedFileIds) {
		long[] ids = new long[publishedFileIds.length];
		int nb = getSubscribedItems(pointer, ids, publishedFileIds.length);

		for (int i = 0; i < nb; i++) {
			publishedFileIds[i] = new SteamPublishedFileID(ids[i]);
		}

		return nb;
	}
	
	public Collection<ItemState> getItemState(SteamPublishedFileID publishedFileID) {
		return ItemState.fromBits(getItemState(pointer, publishedFileID.handle));
	}
	
	public boolean getItemInstallInfo(SteamPublishedFileID publishedFileID, ItemInstallInfo installInfo) {
		return getItemInstallInfo(pointer, publishedFileID.handle, installInfo);
	}
	
	public boolean getItemDownloadInfo(SteamPublishedFileID publishedFileID, ItemDownloadInfo downloadInfo) {
		long[] values = new long[2];
		if (getItemDownloadInfo(pointer, publishedFileID.handle, values)) {
			downloadInfo.bytesDownloaded = values[0];
			downloadInfo.bytesTotal = values[1];
			return true;
		}
		return false;
	}

	public boolean downloadItem(SteamPublishedFileID publishedFileID, boolean highPriority) {
		return downloadItem(pointer, publishedFileID.handle, highPriority);
	}

	public boolean initWorkshopForGameServer(int workshopDepotID, String folder) {
		return initWorkshopForGameServer(pointer, workshopDepotID, folder);
	}

	public void suspendDownloads(boolean suspend) {
		suspendDownloads(pointer, suspend);
	}

	public SteamAPICall startPlaytimeTracking(SteamPublishedFileID[] publishedFileIDs) {
		long[] ids = new long[publishedFileIDs.length];

		for (int i = 0; i < ids.length; i++) {
			ids[i] = publishedFileIDs[i].handle;
		}

		return new SteamAPICall(startPlaytimeTracking(pointer, callback, ids, ids.length));
	}

	public SteamAPICall stopPlaytimeTracking(SteamPublishedFileID[] publishedFileIDs) {
		long[] ids = new long[publishedFileIDs.length];

		for (int i = 0; i < ids.length; i++) {
			ids[i] = publishedFileIDs[i].handle;
		}

		return new SteamAPICall(stopPlaytimeTracking(pointer, callback, ids, ids.length));
	}

	public SteamAPICall stopPlaytimeTrackingForAllItems() {
		return new SteamAPICall(stopPlaytimeTrackingForAllItems(pointer, callback));
	}

	// @off

	/*JNI
		#include "SteamUGCCallback.h"
	*/

	private static native long createCallback(SteamUGCCallbackAdapter javaCallback); /*
		return (intp) new SteamUGCCallback(env, javaCallback);
	*/

	private static native long createQueryUserUGCRequest(long pointer, long accountID, int listType,
														 int matchingType, int sortOrder,
														 int creatorAppID, int consumerAppID, int page); /*
		ISteamUGC* ugc = (ISteamUGC*) pointer;
		UGCQueryHandle_t query = ugc->CreateQueryUserUGCRequest(accountID, (EUserUGCList) listType,
			(EUGCMatchingUGCType) matchingType, (EUserUGCListSortOrder) sortOrder, creatorAppID, consumerAppID, page);
		return (intp) query;
	*/

	private static native long createQueryAllUGCRequest(long pointer, int queryType, int matchingType,
														int creatorAppID, int consumerAppID, int page); /*
		ISteamUGC* ugc = (ISteamUGC*) pointer;
		UGCQueryHandle_t query = ugc->CreateQueryAllUGCRequest((EUGCQuery) queryType,
			(EUGCMatchingUGCType) matchingType, creatorAppID, consumerAppID, page);
		return (intp) query;
	*/

	private static native long createQueryUGCDetailsRequest(long pointer, long[] publishedFileIDs, int numPublishedFileIDs); /*
		ISteamUGC* ugc = (ISteamUGC*) pointer;
		UGCQueryHandle_t query = ugc->CreateQueryUGCDetailsRequest((PublishedFileId_t*) publishedFileIDs, numPublishedFileIDs);
		return (intp) query;
	*/

	private static native long sendQueryUGCRequest(long pointer, long callback, long query); /*
		ISteamUGC* ugc = (ISteamUGC*) pointer;
		SteamUGCCallback* cb = (SteamUGCCallback*) callback;
		SteamAPICall_t handle = ugc->SendQueryUGCRequest(query);
		cb->onUGCQueryCompletedCall.Set(handle, cb, &SteamUGCCallback::onUGCQueryCompleted);
		return handle;
	*/

	private static native boolean getQueryUGCResult(long pointer, long query, int index, SteamUGCDetails details); /*
		ISteamUGC* ugc = (ISteamUGC*) pointer;
		SteamUGCDetails_t result;

		if (ugc->GetQueryUGCResult(query, index, &result)) {
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
			env->SetIntField(details, field, (jboolean) result.m_bTagsTruncated);

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

	private static native String getQueryUGCPreviewURL(long pointer, long query, int index); /*
		char url[1024];

		ISteamUGC* ugc = (ISteamUGC*) pointer;
		if (ugc->GetQueryUGCPreviewURL(query, index, url, 1024)) {
			return env->NewStringUTF(url);
		}

		return nullptr;
	*/

	private static native String getQueryUGCMetadata(long pointer, long query, int index); /*
		char metadata[k_cchDeveloperMetadataMax];

		ISteamUGC* ugc = (ISteamUGC*) pointer;
		if (ugc->GetQueryUGCMetadata(query, index, metadata, k_cchDeveloperMetadataMax)) {
			return env->NewStringUTF(metadata);
		}

		return nullptr;
	*/

//	private static native boolean getQueryUGCChildren(long pointer, long query, int index,
//													  long[] publishedFileIDs, long maxEntries);

	private static native long getQueryUGCStatistic(long pointer, long query, int index, int statType); /*
		uint64 statValue;

		ISteamUGC* ugc = (ISteamUGC*) pointer;
		if (ugc->GetQueryUGCStatistic(query, index, (EItemStatistic) statType, &statValue)) {
			return statValue;
		}

		return 0;
	*/

	private static native int getQueryUGCNumAdditionalPreviews(long pointer, long query, int index); /*
		ISteamUGC* ugc = (ISteamUGC*) pointer;
		return ugc->GetQueryUGCNumAdditionalPreviews(query, index);
	*/

	private static native boolean getQueryUGCAdditionalPreview(long pointer, long query, int index,
															   int previewIndex, ItemAdditionalPreview previewData); /*
		char url[1024];
		char fileName[1024];
		EItemPreviewType type;

		ISteamUGC* ugc = (ISteamUGC*) pointer;
		bool success = ugc->GetQueryUGCAdditionalPreview(query, index, previewIndex, url, 1024, fileName, 1024, &type);

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

	private static native int getQueryUGCNumKeyValueTags(long pointer, long query, int index); /*
		ISteamUGC* ugc = (ISteamUGC*) pointer;
		return ugc->GetQueryUGCNumKeyValueTags(query, index);
	*/

	private static native boolean getQueryUGCKeyValueTag(long pointer, long query, int index, int keyValueTagIndex, String[] keyAndValue); /*
		char key[1024];
		char value[1024];

		ISteamUGC* ugc = (ISteamUGC*) pointer;
		bool success = ugc->GetQueryUGCKeyValueTag(query, index, keyValueTagIndex, key, 1024, value, 1024);

		if (success) {
			env->SetObjectArrayElement(keyAndValue, 0, env->NewStringUTF(key));
			env->SetObjectArrayElement(keyAndValue, 1, env->NewStringUTF(value));
		}

		return success;
	*/

	private static native boolean releaseQueryUserUGCRequest(long pointer, long query); /*
		ISteamUGC* ugc = (ISteamUGC*) pointer;
		return ugc->ReleaseQueryUGCRequest(query);
	*/

	private static native boolean addRequiredTag(long pointer, long query, String tagName); /*
		ISteamUGC* ugc = (ISteamUGC*) pointer;
		return ugc->AddRequiredTag(query, tagName);
	*/

	private static native boolean addExcludedTag(long pointer, long query, String tagName); /*
		ISteamUGC* ugc = (ISteamUGC*) pointer;
		return ugc->AddExcludedTag(query, tagName);
	*/

	private static native boolean setReturnOnlyIDs(long pointer, long query, boolean returnOnlyIDs); /*
		ISteamUGC* ugc = (ISteamUGC*) pointer;
		return ugc->SetReturnOnlyIDs(query, returnOnlyIDs);
	*/

	private static native boolean setReturnKeyValueTags(long pointer, long query, boolean returnKeyValueTags); /*
		ISteamUGC* ugc = (ISteamUGC*) pointer;
		return ugc->SetReturnKeyValueTags(query, returnKeyValueTags);
	*/

	private static native boolean setReturnLongDescription(long pointer, long query, boolean returnLongDescription); /*
		ISteamUGC* ugc = (ISteamUGC*) pointer;
		return ugc->SetReturnLongDescription(query, returnLongDescription);
	*/

	private static native boolean setReturnMetadata(long pointer, long query, boolean returnMetadata); /*
		ISteamUGC* ugc = (ISteamUGC*) pointer;
		return ugc->SetReturnMetadata(query, returnMetadata);
	*/

	private static native boolean setReturnChildren(long pointer, long query, boolean returnChildren); /*
		ISteamUGC* ugc = (ISteamUGC*) pointer;
		return ugc->SetReturnChildren(query, returnChildren);
	*/

	private static native boolean setReturnAdditionalPreviews(long pointer, long query, boolean returnAdditionalPreviews); /*
		ISteamUGC* ugc = (ISteamUGC*) pointer;
		return ugc->SetReturnAdditionalPreviews(query, returnAdditionalPreviews);
	*/

	private static native boolean setReturnTotalOnly(long pointer, long query, boolean returnTotalOnly); /*
		ISteamUGC* ugc = (ISteamUGC*) pointer;
		return ugc->SetReturnTotalOnly(query, returnTotalOnly);
	*/

	private static native boolean setLanguage(long pointer, long query, String language); /*
		ISteamUGC* ugc = (ISteamUGC*) pointer;
		return ugc->SetLanguage(query, language);
	*/

	private static native boolean setAllowCachedResponse(long pointer, long query, int maxAgeSeconds); /*
		ISteamUGC* ugc = (ISteamUGC*) pointer;
		return ugc->SetAllowCachedResponse(query, maxAgeSeconds);
	*/

	private static native boolean setCloudFileNameFilter(long pointer, long query, String matchCloudFileName); /*
		ISteamUGC* ugc = (ISteamUGC*) pointer;
		return ugc->SetCloudFileNameFilter(query, matchCloudFileName);
	*/

	private static native boolean setMatchAnyTag(long pointer, long query, boolean matchAnyTag); /*
		ISteamUGC* ugc = (ISteamUGC*) pointer;
		return ugc->SetMatchAnyTag(query, matchAnyTag);
	*/

	private static native boolean setSearchText(long pointer, long query, String searchText); /*
		ISteamUGC* ugc = (ISteamUGC*) pointer;
		return ugc->SetSearchText(query, searchText);
	*/

	private static native boolean setRankedByTrendDays(long pointer, long query, int days); /*
		ISteamUGC* ugc = (ISteamUGC*) pointer;
		return ugc->SetRankedByTrendDays(query, days);
	*/

	private static native boolean addRequiredKeyValueTag(long pointer, long query, String key, String value); /*
		ISteamUGC* ugc = (ISteamUGC*) pointer;
		return ugc->AddRequiredKeyValueTag(query, key, value);
	*/

	private static native long requestUGCDetails(long pointer, long callback, long publishedFileID, int maxAgeSeconds); /*
		ISteamUGC* ugc = (ISteamUGC*) pointer;
		SteamUGCCallback* cb = (SteamUGCCallback*) callback;
		SteamAPICall_t handle = ugc->RequestUGCDetails(publishedFileID, maxAgeSeconds);
		cb->onRequestUGCDetailsCall.Set(handle, cb, &SteamUGCCallback::onRequestUGCDetails);
		return handle;
	*/

	private static native long createItem(long pointer, long callback, int consumerAppID, int fileType); /*
		ISteamUGC* ugc = (ISteamUGC*) pointer;
		SteamUGCCallback* cb = (SteamUGCCallback*) callback;
		SteamAPICall_t handle = ugc->CreateItem(consumerAppID, (EWorkshopFileType) fileType);
		cb->onCreateItemCall.Set(handle, cb, &SteamUGCCallback::onCreateItem);
		return handle;
	*/

	private static native long startItemUpdate(long pointer, int consumerAppID, long publishedFileID); /*
		ISteamUGC* ugc = (ISteamUGC*) pointer;
		return ugc->StartItemUpdate(consumerAppID, publishedFileID);
	*/

	private static native boolean setItemTitle(long pointer, long update, String title); /*
		ISteamUGC* ugc = (ISteamUGC*) pointer;
		return ugc->SetItemTitle(update, title);
	*/

	private static native boolean setItemDescription(long pointer, long update, String description); /*
		ISteamUGC* ugc = (ISteamUGC*) pointer;
		return ugc->SetItemDescription(update, description);
	*/

	private static native boolean setItemUpdateLanguage(long pointer, long update, String language); /*
		ISteamUGC* ugc = (ISteamUGC*) pointer;
		return ugc->SetItemUpdateLanguage(update, language);
	*/

	private static native boolean setItemMetadata(long pointer, long update, String metaData); /*
		ISteamUGC* ugc = (ISteamUGC*) pointer;
		return ugc->SetItemMetadata(update, metaData);
	*/

	private static native boolean setItemVisibility(long pointer, long update, int visibility); /*
		ISteamUGC* ugc = (ISteamUGC*) pointer;
		return ugc->SetItemVisibility(update, (ERemoteStoragePublishedFileVisibility) visibility);
	*/

	private static native boolean setItemTags(long pointer, long update, String[] tags, int numTags); /*
		SteamParamStringArray_t arrayTags;
		arrayTags.m_ppStrings = (numTags > 0) ? new const char*[numTags] : NULL;
		arrayTags.m_nNumStrings = numTags;
		for (int t = 0; t < numTags; t++) {
			arrayTags.m_ppStrings[t] = env->GetStringUTFChars((jstring) env->GetObjectArrayElement(tags, t), 0);
		}

		ISteamUGC* ugc = (ISteamUGC*) pointer;
		bool result = ugc->SetItemTags(update, &arrayTags);

		for (int t = 0; t < numTags; t++) {
			env->ReleaseStringUTFChars((jstring) env->GetObjectArrayElement(tags, t), arrayTags.m_ppStrings[t]);
		}
		delete[] arrayTags.m_ppStrings;

		return result;
	*/

	private static native boolean setItemContent(long pointer, long update, String contentFolder); /*
		ISteamUGC* ugc = (ISteamUGC*) pointer;
		return ugc->SetItemContent(update, contentFolder);
	*/

	private static native boolean setItemPreview(long pointer, long update, String previewFile); /*
		ISteamUGC* ugc = (ISteamUGC*) pointer;
		return ugc->SetItemPreview(update, previewFile);
	*/

	private static native boolean removeItemKeyValueTags(long pointer, long update, String key); /*
		ISteamUGC* ugc = (ISteamUGC*) pointer;
		return ugc->RemoveItemKeyValueTags(update, key);
	*/

	private static native boolean addItemKeyValueTag(long pointer, long update, String key, String value); /*
		ISteamUGC* ugc = (ISteamUGC*) pointer;
		return ugc->AddItemKeyValueTag(update, key, value);
	*/

	private static native long submitItemUpdate(long pointer, long callback, long update, String changeNote); /*
		ISteamUGC* ugc = (ISteamUGC*) pointer;
		SteamUGCCallback* cb = (SteamUGCCallback*) callback;
		SteamAPICall_t handle = ugc->SubmitItemUpdate(update, changeNote);
		cb->onSubmitItemUpdateCall.Set(handle, cb, &SteamUGCCallback::onSubmitItemUpdate);
		return handle;
	*/

	private static native int getItemUpdateProgress(long pointer, long update, long[] bytesProcessedAndTotal); /*
		ISteamUGC* ugc = (ISteamUGC*) pointer;
		uint64* values = (uint64*) bytesProcessedAndTotal;
		return ugc->GetItemUpdateProgress(update, &values[0], &values[1]);
	*/

	private static native long setUserItemVote(long pointer, long callback, long publishedFileID, boolean voteUp); /*
		ISteamUGC* ugc = (ISteamUGC*) pointer;
		SteamUGCCallback* cb = (SteamUGCCallback*) callback;
		SteamAPICall_t handle = ugc->SetUserItemVote(publishedFileID, voteUp);
		cb->onSetUserItemVoteCall.Set(handle, cb, &SteamUGCCallback::onSetUserItemVote);
		return handle;
	*/

	private static native long getUserItemVote(long pointer, long callback, long publishedFileID); /*
		ISteamUGC* ugc = (ISteamUGC*) pointer;
		SteamUGCCallback* cb = (SteamUGCCallback*) callback;
		SteamAPICall_t handle = ugc->GetUserItemVote(publishedFileID);
		cb->onGetUserItemVoteCall.Set(handle, cb, &SteamUGCCallback::onGetUserItemVote);
		return handle;
	*/

	private static native long addItemToFavorites(long pointer, long callback, int appID, long publishedFileID); /*
		ISteamUGC* ugc = (ISteamUGC*) pointer;
		SteamUGCCallback* cb = (SteamUGCCallback*) callback;
		SteamAPICall_t handle = ugc->AddItemToFavorites(appID, publishedFileID);
		cb->onUserFavoriteItemsListChangedCall.Set(handle, cb, &SteamUGCCallback::onUserFavoriteItemsListChanged);
		return handle;
	*/

	private static native long removeItemFromFavorites(long pointer, long callback, int appID, long publishedFileID); /*
		ISteamUGC* ugc = (ISteamUGC*) pointer;
		SteamUGCCallback* cb = (SteamUGCCallback*) callback;
		SteamAPICall_t handle = ugc->RemoveItemFromFavorites(appID, publishedFileID);
		cb->onUserFavoriteItemsListChangedCall.Set(handle, cb, &SteamUGCCallback::onUserFavoriteItemsListChanged);
		return handle;
	*/

	private static native long subscribeItem(long pointer, long callback, long publishedFileID); /*
		ISteamUGC* ugc = (ISteamUGC*) pointer;
		SteamUGCCallback* cb = (SteamUGCCallback*) callback;
		SteamAPICall_t handle = ugc->SubscribeItem(publishedFileID);
		cb->onSubscribeItemCall.Set(handle, cb, &SteamUGCCallback::onSubscribeItem);
		return handle;
	 */

	private static native long unsubscribeItem(long pointer, long callback, long publishedFileID); /*
		ISteamUGC* ugc = (ISteamUGC*) pointer;
		SteamUGCCallback* cb = (SteamUGCCallback*) callback;
		SteamAPICall_t handle = ugc->UnsubscribeItem(publishedFileID);
		cb->onUnsubscribeItemCall.Set(handle, cb, &SteamUGCCallback::onUnsubscribeItem);
		return handle;
	 */

	private static native int getNumSubscribedItems(long pointer); /*
		ISteamUGC* ugc = (ISteamUGC*) pointer;
		return ugc->GetNumSubscribedItems();
	*/

	private static native int getSubscribedItems(long pointer, long[] files, int maxEntries); /*
		ISteamUGC* ugc = (ISteamUGC*) pointer;
		return ugc->GetSubscribedItems((PublishedFileId_t*) files, maxEntries);
	*/
	
	private static native int getItemState(long pointer, long publishedFileID); /*
		ISteamUGC* ugc = (ISteamUGC*) pointer;
		return ugc->GetItemState(publishedFileID);
	*/

	private static native boolean getItemInstallInfo(long pointer, long publishedFileID, ItemInstallInfo installInfo); /*
		ISteamUGC* ugc = (ISteamUGC*) pointer;

		char folder[1024];
		uint64 sizeOnDisk;
		uint32 timeStamp;

		if (ugc->GetItemInstallInfo(publishedFileID, &sizeOnDisk, folder, 1024, &timeStamp)) {

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
	
	private static native boolean getItemDownloadInfo(long pointer, long publishedFileID,
													  long[] bytesDownloadedAndTotal); /*

		ISteamUGC* ugc = (ISteamUGC*) pointer;
		uint64* values = (uint64*) bytesDownloadedAndTotal;
		return ugc->GetItemDownloadInfo(publishedFileID, &values[0], &values[1]);
	*/

	private static native boolean downloadItem(long pointer, long publishedFileID, boolean highPriority); /*
		ISteamUGC* ugc = (ISteamUGC*) pointer;
		return ugc->DownloadItem(publishedFileID, highPriority);
	*/

	private static native boolean initWorkshopForGameServer(long pointer, int workshopDepotID, String folder); /*
		ISteamUGC* ugc = (ISteamUGC*) pointer;
		return ugc->BInitWorkshopForGameServer(workshopDepotID, folder);
	*/

	private static native void suspendDownloads(long pointer, boolean suspend); /*
		ISteamUGC* ugc = (ISteamUGC*) pointer;
		ugc->SuspendDownloads(suspend);
	*/

	private static native long startPlaytimeTracking(long pointer, long callback, long[] publishedFileIDs, int numPublishedFileIDs); /*
		ISteamUGC* ugc = (ISteamUGC*) pointer;
		SteamUGCCallback* cb = (SteamUGCCallback*) callback;
		SteamAPICall_t handle = ugc->StartPlaytimeTracking((PublishedFileId_t*) publishedFileIDs, numPublishedFileIDs);
		cb->onStartPlaytimeTrackingCall.Set(handle, cb, &SteamUGCCallback::onStartPlaytimeTracking);
		return handle;
	*/

	private static native long stopPlaytimeTracking(long pointer, long callback, long[] publishedFileIDs, int numPublishedFileIDs); /*
		ISteamUGC* ugc = (ISteamUGC*) pointer;
		SteamUGCCallback* cb = (SteamUGCCallback*) callback;
		SteamAPICall_t handle = ugc->StopPlaytimeTracking((PublishedFileId_t*) publishedFileIDs, numPublishedFileIDs);
		cb->onStopPlaytimeTrackingCall.Set(handle, cb, &SteamUGCCallback::onStopPlaytimeTracking);
		return handle;
	*/

	private static native long stopPlaytimeTrackingForAllItems(long pointer, long callback); /*
		ISteamUGC* ugc = (ISteamUGC*) pointer;
		SteamUGCCallback* cb = (SteamUGCCallback*) callback;
		SteamAPICall_t handle = ugc->StopPlaytimeTrackingForAllItems();
		cb->onStopPlaytimeTrackingForAllItemsCall.Set(handle, cb, &SteamUGCCallback::onStopPlaytimeTrackingForAllItems);
		return handle;
	*/

}
