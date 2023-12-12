package com.benavivi.violetchatapp.adapters.GroupListAdapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.benavivi.violetchatapp.R;
import com.benavivi.violetchatapp.activities.ConversationActivity;
import com.benavivi.violetchatapp.dataModels.Group;
import com.benavivi.violetchatapp.utilities.FirebaseManager;
import com.benavivi.violetchatapp.utilities.IntentGroupSwitcher;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ChatsListRecycleViewAdapter extends FirestoreRecyclerAdapter<Group, GroupViewHolder> {
	private final Context context;
	ArrayList<Group> groupsArrayList;

	public ChatsListRecycleViewAdapter (FirestoreRecyclerOptions<Group> options, Context context) {
		super(options);
		this.context = context;
	}
	@NonNull
	@Override
	public GroupViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
		LayoutInflater layoutInflater = LayoutInflater.from(context);
		View view = layoutInflater.inflate(R.layout.layoutgroupitem, parent, false);
		return new GroupViewHolder(view);
	}


	@Override
	protected void onBindViewHolder (@NonNull GroupViewHolder holder, int position, @NonNull Group model) {

		holder.groupName.setText(model.getName());
		holder.groupLastMessage.setText(model.getLastMessage().getFormatedMessage());
		holder.groupLastmessageTime.setText(model.getLastMessage().getFormatedDate());
		Picasso.get().load(model.getImageURL()).into(holder.roundedImageView);
		holder.itemView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick (View v) {
				Intent intent = IntentGroupSwitcher.switchToIntent(context, ConversationActivity.class,model);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(intent);
			}
		});
	}



}
