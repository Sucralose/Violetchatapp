package com.benavivi.violetchatapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.benavivi.violetchatapp.databinding.ActivitySignInBinding;
import com.benavivi.violetchatapp.utilities.FirebaseManager;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity {

	private ActivitySignInBinding binding;
	private FirebaseAuth firebaseAuth;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		binding = ActivitySignInBinding.inflate(getLayoutInflater());
		firebaseAuth= FirebaseAuth.getInstance();
		setContentView(binding.getRoot());
		setLisenters();
	}

	@Override
	protected void onStart () {
		super.onStart();
		if(FirebaseManager.isSignedIn()){
			Intent mainActivityIntent = new Intent(SignInActivity.this, MainActivity.class);
			startActivity(mainActivityIntent);
		}
	}

	private void setLisenters(){
		binding.createNewAccountText.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(),SignUpActivity.class)));
		binding.signInButton.setOnClickListener(view -> {
			if(isValidSignInData())
				signIn();
		});
		binding.forgotPasswordText.setOnClickListener(view -> {
			if (isValidEmail())
				sendPasswordReset();
		});
	}

	private void sendPasswordReset () {
		loading(true);
		firebaseAuth.sendPasswordResetEmail(binding.inputEmailAddress.getText().toString())
			.addOnSuccessListener(unused -> {
				showShortToast("Please check your email for a password reset.");
				loading(false);
			})
			.addOnFailureListener(e -> {
				showShortToast(e.getMessage());
				loading(false);
			});
	}


	private void signIn () {
		loading(true);
		Log.d("TAG","StartedSignIn");
		firebaseAuth.signInWithEmailAndPassword(binding.inputEmailAddress.getText().toString(),binding.inputPassword.getText().toString())
			.addOnSuccessListener(authResult -> {
				//FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
				Intent mainActivityIntent = new Intent(SignInActivity.this, MainActivity.class);
				startActivity(mainActivityIntent);
				loading(false);
			})
			.addOnFailureListener(e -> {
				showShortToast(e.getMessage());
				loading(false);
			});
	}

	private void loading(Boolean isLoading){
		if(isLoading){
			binding.signInButton.setVisibility(View.INVISIBLE);
			binding.progressBar.setVisibility(View.VISIBLE);
		}else{
			binding.signInButton.setVisibility(View.VISIBLE);
			binding.progressBar.setVisibility(View.INVISIBLE);
		}

	}

	private boolean isValidSignInData(){
		if(!isValidEmail())
			return false;
		if(binding.inputPassword.getText().toString().isEmpty() ){
			showShortToast("Please enter your password");
			return false;
		}
		return true;
	}

	private boolean isValidEmail () {
		if(binding.inputEmailAddress.getText().toString().isEmpty()){
			showShortToast("Please enter your email address");
			return false;
		}
		//Use builtin regex to determine if is valid address
		if(!Patterns.EMAIL_ADDRESS.matcher(binding.inputEmailAddress.getText().toString()).matches()){
			showShortToast("Please enter a valid email address");
			return false;
		}
		return true;
	}


	private void showShortToast(String message){
		Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
	}

}