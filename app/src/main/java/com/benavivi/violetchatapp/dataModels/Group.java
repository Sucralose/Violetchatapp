package com.benavivi.violetchatapp.dataModels;

import static com.benavivi.violetchatapp.utilities.Constants.FirebaseConstants.KEY_GROUP_DETAILS_CREATION_DATE;
import static com.benavivi.violetchatapp.utilities.Constants.FirebaseConstants.KEY_GROUP_DETAILS_ICON;
import static com.benavivi.violetchatapp.utilities.Constants.FirebaseConstants.KEY_GROUP_DETAILS_ID;
import static com.benavivi.violetchatapp.utilities.Constants.FirebaseConstants.KEY_GROUP_DETAILS_IS_PRIVATE_MESSAGES;
import static com.benavivi.violetchatapp.utilities.Constants.FirebaseConstants.KEY_GROUP_DETAILS_LAST_MESSAGE;
import static com.benavivi.violetchatapp.utilities.Constants.FirebaseConstants.KEY_GROUP_DETAILS_MEMBERS_LIST;
import static com.benavivi.violetchatapp.utilities.Constants.FirebaseConstants.KEY_GROUP_DETAILS_NAME;
import static com.benavivi.violetchatapp.utilities.Constants.FirebaseConstants.KEY_GROUP_DETAIL_ADMIN_ID;
import static com.benavivi.violetchatapp.utilities.Constants.FirebaseConstants.KEY_MESSAGE_DATE;
import static com.benavivi.violetchatapp.utilities.Constants.FirebaseConstants.KEY_MESSAGE_SENDER_ID;
import static com.benavivi.violetchatapp.utilities.Constants.FirebaseConstants.KEY_MESSAGE_SENDER_IMAGE_URL;
import static com.benavivi.violetchatapp.utilities.Constants.FirebaseConstants.KEY_MESSAGE_SENDER_NAME;
import static com.benavivi.violetchatapp.utilities.Constants.FirebaseConstants.KEY_MESSAGE_TEXT;

import com.google.firebase.firestore.PropertyName;

import java.util.ArrayList;
import java.util.Map;

public class Group {
private String adminID, chatID, name, imageURL;
private String lastMessageText, lastSenderName, lastSenderID, lastMessageSenderImageURL;
private long lastMessageDate;
private ArrayList<String> membersList;
private long creation_date;

private boolean is_private_messages;

public Group ( ) {
	//Empty constructor for Firebase. (REQUIRED)
}

public Group ( String adminID, String chatID, String name, Message lastMessage, String imageURL, long creation_date, boolean is_private_messages ) {
	this.adminID = adminID;
	this.chatID = chatID;
	this.name = name;

	this.lastMessageDate = lastMessage.getDate();
	this.lastMessageText = lastMessage.getMessageText();
	this.lastSenderID = lastMessage.getSenderID();
	this.lastSenderName = lastMessage.getSenderName();

	this.imageURL = imageURL;
	this.creation_date = creation_date;
	this.is_private_messages = is_private_messages;
	//this.membersList = new ArrayList<String>();
	//membersList.add(adminID);
}

public Group ( String adminID, String chatID, String name, String imageURL, long creation_date, boolean is_private_messages, ArrayList<String> membersList ) {
	this.adminID = adminID;
	this.chatID = chatID;
	this.name = name;

	this.lastMessageDate = 0;
	this.lastMessageText = "";
	this.lastSenderID = "";
	this.lastSenderName = "";


	this.imageURL = imageURL;
	this.creation_date = creation_date;
	this.is_private_messages = is_private_messages;
	this.membersList = membersList;
}


@PropertyName(KEY_GROUP_DETAILS_CREATION_DATE)
public long getCreation_date ( ) {
	return creation_date;
}

@PropertyName(KEY_GROUP_DETAILS_CREATION_DATE)
public void setCreation_date ( long creation_date ) {
	this.creation_date = creation_date;
}

@PropertyName(KEY_GROUP_DETAILS_IS_PRIVATE_MESSAGES)
public boolean getIs_private_messages ( ) {
	return is_private_messages;
}

@PropertyName(KEY_GROUP_DETAILS_IS_PRIVATE_MESSAGES)
public void setIs_private_messages ( boolean is_private_messages ) {
	this.is_private_messages = is_private_messages;
}

@PropertyName(KEY_GROUP_DETAILS_ICON)
public String getImageURL ( ) {
	return imageURL;
}

@PropertyName(KEY_GROUP_DETAILS_ICON)
public void setImageURL ( String imageURL ) {
	this.imageURL = imageURL;
}

@PropertyName(KEY_GROUP_DETAIL_ADMIN_ID)
public String getAdminID ( ) {
	return adminID;
}

@PropertyName(KEY_GROUP_DETAIL_ADMIN_ID)
public void setAdminID ( String adminID ) {
	this.adminID = adminID;
}

@PropertyName(KEY_GROUP_DETAILS_ID)
public String getChatID ( ) {
	return chatID;
}

@PropertyName(KEY_GROUP_DETAILS_ID)
public void setChatID ( String chatID ) {
	this.chatID = chatID;
}

@PropertyName(KEY_GROUP_DETAILS_NAME)
public String getName ( ) {
	return name;
}

@PropertyName(KEY_GROUP_DETAILS_NAME)
public void setName ( String chatName ) {
	this.name = chatName;
}

@PropertyName(KEY_GROUP_DETAILS_LAST_MESSAGE)
public Message getLastMessage ( ) {
	return new Message(lastMessageText, lastSenderID, lastSenderName, lastMessageSenderImageURL, lastMessageDate);
}

@PropertyName(KEY_GROUP_DETAILS_LAST_MESSAGE)
public void setLast_message ( Map<String,Object> message ) {
	this.lastMessageDate = Long.parseLong(message.get(KEY_MESSAGE_DATE).toString());
	this.lastMessageText = message.get(KEY_MESSAGE_TEXT).toString();
	this.lastSenderID = message.get(KEY_MESSAGE_SENDER_ID).toString();
	this.lastSenderName = message.get(KEY_MESSAGE_SENDER_NAME).toString();
	this.lastMessageSenderImageURL = message.get(KEY_MESSAGE_SENDER_IMAGE_URL).toString();
}

public void setLast_message ( Message lastMessage ) {
	this.lastMessageDate = lastMessage.getDate();
	this.lastMessageText = lastMessage.getMessageText();
	this.lastSenderID = lastMessage.getSenderID();
	this.lastSenderName = lastMessage.getSenderName();
	this.lastMessageSenderImageURL = lastMessage.getSenderImageURL();
}

@PropertyName(KEY_GROUP_DETAILS_MEMBERS_LIST)
public ArrayList<String> getMembersList ( ) {
	return membersList;
}

@PropertyName(KEY_GROUP_DETAILS_MEMBERS_LIST)
public void setMembersList ( ArrayList<String> membersList ) {
	this.membersList = membersList;
}


	/*public ArrayList<String> getMembersList () {
		return membersList;
	}*/
}
