package com.benavivi.violetchatapp.fragments;

import static android.app.Activity.RESULT_OK;
import static com.benavivi.violetchatapp.utilities.Constants.FirebaseConstants.KEY_USER_DISPLAY_NAME;
import static com.benavivi.violetchatapp.utilities.Constants.FirebaseConstants.KEY_USER_PROFILE_IMAGE;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.benavivi.violetchatapp.activities.SignInActivity;
import com.benavivi.violetchatapp.databinding.FragmentSettingsBinding;
import com.benavivi.violetchatapp.utilities.CameraHandler;
import com.benavivi.violetchatapp.utilities.Constants;
import com.benavivi.violetchatapp.utilities.FirebaseManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.squareup.picasso.Picasso;

import java.util.Map;

public class SettingsFragment extends Fragment {
private FragmentSettingsBinding binding;
private final ActivityResultLauncher<Intent> pickImage = registerForActivityResult(
	new ActivityResultContracts.StartActivityForResult( ),
	result -> {
		if ( result.getData( ) != null ) {
			Uri profilePictureImageUri = result.getData( ).getData( );
			binding.profileImage.setImageURI(profilePictureImageUri);
			FirebaseManager.uploadUserProfileImage(profilePictureImageUri);
		}
	}
);
private final ActivityResultLauncher<Intent> openCamera = registerForActivityResult(
	new ActivityResultContracts.StartActivityForResult( ),
	result -> {
		if ( result.getResultCode( ) == RESULT_OK && result.getData( ) != null ) {

			Uri profilePictureImageUri = CameraHandler.getImageUriFromBitmap(getContext( ), (Bitmap) result.getData( ).getExtras( ).get("data"));
			binding.profileImage.setImageURI(profilePictureImageUri);
			FirebaseManager.uploadUserProfileImage(profilePictureImageUri);
		}
	}
);

@Override
public void onCreate( Bundle savedInstanceState ) {
	super.onCreate(savedInstanceState);
}

@Override
public void onViewCreated( @NonNull View view, @Nullable Bundle savedInstanceState ) {
	super.onViewCreated(view, savedInstanceState);
	setListeners( );
	personalizeTextAndImage( );
}

private void personalizeTextAndImage( ) {
	FirebaseManager.getCurrentUserData( )
		.addOnCompleteListener(
			new OnCompleteListener<DocumentSnapshot>( ) {
				@Override
				public void onComplete( @NonNull Task<DocumentSnapshot> task ) {
					if ( task.isSuccessful( ) ) {
						Map<String, Object> userData = task.getResult( ).getData( );
						if ( !userData.get(KEY_USER_PROFILE_IMAGE).toString( ).isEmpty( ) )
							Picasso.get( ).load(userData.get(KEY_USER_PROFILE_IMAGE).toString( )).into(binding.profileImage);
						binding.usernameTextview.setText(userData.get(KEY_USER_DISPLAY_NAME).toString( ));

					}
				}
			});
}

private void setListeners( ) {
	binding.signOutButton.setOnClickListener(view -> {
		FirebaseManager.signOut( );
		Intent returnToSignInIntent = new Intent(getContext( ), SignInActivity.class);
		startActivity(returnToSignInIntent);
	});

	binding.ChangeDisplayNameButton.setOnClickListener(view -> {
		changeNameAlertDialog( );
	});
	binding.ChangeProfileImageButton.setOnClickListener(view -> {
		CameraHandler.selectImage(getContext( ), pickImage, openCamera);
	});
}

@Override
public View onCreateView( LayoutInflater inflater, ViewGroup container,
			  Bundle savedInstanceState ) {
	super.onCreateView(inflater, container, savedInstanceState);
	binding = FragmentSettingsBinding.inflate(inflater, container, false);


	return binding.getRoot( );
}

private void changeNameAlertDialog( ) {
	AlertDialog.Builder changeNameDialogBuilder = new AlertDialog.Builder(getContext( ));
	changeNameDialogBuilder.setTitle("What would you like your new name to be?");

	final EditText newNameEditText = new EditText(getContext( ));
	newNameEditText.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

	changeNameDialogBuilder.setView(newNameEditText);
	changeNameDialogBuilder.setCancelable(true);
	changeNameDialogBuilder.setNegativeButton("Cancel", ( dialog, which ) -> dialog.dismiss( ));
	changeNameDialogBuilder.setPositiveButton("Change", ( dialog, which ) -> {
		String newUserName = newNameEditText.getText( ).toString( );
		if ( isValidName(newUserName) ) {
			FirebaseManager.changeCurrentUserDisplayName(newUserName);
			binding.usernameTextview.setText(newUserName);
		}
	});

	changeNameDialogBuilder.show( );
}

private boolean isValidName( String name ) {
	return name.length( ) >= Constants.UserConstants.MIN_DISPLAY_NAME_LENGTH && name.length( ) <= Constants.UserConstants.MAX_DISPLAY_NAME_LENGTH;
}
}