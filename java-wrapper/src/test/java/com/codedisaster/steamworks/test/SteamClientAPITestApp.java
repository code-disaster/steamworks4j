package com.codedisaster.steamworks.test;

import com.codedisaster.steamworks.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Collection;

public class SteamClientAPITestApp extends SteamTestApp {

	private SteamUser user;
	private SteamUserStats userStats;
	private SteamRemoteStorage remoteStorage;
	private SteamUGC ugc;
	private SteamUtils utils;
	private SteamApps apps;
	private SteamFriends friends;

	private SteamLeaderboardHandle currentLeaderboard = null;

	private SteamUserStatsCallback userStatsCallback = new SteamUserStatsCallback() {
		@Override
		public void onUserStatsReceived(long gameId, SteamID steamIDUser, SteamResult result) {
			System.out.println("User stats received: gameId=" + gameId + ", userId=" + steamIDUser.getAccountID() +
					", result=" + result.toString());

			int numAchievements = userStats.getNumAchievements();
			System.out.println("Num of achievements: " + numAchievements);

			for (int i = 0; i < numAchievements; i++) {
				String name = userStats.getAchievementName(i);
				boolean achieved = userStats.isAchieved(name, false);
				System.out.println("# " + i + " : name=" + name + ", achieved=" + (achieved ? "yes" : "no"));
			}
		}

		@Override
		public void onUserStatsStored(long gameId, SteamResult result) {
			System.out.println("User stats stored: gameId=" + gameId +
					", result=" + result.toString());
		}

		@Override
		public void onLeaderboardFindResult(SteamLeaderboardHandle leaderboard, boolean found) {
			System.out.println("Leaderboard find result: handle=" + leaderboard.toString() +
					", found=" + (found ? "yes" : "no"));

			if (found) {
				System.out.println("Leaderboard: name=" + userStats.getLeaderboardName(leaderboard) +
						", entries=" + userStats.getLeaderboardEntryCount(leaderboard));

				currentLeaderboard = leaderboard;
			}
		}

		@Override
		public void onLeaderboardScoresDownloaded(SteamLeaderboardHandle leaderboard,
												  SteamLeaderboardEntriesHandle entries,
												  int numEntries) {

			System.out.println("Leaderboard scores downloaded: handle=" + leaderboard.toString() +
					", entries=" + entries.toString() + ", count=" + numEntries);

			for (int i = 0; i < numEntries; i++) {

				SteamLeaderboardEntry entry = new SteamLeaderboardEntry();
				if (userStats.getDownloadedLeaderboardEntry(entries, i, entry)) {

					System.out.println("Leaderboard entry #" + i +
							": steamIDUser=" + entry.getSteamIDUser().getAccountID() +
							", globalRank=" + entry.getGlobalRank() +
							", score=" + entry.getScore());

					if (friends.requestUserInformation(entry.getSteamIDUser(), false)) {
						System.out.println("  ... requested user information for entry");
					} else {
						System.out.println("  ... user name is '" +
								friends.getFriendPersonaName(entry.getSteamIDUser()) + "'");

						int smallAvatar = friends.getSmallFriendAvatar(entry.getSteamIDUser());
						if (smallAvatar != 0) {
							int w = utils.getImageWidth(smallAvatar);
							int h = utils.getImageHeight(smallAvatar);
							System.out.println("  ... small avatar size: " + w + "x" + h + " pixels");

							ByteBuffer image = ByteBuffer.allocate(w * h * 4);

							if (utils.getImageRGBA(smallAvatar, image, w * h * 4)) {
								System.out.println("  ... small avatar retrieve avatar image successful");
							} else {
								System.out.println("  ... small avatar retrieve avatar image failed!");
							}
						} else {
							System.out.println("  ... small avatar image not available!");
						}

					}
				}

			}
		}

		@Override
		public void onLeaderboardScoreUploaded(boolean success,
											   SteamLeaderboardHandle leaderboard,
											   int score,
											   boolean scoreChanged,
											   int globalRankNew,
											   int globalRankPrevious) {

			System.out.println("Leaderboard score uploaded: " + (success ? "yes" : "no") +
					", handle=" + leaderboard.toString() +
					", score=" + score +
					", changed=" + (scoreChanged ? "yes" : "no") +
					", globalRankNew=" + globalRankNew +
					", globalRankPrevious=" + globalRankPrevious);
		}
	};

