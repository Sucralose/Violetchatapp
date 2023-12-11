package com.benavivi.violetchatapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import com.benavivi.violetchatapp.dataModels.Group;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.benavivi.violetchatapp.databinding.ActivityConversationBinding;
import com.benavivi.violetchatapp.utilities.IntentGroupSwitcher;

public class ConversationActivity extends AppCompatActivity {
	ActivityConversationBinding binding;
	Group currentGroup;
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		binding = ActivityConversationBinding.inflate(getLayoutInflater());
		setContentView(binding.getRoot());

		 currentGroup = IntentGroupSwitcher.switchToGroup(getIntent());

		binding.conversationTitle.setText(currentGroup.getName());
		binding.conversationBack.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick (View view) {
				Intent intent = new Intent(ConversationActivity.this, MainActivity.class);
				startActivity(intent);
			}
		});

	}
}