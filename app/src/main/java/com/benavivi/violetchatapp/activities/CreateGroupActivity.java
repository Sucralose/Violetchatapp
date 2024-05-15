package com.benavivi.violetchatapp.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.benavivi.violetchatapp.databinding.ActivityCreateGroupBinding;
import com.benavivi.violetchatapp.utilities.CameraHandler;
import com.benavivi.violetchatapp.utilities.Constants;
import com.benavivi.violetchatapp.utilities.FirebaseManager;
import com.benavivi.violetchatapp.utilities.InternetBroadcastHelper;

public class CreateGroupActivity extends AppCompatActivity {
ActivityCreateGroupBinding binding;
InternetBroadcastHelper internetBroadcastHelper;
private Uri chatImageUri;
private final ActivityResultLauncher<Intent> pickImage = registerForActivityResult(
	new ActivityResultContracts.StartActivityForResult( ),
	result -> {
		if ( result.getData( ) != null ) {
			chatImageUri = result.getData( ).getData( );
			binding.groupProfileImage.setImageURI(chatImageUri);
			binding.addChatImageText.setVisibility(View.INVISIBLE);
		}
	}
);
private final ActivityResultLauncher<Intent> openCamera = registerForActivityResult(
	new ActivityResultContracts.StartActivityForResult( ),
	result -> {
		if ( result.getResultCode( ) == RESULT_OK && result.getData( ) != null ) {

			chatImageUri = CameraHandler.getImageUriFromBitmap(CreateGroupActivity.this, (Bitmap) result.getData( ).getExtras( ).get("data"));
			binding.groupProfileImage.setImageURI(chatImageUri);
			binding.addChatImageText.setVisibility(View.INVISIBLE);
		}
	}
);

@Override
protected void onCreate( Bundle savedInstanceState ) {
	super.onCreate(savedInstanceState);
	binding = ActivityCreateGroupBinding.inflate(getLayoutInflater( ));
	setContentView(binding.getRoot( ));

	setListeners( );

	internetBroadcastHelper = new InternetBroadcastHelper(this);
	internetBroadcastHelper.registerInternetBroadcast( );
}

private void setListeners( ) {
	binding.createGroupButton.setOnClickListener(view -> {
		buttonActionLoading(true);
		if ( validGroupData( ) ) {

			FirebaseManager.createNewGroup(
				binding.inputChatName.getText( ).toString( ),
				chatImageUri
			);
			//Delay inorder to ensure that the image has been uploaded.
			new Handler( ).postDelayed(( ) -> {
				Intent intent = new Intent(CreateGroupActivity.this, MainActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
				startActivity(intent);
				finish( );
			}, Constants.ApplicationConstants.CREATE_GROUP_DELAY_MILLISECONDS);
		}
		buttonActionLoading(false);
	});

	binding.groupProfileImage.setOnClickListener(view -> {
		CameraHandler.selectImage(this, pickImage, openCamera);
	});

	binding.createGroupBack.setOnClickListener(view -> {
		Intent intent = new Intent(CreateGroupActivity.this, MainActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
		startActivity(intent);
		finish( );
	});


}

private boolean validGroupData( ) {
	if ( binding.inputChatName.getText( ).length( ) < Constants.UserConstants.MIN_DISPLAY_NAME_LENGTH || binding.inputChatName.getText( ).length( ) > Constants.UserConstants.MAX_DISPLAY_NAME_LENGTH ) {
		showShortToast("Group name must be between " + Constants.UserConstants.MIN_DISPLAY_NAME_LENGTH + " and " + Constants.UserConstants.MAX_DISPLAY_NAME_LENGTH + " Characters.");
		return false;
	}
	if ( chatImageUri == null ) {
		showShortToast("Please select a chat picture");
		return false;
	}
	return true;
}

private void showShortToast( String message ) {
	Toast.makeText(this, message, Toast.LENGTH_SHORT).show( );
}

private void buttonActionLoading( Boolean isLoading ) {
	if ( isLoading ) {
		binding.createGroupButton.setVisibility(View.INVISIBLE);
		binding.progressBar.setVisibility(View.VISIBLE);
	} else {
		binding.createGroupButton.setVisibility(View.VISIBLE);
		binding.progressBar.setVisibility(View.INVISIBLE);
	}
}


protected void onDestroy( ) {
	internetBroadcastHelper.unregisterInternetBroadcast( );
	super.onDestroy( );
}

}