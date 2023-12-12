package com.benavivi.violetchatapp.fragments;

import static com.benavivi.violetchatapp.utilities.Constants.FirebaseConstants.KEY_USER_DISPLAY_NAME;
import static com.benavivi.violetchatapp.utilities.Constants.FirebaseConstants.KEY_USER_PROFILE_IMAGE;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.benavivi.violetchatapp.activities.SignInActivity;
import com.benavivi.violetchatapp.databinding.FragmentSettingsBinding;
import com.benavivi.violetchatapp.utilities.FirebaseManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.squareup.picasso.Picasso;

import java.util.Map;

public class SettingsFragment extends Fragment {
private FragmentSettingsBinding binding;

@Override
public void onCreate ( Bundle savedInstanceState ) {
	super.onCreate(savedInstanceState);
}

@Override
public void onViewCreated ( @NonNull View view, @Nullable Bundle savedInstanceState ) {
	super.onViewCreated(view, savedInstanceState);
	setListeners();
	personalizeTextAndImage();
}

private void personalizeTextAndImage ( ) {
	FirebaseManager.getCurrentUserData()
		.addOnCompleteListener(
			new OnCompleteListener<DocumentSnapshot>() {
				@Override
				public void onComplete ( @NonNull Task<DocumentSnapshot> task ) {
					if ( task.isSuccessful() ) {
						Map<String,Object> userData = task.getResult().getData();
						if ( !userData.get(KEY_USER_PROFILE_IMAGE).toString().isEmpty() )
							Picasso.get().load(userData.get(KEY_USER_PROFILE_IMAGE).toString()).into(binding.profileImage);
						binding.usernameTextview.setText(userData.get(KEY_USER_DISPLAY_NAME).toString());

					}
				}
			});
}

private void setListeners ( ) {
	binding.signOutButton.setOnClickListener(view -> {
		FirebaseManager.signOut();
		Intent returnToSignInIntent = new Intent(getContext(), SignInActivity.class);
		startActivity(returnToSignInIntent);
	});
}

@Override
public View onCreateView ( LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState ) {
	super.onCreateView(inflater, container, savedInstanceState);
	binding = FragmentSettingsBinding.inflate(inflater, container, false);


	return binding.getRoot();
}
}