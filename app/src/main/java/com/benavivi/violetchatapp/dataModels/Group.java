package com.benavivi.violetchatapp.dataModels;

import static com.benavivi.violetchatapp.utilities.Constants.FirebaseConstants.FIRST_CHAT_MESSAGE;
import static com.benavivi.violetchatapp.utilities.Constants.FirebaseConstants.KEY_GROUP_DETAILS_CREATION_DATE;
import static com.benavivi.violetchatapp.utilities.Constants.FirebaseConstants.KEY_GROUP_DETAILS_ICON;
import static com.benavivi.violetchatapp.utilities.Constants.FirebaseConstants.KEY_GROUP_DETAILS_ID;
import static com.benavivi.violetchatapp.utilities.Constants.FirebaseConstants.KEY_GROUP_DETAILS_IS_PRIVATE_MESSAGES;
import static com.benavivi.violetchatapp.utilities.Constants.FirebaseConstants.KEY_GROUP_DETAILS_LAST_MESSAGE;
import static com.benavivi.violetchatapp.utilities.Constants.FirebaseConstants.KEY_GROUP_DETAILS_MEMBERS_LIST;
import static com.benavivi.violetchatapp.utilities.Constants.FirebaseConstants.KEY_GROUP_DETAILS_NAME;
import static com.benavivi.violetchatapp.utilities.Constants.FirebaseConstants.KEY_GROUP_DETAIL_ADMIN_ID;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.PropertyName;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class Group {
private String adminID, chatID, name, imageURL;
private Message lastMessage;
private ArrayList<String> membersList;
private Timestamp creation_date;

private boolean isPrivateMessages;

public Group( ) {
	//Empty constructor for Firebase. (REQUIRED)
}

public Group( Map<String, Object> groupModelData ) {
	this.adminID = groupModelData.get(KEY_GROUP_DETAIL_ADMIN_ID).toString( );
	this.chatID = groupModelData.get(KEY_GROUP_DETAILS_ID).toString( );
	this.name = groupModelData.get(KEY_GROUP_DETAILS_NAME).toString( );
	this.imageURL = groupModelData.get(KEY_GROUP_DETAILS_ICON).toString( );
	this.creation_date = (Timestamp) groupModelData.get(KEY_GROUP_DETAILS_CREATION_DATE);
	this.isPrivateMessages = Boolean.parseBoolean(groupModelData.get(KEY_GROUP_DETAILS_IS_PRIVATE_MESSAGES).toString( ));

	lastMessage = (Message) groupModelData.get(KEY_GROUP_DETAILS_LAST_MESSAGE);


}

public Group( String adminID, String chatID, String name, Message lastMessage, String imageURL, Timestamp creationDate, boolean isPrivateMessages ) {
	this.adminID = adminID;
	this.chatID = chatID;
	this.name = name;
	this.lastMessage = lastMessage;
	this.imageURL = imageURL;
	this.creation_date = creationDate;
	this.isPrivateMessages = isPrivateMessages;
	//this.membersList = new ArrayList<String>();
	//membersList.add(adminID);
}

public Group( String adminID, String chatID, String name, String imageURL, Timestamp creation_date, boolean is_private_messages, ArrayList<String> membersList ) {
	this.adminID = adminID;
	this.chatID = chatID;
	this.name = name;

	this.imageURL = imageURL;
	this.creation_date = creation_date;
	this.isPrivateMessages = is_private_messages;
	this.membersList = membersList;
	this.lastMessage = FIRST_CHAT_MESSAGE;
}

public void setMessage( Message lastMessage ) {
	this.lastMessage = lastMessage;
}


@PropertyName ( KEY_GROUP_DETAILS_CREATION_DATE )
public Date getCreationDate( ) {
	return creation_date.toDate( );
}

/*
@PropertyName(KEY_GROUP_DETAILS_CREATION_DATE)
*/
public void setCreation_date( Timestamp creation_date ) {
	this.creation_date = creation_date;
}

@PropertyName ( KEY_GROUP_DETAILS_IS_PRIVATE_MESSAGES )
public boolean getIsPrivateMessages( ) {
	return isPrivateMessages;
}

@PropertyName ( KEY_GROUP_DETAILS_IS_PRIVATE_MESSAGES )
public void setIsPrivateMessages( boolean isPrivateMessages ) {
	this.isPrivateMessages = isPrivateMessages;
}

@PropertyName ( KEY_GROUP_DETAILS_ICON )
public String getImageURL( ) {
	return imageURL;
}

@PropertyName ( KEY_GROUP_DETAILS_ICON )
public void setImageURL( String imageURL ) {
	this.imageURL = imageURL;
}

@PropertyName ( KEY_GROUP_DETAIL_ADMIN_ID )
public String getAdminID( ) {
	return adminID;
}

@PropertyName ( KEY_GROUP_DETAIL_ADMIN_ID )
public void setAdminID( String adminID ) {
	this.adminID = adminID;
}

@PropertyName ( KEY_GROUP_DETAILS_ID )
public String getChatID( ) {
	return chatID;
}

@PropertyName ( KEY_GROUP_DETAILS_ID )
public void setChatID( String chatID ) {
	this.chatID = chatID;
}

@PropertyName ( KEY_GROUP_DETAILS_NAME )
public String getName( ) {
	return name;
}

@PropertyName ( KEY_GROUP_DETAILS_NAME )
public void setName( String chatName ) {
	this.name = chatName;
}

@PropertyName ( KEY_GROUP_DETAILS_LAST_MESSAGE )
public Message getLastMessage( ) {
	return lastMessage;
}


//This is strictly for the firebase auto constructor.
//Collection returns Map of string object
@PropertyName ( KEY_GROUP_DETAILS_LAST_MESSAGE )
public void setLastMessage( Map<String, Object> message ) {
	this.lastMessage = new Message(message);
}

/*public void setLastMessage( Message lastMessage ) {
	this.lastMessage = lastMessage;
}*/

@PropertyName ( KEY_GROUP_DETAILS_MEMBERS_LIST )
public ArrayList<String> getMembersList( ) {
	return membersList;
}

@PropertyName ( KEY_GROUP_DETAILS_MEMBERS_LIST )
public void setMembersList( ArrayList<String> membersList ) {
	this.membersList = membersList;
}

public long recieveTimestampNumber( ) {
	//return creation_date.getSeconds();
	return 1;
}

}
