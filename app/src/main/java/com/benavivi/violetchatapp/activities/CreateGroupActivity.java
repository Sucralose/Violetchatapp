package com.benavivi.violetchatapp.activities;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import com.benavivi.violetchatapp.R;
import com.benavivi.violetchatapp.dataModels.Group;
import com.benavivi.violetchatapp.databinding.ActivityCreateGroupBinding;
import com.benavivi.violetchatapp.utilities.Constants;
import com.benavivi.violetchatapp.utilities.FirebaseManager;
import com.google.firebase.Timestamp;

import java.util.ArrayList;

public class CreateGroupActivity extends AppCompatActivity {
	ActivityCreateGroupBinding binding;
	private Uri chatImageUri;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		binding = ActivityCreateGroupBinding.inflate(getLayoutInflater());
		setContentView(binding.getRoot());

		setListeners();
	}

	private void setListeners( ) {
		binding.createGroupButton.setOnClickListener(view -> {
			if(validSignupData()){
				loading(true);
				FirebaseManager.createNewGroup(
					binding.inputChatName.getText().toString(),
					chatImageUri
				);
				new Handler(  ).postDelayed(( ) -> {
					loading(false);
					Intent intent = new Intent(CreateGroupActivity.this, MainActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
					startActivity(intent);
					finish();
				},Constants.ApplicationConstants.CREATE_GROUP_DELAY_MILLISECONDS);
			}
		});

		binding.groupProfileImage.setOnClickListener(view -> {
			Intent pickImageIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			pickImageIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
			pickImage.launch(pickImageIntent);
		});

		binding.createGroupBack.setOnClickListener(view -> {
			Intent intent = new Intent(CreateGroupActivity.this, MainActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
			startActivity(intent);
			finish();
		});


	}

	private boolean validSignupData( ) {
		if(binding.inputChatName.getText().length() < Constants.UserConstants.MIN_DISPLAY_NAME_LENGTH || binding.inputChatName.getText().length() > Constants.UserConstants.MAX_DISPLAY_NAME_LENGTH){
			showShortToast("Group name must be between " + Constants.UserConstants.MIN_DISPLAY_NAME_LENGTH + " and " + Constants.UserConstants.MAX_DISPLAY_NAME_LENGTH + " Characters.");
			return false;
		}
		if(chatImageUri == null){
			showShortToast("Please select a chat picture");
			return false;
		}
		return true;
	}

	private final ActivityResultLauncher<Intent> pickImage = registerForActivityResult(
		new ActivityResultContracts.StartActivityForResult(),
		result -> {
			if ( result.getData() != null ) {
				chatImageUri = result.getData().getData();
				binding.groupProfileImage.setImageURI(chatImageUri);
				binding.addChatImageText.setVisibility(View.INVISIBLE);
			}
		}
	);

	private void showShortToast ( String message ) {
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}

	private void loading ( Boolean isLoading ) {
		if ( isLoading ) {
			binding.createGroupButton.setVisibility(View.INVISIBLE);
			binding.progressBar.setVisibility(View.VISIBLE);
		} else {
			binding.createGroupButton.setVisibility(View.VISIBLE);
			binding.progressBar.setVisibility(View.INVISIBLE);
		}
	}

}