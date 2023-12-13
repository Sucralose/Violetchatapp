package com.benavivi.violetchatapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.benavivi.violetchatapp.adapters.ChatsListRecycleViewAdapter;
import com.benavivi.violetchatapp.databinding.FragmentChatsBinding;
import com.benavivi.violetchatapp.utilities.FirebaseManager;
import com.benavivi.violetchatapp.utilities.LinearLayoutManagerWrapper;

public class ChatsFragment extends Fragment {
FragmentChatsBinding binding;
ChatsListRecycleViewAdapter adapter;

public ChatsFragment ( ) {
	// Required empty public constructor
}


@Override
public View onCreateView ( LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState ) {
	// Inflate the layout for this fragment
	binding = FragmentChatsBinding.inflate(inflater, container, false);

	setUpRecyclerView();


	return binding.getRoot();
}


private void setUpRecyclerView ( ) {
	LinearLayoutManager linearLayoutManager = new LinearLayoutManagerWrapper(getContext());
	binding.chatsRecyclerView.setLayoutManager(linearLayoutManager);
	binding.chatsRecyclerView.setHasFixedSize(true);

	adapter = FirebaseManager.getUserGroupsDetailsAdapter(getContext());
	binding.chatsRecyclerView.setAdapter(adapter);
	adapter.startListening();
}

public void onStart ( ) {
	super.onStart();
	if ( adapter != null )
		adapter.startListening();
}

public void onStop ( ) {
	super.onStop();
	if ( adapter != null )
		adapter.stopListening();
}

}