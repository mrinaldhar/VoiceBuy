package com.example.gui;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.Menu;
import android.widget.TextView;
 
public class SplashScreen extends Activity {
	TextToSpeech ttobj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().hide();
        ttobj=new TextToSpeech(getApplicationContext(), 
      	      new TextToSpeech.OnInitListener() {
      	      @Override
      	      public void onInit(int status) {
      	         if(status != TextToSpeech.ERROR){
      	             ttobj.setLanguage(Locale.UK);
      	            }				
      	         }
      	      });

        setContentView(R.layout.activity_splashscreen);
     
// METHOD 1     
         
         /****** Create Thread that will sleep for 5 seconds *************/        
        Thread background = new Thread() {
            @Override
			public void run() {
                 
                try {
                    // Thread will sleep for 3 seconds
                    sleep(3*1000+500);
                     
                    // After 5 seconds redirect to another intent
                    Intent i=new Intent(getBaseContext(),MainActivity.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                     
                    //Remove activity
                    finish();
                     
                } catch (Exception e) {
                 
                }
            }
        };
         
        // start thread
        background.start();
         
    }
     @Override 
     public boolean onCreateOptionsMenu(Menu menu) {
         TextView text = (TextView) findViewById(R.id.appname);
         TextView text2 = (TextView) findViewById(R.id.smart);
         TextView text3 = (TextView) findViewById(R.id.copyright);
         Typeface font = Typeface.createFromAsset(getAssets(), "myriadpro.otf");
     	text.setTypeface(font);
     	text2.setTypeface(font);
     	text3.setTypeface(font);
    	  ttobj.speak("Welcome to Voice Buy, the smarter way of online shopping.", TextToSpeech.QUEUE_FLUSH, null);

    	 return true;
     }
    @Override
    protected void onDestroy() {
         
        super.onDestroy();
         
    }
}