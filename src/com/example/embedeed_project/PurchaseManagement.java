package com.example.embedeed_project;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Locale;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class PurchaseManagement extends Activity {
	// 판매(공민식)
	private ArrayList<String> list; // listview의 목록
	private ArrayAdapter<String> adapter;
	private ListView itemList;
	public static final String DATABASE_NAME = "posDB.db";
	public static final String DATABASE_PATH = "/data/data/com.example.embedeed_project/databases/posDB.db";
	Cursor cursor;
	SQLiteDatabase db;
	public int count;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.purchase_management);

		list = new ArrayList<String>();

		dbInstall();
		db = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
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
		itemList = (ListView) findViewById(R.id.PurchaseManagementList);
		itemList.setAdapter(adapter);
	}

	public void dbInstall() {
		AssetManager assetManager = getResources().getAssets();
		File file = new File(DATABASE_PATH);

		FileOutputStream fileOutputStream = null;
		BufferedOutputStream bufferedOutputStream = null;

		try {
			InputStream inputStream = assetManager.open(DATABASE_NAME);
			BufferedInputStream bufferedInputStream = new BufferedInputStream(
					inputStream);

			if (file.exists()) {
				file.delete();
				file.createNewFile();
			}

			fileOutputStream = new FileOutputStream(file);
			bufferedOutputStream = new BufferedOutputStream(fileOutputStream);

			int read = -1;
			byte[] buffer = new byte[1024];
			while ((read = bufferedInputStream.read(buffer, 0, 1024)) != -1) {
				bufferedOutputStream.write(buffer, 0, read);
			}

			bufferedOutputStream.flush();

			bufferedOutputStream.close();
			fileOutputStream.close();
			bufferedInputStream.close();
			inputStream.close();
		} catch (IOException e) {
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.purchase_management, menu);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case R.id.PurchaseManagementByDateMenu: // 일자별
			Intent intent1 = new Intent(PurchaseManagement.this,
					PurchaseManagementByDate.class);
			startActivity(intent1);
			break;

		case R.id.PurchaseManagementByCompanyMenu: // 업체별
			Intent intent2 = new Intent(PurchaseManagement.this,
					PurchaseManagementByCompany.class);
			startActivity(intent2);
			break;

		case R.id.PurchaseManagementByItemMenu: // 상품별
			Intent intent3 = new Intent(PurchaseManagement.this,
					PurchaseManagementByItem.class);
			startActivity(intent3);
			break;

		case R.id.PurchaseManagementAddListMenu: // 매입내역 추가
			Intent intent4 = new Intent(PurchaseManagement.this,
					PurchaseManagementAddList.class);
			startActivity(intent4);
			break;
		}
		return true;
	}
}
