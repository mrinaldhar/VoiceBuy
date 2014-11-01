package com.example.gui;
 
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

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
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class VoiceRecognitionActivity extends Activity {
 private static final int VOICE_RECOGNITION_REQUEST_CODE = 1001;
 
 private EditText metTextSearch;
 private ImageButton mbtSpeak;
// private TextView searchresults;
 public String storename;
 
 private List<Movie> movieList = new ArrayList<Movie>();
 private ListView listView;
 private CustomListAdapter adapter;
 
 @Override
 public void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.activity_voice_recognition);
//  Intent intent = new Intent(getApplicationContext(), MyTooleapActivity.class);
//
//  TooleapNotificationMiniApp miniApp = new TooleapNotificationMiniApp(getApplicationContext(), intent);
//   
//  // An example for some customizations of a mini app. You can use your own...
//  miniApp.notificationText = "I'm right here to help you out. Always.";
//  miniApp.contentTitle = "VoiceBuy Search Bubble";
//  Tooleap tooleap = Tooleap.getInstance(getApplicationContext());
//  tooleap.addMiniApp(miniApp);
  listView = (ListView) findViewById(R.id.list);
  adapter = new CustomListAdapter(this, movieList);
  listView.setAdapter(adapter);
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
 @SuppressWarnings("deprecation")
void getsearchres(String query) {
//	 searchresults.setText("What did you mean by that?");
	 showToastMessage("Searching...");
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
	        printthis = "The user wants \nbrand: "+prod_brand+", \ncategory: "+prod_cat+", \nfor: "+prod_gender+", \nof type: "+prod_type+", \nand subcategory: "+prod_subcat;
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
           
           Movie movie = new Movie();
           movie.setTitle(prodTitle);
           movie.setThumbnailUrl(prodPic);
           movie.setRating(Double.valueOf(prodPrice).doubleValue());
           movie.setYear(prodCurr);

           // adding movie to movies array
           movieList.add(movie);
           
           
         }
         } catch (JSONException ex) {
        	 System.out.println(ex);
    	 }
         adapter.notifyDataSetChanged();
//         searchresults.setText(teststring);
    }
 }
}
