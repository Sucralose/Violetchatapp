package com.benavivi.violetchatapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.benavivi.violetchatapp.adapters.ChatsListRecycleViewAdapter;
import com.benavivi.violetchatapp.dataModels.Group;
import com.benavivi.violetchatapp.databinding.FragmentChatsBinding;
import com.benavivi.violetchatapp.utilities.FirebaseManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ChatsFragment extends Fragment {
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

		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
		binding.chatsRecyclerView.setLayoutManager(linearLayoutManager);
		binding.chatsRecyclerView.setHasFixedSize(true);
		binding.chatsRecyclerView.setAdapter(new ChatsListRecycleViewAdapter(getContext(), groupsArrayList));

		FirebaseManager.getUserGroupsDetails()
			.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
				@Override
				public void onComplete (Task<QuerySnapshot> task) {
					if(task.isSuccessful()){
						task.getResult().getDocuments().forEach(documentSnapshot -> {
							Group group = new Group(documentSnapshot.getData(), documentSnapshot.getReference().getId());
							groupsArrayList.add(group);
							showShortToast("Added " + documentSnapshot.getData());
						});
						binding.chatsRecyclerView.setAdapter(new ChatsListRecycleViewAdapter(getContext(), groupsArrayList));

					}
				}
			});




		return binding.getRoot();
	}

	private void showShortToast (String message) {
		Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
	}
}