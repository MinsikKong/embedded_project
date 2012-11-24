package com.example.embedeed_project;

import java.util.ArrayList;
import java.util.Locale;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

public class PurchaseManagementByCompany extends Activity {
	// 판매(공민식)
	private ArrayList<String> list;
	private ArrayAdapter<String> adapter;
	private ListView productList;
	Cursor cursor;
	SQLiteDatabase db;
	public int count;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.purchase_management_by_company);

		Spinner spinner = (Spinner) findViewById(R.id.PurchaseManagementByCompanySpinner);
		// Create an ArrayAdapter using the string array and a default spinner
		// layout
		ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter
				.createFromResource(this,
						R.array.PurchaseManagementByCompanySpinner,
						android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		spinnerAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner.setAdapter(spinnerAdapter);

		productList = (ListView) findViewById(R.id.PurchaseManagementByCompanyList);
		list = new ArrayList<String>();

		// adapter.notifyDataSetChanged();

		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, list);
		productList.setAdapter(adapter);

	}
}
