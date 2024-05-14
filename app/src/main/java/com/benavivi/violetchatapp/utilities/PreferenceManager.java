package com.benavivi.violetchatapp.utilities;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

/***
 * Preference Manager allows save data on the users device
 *
 * @version 1.2
 *
 */
public class PreferenceManager {

private final SharedPreferences sharedPreferences;

public PreferenceManager( Context context ) {
	sharedPreferences = context.getSharedPreferences(PreferenceManagerConstants.PREFERENCES_NAME, Context.MODE_PRIVATE);
}

public void putInt( String key, int value ) {
	SharedPreferences.Editor editor = sharedPreferences.edit( );
	editor.putInt(key, value);
	editor.apply( );
}

public int getInt( String key ) {
	return sharedPreferences.getInt(key, PreferenceManagerConstants.DEFAULT_INT);
}

public void putLong( String key, long value ) {
	SharedPreferences.Editor editor = sharedPreferences.edit( );
	editor.putLong(key, value);
	editor.apply( );
}

public long getLong( String key ) {
	return sharedPreferences.getLong(key, PreferenceManagerConstants.DEFAULT_LONG);
}

public void putFloat( String key, float value ) {
	SharedPreferences.Editor editor = sharedPreferences.edit( );
	editor.putFloat(key, value);
	editor.apply( );
}

public float getFloat( String key ) {
	return sharedPreferences.getFloat(key, PreferenceManagerConstants.DEFAULT_FLOAT);
}

public void putBoolean( String key, Boolean value ) {
	SharedPreferences.Editor editor = sharedPreferences.edit( );
	editor.putBoolean(key, value);
	editor.apply( );
}

public Boolean getBoolean( String key ) {
	return sharedPreferences.getBoolean(key, PreferenceManagerConstants.DEFAULT_BOOLEAN);
}

public void putString( String key, String value ) {
	SharedPreferences.Editor editor = sharedPreferences.edit( );
	editor.putString(key, value);
	editor.apply( );
}

public String getString( String key ) {
	return sharedPreferences.getString(key, PreferenceManagerConstants.DEFAULT_STRING);
}

public void remove( String key ) {
	SharedPreferences.Editor editor = sharedPreferences.edit( );
	editor.remove(key);
	editor.apply( );
}

public void putStringSet( String key, Set<String> value ) {
	SharedPreferences.Editor editor = sharedPreferences.edit( );
	editor.putStringSet(key, value);
	editor.apply( );
}

public Set<String> getStringSet( String key ) {
	return sharedPreferences.getStringSet(key, PreferenceManagerConstants.DEFAULT_STRING_SET);
}

public void wipeSharedPreferences( ) {
	SharedPreferences.Editor editor = sharedPreferences.edit( );
	editor.clear( );
	editor.apply( );
}

private static class PreferenceManagerConstants {
	private static final String PREFERENCES_NAME = "VioletChatApp";
	private static final int DEFAULT_INT = -1;
	private static final long DEFAULT_LONG = -1;
	private static final float DEFAULT_FLOAT = -1;
	private static final boolean DEFAULT_BOOLEAN = false;
	private static final String DEFAULT_STRING = "";
	private static final Set<String> DEFAULT_STRING_SET = null;
}


}
