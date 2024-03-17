package com.benavivi.violetchatapp.utilities;

import static com.benavivi.violetchatapp.utilities.Constants.FirebaseConstants.COLLECTION_GROUP_DETAILS;
import static com.benavivi.violetchatapp.utilities.Constants.FirebaseConstants.KEY_GROUP_DETAILS_CREATION_DATE;
import static com.benavivi.violetchatapp.utilities.Constants.FirebaseConstants.KEY_GROUP_DETAILS_ICON;
import static com.benavivi.violetchatapp.utilities.Constants.FirebaseConstants.KEY_GROUP_DETAILS_ID;
import static com.benavivi.violetchatapp.utilities.Constants.FirebaseConstants.KEY_GROUP_DETAILS_IS_PRIVATE_MESSAGES;
import static com.benavivi.violetchatapp.utilities.Constants.FirebaseConstants.KEY_GROUP_DETAILS_LAST_MESSAGE;
import static com.benavivi.violetchatapp.utilities.Constants.FirebaseConstants.KEY_GROUP_DETAILS_MEMBERS_LIST;
import static com.benavivi.violetchatapp.utilities.Constants.FirebaseConstants.KEY_GROUP_DETAILS_NAME;
import static com.benavivi.violetchatapp.utilities.Constants.FirebaseConstants.KEY_GROUP_DETAIL_ADMIN_ID;

import android.content.Context;
import android.content.Intent;

import com.benavivi.violetchatapp.dataModels.Group;
import com.benavivi.violetchatapp.dataModels.Message;
import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.Map;


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

	String[] data = {
		group.getAdminID(),
		group.getChatID(),
		group.getName(),
		group.getImageURL( ),
		String.valueOf(group.recieveTimestampNumber()),
		String.valueOf(group.getIsPrivateMessages()),
	};

	String[] lastMessage = {
		group.getLastMessage().getMessageText(),
		group.getLastMessage().getSenderID(),
		group.getLastMessage().getSenderName(),
		group.getLastMessage().getSenderImageURL(),
		String.valueOf(group.getLastMessage().recieveTimestampNumber())
	};

	intent.putExtra(COLLECTION_GROUP_DETAILS,data);
	intent.putExtra(KEY_GROUP_DETAILS_LAST_MESSAGE,lastMessage);
	intent.putExtra(KEY_GROUP_DETAILS_MEMBERS_LIST, group.getMembersList());
	/*intent.putExtra(KEY_GROUP_DETAILS_NAME, group.getName());
	intent.putExtra(KEY_GROUP_DETAILS_ID, group.getChatID());
	intent.putExtra(KEY_GROUP_DETAILS_ICON, group.getImageURL());
	intent.putExtra(KEY_GROUP_DETAILS_MEMBERS_LIST, group.getMembersList());
	intent.putExtra(KEY_GROUP_DETAIL_ADMIN_ID, group.getAdminID());
	intent.putExtra(KEY_GROUP_DETAILS_IS_PRIVATE_MESSAGES, group.getIsPrivateMessages());
	intent.putExtra(KEY_GROUP_DETAILS_CREATION_DATE, group.getCreationDate());*/
	return intent;
}

public static Group IntentToGroup ( Intent intent ) {

	String[] data = intent.getStringArrayExtra(COLLECTION_GROUP_DETAILS);
	String[] lastMessage = intent.getStringArrayExtra(KEY_GROUP_DETAILS_LAST_MESSAGE);
	ArrayList<String> membersList = intent.getStringArrayListExtra(KEY_GROUP_DETAILS_MEMBERS_LIST);
	Group gp =  new Group(
		data[0],
		data[1],
		data[2],
		data[3],
		new Timestamp(Long.parseLong(data[4]),0),
		Boolean.parseBoolean(data[5]),
		membersList
	);
	gp.setMessage(
		new Message(
			lastMessage[0],
			lastMessage[1],
			lastMessage[2],
			lastMessage[3],
			new Timestamp(Long.parseLong(lastMessage[4]),0))
	);

		return gp;
}

public static Group IntentMapToGroup( Map<String,Object> groupMap ) {

	Group group = new Group(
		(String) groupMap.get(KEY_GROUP_DETAIL_ADMIN_ID),
		(String) groupMap.get(KEY_GROUP_DETAILS_ID),
		(String) groupMap.get(KEY_GROUP_DETAILS_NAME),
		(String) groupMap.get(KEY_GROUP_DETAILS_ICON),
		(Timestamp) groupMap.get(KEY_GROUP_DETAILS_CREATION_DATE),
		(Boolean) groupMap.get(KEY_GROUP_DETAILS_IS_PRIVATE_MESSAGES),
		(ArrayList<String>) groupMap.get(KEY_GROUP_DETAILS_MEMBERS_LIST)
	);

	group.setLastMessage((Map<String, Object>) groupMap.get(KEY_GROUP_DETAILS_LAST_MESSAGE));

	return group;
}
}
