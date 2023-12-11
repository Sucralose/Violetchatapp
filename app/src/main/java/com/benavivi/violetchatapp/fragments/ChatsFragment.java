package com.benavivi.violetchatapp.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.benavivi.violetchatapp.activities.ConversationActivity;
import com.benavivi.violetchatapp.adapters.GroupListAdapter.ChatsListRecycleViewAdapter;
import com.benavivi.violetchatapp.adapters.GroupListAdapter.RecyclerViewOnClickInterface;
import com.benavivi.violetchatapp.dataModels.Group;
import com.benavivi.violetchatapp.dataModels.Message;
import com.benavivi.violetchatapp.databinding.FragmentChatsBinding;
import com.benavivi.violetchatapp.utilities.FirebaseManager;
import com.benavivi.violetchatapp.utilities.IntentGroupSwitcher;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ChatsFragment extends Fragment implements RecyclerViewOnClickInterface {
	FragmentChatsBinding binding;
	ArrayList<Group> groupsArrayList;
	ChatsListRecycleViewAdapter chatsListRecycleViewAdapter;

	public ChatsFragment () {
		// Required empty public constructor
	}


	@Override
	public View onCreateView (LayoutInflater inflater, ViewGroup container,
			      Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		binding = FragmentChatsBinding.inflate(inflater, container, false);
		groupsArrayList = new ArrayList<>();


		setUpRecyclerView();
		setRecyclerViewAdapter();


		return binding.getRoot();
	}


	private void setRecyclerViewAdapter () {
		ChatsListRecycleViewAdapter adapter = FirebaseManager.getUserGroupsDetailsAdapter(getContext());
		binding.chatsRecyclerView.setAdapter(adapter);
		adapter.startListening();
	}


	private void setUpRecyclerView () {
		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
		binding.chatsRecyclerView.setLayoutManager(linearLayoutManager);
		binding.chatsRecyclerView.setHasFixedSize(true);
	}

	private void showShortToast (String message) {
		Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onRecyclerViewItemClick (int position) {
			Intent intent = IntentGroupSwitcher.switchToIntent(getContext(), ConversationActivity.class, groupsArrayList.get(position));
			startActivity(intent);
	}
}