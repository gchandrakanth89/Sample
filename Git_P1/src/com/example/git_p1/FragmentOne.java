package com.example.git_p1;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentOne extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.frag_layout1, container, false);
		
		ActionBar mActionBar=((AppCompatActivity) getActivity()).getSupportActionBar();
		mActionBar.setTitle("FragOne");
		return view;
	}
}
