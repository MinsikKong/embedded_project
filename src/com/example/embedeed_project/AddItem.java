package com.example.embedeed_project;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AddItem extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_item);

		// 오픈소스 "ZXing"을 이용한 "Barcode Scanner"
		// app(https://zxing.googlecode.com/files/BarcodeScanner4.31.apk)을
		// intent로 실행, 결과 받아옴
		final Intent intent = new Intent("com.google.zxing.client.android.SCAN");
		intent.setPackage("com.google.zxing.client.android");
		Button b1 = (Button) findViewById(R.id.barcodeReadButton);

		Toast.makeText(AddItem.this, intent.getStringExtra("code"),
				Toast.LENGTH_SHORT).show();

		b1.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				startActivityForResult(intent, 0);
			}
		});

	}

	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		if (requestCode == 0) {
			if (resultCode == RESULT_OK) {
				String s = intent.getStringExtra("SCAN_RESULT");
				Toast.makeText(AddItem.this, s, Toast.LENGTH_SHORT).show();

			} else if (resultCode == RESULT_CANCELED) {
				Toast.makeText(AddItem.this, "Cancel", Toast.LENGTH_SHORT)
						.show();
			}
		}
	}
}