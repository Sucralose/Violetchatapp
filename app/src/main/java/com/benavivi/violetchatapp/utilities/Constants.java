package com.benavivi.violetchatapp.utilities;

import com.benavivi.violetchatapp.dataModels.Message;
import com.google.firebase.Timestamp;

public class Constants {
public static class UserConstants {
	public static final int MIN_PASSWORD_LENGTH = 6;
	public static final int MAX_PASSWORD_LENGTH = 25;
	public static final int MAX_DISPLAY_NAME_LENGTH = 25;
	public static final int MIN_DISPLAY_NAME_LENGTH = 2;

	public static final int MAX_GROUP_NAME_LENGTH = 25;
	public static final int MIN_GROUP_NAME_LENGTH = 2;

}

public static class FirebaseConstants {

	//--- User Constants ---
	public static final String COLLECTION_USER = "Users";
	public static final String KEY_USER_DISPLAY_NAME = "DisplayName";
	public static final String KEY_USER_EMAIL_ADDRESS = "EmailAddress";
	public static final String KEY_USER_PROFILE_IMAGE = "ProfileImage";
	public static final String KEY_USER_FCM_TOKEN = "FCM_Token";

	//--- Group Details Constants ---
	public static final String COLLECTION_GROUP_DETAILS = "GroupsDetails";
	public static final String KEY_GROUP_DETAILS_NAME = "GroupName";
	public static final String KEY_GROUP_DETAILS_ICON = "GroupIcon";
	public static final String KEY_GROUP_DETAILS_MEMBERS_LIST = "MembersList";
	public static final String KEY_GROUP_DETAILS_LAST_MESSAGE = "LastMessage";
	public static final String KEY_GROUP_DETAILS_CREATION_DATE = "CreationDate";
	public static final String KEY_GROUP_DETAIL_ADMIN_ID = "AdminID";
	public static final String KEY_GROUP_DETAILS_ID = "GroupID";


	public static final String COLLECTION_GROUP_MESSAGES = "GroupMessages";
	public static final String SUB_COLLECTION_MESSAGES = "MessageCollection";

	//--- Messages Constants ---
	public static final String KEY_MESSAGE_SENDER_ID = "SenderID";
	public static final String KEY_MESSAGE_SENDER_NAME = "SenderName";
	public static final String KEY_MESSAGE_DATE = "Date";
	public static final String KEY_MESSAGE_TEXT = "Text";
	public static final String KEY_MESSAGE_SENDER_IMAGE_URL = "SenderImageURL";

	//--- Firebase Storage ---
	public static final String KEY_USER_PROFILE_IMAGE_STORAGE_REFERENCE = "UserProfileImages";
	public static final String KEY_GROUP_PROFILE_IMAGE_STORAGE_REFERENCE = "GroupProfileImages";
	public static final String KEY_GROUP_ATTACHMENT_STORAGE_REFERENCE = "GroupAttachments";


	//--- Firebase Notifications ---
	public static final String NOTIFICATION_EXTRA_SENTFROMID = "sentFromID";
	public static final String NOTIFICATION_PUSH_URL = "https://fcm.googleapis.com/fcm/send";
	public static final String NOTIFICATION_API_KEY = "AAAAC4X48r8:APA91bEdbOMkUYcbwiWP7Y6vnLSTKU4dtgF625A0xsfvjfZNZmuxbDIlR-HcoLYJ0kmE_EWeF5WzWV3e6SuU6s_9qjWXtMPS-6WktO7k6f9yhfb6PEjQdeAL44d_q_MRBv7daENVOhaq";

	public static final Message FIRST_CHAT_MESSAGE = new Message("Created this chat", "-1", "Admin", "invalidImage", Timestamp.now( ));
}

public static class ApplicationConstants {

	//public static final String KEY_PREFERENCE_NAME = "VioletChatAppPreference";
	public static final int SPLASH_DELAY_MILLISECONDS = 500;
	public static final int CREATE_GROUP_DELAY_MILLISECONDS = 500;
}

}
