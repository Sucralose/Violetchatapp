package com.benavivi.violetchatapp.activities;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;

import com.benavivi.violetchatapp.R;
import com.benavivi.violetchatapp.dataModels.Group;
import com.benavivi.violetchatapp.databinding.ActivityCreateGroupBinding;
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
		binding.createGroupButton.setOnClickListener(v -> {
			if(validSignupData()){
				FirebaseManager.createNewGroup(
					binding.inputChatName.getText().toString(),
					chatImageUri
				);
			}
		});

		binding.groupProfileImage.setOnClickListener(view -> {
			Intent pickImageIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			pickImageIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
			pickImage.launch(pickImageIntent);
		});
	}

	private boolean validSignupData( ) {
		if(binding.inputChatName.getText().toString().isEmpty())
			return false;
		if(chatImageUri == null)
			return false;
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

}