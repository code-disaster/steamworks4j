package com.codedisaster.steamworks.test;

import com.codedisaster.steamworks.*;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.Collection;

public class SteamClientAPITest extends SteamTestApp {

	private SteamUser user;
	private SteamUserStats userStats;
	private SteamRemoteStorage remoteStorage;
	private SteamUGC ugc;
	private SteamUtils utils;
	private SteamApps apps;
	private SteamFriends friends;

	private SteamLeaderboardHandle currentLeaderboard = null;

	private SteamUserCallback userCallback = new SteamUserCallback() {
		@Override
		public void onAuthSessionTicket(SteamAuthTicket authTicket, SteamResult result) {

		}

		@Override
		public void onValidateAuthTicket(SteamID steamID, SteamAuth.AuthSessionResponse authSessionResponse, SteamID ownerSteamID) {

		}

		@Override
		public void onMicroTxnAuthorization(int appID, long orderID, boolean authorized) {

		}

		@Override
		public void onEncryptedAppTicket(SteamResult result) {

		}

		@Override
		public void onGetTicketForWebApi(SteamAuthTicket authTicket, SteamResult result, byte[] ticketData) {
			System.out.println("auth ticket for web API: " + ticketData.length + " bytes" +
					", result=" + result.toString());
		}
	};

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
		public void onUserStatsUnloaded(SteamID steamIDUser) {
			System.out.println("User stats unloaded: userId=" + steamIDUser.getAccountID());
		}

