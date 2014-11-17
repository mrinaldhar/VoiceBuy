package com.example.gui;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import net.viralpatel.android.imagegalleray.R1;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import android.content.SharedPreferences;

public class HomeScreen extends Fragment {
	Button log_main,sign_main,log_in,sign_in,upload,twitter_button;
	//AutoCompleteTextView country;
	View logv;
	TextView imgpath;
	ImageView imageView,proPic;
	String picturePath;
	InputStream is;
	EditText sign_lname,log_email,log_pass,sign_fname,sign_mobile,sign_email,sign_pass,sign_pass_conf;
	private static int RESULT_LOAD_IMAGE = 1;
	public static int RESULT_OK=-1;
	   @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	        View v = inflater.inflate(R.layout.activity_home_screen, container, false);
	   
	     log_in=(Button) v.findViewById(R.id.login_button);
	     sign_in=(Button) v.findViewById(R.id.signup_button);
	     twitter_button=(Button) v.findViewById(R.id.twitter_button);
	     upload=(Button) v.findViewById(R.id.upload_image);
	     imageView = (ImageView) (v.findViewById(R.id.imgView));
	     imgpath=(TextView)v.findViewById(R.id.image_path);
	     imgpath.setText("No Image Selected");
	     proPic=(ImageView) v.findViewById(R.id.pic_disp);
	     log_email=(EditText) v.findViewById(R.id.login_email);
	     log_email.setTextColor(Color.parseColor("#000000"));
	     log_pass=(EditText) v.findViewById(R.id.login_password);
	     log_pass.setTextColor(Color.parseColor("#000000"));
	     sign_fname=(EditText) v.findViewById(R.id.signup_fname);
	     sign_fname.setTextColor(Color.parseColor("#000000"));
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
	     Log.e("lol","finished views");
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
					String email=sign_email.getText().toString();
					String mpass=sign_pass.getText().toString();
					String rpass=sign_pass_conf.getText().toString();
					
				    
				
			           MultipartEntityBuilder multipartEntity =MultipartEntityBuilder.create();
	 
			            multipartEntity.addTextBody("Firstname", fname);
			            multipartEntity.addTextBody("Email", email);
			            multipartEntity.addTextBody("Password", mpass);
			            multipartEntity.addPart("Image",  new FileBody(new File(picturePath)));
	
					if(mpass.equals(rpass)){
						
						String url="http://rohithdb.besaba.com/signup_app.php";
						postdata(url,multipartEntity);
						
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
	     upload.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					
					Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
					i.putExtra("view","v");
					startActivityForResult(i, RESULT_LOAD_IMAGE);
				}
			});
	     
	
	     return v;
	        
	    }
	    @Override
		public void onActivityResult(int requestCode, int resultCode, Intent data) {
			// TODO Auto-generated method stub
			super.onActivityResult(requestCode, resultCode, data);
	super.onActivityResult(requestCode, resultCode, data);
	    	Bundle v=getActivity().getIntent().getBundleExtra("view");
			if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
				Uri selectedImage = data.getData();
				String[] filePathColumn = { MediaStore.Images.Media.DATA };

				Cursor cursor = getActivity().getContentResolver().query(selectedImage,
						filePathColumn, null, null, null);
				cursor.moveToFirst();

				int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
				picturePath = cursor.getString(columnIndex);
				cursor.close();
				imgpath.setText(picturePath);
				imageView.setVisibility(View.VISIBLE);
			//	ImageView imageView = (ImageView)findViewById(R.id.imgView);
				imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
			
			}
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
	    		String pro=json.getString("Image");
	    		byte[] decodedString = Base64.decode(pro, Base64.NO_PADDING);
	    		Log.e("code",decodedString.toString());
	    		Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0,decodedString.length);
	    		
	    		if(s.equals(psswd)){
	    			proPic.setVisibility(View.VISIBLE);
	    			proPic.setImageBitmap(decodedByte);
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
	    public void postdata(String url,MultipartEntityBuilder multipartEntity){
	    	try{
	    	DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(multipartEntity.build());
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