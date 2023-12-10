package com.benavivi.violetchatapp.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.benavivi.violetchatapp.databinding.FragmentGroupsBinding;

import java.util.ArrayList;

public class GroupsFragment extends Fragment {

	private FragmentGroupsBinding binding;

	private ArrayAdapter<String> arrayAdapter;
	private ArrayList<String> groupsList = new ArrayList<>();

	public GroupsFragment () {
		// Required empty public constructor
	}


	@Override
	public void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}


	@Override
	public View onCreateView (LayoutInflater inflater, ViewGroup container,
			      Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		binding = FragmentGroupsBinding.inflate(inflater,container,false);
		return binding.getRoot();
	}
}