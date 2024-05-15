package com.benavivi.violetchatapp.activities;

import static com.benavivi.violetchatapp.utilities.Constants.UserConstants.MAX_DISPLAY_NAME_LENGTH;
import static com.benavivi.violetchatapp.utilities.Constants.UserConstants.MAX_PASSWORD_LENGTH;
import static com.benavivi.violetchatapp.utilities.Constants.UserConstants.MIN_DISPLAY_NAME_LENGTH;
import static com.benavivi.violetchatapp.utilities.Constants.UserConstants.MIN_PASSWORD_LENGTH;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.benavivi.violetchatapp.databinding.ActivitySignUpBinding;
import com.benavivi.violetchatapp.services.InternetConnectionBroadcastReceiver;
import com.benavivi.violetchatapp.utilities.CameraHandler;
import com.benavivi.violetchatapp.utilities.FirebaseManager;
import com.benavivi.violetchatapp.utilities.InternetBroadcastHelper;

public class SignUpActivity extends AppCompatActivity {

InternetConnectionBroadcastReceiver networkReceiver;
InternetBroadcastHelper internetBroadcastHelper;
private ActivitySignUpBinding binding;
private Uri profilePictureImageUri;
//Image Picker handler
private final ActivityResultLauncher<Intent> pickImage = registerForActivityResult(
	new ActivityResultContracts.StartActivityForResult( ),
	result -> {
		if ( result.getData( ) != null ) {
			profilePictureImageUri = result.getData( ).getData( );
			binding.profileImage.setImageURI(profilePictureImageUri);
			binding.addImageText.setVisibility(View.INVISIBLE);
		}
	}
);

private final ActivityResultLauncher<Intent> openCamera = registerForActivityResult(
	new ActivityResultContracts.StartActivityForResult( ),
	result -> {
		if ( result.getResultCode( ) == RESULT_OK && result.getData( ) != null ) {

			profilePictureImageUri = CameraHandler.getImageUriFromBitmap(SignUpActivity.this, (Bitmap) result.getData( ).getExtras( ).get("data"));
			binding.profileImage.setImageURI(profilePictureImageUri);
			binding.addImageText.setVisibility(View.INVISIBLE);
		}
	}
);

@Override
protected void onCreate( Bundle savedInstanceState ) {
	super.onCreate(savedInstanceState);
	binding = ActivitySignUpBinding.inflate(getLayoutInflater( ));
	setContentView(binding.getRoot( ));
	setListeners( );

	internetBroadcastHelper = new InternetBroadcastHelper(this);
	internetBroadcastHelper.registerInternetBroadcast( );
}


private void setListeners( ) {
	binding.signInText.setOnClickListener(view -> startActivity(new Intent(this, SignInActivity.class)));
	binding.signUpButton.setOnClickListener(view -> {
		if ( isValidSignUpDetails( ) ) signUp( );
	});
	binding.layoutImage.setOnClickListener(view -> {
		CameraHandler.selectImage(this, pickImage, openCamera);
	});
}

private void showShortToast( String message ) {
	Toast.makeText(getApplicationContext( ), message, Toast.LENGTH_SHORT).show( );
}

private void signUp( ) {
	loading(true);

	FirebaseManager.signUp(binding.inputEmailAddress.getText( ).toString( ),
			binding.inputPassword.getText( ).toString( ),
			binding.inputDisplayName.getText( ).toString( ),
			profilePictureImageUri)
		.addOnCompleteListener(task -> {
			loading(false);
			if ( task.isSuccessful( ) ) {
				Intent goToChatsIntent = new Intent(this, MainActivity.class);
				startActivity(goToChatsIntent);
				finish( );
			} else
				showShortToast(task.getException( ).getLocalizedMessage( ));
		});

}

private boolean isValidSignUpDetails( ) {
	String displayName, emailAddress, password, confirmPassword;
	displayName = binding.inputDisplayName.getText( ).toString( );
	emailAddress = binding.inputEmailAddress.getText( ).toString( );
	password = binding.inputPassword.getText( ).toString( );
	confirmPassword = binding.inputConfirmPassword.getText( ).toString( );

	if ( profilePictureImageUri == null ) {
		showShortToast("Please select a profile picture");
		return false;
	}
	if ( displayName.length( ) < MIN_DISPLAY_NAME_LENGTH || displayName.length( ) > MAX_DISPLAY_NAME_LENGTH ) {
		showShortToast("Your display name must be between " + MIN_DISPLAY_NAME_LENGTH + " and "
			+ MAX_DISPLAY_NAME_LENGTH + " Characters.");
		return false;
	}
	if ( emailAddress.isEmpty( ) ) {
		showShortToast("Please enter your email address");
		return false;
	}
	//Use builtin regex to determine if is valid address
	if ( !Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches( ) ) {
		showShortToast("Please enter a valid email address");
		return false;
	}
	if ( password.length( ) < MIN_PASSWORD_LENGTH || password.length( ) > MAX_PASSWORD_LENGTH ) {
		showShortToast("Your password must be between " + MIN_PASSWORD_LENGTH + " and " + MAX_PASSWORD_LENGTH + " Characters.");
		return false;
	}
	if ( confirmPassword.isEmpty( ) ) {
		showShortToast("Please confirm your passwords");
		return false;
	}
	if ( !password.equals(confirmPassword) ) {
		showShortToast("Both passwords must be the same.");
		binding.inputConfirmPassword.setText("");
		return false;
	}
	return true;

}

private void loading( Boolean isLoading ) {
	if ( isLoading ) {
		binding.signUpButton.setVisibility(View.INVISIBLE);
		binding.progressBar.setVisibility(View.VISIBLE);
	} else {
		binding.signUpButton.setVisibility(View.VISIBLE);
		binding.progressBar.setVisibility(View.INVISIBLE);
	}

}

protected void onDestroy( ) {
	internetBroadcastHelper.unregisterInternetBroadcast( );
	super.onDestroy( );
}
}