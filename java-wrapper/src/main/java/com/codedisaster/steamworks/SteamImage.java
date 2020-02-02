package com.codedisaster.steamworks;

import java.nio.ByteBuffer;

public class SteamImage {
	private final int imageHandle;
	private int width = -1;
	private int height = -1;
	private ByteBuffer imageBuffer = null;

	SteamImage(int imageHandle) {
		this.imageHandle = imageHandle;
	}

	public int getImageHandle() {
		return imageHandle;
	}

	public int getWidth(SteamUtils steamUtils) {
		if (width == -1) {
			this.getImageSizeAndSet(steamUtils);
		}
		return width;
	}

	public int getHeight(SteamUtils steamUtils) {
		if (height == -1) {
			this.getImageSizeAndSet(steamUtils);
		}
		return height;
	}

	public ByteBuffer getImageBuffer(SteamUtils steamUtils) throws SteamException {
		if (this.imageBuffer != null) {
			return this.imageBuffer;
		}
		ByteBuffer imageBuffer = ByteBuffer.allocateDirect(this.getWidth(steamUtils) * this.getHeight(steamUtils) * 4);
		if (steamUtils.getImageRGBA(getImageHandle(), imageBuffer)) {
			this.imageBuffer = imageBuffer;
		}
		return this.imageBuffer;
	}

	protected void getImageSizeAndSet(SteamUtils steamUtils) {
		int[] size = new int[2];
		steamUtils.getImageSize(getImageHandle(), size);
		this.width = size[0];
		this.height = size[1];
	}

}