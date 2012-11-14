package com.example.embedeed_project;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class PurchaseManagement extends Activity {
	// 판매(공민식)
	private ArrayList<String> list;
	private ArrayAdapter<String> adapter;
	private ListView productList;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.purchase_management);

		productList = (ListView) findViewById(R.id.PurchaseList);
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

		Button byDay = (Button) findViewById(R.id.PurchaseByDayButton);
		byDay.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(PurchaseManagement.this,
						PurchaseManagementByDate.class);
				startActivity(intent);
			}
		});

		Button byCompany = (Button) findViewById(R.id.PurchaseByCompanyButton);
		byCompany.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(PurchaseManagement.this,
						SalesManagementByCompany.class);
				startActivity(intent);
			}
		});

		Button byItem = (Button) findViewById(R.id.PurchaseByItemButton);
		byItem.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(PurchaseManagement.this,
						SalesManagementByItem.class);
				startActivity(intent);
			}
		});
	}
}