		@Override
		public void onUserAchievementStored(long gameId, boolean isGroupAchievement, String achievementName,
											int curProgress, int maxProgress) {
			System.out.println("User achievement stored: gameId=" + gameId + ", name=" + achievementName +
					", progress=" + curProgress + "/" + maxProgress);
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

			int[] details = new int[16];

			for (int i = 0; i < numEntries; i++) {

				SteamLeaderboardEntry entry = new SteamLeaderboardEntry();
				if (userStats.getDownloadedLeaderboardEntry(entries, i, entry, details)) {

					int numDetails = entry.getNumDetails();

					System.out.println("Leaderboard entry #" + i +
							": accountID=" + entry.getSteamIDUser().getAccountID() +
							", globalRank=" + entry.getGlobalRank() +
							", score=" + entry.getScore() +
							", numDetails=" + numDetails);

					for (int detail = 0; detail < numDetails; detail++) {
						System.out.println("  ... detail #" + detail + "=" + details[detail]);
					}

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

							ByteBuffer image = ByteBuffer.allocateDirect(w * h * 4);

							try {
								if (utils.getImageRGBA(smallAvatar, image)) {
									System.out.println("  ... small avatar retrieve avatar image successful");

									int nonZeroes = w * h;
									for (int y = 0; y < h; y++) {
										for (int x = 0; x < w; x++) {
											//System.out.print(String.format(" %08x", image.getInt(y * w + x)));
											if (image.getInt(y * w + x) == 0) {
												nonZeroes--;
											}
										}
										//System.out.println();
									}

									if (nonZeroes == 0) {
										System.err.println("Something's wrong! Avatar image is empty!");
									}

								} else {
									System.out.println("  ... small avatar retrieve avatar image failed!");
								}
							} catch (SteamException e) {
								e.printStackTrace();
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

		@Override
		public void onNumberOfCurrentPlayersReceived(boolean success, int players) {
			System.out.println("Number of current players received: " + players);
		}

		@Override
		public void onGlobalStatsReceived(long gameId, SteamResult result) {
			System.out.println("Global stats received: gameId=" + gameId + ", result=" + result.toString());
		}
	};

	private SteamRemoteStorageCallback remoteStorageCallback = new SteamRemoteStorageCallback() {
		@Override
		public void onFileWriteAsyncComplete(SteamResult result) {

		}

		@Override
		public void onFileReadAsyncComplete(SteamAPICall fileReadAsync, SteamResult result, int offset, int read) {

		}

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

		@Override
		public void onPublishedFileSubscribed(SteamPublishedFileID publishedFileID, int appID) {

		}

		@Override
		public void onPublishedFileUnsubscribed(SteamPublishedFileID publishedFileID, int appID) {

		}

		@Override
		public void onPublishedFileDeleted(SteamPublishedFileID publishedFileID, int appID) {

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
				printUGCDetails("UGC details #" + i, details);
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
			printUGCDetails("UGC details ", details);
		}

		@Override
		public void onCreateItem(SteamPublishedFileID publishedFileID, boolean needsToAcceptWLA, SteamResult result) {

		}

		@Override
		public void onSubmitItemUpdate(SteamPublishedFileID publishedFileID, boolean needsToAcceptWLA, SteamResult result) {

		}

		@Override
		public void onDownloadItemResult(int appID, SteamPublishedFileID publishedFileID, SteamResult result) {

		}

		@Override
		public void onUserFavoriteItemsListChanged(SteamPublishedFileID publishedFileID, boolean wasAddRequest, SteamResult result) {

		}

		@Override
		public void onSetUserItemVote(SteamPublishedFileID publishedFileID, boolean voteUp, SteamResult result) {

		}

		@Override
		public void onGetUserItemVote(SteamPublishedFileID publishedFileID, boolean votedUp, boolean votedDown, boolean voteSkipped, SteamResult result) {

		}

		private void printUGCDetails(String prefix, SteamUGCDetails details) {
			System.out.println(prefix +
					": publishedFileID=" + details.getPublishedFileID().toString() +
					", result=" + details.getResult().name() +
					", type=" + details.getFileType().name() +
					", title='" + details.getTitle() + "'" +
					", description='" + details.getDescription() + "'" +
					", tags='" + details.getTags() + "'" +
					", fileName=" + details.getFileName() +
					", fileHandle=" + details.getFileHandle().toString() +
					", previewFileHandle=" + details.getPreviewFileHandle().toString() +
					", url=" + details.getURL());
		}

		@Override
		public void onStartPlaytimeTracking(SteamResult result) {

		}

		@Override
		public void onStopPlaytimeTracking(SteamResult result) {

		}

		@Override
		public void onStopPlaytimeTrackingForAllItems(SteamResult result) {

		}

		@Override
		public void onDeleteItem(SteamPublishedFileID publishedFileID, SteamResult result) {

		}
	};

	private SteamFriendsCallback friendsCallback = new SteamFriendsCallback() {
		@Override
		public void onSetPersonaNameResponse(boolean success, boolean localSuccess, SteamResult result) {

		}

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

		@Override
		public void onGameOverlayActivated(boolean active, boolean userInitiated, int appID) {

		}

		@Override
		public void onGameLobbyJoinRequested(SteamID steamIDLobby, SteamID steamIDFriend) {

		}

		@Override
		public void onAvatarImageLoaded(SteamID steamID, int image, int width, int height) {

		}

		@Override
		public void onFriendRichPresenceUpdate(SteamID steamIDFriend, int appID) {

		}

		@Override
		public void onGameRichPresenceJoinRequested(SteamID steamIDFriend, String connect) {

		}

		@Override
		public void onGameServerChangeRequested(String server, String password) {

		}
	};

	private SteamUtilsCallback utilsCallback = new SteamUtilsCallback() {
		@Override
		public void onSteamShutdown() {
			System.out.println("Steam client wants to shut down!");
		}

		@Override
		public void onFloatingGamepadTextInputDismissed() {

		}
	};

	@Override
	protected void registerInterfaces() {

		System.out.println("Register user ...");
		user = new SteamUser(userCallback);

		System.out.println("Register user stats callback ...");
		userStats = new SteamUserStats(userStatsCallback);

		System.out.println("Register remote storage ...");
		remoteStorage = new SteamRemoteStorage(remoteStorageCallback);

		System.out.println("Register UGC ...");
		ugc = new SteamUGC(ugcCallback);

		System.out.println("Register Utils ...");
		utils = new SteamUtils(utilsCallback);

		System.out.println("Register Apps ...");
		apps = new SteamApps();

		System.out.println("Register Friends ...");
		friends = new SteamFriends(friendsCallback);

		System.out.println("Local user account ID: " + user.getSteamID().getAccountID());
		System.out.println("Local user steam ID: " + SteamID.getNativeHandle(user.getSteamID()));
		System.out.println("Local user friends name: " + friends.getPersonaName());
		System.out.println("App ID: " + utils.getAppID());

		System.out.println("App build ID: " + apps.getAppBuildId());
		System.out.println("App owner: " + apps.getAppOwner().getAccountID());

		System.out.println("Current game language: " + apps.getCurrentGameLanguage());
		System.out.println("Available game languages: " + apps.getAvailableGameLanguages());
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

		if (input.startsWith("stats global ")) {
			String[] cmd = input.substring("stats global ".length()).split(" ");
			if (cmd.length > 0) {
				if (cmd[0].equals("request")) {
					int days = 0;
					if (cmd.length > 1) {
						days = Integer.parseInt(cmd[1]);
					}
					userStats.requestGlobalStats(days);
				} else if (cmd[0].equals("players")) {
					userStats.getNumberOfCurrentPlayers();
				} else if (cmd[0].equals("lget") && cmd.length > 1) {
					int days = 0;
					if (cmd.length > 2) {
						days = Integer.parseInt(cmd[2]);
					}
					if (days == 0) {
						long value = userStats.getGlobalStat(cmd[1], -1);
						System.out.println("global stat (L) '" + cmd[1] + "' = " + value);
					} else {
						long[] data = new long[days];
						int count = userStats.getGlobalStatHistory(cmd[1], data);
						System.out.print("global stat history (L) for " + count + " of " + days + " days:");
						for (int i = 0; i < count; i++) {
							System.out.print(" " + Long.toString(data[i]));
						}
						System.out.println();
					}
				} else if (cmd[0].equals("dget") && cmd.length > 1) {
					int days = 0;
					if (cmd.length > 2) {
						days = Integer.parseInt(cmd[2]);
					}
					if (days == 0) {
						double value = userStats.getGlobalStat(cmd[1], -1.0);
						System.out.println("global stat (D) '" + cmd[1] + "' = " + value);
					} else {
						double[] data = new double[days];
						int count = userStats.getGlobalStatHistory(cmd[1], data);
						System.out.print("global stat history (D) for " + count + " of " + days + " days:");
						for (int i = 0; i < count; i++) {
							System.out.print(" " + Double.toString(data[i]));
						}
						System.out.println();
					}
				}
			}
		} else if (input.equals("stats request")) {
			userStats.requestCurrentStats();
		} else if (input.equals("stats store")) {
			userStats.storeStats();
		} else if (input.startsWith("achievement set ")) {
			String achievementName = input.substring("achievement set ".length());
			System.out.println("- setting " + achievementName + " to 'achieved'");
			userStats.setAchievement(achievementName);
		} else if (input.startsWith("achievement clear ")) {
			String achievementName = input.substring("achievement clear ".length());
			System.out.println("- clearing " + achievementName);
			userStats.clearAchievement(achievementName);
		} else if (input.equals("file list")) {
			int numFiles = remoteStorage.getFileCount();
			System.out.println("Num of files: " + numFiles);

			for (int i = 0; i < numFiles; i++) {
				int[] sizes = new int[1];
				String file = remoteStorage.getFileNameAndSize(i, sizes);
				boolean exists = remoteStorage.fileExists(file);
				System.out.println("# " + i + " : name=" + file + ", size=" + sizes[0] + ", exists=" + (exists ? "yes" : "no"));
			}
		} else if (input.startsWith("file write ")) {
			String path = input.substring("file write ".length());
			File file = new File(path);
			try (FileInputStream in = new FileInputStream(file)) {
				SteamUGCFileWriteStreamHandle remoteFile = remoteStorage.fileWriteStreamOpen(path);
				if (remoteFile != null) {
					byte[] bytes = new byte[1024];
					int bytesRead;
					while ((bytesRead = in.read(bytes, 0, bytes.length)) > 0) {
						ByteBuffer buffer = ByteBuffer.allocateDirect(bytesRead);
						buffer.put(bytes, 0, bytesRead);
						buffer.flip();
						remoteStorage.fileWriteStreamWriteChunk(remoteFile, buffer);
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
						Integer.parseInt(params[0]), Integer.parseInt(params[1]));
			}
		} else if (input.startsWith("leaderboard users ")) {
			String[] params = input.substring("leaderboard users ".length()).split(" ");
			if (currentLeaderboard != null && params.length > 0) {
				SteamID[] users = new SteamID[params.length];
				for (int i = 0; i < params.length; i++) {
					users[i] = SteamID.createFromNativeHandle(Long.parseLong(params[i]));
				}
				userStats.downloadLeaderboardEntriesForUsers(currentLeaderboard, users);
			}
		} else if (input.startsWith("leaderboard score ")) {
			String score = input.substring("leaderboard score ".length());
			if (currentLeaderboard != null) {
				System.out.println("uploading score " + score + " to leaderboard " + currentLeaderboard.toString());
				userStats.uploadLeaderboardScore(currentLeaderboard,
						SteamUserStats.LeaderboardUploadScoreMethod.KeepBest, Integer.parseInt(score), new int[] {});
			}
		} else if (input.startsWith("apps subscribed ")) {
			String appId = input.substring("apps subscribed ".length());
			boolean subscribed = apps.isSubscribedApp(Integer.parseInt(appId));
			System.out.println("user described to app #" + appId + ": " + (subscribed ? "yes" : "no"));
		} else if (input.startsWith("deck ")) {
			String cmd = input.substring("deck ".length());
			if (cmd.equals("status")) {
				boolean isDeck = utils.isSteamRunningOnSteamDeck();
				System.out.println("Steam is running on SteamDeck: " + (isDeck ? "yes" : "no"));
			} else if (cmd.equals("input")) {
				// supposed to fail, since we run w/o UI here
				boolean success = utils.showFloatingGamepadTextInput(
						SteamUtils.FloatingGamepadTextInputMode.ModeSingleLine, 0, 0, 1280,200);
				System.out.println("Show floating gamepad text input: " + (success ? "success" : "failed"));
			}
		} else if (input.equals("auth web")) {
			/*SteamAuthTicket ticket =*/ user.getAuthTicketForWebApi();
		}
	}

	public static void main(String[] arguments) {
		new SteamClientAPITest().clientMain(arguments);
	}

}
