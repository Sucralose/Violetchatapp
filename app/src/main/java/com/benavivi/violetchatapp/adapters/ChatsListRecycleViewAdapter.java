package com.benavivi.violetchatapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.benavivi.violetchatapp.R;
import com.benavivi.violetchatapp.dataModels.Group;

import java.util.ArrayList;

public class ChatsListRecycleViewAdapter extends RecyclerView.Adapter<GroupViewHolder> {
	private final Context context;
	ArrayList<Group> groupsArrayList;

	public ChatsListRecycleViewAdapter (Context context, ArrayList<Group> groupsArrayList) {
		this.context = context;
		this.groupsArrayList = groupsArrayList;
	}
	@NonNull
	@Override
	public GroupViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
		LayoutInflater layoutInflater = LayoutInflater.from(context);
		View view = layoutInflater.inflate(R.layout.layoutgroupitem, parent, false);
		return new GroupViewHolder(view);
	}

	@Override
	public void onBindViewHolder (@NonNull GroupViewHolder holder, int position) {
		holder.groupName.setText(groupsArrayList.get(position).getChatName());
		holder.groupLastMessage.setText(groupsArrayList.get(position).getLastMessage().getFormatedMessage());
		holder.groupLastmessageTime.setText(groupsArrayList.get(position).getLastMessage().getFormatedDate());



	}
	public Object getItem (int i) {
		return groupsArrayList.get(i);
	}

	@Override
	public int getItemCount () {
		return groupsArrayList.size();
	}


}
