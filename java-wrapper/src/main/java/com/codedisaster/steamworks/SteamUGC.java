package com.codedisaster.steamworks;

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

	public enum AllUGCQueryType {
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

	public SteamUGC(long pointer, SteamUGCCallback callback) {
		super(pointer);
		registerCallback(new SteamUGCCallbackAdapter(callback));
	}

	static void dispose() {
		registerCallback(null);
	}

	public SteamUGCQuery createQueryUserUGCRequest(long accountID, UserUGCList listType,
										  MatchingUGCType matchingType, UserUGCListSortOrder sortOrder,
										  long creatorAppID, long consumerAppID, int page) {

		return new SteamUGCQuery(createQueryUserUGCRequest(pointer, accountID, listType.ordinal(),
				matchingType.ordinal(), sortOrder.ordinal(), creatorAppID, consumerAppID, page));
	}

	public SteamUGCQuery createQueryAllUGCRequest(AllUGCQueryType queryType, UserUGCList listType,
			  							  MatchingUGCType matchingType, UserUGCListSortOrder sortOrder,
			  							  long creatorAppID, long consumerAppID, int page) {

		return new SteamUGCQuery(createQueryAllUGCRequest(pointer, queryType.ordinal(), listType.ordinal(),
				matchingType.ordinal(), sortOrder.ordinal(), creatorAppID, consumerAppID, page));
	}
	
	public boolean setReturnTotalOnly(SteamUGCQuery query, boolean returnTotalOnly) {
		return setReturnTotalOnly(pointer, query.handle, returnTotalOnly);
	}

	public SteamAPICall sendQueryUGCRequest(SteamUGCQuery query) {
		return new SteamAPICall(sendQueryUGCRequest(pointer, query.handle));
	}

	public boolean getQueryUGCResult(SteamUGCQuery query, int index, SteamUGCDetails details) {
		return getQueryUGCResult(pointer, query.handle, index, details);
	}

	public boolean releaseQueryUserUGCRequest(SteamUGCQuery query) {
		return releaseQueryUserUGCRequest(pointer, query.handle);
	}

	public SteamAPICall subscribeItem(SteamPublishedFileID publishedFileID) {
		return new SteamAPICall(createQuerySubscribeItem(pointer, publishedFileID.handle));
	}

	public SteamAPICall unsubscribeItem(SteamPublishedFileID publishedFileID) {
		return new SteamAPICall(createQueryUnsubscribeItem(pointer, publishedFileID.handle));
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

	/*JNI
		#include <steam_api.h>
		#include "SteamUGCCallback.h"

		static SteamUGCCallback* callback = NULL;
	*/

	static private native boolean registerCallback(SteamUGCCallbackAdapter javaCallback); /*
		if (callback != NULL) {
			delete callback;
			callback = NULL;
		}

		if (javaCallback != NULL) {
			callback = new SteamUGCCallback(env, javaCallback);
		}

		return callback != NULL;
	*/

	static private native long createQueryUserUGCRequest(long pointer, long accountID, int listType,
														 int matchingType, int sortOrder,
														 long creatorAppID, long consumerAppID, int page); /*
		ISteamUGC* ugc = (ISteamUGC*) pointer;
		UGCQueryHandle_t query = ugc->CreateQueryUserUGCRequest(accountID, (EUserUGCList) listType,
			(EUGCMatchingUGCType) matchingType, (EUserUGCListSortOrder) sortOrder, creatorAppID, consumerAppID, page);
		return (long) query;
	*/

	static private native long createQueryAllUGCRequest(long pointer, int queryType, int listType,
														 int matchingType, int sortOrder,
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

	static private native long sendQueryUGCRequest(long pointer, long query); /*
		ISteamUGC* ugc = (ISteamUGC*) pointer;
		SteamAPICall_t handle = ugc->SendQueryUGCRequest(query);
		callback->onUGCQueryCompletedCall.Set(handle, callback, &SteamUGCCallback::onUGCQueryCompleted);
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

			return true;
		}

		return false;
	*/

	static private native boolean releaseQueryUserUGCRequest(long pointer, long query); /*
		ISteamUGC* ugc = (ISteamUGC*) pointer;
		return ugc->ReleaseQueryUGCRequest(query);
	*/

	static private native long createQuerySubscribeItem(long pointer, long publishedFileID); /*
		ISteamUGC* ugc = (ISteamUGC*) pointer;
		SteamAPICall_t handle = ugc->SubscribeItem(publishedFileID);
		callback->onSubscribeItemCall.Set(handle, callback, &SteamUGCCallback::onSubscribeItem);
		return handle;
	 */

	static private native long createQueryUnsubscribeItem(long pointer, long publishedFileID); /*
		ISteamUGC* ugc = (ISteamUGC*) pointer;
		SteamAPICall_t handle = ugc->UnsubscribeItem(publishedFileID);
		callback->onUnsubscribeItemCall.Set(handle, callback, &SteamUGCCallback::onUnsubscribeItem);
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

}
