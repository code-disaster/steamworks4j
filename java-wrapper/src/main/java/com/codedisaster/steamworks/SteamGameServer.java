package com.codedisaster.steamworks;

/**
 *
 * @author Francisco "Franz" Bischoff
 */
import java.io.*;

public class SteamGameServer extends SteamInterface {

    public enum EBeginAuthSessionResult {

        k_EBeginAuthSessionResultOK, // Ticket is valid for this game and this steamID.
        k_EBeginAuthSessionResultInvalidTicket, // Ticket is not valid.
        k_EBeginAuthSessionResultDuplicateRequest, // A ticket has already been submitted for this steamID
        k_EBeginAuthSessionResultInvalidVersion, // Ticket is from an incompatible interface version
        k_EBeginAuthSessionResultGameMismatch, // Ticket is not for this game
        k_EBeginAuthSessionResultExpiredTicket // Ticket has expired
    }

    public enum EAuthSessionResponse {

        k_EAuthSessionResponseOK, // Steam has verified the user is online, the ticket is valid and ticket has not been reused.  
        k_EAuthSessionResponseUserNotConnectedToSteam, // The user in question is not connected to steam  
        k_EAuthSessionResponseNoLicenseOrExpired, // The license has expired.  
        k_EAuthSessionResponseVACBanned, // The user is VAC banned for this game. 
        k_EAuthSessionResponseLoggedInElseWhere, // The user account has logged in elsewhere and the session containing the game instance has been disconnected. 
        k_EAuthSessionResponseVACCheckTimedOut, // VAC has been unable to perform anti-cheat checks on this user  
        k_EAuthSessionResponseAuthTicketCanceled, // The ticket has been canceled by the issuer  
        k_EAuthSessionResponseAuthTicketInvalidAlreadyUsed, // This ticket has already been used, it is not valid.  
        k_EAuthSessionResponseAuthTicketInvalid, // This ticket is not from a user instance currently connected to steam. 
        k_EAuthSessionResponsePublisherIssuedBan  // The user is banned for this game. The ban came via the web api and not VAC  
    };

    public enum EUserHasLicenseForAppResult {

        k_EUserHasLicenseResultHasLicense, // User has a license for specified app
        k_EUserHasLicenseResultDoesNotHaveLicense, // User does not have a license for the specified app
        k_EUserHasLicenseResultNoAuth	// User has not been authenticated
    };

    public enum EDenyReason {

        k_EDenyInvalid,
        k_EDenyInvalidVersion,
        k_EDenyGeneric,
        k_EDenyNotLoggedOn,
        k_EDenyNoLicense,
        k_EDenyCheater,
        k_EDenyLoggedInElseWhere,
        k_EDenyUnknownText,
        k_EDenyIncompatibleAnticheat,
        k_EDenyMemoryCorruption,
        k_EDenyIncompatibleSoftware,
        k_EDenySteamConnectionLost,
        k_EDenySteamConnectionError,
        k_EDenySteamResponseTimedOut,
        k_EDenySteamValidationStalled,
        k_EDenySteamOwnerLeftGuestUser,
    };

    public enum EResult {

