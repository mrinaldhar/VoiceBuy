package com.example.gui;

import java.io.BufferedReader;
import com.example.gui.JSONParser;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class HomeScreen extends Fragment {
		Button log_main,sign_main,log_in,sign_in;
		//AutoCompleteTextView country;
		View logv;
		TextView res;
		InputStream is;
 		EditText sign_lname,log_email,log_pass,sign_fname,sign_mobile,sign_email,sign_pass,sign_pass_conf;
	   @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	        View v = inflater.inflate(R.layout.activity_home_screen, container, false);
	        final Spinner spin_gender = (Spinner) v.findViewById(R.id.gender);
	        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(getActivity(),
	                R.array.gender_array, android.R.layout.simple_spinner_item);
	        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	        spin_gender.setAdapter(adapter1);
	      //  AutoCompleteTextView textView = (AutoCompleteTextView) v.findViewById(R.id.autocomplete_country);
	     // Get the string array
	     String[] countries = getResources().getStringArray(R.array.countries_array);
	     // Create the adapter and set it to the AutoCompleteTextView 
	     ArrayAdapter<String> adapter2 = 
	             new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, countries);
	     //textView.setAdapter(adapter2);
	     log_main=(Button) v.findViewById(R.id.login);
	     res=(TextView) v.findViewById(R.id.error_textfield);
	     logv=v.findViewById(R.id.login);
	     sign_main=(Button) v.findViewById(R.id.signup);
	     log_in=(Button) v.findViewById(R.id.login_button);
	     sign_in=(Button) v.findViewById(R.id.signup_button);
	     sign_lname=(EditText) v.findViewById(R.id.signup_lname);
	     log_email=(EditText) v.findViewById(R.id.login_email);
	     log_pass=(EditText) v.findViewById(R.id.login_password);
	     sign_fname=(EditText) v.findViewById(R.id.signup_fname);
	     sign_mobile=(EditText) v.findViewById(R.id.signup_mobno);
	     sign_email=(EditText) v.findViewById(R.id.signup_email);
	     sign_pass=(EditText) v.findViewById(R.id.signup_password);
	     sign_pass_conf=(EditText) v.findViewById(R.id.signup_password_confirm);
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
					String lname=sign_lname.getText().toString();
					String gen=spin_gender.getSelectedItem().toString();
					String mobile=sign_mobile.getText().toString();
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
						res.setText("Passwords did not match !!!!!!");
					}
				}
			});
	     
	     
	     log_main.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sign_fname.setVisibility(View.GONE);
				sign_lname.setVisibility(View.GONE);
				sign_mobile.setVisibility(View.GONE);
				sign_email.setVisibility(View.GONE);
				sign_pass.setVisibility(View.GONE);
				sign_pass_conf.setVisibility(View.GONE);
				spin_gender.setVisibility(View.GONE);
				sign_in.setVisibility(View.GONE);
				log_email.setVisibility(View.VISIBLE);
				log_pass.setVisibility(View.VISIBLE);
				log_in.setVisibility(View.VISIBLE);
				res.setVisibility(View.VISIBLE);
			}
		});
	     sign_main.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					res.setVisibility(View.GONE);
					log_email.setVisibility(View.GONE);
					log_pass.setVisibility(View.GONE);
					log_in.setVisibility(View.GONE);
					sign_fname.setVisibility(View.VISIBLE);
					sign_lname.setVisibility(View.VISIBLE);
					sign_mobile.setVisibility(View.VISIBLE);
					sign_email.setVisibility(View.VISIBLE);
					sign_pass.setVisibility(View.VISIBLE);
					sign_pass_conf.setVisibility(View.VISIBLE);
					sign_in.setVisibility(View.VISIBLE);
					res.setVisibility(View.GONE);
					spin_gender.setVisibility(View.VISIBLE);
					
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
	    	List<NameValuePair> params = new ArrayList<NameValuePair>();
	    	  params.add(new BasicNameValuePair("username",usrname));
	        //  params.add(new BasicNameValuePair("password",passwd));
	    	JSONArray jarray=JSONParser.getJSONFromUrl(url,params);
	    	if(jarray!=null){
	    	try{
	    		String s="";
				for(int i = 0;i<jarray.length();i++)
	    		{   res.setText("entered loop");
	    			JSONObject json=jarray.getJSONObject(i);
	    			if(json!=null){
	    			s=s+json.getString("Password");
	    			res.setText("ended loop");
	    			}
	    		} 
				if(s.equals(passwd)){
	    			res.setText("Login successful");
				}
				else{
					res.setText("Login Failed,please check your username/password!!!!");
				}
	    	}
	    	catch(Exception e){
	    		Log.d("conn","Error to json"+e.toString());
	    	}
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
            String msg="Data Entered Successfully!!!";
            res.setText(msg);
	    	}
	    	catch(Exception e){
	    		Log.e("conn",e.toString());
	    		e.printStackTrace();
	    	}
	    }
	}