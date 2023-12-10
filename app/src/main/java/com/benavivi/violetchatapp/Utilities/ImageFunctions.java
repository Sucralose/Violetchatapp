package com.benavivi.violetchatapp.Utilities;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;
import java.util.Base64;

public class ImageFunctions {
	public static String encodeBitmapImage(Bitmap bitmapImage){
		int previewWidth = 150;
		int previewHeight = bitmapImage.getHeight() * previewWidth / bitmapImage.getWidth();
		Bitmap previewBitmapImage = Bitmap.createScaledBitmap(bitmapImage,previewWidth,previewHeight,false);
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		previewBitmapImage.compress(Bitmap.CompressFormat.JPEG,50, byteArrayOutputStream);
		byte[] bytes = byteArrayOutputStream.toByteArray();
		return Base64.getEncoder().encodeToString(bytes);
	}
}
