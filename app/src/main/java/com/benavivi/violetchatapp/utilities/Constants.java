package com.benavivi.violetchatapp.utilities;

public class Constants {
	public static class UserConstants{
		public static final int MIN_PASSWORD_LENGTH=6;
		public static final int MAX_PASSWORD_LENGTH=25;
		public static final int MAX_DISPLAY_NAME_LENGTH=25;
		public static final int MIN_DISPLAY_NAME_LENGTH=2;

	}
	public static class FirebaseConstants{
		//public static final String REALTIME_DATABASE_LINK = "https://voilet-chatapp-default-rtdb.europe-west1.firebasedatabase.app";
		public static final String COLLECTION_USER ="Users";
		public static final String KEY_USER_DISPLAY_NAME ="DisplayName";
		public static final String KEY_USER_EMAIL_ADDRESS ="EmailAddress";
		public static final String KEY_USER_PROFILE_IMAGE ="ProfileImage";
		public static final String KEY_USER_CONTACTS_LIST="ContactsList";
		public static final String KEY_USER_GROUPS_LIST="GroupsList";
		public static final String COLLECTION_GROUP_DETAILS ="GroupsDetails";
		public static final String KEY_GROUP_DETAILS_NAME ="GroupName";
		public static final String KEY_GROUP_DETAILS_ICON ="GroupIcon";
		public static final String KEY_GROUP_DETAILS_MEMBERS_LIST="MembersList";
		public static final String KEY_GROUP_DETAILS_LAST_MESSAGE="LastMessage";
		public static final String KEY_GROUP_DETAILS_CREATION_DATE="CreationDate";
		public static final String KEY_GROUP_DETAIL_ADMINS_LIST = "AdminsList";
		public static final String KEY_GROUP_DETAILS_IS_PRIVATE_MESSAGES="IsPrivateMessages";


	}
	public static class ApplicationConstants{

		public static final String KEY_PREFERENCE_NAME="VioletChatAppPreference";
	}

}
