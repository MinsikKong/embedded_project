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
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

public class PurchaseManagementByItem extends Activity {
	// 판매(공민식)
	private ArrayList<String> list;
	private ArrayAdapter<String> adapter;
	private ListView itemList;
	public static final String DATABASE_NAME = "posDB.db";
	public static final String DATABASE_PATH = "/data/data/com.example.embedeed_project/databases/posDB.db";
	Cursor cursor;
	SQLiteDatabase db;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.purchase_management_by_item);

		String[] itemListArray = new String[] {};

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
			cursor.moveToNext();
		}
		cursor.close();

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, itemListArray);

		Spinner sp = (Spinner) findViewById(R.id.PurchaseManagementByItemSpinner);
		sp.setAdapter(adapter);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		list = new ArrayList<String>();
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, list);
		itemList = (ListView) findViewById(R.id.PurchaseManagementByItemList);
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
}
