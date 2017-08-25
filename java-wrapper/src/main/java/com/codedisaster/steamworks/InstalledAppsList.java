package com.codedisaster.steamworks;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.AbstractList;
import java.util.Iterator;

import static com.codedisaster.steamworks.SteamTypeUtils.UINT32_SIZE;
import static com.codedisaster.steamworks.SteamTypeUtils.wrapUint32;

/**
 * Package private read only {@link java.util.List} implementation backed by a {@link java.nio.DirectByteBuffer}.
 * This is an internal class used by {@link SteamAppList#getInstalledApps(int)}.
 * All methods causing structural modification throw {@link UnsupportedOperationException}.
 */
class InstalledAppsList extends AbstractList<Long> {

	final ByteBuffer buffer;

	InstalledAppsList(int capacity) {
		buffer = ByteBuffer
				.allocateDirect(capacity * UINT32_SIZE)
				.order(ByteOrder.nativeOrder()); // so that DirectByteBuffer translates getInt() to big endian correctly
	}

	@Override
	public Long get(int i) {
		return wrapUint32(buffer.getInt(i * UINT32_SIZE)); // offset is checked implicitly by DirectByteBuffer
	}

	@Override
	public int size() {
		return buffer.limit() / UINT32_SIZE;
	}

	@Override
	public Iterator<Long> iterator() {
		return new Iter();
	}

	private class Iter implements Iterator<Long> {

		private final ByteBuffer iterBuf;

		Iter() {
			this.iterBuf = InstalledAppsList.this.buffer
					.duplicate()
					.order(ByteOrder.nativeOrder()); // duplicate() does not pass byte order setting to a new instance
		}

		@Override
		public boolean hasNext() {
			return iterBuf.hasRemaining();
		}

		@Override
		public Long next() {
			return wrapUint32(iterBuf.getInt());
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
}
