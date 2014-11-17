package com.example.gui;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;



/*
import android.app.Fragment;
import android.app.Activity;
import android.app.FragmentTransaction;*/
import android.content.SharedPreferences;




public class HomeScreen extends Fragment {
		Button log_main,sign_main,log_in,sign_in,twitter_button;
		//AutoCompleteTextView country;
		View logv;
		TextView res,info;
		InputStream is;
 		EditText sign_lname,log_email,log_pass,sign_fname,sign_mobile,sign_email,sign_pass,sign_pass_conf;
	   @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	        View v = inflater.inflate(R.layout.activity_home_screen, container, false);
	       // final Spinner spin_gender = (Spinner) v.findViewById(R.id.gender);
	        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(getActivity(),
	                R.array.gender_array, android.R.layout.simple_spinner_item);
	        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	       // spin_gender.setAdapter(adapter1);
	      //  AutoCompleteTextView textView = (AutoCompleteTextView) v.findViewById(R.id.autocomplete_country);
	     // Get the string array
	     String[] countries = getResources().getStringArray(R.array.countries_array);
	     // Create the adapter and set it to the AutoCompleteTextView 
	     ArrayAdapter<String> adapter2 = 
	             new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, countries);
	     //textView.setAdapter(adapter2);
//	     info=(TextView) v.findViewById(R.id.textView2);
	     log_main=(Button) v.findViewById(R.id.login);
	     res=(TextView) v.findViewById(R.id.error_textfield);
	     logv=v.findViewById(R.id.login);
//	     sign_main=(Button) v.findViewById(R.id.signup);
	     log_in=(Button) v.findViewById(R.id.login_button);
	     sign_in=(Button) v.findViewById(R.id.signup_button);
	     twitter_button=(Button) v.findViewById(R.id.twitter_button);
	   //  sign_lname=(EditText) v.findViewById(R.id.signup_lname);
	    // sign_lname.setTextColor(Color.parseColor("#000000"));
	     log_email=(EditText) v.findViewById(R.id.login_email);
	     log_email.setTextColor(Color.parseColor("#000000"));
	     log_pass=(EditText) v.findViewById(R.id.login_password);
	     log_pass.setTextColor(Color.parseColor("#000000"));
	     sign_fname=(EditText) v.findViewById(R.id.signup_fname);
	     sign_fname.setTextColor(Color.parseColor("#000000"));
	     //sign_mobile=(EditText) v.findViewById(R.id.signup_mobno);
	    // sign_mobile.setTextColor(Color.parseColor("#000000"));
	     sign_email=(EditText) v.findViewById(R.id.signup_email);
	     sign_email.setTextColor(Color.parseColor("#000000"));
	     sign_pass=(EditText) v.findViewById(R.id.signup_password);
	     sign_pass_conf=(EditText) v.findViewById(R.id.signup_password_confirm);
	     sign_pass.setTextColor(Color.parseColor("#000000"));
	     sign_pass_conf.setTextColor(Color.parseColor("#000000"));
	     log_in.setTextColor(Color.WHITE);
	     
	     Drawable back = getResources().getDrawable(R.drawable.app_back);
	     back.setAlpha(195);