        k_EResultOK, // success
        k_EResultFail, // generic failure 
        k_EResultNoConnection, // no/failed network connection
        //	k_EResultNoConnectionRetry,				// OBSOLETE - removed
        k_EResultInvalidPassword, // password/ticket is invalid
        k_EResultLoggedInElsewhere, // same user logged in elsewhere
        k_EResultInvalidProtocolVer, // protocol version is incorrect
        k_EResultInvalidParam, // a parameter is incorrect
        k_EResultFileNotFound, // file was not found
        k_EResultBusy, // called method busy - action not taken
        k_EResultInvalidState, // called object was in an invalid state
        k_EResultInvalidName, // name is invalid
        k_EResultInvalidEmail, // email is invalid
        k_EResultDuplicateName, // name is not unique
        k_EResultAccessDenied, // access is denied
        k_EResultTimeout, // operation timed out
        k_EResultBanned, // VAC2 banned
        k_EResultAccountNotFound, // account not found
        k_EResultInvalidSteamID, // steamID is invalid
        k_EResultServiceUnavailable, // The requested service is currently unavailable
        k_EResultNotLoggedOn, // The user is not logged on
        k_EResultPending, // Request is pending (may be in process, or waiting on third party)
        k_EResultEncryptionFailure, // Encryption or Decryption failed
        k_EResultInsufficientPrivilege, // Insufficient privilege
        k_EResultLimitExceeded, // Too much of a good thing
        k_EResultRevoked, // Access has been revoked (used for revoked guest passes)
        k_EResultExpired, // License/Guest pass the user is trying to access is expired
        k_EResultAlreadyRedeemed, // Guest pass has already been redeemed by account, cannot be acked again
        k_EResultDuplicateRequest, // The request is a duplicate and the action has already occurred in the past, ignored this time
        k_EResultAlreadyOwned, // All the games in this guest pass redemption request are already owned by the user
        k_EResultIPNotFound, // IP address not found
        k_EResultPersistFailed, // failed to write change to the data store
        k_EResultLockingFailed, // failed to acquire access lock for this operation
        k_EResultLogonSessionReplaced,
        k_EResultConnectFailed,
        k_EResultHandshakeFailed,
        k_EResultIOFailure,
        k_EResultRemoteDisconnect,
        k_EResultShoppingCartNotFound, // failed to find the shopping cart requested
        k_EResultBlocked, // a user didn't allow it
        k_EResultIgnored, // target is ignoring sender
        k_EResultNoMatch, // nothing matching the request found
        k_EResultAccountDisabled,
        k_EResultServiceReadOnly, // this service is not accepting content changes right now
        k_EResultAccountNotFeatured, // account doesn't have value, so this feature isn't available
        k_EResultAdministratorOK, // allowed to take this action, but only because requester is admin
        k_EResultContentVersion, // A Version mismatch in content transmitted within the Steam protocol.
        k_EResultTryAnotherCM, // The current CM can't service the user making a request, user should try another.
        k_EResultPasswordRequiredToKickSession,// You are already logged in elsewhere, this cached credential login has failed.
        k_EResultAlreadyLoggedInElsewhere, // You are already logged in elsewhere, you must wait
        k_EResultSuspended, // Long running operation (content download) suspended/paused
        k_EResultCancelled, // Operation canceled (typically by user: content download)
        k_EResultDataCorruption, // Operation canceled because data is ill formed or unrecoverable
        k_EResultDiskFull, // Operation canceled - not enough disk space.
        k_EResultRemoteCallFailed, // an remote call or IPC call failed
        k_EResultPasswordUnset, // Password could not be verified as it's unset server side
        k_EResultExternalAccountUnlinked, // External account (PSN, Facebook...) is not linked to a Steam account
        k_EResultPSNTicketInvalid, // PSN ticket was invalid
        k_EResultExternalAccountAlreadyLinked, // External account (PSN, Facebook...) is already linked to some other account, must explicitly request to replace/delete the link first
        k_EResultRemoteFileConflict, // The sync cannot resume due to a conflict between the local and remote files
        k_EResultIllegalPassword, // The requested new password is not legal
        k_EResultSameAsPreviousValue, // new value is the same as the old one ( secret question and answer )
        k_EResultAccountLogonDenied, // account login denied due to 2nd factor authentication failure
        k_EResultCannotUseOldPassword, // The requested new password is not legal
        k_EResultInvalidLoginAuthCode, // account login denied due to auth code invalid
        k_EResultAccountLogonDeniedNoMail, // account login denied due to 2nd factor auth failure - and no mail has been sent
        k_EResultHardwareNotCapableOfIPT, // 
        k_EResultIPTInitError, // 
        k_EResultParentalControlRestricted, // operation failed due to parental control restrictions for current user
        k_EResultFacebookQueryError, // Facebook query returned an error
        k_EResultExpiredLoginAuthCode, // account login denied due to auth code expired
        k_EResultIPLoginRestrictionFailed,
        k_EResultAccountLockedDown,
        k_EResultAccountLogonDeniedVerifiedEmailRequired,
        k_EResultNoMatchingURL,
        k_EResultBadResponse, // parse failure, missing field, etc.
        k_EResultRequirePasswordReEntry, // The user cannot complete the action until they re-enter their password
        k_EResultValueOutOfRange, // the value entered is outside the acceptable range
        k_EResultUnexpectedError, // something happened that we didn't expect to ever happen
        k_EResultDisabled, // The requested service has been configured to be unavailable
        k_EResultInvalidCEGSubmission, // The set of files submitted to the CEG server are not valid !
        k_EResultRestrictedDevice, // The device being used is not allowed to perform this action
        k_EResultRegionLocked, // The action could not be complete because it is region restricted
        k_EResultRateLimitExceeded, // Temporary rate limit exceeded, try again later, different from k_EResultLimitExceeded which may be permanent
        k_EResultAccountLoginDeniedNeedTwoFactor, // Need two-factor code to login
        k_EResultItemDeleted, // The thing we're trying to access has been deleted
        k_EResultAccountLoginDeniedThrottle, // login attempt failed, try to throttle response to possible attacker
        k_EResultTwoFactorCodeMismatch, // two factor code mismatch
        k_EResultTwoFactorActivationCodeMismatch, // activation code for two-factor didn't match
        k_EResultAccountAssociatedToMultiplePartners, // account has been associated with multiple partners
        k_EResultNotModified, // data not modified
    };

