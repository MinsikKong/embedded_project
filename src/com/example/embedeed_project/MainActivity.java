package com.example.embedeed_project;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);


		GridView gridview = (GridView) findViewById(R.id.gridview);
		gridview.setAdapter(new ImageAdapter(this));


		gridview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				Toast.makeText(MainActivity.this, "" + position,
						Toast.LENGTH_SHORT).show();

				switch (position) {
				case 0:
					Intent intent0 = new Intent(MainActivity.this,
							SalesManagement.class);
					startActivity(intent0);
					break;

				case 1:
					Intent intent1 = new Intent(MainActivity.this,
							PurchaseManagement.class);
					startActivity(intent1);
					break;

				case 2:
					Intent intent2 = new Intent(MainActivity.this,
							ProductsManagement.class);
					startActivity(intent2);
					break;

				case 3:
					Intent intent3 = new Intent(MainActivity.this,
							ShopInfo.class);
					startActivity(intent3);
					break;

				case 4:
					Intent intent4 = new Intent(MainActivity.this,
							PurchaseMain.class);
					startActivity(intent4);
					break;

				case 5:
					Intent intent5 = new Intent(MainActivity.this,
							Advertisement.class);
					startActivity(intent5);
					break;

				case 6:
					Intent intent6 = new Intent(MainActivity.this, Cafe.class);
					startActivity(intent6);
					break;

				case 7:
					Intent intent7 = new Intent(MainActivity.this, Option.class);
					startActivity(intent7);
					break;

				case 8:
					Intent intent8 = new Intent(MainActivity.this,
							AppInfo.class);
					startActivity(intent8);
					break;
				}
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
}
