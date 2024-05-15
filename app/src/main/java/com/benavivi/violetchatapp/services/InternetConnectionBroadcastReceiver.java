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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.benavivi.violetchatapp.R;

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
	TextView connectionStatusText, connectionStatusExplanation;
	ImageView connectionStatusIcon;
	Button connectionStatusButton;
	boolean isConnected = !connectionStatus.equals(INTERNET_UNAVAILABLE_CODE);

	//TODO: create dialog here
	final Dialog internetDialog = new Dialog(currentContext);
	internetDialog.setCancelable(false);
	internetDialog.setContentView(R.layout.layout_internet_alert_dialog);
	internetDialog.getWindow( ).setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
	internetDialog.getWindow( ).getAttributes( ).windowAnimations = android.R.style.Animation_Dialog;

	connectionStatusText = internetDialog.findViewById(R.id.layout_internet_alert_dialog_connection_status);
	connectionStatusExplanation = internetDialog.findViewById(R.id.layout_internet_alert_dialog_connection_status_explanation);
	connectionStatusIcon = internetDialog.findViewById(R.id.layout_internet_alert_dialog_connection_icon);
	connectionStatusButton = internetDialog.findViewById(R.id.layout_internet_alert_dialog_connection_button);

	connectionStatusIcon.setImageResource(isConnected ? R.drawable.connected_internet_icon_24 : R.drawable.disconnected_internet_icon_24);

	connectionStatusText.setText(connectionStatus);

	connectionStatusButton.setOnClickListener(v -> {
		internetDialog.dismiss( );
	});

	if ( connectionStatus.equals(INTERNET_CONNECTED_WITH_WIFI_CODE) )
		connectionStatusExplanation.setText(R.string.ConnectionWifiExplanation);
	if ( connectionStatus.equals(INTERNET_CONNECTED_WITH_MOBILE_DATA_CODE) )
		connectionStatusExplanation.setText(R.string.ConnectionMobileDataExplanation);
	if ( connectionStatus.equals(INTERNET_UNAVAILABLE_CODE) )
		connectionStatusExplanation.setText(R.string.InternetUnavailableExplanation);


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


