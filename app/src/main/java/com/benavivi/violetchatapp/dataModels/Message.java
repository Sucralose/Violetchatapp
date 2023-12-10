package com.benavivi.violetchatapp.dataModels;

import static com.benavivi.violetchatapp.utilities.Constants.FirebaseConstants.*;

import java.text.DateFormat;
import java.util.Date;
import java.util.Map;

public class Message {

	private String message , senderID, receiverID,senderName;
	private long date;



	public Message (String message, String senderID, String receiverID, long date, String senderName) {
		this.message = message;
		this.senderID = senderID;
		this.receiverID = receiverID;
		this.date = date;
		this.senderName = senderName;

	}

	public Message (Map<String, Object> stringObjectMap, String sentToID) {
		this.message = stringObjectMap.get(KEY_MESSAGE_TEXT).toString();
		this.senderID = stringObjectMap.get(KEY_MESSAGE_SENDER_ID).toString();
		this.receiverID = sentToID;
		this.date = (long) stringObjectMap.get(KEY_MESSAGE_DATE);
		this.senderName = stringObjectMap.get(KEY_MESSAGE_SENDER_NAME).toString();

	}


	public String getSenderName () {
		return senderName;
	}

	public void setSenderName (String senderName) {
		this.senderName = senderName;
	}

	public String getMessageText () {
		return message;
	}

	public void setMessageText (String message) {
		this.message = message;
	}

	public String getSenderID () {
		return senderID;
	}

	public void setSenderID (String senderID) {
		this.senderID = senderID;
	}

	public String getReceiverID () {
		return receiverID;
	}

	public void setReceiverID (String receiverID) {
		this.receiverID = receiverID;
	}

	public long getDate () {
		return this.date;
	}

	public void setDate (long date) {
		this.date = date;
	}

	public String getFormatedMessage () {
		return this.senderName + ": " + this.message;
	}

	public String getFormatedDate () {
		return DateFormat.getDateInstance().format(new Date(this.date));
	}
}
