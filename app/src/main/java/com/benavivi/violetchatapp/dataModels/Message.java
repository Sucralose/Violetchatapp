package com.benavivi.violetchatapp.dataModels;

import static com.benavivi.violetchatapp.utilities.Constants.FirebaseConstants.*;

import com.google.firebase.firestore.PropertyName;

import java.text.DateFormat;
import java.util.Date;
import java.util.Map;

public class Message {

	private String message , senderID,senderName;
	private long date;


	public Message () {

	}

	public Message (String message, String senderID, long date, String senderName) {
		this.message = message;
		this.senderID = senderID;
		this.date = date;
		this.senderName = senderName;

	}

	public Message (Map<String, Object> stringObjectMap) {
		this.message = stringObjectMap.get(KEY_MESSAGE_TEXT).toString();
		this.senderID = stringObjectMap.get(KEY_MESSAGE_SENDER_ID).toString();
		this.date = (long) stringObjectMap.get(KEY_MESSAGE_DATE);
		this.senderName = stringObjectMap.get(KEY_MESSAGE_SENDER_NAME).toString();

	}


	@PropertyName(KEY_MESSAGE_SENDER_NAME)
	public String getSenderName () {
		return senderName;
	}

	public void setSenderName (String senderName) {
		this.senderName = senderName;
	}

	@PropertyName(KEY_MESSAGE_TEXT)
	public String getMessageText () {
		return message;
	}

	public void setMessageText (String message) {
		this.message = message;
	}

	@PropertyName(KEY_MESSAGE_SENDER_ID)
	public String getSenderID () {
		return senderID;
	}

	public void setSenderID (String senderID) {
		this.senderID = senderID;
	}

	@PropertyName(KEY_MESSAGE_DATE)
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
