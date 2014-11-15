package com.example.gui;


import java.util.Locale;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.viewpagerindicator.CirclePageIndicator;

public class MainActivity extends FragmentActivity{
	
	CirclePageIndicator mIndicator;
	TextToSpeech ttobj;
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
        ViewPager pager = (ViewPager) findViewById(R.id.viewPager);
        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));

//        getActionBar().hide();
        mIndicator = (CirclePageIndicator) findViewById(R.id.indicator);
        mIndicator.setViewPager(pager);
        ttobj=new TextToSpeech(getApplicationContext(), 
        	      new TextToSpeech.OnInitListener() {
        	      @Override
        	      public void onInit(int status) {
        	         if(status != TextToSpeech.ERROR){
        	             ttobj.setLanguage(Locale.UK);
        	            }				
        	         }
        	      });
       	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		MenuItem item = menu.findItem(R.id.action_cart);
		item.setVisible(false);
		getActionBar().hide();
	
		TextView logintxt = (TextView) findViewById(R.id.login);
		Typeface font = Typeface.createFromAsset(getAssets(), "myriadpro.otf");
		logintxt.setTypeface(font);
		logintxt.setTextColor(Color.WHITE);
		TextView signuptxt = (TextView) findViewById(R.id.signup);
		signuptxt.setTypeface(font);
		signuptxt.setTextColor(Color.WHITE);
		 ttobj.speak("Welcome to Voice Buy, the smarter way of online shopping. Swipe right on the screen to pick a store.", TextToSpeech.QUEUE_FLUSH, null);

		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private class MyPagerAdapter extends FragmentPagerAdapter {

		
        public MyPagerAdapter(FragmentManager fm) {
            
        	super(fm);

//        	new RetrieveBrandsTask(MainActivity.this).execute();
        }

        @Override
        public Fragment getItem(int pos) {
            switch(pos) {

            case 0: return HomeScreen.newInstance("HomeScreen");
            case 1: return StoresList.newInstance("StoresList");
            default: return HomeScreen.newInstance("HomeScreen");
            }
        }
        @Override
        public int getCount() {
            return 2;
        }
        
        
    }
        
}