/**
 * 
 */
package com.haole.core.utils;

/**
 * @author shengjunzhao
 * 
 */
public class ImageType {
	public static boolean isImageJpeg(byte[] blob) {
		if (blob != null && blob.length > 2) {
			// 0xFFD8
			if (blob[0] == (byte) 0xFF && blob[1] == (byte) 0xD8) {
				return true;
			}
		}
		return false;
	}

	public static boolean isImageBmp(byte[] blob) {
		if (blob != null && blob.length > 2) {
			// BM: Windows 3.1x, 95, NT, …
			// BA: OS/2 Bitmap Array
			// CI: OS/2 Color Icon
			// CP: OS/2 Color Pointer
			// IC: OS/2 Icon
			// PT: OS/2 Pointer
			if ((blob[0] == 'B' && blob[1] == 'M')
					|| (blob[0] == 'B' && blob[1] == 'A')) {
				return true;
			}
		}
		return false;
	}

	public static boolean isImagePng(byte[] blob) {
		if (blob != null && blob.length > 8) {
			// 89 50 4E 47 0D 0A 1A 0A
			if (blob[0] == (byte) 0x89 && blob[1] == (byte) 0x50
					&& blob[2] == (byte) 0x4E && blob[3] == (byte) 0x47
					&& blob[4] == (byte) 0x0D && blob[5] == (byte) 0x0A
					&& blob[6] == (byte) 0x1A && blob[7] == (byte) 0x0A)
				return true;
		}
		return false;
	}

	public static boolean isImageGif(byte[] blob) {
		if (blob != null && blob.length > 3) { // 只有3字节的gif？这里仅避免异常
			if (blob[0] == 'G' && blob[1] == 'I' && blob[2] == 'F')
				return true;
		}
		return false;
	}
}
