package com.benavivi.violetchatapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.benavivi.violetchatapp.R;
import com.benavivi.violetchatapp.databinding.ActivityMainBinding;
import com.benavivi.violetchatapp.fragments.ChatsFragment;
import com.benavivi.violetchatapp.fragments.ContactsFragment;
import com.benavivi.violetchatapp.fragments.GroupsFragment;
import com.benavivi.violetchatapp.fragments.RequestsFragment;
import com.benavivi.violetchatapp.fragments.SettingsFragment;

public class MainActivity extends AppCompatActivity {

	private ActivityMainBinding binding;
	@Override
	protected void onStart () {
		super.onStart();
	}


	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		binding = ActivityMainBinding.inflate(getLayoutInflater());
		setContentView(binding.getRoot());

		setListeners();
		replaceFragments(new ChatsFragment());

	}

	private void setListeners () {
		binding.mainBottomNavigationView.setOnItemSelectedListener(item ->{
			//Switch requires constant values, therefore I have to use if else.
			if(item.getItemId() == R.id.chatsNavbarMenuItem)
				replaceFragments(new ChatsFragment());

			if(item.getItemId() == R.id.groupsNavbarMenuItem)
				replaceFragments(new GroupsFragment());

			if(item.getItemId() == R.id.contactsNavbarMenuItem)
				replaceFragments(new ContactsFragment());

			if(item.getItemId() == R.id.requestsNavbarMenuItem)
				replaceFragments(new RequestsFragment());

			if(item.getItemId() == R.id.settingsNavbarMenuItem)
				replaceFragments(new SettingsFragment());

			return true;
		});
	}

	private void replaceFragments(Fragment fragment){
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.replace(R.id.mainFragmentViewLayout,fragment);
		fragmentTransaction.commit();


	}


}