	private SteamRemoteStorageCallback remoteStorageCallback = new SteamRemoteStorageCallback() {
		@Override
		public void onFileShareResult(SteamUGCHandle fileHandle, String fileName, SteamResult result) {
			System.out.println("Remote storage file share result: handle='" + fileHandle.toString() +
					", name=" + fileName + "', result=" + result.toString());
		}

		@Override
		public void onDownloadUGCResult(SteamUGCHandle fileHandle, SteamResult result) {
			System.out.println("Remote storage download UGC result: handle='" + fileHandle.toString() +
					"', result=" + result.toString());

			ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
			int offset = 0, bytesRead;

			do {
				bytesRead = remoteStorage.ugcRead(fileHandle, buffer, buffer.limit(), offset,
					SteamRemoteStorage.UGCReadAction.ContinueReadingUntilFinished);
				offset += bytesRead;
			} while (bytesRead > 0);

			System.out.println("Read " + offset + " bytes from handle=" + fileHandle.toString());
		}

		@Override
		public void onPublishFileResult(SteamPublishedFileID publishedFileID, boolean needsToAcceptWLA, SteamResult result) {
			System.out.println("Remote storage publish file result: publishedFileID=" + publishedFileID.toString() +
					", needsToAcceptWLA=" + needsToAcceptWLA + ", result=" + result.toString());
		}

		@Override
		public void onUpdatePublishedFileResult(SteamPublishedFileID publishedFileID, boolean needsToAcceptWLA, SteamResult result) {
			System.out.println("Remote storage update published file result: publishedFileID=" + publishedFileID.toString() +
					", needsToAcceptWLA=" + needsToAcceptWLA + ", result=" + result.toString());
		}
	};

	private SteamUGCCallback ugcCallback = new SteamUGCCallback() {
		@Override
		public void onUGCQueryCompleted(SteamUGCQuery query, int numResultsReturned, int totalMatchingResults,
										boolean isCachedData, SteamResult result) {
			System.out.println("UGC query completed: handle=" + query.toString() + ", " + numResultsReturned + " of " +
							   totalMatchingResults + " results returned, result=" + result.toString());

			for (int i = 0; i < numResultsReturned; i++) {
				SteamUGCDetails details = new SteamUGCDetails();
				ugc.getQueryUGCResult(query, i, details);

				System.out.println("UGC details #" + i +
								   ": publishedFileID=" + details.getPublishedFileID().toString() +
								   ", result=" + details.getResult().toString() +
								   ", title='" + details.getTitle() + "'" +
								   ", description='" + details.getDescription() + "'" +
								   ", fileName=" + details.getFileName() +
								   ", fileHandle=" + details.getFileHandle().toString() +
								   ", previewFileHandle=" + details.getPreviewFileHandle().toString());
			}

			ugc.releaseQueryUserUGCRequest(query);
		}

		@Override
		public void onSubscribeItem(SteamPublishedFileID publishedFileID, SteamResult result) {
			System.out.println("Subscribe item result: publishedFileID=" + publishedFileID + ", result=" + result);
		}

		@Override
		public void onUnsubscribeItem(SteamPublishedFileID publishedFileID, SteamResult result) {
			System.out.println("Unsubscribe item result: publishedFileID=" + publishedFileID + ", result=" + result);
		}

		@Override
		public void onRequestUGCDetails(SteamUGCDetails details, SteamResult result) {
			System.out.println("Request details result: result=" + result);
			System.out.println("UGC details " +
					   ": publishedFileID=" + details.getPublishedFileID().toString() +
					   ", result=" + details.getResult().toString() +
					   ", title='" + details.getTitle() + "'" +
					   ", description='" + details.getDescription() + "'" +
					   ", fileName=" + details.getFileName() +
					   ", fileHandle=" + details.getFileHandle().toString() +
					   ", previewFileHandle=" + details.getPreviewFileHandle().toString());
		}
	};

	private SteamFriendsCallback friendsCallback = new SteamFriendsCallback() {
		@Override
		public void onPersonaStateChange(SteamID steamID, SteamFriends.PersonaChange change) {

			switch (change) {

				case Name:
					System.out.println("Persona name received: " +
							"accountID=" + steamID.getAccountID() +
							", name='" + friends.getFriendPersonaName(steamID) + "'");
					break;

				default:
					System.out.println("Persona state changed (unhandled): " +
							"accountID=" + steamID.getAccountID() +
							", change=" + change.name());
					break;
			}
		}
	};

