package com.benavivi.violetchatapp.utilities;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class FirebaseManager {

	public static void updateUserProfile (String displayName, Uri profilePictureImageUri) {
		FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
		if(user == null) return;
		UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
							  .setDisplayName(displayName)
							  .setPhotoUri(profilePictureImageUri)
							  .build();
		user.updateProfile(profileUpdates)
			.addOnCompleteListener(task -> {
				if (task.isSuccessful()) {
					Log.d("UPDATEUsrProfile", "User profile updated.");
				}
			});
	}
	public static void updateUserProfile (String displayName) {
		FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
		if(user == null) return;
		UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
							  .setDisplayName(displayName)
							  .build();
		user.updateProfile(profileUpdates)
			.addOnCompleteListener(task -> {
				if (task.isSuccessful()) {
					Log.d("UPDATEUserProfile", "User profile updated.");
				}
			});
	}
	public static void updateUserProfile (Uri profilePictureImageUri) {
		FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
		if(user == null) return;
		UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
							  .setPhotoUri(profilePictureImageUri)
							  .build();
		user.updateProfile(profileUpdates)
			.addOnCompleteListener(new OnCompleteListener<Void>() {
				@Override
				public void onComplete(@NonNull Task<Void> task) {
					if (task.isSuccessful()) {
						Log.d("UPDATEUserProfile", "User profile updated.");
					}
				}
			});
	}
	public static void signOut(){
		FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
		firebaseAuth.signOut();
	}

	public static boolean isSignedIn(){
		FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
		return user!=null;
	}

	public static String getUserDisplayName(){
		return FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
	}

	public static String getUserEmail(){
		return FirebaseAuth.getInstance().getCurrentUser().getUid();
	}

	public static Uri getUserUrlImage(){
		return FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl();
	}


}
