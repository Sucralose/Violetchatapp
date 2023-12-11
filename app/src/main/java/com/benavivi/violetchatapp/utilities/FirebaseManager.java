package com.benavivi.violetchatapp.utilities;

import static com.benavivi.violetchatapp.utilities.Constants.FirebaseConstants.*;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.benavivi.violetchatapp.dataModels.Group;
import com.benavivi.violetchatapp.dataModels.Message;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class FirebaseManager {
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
	public static String getCurrentUserDisplayName(){
		return getCurrentUser().getDisplayName();
	}
	public static Task<DocumentSnapshot> getCurrentUserData (){
		return	FirebaseFirestore.getInstance().collection(COLLECTION_USER).document(getCurrentUserUid()).get();

	}

	public static Task<AuthResult> signUp(String email, String password, String displayName, Uri profilePictureImageUri){
		return FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
			.addOnSuccessListener(authResult -> {
				addToUserFirestore(email,displayName,profilePictureImageUri);
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
		user.put(KEY_USER_PROFILE_IMAGE , "");
		user.put(KEY_USER_CONTACTS_LIST, Arrays.asList(""));
		user.put(KEY_USER_GROUPS_LIST,Arrays.asList(""));

		FirebaseFirestore.getInstance().collection(COLLECTION_USER).document(getCurrentUserUid())
			.set(user)
			.addOnSuccessListener(documentReference -> {
				Log.d("FB_TAG","Added user to firebase");
				uploadUserProfileImage(profilePictureImageUri);
			})
			.addOnFailureListener(e -> {
				Log.e("FB_TAG",e.getMessage());
			});
	}

	public static void createNewGroup(String ownerUID, String groupName, String groupPhoto){
		Group group = new Group(ownerUID,"-1",groupName,new Message("","","",100,"")
			,groupPhoto, new Date().getTime(),false);
		createNewGroup(group);
	}
	public static void createNewGroup(String ownerUID, String groupName, String groupPhoto,ArrayList<String> membersList){
		Group group = new Group(ownerUID,"-1",groupName,new Message("","","",100,"")
			,groupPhoto, new Date().getTime(),false);
		createNewGroup(group);
	}
	private static void createNewGroup(Group group){

		Map<String,Object> groupDetail = new HashMap<>();
		Map<String,Object> lastmessageDetail = new HashMap<>();
		groupDetail.put(KEY_GROUP_DETAILS_NAME,group.getChatName());
		groupDetail.put(KEY_GROUP_DETAILS_ICON,group.getImageURL());
		groupDetail.put(KEY_GROUP_DETAILS_IS_PRIVATE_MESSAGES,group.isPrivateMessages());
		groupDetail.put(KEY_GROUP_DETAILS_MEMBERS_LIST,group.getMembersList());
		groupDetail.put(KEY_GROUP_DETAILS_CREATION_DATE,group.getCreationDate());
		groupDetail.put(KEY_GROUP_DETAIL_ADMIN_ID,group.getAdminID());

		Message lastMessage = group.getLastMessage();
		lastmessageDetail.put(KEY_MESSAGE_SENDER_NAME,lastMessage.getSenderName());
		lastmessageDetail.put(KEY_MESSAGE_SENDER_ID,lastMessage.getSenderID());
		lastmessageDetail.put(KEY_MESSAGE_TEXT,lastMessage.getMessageText());
		lastmessageDetail.put(KEY_MESSAGE_DATE,lastMessage.getDate());

		groupDetail.put(KEY_GROUP_DETAILS_LAST_MESSAGE,lastmessageDetail);

		FirebaseFirestore.getInstance().collection(COLLECTION_GROUP_DETAILS)
			.add(groupDetail);

	}


	public static Task<QuerySnapshot>  getUserGroupsDetails(){
		Task <QuerySnapshot> task = FirebaseFirestore.getInstance().collection(COLLECTION_GROUP_DETAILS).whereArrayContains(KEY_GROUP_DETAILS_MEMBERS_LIST,getCurrentUserUid())
					        .orderBy(KEY_GROUP_DETAILS_LAST_MESSAGE+"."+KEY_MESSAGE_DATE,Query.Direction.DESCENDING)
			.get();

		return task;

	}

	public static void sendMessage(String message,String groupID){
		//Convert to message format
		HashMap<String,Object> messageMap = new HashMap<>();
		messageMap.put(KEY_MESSAGE_SENDER_ID,getCurrentUserUid());
		messageMap.put(KEY_MESSAGE_SENDER_NAME,getCurrentUserDisplayName());
		messageMap.put(KEY_MESSAGE_DATE,new java.util.Date().getTime());
		messageMap.put(KEY_MESSAGE_TEXT,message);

		FirebaseFirestore.getInstance()
			.collection(COLLECTION_GROUP_MESSAGES).document(groupID).collection(SUB_COLLECTION_MESSAGES)
			.add(messageMap);

		//Update Last message
		FirebaseFirestore.getInstance()
			.collection(COLLECTION_GROUP_DETAILS).document(groupID).update(KEY_GROUP_DETAILS_LAST_MESSAGE,messageMap);

	}

	public static void uploadUserProfileImage (Uri profilePictureImageUri) {

		StorageReference storageReference = FirebaseStorage.getInstance().getReference(KEY_USER_PROFILE_IMAGE_STORAGE_REFERENCE + "/" + getCurrentUserUid() + ".jpg");
		storageReference.putFile(profilePictureImageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
			@Override
			public void onComplete (@NonNull Task<UploadTask.TaskSnapshot> task) {
				if(task.isSuccessful()){
					task.getResult().getStorage().getDownloadUrl()
						.addOnCompleteListener(uriTask -> {
							if(uriTask.isSuccessful()){
								saveImageInUserProfile(uriTask.getResult().toString());
							}
						});
				}
			}
		});
	}

	private static void saveImageInUserProfile (String downloadURL) {
		HashMap<String,Object> userMap = new HashMap<>();
		userMap.put(KEY_USER_PROFILE_IMAGE,downloadURL);
		FirebaseFirestore.getInstance().collection(COLLECTION_USER).document(getCurrentUserUid())
			.update(userMap);

	}



}
