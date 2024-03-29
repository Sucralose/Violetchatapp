package com.benavivi.violetchatapp.utilities;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;

public class CameraHandler {

/***
 * @param context - Context
 * @param bitmapImage - Bitmap Image
 * @return Uri Image
 * Reference: - Transform Bitmap to Uri
 * https://colinyeoh.wordpress.com/2012/05/18/android-getting-image-uri-from-bitmap
 */
public static Uri getImageUriFromBitmap( Context context, Bitmap bitmapImage ) {
	ByteArrayOutputStream bytes = new ByteArrayOutputStream( );
	bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
	String path = MediaStore.Images.Media.insertImage(context.getContentResolver( ), bitmapImage, "Title", null);
	Uri imageUri = Uri.parse(path);
	return imageUri;
}

public static boolean hasCameraPermission( Context context ) {
	return context.checkSelfPermission(android.Manifest.permission.CAMERA) == android.content.pm.PackageManager.PERMISSION_GRANTED;
}

public static boolean hasStoragePermission( Context context ) {
	return context.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == android.content.pm.PackageManager.PERMISSION_GRANTED;
}

public static void requestCameraPermission( Context context ) {
	( (AppCompatActivity) context ).requestPermissions(new String[]{ android.Manifest.permission.CAMERA }, 0);
}

public static void requestStoragePermission( Context context ) {
	( (AppCompatActivity) context ).requestPermissions(
		new String[]{ Manifest.permission.READ_EXTERNAL_STORAGE,
			Manifest.permission.WRITE_EXTERNAL_STORAGE
			, Manifest.permission.READ_MEDIA_IMAGES }, 0);
}


/**
 * @param context
 * @param pickImageFromGallery - Activity Result - What to do when choosing from gallery
 * @param takePicture          - Activity Result - What to do when snapping a picture.
 */
public static void selectImage( Context context, ActivityResultLauncher<Intent> pickImageFromGallery, ActivityResultLauncher<Intent> takePicture ) {
	final String[] options = { "Take Photo", "Choose from Gallery", "Cancel" };

	AlertDialog.Builder builder = new AlertDialog.Builder(context);
	builder.setTitle("Choose a method");
	builder.setItems(options, ( dialog, item ) ->
		alertDialogOnClick(dialog, item, context, options, pickImageFromGallery, takePicture));
	builder.show( );
}

private static void alertDialogOnClick( DialogInterface dialog, int item, Context context, String[] options, ActivityResultLauncher<Intent> pickImageFromGallery, ActivityResultLauncher<Intent> takePicture ) {
	switch ( options[item] ) {
		case "Take Photo":
			if ( !hasCameraPermission(context) ) {
				requestCameraPermission(context);
				break;
			}

			Intent takeImageIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			takeImageIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
			takePicture.launch(takeImageIntent);
			break;

		case "Choose from Gallery":
			if ( hasStoragePermission(context) ) {
				requestStoragePermission(context);
				break;
			}

			Intent pickImageIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			pickImageIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
			pickImageIntent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
			pickImageFromGallery.launch(pickImageIntent);
			break;

		case "Cancel":
			dialog.dismiss( );
			break;
	}
}
}

