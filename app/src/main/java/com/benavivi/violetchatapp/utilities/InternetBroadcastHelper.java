package com.benavivi.violetchatapp.utilities;

import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

import com.benavivi.violetchatapp.services.InternetConnectionBroadcastReceiver;

public class InternetBroadcastHelper {
InternetConnectionBroadcastReceiver networkReceiver;
Context currentContext;

public InternetBroadcastHelper( Context currentContext ) {
	this.currentContext = currentContext;
	networkReceiver = new InternetConnectionBroadcastReceiver( );
}

public void registerInternetBroadcast( ) {
	IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
	this.currentContext.registerReceiver(networkReceiver, filter);
}

public void unregisterInternetBroadcast( ) {
	this.currentContext.unregisterReceiver(networkReceiver);
}

}
