package com.benavivi.violetchatapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.benavivi.violetchatapp.adapters.ConversationRecyclerViewAdapter;
import com.benavivi.violetchatapp.dataModels.Group;
import com.benavivi.violetchatapp.databinding.ActivityConversationBinding;
import com.benavivi.violetchatapp.utilities.FirebaseManager;
import com.benavivi.violetchatapp.utilities.IntentFactory;

public class ConversationActivity extends AppCompatActivity {
ActivityConversationBinding binding;
ConversationRecyclerViewAdapter adapter;
LinearLayoutManager linearLayoutManager;
Group currentGroup;


@Override
protected void onCreate ( Bundle savedInstanceState ) {
	super.onCreate(savedInstanceState);
	binding = ActivityConversationBinding.inflate(getLayoutInflater());
	setContentView(binding.getRoot());

	currentGroup = IntentFactory.IntentToGroup(getIntent());
	setRecyclerView();

	binding.conversationTitle.setText(currentGroup.getName());
	setListeners();

}

protected void onStop ( ) {
	super.onStop();
	if ( adapter != null )
		adapter.stopListening();
}

protected void onStart ( ) {
	super.onStart();
	if ( adapter != null )
		adapter.startListening();
}


private void setRecyclerView ( ) {
	adapter = FirebaseManager.getConversationAdapter(this, currentGroup.getChatID());
	linearLayoutManager = new LinearLayoutManager(this);
	linearLayoutManager.setReverseLayout(true);


	binding.conversationRecyclerView.setHasFixedSize(true);
	binding.conversationRecyclerView.setLayoutManager(linearLayoutManager);

	binding.conversationRecyclerView.setAdapter(adapter);
	adapter.startListening();

}

private boolean messageValidated ( ) {
	return !binding.conversationEditText.getText().toString().isEmpty();
}

private void setListeners ( ) {
	binding.conversationBack.setOnClickListener(new View.OnClickListener() {
		@Override
		public void onClick ( View view ) {
			Intent intent = new Intent(ConversationActivity.this, MainActivity.class);
			if ( adapter != null ) adapter.stopListening();
			startActivity(intent);

		}
	});

	binding.conversationSendMessageButton.setOnClickListener(new View.OnClickListener() {
		@Override
		public void onClick ( View view ) {
			if ( messageValidated() ) {
				sendMessage();
				clearInput();
			}
		}
	});


	adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
		@Override
		public void onItemRangeInserted ( int positionStart, int itemCount ) {
			linearLayoutManager.smoothScrollToPosition(binding.conversationRecyclerView, null, 0);
		}
	});
}

private void clearInput ( ) {
	binding.conversationEditText.setText("");
}

private void sendMessage ( ) {
	String message = binding.conversationEditText.getText().toString();
	FirebaseManager.sendMessage(message, currentGroup.getChatID());
}
}
