package com.benavivi.violetchatapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.benavivi.violetchatapp.R;
import com.benavivi.violetchatapp.databinding.ActivitySignInBinding;

public class SignInActivity extends AppCompatActivity {

	private ActivitySignInBinding binding;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		binding = ActivitySignInBinding.inflate(getLayoutInflater());
		setContentView(binding.getRoot());
		setLisenters();
	}

	private void setLisenters(){
		binding.createNewAccountText.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(),SignUpActivity.class)));
	}
}