package com.benavivi.violetchatapp.services;

import static com.benavivi.violetchatapp.utilities.Constants.InternetBroadcastReceiverConstants.INTERNET_CONNECTED_WITH_MOBILE_DATA_CODE;
import static com.benavivi.violetchatapp.utilities.Constants.InternetBroadcastReceiverConstants.INTERNET_CONNECTED_WITH_WIFI_CODE;
import static com.benavivi.violetchatapp.utilities.Constants.InternetBroadcastReceiverConstants.INTERNET_UNAVAILABLE_CODE;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.WindowManager;

import com.benavivi.violetchatapp.R;
import com.benavivi.violetchatapp.databinding.LayoutInternetAlertDialogBinding;

public class InternetConnectionBroadcastReceiver extends BroadcastReceiver {
static String connectionStatus = "";

public InternetConnectionBroadcastReceiver( ) {
}

@SuppressLint ( "UnsafeProtectedBroadcastReceiver" )
@Override
public void onReceive( Context context, Intent intent ) {


	ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	NetworkInfo activeNetworkInformation = connectivityManager.getActiveNetworkInfo( );

	if ( changeConnectionStatus(activeNetworkInformation) )
		createInternetStatusAlertDialog(context);
}


private void createInternetStatusAlertDialog( Context currentContext ) {
	LayoutInternetAlertDialogBinding layoutInternetAlertDialogBinding;
	boolean isConnected = !connectionStatus.equals(INTERNET_UNAVAILABLE_CODE);

	final Dialog internetDialog = new Dialog(currentContext);
	layoutInternetAlertDialogBinding = LayoutInternetAlertDialogBinding.inflate(internetDialog.getLayoutInflater( ));

	internetDialog.setContentView(layoutInternetAlertDialogBinding.getRoot( ));
	internetDialog.setCancelable(false);
	internetDialog.getWindow( ).setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
	internetDialog.getWindow( ).getAttributes( ).windowAnimations = android.R.style.Animation_Dialog;
	internetDialog.getWindow( ).setBackgroundDrawableResource(android.R.color.transparent);

	layoutInternetAlertDialogBinding.layoutInternetAlertDialogConnectionIcon.setImageResource(isConnected ? R.drawable.connected_internet_icon_24 : R.drawable.disconnected_internet_icon_24);

	layoutInternetAlertDialogBinding.layoutInternetAlertDialogConnectionStatus.setText(connectionStatus);

	layoutInternetAlertDialogBinding.layoutInternetAlertDialogConnectionButton.setOnClickListener(v -> {
		internetDialog.dismiss( );
	});

	if ( connectionStatus.equals(INTERNET_CONNECTED_WITH_WIFI_CODE) )
		layoutInternetAlertDialogBinding.layoutInternetAlertDialogConnectionStatusExplanation.setText(R.string.ConnectionWifiExplanation);
	if ( connectionStatus.equals(INTERNET_CONNECTED_WITH_MOBILE_DATA_CODE) )
		layoutInternetAlertDialogBinding.layoutInternetAlertDialogConnectionStatusExplanation.setText(R.string.ConnectionMobileDataExplanation);
	if ( connectionStatus.equals(INTERNET_UNAVAILABLE_CODE) )
		layoutInternetAlertDialogBinding.layoutInternetAlertDialogConnectionStatusExplanation.setText(R.string.InternetUnavailableExplanation);


	internetDialog.show( );


}

//Returns true if the has status changed
private boolean changeConnectionStatus( NetworkInfo activeNetworkInformation ) {
	String previousStatus = connectionStatus;
	if ( activeNetworkInformation == null ) {
		connectionStatus = INTERNET_UNAVAILABLE_CODE;
	}

	int connectionType = activeNetworkInformation != null ? activeNetworkInformation.getType( ) : -1;

	if ( connectionType == ConnectivityManager.TYPE_WIFI )
		connectionStatus = INTERNET_CONNECTED_WITH_WIFI_CODE;

	if ( connectionType == ConnectivityManager.TYPE_MOBILE )
		connectionStatus = INTERNET_CONNECTED_WITH_MOBILE_DATA_CODE;

	if ( previousStatus.isEmpty( ) && !connectionStatus.equals(INTERNET_UNAVAILABLE_CODE) )
		return false;

	return !previousStatus.equals(connectionStatus);
}

}


