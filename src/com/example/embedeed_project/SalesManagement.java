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

public class SalesManagement extends Activity {
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
		setContentView(R.layout.sales_management);

		list = new ArrayList<String>();

		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, list);
		itemList = (ListView) findViewById(R.id.SalesManagementList);
		itemList.setAdapter(adapter);

		db = openOrCreateDatabase(Const.DATABASE_NAME, MODE_PRIVATE, null);
		db.setVersion(1);
		db.setLocale(Locale.getDefault());
		db.setLockingEnabled(true);

		// "item" table에서 item 이름을 불러와서 list에 추가
		// ///////////////////////////////////////////////////////////////////
		// ///////////////////////////////////////////////////////////////////
		// ///////////////////////////////////////////////////////////////////
		// ///////////////////////////////////////////////////////////////////
		// ///////////////////////////////////////////////////////////////////
		// ///////////////////////////////////////////////////////////////////
		// ///////////////////////////////////////////////////////////////////
		// 매출 쿼리 짜야됨 아오 졸려
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
		itemList = (ListView) findViewById(R.id.SalesManagementList);
		itemList.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.sales_management, menu);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case R.id.SalesManagementByDateMenu: // 일자별
			Intent intent1 = new Intent(SalesManagement.this,
					SalesManagementByDate.class);
			startActivity(intent1);
			break;

		case R.id.SalesManagementByCompanyMenu: // 업체별
			Intent intent2 = new Intent(SalesManagement.this,
					SalesManagementByCompany.class);
			startActivity(intent2);
			break;

		case R.id.SalesManagementByItemMenu: // 상품별
			Intent intent3 = new Intent(SalesManagement.this,
					SalesManagementByItem.class);
			startActivity(intent3);
			break;

		case R.id.SalesManagementAddListMenu: // 매입내역 추가
			Intent intent4 = new Intent(SalesManagement.this,
					SalesManagementAddList.class);
			startActivity(intent4);
			break;
		}
		return true;
	}
}
