package com.benavivi.violetchatapp.utilities;

import android.net.Uri;
import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import static com.benavivi.violetchatapp.utilities.Constants.FirebaseConstants.*;

public class FirebaseManager {
	public static void updateUserProfile (String displayName, Uri profilePictureImageUri) {
		FirebaseUser user = getCurrentUser();
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
		FirebaseUser user = getCurrentUser();
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
		FirebaseUser user = getCurrentUser();
		if(user == null) return;
		UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
							  .setPhotoUri(profilePictureImageUri)
							  .build();
		user.updateProfile(profileUpdates)
			.addOnCompleteListener(task -> {
				if (task.isSuccessful()) {
					Log.d("UPDATEUserProfile", "User profile updated.");
				}
			});
	}
	public static void signOut(){
		FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
		firebaseAuth.signOut();
	}

	public static boolean isSignedIn(){
		return getCurrentUser()!=null;
	}

	public static String getUserDisplayName(){
		return getCurrentUser().getDisplayName();
	}

	public static String getUserEmail(){
		return getCurrentUser().getEmail();
	}

	private static FirebaseUser getCurrentUser(){
		return FirebaseAuth.getInstance().getCurrentUser();
	}

	public static String getCurrentUserUid(){
		return getCurrentUser().getUid();
	}
	/*public static Uri getUserUriImage (){
		return getCurrentUser().getPhotoUrl();
	}*/

	public static Task<AuthResult> signUp(String email, String password, String displayName, Uri profilePictureImageUri){
		return FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
			.addOnSuccessListener(authResult -> {
				//FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
				addToUserFirestore(email,displayName,profilePictureImageUri);
				updateUserProfile(displayName,profilePictureImageUri);
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
		user.put(KEY_USER_DISPLAY_NAME, displayName);
		user.put(KEY_USER_EMAIL_ADDRESS, email);
		user.put(KEY_USER_PROFILE_IMAGE , profilePictureImageUri);
		user.put(KEY_USER_CONTACTS_LIST, Arrays.asList(""));
		user.put(KEY_USER_GROUPS_LIST,Arrays.asList(""));

		FirebaseFirestore.getInstance().collection(COLLECTION_USER)
			.add(user)
			.addOnSuccessListener(documentReference -> {
				Log.d("FB_TAG","Added user to firebase");
			})
			.addOnFailureListener(e -> {
				Log.e("FB_TAG",e.getMessage());
			});
	}

	private static void createNewGroup(String ownerUID,String groupName, String groupPhoto){
		createNewGroupDetails(ownerUID,groupName, groupPhoto);

	}

	private static void createNewGroupDetails (String ownerUID, String groupName, String groupPhoto) {
		HashMap<String,Object> groupDetail = new HashMap<>();
		groupDetail.put(KEY_GROUP_DETAILS_NAME,groupName);
		groupDetail.put(KEY_GROUP_DETAILS_ICON,groupPhoto);
		groupDetail.put(KEY_GROUP_DETAILS_IS_PRIVATE_MESSAGES,false);
		groupDetail.put(KEY_GROUP_DETAILS_MEMBERS_LIST,Arrays.asList(ownerUID));
		groupDetail.put(KEY_GROUP_DETAILS_CREATION_DATE,new java.util.Date().getTime());
		groupDetail.put(KEY_GROUP_DETAIL_ADMINS_LIST,Arrays.asList(ownerUID));
		groupDetail.put(KEY_GROUP_DETAILS_LAST_MESSAGE,Arrays.asList(""));

		FirebaseFirestore.getInstance().collection(COLLECTION_GROUP_DETAILS)
			.add(groupDetail);

	}



	/*private static void addUserToRealtimeFirebaseDatabase(String email,String displayName,Uri imageUri){
		HashMap<String,String> userMap = new HashMap<>();
		userMap.put(KEY_USER_EMAIL_ADDRESS,email);
		userMap.put(KEY_USER_PROFILE_IMAGE,imageUri.toString());
		userMap.put(KEY_USER_DISPLAY_NAME,displayName);
		FirebaseDatabase.getInstance(REALTIME_DATABASE_LINK).getReference()
			.child(COLLECTION_USER)
				.child(getCurrentUserUid())
					.setValue(userMap);
	}

	private static void updateRealtimeDatabaseForUser(){
		HashMap<String,String> userMap = new HashMap<>();
		FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
		userMap.put(KEY_USER_EMAIL_ADDRESS, currentFirebaseUser.getEmail());
		userMap.put(KEY_USER_PROFILE_IMAGE,currentFirebaseUser.getPhotoUrl().toString());
		userMap.put(KEY_USER_DISPLAY_NAME, currentFirebaseUser.getDisplayName());

		FirebaseDatabase.getInstance(REALTIME_DATABASE_LINK).getReference()
			.child(COLLECTION_USER)
			.child(getCurrentUserUid())
			.setValue(userMap);
	}*/



}
