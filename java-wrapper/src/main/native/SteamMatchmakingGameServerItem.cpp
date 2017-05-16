#include "SteamMatchmakingGameServerItem.h"

void convertGameServerItem(jobject item, JNIEnv* env, const gameserveritem_t& server) {
	jclass ex = env->FindClass("com/codedisaster/steamworks/SteamException");
	jclass itemClazz = env->FindClass("com/codedisaster/steamworks/SteamMatchmakingGameServerItem");

	jfieldID field = env->GetFieldID(itemClazz, "netAdr", "Lcom/codedisaster/steamworks/SteamMatchmakingServerNetAdr;");
	jclass netAdrClazz = env->FindClass("com/codedisaster/steamworks/SteamMatchmakingServerNetAdr");
	jobject netAdr = env->GetObjectField(item, field);
	if (netAdrClazz != nullptr && netAdr != nullptr) {
		field = env->GetFieldID(netAdrClazz, "connectionPort", "S");
		env->SetShortField(netAdr, field, server.m_NetAdr.GetConnectionPort());

		field = env->GetFieldID(netAdrClazz, "queryPort", "S");
		env->SetShortField(netAdr, field, server.m_NetAdr.GetQueryPort());

		field = env->GetFieldID(netAdrClazz, "ip", "I");
		env->SetIntField(netAdr, field, server.m_NetAdr.GetIP());
	} else {
		env->ThrowNew(ex, "Could not access field: SteamMatchmakingGameServerItem#netAdr");
	}

	field = env->GetFieldID(itemClazz, "ping", "I");
	env->SetIntField(item, field, server.m_nPing);

	field = env->GetFieldID(itemClazz, "hadSuccessfulResponse", "Z");
	env->SetBooleanField(item, field, server.m_bHadSuccessfulResponse);

	field = env->GetFieldID(itemClazz, "doNotRefresh", "Z");
	env->SetBooleanField(item, field, server.m_bDoNotRefresh);

	field = env->GetFieldID(itemClazz, "gameDir", "Ljava/lang/String;");
	env->SetObjectField(item, field, env->NewStringUTF(server.m_szGameDir));

	field = env->GetFieldID(itemClazz, "map", "Ljava/lang/String;");
	env->SetObjectField(item, field, env->NewStringUTF(server.m_szMap));

	field = env->GetFieldID(itemClazz, "gameDescription", "Ljava/lang/String;");
	env->SetObjectField(item, field, env->NewStringUTF(server.m_szGameDescription));

	field = env->GetFieldID(itemClazz, "appID", "I");
	env->SetIntField(item, field, server.m_nAppID);

	field = env->GetFieldID(itemClazz, "players", "I");
	env->SetIntField(item, field, server.m_nPlayers);

	field = env->GetFieldID(itemClazz, "maxPlayers", "I");
	env->SetIntField(item, field, server.m_nMaxPlayers);

	field = env->GetFieldID(itemClazz, "botPlayers", "I");
	env->SetIntField(item, field, server.m_nBotPlayers);

	field = env->GetFieldID(itemClazz, "password", "Z");
	env->SetBooleanField(item, field, server.m_bPassword);

	field = env->GetFieldID(itemClazz, "secure", "Z");
	env->SetBooleanField(item, field, server.m_bSecure);

	field = env->GetFieldID(itemClazz, "timeLastPlayed", "I");
	env->SetIntField(item, field, server.m_ulTimeLastPlayed);

	field = env->GetFieldID(itemClazz, "serverVersion", "I");
	env->SetIntField(item, field, server.m_nServerVersion);

	field = env->GetFieldID(itemClazz, "serverName", "Ljava/lang/String;");
	env->SetObjectField(item, field, env->NewStringUTF(server.GetName()));

	field = env->GetFieldID(itemClazz, "gameTags", "Ljava/lang/String;");
	env->SetObjectField(item, field, env->NewStringUTF(server.m_szGameTags));

	field = env->GetFieldID(itemClazz, "steamID", "J");
	env->SetLongField(item, field, server.m_steamID.ConvertToUint64());
}

jobject createGameServerItem(JNIEnv* env, const gameserveritem_t& server) {

	jclass ex = env->FindClass("com/codedisaster/steamworks/SteamException");
	jclass itemClazz = env->FindClass("com/codedisaster/steamworks/SteamMatchmakingGameServerItem");

	if (itemClazz != nullptr) {
		jmethodID itemCtor = env->GetMethodID(itemClazz, "<init>", "()V");
		if (itemCtor != nullptr) {
			jobject item = env->NewObject(itemClazz, itemCtor);
			convertGameServerItem(item, env, server);
			return item;
		} else {
			env->ThrowNew(ex, "Could not create instance of class: SteamMatchmakingGameServerItem");
		}
	} else {
		env->ThrowNew(ex, "Could not find class: SteamMatchmakingGameServerItem");
	}

	return nullptr;
}
