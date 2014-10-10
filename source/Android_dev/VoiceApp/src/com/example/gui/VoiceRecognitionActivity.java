package com.example.gui;
 
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
 
public class VoiceRecognitionActivity extends Activity {
 private static final int VOICE_RECOGNITION_REQUEST_CODE = 1001;
 
 private EditText metTextSearch;
 private ImageButton mbtSpeak;
 private TextView searchresults;
 
 @Override
 public void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.activity_voice_recognition);
	
  Bundle extras = getIntent().getExtras();
	String value = new String();
  if (extras != null){
		value = extras.getString("getstore");
	}
	ActionBar action = getActionBar();
	action.setTitle(value + " Products");
  metTextSearch = (EditText) findViewById(R.id.search_bar);
  mbtSpeak = (ImageButton) findViewById(R.id.btSpeak);
  searchresults = (TextView) findViewById(R.id.searchresults);
  checkVoiceRecognition();

 metTextSearch.setOnEditorActionListener(
		    new EditText.OnEditorActionListener() {
		@Override
		public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		    if (actionId == EditorInfo.IME_ACTION_SEARCH ||
		            actionId == EditorInfo.IME_ACTION_DONE ||
		            event.getAction() == KeyEvent.ACTION_DOWN &&
		            event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
		        if (!event.isShiftPressed()) {
		           // the user is done typing. 
		        	getsearchres(metTextSearch.getText().toString());
		           return true; // consume.
		        }                
		    }
		    return false; // pass on to other listeners. 
		}
		});
 }
 
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);

		return true;
	}
 
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_cart) {
			Intent intent = new Intent(getApplicationContext(), ShoppingCart.class);
	        startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
 public String loadJSONFromAsset() {
	    String json = null;
	    try {

	        InputStream is = getAssets().open("localjson.json");

	        int size = is.available();

	        byte[] buffer = new byte[size];

	        is.read(buffer);

	        is.close();

	        json = new String(buffer, "UTF-8");


	    } catch (IOException ex) {
	        ex.printStackTrace();
	        return null;
	    }
	    return json;

	}
 
 public void checkVoiceRecognition() {
  // Check if voice recognition is present
  PackageManager pm = getPackageManager();
  List<ResolveInfo> activities = pm.queryIntentActivities(new Intent(
    RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
  if (activities.size() == 0) {
   mbtSpeak.setEnabled(false);
   Toast.makeText(this, "Voice recognizer not present",
     Toast.LENGTH_SHORT).show();
  }
 }
 
 public void speak(View view) {
  Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
 
  // Specify the calling package to identify your application
  intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getClass()
    .getPackage().getName());
 
  // Display an hint to the user about what he should say.
  intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Search For Items Or Say a Command");
 
  // Given an hint to the recognizer about what the user is going to say
  //There are two form of language model available
  //1.LANGUAGE_MODEL_WEB_SEARCH : For short phrases
  //2.LANGUAGE_MODEL_FREE_FORM  : If not sure about the words or phrases and its domain.
intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
    RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
 

 
  // Specify how many results you want to receive. The results will be
  // sorted where the first result is the one with higher confidence.
  
  //Start the Voice recognizer activity for the result.
  startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
 }
 
 @Override
 protected void onActivityResult(int requestCode, int resultCode, Intent data) {
  if (requestCode == VOICE_RECOGNITION_REQUEST_CODE)
 
   //If Voice recognition is successful then it returns RESULT_OK
   if(resultCode == RESULT_OK) {
 
    ArrayList<String> textMatchList = data
    .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
 
    if (!textMatchList.isEmpty()) {
     // If first Match contains the 'search' word
     // Then start web search.
//     if (textMatchList.get(0).contains("search")) {
 
//        String searchQuery = textMatchList.get(0);
//                                           searchQuery = searchQuery.replace("search","");
//        Intent search = new Intent(Intent.ACTION_WEB_SEARCH);
//        search.putExtra(SearchManager.QUERY, searchQuery);
//        startActivity(search);
//     } else {
    	  metTextSearch.setText(textMatchList.get(0));
    	  
    	  getsearchres(textMatchList.get(0));
         // populate the Matches
//     }
 
    }
   //Result code for various error.
   }else if(resultCode == RecognizerIntent.RESULT_AUDIO_ERROR){
    showToastMessage("Audio Error");
   }else if(resultCode == RecognizerIntent.RESULT_CLIENT_ERROR){
    showToastMessage("Client Error");
   }else if(resultCode == RecognizerIntent.RESULT_NETWORK_ERROR){
    showToastMessage("Network Error");
   }else if(resultCode == RecognizerIntent.RESULT_NO_MATCH){
    showToastMessage("No Match");
   }else if(resultCode == RecognizerIntent.RESULT_SERVER_ERROR){
    showToastMessage("Server Error");
   }
  super.onActivityResult(requestCode, resultCode, data);
 }
 /**
 * Helper method to show the toast message
 **/
 void getsearchres(String query) {
	 searchresults.setText("What did you mean by that?");
	 List<String> split_query = new ArrayList<String>();
	 	 query = query.toLowerCase();
	 	
	 	 try{ 
	 	 JSONObject obj = new JSONObject(loadJSONFromAsset());
	 //     String result = obj.getJSONArray(query);
	 //     JSONObject jo_inside = m_jArry.getJSONObject(0);
	 	 	String prod_cat = "";
	 	 	String prod_brand = "";
	 	 	String prod_subcat = "";
	 	 	String prod_gender = "";
	 	 	String prod_type = "";
	 	 	for (String token: query.split(" ")) {
	 	 		if(!split_query.contains(token)){  //to avoid duplicates
		 			split_query.add(token);
	 	 		try {
	 	 			
	 	 		String searchval = obj.getString(token);
	 	 		if (searchval.equals("brand")) {
	 	 			prod_brand = token;
	 	 		}
	 	 		else if (searchval.equals("cat")) {
	 	 			prod_cat = token;
	 	 		}
	 	 		else if (searchval.equals("gender")) {
	 	 			prod_gender = token;
	 	 		}
	 	 		else if (searchval.equals("type")) {
	 	 			prod_type = token;
	 	 		}
	 	 		else if (searchval.equals("subcat")) {
	 	 			prod_subcat = token;
	 	 		}
	 
	 	 		System.out.println(searchval);
	 	 		}
	 	 		catch (JSONException exce) {
	 	 			
	 	 		}
	 	 	}
	 	 	}
	        String printthis;
	        printthis = "The user wants brand: "+prod_brand+", category: "+prod_cat+", for: "+prod_gender+", of type: "+prod_type+", and subcategory: "+prod_subcat;
	        searchresults.setText(printthis);
	        } catch (JSONException ex) {
	 }}
 
 void showToastMessage(String message){
  Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
 }
}