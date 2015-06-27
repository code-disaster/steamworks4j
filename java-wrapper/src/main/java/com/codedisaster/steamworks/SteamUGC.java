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
		Items,
		ItemsMtx,
		ItemsReadyToUse,
		Collections,
		Artwork,
		Videos,
		Screenshots,
		AllGuides,
		WebGuides,
		IntegratedGuides,
		UsableInGame,
		ControllerBindings
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
		RankedByTotalUniqueSubscriptions
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

	public class ItemInstallInfo {
		private boolean installed;
		private SteamPublishedFileID publishedFileID;
		private long fileHandle;
		private String path;
		private int size;

		public boolean isInstalled() {
			return installed;
		}

		public SteamPublishedFileID getPublishedFileID() {
			return publishedFileID;
		}

		public String getPath() {
			return path;
		}

		public int getSize() {
			return size;
		}
	}
	
	public class ItemDownloadInfo {
		private boolean available;
		private SteamPublishedFileID publishedFileID;
		private long fileHandle;
		private long bytesDownloaded;
		private long bytesTotal;

		public boolean isAvailable() {
			return available;
		}

		public SteamPublishedFileID getPublishedFileID() {
			return publishedFileID;
		}

		public long getBytesDownloaded() {
			return bytesDownloaded;
		}

		public long getBytesTotal() {
			return bytesTotal;
		}
	}
	
	public SteamUGC(SteamUGCCallback callback) {
		super(SteamAPI.getSteamUGCPointer(), createCallback(new SteamUGCCallbackAdapter(callback)));
	}

	public SteamUGCQuery createQueryUserUGCRequest(long accountID, UserUGCList listType,
										  MatchingUGCType matchingType, UserUGCListSortOrder sortOrder,
										  long creatorAppID, long consumerAppID, int page) {

		return new SteamUGCQuery(createQueryUserUGCRequest(pointer, accountID, listType.ordinal(),
				matchingType.ordinal(), sortOrder.ordinal(), creatorAppID, consumerAppID, page));
	}

	public SteamUGCQuery createQueryAllUGCRequest(UGCQueryType queryType, MatchingUGCType matchingType,
			  							  long creatorAppID, long consumerAppID, int page) {

		return new SteamUGCQuery(createQueryAllUGCRequest(pointer, queryType.ordinal(), matchingType.ordinal(),
				creatorAppID, consumerAppID, page));
	}
	
	public boolean setReturnTotalOnly(SteamUGCQuery query, boolean returnTotalOnly) {
		return setReturnTotalOnly(pointer, query.handle, returnTotalOnly);
	}

	public SteamAPICall sendQueryUGCRequest(SteamUGCQuery query) {
		return new SteamAPICall(sendQueryUGCRequest(pointer, callback, query.handle));
	}

	public boolean getQueryUGCResult(SteamUGCQuery query, int index, SteamUGCDetails details) {
		boolean ret = getQueryUGCResult(pointer, query.handle, index, details);
		details.ownerID = new SteamID(details.ownerIDHandle);
		return ret;
	}

	public boolean releaseQueryUserUGCRequest(SteamUGCQuery query) {
		return releaseQueryUserUGCRequest(pointer, query.handle);
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
	
	public ItemInstallInfo getItemInstallInfo(SteamPublishedFileID publishedFileID) {
		ItemInstallInfo itemInstallInfo = new ItemInstallInfo();
		getItemInstallInfo(pointer, publishedFileID.handle, itemInstallInfo);
		itemInstallInfo.publishedFileID = new SteamPublishedFileID(itemInstallInfo.fileHandle);
		return itemInstallInfo;
	}
	
	public ItemDownloadInfo getItemDownloadInfo(SteamPublishedFileID publishedFileID) {
		ItemDownloadInfo itemDownloadInfo = new ItemDownloadInfo();
		getItemDownloadInfo(pointer, publishedFileID.handle, itemDownloadInfo);
		itemDownloadInfo.publishedFileID = new SteamPublishedFileID(itemDownloadInfo.fileHandle);
		return itemDownloadInfo;
	}
	
	public SteamAPICall requestUGCDetails(SteamPublishedFileID publishedFileID, int maxAgeSeconds) {
		return new SteamAPICall(requestUGCDetails(pointer, callback, publishedFileID.handle, maxAgeSeconds));
	}

	// @off

	/*JNI
		#include "SteamUGCCallback.h"
	*/

	static private native long createCallback(SteamUGCCallbackAdapter javaCallback); /*
		return (long) new SteamUGCCallback(env, javaCallback);
	*/

	static private native long createQueryUserUGCRequest(long pointer, long accountID, int listType,
														 int matchingType, int sortOrder,
														 long creatorAppID, long consumerAppID, int page); /*
		ISteamUGC* ugc = (ISteamUGC*) pointer;
		UGCQueryHandle_t query = ugc->CreateQueryUserUGCRequest(accountID, (EUserUGCList) listType,
			(EUGCMatchingUGCType) matchingType, (EUserUGCListSortOrder) sortOrder, creatorAppID, consumerAppID, page);
		return (long) query;
	*/

	static private native long createQueryAllUGCRequest(long pointer, int queryType, int matchingType,
														long creatorAppID, long consumerAppID, int page); /*
		ISteamUGC* ugc = (ISteamUGC*) pointer;
		UGCQueryHandle_t query = ugc->CreateQueryAllUGCRequest((EUGCQuery) queryType,
			(EUGCMatchingUGCType) matchingType, creatorAppID, consumerAppID, page);
		return (long) query;
	*/

	static private native boolean setReturnTotalOnly(long pointer, long query, boolean returnTotalOnly); /*
		ISteamUGC* ugc = (ISteamUGC*) pointer;
		return ugc->SetReturnTotalOnly(query, returnTotalOnly);
	*/

	static private native long sendQueryUGCRequest(long pointer, long callback, long query); /*
		ISteamUGC* ugc = (ISteamUGC*) pointer;
		SteamUGCCallback* cb = (SteamUGCCallback*) callback;
		SteamAPICall_t handle = ugc->SendQueryUGCRequest(query);
		cb->onUGCQueryCompletedCall.Set(handle, cb, &SteamUGCCallback::onUGCQueryCompleted);
		return handle;
	*/

	static private native boolean getQueryUGCResult(long pointer, long query, int index, SteamUGCDetails details); /*
		ISteamUGC* ugc = (ISteamUGC*) pointer;
		SteamUGCDetails_t result;

		if (ugc->GetQueryUGCResult(query, index, &result)) {
			jclass clazz = env->GetObjectClass(details);

			jfieldID field = env->GetFieldID(clazz, "publishedFileID", "J");
			env->SetLongField(details, field, (jlong) result.m_nPublishedFileId);

			field = env->GetFieldID(clazz, "result", "I");
			env->SetIntField(details, field, (jint) result.m_eResult);

			jstring title = env->NewStringUTF(result.m_rgchTitle);
			field = env->GetFieldID(clazz, "title", "Ljava/lang/String;");
			env->SetObjectField(details, field, title);

			jstring description = env->NewStringUTF(result.m_rgchDescription);
			field = env->GetFieldID(clazz, "description", "Ljava/lang/String;");
			env->SetObjectField(details, field, description);

			field = env->GetFieldID(clazz, "fileHandle", "J");
			env->SetLongField(details, field, (jlong) result.m_hFile);

			field = env->GetFieldID(clazz, "previewFileHandle", "J");
			env->SetLongField(details, field, (jlong) result.m_hPreviewFile);

			jstring fileName = env->NewStringUTF(result.m_pchFileName);
			field = env->GetFieldID(clazz, "fileName", "Ljava/lang/String;");
			env->SetObjectField(details, field, fileName);

			field = env->GetFieldID(clazz, "votesUp", "I");
			env->SetIntField(details, field, (jint) result.m_unVotesUp);

			field = env->GetFieldID(clazz, "votesDown", "I");
			env->SetIntField(details, field, (jint) result.m_unVotesDown);

			field = env->GetFieldID(clazz, "ownerIDHandle", "J");
			env->SetLongField(details, field, (jlong) result.m_ulSteamIDOwner);

			field = env->GetFieldID(clazz, "timeCreated", "I");
			env->SetIntField(details, field, (jint) result.m_rtimeCreated);

			field = env->GetFieldID(clazz, "timeUpdated", "I");
			env->SetIntField(details, field, (jint) result.m_rtimeUpdated);

			return true;
		}

		return false;
	*/

	static private native boolean releaseQueryUserUGCRequest(long pointer, long query); /*
		ISteamUGC* ugc = (ISteamUGC*) pointer;
		return ugc->ReleaseQueryUGCRequest(query);
	*/

	static private native long subscribeItem(long pointer, long callback, long publishedFileID); /*
		ISteamUGC* ugc = (ISteamUGC*) pointer;
		SteamUGCCallback* cb = (SteamUGCCallback*) callback;
		SteamAPICall_t handle = ugc->SubscribeItem(publishedFileID);
		cb->onSubscribeItemCall.Set(handle, cb, &SteamUGCCallback::onSubscribeItem);
		return handle;
	 */

	static private native long unsubscribeItem(long pointer, long callback, long publishedFileID); /*
		ISteamUGC* ugc = (ISteamUGC*) pointer;
		SteamUGCCallback* cb = (SteamUGCCallback*) callback;
		SteamAPICall_t handle = ugc->UnsubscribeItem(publishedFileID);
		cb->onUnsubscribeItemCall.Set(handle, cb, &SteamUGCCallback::onUnsubscribeItem);
		return handle;
	 */

	static private native int getNumSubscribedItems(long pointer); /*
		ISteamUGC* ugc = (ISteamUGC*) pointer;
		return ugc->GetNumSubscribedItems();
	*/

	static private native int getSubscribedItems(long pointer, long[] files, int maxEntries); /*
		ISteamUGC* ugc = (ISteamUGC*) pointer;
		return ugc->GetSubscribedItems((PublishedFileId_t*) files, maxEntries);
	*/
	
	static private native int getItemState(long pointer, long publishedFileID); /*
		ISteamUGC* ugc = (ISteamUGC*) pointer;
		return ugc->GetItemState(publishedFileID);
	*/

	static private native void getItemInstallInfo(long pointer, long publishedFileID, ItemInstallInfo itemInstallInfo); /*
		ISteamUGC* ugc = (ISteamUGC*) pointer;
		uint64 punSizeOnDisk;
		uint32 cchFolderSize = 1024;
		uint32 punTimeStamp;
		char* pchFolder = new char[cchFolderSize];
		bool installed = ugc->GetItemInstallInfo(publishedFileID, &punSizeOnDisk, pchFolder, cchFolderSize, &punTimeStamp);
		
		jclass clzz = env->GetObjectClass(itemInstallInfo);
	
		jfieldID field = env->GetFieldID(clzz, "fileHandle", "J");
		env->SetLongField(itemInstallInfo, field, (jlong) publishedFileID);
	
		field = env->GetFieldID(clzz, "installed", "Z");
		env->SetBooleanField(itemInstallInfo, field, (jboolean) installed);
	
		jstring description = env->NewStringUTF(pchFolder);
		field = env->GetFieldID(clzz, "path", "Ljava/lang/String;");
		env->SetObjectField(itemInstallInfo, field, description);
	
		field = env->GetFieldID(clzz, "size", "I");
		env->SetIntField(itemInstallInfo, field, (jint) punSizeOnDisk);
	*/
	
	static private native void getItemDownloadInfo(long pointer, long publishedFileID, ItemDownloadInfo itemDownloadInfo); /*
		ISteamUGC* ugc = (ISteamUGC*) pointer;
		uint64 punBytesDownloaded;
		uint64 punBytesTotal;
		bool available = ugc->GetItemDownloadInfo(publishedFileID, &punBytesDownloaded, &punBytesTotal);
		
		jclass clzz = env->GetObjectClass(itemDownloadInfo);
	
		jfieldID field = env->GetFieldID(clzz, "fileHandle", "J");
		env->SetLongField(itemDownloadInfo, field, (jlong) publishedFileID);
	
		field = env->GetFieldID(clzz, "available", "Z");
		env->SetBooleanField(itemDownloadInfo, field, (jboolean) available);
	
		field = env->GetFieldID(clzz, "bytesDownloaded", "J");
		env->SetLongField(itemDownloadInfo, field, (jlong) punBytesDownloaded);
	
		field = env->GetFieldID(clzz, "bytesTotal", "J");
		env->SetLongField(itemDownloadInfo, field, (jlong) punBytesTotal);
	*/
	
	static private native long requestUGCDetails(long pointer, long callback, long publishedFileID, int maxAgeSeconds); /*
		ISteamUGC* ugc = (ISteamUGC*) pointer;
		SteamUGCCallback* cb = (SteamUGCCallback*) callback;
		SteamAPICall_t handle = ugc->RequestUGCDetails(publishedFileID, maxAgeSeconds);
		cb->onRequestUGCDetailsCall.Set(handle, cb, &SteamUGCCallback::onRequestUGCDetails);
		return handle;
	*/

}
