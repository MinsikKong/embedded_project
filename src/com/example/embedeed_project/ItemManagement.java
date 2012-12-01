package com.example.embedeed_project;

import java.util.ArrayList;
import java.util.Locale;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ItemManagement extends Activity {
	// 판매(공민식)
	private ArrayList<String> list; // listview의 목록
	private ArrayAdapter<String> adapter;
	private ListView itemList;
	Cursor cursor;
	SQLiteDatabase db;
	public int count;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.item_management);

		list = new ArrayList<String>();

		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, list);
		itemList = (ListView) findViewById(R.id.ItemManagementList);
		itemList.setAdapter(adapter);

		db = openOrCreateDatabase(Const.DATABASE_NAME, MODE_PRIVATE, null);
		db.setVersion(1);
		db.setLocale(Locale.getDefault());
		db.setLockingEnabled(true);

		// "item" table에서 item 이름을 불러와서 list에 추가
		cursor = db.rawQuery("select * from product", null);
		cursor.moveToFirst();

		while (!cursor.isLast()) {
			String item_name = cursor.getString(3);
			list.add(item_name);
			cursor.moveToNext();
		}
		cursor.close();

		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, list);
		itemList = (ListView) findViewById(R.id.ItemManagementList);
		itemList.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.item_management, menu);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case R.id.ItemManagementByDateMenu: // 일자별
			Intent intent1 = new Intent(ItemManagement.this,
					ItemManagementByDate.class);
			startActivity(intent1);
			break;

		case R.id.ItemManagementByCompanyMenu: // 업체별
			Intent intent2 = new Intent(ItemManagement.this,
					ItemManagementByCompany.class);
			startActivity(intent2);
			break;

		case R.id.ItemManagementAddListMenu: // 매입내역 추가
			Intent intent3 = new Intent(ItemManagement.this,
					ItemManagementAddItem.class);
			startActivity(intent3);
			break;
		}
		return true;
	}
}
