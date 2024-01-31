package com.benavivi.violetchatapp.utilities;

import static com.benavivi.violetchatapp.utilities.Constants.FirebaseConstants.COLLECTION_GROUP_DETAILS;
import static com.benavivi.violetchatapp.utilities.Constants.FirebaseConstants.KEY_GROUP_DETAILS_CREATION_DATE;
import static com.benavivi.violetchatapp.utilities.Constants.FirebaseConstants.KEY_GROUP_DETAILS_ICON;
import static com.benavivi.violetchatapp.utilities.Constants.FirebaseConstants.KEY_GROUP_DETAILS_ID;
import static com.benavivi.violetchatapp.utilities.Constants.FirebaseConstants.KEY_GROUP_DETAILS_IS_PRIVATE_MESSAGES;
import static com.benavivi.violetchatapp.utilities.Constants.FirebaseConstants.KEY_GROUP_DETAILS_MEMBERS_LIST;
import static com.benavivi.violetchatapp.utilities.Constants.FirebaseConstants.KEY_GROUP_DETAILS_NAME;
import static com.benavivi.violetchatapp.utilities.Constants.FirebaseConstants.KEY_GROUP_DETAIL_ADMIN_ID;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;

import com.benavivi.violetchatapp.dataModels.Group;
import com.google.firebase.Timestamp;


/**
 * Intent Factory
 *
 * @version 1.0
 * <p>
 * This class is used to convert intents between the different datamodels.
 */

public class IntentFactory {

public static Intent GroupToIntent ( Context context, Class<?> destination, Group group ) {
	Intent intent = new Intent(context, destination);
	intent.putExtra(COLLECTION_GROUP_DETAILS, (Parcelable) group);
	/*intent.putExtra(KEY_GROUP_DETAILS_NAME, group.getName());
	intent.putExtra(KEY_GROUP_DETAILS_ID, group.getChatID());
	intent.putExtra(KEY_GROUP_DETAILS_ICON, group.getImageURL());
	intent.putExtra(KEY_GROUP_DETAILS_MEMBERS_LIST, group.getMembersList());
	intent.putExtra(KEY_GROUP_DETAIL_ADMIN_ID, group.getAdminID());
	intent.putExtra(KEY_GROUP_DETAILS_IS_PRIVATE_MESSAGES, group.getIsPrivateMessages());
	intent.putExtra(KEY_GROUP_DETAILS_CREATION_DATE, group.getCreationDate());*/
	return intent;
}

//TODO: FIX INTENT FACTORY
public static Group IntentToGroup ( Intent intent ) {

	return intent.getParcelableExtra(COLLECTION_GROUP_DETAILS);
	/*return new Group(
		intent.getStringExtra(KEY_GROUP_DETAIL_ADMIN_ID),
		intent.getStringExtra(KEY_GROUP_DETAILS_ID),
		intent.getStringExtra(KEY_GROUP_DETAILS_NAME),
		intent.getStringExtra(KEY_GROUP_DETAILS_ICON),
		intent.getLongExtra(KEY_GROUP_DETAILS_CREATION_DATE,),
		intent.getBooleanExtra(KEY_GROUP_DETAILS_IS_PRIVATE_MESSAGES, false),
		intent.getStringArrayListExtra(KEY_GROUP_DETAILS_MEMBERS_LIST)
	);*/
}
}