//
//	     .setImageDrawable(rightArrow);
	     twitter_button.setOnClickListener(new View.OnClickListener() {
	    	 
				@Override
				public void onClick(View v) {
				
			    
			        
			       /* SharedPreferences.Editor edit = pref.edit();
			        edit.putString("CONSUMER_KEY", CONSUMER_KEY);
			        edit.putString("CONSUMER_SECRET", CONSUMER_SECRET);
			        edit.commit();
			        Fragment login = new LoginFragment();
			        FragmentTransaction ft = getFragmentManager().beginTransaction();
			        ft.replace(R.id.content_frame, login);
			        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			        ft.addToBackStack(null);
			        ft.commit();*/
				//public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent i = new Intent(getActivity(),Twitter.class);
              startActivity(i);
				}
				
			});
	     log_in.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					StrictMode.enableDefaults();
					String username=log_email.getText().toString();
					String psswd=log_pass.getText().toString();
					getdata(username,psswd);
				}
			});
	     sign_in.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					StrictMode.enableDefaults();
					String fname=sign_fname.getText().toString();
					String lname="";
					String gen="";
					String mobile="";
					String email=sign_email.getText().toString();
					String mpass=sign_pass.getText().toString();
					String rpass=sign_pass_conf.getText().toString();
					 List<NameValuePair> params = new ArrayList<NameValuePair>();
			            params.add(new BasicNameValuePair("Firstname",fname));
			            params.add(new BasicNameValuePair("Lastname",lname));
			            params.add(new BasicNameValuePair("Gender",gen));
			            params.add(new BasicNameValuePair("Mobileno",mobile));
			            params.add(new BasicNameValuePair("Email",email));
			            params.add(new BasicNameValuePair("Password",mpass));

					if(mpass.equals(rpass)){
						
						String url="http://rohithdb.besaba.com/signup_app.php";
						postdata(url,params);
					}
					else
					{
						int duration = Toast.LENGTH_SHORT;
						Context context = getActivity().getApplicationContext();
						CharSequence text = "The passwords you have entered do not match.";
						Toast toast = Toast.makeText(context, text, duration);
			    		toast.show();
						
					}
				}
			});
	     
	     
	
	     return v;
	        
	    }

	    public static HomeScreen newInstance(String text) {

	    	HomeScreen home = new HomeScreen();
	    	

	        return home;
	    }
	    public void getdata(String usrname,String passwd){
	    	String url="http://rohithdb.besaba.com/login_app.php";
	    	String usrname1=log_email.getText().toString();
	    	List<NameValuePair> params = new ArrayList<NameValuePair>();
	    	  params.add(new BasicNameValuePair("username",usrname1));
	        //  params.add(new BasicNameValuePair("password",passwd));
	    	String userpass=JSONParser.getJSONFromUrl(url,params);
	    	String s="";
	    	try{
	    		
					JSONObject json = new JSONObject(userpass);
					
	    			String username=json.getString("Email");
	    			//res.setText("ended loop");
	    		if(username.equals("")){
	    			
	    				int duration = Toast.LENGTH_SHORT;
						Context context = getActivity().getApplicationContext();
						CharSequence text = "Invalid username. Please try again/Signup.";
						Toast toast = Toast.makeText(context, text, duration);
			    		toast.show();
	    			
	    		}
	    		else{
	    			s=json.getString("Password");
	    		Log.e("string returned", s+"passs..");	
	    		String psswd=log_pass.getText().toString();
	    		Log.e("actual",psswd);
	    		if(s.equals(psswd)){
					int duration = Toast.LENGTH_SHORT;
					Context context = getActivity().getApplicationContext();
					//sign_main.setVisibility(View.GONE);
					//log_main.setVisibility(View.GONE);
					String text="Hello there,"+" "+json.getString("Firstname")+" "+" \n";	
					Log.e("name",text);
				//	info.setText(text);
					CharSequence text1 = "Login successful";
					Toast toast = Toast.makeText(context, text, duration);
		    		toast.show();	
				}
				else{
					int duration = Toast.LENGTH_SHORT;
					Context context = getActivity().getApplicationContext();
					CharSequence text = "Invalid password. Please try again.";
					Toast toast = Toast.makeText(context, text, duration);
		    		toast.show();
					
					} 
	    		}
	    			
	    	}
	    	catch(Exception e){
	    		Log.d("conn","Error to json"+e.toString());
	    	}
	    	
	    }
	    public void postdata(String url,List<NameValuePair> params){
	    	try{
	    	DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new UrlEncodedFormEntity(params));
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            is = httpEntity.getContent();
            int duration = Toast.LENGTH_SHORT;
			Context context = getActivity().getApplicationContext();
			CharSequence text = "Processing sign-up request...";
			Toast toast = Toast.makeText(context, text, duration);
    		toast.show();
	    	}
	    	catch(Exception e){
	    		Log.e("conn",e.toString());
	    		e.printStackTrace();
	    	}
	    }
	}