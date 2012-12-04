package com.example.embedeed_project;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ShopInfo extends Activity {
	//매장정보(박민성)

	EditText edit1;
	EditText edit2;
	EditText edit3;
	EditText edit4;
	EditText edit5;
	EditText edit6;
	EditText edit7;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
		setContentView(R.layout.shop_info);
		
		SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
		
		edit1 = (EditText)findViewById(R.id.ShopInfoEditText1);
		edit2 = (EditText)findViewById(R.id.ShopInfoEditText2);
		edit3 = (EditText)findViewById(R.id.ShopInfoEditText3);
		edit4 = (EditText)findViewById(R.id.ShopInfoEditText4);
		edit5 = (EditText)findViewById(R.id.ShopInfoEditText5);
		edit6 = (EditText)findViewById(R.id.ShopInfoEditText6);
		edit7 = (EditText)findViewById(R.id.ShopInfoEditText7);
		
		edit1.setText(pref.getString("edit1", ""));
		edit2.setText(pref.getString("edit2", ""));
		edit3.setText(pref.getString("edit3", ""));
		edit4.setText(pref.getString("edit4", ""));
		edit5.setText(pref.getString("edit5", ""));
		edit6.setText(pref.getString("edit6", ""));
		edit7.setText(pref.getString("edit7", ""));
		
		Button saveButton = (Button)findViewById(R.id.ShopInfoSaveButton);
		saveButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
				SharedPreferences.Editor editor = pref.edit();
				
				editor.putString("edit1", edit1.getText().toString());
				editor.putString("edit2", edit2.getText().toString());
				editor.putString("edit3", edit3.getText().toString());
				editor.putString("edit4", edit4.getText().toString());
				editor.putString("edit5", edit5.getText().toString());
				editor.putString("edit6", edit6.getText().toString());
				editor.putString("edit7", edit7.getText().toString());
				
				editor.commit();
				Toast.makeText(ShopInfo.this, "저장되었습니다.", Toast.LENGTH_SHORT).show();
			}
		});
	}
}