    public SteamGameServer(long pointer, SteamGameServerCallback callback) {
        super(pointer);
        registerCallback(new SteamGameServerCallbackAdapter(callback));
    }

    static void dispose() {
        registerCallback(null);
    }

    // @off

    /*JNI
     #include <steam_gameserver.h>
     #include "SteamGameServerCallback.h"

     static SteamGameServerCallback* callback = NULL;
     */
    static private native boolean registerCallback(SteamGameServerCallbackAdapter javaCallback); /*
     if (callback != NULL) {
     delete callback;
     callback = NULL;
     }

     if (javaCallback != NULL) {
     callback = new SteamGameServerCallback(env, javaCallback);
     }

     return callback != NULL;
     */


    static public native void setProduct(String pszProduct); /*
     SteamGameServer()->SetProduct(pszProduct);
     */


    static public native void setGameDescription(String pszGameDescription); /*
     SteamGameServer()->SetGameDescription(pszGameDescription);
     */


    static public native void setModDir(String pszModDir); /*
     SteamGameServer()->SetModDir(pszModDir);
     */


    static public native void setDedicatedServer(boolean bDedicated); /*
     SteamGameServer()->SetDedicatedServer(bDedicated);
     */


    static public native void logOn(String pszToken); /*
     SteamGameServer()->LogOn(pszToken);
     */


    static public native void logOnAnonymous(); /*
     SteamGameServer()->LogOnAnonymous();
     */


    static public native void logOff(); /*
     SteamGameServer()->LogOff();
     */


    static public native boolean bloggedOn(); /*
     return SteamGameServer()->BLoggedOn();
     */


    static public native boolean bSecure(); /*
     return SteamGameServer()->BSecure();
     */


    static public SteamID getSteamID() {
        SteamID id = new SteamID(nativeGetSteamID());
        return id;
    }

    static private native long nativeGetSteamID(); /*
     return SteamGameServer()->GetSteamID().ConvertToUint64();
     */


    static public native boolean wasRestartRequested(); /*
     return SteamGameServer()->WasRestartRequested();
     */


    static public native void setMaxPlayerCount(int cPlayersMax); /*
     SteamGameServer()->SetMaxPlayerCount(cPlayersMax);
     */


    static public native void setBotPlayerCount(int cBotplayers); /*
     SteamGameServer()->SetBotPlayerCount(cBotplayers);
     */


    static public native void setServerName(String pszServerName); /*
     SteamGameServer()->SetServerName(pszServerName);
     */


    static public native void setMapName(String pszMapName); /*
     SteamGameServer()->SetMapName(pszMapName);
     */


    static public native void setPasswordProtected(boolean bPasswordProtected); /*
     SteamGameServer()->SetPasswordProtected(bPasswordProtected);
     */


    static public native void setSpectatorPort(short unSpectatorPort); /*
     SteamGameServer()->SetSpectatorPort(unSpectatorPort);
     */


    static public native void setSpectatorServerName(String pszSpectatorServerName); /*
     SteamGameServer()->SetSpectatorServerName(pszSpectatorServerName);
     */


    static public native void clearAllKeyValues(); /*
     SteamGameServer()->ClearAllKeyValues();
     */


    static public native void setKeyValue(String pKey, String pValue); /*
     SteamGameServer()->SetKeyValue(pKey, pValue);
     */


    static public native void setGameTags(String pchGameTags); /*
     SteamGameServer()->SetGameTags(pchGameTags);
     */


