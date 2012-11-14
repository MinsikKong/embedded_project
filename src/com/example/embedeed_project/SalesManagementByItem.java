package com.example.embedeed_project;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

public class SalesManagementByItem extends Activity {
	// 판매(공민식)
	private ArrayList<String> list;
	private ArrayAdapter<String> adapter;
	private ListView productList;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sales_management_by_item);

		Spinner spinner = (Spinner) findViewById(R.id.SalesManagementByItemSpinner);
		// Create an ArrayAdapter using the string array and a default spinner
		// layout
		ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter
				.createFromResource(this, R.array.SalesManagementByItemSpinner,
						android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		spinnerAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner.setAdapter(spinnerAdapter);

		productList = (ListView) findViewById(R.id.SalesManagementByItemList);
		list = new ArrayList<String>();

		list.add("a");
		list.add("s");
		list.add("d");
		list.add("g");
		list.add("5");
		list.add("6");
		list.add("7");
		list.add("8");
		list.add("9");
		// adapter.notifyDataSetChanged();

		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, list);
		productList.setAdapter(adapter);

	}
}
