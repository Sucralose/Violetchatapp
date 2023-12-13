package com.benavivi.violetchatapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.benavivi.violetchatapp.R;
import com.benavivi.violetchatapp.dataModels.Message;
import com.benavivi.violetchatapp.utilities.FirebaseManager;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

public class ConversationRecyclerViewAdapter extends FirestoreRecyclerAdapter<Message,ConversationRecyclerViewAdapter.MessageViewHolder> {

private final Context context;
private final String currentUserUid = FirebaseManager.getCurrentUserUid();

public ConversationRecyclerViewAdapter ( @NonNull FirestoreRecyclerOptions<Message> options, Context context ) {
	super(options);
	this.context = context;
}

@Override
protected void onBindViewHolder ( @NonNull ConversationRecyclerViewAdapter.MessageViewHolder holder, int position, @NonNull Message model ) {

	if ( model.getSenderID().equals(currentUserUid) ) { //The author is the user
		holder.userMessageText.setText(model.getMessageText());
		holder.userMessageTime.setText(model.getFormatedDate());
		holder.userMessageFrame.setVisibility(View.VISIBLE);
		holder.senderMessageFrame.setVisibility(View.INVISIBLE);
	} else { //The author is not the user
		holder.senderMessageText.setText(model.getMessageText());
		holder.senderMessageTime.setText(model.getFormatedDate());
		holder.senderName.setText(model.getSenderName());
		holder.senderMessageFrame.setVisibility(View.VISIBLE);
		holder.userMessageFrame.setVisibility(View.INVISIBLE);
		Picasso.get().load(model.getSenderImageURL()).into(holder.senderImage);
	}
}

@NonNull
@Override
public ConversationRecyclerViewAdapter.MessageViewHolder onCreateViewHolder ( @NonNull ViewGroup parent, int viewType ) {
	LayoutInflater layoutInflater = LayoutInflater.from(context);
	View view = layoutInflater.inflate(R.layout.layout_message_item, parent, false);
	return new ConversationRecyclerViewAdapter.MessageViewHolder(view);
}

static class MessageViewHolder extends RecyclerView.ViewHolder {
	RoundedImageView senderImage;
	TextView senderName, senderMessageText, senderMessageTime, userMessageText, userMessageTime;
	FrameLayout senderMessageFrame, userMessageFrame;

	public MessageViewHolder ( @NonNull View itemView ) {
		super(itemView);
		senderImage = itemView.findViewById(R.id.recievedMessageProfileImage);
		senderMessageText = itemView.findViewById(R.id.recievedMessageText);
		senderName = itemView.findViewById(R.id.recievedMessageSenderName);
		senderMessageTime = itemView.findViewById(R.id.recievedMessageDate);

		userMessageText = itemView.findViewById(R.id.sentMessageText);
		userMessageTime = itemView.findViewById(R.id.sentMessageDate);

		userMessageFrame = itemView.findViewById(R.id.sentMessageFrame);
		senderMessageFrame = itemView.findViewById(R.id.recievedMessageFrame);
	}


}

}

