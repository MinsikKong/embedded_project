package com.example.embedeed_project;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;

public class PurchaseMain extends Activity {
	//구매(박민성)
	private ArrayList<String> productArray;
	private ArrayList<String> orderArray;
	private ArrayAdapter<String> productAdapter;
	private ArrayAdapter<String> orderAdapter;
	private GridView productList;
	private ListView orderList;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
		setContentView(R.layout.purchase_main);
		
		productList = (GridView)findViewById(R.id.productList);
		orderList = (ListView)findViewById(R.id.orderList);
		productArray = new ArrayList<String>();
		orderArray = new ArrayList<String>();

		productArray.add("1");
		productArray.add("2");
		productArray.add("3");
		productArray.add("4");
		orderArray.add("5");
		orderArray.add("6");
		orderArray.add("7");
		orderArray.add("8");
		orderArray.add("9");
		
		// adapter.notifyDataSetChanged();

		productAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, productArray);
		orderAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, orderArray);
	    productList.setAdapter(productAdapter);
	    orderList.setAdapter(orderAdapter);
	}
}
