package com.benavivi.violetchatapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.benavivi.violetchatapp.R;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onStart () {
		super.onStart();
	}


	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
}