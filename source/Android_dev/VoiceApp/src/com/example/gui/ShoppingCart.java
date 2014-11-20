package com.example.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import model.Movie;
import adapter.CustomListAdapter;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class ShoppingCart extends Activity {


	 public  static List<Movie> itemList = new ArrayList<Movie>();
	 private ListView cart;
	 private CustomListAdapter adapter;
	 TextToSpeech ttobj;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shopping_cart);
		Button btn = (Button) findViewById(R.id.buy);
		cart = (ListView) findViewById(R.id.cart_list);
		  adapter = new CustomListAdapter(this, itemList);
		  cart.setAdapter(adapter);
		  Button buy = (Button)findViewById(R.id.buy);

		  buy.setTextColor(Color.WHITE);
		  Typeface font = Typeface.createFromAsset(getAssets(), "Roboto-ThinItalic.ttf");
			buy.setTypeface(font);
			
			ttobj=new TextToSpeech(getApplicationContext(), 
				      new TextToSpeech.OnInitListener() {
				      @Override
				      public void onInit(int status) {
				         if(status != TextToSpeech.ERROR){
				             ttobj.setLanguage(Locale.UK);
				            }				
				         }
				      });

			
		  cart.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
			  
				@Override
			       public void onItemClick(AdapterView<?> a, View v, int position,
			               long id) {
					itemList.remove(position);
					adapter.notifyDataSetChanged();
			           }
			   });

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.shopping_cart, menu);
		return true;
	}
	
	public void myfunc(View view) {
		ttobj.speak("I'm really thankful for this opportunity to talk. Rarely do we apps ever get to speak for ourselves. Ha Ha. I now invite Karthik to finalize the presentation." , TextToSpeech.QUEUE_FLUSH, null);
		showToastMessage("I'm really thankful for this opportunity to talk.");
		
		showToastMessage("Rarely do we apps ever get to speak for ourselves.");
		showToastMessage("Ha ha.");
		showToastMessage("I now invite Karthik to finalize the presentation.");
		
	}
	public void showToastMessage(String message){
		  Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
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
}