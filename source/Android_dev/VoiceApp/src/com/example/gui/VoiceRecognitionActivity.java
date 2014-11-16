package com.example.gui;
 
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import model.Movie;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import adapter.CustomListAdapter;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gui.RangeSeekBar.OnRangeSeekBarChangeListener;

public class VoiceRecognitionActivity extends Activity {
 
private static final int VOICE_RECOGNITION_REQUEST_CODE = 1001;
 TextToSpeech ttobj;
 private EditText metTextSearch;
 private ImageButton mbtSpeak;
// private TextView searchresults;
 public String storename;
 

// private List<Movie> tempList = new ArrayList<Movie>();
 private List<Movie> movieList = new ArrayList<Movie>();
 private List<Movie> tempList = new ArrayList<Movie>();
 private ListView listView;
 
// Spinner dropdown = (Spinner)findViewById(R.id.pricefil);
 
 private CustomListAdapter adapter;
 
 @Override
 public void onCreate(Bundle savedInstanceState) {
  
	 super.onCreate(savedInstanceState);
	 setContentView(R.layout.activity_voice_recognition);
	 listView = (ListView) findViewById(R.id.list);
		adapter = new CustomListAdapter(this, movieList);
      	
  Button modfill = (Button)findViewById(R.id.filter);
  modfill.setAlpha(0);
 
//create RangeSeekBar as Integer range between 20 and 75
  


 
//  String[] items = new String[]{"0 - 500","500 - 1000","1000 - 2000","2000 - 5000","5000 and above"};
//  ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, items);
//  dropdown.setAdapter(adapter);

//  OnClickListener filterlist = new OnClickListener() {
//	
//	@Override
//	public void onClick(final View v){
//		 	movieList.clear();
//			movieList.addAll(tempList);
//	         Spinner tmp = (Spinner)findViewById(R.id.pricefil);
//	         
//	         String myprice = new String(tmp.getItemAtPosition(0).toString());
//	         for (Movie thismovie : tempList) {
//	        	 if (myprice.equals("0 - 500")){
////	        		 showToastMessage("0 - 500");
//	        		 if (thismovie.getRating() > 500){
//	        			 movieList.remove(thismovie);
//	        		 }
//	        	 }
//	        	 else if (myprice.equals("500 - 1000")){
//	        		 if (thismovie.getRating() > 1000 && thismovie.getRating() <= 500){
//
//	        			 movieList.remove(thismovie);
//	        		 }
//	        	 }
//	        	 else if (myprice.equals("1000 - 2000")){
//	        		 if (thismovie.getRating() > 2000 && thismovie.getRating() <= 1000){
//	        			 movieList.remove(thismovie);
//	        		 }
//	        	 }
//	        	 else if (myprice.equals("2000 - 5000")){
//	        		 if (thismovie.getRating() > 5000 && thismovie.getRating() <= 2000){
//
//	       	        	 showToastMessage("" + thismovie.getRating());
//	        			 movieList.remove(thismovie);
//	        		 }
//	        	 }
//
//	        	 else if (myprice.equals("5000 and above")){
//	        		 if (thismovie.getRating() < 5000){
//	        			 movieList.remove(thismovie);
//	        		 }
//	        	 }
//	        }
//	          if (movieList.size() == 0)  
//	         showToastMessage("No Item Match Selected Criteria");
//
//	}
//
//
//};
//Button filternow = (Button)findViewById(R.id.filter);
//filternow.setOnClickListener(filterlist);

ttobj=new TextToSpeech(getApplicationContext(), 
	      new TextToSpeech.OnInitListener() {
	      @Override
	      public void onInit(int status) {
	         if(status != TextToSpeech.ERROR){
	             ttobj.setLanguage(Locale.UK);
	            }				
	         }
	      });

  listView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
	  
		@Override
	       public void onItemClick(AdapterView<?> a, View v, int position,
	               long id) {
	         Movie select = new Movie();
	         Movie tid = (Movie)a.getItemAtPosition(position);
	     
	          select.setTitle(tid.getTitle());
	          select.setThumbnailUrl(tid.getThumbnailUrl());
	          select.setRating(Double.valueOf(tid.getRating()).doubleValue());
	          select.setYear(tid.getYear());
	          if (ShoppingCart.itemList.contains(select)){
	        	  showToastMessage("You Already have this item in your cart");
	       
	          }
	          else{
	          ShoppingCart.itemList.add(select);
	          showToastMessage(tid.getTitle() + " Added To Cart");
	          }
	          saythis("Okay, I'll add this item to your shopping cart.");
//	    Intent intent = new Intent(getActivity().getApplicationContext(), VoiceRecognitionActivity.class);
//	    String store_name = a.getItemAtPosition(position).toString();
//	    System.out.println("Store:" + store_name);
//	    intent.putExtra("getstore",store_name);
//	    startActivity(intent);
	           }
	       
	   });

  Bundle extras = getIntent().getExtras();
	String value = new String();
	storename = new String();
  if (extras != null){
		value = extras.getString("getstore");
	}
  storename = value;	
	ActionBar action = getActionBar();
	action.setTitle(value + " Products");
	
  metTextSearch = (EditText) findViewById(R.id.search_bar);
  mbtSpeak = (ImageButton) findViewById(R.id.btSpeak);