    static public native void setGameData(String pchGameData); /*
     SteamGameServer()->SetGameData(pchGameData);
     */


    static public native void setRegion(String pszRegion); /*
     SteamGameServer()->SetRegion(pszRegion);
     */


    static public void sendUserDisconnect(SteamID steamIDUser) {
        nativeSendUserDisconnect(steamIDUser.handle);
    }

    static private native void nativeSendUserDisconnect(long steamIDUser); /*
     SteamGameServer()->SendUserDisconnect((uint64)steamIDUser);
     */


    static public boolean bUpdateUserData(SteamID steamIDUser, String pchPlayerName, int uScore) {
        return nativeBUpdateUserData(steamIDUser.handle, pchPlayerName, uScore);
    }

    static private native boolean nativeBUpdateUserData(long steamIDUser, String pchPlayerName, int uScore); /*
     return SteamGameServer()->BUpdateUserData((uint64)steamIDUser, pchPlayerName, uScore);
     */

// HAuthTicket  GetAuthSessionTicket (void *pTicket, int cbMaxTicket, uint32 *pcbTicket)

    static public EBeginAuthSessionResult beginAuthSession(Integer pAuthTicket, int cbAuthTicket, SteamID steamID) {
        return EBeginAuthSessionResult.values()[nativeBeginAuthSession(pAuthTicket, cbAuthTicket, steamID.handle)];
    }

    static private native int nativeBeginAuthSession(Integer pAuthTicket, int cbAuthTicket, long steamID); /*
     return SteamGameServer()->BeginAuthSession(pAuthTicket, cbAuthTicket, (uint64) steamID);
     */


    static public void endAuthSession(SteamID steamID) {
        nativeEndAuthSession(steamID.handle);
    }

    static private native void nativeEndAuthSession(long steamID); /*
     SteamGameServer()->EndAuthSession ((uint64) steamID);
     */


    static public native void cancelAuthTicket(int hAuthTicket); /*
     SteamGameServer()->CancelAuthTicket (hAuthTicket);
     */


    static public EUserHasLicenseForAppResult userHasLicenseForApp(SteamID steamID, long appID) {
        return EUserHasLicenseForAppResult.values()[nativeUserHasLicenseForApp(steamID.handle, appID)];
    }

    static private native int nativeUserHasLicenseForApp(long steamID, long appID); /*
     return SteamGameServer()->UserHasLicenseForApp((uint64) steamID, (AppId_t) appID);
     */


    static public boolean requestUserGroupStatus(SteamID steamIDUser, SteamID steamIDGroup) {
        return nativeRequestUserGroupStatus(steamIDUser.handle, steamIDGroup.handle);
    }

    static private native boolean nativeRequestUserGroupStatus(long steamIDUser, long steamIDGroup); /*
     return SteamGameServer()->RequestUserGroupStatus((uint64)steamIDUser, (uint64)steamIDGroup);
     */


    static public native int getPublicIP(); /*
     return SteamGameServer()->GetPublicIP();
     */

// bool  HandleIncomingPacket (const void *pData, int cbData, uint32 srcIP, uint16 srcPort)

// int  GetNextOutgoingPacket (void *pOut, int cbMaxOut, uint32 *pNetAdr, uint16 *pPort)
    static public native void enableHeartbeats(boolean bActive); /*
     SteamGameServer()->EnableHeartbeats(bActive);
     */


    static public native void setHeartbeatInterval(int iHeartbeatInterval); /*
     SteamGameServer()->SetHeartbeatInterval (iHeartbeatInterval);
     */


    static public native void forceHeartbeat(); /*
     SteamGameServer()->ForceHeartbeat();
     */


    static public SteamAPICall associateWithClan(SteamID steamIDClan) {
        return new SteamAPICall(nativegAssociateWithClan(steamIDClan.handle));
    }

    static private native long nativegAssociateWithClan(long steamIDClan); /*
     return SteamGameServer()->AssociateWithClan((uint64) steamIDClan);
     */


    static public SteamAPICall computeNewPlayerCompatibility(SteamID steamIDNewPlayer) {
        return new SteamAPICall(nativeComputeNewPlayerCompatibility(steamIDNewPlayer.handle));
    }

    static private native long nativeComputeNewPlayerCompatibility(long steamIDNewPlayer); /*
     return SteamGameServer()->ComputeNewPlayerCompatibility((uint64) steamIDNewPlayer);
     */

}
