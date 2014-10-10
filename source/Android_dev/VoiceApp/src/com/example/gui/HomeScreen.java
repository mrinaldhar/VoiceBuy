package com.example.gui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class HomeScreen extends Fragment {

	   @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	        View v = inflater.inflate(R.layout.activity_home_screen, container, false);
	        
	        return v;
	    }

	    public static HomeScreen newInstance(String text) {

	    	HomeScreen home = new HomeScreen();

	        return home;
	    }
	}