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
import com.benavivi.violetchatapp.utilities.Constants;
import com.benavivi.violetchatapp.utilities.FirebaseManager;
import com.benavivi.violetchatapp.utilities.PreferenceManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {

	private ActivitySignUpBinding binding;

	private FirebaseFirestore firestoreDatabase;
	private FirebaseAuth firebaseAuth;
	private Uri profilePictureImageUri;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		binding = ActivitySignUpBinding.inflate(getLayoutInflater());
		firestoreDatabase = FirebaseFirestore.getInstance();
		firebaseAuth = FirebaseAuth.getInstance();
		setContentView(binding.getRoot());
		setListeners();
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

		firebaseAuth.createUserWithEmailAndPassword(binding.inputEmailAddress.getText().toString(), binding.inputPassword.getText().toString())
			.addOnSuccessListener(authResult -> {
				FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
				FirebaseManager.updateUserProfile(binding.inputDisplayName.getText().toString(),profilePictureImageUri);
				addToUserFirestore();
			})
			.addOnFailureListener(e -> {
				showShortToast(e.getMessage());
				loading(false);
			});
		}

	private void addToUserFirestore () {
		HashMap<String,Object> user = new HashMap<>();
		user.put(Constants.UserConstants.KEY_NAME, binding.inputDisplayName.getText().toString());
		user.put(Constants.UserConstants.KEY_EMAIL,binding.inputEmailAddress.getText().toString());
		user.put(Constants.UserConstants.KEY_IMAGE , profilePictureImageUri);


		firestoreDatabase.collection(Constants.UserConstants.KEY_COLLECTION_USER)
			.add(user)
			.addOnSuccessListener(documentReference -> {
				Intent goToChatsIntent = new Intent(getApplicationContext(), MainActivity.class);
				startActivity(goToChatsIntent);
				loading(false);
			})
			.addOnFailureListener(e -> {
				showShortToast(e.getMessage());
				loading(false);
			});
	}


	//Image Picker handler
	private final ActivityResultLauncher<Intent> pickImage = registerForActivityResult(
		new ActivityResultContracts.StartActivityForResult(),
		result ->{
			if(result.getData() != null){
				profilePictureImageUri = result.getData().getData();
				binding.profileImage.setImageURI(profilePictureImageUri);
			}
		}
	);


	private boolean isValidSignUpDetails(){
		if(profilePictureImageUri==null){
			showShortToast("Please select a profile picture");
			return false;
		}
		if(binding.inputDisplayName.getText().toString().isEmpty()){
			showShortToast("Please enter your display name");
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
		if(binding.inputPassword.getText().toString().length() <6 ){
			showShortToast("Your password must be atleast 6 digits.");
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