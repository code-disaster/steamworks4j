package com.codedisaster.steamworks;

import java.nio.ByteBuffer;

public class SteamRemoteStorage {

	private long pointer;

	public SteamRemoteStorage(long pointer) {
		this.pointer = pointer;
	}

	static void dispose() {
	}

	public boolean fileWrite(String name, ByteBuffer data, int length) throws SteamException {
		if (!data.isDirect()) {
			throw new SteamException("Buffer must be direct!");
		}
		return fileWrite(pointer, name, data, length);
	}

	public boolean fileRead(String name, ByteBuffer buffer, int capacity) throws SteamException {
		if (!buffer.isDirect()) {
			throw new SteamException("Buffer must be direct!");
		}
		return fileRead(pointer, name, buffer, capacity);
	}

	public boolean fileDelete(String name) {
		return fileDelete(pointer, name);
	}

	public boolean fileExists(String name) {
		return fileExists(pointer, name);
	}

	public int getFileSize(String name) {
		return getFileSize(pointer, name);
	}

	public int getFileCount() {
		return getFileCount(pointer);
	}

	public String getFileNameAndSize(int index, int[] sizes) {
		return getFileNameAndSize(pointer, index, sizes);
	}

	/*JNI
		#include <steam_api.h>
	*/

	static private native boolean fileWrite(long pointer, String name, ByteBuffer data, int length); /*
		ISteamRemoteStorage* storage = (ISteamRemoteStorage*) pointer;
		return storage->FileWrite(name, data, length);
	*/

	static private native boolean fileRead(long pointer, String name, ByteBuffer buffer, int capacity); /*
		ISteamRemoteStorage* storage = (ISteamRemoteStorage*) pointer;
		return storage->FileRead(name, buffer, capacity);
	*/

	static private native boolean fileDelete(long pointer, String name); /*
		ISteamRemoteStorage* storage = (ISteamRemoteStorage*) pointer;
		return storage->FileDelete(name);
	*/

	static private native boolean fileExists(long pointer, String name); /*
		ISteamRemoteStorage* storage = (ISteamRemoteStorage*) pointer;
		return storage->FileExists(name);
	*/

	static private native int getFileSize(long pointer, String name); /*
		ISteamRemoteStorage* storage = (ISteamRemoteStorage*) pointer;
		return storage->GetFileSize(name);
	*/

	static private native int getFileCount(long pointer); /*
		ISteamRemoteStorage* storage = (ISteamRemoteStorage*) pointer;
		return storage->GetFileCount();
	*/

	static private native String getFileNameAndSize(long pointer, int index, int[] sizes); /*
		ISteamRemoteStorage* storage = (ISteamRemoteStorage*) pointer;
		jstring name = env->NewStringUTF(storage->GetFileNameAndSize(index, &sizes[0]));
		return name;
	*/

}
