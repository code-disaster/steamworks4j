## change log

### [1.10.0-SNAPSHOT]
- Updated to Steamworks SDK v1.57.
- Updated Maven modules to compile with Java 8.
- All callback interfaces are now implemented using empty `default` methods. (#110)
- Added `SteamUtilsCallback.onFloatingGamepadTextInputDismissed()`. (#118)
- Added `SteamUser.getAuthTicketForWebApi()`. (#126)
- MacOS: build fat dylib to support both x86_64 and arm64 architectures. (#107, #116)
- API change: removed `SteamSharedLibraryLoader`
  - Replaced by `SteamLibraryLoader` interface. Applications must pass an implementation of this interface to `SteamAPI.loadLibraries()`.
  - Added two new submodules: `steamworks4j-gdx` and `steamworks4j-lwjgl3`. They can be imported and used as-is, or serve as a reference for your own implementations.
  - `SteamAPI.skipLoadLibraries()` has been removed. Calling `SteamAPI.loadLibraries(new SteamLibraryLoader())` has the same effect.

### [1.9.0]
- Updated to Steamworks SDK v1.53. This also removes a few functions, or marks them deprecated, which are removed or flagged as deprecated by the SDK.
- Refactored interface to the native API.
  - Each interface class (any class extending `SteamInterface`) is now split into a Java class and an accompanying `*Native` class which implement its native calls.
  - Interfaces do not cache their native class pointer anymore, as it is recommended in the SDK documentation.
- OS X: Updated build script to compile with premake5.
- Windows: Updated build script to compile with premake5.0.0-beta1. Prebuilt libraries are now generated using Visual Studio 2022.
- Added `SteamAPI.skipLoadLibraries()` and `SteamGameServerAPI.skipLoadLibraries()`. They can be used to skip the library's own SharedLibraryLoader entirely, so you can, for example, use your own, or the one shipped with LWJGL3.
- Use a simple binary search for more efficient HTTPStatusCode lookups. (#81)
- Added `SteamUserCallback.onAuthSessionTicket()`. (#83)
- Added `SteamUserCallback.getNumberOfCurrentPlayers()`.
- Fixed `SteamRemoteStorage.fileRead()` to return `int` instead of `boolean`.
- Added `isSteamRunningOnSteamDeck()`, `showFloatingGamepadTextInput()` and `dismissFloatingGamepadTextInput()` functions to SteamUtils interface.
- Fixed `SteamUGC.submitItemUpdate()` to allow for the changeNote parameter set to null.
- Added Coplay functions to SteamFriends interface. (#85)
- API change: `SteamNetworking.isP2PPacketAvailable()` now returns a boolean in addition to the packet size.

### [1.8.0]
- MacOS: removed 32-bit support.
- MacOS: raised minimum version support to OS X 10.9.
- API change: split up initialization into `SteamAPI.loadLibraries()` and `SteamAPI.init()`. Allows to call some native functions, like restartAppIfNecessary(), before SteamAPI.init().
- API change: applied same change as above to `SteamGameServerAPI.loadLibraries()` and `SteamGameServerAPI.init()`.
- SteamGameServerAPI skips loading of shared libraries if already loaded by SteamAPI. (#56)
- Added `SteamUGC.deleteItem()`. (#64)
- Added Voice API. [contributed by sf17k]
- Fixed signature of `SteamMatchmaking.getLobbyMemberLimit()`. (#67)
- Added `SteamFriends.getFriendGamePlayed()`. (#68)
- Added `SteamMatchmaking.[set|get]LobbyMemberData()`. (#72) [contributed by XaeroDegreaz]
- Added `SteamUserStats.downloadLeaderboardEntriesForUsers()` (#74)

### [1.7.0]
- Updated to Steamworks SDK v1.42.
- Reviewed all API functions which use NIO buffers. They now all use direct *byte* buffers and properly read `ByteBuffer#position()` as offset and `ByteBuffer#remaining()` as size/capacity markers. Also, they never change the buffer state, so for functions returning data, the caller must ensure that buffer position and/or limit are updated accordingly.
- Removed redistributable shared libraries from source control. They are now pulled from the `./sdk/redistributable_bin/` folder directly by Maven.
- Added `steamworks4j-server` module. It creates two native libraries, steamworks4j-server and steamworks4j-encryptedappticket.
- Added `tests` module, which now contains all test apps.
- Added SteamRemoteStorage callbacks to notify application about external changes to workshop item subscriptions. (#42)
- Added SteamRemoteStorage functions for asynchronous file access and file/cloud queries.
- Added SteamNetworking.getP2PSessionState(). (#43)
- Added SteamUser functions to request encrypted app ticket. (#45)
- Added SteamEncryptedAppTicket server functions. (#45)
- API change: adjusted SteamGameServer.sendUserConnectAndAuthenticate() to better mimic the C++ API.
- Added SteamMatchmakingServers wrapper.
- Refactored shared library loader to work with the new `server` and `test` modules.
- Added some options to customize the shared library loader behavior during development.
- Moved all SteamGameServer* classes to the server module.
- API change: Removed `API` parameter when creating HTTP and networking wrappers. Added SteamGameServerHTTP and SteamGameServerNetworking to create server-side versions.
- Added SteamFriendsCallback.onGameServerChangeRequested(). (#48)
- Removed support for Linux 32-bit.
- Updated native Windows build to a newer version of premake 5 (alpha 11).
- Added some missing SteamApps functions. (#50)
- Added SteamScreenshots wrapper.
- All Maven modules are now compiled with Java 7.
- Added SteamUtils.getImageSize(). (#62)
- Added com.codedisaster.steamworks.Version to query library version at runtime.

### [1.6.2]
- Cleaned up Maven project structure. Removed superfluous releases of 'steamworks4j-parent' and 'steamworks4j-jnigen' repositories.
- Reduced JNI overhead for invocations of callback adapters.
- Fixed crash when trying to run multiple processes using steamworks4j simultaneously.

### [1.6.1]
- Updated to Steamworks SDK v1.39.
- Added SteamFriends.setPersonaName().
- Added remaining functions to activate Steam overlay.
- Added functions for rich presence and game invites to SteamFriends (#38).
- API change: the SteamFriendsCallback interface has been extended to reflect the latest additions.
- API change: removed SteamID.getNativeHandle(). All native handles can now be queried with SteamNativeHandle.getNativeHandle().
- The default behaviour of SteamAPI.isSteamRunning() has changed to *not* call SteamAPI_IsSteamRunning(), see JavaDocs for more information.
- Added SteamAPI.releaseCurrentThreadMemory().

### [1.6.0]
- Added SteamController interface.
- Fixed resource files (prebuilt native libraries) included in source jars.

### [1.5.0]
- Updated to Steamworks SDK v1.38a.
- Added SteamUserCallback.onMicroTxnAuthorization() callback.
- API change: renamed SteamUserCallback.onValidateAuthTicketResponse() to SteamUserCallback.onValidateAuthTicket().
- API change: all uses of appID in member functions and callbacks now use 32-bit ints.

### [1.4.0]
- Removed steamworks4j-natives subproject. Native libraries are now added as resources to steamworks4j.jar directly.
- Added functions to query global stats. This also adds a new method to SteamUserStatsCallback (#36).
- Updated shared library loader, adding a few fallbacks in case executables cannot be written to, or launched from, java.io.tmpdir.
- API change: SteamAPI.init() now throws SteamException if loading of native libraries fails. The function now returns false only if initialization fails for Steamworks reasons (e.g. no Steam client running).

### [1.3.1]
- Updated to Steamworks SDK v1.37.
- Added some missing SteamUtils functions (#35).
- Added more fields returned in SteamUGCDetails (#34).

### [1.3.0]
- Updated to Steamworks SDK v1.36.
- API change: changed auth ticket API to use SteamAuthTicket objects instead of plain integer handles.
- API change: getAuthSessionTicket() now takes an int[1] argument to return the the required buffer size.
- Added buffer validation checks to auth ticket functions.
- Added auth ticket test functions to SteamNetworkingTestApp.
- Added two missing SteamRemoteStorage.updatePublishedFile*() functions.
- Added public functions to serialize SteamID as plain 64 bit value.
- Added ISteamUGC-based workshop functions.
- API change: SteamUtils now exposes a SteamUtilsCallback interface.
- Added SteamUtils.onSteamShutdown() callback.
- Added game-defined details to leaderboard score queries & uploads.

### [1.2.3]
- Fixed native pointers possibly truncated on Windows 64 bit.

### [1.2.2]
- Updated to Steamworks SDK v1.35.
- Added buffer validation check to SteamUtils.getImageRGBA().
- Added SteamMatchmaking interface, implementing most lobby functions.
- Added SteamMatchmakingTestApp.
- API change: added SteamFriendsCallback.onGameLobbyJoinRequested().
- API change: added SteamFriendsCallback.onGameOverlayActivated(), onAvatarImageLoaded().

### [1.2.1]
- Added UserStatsStored, UserAchievementStored callbacks.
- Added a few SteamApps utility functions.

### [1.2.0]
- Prebuilt libraries now compiled with Steamworks SDK v1.34.
- Native code generator moved to separate sub-project. Removes runtime dependency on gdx-jnigen.
- Moved documentation to GitHub pages: http://code-disaster.github.io/steamworks4j/
- Added SteamUGC.getItemInstallInfo(), getItemDownloadInfo(), requestUGCDetails(), createQueryUGCDetailsRequest().
- Added warning message hook.
- Added SteamAPI.restartAppIfNecessary().
- Added auth session API to SteamUser. This also adds a SteamUserCallback interface.
- Added SteamHTTP interface
- Added SteamHTTPTestApp which calls some basic Steam Web API functions.
- API change: moved some enums from SteamGameServer to SteamAuth.

### [1.1.0]
- Prebuilt libraries now compiled with Steamworks SDK v1.33b.
- Added SteamUserStats.findOrCreateLeaderboard(), getLeaderboardSortMethod(), getLeaderboardDisplayType().
- Added SteamUGC.createQueryAllUGCRequest(), subscribeItems(), unsubscribeItems(), getNumSubscribedItems(), getSubscribedItems(). [thanks, titoasty]
- Added SteamFriends API to retrieve avatar images.
- Added SteamFriends API to enumerate friend lists and query persona states.
- Added SteamGameServer, SteamGameServerStats and SteamNetworking APIs. [initially added by Francisco "Franz" Bischoff]
- Updated test application to prepare for more (isolated) tests. SteamAPITestApplication is now called SteamClientAPITestApp.
- Added SteamNetworkingTestApp for testing P2P networking functions.
- Removed static native callback objects. They are now created and stored per instance of each interface.
- Added functions to check for SteamAPICall status and result.
- Added option to specify path to jar or directory from where to load native libraries. Mostly meant to avoid need of 'mvn package' after recompiling native code during development.
- API change: Removed static dispose() methods. User applications need to call SteamInterface.dispose() to prevent native memory leaks.
- API change: SteamUserStatsCallback.onUserStatsReceived() is now passed a SteamID instead of a long value.
- API change: Simplified all interface constructors by removing 'pointer' parameters. They are obtained internally now.

### [1.0.2]
- Added wrapper to ISteamFriends::RequestUserInformation().
- Added PersonaStateChange callback.

### [1.0.1]
- Updated to Steamworks SDK v1.32.
- Added ISteamApps wrapper to expose BIsSubscribedApp() function.
- Native Windows library is now built using premake5 and Visual Studio 2013.

### [1.0.0]
- First version released on Maven Central.
