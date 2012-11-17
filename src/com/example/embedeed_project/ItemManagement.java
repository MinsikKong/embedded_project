package com.example.embedeed_project;

import java.util.ArrayList;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class ItemManagement extends Activity {
	// 상품관리(공민식)

	private ArrayList<String> list; // listview의 목록
	private ArrayAdapter<String> adapter;
	private ListView itemList;

	Cursor cursor;
	SQLiteDatabase db;
	public static final String DB_PATH = "/sdcard/db.db";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.item_management);

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
		itemList = (ListView) findViewById(R.id.ItemManagementList);
		itemList.setAdapter(adapter);

		Button b1 = (Button) findViewById(R.id.ItemManagementButton1);
		Button b2 = (Button) findViewById(R.id.ItemManagementButton2);
		Button b3 = (Button) findViewById(R.id.ItemManagementButton3);

		b1.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ItemManagement.this,
						ItemManagementByDate.class);
				startActivity(intent);
			}
		});

		b2.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ItemManagement.this,
						ItemManagementByCompany.class);
				startActivity(intent);
			}
		});

		b3.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ItemManagement.this,
						ItemManagementAddItem.class);
				startActivity(intent);
			}
		});

	}

}
