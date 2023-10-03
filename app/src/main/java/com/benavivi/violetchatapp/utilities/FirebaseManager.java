package com.benavivi.violetchatapp.utilities;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.benavivi.violetchatapp.activities.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

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

	public static Task<AuthResult> signUp(String email, String password, String displayName, Uri profilePictureImageUri){
		return FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
			.addOnSuccessListener(authResult -> {
				//FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
				updateUserProfile(displayName,profilePictureImageUri);
				addToUserFirestore(email, displayName, profilePictureImageUri);
			});

	}

	public static Task<AuthResult> signIn(String email, String password){
		return FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password);
	}
	public static Task<Void> sendPasswordReset(String email){
		return FirebaseAuth.getInstance().sendPasswordResetEmail(email);
	}

	private static void addToUserFirestore (String email, String displayName, Uri profilePictureImageUri) {
		HashMap<String,Object> user = new HashMap<>();
		user.put(Constants.UserConstants.KEY_NAME, displayName);
		user.put(Constants.UserConstants.KEY_EMAIL, email);
		user.put(Constants.UserConstants.KEY_IMAGE , profilePictureImageUri);

		FirebaseFirestore.getInstance().collection(Constants.UserConstants.KEY_COLLECTION_USER)
			.add(user)
			.addOnSuccessListener(documentReference -> {
				Log.d("FB_TAG","Added user to firebase");
			})
			.addOnFailureListener(e -> {
				Log.e("FB_TAG",e.getMessage());
			});
	}


}
