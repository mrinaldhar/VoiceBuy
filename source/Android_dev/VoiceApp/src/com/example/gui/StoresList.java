package com.example.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class StoresList extends Fragment {

@Override
public void onActivityCreated(Bundle savedInstanceState) {
	super.onActivityCreated(savedInstanceState);
	String[] stores = {"Flipkart","Myntra","Amazon","eBay","SnapDeal","Jabong","Yebhi"};

	 ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,stores);
     adapter.sort(null);
     ListView list = (ListView)getActivity().findViewById(R.id.listview);
     list.setAdapter(adapter);
   list.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {

   	@Override
       public void onItemClick(AdapterView<?> a, View v, int position,
               long id) {
    Intent intent = new Intent(getActivity().getApplicationContext(), VoiceRecognitionActivity.class);
    String store_name = a.getItemAtPosition(position).toString();
    System.out.println("Store:" + store_name);
    intent.putExtra("getstore",store_name);
    startActivity(intent);
           }
       
   });
}


public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.activity_stores_list, container, false);
	
    
    return v;
}

public static StoresList newInstance(String text) {

    StoresList store = new StoresList();

    return store;
}
}