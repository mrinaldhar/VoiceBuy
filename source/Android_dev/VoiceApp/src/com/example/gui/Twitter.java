package com.example.gui;

import android.app.Activity;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.FragmentTransaction;

public class Twitter extends Activity {
	  SharedPreferences pref;
	    private static String CONSUMER_KEY = "Hy30sjRzcwwKAkwVvD3gvjXus";
	    private static String CONSUMER_SECRET = "vzex5j4iqWwpNjDvofDl9DYQbTTqLhQCfFP86Qf4VEWE8HKvgf";
	  @Override
	  protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_twitter);
	        pref = getPreferences(0);
	        SharedPreferences.Editor edit = pref.edit();
	        edit.putString("CONSUMER_KEY", CONSUMER_KEY);
	        edit.putString("CONSUMER_SECRET", CONSUMER_SECRET);
	        edit.commit();
	        Fragment login = new LoginFragment();
	        FragmentTransaction ft = getFragmentManager().beginTransaction();
	        ft.replace(R.id.content_frame, login);
	        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
	        ft.addToBackStack(null);
	        ft.commit();
	  }
	}
