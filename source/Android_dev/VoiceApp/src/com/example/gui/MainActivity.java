package com.example.gui;


import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends FragmentActivity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
        ViewPager pager = (ViewPager) findViewById(R.id.viewPager);
        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));

	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		MenuItem item = menu.findItem(R.id.action_cart);
		item.setVisible(false);
		
		TextView logintxt = (TextView) findViewById(R.id.login);
		Typeface font = Typeface.createFromAsset(getAssets(), "myriadpro.otf");
		logintxt.setTypeface(font);
		logintxt.setTextColor(Color.WHITE);
		TextView signuptxt = (TextView) findViewById(R.id.signup);
		signuptxt.setTypeface(font);
		signuptxt.setTextColor(Color.WHITE);
	
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