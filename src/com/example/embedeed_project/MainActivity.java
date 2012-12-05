package com.example.embedeed_project;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	boolean dbInstalled = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// DB인스톨
		if (!dbInstalled)
			dbInstall();

		GridView gridview = (GridView) findViewById(R.id.gridview);
		gridview.setAdapter(new ImageAdapter(this));

		AlertDialog.Builder builder;
		AlertDialog alertDialog;
		 // instantiate the dialog with the custom Theme

		Context mContext = getApplicationContext();
		LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.shopinfo_custom_dialog, (ViewGroup)findViewById(R.id.shoppiclayout));
		
		ImageView image = (ImageView)layout.findViewById(R.id.shopimg);
		image.setImageResource(R.drawable.ic_launcher);
		TextView text = (TextView)layout.findViewById(R.id.shoptext);
		
		
		builder = new AlertDialog.Builder(mContext);
		builder.setView(layout);
		builder.setPositiveButton("OK",new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int arg1) {
				dialog.dismiss();
			}
		});
		alertDialog = builder.create();
		
		gridview.setOnItemClickListener(new OnItemClickListener() {
			
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				switch (position) {
				case 0: // 매출관리
					Intent intent0 = new Intent(MainActivity.this,
							SalesManagement.class);
					startActivity(intent0);
					break;

				case 1: // 매입관리
					Intent intent1 = new Intent(MainActivity.this,
							PurchaseManagement.class);
					startActivity(intent1);
					break;

				case 2: // 상품관리
					Intent intent2 = new Intent(MainActivity.this,
							ItemManagement.class);
					startActivity(intent2);
					break;

				case 3: // 매장정보
					Intent intent3 = new Intent(MainActivity.this,
							ShopInfo.class);
					startActivity(intent3);
					break;

				case 4: // POS
					Intent intent4 = new Intent(MainActivity.this,
							PurchaseMain.class);
					startActivity(intent4);
					break;

				case 5: // 업체광고
					Intent intent5 = new Intent(MainActivity.this,
							Advertisement.class);
					startActivity(intent5);
					break;

				case 6: // 카페 가기
					Intent intent6 = new Intent(MainActivity.this, Cafe.class);
					startActivity(intent6);
					break;

				case 7: // 옵션
					Intent intent7 = new Intent(MainActivity.this, Option.class);
					startActivity(intent7);
					break;

				case 8: // 정보
					Intent intent8 = new Intent(MainActivity.this,
							AppInfo.class);
					startActivity(intent8);
					break;
				}
			}
		});
	}

	public void dbInstall() {
		AssetManager assetManager = getResources().getAssets();
		File file = new File(Const.DATABASE_PATH);

		FileOutputStream fileOutputStream = null;
		BufferedOutputStream bufferedOutputStream = null;

		try {
			if (!file.exists()) {
				file.delete();
				file.createNewFile();
				InputStream inputStream = assetManager
						.open(Const.DATABASE_NAME);
				BufferedInputStream bufferedInputStream = new BufferedInputStream(
						inputStream);

				fileOutputStream = new FileOutputStream(file);
				bufferedOutputStream = new BufferedOutputStream(
						fileOutputStream);

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
			}
			dbInstalled = true;
		} catch (IOException e) {

		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
}
