package com.benavivi.violetchatapp.utilities;

public class Constants {
	public static class UserConstants{
		public static final int MIN_PASSWORD_LENGTH=6;
		public static final int MAX_DISPLAY_NAME_LENGTH=25;

	}
	public static class FirebaseConstants{
		public static final String DATABASE_LINK = "https://voilet-chatapp-default-rtdb.europe-west1.firebasedatabase.app";
		public static final String KEY_COLLECTION_USER="Users";
		public static final String KEY_NAME="DisplayName";
		public static final String KEY_EMAIL="EmailAddress";
		public static final String KEY_IMAGE="ProfileImage";
	}

	public static class ApplicationConstants{
		public static final String KEY_PREFERENCE_NAME="VioletChatAppPreference";
	}

}
