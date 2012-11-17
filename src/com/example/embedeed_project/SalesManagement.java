package com.example.embedeed_project;

import java.util.ArrayList;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class SalesManagement extends Activity {
	// 판매(공민식)
	private ArrayList<String> list;
	private ArrayAdapter<String> adapter;
	private ListView itemList;
	Cursor cursor;
	SQLiteDatabase db;
	@SuppressLint("SdCardPath")
	public static final String DB_PATH = "/sdcard/db.db";

	@SuppressLint("SdCardPath")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sales_management);

		db = openOrCreateDatabase(DB_PATH, MODE_PRIVATE, null);
		db.setVersion(1);
		db.setLocale(Locale.getDefault());
		db.setLockingEnabled(true);

		list = new ArrayList<String>();

		// "item" table에서 item 이름을 불러와서 list에 추가
		cursor = db.rawQuery("select * from item", null);
		cursor.moveToFirst();
		while (!cursor.isLast()) {
			String item_name = cursor.getString(2);
			list.add(item_name);
			cursor.moveToNext();
		}
		cursor.close();

		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, list);

		itemList = (ListView) findViewById(R.id.SalesManagementList);
		itemList.setAdapter(adapter);

		Button byDay = (Button) findViewById(R.id.SalesManagementButton1);
		byDay.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(SalesManagement.this,
						SalesManagementByDate.class);
				startActivity(intent);
			}
		});

		Button byCompany = (Button) findViewById(R.id.SalesManagementButton2);
		byCompany.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(SalesManagement.this,
						SalesManagementByCompany.class);
				startActivity(intent);
			}
		});

		Button byItem = (Button) findViewById(R.id.SalesManagementButton3);
		byItem.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(SalesManagement.this,
						SalesManagementByItem.class);
				startActivity(intent);
			}
		});
	}
}
