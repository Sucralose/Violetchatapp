package com.benavivi.violetchatapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.benavivi.violetchatapp.R;
import com.benavivi.violetchatapp.activities.ConversationActivity;
import com.benavivi.violetchatapp.dataModels.Group;
import com.benavivi.violetchatapp.utilities.IntentFactory;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;


//Firestore Recycler Adapter is a builtin adapter that allows to listen for changes in the database (for a given query)
public class ChatsListRecycleViewAdapter extends FirestoreRecyclerAdapter<Group, ChatsListRecycleViewAdapter.GroupViewHolder> {
private final Context context;

public ChatsListRecycleViewAdapter( FirestoreRecyclerOptions<Group> options, Context context ) {
	super(options);
	this.context = context;
}

@NonNull
@Override
public GroupViewHolder onCreateViewHolder( @NonNull ViewGroup parent, int viewType ) {
	LayoutInflater layoutInflater = LayoutInflater.from(context);
	View view = layoutInflater.inflate(R.layout.layout_group_item, parent, false);
	return new GroupViewHolder(view);
}


@Override
protected void onBindViewHolder( @NonNull GroupViewHolder holder, int position, @NonNull Group model ) {

	holder.groupName.setText(model.getName( ));
	holder.groupLastMessage.setText(model.getLastMessage( ).getFormattedMessage( ));
	holder.groupLastMessageTime.setText(model.getLastMessage( ).getFormattedDate( ));
	if ( !model.getImageURL( ).isEmpty( ) )
		Picasso.get( ).load(model.getImageURL( )).into(holder.roundedImageView);
	holder.itemView.setOnClickListener(new View.OnClickListener( ) {
		@Override
		public void onClick( View v ) {
			Intent intent = IntentFactory.GroupToIntent(context, ConversationActivity.class, model);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(intent);
		}
	});
}


static class GroupViewHolder extends RecyclerView.ViewHolder {
	ShapeableImageView roundedImageView;
	TextView groupName, groupLastMessage, groupLastMessageTime;

	public GroupViewHolder( @NonNull View itemView ) {
		super(itemView);
		roundedImageView = itemView.findViewById(R.id.layoutgroup_groupImage);
		groupName = itemView.findViewById(R.id.layoutgroup_groupName);
		groupLastMessage = itemView.findViewById(R.id.layoutgroup_lastMessage);
		groupLastMessageTime = itemView.findViewById(R.id.layoutgroup_lastMessageTime);

	}


}


}
