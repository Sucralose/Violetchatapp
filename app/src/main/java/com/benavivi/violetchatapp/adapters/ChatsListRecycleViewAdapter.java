package com.benavivi.violetchatapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.benavivi.violetchatapp.R;
import com.benavivi.violetchatapp.dataModels.Group;

import java.util.ArrayList;

public class ChatsListRecycleViewAdapter extends RecyclerView.Adapter<GroupViewHolder> implements RecyclerViewOnClickInterface {
	private final Context context;
	private final RecyclerViewOnClickInterface recyclerViewInterface;
	ArrayList<Group> groupsArrayList;

	public ChatsListRecycleViewAdapter (Context context, ArrayList<Group> groupsArrayList, RecyclerViewOnClickInterface recyclerViewInterface) {
		this.context = context;
		this.groupsArrayList = groupsArrayList;
		this.recyclerViewInterface = recyclerViewInterface;
	}
	@NonNull
	@Override
	public GroupViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
		LayoutInflater layoutInflater = LayoutInflater.from(context);
		View view = layoutInflater.inflate(R.layout.layoutgroupitem, parent, false);
		return new GroupViewHolder(view, recyclerViewInterface);
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


	@Override
	public void onRecyclerViewItemClick (int position) {

	}
}
