package com.benavivi.violetchatapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.benavivi.violetchatapp.R;
import com.benavivi.violetchatapp.activities.ConversationActivity;
import com.benavivi.violetchatapp.dataModels.Group;
import com.benavivi.violetchatapp.databinding.LayoutgroupitemBinding;
import com.makeramen.roundedimageview.RoundedImageView;

public class GroupViewHolder extends RecyclerView.ViewHolder {



	RoundedImageView roundedImageView;
	TextView groupName,groupLastMessage,groupLastmessageTime;

	public GroupViewHolder (@NonNull View itemView , RecyclerViewOnClickInterface recyclerViewOnClickInterface) {
		super(itemView);
		roundedImageView = itemView.findViewById(R.id.layoutgroup_groupImage);
		groupName = itemView.findViewById(R.id.layoutgroup_groupName);
		groupLastMessage = itemView.findViewById(R.id.layoutgroup_lastMessage);
		groupLastmessageTime = itemView.findViewById(R.id.layoutgroup_lastMessageTime);


		itemView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick (View view) {
				Intent intent = new Intent(view.getContext(), ConversationActivity.class);
				int pos = getAdapterPosition();
				if(recyclerViewOnClickInterface != null || pos == RecyclerView.NO_POSITION)
					return;
				else
				recyclerViewOnClickInterface.onRecyclerViewItemClick(pos);

			}
		});

	}


	public void setGroup(Context context, Group group) {

		LayoutInflater inflater = LayoutInflater.from(context);
		LayoutgroupitemBinding binding = LayoutgroupitemBinding.inflate(inflater);
		binding.layoutgroupGroupName.setText(group.getChatName());
		binding.layoutgroupLastMessage.setText(group.getLastMessage().getFormatedMessage());
		binding.layoutgroupLastMessageTime.setText(group.getLastMessage().getFormatedDate());

	}


}
