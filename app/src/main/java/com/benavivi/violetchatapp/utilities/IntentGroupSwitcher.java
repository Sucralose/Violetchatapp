package com.benavivi.violetchatapp.utilities;

import static com.benavivi.violetchatapp.utilities.Constants.FirebaseConstants.*;

import android.content.Context;
import android.content.Intent;
import com.benavivi.violetchatapp.dataModels.Group;
import com.benavivi.violetchatapp.dataModels.Message;

public class IntentGroupSwitcher {

	public static Intent switchToIntent(Context context, Class<?> destination, Group group) {
		Intent intent = new Intent(context, destination);
		intent.putExtra(KEY_GROUP_DETAILS_NAME, group.getChatName());
		intent.putExtra(KEY_GROUP_DETAILS_ID, group.getChatID());
		intent.putExtra(KEY_GROUP_DETAILS_ICON, group.getImageURL());
		intent.putExtra(KEY_GROUP_DETAILS_MEMBERS_LIST, group.getMembersList());
		intent.putExtra(KEY_GROUP_DETAIL_ADMIN_ID, group.getAdminID());
		intent.putExtra(KEY_GROUP_DETAILS_IS_PRIVATE_MESSAGES, group.isPrivateMessages());
		intent.putExtra(KEY_GROUP_DETAILS_CREATION_DATE, group.getCreationDate());
		return intent;
	}

	public static Group switchToGroup(Intent intent) {

		Group group = new Group(
			intent.getStringExtra(KEY_GROUP_DETAIL_ADMIN_ID),
			intent.getStringExtra(KEY_GROUP_DETAILS_ID),
			intent.getStringExtra(KEY_GROUP_DETAILS_NAME),
			intent.getStringExtra(KEY_GROUP_DETAILS_ICON),
			intent.getLongExtra(KEY_GROUP_DETAILS_CREATION_DATE,-1),
			intent.getBooleanExtra(KEY_GROUP_DETAILS_IS_PRIVATE_MESSAGES,false),
			intent.getStringArrayListExtra(KEY_GROUP_DETAILS_MEMBERS_LIST)
		);

		return group;
	}
}
