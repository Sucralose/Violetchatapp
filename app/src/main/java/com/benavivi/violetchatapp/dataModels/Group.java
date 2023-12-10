package com.benavivi.violetchatapp.dataModels;

import static com.benavivi.violetchatapp.utilities.Constants.FirebaseConstants.KEY_GROUP_DETAILS_CREATION_DATE;
import static com.benavivi.violetchatapp.utilities.Constants.FirebaseConstants.KEY_GROUP_DETAILS_ICON;
import static com.benavivi.violetchatapp.utilities.Constants.FirebaseConstants.KEY_GROUP_DETAILS_IS_PRIVATE_MESSAGES;
import static com.benavivi.violetchatapp.utilities.Constants.FirebaseConstants.KEY_GROUP_DETAILS_LAST_MESSAGE;
import static com.benavivi.violetchatapp.utilities.Constants.FirebaseConstants.KEY_GROUP_DETAILS_NAME;
import static com.benavivi.violetchatapp.utilities.Constants.FirebaseConstants.KEY_GROUP_DETAIL_ADMIN_ID;

import java.util.ArrayList;
import java.util.Map;

public class Group {
	private String adminID, chatID, chatName, imageURL;
	private Message lastMessage;
	private ArrayList<String> membersList;
	private long creationDate;

	private boolean isPrivateMessages;
	public Group (String adminID, String chatID, String chatName, Message lastMessage, String imageURL, long creationDate, boolean isPrivateMessages) {
		this.adminID = adminID;
		this.chatID = chatID;
		this.chatName = chatName;
		this.lastMessage = lastMessage;
		this.imageURL = imageURL;
		this.creationDate = creationDate;
		this.isPrivateMessages = isPrivateMessages;
		this.membersList = new ArrayList<String>();
		membersList.add(adminID);
	}
	public Group (String adminID, String chatID, String chatName, String imageURL, long creationDate, boolean isPrivateMessages,ArrayList<String> membersList) {
		this.adminID = adminID;
		this.chatID = chatID;
		this.chatName = chatName;
		this.lastMessage = null;
		this.imageURL = imageURL;
		this.creationDate = creationDate;
		this.isPrivateMessages = isPrivateMessages;
		this.membersList = membersList;
	}


	public long getCreationDate () {
		return creationDate;
	}

	public void setCreationDate (long creationDate) {
		this.creationDate = creationDate;
	}

	public boolean isPrivateMessages () {
		return isPrivateMessages;
	}

	public void setPrivateMessages (boolean privateMessages) {
		isPrivateMessages = privateMessages;
	}

	public Group(Map<String,Object> groupMap, String groupID){

		adminID = (String) groupMap.get(KEY_GROUP_DETAIL_ADMIN_ID);
		chatID = groupID;
		chatName = (String) groupMap.get(KEY_GROUP_DETAILS_NAME);
		imageURL = (String) groupMap.get(KEY_GROUP_DETAILS_ICON);
		creationDate = (long) groupMap.get(KEY_GROUP_DETAILS_CREATION_DATE);
		isPrivateMessages = (boolean) groupMap.get(KEY_GROUP_DETAILS_IS_PRIVATE_MESSAGES);
		lastMessage = new Message((Map<String,Object>)groupMap.get(KEY_GROUP_DETAILS_LAST_MESSAGE),groupID);

	}

	public String getImageURL () {
		return imageURL;
	}

	public void setImageURL (String imageURL) {
		this.imageURL = imageURL;
	}

	public String getAdminID () {
		return adminID;
	}

	public void setAdminID (String adminID) {
		this.adminID = adminID;
	}

	public String getChatID () {
		return chatID;
	}

	public void setChatID (String chatID) {
		this.chatID = chatID;
	}

	public String getChatName () {
		return chatName;
	}

	public void setChatName (String chatName) {
		this.chatName = chatName;
	}

	public Message getLastMessage () {
		return lastMessage;
	}

	public void setLastMessage (Message lastMessage) {
		this.lastMessage = lastMessage;
	}


	public ArrayList<String> getMembersList () {
		return membersList;
	}
}
