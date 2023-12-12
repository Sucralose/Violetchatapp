package com.benavivi.violetchatapp.utilities;

import static com.benavivi.violetchatapp.utilities.Constants.FirebaseConstants.KEY_GROUP_DETAILS_CREATION_DATE;
import static com.benavivi.violetchatapp.utilities.Constants.FirebaseConstants.KEY_GROUP_DETAILS_ICON;
import static com.benavivi.violetchatapp.utilities.Constants.FirebaseConstants.KEY_GROUP_DETAILS_ID;
import static com.benavivi.violetchatapp.utilities.Constants.FirebaseConstants.KEY_GROUP_DETAILS_IS_PRIVATE_MESSAGES;
import static com.benavivi.violetchatapp.utilities.Constants.FirebaseConstants.KEY_GROUP_DETAILS_MEMBERS_LIST;
import static com.benavivi.violetchatapp.utilities.Constants.FirebaseConstants.KEY_GROUP_DETAILS_NAME;
import static com.benavivi.violetchatapp.utilities.Constants.FirebaseConstants.KEY_GROUP_DETAIL_ADMIN_ID;

import android.content.Context;
import android.content.Intent;

import com.benavivi.violetchatapp.dataModels.Group;


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
	intent.putExtra(KEY_GROUP_DETAILS_NAME, group.getName());
	intent.putExtra(KEY_GROUP_DETAILS_ID, group.getChatID());
	intent.putExtra(KEY_GROUP_DETAILS_ICON, group.getImageURL());
	//intent.putExtra(KEY_GROUP_DETAILS_MEMBERS_LIST, group.getMembersList());
	intent.putExtra(KEY_GROUP_DETAIL_ADMIN_ID, group.getAdminID());
	intent.putExtra(KEY_GROUP_DETAILS_IS_PRIVATE_MESSAGES, group.getIs_private_messages());
	intent.putExtra(KEY_GROUP_DETAILS_CREATION_DATE, group.getCreation_date());
	return intent;
}

public static Group IntentToGroup ( Intent intent ) {

	return new Group(
		intent.getStringExtra(KEY_GROUP_DETAIL_ADMIN_ID),
		intent.getStringExtra(KEY_GROUP_DETAILS_ID),
		intent.getStringExtra(KEY_GROUP_DETAILS_NAME),
		intent.getStringExtra(KEY_GROUP_DETAILS_ICON),
		intent.getLongExtra(KEY_GROUP_DETAILS_CREATION_DATE, -1),
		intent.getBooleanExtra(KEY_GROUP_DETAILS_IS_PRIVATE_MESSAGES, false),
		intent.getStringArrayListExtra(KEY_GROUP_DETAILS_MEMBERS_LIST)
	);
}
}
