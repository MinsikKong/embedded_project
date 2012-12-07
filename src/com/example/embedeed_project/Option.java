package com.example.embedeed_project;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Option extends Activity {
	// 옵션(박민성)

	EditText shopFileName;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.option);
		SharedPreferences pref = getSharedPreferences("pref2", Activity.MODE_PRIVATE);

		shopFileName = (EditText)findViewById(R.id.optionDBFile);//매장 사진 파일명
		shopFileName.setText(pref.getString("edit1", ""));
		
		
		//디비 초기화
		Button dbReset = (Button)findViewById(R.id.DBResetButton);
		dbReset.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				dbInstall();
			}
		});
		
		//옵션 저장
		Button saveOption = (Button)findViewById(R.id.optionSaveButton);
		saveOption.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				//상점 정보 저장
				SharedPreferences pref = getSharedPreferences("pref2", Activity.MODE_PRIVATE);
				SharedPreferences.Editor editor = pref.edit();
				
				editor.putString("edit1", shopFileName.getText().toString());
				
				editor.commit();
				Toast.makeText(Option.this, "저장되었습니다.", Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	public void dbInstall() {
		AssetManager assetManager = getResources().getAssets();
		File file = new File(Const.DATABASE_PATH);

		FileOutputStream fileOutputStream = null;
		BufferedOutputStream bufferedOutputStream = null;

		try {
				file.delete();
				file.createNewFile();
				InputStream inputStream = assetManager.open(Const.DATABASE_NAME);
				BufferedInputStream bufferedInputStream = new BufferedInputStream(
						inputStream);
	
	
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

				Toast.makeText(Option.this, "DB초기화완료", Toast.LENGTH_SHORT).show();
		} catch (IOException e) {

		}
	}

}
