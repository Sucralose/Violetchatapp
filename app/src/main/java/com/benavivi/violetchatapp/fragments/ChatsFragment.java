package com.benavivi.violetchatapp.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.benavivi.violetchatapp.activities.ConversationActivity;
import com.benavivi.violetchatapp.adapters.ChatsListRecycleViewAdapter;
import com.benavivi.violetchatapp.adapters.RecyclerViewOnClickInterface;
import com.benavivi.violetchatapp.dataModels.Group;
import com.benavivi.violetchatapp.databinding.FragmentChatsBinding;
import com.benavivi.violetchatapp.utilities.Constants;
import com.benavivi.violetchatapp.utilities.FirebaseManager;
import com.benavivi.violetchatapp.utilities.IntentGroupSwitcher;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ChatsFragment extends Fragment implements RecyclerViewOnClickInterface {
	FragmentChatsBinding binding;
	ArrayList<Group> groupsArrayList;

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
		gatherDataFromFirebase();



		return binding.getRoot();
	}

	private void gatherDataFromFirebase () {
		FirebaseManager.getUserGroupsDetails()
			.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
				@Override
				public void onComplete (Task<QuerySnapshot> task) {
					if(task.isSuccessful()){
						task.getResult().getDocuments().forEach(documentSnapshot -> {
							addGroupDetailsFromDocumentSnapshot(documentSnapshot);
						});
						setRecyclerViewAdapter(groupsArrayList);

					}
				}


			});
	}

	private void setRecyclerViewAdapter (ArrayList<Group> groupsArrayList) {
		binding.chatsRecyclerView.setAdapter(new ChatsListRecycleViewAdapter(getContext(), groupsArrayList, this));
	}

	private void addGroupDetailsFromDocumentSnapshot (DocumentSnapshot documentSnapshot) {
		Group group = new Group(documentSnapshot.getData(), documentSnapshot.getReference().getId());
		groupsArrayList.add(group);
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