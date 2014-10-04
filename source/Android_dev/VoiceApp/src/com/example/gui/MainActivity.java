package com.example.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class MainActivity extends Activity {

	private ViewFlipper viewFlipper;
	private float lastX;
	public static List<String> stores = new ArrayList<String>();
	
	public static ArrayAdapter<String> adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		viewFlipper = (ViewFlipper) findViewById(R.id.view_flipper);
		String url1 = "http://web.iiit.ac.in/~bhavesh.goyal/ssad_project/clothes-info.xml";
		System.out.println("about to");
		new RetrieveBrandsTask(MainActivity.this).execute(url1);

/*		search.addTextChangedListener(new TextWatcher() {

	          public void afterTextChanged(Editable s) {
//	        	  search.setEllipsize(TruncateAt.MARQUEE);
//	        	  search.setSelected(true);

	          }

			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub
				
			}

			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				
			}
		});*/
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		
		
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
	
    public boolean onTouchEvent(MotionEvent touchevent) 
    {
                 switch (touchevent.getAction())
                 {
                        // when user first touches the screen to swap
                         case MotionEvent.ACTION_DOWN: 
                         {
                             lastX = touchevent.getX();
                             break;
                        }
                         case MotionEvent.ACTION_UP: 
                         {
                             float currentX = touchevent.getX();
                             
                             // if left to right swipe on screen
                             if (lastX < currentX) 
                             {
                                  // If no more View/Child to flip
                                 if (viewFlipper.getDisplayedChild() == 0)
                                     break;
                                 
                                 // set the required Animation type to ViewFlipper
                                 // The Next screen will come in form Left and current Screen will go OUT from Rights
                                 viewFlipper.setInAnimation(this, R.anim.in_from_left);
                                 viewFlipper.setOutAnimation(this, R.anim.out_from_right);
                                 // Show the next Screen
                                 viewFlipper.showNext();
                             }
                             
                             // if right to left swipe on screen
                             if (lastX > currentX)
                             {
                                 if (viewFlipper.getDisplayedChild() == 1)
                                     break;
                                 // set the required Animation type to ViewFlipper
                                 // The Next screen will come in form Right and current Screen will go OUT from Left 
                                 viewFlipper.setInAnimation(this, R.anim.in_from_right);
                                 viewFlipper.setOutAnimation(this, R.anim.out_from_left);
                                 // Show The Previous Screen
                                 viewFlipper.showPrevious();
                             }
                             break;
                         }
                 }
                 return false;
    }

}
class RetrieveBrandsTask extends AsyncTask<String, Void, Boolean> {

	String[] simpleArray = new String[ MainActivity.stores.size() ];
    private ProgressDialog dialog;
    List<String> titles;
    private MainActivity activity;
	
    public RetrieveBrandsTask(MainActivity activity) {
        this.activity = activity;
        context = activity;
        dialog = new ProgressDialog(context);
    }
    
    private Context context;
    
    protected void onPreExecute() {
        this.dialog.setMessage("Loading Stores");
        this.dialog.show();
    }
    protected Boolean doInBackground(String... urls) {
    	Document doc;
		try {
	 
			// need http protocol
			doc = Jsoup.connect(urls[0]).get();


			// get all links
			Elements brands = doc.select("Brand");
			
			for (Element link : brands) {
	 
				if (MainActivity.stores.contains("" + link.attr("text")) == false){
					MainActivity.stores.add("" + link.attr("text"));
//					System.out.println("" + link.attr("text"));
				}
	
			}
			return true;
	 
		} catch (IOException e) {
			return false;
		}
    }
    
    protected void onPostExecute(final Boolean success) {

		MainActivity.stores.toArray( simpleArray );
		System.out.println("Size" + MainActivity.stores.size());
        MainActivity.adapter = new ArrayAdapter<String>(this.activity,android.R.layout.simple_list_item_1,MainActivity.stores);

        MainActivity.adapter.notifyDataSetChanged();
        ListView list = (ListView)this.activity.findViewById(R.id.listview);
        MainActivity.adapter.sort(null);
        list.setAdapter(MainActivity.adapter);
        if (dialog.isShowing()) {
        dialog.dismiss();
        }
        
        list.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {

        	@Override
            public void onItemClick(AdapterView<?> a, View v, int position,
                    long id) {
         Intent intent = new Intent(activity.getApplicationContext(), VoiceRecognitionActivity.class);
        activity.startActivity(intent);
                }
            
        });
        
        if (success) {
            Toast.makeText(context, "Stores Loaded", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "Error Loading Stores", Toast.LENGTH_LONG).show();
        }

}

}