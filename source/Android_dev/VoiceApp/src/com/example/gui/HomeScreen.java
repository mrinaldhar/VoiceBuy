package com.example.gui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class HomeScreen extends Fragment {
		Button log,sign;
		View logv;
	   @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	        View v = inflater.inflate(R.layout.activity_home_screen, container, false);
	        Spinner spin_gender = (Spinner) v.findViewById(R.id.gender);
	        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(getActivity(),
	                R.array.gender_array, android.R.layout.simple_spinner_item);
	        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	        spin_gender.setAdapter(adapter1);
	        AutoCompleteTextView textView = (AutoCompleteTextView) v.findViewById(R.id.autocomplete_country);
	     // Get the string array
	     String[] countries = getResources().getStringArray(R.array.countries_array);
	     // Create the adapter and set it to the AutoCompleteTextView 
	     ArrayAdapter<String> adapter2 = 
	             new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, countries);
	     textView.setAdapter(adapter2);
	     log=(Button) v.findViewById(R.id.login);
	     logv=v.findViewById(R.id.login);
	     View log1 = (View)log;
	     sign=(Button) v.findViewById(R.id.signup);
	     log.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				
			}
		});
	     sign.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					
				}
			});
	     
	     return v;
	        
	    }

	    public static HomeScreen newInstance(String text) {

	    	HomeScreen home = new HomeScreen();
	    	

	        return home;
	    }
	    public void display_signup(View v){
	    	
	    }
	    public void display_login(View v){
	    	
	    }
	}