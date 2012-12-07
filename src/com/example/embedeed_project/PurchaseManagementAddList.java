package com.example.embedeed_project;

import java.util.Locale;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PurchaseManagementAddList extends Activity {

	EditText business_name, product_name, amount, price, note;
	Button confirmButton, cancelButton;
	Cursor cursor;
	SQLiteDatabase db;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.purchase_management_add);

		business_name = (EditText) findViewById(R.id.PurchaseManagementAddListEditText1);
		product_name = (EditText) findViewById(R.id.PurchaseManagementAddListEditText2);
		amount = (EditText) findViewById(R.id.PurchaseManagementAddListEditText3);
		price = (EditText) findViewById(R.id.PurchaseManagementAddListEditText4);
		note = (EditText) findViewById(R.id.PurchaseManagementAddListEditText5);
		confirmButton = (Button) findViewById(R.id.PurchaseManagementAddListButton1);
		cancelButton = (Button) findViewById(R.id.PurchaseManagementAddListButton2);

		confirmButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				// note(메모)제외한 나머지 항목이 비었을때 에러 출력
				if (business_name.getText().equals("")
						|| product_name.getText().equals("")
						|| amount.getText().equals("")
						|| price.getText().equals("")) {
					Toast.makeText(getApplicationContext(),
							"매입 정보를 올바로 입력해주세요", Toast.LENGTH_SHORT).show();

				} else {
					// db 설정
					db = openOrCreateDatabase(Const.DATABASE_NAME,
							MODE_PRIVATE, null);
					db.setVersion(1);
					db.setLocale(Locale.getDefault());
					db.setLockingEnabled(true);

					// edittext의 내용을 db에 insert함
					db.execSQL("insert into purchase (business_name, product_name, amount, price, note) values ('"
							+ business_name.getText()
							+ "','"
							+ product_name.getText()
							+ "','"
							+ amount.getText()
							+ "','"
							+ price.getText()
							+ "','"
							+ note.getText()
							+ "');");
				}
				finish();
			}
		});

		cancelButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});

	}
}
