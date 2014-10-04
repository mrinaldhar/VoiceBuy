package com.example.gui;
 
import java.util.ArrayList;
import java.util.List;
 
import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
 
public class VoiceRecognitionActivity extends Activity {
 private static final int VOICE_RECOGNITION_REQUEST_CODE = 1001;
 
 private EditText metTextSearch;
 private ImageButton mbtSpeak;
 
 @Override
 public void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.activity_voice_recognition);
  metTextSearch = (EditText) findViewById(R.id.search_bar);
  mbtSpeak = (ImageButton) findViewById(R.id.btSpeak);
  checkVoiceRecognition();
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
     if (textMatchList.get(0).contains("search")) {
 
        String searchQuery = textMatchList.get(0);
                                           searchQuery = searchQuery.replace("search","");
        Intent search = new Intent(Intent.ACTION_WEB_SEARCH);
        search.putExtra(SearchManager.QUERY, searchQuery);
        startActivity(search);
     } else {
    	  metTextSearch.setText(textMatchList.get(0));
         // populate the Matches
     }
 
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
 void showToastMessage(String message){
  Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
 }
}