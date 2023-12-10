package com.benavivi.violetchatapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.benavivi.violetchatapp.R;
import com.benavivi.violetchatapp.dataModels.Group;
import com.benavivi.violetchatapp.dataModels.Message;
import com.benavivi.violetchatapp.databinding.LayoutgroupitemBinding;
import com.makeramen.roundedimageview.RoundedImageView;

public class GroupViewHolder extends RecyclerView.ViewHolder {

	RoundedImageView roundedImageView;
	TextView groupName,groupLastMessage,groupLastmessageTime;

	public GroupViewHolder (@NonNull View itemView) {
		super(itemView);
		roundedImageView = itemView.findViewById(R.id.layoutgroup_groupImage);
		groupName = itemView.findViewById(R.id.layoutgroup_groupName);
		groupLastMessage = itemView.findViewById(R.id.layoutgroup_lastMessage);
		groupLastmessageTime = itemView.findViewById(R.id.layoutgroup_lastMessageTime);
	}


	public void setGroup(Context context, Group group) {

		LayoutInflater inflater = LayoutInflater.from(context);
		LayoutgroupitemBinding binding = LayoutgroupitemBinding.inflate(inflater);
		binding.layoutgroupGroupName.setText(group.getChatName());
		binding.layoutgroupLastMessage.setText(group.getLastMessage().getFormatedMessage());
		binding.layoutgroupLastMessageTime.setText(group.getLastMessage().getFormatedDate());

	}
}