	@Override
	protected void registerInterfaces() {

		System.out.println("Register user ...");
		user = new SteamUser();

		System.out.println("Register user stats callback ...");
		userStats = new SteamUserStats(userStatsCallback);

		System.out.println("Register remote storage ...");
		remoteStorage = new SteamRemoteStorage(remoteStorageCallback);

		System.out.println("Register UGC ...");
		ugc = new SteamUGC(ugcCallback);

		System.out.println("Register Utils ...");
		utils = new SteamUtils();

		System.out.println("Register Apps ...");
		apps = new SteamApps();

		System.out.println("Register Friends ...");
		friends = new SteamFriends(friendsCallback);

		System.out.println("Local user account ID: " + user.getSteamID().getAccountID());
		System.out.println("App ID: " + utils.getAppID());

	}

	@Override
	protected void unregisterInterfaces() {
		user.dispose();
		userStats.dispose();
		remoteStorage.dispose();
		ugc.dispose();
		utils.dispose();
		apps.dispose();
		friends.dispose();
	}

	@Override
	protected void processUpdate() throws SteamException {

	}

	@Override
	protected void processInput(String input) throws SteamException {

		if (input.equals("stats request")) {
			userStats.requestCurrentStats();
		} else if (input.equals("stats store")) {
			userStats.storeStats();
		} else if (input.equals("file list")) {
			int numFiles = remoteStorage.getFileCount();
			System.out.println("Num of files: " + numFiles);

			for (int i = 0; i < numFiles; i++) {
				int[] sizes = new int[1];
				String name = remoteStorage.getFileNameAndSize(i, sizes);
				boolean exists = remoteStorage.fileExists(name);
				System.out.println("# " + i + " : name=" + name + ", size=" + sizes[0] + ", exists=" + (exists ? "yes" : "no"));
			}
		} else if (input.startsWith("file write ")) {
			String path = input.substring("file write ".length());
			File file = new File(path);
			try {
				FileInputStream in = new FileInputStream(file);
				SteamUGCFileWriteStreamHandle remoteFile = remoteStorage.fileWriteStreamOpen(path);
				if (remoteFile != null) {
					byte[] bytes = new byte[1024];
					int bytesRead;
					while((bytesRead = in.read(bytes, 0, bytes.length)) > 0) {
						ByteBuffer buffer = ByteBuffer.allocateDirect(bytesRead);
						buffer.put(bytes, 0, bytesRead);
						remoteStorage.fileWriteStreamWriteChunk(remoteFile, buffer, buffer.limit());
					}
					remoteStorage.fileWriteStreamClose(remoteFile);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (input.startsWith("file delete ")) {
			String path = input.substring("file delete ".length());
			if (remoteStorage.fileDelete(path)) {
				System.out.println("deleted file '" + path + "'");
			}
		} else if (input.startsWith("file share ")) {
			remoteStorage.fileShare(input.substring("file share ".length()));
		} else if (input.startsWith("file publish ")) {
			String[] paths = input.substring("file publish ".length()).split(" ");
			if (paths.length >= 2) {
				System.out.println("publishing file: " + paths[0] + ", preview file: " + paths[1]);
				remoteStorage.publishWorkshopFile(paths[0], paths[1], utils.getAppID(),
						"Test UGC!", "Dummy UGC file published by test application.",
						SteamRemoteStorage.PublishedFileVisibility.Private, null,
						SteamRemoteStorage.WorkshopFileType.Community);
			}
		} else if (input.startsWith("file republish ")) {
			String[] paths = input.substring("file republish ".length()).split(" ");
			if (paths.length >= 3) {
				System.out.println("republishing id: " + paths[0] + ", file: " + paths[1] + ", preview file: " + paths[2]);

				SteamPublishedFileID fileID = new SteamPublishedFileID(Long.parseLong(paths[0]));

				SteamPublishedFileUpdateHandle updateHandle = remoteStorage.createPublishedFileUpdateRequest(fileID);
				if (updateHandle != null) {
					remoteStorage.updatePublishedFileFile(updateHandle, paths[1]);
					remoteStorage.updatePublishedFilePreviewFile(updateHandle, paths[2]);
					remoteStorage.updatePublishedFileTitle(updateHandle, "Updated Test UGC!");
					remoteStorage.updatePublishedFileDescription(updateHandle, "Dummy UGC file *updated* by test application.");
					remoteStorage.commitPublishedFileUpdate(updateHandle);
				}
			}
		} else if (input.equals("ugc query")) {
			SteamUGCQuery query = ugc.createQueryUserUGCRequest(user.getSteamID().getAccountID(), SteamUGC.UserUGCList.Subscribed,
					SteamUGC.MatchingUGCType.UsableInGame, SteamUGC.UserUGCListSortOrder.TitleAsc,
					utils.getAppID(), utils.getAppID(), 1);

			if (query.isValid()) {
				System.out.println("sending UGC query: " + query.toString());
				//ugc.setReturnTotalOnly(query, true);
				ugc.sendQueryUGCRequest(query);
			}
		} else if (input.startsWith("ugc download ")) {
			String name = input.substring("ugc download ".length());
			SteamUGCHandle handle = new SteamUGCHandle(Long.parseLong(name, 16));
			remoteStorage.ugcDownload(handle, 0);
		} else if (input.startsWith("ugc subscribe ")) {
			Long id = Long.parseLong(input.substring("ugc subscribe ".length()), 16);
			ugc.subscribeItem(new SteamPublishedFileID(id));
		} else if (input.startsWith("ugc unsubscribe ")) {
			Long id = Long.parseLong(input.substring("ugc unsubscribe ".length()), 16);
			ugc.unsubscribeItem(new SteamPublishedFileID(id));
		} else if (input.startsWith("ugc state ")) {
			Long id = Long.parseLong(input.substring("ugc state ".length()), 16);
			Collection<SteamUGC.ItemState> itemStates = ugc.getItemState(new SteamPublishedFileID(id));
			System.out.println("UGC item states: " + itemStates.size());
			for (SteamUGC.ItemState itemState : itemStates) {
				System.out.println("  " + itemState.name());
			}
		} else if (input.startsWith("ugc details ")) {
			System.out.println("requesting UGC details (deprecated API call)");
			Long id = Long.parseLong(input.substring("ugc details ".length()), 16);
			ugc.requestUGCDetails(new SteamPublishedFileID(id), 0);

			SteamUGCQuery query = ugc.createQueryUGCDetailsRequest(new SteamPublishedFileID(id));
			if (query.isValid()) {
				System.out.println("sending UGC details query: " + query.toString());
				ugc.sendQueryUGCRequest(query);
			}
		} else if (input.startsWith("ugc info ")) {
			Long id = Long.parseLong(input.substring("ugc info ".length()), 16);
			SteamUGC.ItemInstallInfo installInfo = new SteamUGC.ItemInstallInfo();
			if (ugc.getItemInstallInfo(new SteamPublishedFileID(id), installInfo)) {
				System.out.println("  folder: " + installInfo.getFolder());
				System.out.println("  size on disk: " + installInfo.getSizeOnDisk());
			}
			SteamUGC.ItemDownloadInfo downloadInfo = new SteamUGC.ItemDownloadInfo();
			if (ugc.getItemDownloadInfo(new SteamPublishedFileID(id), downloadInfo)) {
				System.out.println("  bytes downloaded: " + downloadInfo.getBytesDownloaded());
				System.out.println("  bytes total: " + downloadInfo.getBytesTotal());
			}
		} else if (input.startsWith("leaderboard find ")) {
			String name = input.substring("leaderboard find ".length());
			userStats.findLeaderboard(name);
		} else if (input.startsWith("leaderboard list ")) {
			String[] params = input.substring("leaderboard list ".length()).split(" ");
			if (currentLeaderboard != null && params.length >= 2) {
				userStats.downloadLeaderboardEntries(currentLeaderboard,
						SteamUserStats.LeaderboardDataRequest.Global,
						Integer.valueOf(params[0]), Integer.valueOf(params[1]));
			}
		} else if (input.startsWith("leaderboard score ")) {
			String score = input.substring("leaderboard score ".length());
			if (currentLeaderboard != null) {
				System.out.println("uploading score " + score + " to leaderboard " + currentLeaderboard.toString());
				userStats.uploadLeaderboardScore(currentLeaderboard,
						SteamUserStats.LeaderboardUploadScoreMethod.KeepBest, Integer.valueOf(score));
			}
		} else if (input.startsWith("apps subscribed ")) {
			String appId = input.substring("apps subscribed ".length());
			boolean subscribed = apps.isSubscribedApp(Long.parseLong(appId));
			System.out.println("user described to app #" + appId + ": " + (subscribed ? "yes" : "no"));
		}

	}

	public static void main(String[] arguments) {
		new SteamClientAPITestApp().clientMain(arguments);
	}

}
