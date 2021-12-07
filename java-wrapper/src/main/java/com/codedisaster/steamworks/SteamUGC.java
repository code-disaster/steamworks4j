package com.codedisaster.steamworks;

import java.util.Collection;
import java.util.EnumSet;

@SuppressWarnings({ "unused", "UnusedReturnValue" })
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
		NumComments,
		NumSecondsPlayedDuringTimePeriod,
		NumPlaytimeSessionsDuringTimePeriod
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

	@SuppressWarnings("unused")
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

	@SuppressWarnings("unused")
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
		super(SteamUGCNative.createCallback(new SteamUGCCallbackAdapter(callback)));
	}

	public SteamUGCQuery createQueryUserUGCRequest(int accountID, UserUGCList listType,
												   MatchingUGCType matchingType, UserUGCListSortOrder sortOrder,
												   int creatorAppID, int consumerAppID, int page) {

		return new SteamUGCQuery(SteamUGCNative.createQueryUserUGCRequest(accountID, listType.ordinal(),
				matchingType.value, sortOrder.ordinal(), creatorAppID, consumerAppID, page));
	}

	public SteamUGCQuery createQueryAllUGCRequest(UGCQueryType queryType, MatchingUGCType matchingType,
												  int creatorAppID, int consumerAppID, int page) {

		return new SteamUGCQuery(SteamUGCNative.createQueryAllUGCRequest(queryType.ordinal(), matchingType.value,
				creatorAppID, consumerAppID, page));
	}

	public SteamUGCQuery createQueryUGCDetailsRequest(SteamPublishedFileID publishedFileID) {
		long[] fileIDs = new long[1];
		fileIDs[0] = publishedFileID.handle;
		return new SteamUGCQuery(SteamUGCNative.createQueryUGCDetailsRequest(fileIDs, 1));
	}

	public SteamUGCQuery createQueryUGCDetailsRequest(Collection<SteamPublishedFileID> publishedFileIDs) {
		int size = publishedFileIDs.size();
		long[] fileIDs = new long[size];

		int index = 0;
		for (SteamPublishedFileID fileID : publishedFileIDs) {
			fileIDs[index++] = fileID.handle;
		}

		return new SteamUGCQuery(SteamUGCNative.createQueryUGCDetailsRequest(fileIDs, size));
	}

	public SteamAPICall sendQueryUGCRequest(SteamUGCQuery query) {
		return new SteamAPICall(SteamUGCNative.sendQueryUGCRequest(callback, query.handle));
	}

	public boolean getQueryUGCResult(SteamUGCQuery query, int index, SteamUGCDetails details) {
		return SteamUGCNative.getQueryUGCResult(query.handle, index, details);
	}

	public String getQueryUGCPreviewURL(SteamUGCQuery query, int index) {
		return SteamUGCNative.getQueryUGCPreviewURL(query.handle, index);
	}

	public String getQueryUGCMetadata(SteamUGCQuery query, int index) {
		return SteamUGCNative.getQueryUGCMetadata(query.handle, index);
	}

	public long getQueryUGCStatistic(SteamUGCQuery query, int index, ItemStatistic statType) {
		return SteamUGCNative.getQueryUGCStatistic(query.handle, index, statType.ordinal());
	}

	public int getQueryUGCNumAdditionalPreviews(SteamUGCQuery query, int index) {
		return SteamUGCNative.getQueryUGCNumAdditionalPreviews(query.handle, index);
	}

	public boolean getQueryUGCAdditionalPreview(SteamUGCQuery query, int index, int previewIndex,
												ItemAdditionalPreview previewInfo) {

		return SteamUGCNative.getQueryUGCAdditionalPreview(query.handle, index, previewIndex, previewInfo);
	}

	public int getQueryUGCNumKeyValueTags(SteamUGCQuery query, int index) {
		return SteamUGCNative.getQueryUGCNumKeyValueTags(query.handle, index);
	}

	public boolean getQueryUGCKeyValueTag(SteamUGCQuery query, int index, int keyValueTagIndex, String[] keyAndValue) {
		return SteamUGCNative.getQueryUGCKeyValueTag(query.handle, index, keyValueTagIndex, keyAndValue);
	}

	public boolean releaseQueryUserUGCRequest(SteamUGCQuery query) {
		return SteamUGCNative.releaseQueryUserUGCRequest(query.handle);
	}

	public boolean addRequiredTag(SteamUGCQuery query, String tagName) {
		return SteamUGCNative.addRequiredTag(query.handle, tagName);
	}

	public boolean addExcludedTag(SteamUGCQuery query, String tagName) {
		return SteamUGCNative.addExcludedTag(query.handle, tagName);
	}

	public boolean setReturnOnlyIDs(SteamUGCQuery query, boolean returnOnlyIDs) {
		return SteamUGCNative.setReturnOnlyIDs(query.handle, returnOnlyIDs);
	}

	public boolean setReturnKeyValueTags(SteamUGCQuery query, boolean returnKeyValueTags) {
		return SteamUGCNative.setReturnKeyValueTags(query.handle, returnKeyValueTags);
	}

	public boolean setReturnLongDescription(SteamUGCQuery query, boolean returnLongDescription) {
		return SteamUGCNative.setReturnLongDescription(query.handle, returnLongDescription);
	}

	public boolean setReturnMetadata(SteamUGCQuery query, boolean returnMetadata) {
		return SteamUGCNative.setReturnMetadata(query.handle, returnMetadata);
	}

	public boolean setReturnChildren(SteamUGCQuery query, boolean returnChildren) {
		return SteamUGCNative.setReturnChildren(query.handle, returnChildren);
	}

	public boolean setReturnAdditionalPreviews(SteamUGCQuery query, boolean returnAdditionalPreviews) {
		return SteamUGCNative.setReturnAdditionalPreviews(query.handle, returnAdditionalPreviews);
	}

	public boolean setReturnTotalOnly(SteamUGCQuery query, boolean returnTotalOnly) {
		return SteamUGCNative.setReturnTotalOnly(query.handle, returnTotalOnly);
	}

	public boolean setReturnPlaytimeStats(SteamUGCQuery query, int days) {
		return SteamUGCNative.setReturnPlaytimeStats(query.handle, days);
	}

	public boolean setLanguage(SteamUGCQuery query, String language) {
		return SteamUGCNative.setLanguage(query.handle, language);
	}

	public boolean setAllowCachedResponse(SteamUGCQuery query, int maxAgeSeconds) {
		return SteamUGCNative.setAllowCachedResponse(query.handle, maxAgeSeconds);
	}

	public boolean setCloudFileNameFilter(SteamUGCQuery query, String matchCloudFileName) {
		return SteamUGCNative.setCloudFileNameFilter(query.handle, matchCloudFileName);
	}

	public boolean setMatchAnyTag(SteamUGCQuery query, boolean matchAnyTag) {
		return SteamUGCNative.setMatchAnyTag(query.handle, matchAnyTag);
	}

	public boolean setSearchText(SteamUGCQuery query, String searchText) {
		return SteamUGCNative.setSearchText(query.handle, searchText);
	}

	public boolean setRankedByTrendDays(SteamUGCQuery query, int days) {
		return SteamUGCNative.setRankedByTrendDays(query.handle, days);
	}

	public boolean addRequiredKeyValueTag(SteamUGCQuery query, String key, String value) {
		return SteamUGCNative.addRequiredKeyValueTag(query.handle, key, value);
	}

	@Deprecated // API docs: use createQueryUGCDetailsRequest call instead
	public SteamAPICall requestUGCDetails(SteamPublishedFileID publishedFileID, int maxAgeSeconds) {
		return new SteamAPICall(SteamUGCNative.requestUGCDetails(callback, publishedFileID.handle, maxAgeSeconds));
	}

	public SteamAPICall createItem(int consumerAppID, SteamRemoteStorage.WorkshopFileType fileType) {
		return new SteamAPICall(SteamUGCNative.createItem(callback, consumerAppID, fileType.ordinal()));
	}

	public SteamUGCUpdateHandle startItemUpdate(int consumerAppID, SteamPublishedFileID publishedFileID) {
		return new SteamUGCUpdateHandle(SteamUGCNative.startItemUpdate(consumerAppID, publishedFileID.handle));
	}

	public boolean setItemTitle(SteamUGCUpdateHandle update, String title) {
		return SteamUGCNative.setItemTitle(update.handle, title);
	}

	public boolean setItemDescription(SteamUGCUpdateHandle update, String description) {
		return SteamUGCNative.setItemDescription(update.handle, description);
	}

	public boolean setItemUpdateLanguage(SteamUGCUpdateHandle update, String language) {
		return SteamUGCNative.setItemUpdateLanguage(update.handle, language);
	}

	public boolean setItemMetadata(SteamUGCUpdateHandle update, String metaData) {
		return SteamUGCNative.setItemMetadata(update.handle, metaData);
	}

	public boolean setItemVisibility(SteamUGCUpdateHandle update,
									 SteamRemoteStorage.PublishedFileVisibility visibility) {

		return SteamUGCNative.setItemVisibility(update.handle, visibility.ordinal());
	}

	public boolean setItemTags(SteamUGCUpdateHandle update, String[] tags) {
		return SteamUGCNative.setItemTags(update.handle, tags, tags.length);
	}

	public boolean setItemContent(SteamUGCUpdateHandle update, String contentFolder) {
		return SteamUGCNative.setItemContent(update.handle, contentFolder);
	}

	public boolean setItemPreview(SteamUGCUpdateHandle update, String previewFile) {
		return SteamUGCNative.setItemPreview(update.handle, previewFile);
	}

	public boolean removeItemKeyValueTags(SteamUGCUpdateHandle update, String key) {
		return SteamUGCNative.removeItemKeyValueTags(update.handle, key);
	}

	public boolean addItemKeyValueTag(SteamUGCUpdateHandle update, String key, String value) {
		return SteamUGCNative.addItemKeyValueTag(update.handle, key, value);
	}

	public SteamAPICall submitItemUpdate(SteamUGCUpdateHandle update, String changeNote) {
		if (changeNote == null) {
			// The native API allows passing NULL, but the wrapper code
			// generated by jnigen doesn't check for null Strings. So we
			// silently convert to an empty string here, and check again
			// before calling the native function.
			changeNote = "";
		}
		return new SteamAPICall(SteamUGCNative.submitItemUpdate(callback, update.handle, changeNote));
	}

	public ItemUpdateStatus getItemUpdateProgress(SteamUGCUpdateHandle update, ItemUpdateInfo updateInfo) {
		long[] values = new long[2];
		ItemUpdateStatus status = ItemUpdateStatus.byOrdinal(SteamUGCNative.getItemUpdateProgress(update.handle, values));
		updateInfo.bytesProcessed = values[0];
		updateInfo.bytesTotal = values[1];
		return status;
	}

	public SteamAPICall setUserItemVote(SteamPublishedFileID publishedFileID, boolean voteUp) {
		return new SteamAPICall(SteamUGCNative.setUserItemVote(callback, publishedFileID.handle, voteUp));
	}

	public SteamAPICall getUserItemVote(SteamPublishedFileID publishedFileID) {
		return new SteamAPICall(SteamUGCNative.getUserItemVote(callback, publishedFileID.handle));
	}

	public SteamAPICall addItemToFavorites(int appID, SteamPublishedFileID publishedFileID) {
		return new SteamAPICall(SteamUGCNative.addItemToFavorites(callback, appID, publishedFileID.handle));
	}

	public SteamAPICall removeItemFromFavorites(int appID, SteamPublishedFileID publishedFileID) {
		return new SteamAPICall(SteamUGCNative.removeItemFromFavorites(callback, appID, publishedFileID.handle));
	}

	public SteamAPICall subscribeItem(SteamPublishedFileID publishedFileID) {
		return new SteamAPICall(SteamUGCNative.subscribeItem(callback, publishedFileID.handle));
	}

	public SteamAPICall unsubscribeItem(SteamPublishedFileID publishedFileID) {
		return new SteamAPICall(SteamUGCNative.unsubscribeItem(callback, publishedFileID.handle));
	}

	public int getNumSubscribedItems() {
		return SteamUGCNative.getNumSubscribedItems();
	}

	public int getSubscribedItems(SteamPublishedFileID[] publishedFileIds) {
		long[] ids = new long[publishedFileIds.length];
		int nb = SteamUGCNative.getSubscribedItems(ids, publishedFileIds.length);

		for (int i = 0; i < nb; i++) {
			publishedFileIds[i] = new SteamPublishedFileID(ids[i]);
		}

		return nb;
	}

	public Collection<ItemState> getItemState(SteamPublishedFileID publishedFileID) {
		return ItemState.fromBits(SteamUGCNative.getItemState(publishedFileID.handle));
	}

	public boolean getItemInstallInfo(SteamPublishedFileID publishedFileID, ItemInstallInfo installInfo) {
		return SteamUGCNative.getItemInstallInfo(publishedFileID.handle, installInfo);
	}

	public boolean getItemDownloadInfo(SteamPublishedFileID publishedFileID, ItemDownloadInfo downloadInfo) {
		long[] values = new long[2];
		if (SteamUGCNative.getItemDownloadInfo(publishedFileID.handle, values)) {
			downloadInfo.bytesDownloaded = values[0];
			downloadInfo.bytesTotal = values[1];
			return true;
		}
		return false;
	}

	public SteamAPICall deleteItem(SteamPublishedFileID publishedFileID) {
		return new SteamAPICall(SteamUGCNative.deleteItem(callback, publishedFileID.handle));
	}

	public boolean downloadItem(SteamPublishedFileID publishedFileID, boolean highPriority) {
		return SteamUGCNative.downloadItem(publishedFileID.handle, highPriority);
	}

	public boolean initWorkshopForGameServer(int workshopDepotID, String folder) {
		return SteamUGCNative.initWorkshopForGameServer(workshopDepotID, folder);
	}

	public void suspendDownloads(boolean suspend) {
		SteamUGCNative.suspendDownloads(suspend);
	}

	public SteamAPICall startPlaytimeTracking(SteamPublishedFileID[] publishedFileIDs) {
		long[] ids = new long[publishedFileIDs.length];

		for (int i = 0; i < ids.length; i++) {
			ids[i] = publishedFileIDs[i].handle;
		}

		return new SteamAPICall(SteamUGCNative.startPlaytimeTracking(callback, ids, ids.length));
	}

	public SteamAPICall stopPlaytimeTracking(SteamPublishedFileID[] publishedFileIDs) {
		long[] ids = new long[publishedFileIDs.length];

		for (int i = 0; i < ids.length; i++) {
			ids[i] = publishedFileIDs[i].handle;
		}

		return new SteamAPICall(SteamUGCNative.stopPlaytimeTracking(callback, ids, ids.length));
	}

	public SteamAPICall stopPlaytimeTrackingForAllItems() {
		return new SteamAPICall(SteamUGCNative.stopPlaytimeTrackingForAllItems(callback));
	}

}