//  searchresults = (TextView) findViewById(R.id.searchresults);
  	checkVoiceRecognition();
	Typeface font = Typeface.createFromAsset(getAssets(), "myriadpro.otf");
	metTextSearch.setTypeface(font);
//	searchresults.setTypeface(font);

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
		ttobj.speak("Okay, what should I search for in "+storename+"?", TextToSpeech.QUEUE_FLUSH, null);
		while (ttobj.isSpeaking())
		{

		}
		speak(this.findViewById(R.layout.activity_voice_recognition));

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
			saythis("Here's your shopping cart so far.");
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
  intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "What would you like to search for?");
 
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
    	  for(int i=0;i < textMatchList.size();i++){
    	  System.out.println(textMatchList.get(i));
    	  
    	  }
    	  getsearchres(textMatchList);
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
 
 public int LevenshteinDistance (String s0, String s1) {                          
	    int len0 = s0.length() + 1;                                                     
	    int len1 = s1.length() + 1;                                                     
	 
	    // the array of distances                                                       
	    int[] cost = new int[len0];                                                     
	    int[] newcost = new int[len0];                                                  
	 
	    // initial cost of skipping prefix in String s0                                 
	    for (int i = 0; i < len0; i++) cost[i] = i;                                     
	 
	    // dynamicaly computing the array of distances                                  
	 
	    // transformation cost for each letter in s1                                    
	    for (int j = 1; j < len1; j++) {                                                
	        // initial cost of skipping prefix in String s1                             
	        newcost[0] = j;                                                             
	 
	        // transformation cost for each letter in s0                                
	        for(int i = 1; i < len0; i++) {                                             
	            // matching current letters in both strings                             
	            int match = (s0.charAt(i - 1) == s1.charAt(j - 1)) ? 0 : 1;             
	 
	            // computing cost for each transformation                               
	            int cost_replace = cost[i - 1] + match;                                 
	            int cost_insert  = cost[i] + 1;                                         
	            int cost_delete  = newcost[i - 1] + 1;                                  
	 
	            // keep minimum cost                                                    
	            newcost[i] = Math.min(Math.min(cost_insert, cost_delete), cost_replace);
	        }                                                                           
	 
	        // swap cost/newcost arrays                                                 
	        int[] swap = cost; cost = newcost; newcost = swap;                          
	    }                                                                               
	 
	    // the distance is the cost for transforming all letters in both strings        
	    return cost[len0 - 1];                                                          
	}
 
 /**
 * Helper method to show the toast message
 **/
 @SuppressWarnings("deprecation")
