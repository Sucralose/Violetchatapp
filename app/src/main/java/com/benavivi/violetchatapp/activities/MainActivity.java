package com.benavivi.violetchatapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.benavivi.violetchatapp.R;
import com.benavivi.violetchatapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

	private ActivityMainBinding binding;
	@Override
	protected void onStart () {
		super.onStart();
	}


	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		binding = ActivityMainBinding.inflate(getLayoutInflater());
		setUpToolbar();

	}

	private void setUpToolbar () {
		setSupportActionBar(findViewById(R.id.mainPageToolBar));
		getSupportActionBar().setTitle("Violet Chat");
	}
}