package com.benavivi.violetchatapp.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.benavivi.violetchatapp.activities.SignInActivity;
import com.benavivi.violetchatapp.databinding.FragmentSettingsBinding;
import com.benavivi.violetchatapp.utilities.FirebaseManager;

public class SettingsFragment extends Fragment {
	private FragmentSettingsBinding binding;

	@Override
	public void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onViewCreated (@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		setListeners();
		personalizeTextAndImage();
	}

	private void personalizeTextAndImage () {
		binding.usernameTextview.setText(FirebaseManager.getUserDisplayName());
		//binding.profileImage.setImageURI(FirebaseManager.getUserUriImage()); //<-- TODO: Fix permission to get image URI or find a way around it
	}

	private void setListeners () {
		binding.signOutButton.setOnClickListener(view -> {
			FirebaseManager.signOut();
			Intent returnToSignInIntent = new Intent(getContext(), SignInActivity.class);
			startActivity(returnToSignInIntent);
		});
	}

	@Override
	public View onCreateView (LayoutInflater inflater, ViewGroup container,
			      Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		//return inflater.inflate(R.layout.fragment_settings, container, false);
		binding = FragmentSettingsBinding.inflate(inflater,container,false);
		return binding.getRoot();
	}
}