void getsearchres(ArrayList<String> querylist) {
//	 searchresults.setText("What did you mean by that?");
	 showToastMessage("Searching...");
	 List<String> split_query = new ArrayList<String>();

	 List<String> split_query2 = new ArrayList<String>();
	 	
	 	 try{ 
	 	 JSONObject obj = new JSONObject(loadJSONFromAsset());
	 //     String result = obj.getJSONArray(query);
	 //     JSONObject jo_inside = m_jArry.getJSONObject(0);
	 	 	String prod_cat = "";
	 	 	String prod_brand = "";
	 	 	String prod_subcat = "";
	 	 	String prod_gender = "";
	 	 	String prod_type = "";
	 	 	int bflag = 0;
	 	 	int cflag = 0;
	 	 	int gflag = 0;
	 	 	int tflag = 0;
	 	 	int sflag = 0;
	 	 	for (String query : querylist){

		 	 	 query = query.toLowerCase();
		 	 	 
	 	 		for (String token: query.split(" ")) {
	 	 		if(!split_query.contains(token)){  //to avoid duplicates
		 			split_query.add(token);
	 	 		try {
	 	 			
	 	 		String searchval = obj.getString(token);
	 	 		
	 	 		if (searchval.equals("brand")) {
	 	 			if (bflag != 1){
	 	 			prod_brand = token;
	 	 			bflag = 1;
	 	 			}
	 	 		}
	 	 		else if (searchval.equals("cat")) {
	 	 			if (cflag != 1){
	 	 				prod_cat = token;
	 	 				cflag = 1;
		 	 			}
	 	 			
	 	 		}
	 	 		else if (searchval.equals("gender")) {
	 	 			if (gflag != 1){
	 	 			prod_gender = token;
	 	 			gflag = 1;
	 	 			}
	 	 		}
	 	 		else if (searchval.equals("type")) {
	 	 			if (tflag != 1){
	 	 			prod_type = token;
	 	 			tflag = 1;
	 	 			}
	 	 		}
	 	 		else if (searchval.equals("subcat")) {
	 	 			if (sflag != 1){
	 	 			prod_subcat = token;
	 	 			sflag = 1;
	 	 			}
	 	 		}
	 
	 	 		System.out.println(searchval);
	 	 		}
	 	 		catch (JSONException exce) {
	 	 		 	
	 	 		}
	 	 	}
	 	 	}
	 	 	}
	 	 	if (prod_brand == "" && prod_cat == "" || prod_brand == ""){
	 			 String finalkey = null;
				 	
	 	 		for (String query : querylist){
	 	 			 query = query.toLowerCase();
			 	 	 
		 	 	 		for (String token2: query.split(" ")) {
		 	 	 		if(!split_query2.contains(token2)){  //to avoid duplicates
		 		 			split_query2.add(token2);
		 		 			
		 		 			 Iterator<String> iter = obj.keys();
			 	 			 int min = 99999999;
			 	 			 
			 	 			 while (iter.hasNext()) {
			 	 		        String temp = iter.next();
			 	 		        System.out.println(token2);
			 	 		        int dis = LevenshteinDistance(token2, temp);
					
			 	 		        if ( dis < min){
									min = dis;
									finalkey = temp;
								}
								}			
		 	 	 		}
		 	 	 	}
	 	 		}
	 	 		String searchval = obj.getString(finalkey);
		 	 	
	 	 		if (searchval.equals("brand")) {
	 	 			if (bflag != 1){
	 	 			prod_brand = finalkey;
	 	 			bflag = 1;
	 	 			}
	 	 		}
	 	 		else if (searchval.equals("cat")) {
	 	 			if (cflag != 1){
	 	 				prod_cat = finalkey;
	 	 				cflag = 1;
		 	 			}
	 	 			
	 	 		}
	
	 	 		else if (searchval.equals("subcat")) {
	 	 			if (sflag != 1){
	 	 			prod_subcat = finalkey;
	 	 			sflag = 1;
	 	 			}
	 	 		}	
	 	 	}
	        String printthis;
//	        metTextSearch.setText("" + prod_brand + " " + prod_cat + " " + prod_gender+  " " + prod_type + " " + prod_subcat);
	        printthis = "The user wants \nbrand: "+prod_brand+", \ncategory: "+prod_cat+", \nfor: "+prod_gender+", \nof type: "+prod_type+", \nand subcategory: "+prod_subcat;
	        System.out.println(printthis);
//	        showToastMessage(storename);
	   
	        //Starting Search procedure
	        

	        new HttpAsyncTask().execute("http://svcs.ebay.com/services/search/FindingService/v1?OPERATION-NAME=findItemsByKeywords&SERVICE-VERSION=1.0.0&SECURITY-APPNAME=IIIT38ebc-682e-4421-9e85-afd72d6451e&GLOBAL-ID=EBAY-IN&RESPONSE-DATA-FORMAT=JSON&REST-PAYLOAD&keywords="+URLEncoder.encode(prod_brand+" "+prod_cat+" "+prod_gender+" "+prod_type+" "+prod_subcat)+"&paginationInput.entriesPerPage=30");
            
            System.out.println("OKLOOPCLOSE");
	        
	        
	        
	        
	        } catch (JSONException ex) {
	 }}
 
 void saythis(String toSpeak) {
	 ttobj.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
 }
 void getsearchres(String query) {

//	 searchresults.setText("What did you mean by that?");
	 showToastMessage("Searching...");
	 saythis("One moment please. I'm looking for "+query+" for you now.");
	 List<String> split_query = new ArrayList<String>();
	
	 	
	 	 try{ 
	 	 JSONObject obj = new JSONObject(loadJSONFromAsset());
	 //     String result = obj.getJSONArray(query);
	 //     JSONObject jo_inside = m_jArry.getJSONObject(0);
	 	 	String prod_cat = "";
	 	 	String prod_brand = "";
	 	 	String prod_subcat = "";
	 	 	String prod_gender = "";
	 	 	String prod_type = "";
	 	 	int bflag = 0;
	 	 	int cflag = 0;
	 	 	int gflag = 0;
	 	 	int tflag = 0;
	 	 	int sflag = 0;

		 	 	 query = query.toLowerCase();
		 	 	 
	 	 		for (String token: query.split(" ")) {
	 	 		if(!split_query.contains(token)){  //to avoid duplicates
		 			split_query.add(token);
	 	 		try {
	 	 			
	 	 		String searchval = obj.getString(token);
	 	 	
	 	 		if (searchval.equals("brand")) {
	 	 			if (bflag != 1){
	 	 			prod_brand = token;
	 	 			bflag = 1;
	 	 			}
	 	 		}
	 	 		else if (searchval.equals("cat")) {
	 	 			if (cflag != 1){
	 	 				prod_cat = token;
	 	 				cflag = 1;
		 	 			}
	 	 			
	 	 		}
	 	 		else if (searchval.equals("gender")) {
	 	 			if (gflag != 1){
	 	 			prod_gender = token;
	 	 			gflag = 1;
	 	 			}
	 	 		}
	 	 		else if (searchval.equals("type")) {
	 	 			if (tflag != 1){
	 	 			prod_type = token;
	 	 			tflag = 1;
	 	 			}
	 	 		}
	 	 		else if (searchval.equals("subcat")) {
	 	 			if (sflag != 1){
	 	 			prod_subcat = token;
	 	 			sflag = 1;
	 	 			}
	 	 		}
	 	 	
	 	 		System.out.println(searchval);
	 	 		}
	 	 		catch (JSONException exce) {
//		 	 			System.out.println("Search " + searchval + "token " + token);
			 	 			 Iterator<String> iter = obj.keys();
			 	 			 int min = 99999999;
			 	 			 String finalkey = null;
			 	 			 
			 	 			 while (iter.hasNext()) {
			 	 		        String temp = iter.next();
			 	 		        System.out.println(token);
			 	 		        int dis = LevenshteinDistance(token, temp);
					
			 	 		        if ( dis < min){
									min = dis;
									finalkey = temp;
								}
								}
		
			 	 			String searchval = obj.getString(finalkey);
			 	 			 if (searchval.equals("brand")) {
				 	 			if (bflag != 1){
				 	 			prod_brand = finalkey;
				 	 			bflag = 1;
				 	 			}
				 	 		}
				 	 		else if (searchval.equals("cat")) {
				 	 			if (cflag != 1){
				 	 				prod_cat = finalkey;
				 	 				cflag = 1;
					 	 			}
				 	 			
				 	 		}
				 	 		else if (searchval.equals("gender")) {
				 	 			if (gflag != 1){
				 	 			prod_gender = finalkey;
				 	 			gflag = 1;
				 	 			}
				 	 		}
				 	 		else if (searchval.equals("type")) {
				 	 			if (tflag != 1){
				 	 			prod_type = finalkey;
				 	 			tflag = 1;
				 	 			}
				 	 		}
				 	 		else if (searchval.equals("subcat")) {
				 	 			if (sflag != 1){
				 	 			prod_subcat = finalkey;
				 	 			sflag = 1;
				 	 			}
				 	 		}
			 	
	 	 		}
	 	 	}
	 	 	}
	 	        String printthis;
	        printthis = "The user wants \nbrand: "+prod_brand+", \ncategory: "+prod_cat+", \nfor: "+prod_gender+", \nof type: "+prod_type+", \nand subcategory: "+prod_subcat;
	        System.out.println(printthis);
	        //	        showToastMessage(storename);
	   
	        //Starting Search procedure
	        

	        new HttpAsyncTask().execute("http://svcs.ebay.com/services/search/FindingService/v1?OPERATION-NAME=findItemsByKeywords&SERVICE-VERSION=1.0.0&SECURITY-APPNAME=IIIT38ebc-682e-4421-9e85-afd72d6451e&GLOBAL-ID=EBAY-IN&RESPONSE-DATA-FORMAT=JSON&REST-PAYLOAD&keywords="+URLEncoder.encode(prod_brand+" "+prod_cat+" "+prod_gender+" "+prod_type+" "+prod_subcat)+"&paginationInput.entriesPerPage=30");
            
            System.out.println("OKLOOPCLOSE");
	        
	        
	        
	        
	        } catch (JSONException ex) {
	 }}

 
 void showToastMessage(String message){
  Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
 }
 
 
 public static String GET(String url){
	 System.out.println("entered first func");
     InputStream inputStream = null;
     String result = "";
     try {

         // create HttpClient
         HttpClient httpclient = new DefaultHttpClient();

         // make GET request to the given URL
         HttpResponse httpResponse = httpclient.execute(new HttpGet(url));

         // receive response as inputStream
         inputStream = httpResponse.getEntity().getContent();

         // convert inputstream to string
         if(inputStream != null)
             result = convertInputStreamToString(inputStream);
         else
             result = "Did not work!";

     } catch (Exception e) {
    	 System.out.println(e);
//         Log.d("InputStream", e.getLocalizedMessage());
     }

     return result;
 }

 // convert inputstream to String
 private static String convertInputStreamToString(InputStream inputStream) throws IOException{
	 System.out.println("enteredfunc2");
     BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
     String line = "";
     String result = "";
     while((line = bufferedReader.readLine()) != null)
         result += line;

     inputStream.close();
     return result;

 }
 
 private class HttpAsyncTask extends AsyncTask<String, Void, String> {
     @Override
     protected String doInBackground(String... urls) {

         return GET(urls[0]);
     }
     // onPostExecute displays the results of the AsyncTask.
     @Override
     protected void onPostExecute(String result) {
    	 
         Toast.makeText(getBaseContext(), "Received!", Toast.LENGTH_LONG).show();
//         etResponse.setText(result);
         String teststring = "";
         try {
        	
         final JSONObject obj = new JSONObject(result);
         System.out.println("1");
         final JSONArray items1 = obj.getJSONArray("findItemsByKeywordsResponse");
         System.out.println("2");
         final JSONObject searchResObj1 = items1.getJSONObject(0);
         System.out.println("3");
         final JSONArray searchResObj2 = searchResObj1.getJSONArray("searchResult");
         System.out.println("4");
         final JSONObject searchResObj3 = searchResObj2.getJSONObject(0);
         System.out.println("5");
         final JSONArray searchResObj = searchResObj3.getJSONArray("item");
         System.out.println("6");
         final int n = searchResObj.length();
         tempList.clear();
         movieList.clear();
         int max = 0;
         for (int i = 0; i < n; ++i) {
           final JSONObject searchItem = searchResObj.getJSONObject(i);
           JSONArray toPrint = searchItem.getJSONArray("title");
           System.out.println(toPrint.getString(0));
           final String prodTitle = toPrint.getString(0);
           toPrint = searchItem.getJSONArray("galleryURL");
           final String prodPic = toPrint.getString(0);
           final JSONArray priceDet = searchItem.getJSONArray("sellingStatus");
           final JSONObject priceDet2 = priceDet.getJSONObject(0);
           final JSONArray priceDet3 = priceDet2.getJSONArray("convertedCurrentPrice");
           final JSONObject priceDet4 = priceDet3.getJSONObject(0);
           final String prodCurr = priceDet4.getString("@currencyId");
           String prodPrice = priceDet4.getString("__value__");
           
           if ((int)Double.valueOf(prodPrice).doubleValue() > max){
        	   max = (int)Double.valueOf(prodPrice).doubleValue();
           }
           Movie movie = new Movie();
           movie.setTitle(prodTitle);
           movie.setThumbnailUrl(prodPic);
           movie.setRating(Double.valueOf(prodPrice).doubleValue());
           movie.setYear(prodCurr);
        	   movieList.add(movie);   
        	   tempList.add(movie);
           
         }
         
         RangeSeekBar<Integer> seekBar = new RangeSeekBar<Integer>(0, max, getApplicationContext());
         
         seekBar.setOnRangeSeekBarChangeListener(new OnRangeSeekBarChangeListener<Integer>() {
                @Override
                public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar, Integer minValue, Integer maxValue) {
                	  
//                	  listView.setAdapter(adapter);
                	movieList.clear(); 
                	for (Movie tempmov : tempList) {
                    	 if (tempmov.getRating() < maxValue && tempmov.getRating() > minValue)
                    		 movieList.add(tempmov);
            		} 
                  listView.setAdapter(adapter);
                	// handle changed range values
                	showToastMessage("List Filtered");
                        Log.i("TAG", "User selected new range values: MIN=" + minValue + ", MAX=" + maxValue);
                }
         });
        
        
         //add RangeSeekBar to pre-defined layout
         Button modfil = (Button)findViewById(R.id.filter);
         modfil.setAlpha(1);
         modfil.setTextColor(Color.WHITE);
         ViewGroup layout = (ViewGroup) findViewById(R.id.rangebar);
         layout.addView(seekBar);   

        
         
         } catch (JSONException ex) {
        	 System.out.println(ex);
         }
         adapter.notifyDataSetChanged();
   
         saythis("Here is what I found.");
//         searchresults.setText(teststring);
    }
 }
}

