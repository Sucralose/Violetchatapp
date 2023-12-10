package com.benavivi.violetchatapp.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.benavivi.violetchatapp.databinding.FragmentChatsBinding;

import java.util.ArrayList;

public class ChatsFragment extends Fragment {
	FragmentChatsBinding binding;
	ArrayList<String> chatIds;

	public ChatsFragment () {
		// Required empty public constructor
	}


	@Override
	public View onCreateView (LayoutInflater inflater, ViewGroup container,
			      Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		binding = FragmentChatsBinding.inflate(inflater, container, false);

		chatIds = new ArrayList<>();



		return binding.getRoot();
	}
}