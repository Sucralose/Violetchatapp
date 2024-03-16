package com.benavivi.violetchatapp.dataModels;

import static com.benavivi.violetchatapp.utilities.Constants.FirebaseConstants.KEY_MESSAGE_DATE;
import static com.benavivi.violetchatapp.utilities.Constants.FirebaseConstants.KEY_MESSAGE_SENDER_ID;
import static com.benavivi.violetchatapp.utilities.Constants.FirebaseConstants.KEY_MESSAGE_SENDER_IMAGE_URL;
import static com.benavivi.violetchatapp.utilities.Constants.FirebaseConstants.KEY_MESSAGE_SENDER_NAME;
import static com.benavivi.violetchatapp.utilities.Constants.FirebaseConstants.KEY_MESSAGE_TEXT;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.PropertyName;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.Map;

public class Message {

private String message;
private String senderID;
private String senderName;


private String senderImageURL;
private Timestamp date;


public Message ( ) {

}

public Message ( String message, String senderID, Timestamp date, String senderName ) {
	this.message = message;
	this.senderID = senderID;
	this.date = date;
	this.senderName = senderName;

}

public Message ( String message, String senderID, String senderName, String senderImageURL, Timestamp date ) {
	this.message = message;
	this.senderID = senderID;
	this.senderName = senderName;
	this.senderImageURL = senderImageURL;
	this.date = date;
}

public Message ( Map<String,Object> stringObjectMap ) {
	this.message = stringObjectMap.get(KEY_MESSAGE_TEXT).toString();
	this.senderID = stringObjectMap.get(KEY_MESSAGE_SENDER_ID).toString();
	this.date = (Timestamp) stringObjectMap.get(KEY_MESSAGE_DATE);
	this.senderName = stringObjectMap.get(KEY_MESSAGE_SENDER_NAME).toString();
	this.senderImageURL = stringObjectMap.get(KEY_MESSAGE_SENDER_IMAGE_URL).toString();

}


@PropertyName(KEY_MESSAGE_SENDER_NAME)
public String getSenderName ( ) {
	return senderName;
}

@PropertyName(KEY_MESSAGE_SENDER_NAME)
public void setSenderName ( String senderName ) {
	this.senderName = senderName;
}

@PropertyName(KEY_MESSAGE_TEXT)
public String getMessageText ( ) {
	return message;
}

@PropertyName(KEY_MESSAGE_TEXT)
public void setMessageText ( String message ) {
	this.message = message;
}

@PropertyName(KEY_MESSAGE_SENDER_ID)
public String getSenderID ( ) {
	return senderID;
}

@PropertyName(KEY_MESSAGE_SENDER_ID)
public void setSenderID ( String senderID ) {
	this.senderID = senderID;
}

@PropertyName(KEY_MESSAGE_DATE)
public Date getDate ( ) {
	return this.date.toDate();
}


@PropertyName(KEY_MESSAGE_DATE)
public void setDate ( Timestamp date ) {
		this.date = date;
	}


public String getFormattedMessage( ) {
	return this.senderName + ": " + this.message;
}

public String getFormattedDate( ) {
	String pattern = "dd/MM/yyyy HH:mm:ss";
	return new SimpleDateFormat(pattern, java.util.Locale.ENGLISH).format(date.toDate());
}

@PropertyName(KEY_MESSAGE_SENDER_IMAGE_URL)
public String getSenderImageURL ( ) {
	return this.senderImageURL;
}

@PropertyName(KEY_MESSAGE_SENDER_IMAGE_URL)
public void setSenderImageURL ( String senderImageURL ) {
	this.senderImageURL = senderImageURL;
}

public long recieveTimestampNumber ( ) {
		return this.date.getSeconds();
	}


}
