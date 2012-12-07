package com.example.embedeed_project;

import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ItemManagementAddItem extends Activity {

	EditText img, category, name, price, stock, barcode, note;
	Button confirmButton, cancelButton, barcodeButton;
	Cursor cursor;
	SQLiteDatabase db;
	String barcodeNumber = "";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.item_management_add);

		img = (EditText) findViewById(R.id.ItemManagementAddListEditText1);
		category = (EditText) findViewById(R.id.ItemManagementAddListEditText2);
		name = (EditText) findViewById(R.id.ItemManagementAddListEditText3);
		price = (EditText) findViewById(R.id.ItemManagementAddListEditText4);
		stock = (EditText) findViewById(R.id.ItemManagementAddListEditText5);
		barcode = (EditText) findViewById(R.id.ItemManagementAddListEditText6);
		note = (EditText) findViewById(R.id.ItemManagementAddListEditText7);
		confirmButton = (Button) findViewById(R.id.ItemManagementAddListButton1);
		cancelButton = (Button) findViewById(R.id.ItemManagementAddListButton2);
		barcodeButton = (Button) findViewById(R.id.ItemManagementAddListButton3);

		confirmButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				// note(메모)제외한 나머지 항목이 비었을때 에러 출력
				if (img.getText().equals("") || category.getText().equals("")
						|| name.getText().equals("")
						|| price.getText().equals("")
						|| stock.getText().equals("")
						|| barcode.getText().equals("")) {
					Toast.makeText(getApplicationContext(),
							"상품 정보를 올바로 입력해주세요", Toast.LENGTH_SHORT).show();

				} else {
					db = openOrCreateDatabase(Const.DATABASE_NAME,
							MODE_PRIVATE, null);
					db.setVersion(1);
					db.setLocale(Locale.getDefault());
					db.setLockingEnabled(true);

					// Edittext의 내용을 db에 insert
					db.execSQL("insert into product (img, category, name, price, stock, barcode, note) values ('"
							+ img.getText()
							+ "','"
							+ category.getText()
							+ "','"
							+ name.getText()
							+ "','"
							+ price.getText()
							+ "','"
							+ stock.getText()
							+ "','"
							+ barcode.getText()
							+ "','"
							+ note.getText()
							+ "');");
				}
				finish();
			}
		});

		// 취소버튼
		cancelButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});

		// 바코드 스캐너로 바코드 입력
		barcodeButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {// 오픈소스 "ZXing"을 이용한 "Barcode Scanner"
				// app(https://zxing.googlecode.com/files/BarcodeScanner4.31.apk)을
				// intent로 실행, 결과 받아옴
				final Intent intent = new Intent(
						"com.google.zxing.client.android.SCAN");

				intent.setPackage("com.google.zxing.client.android");

				startActivityForResult(intent, 0);
			}
		});

	}

	// 바코드 스캐너에서 결과 받아와서 edittext에 값 설정
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		if (requestCode == 0) {
			if (resultCode == RESULT_OK) {
				barcodeNumber = intent.getStringExtra("SCAN_RESULT");
				barcode.setText(barcodeNumber);

			} else if (resultCode == RESULT_CANCELED) {
				Toast.makeText(ItemManagementAddItem.this, "Cancel",
						Toast.LENGTH_SHORT).show();
			}
		}
	}
}
