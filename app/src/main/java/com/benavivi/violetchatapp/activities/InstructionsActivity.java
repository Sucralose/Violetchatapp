package com.benavivi.violetchatapp.activities;

import static com.benavivi.violetchatapp.utilities.Constants.InstructionActivityConstants.DEVELOPER_EMAIL_ADDRESS;
import static com.benavivi.violetchatapp.utilities.Constants.InstructionActivityConstants.INSTRUCTIONS_FILE_ID;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.benavivi.violetchatapp.databinding.ActivityInstructionsBinding;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class InstructionsActivity extends AppCompatActivity {
ActivityInstructionsBinding binding;

@Override
protected void onCreate( Bundle savedInstanceState ) {

	super.onCreate(savedInstanceState);
	binding = ActivityInstructionsBinding.inflate(getLayoutInflater( ));
	setContentView(binding.getRoot( ));

	setTextFromFile( );
	setListeners( );

}

private void setListeners( ) {
	binding.instructionsBack.setOnClickListener(new View.OnClickListener( ) {
		@Override
		public void onClick( View v ) {
			Intent mainActivityIntent = new Intent(InstructionsActivity.this, MainActivity.class);
			startActivity(mainActivityIntent);
			finish( );
		}
	});

	binding.reportABugButton.setOnClickListener(new View.OnClickListener( ) {
		@Override
		public void onClick( View v ) {
			sendEmailBugReport( );
		}
	});
}

private void sendEmailBugReport( ) {
	Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
	emailIntent.setData(Uri.parse("mailto:" + DEVELOPER_EMAIL_ADDRESS));
	startActivity(Intent.createChooser(emailIntent, "Contact the Developer"));
/*
	int i = 1500;
	Handler handler = new Handler( );
	handler.postDelayed(new Runnable( ) {
		@Override
		public void run( ) {
			//Toast.makeText(this, owner, Toast.LENGTH_LONG).show();

		}
	},i);*/
}

private void setTextFromFile( ) {
	String finalText = "";
	try {
		InputStream inputStream = getResources( ).openRawResource(INSTRUCTIONS_FILE_ID);
		BufferedReader input = new BufferedReader(new InputStreamReader(inputStream));
		String line = "";
		while ( ( line = input.readLine( ) ) != null ) {
			finalText += line;
			finalText += " \n ";
		}
	} catch ( Exception e ) {
		Log.e("Instruction Activity", "setTextFromFile: " + e.getMessage( ));
	}
	binding.instructionsText.setText(finalText);
}
}