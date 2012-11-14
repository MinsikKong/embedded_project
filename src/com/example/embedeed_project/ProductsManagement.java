package com.example.embedeed_project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ProductsManagement extends Activity {
	//상품관리(공민식)
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.products_management);

		Button b1 = (Button) findViewById(R.id.addItemButton);

		b1.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ProductsManagement.this,
						AddItem.class);
				startActivity(intent);
			}
		});

	}

}
