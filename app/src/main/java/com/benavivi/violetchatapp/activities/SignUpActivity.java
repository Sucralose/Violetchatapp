package com.benavivi.violetchatapp.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.benavivi.violetchatapp.databinding.ActivitySignUpBinding;
import com.benavivi.violetchatapp.utilities.FirebaseManager;
import static com.benavivi.violetchatapp.utilities.Constants.UserConstants.*;

public class SignUpActivity extends AppCompatActivity {

	private ActivitySignUpBinding binding;
	private Uri profilePictureImageUri;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		binding = ActivitySignUpBinding.inflate(getLayoutInflater());
		setContentView(binding.getRoot());
		setListeners();

	}

	@Override
	protected void onStart () {
		super.onStart();
	}

	private void setListeners () {
		binding.signInText.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(),SignInActivity.class)));
		binding.signUpButton.setOnClickListener(view -> { if(isValidSignUpDetails()) signUp(); });
		binding.layoutImage.setOnClickListener(view -> {
			Intent pickImageIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			pickImageIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
			pickImage.launch(pickImageIntent);
		});
	}

	private void showShortToast(String message){
		Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
	}

	private void signUp(){
		loading(true);

		 FirebaseManager.signUp(binding.inputEmailAddress.getText().toString(),
				   binding.inputPassword.getText().toString(),
				   binding.inputDisplayName.getText().toString(),
				   profilePictureImageUri)
			.addOnCompleteListener(task -> {
				loading(false);
				if(task.isSuccessful()){
					Intent goToChatsIntent = new Intent(getApplicationContext(), MainActivity.class);
					startActivity(goToChatsIntent);
				}
				else
					showShortToast(task.getException().getMessage());
			});

		}


	//Image Picker handler
	private final ActivityResultLauncher<Intent> pickImage = registerForActivityResult(
		new ActivityResultContracts.StartActivityForResult(),
		result ->{
			if(result.getData() != null){
				profilePictureImageUri = result.getData().getData();
				binding.profileImage.setImageURI(profilePictureImageUri);
				binding.addImageText.setVisibility(View.INVISIBLE);

			}
		}
	);


	private boolean isValidSignUpDetails(){
		if(profilePictureImageUri==null){
			showShortToast("Please select a profile picture");
			return false;
		}
		if(! (binding.inputDisplayName.getText().toString().length() >= MIN_DISPLAY_NAME_LENGTH && binding.inputDisplayName.getText().toString().length() <= MAX_DISPLAY_NAME_LENGTH) ){
			showShortToast("Your display name must be between " + MIN_DISPLAY_NAME_LENGTH + " and " + MAX_DISPLAY_NAME_LENGTH + " Characters.");
			return false;
		}
		if(binding.inputEmailAddress.getText().toString().isEmpty()){
			showShortToast("Please enter your email address");
			return false;
		}
		//Use builtin regex to determine if is valid address
		if(!Patterns.EMAIL_ADDRESS.matcher(binding.inputEmailAddress.getText().toString()).matches()){
			showShortToast("Please enter a valid email address");
			return false;
		}
		if(! (binding.inputPassword.getText().toString().length() >= MIN_PASSWORD_LENGTH && binding.inputPassword.getText().toString().length() <= MAX_PASSWORD_LENGTH) ){
			showShortToast("Your password must be between " + MIN_PASSWORD_LENGTH + " and " + MAX_PASSWORD_LENGTH + " Characters.");
			return false;
		}
		if(binding.inputConfirmPassword.getText().toString().isEmpty()){
			showShortToast("Please confirm your passwords");
			return false;
		}
		if(!binding.inputPassword.getText().toString().equals(  binding.inputConfirmPassword.getText().toString()  )){
			showShortToast("Both passwords must be the same.");
			binding.inputConfirmPassword.setText("");
			return false;
		}
		return true;

	}

	private void loading(Boolean isLoading){
		if(isLoading){
			binding.signUpButton.setVisibility(View.INVISIBLE);
			binding.progressBar.setVisibility(View.VISIBLE);
		}else{
			binding.signUpButton.setVisibility(View.VISIBLE);
			binding.progressBar.setVisibility(View.INVISIBLE);
		}

	}
}