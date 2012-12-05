package com.example.embedeed_project;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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
	
	public String getShopFileName(){
		return getSharedPreferences("pref2", Activity.MODE_PRIVATE).getString("edit1", "");
	}
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
		setContentView(R.layout.shop_info);
		
		SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
		
		edit1 = (EditText)findViewById(R.id.ShopInfoEditText1);//사업자번호
		edit2 = (EditText)findViewById(R.id.ShopInfoEditText2);//매장명
		edit3 = (EditText)findViewById(R.id.ShopInfoEditText3);//대표명
		edit4 = (EditText)findViewById(R.id.ShopInfoEditText4);//주소
		edit5 = (EditText)findViewById(R.id.ShopInfoEditText5);//전화
		edit6 = (EditText)findViewById(R.id.ShopInfoEditText6);//환경정보
		edit7 = (EditText)findViewById(R.id.ShopInfoEditText7);//위치정보
		
		edit1.setText(pref.getString("edit1", ""));
		edit2.setText(pref.getString("edit2", ""));
		edit3.setText(pref.getString("edit3", ""));
		edit4.setText(pref.getString("edit4", ""));
		edit5.setText(pref.getString("edit5", ""));
		edit6.setText(pref.getString("edit6", ""));
		edit7.setText(pref.getString("edit7", ""));
		
		Button picButton = (Button)findViewById(R.id.ShopInfoPicButton);
		picButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Boolean isFileExist = false;	//매장사진이 존재하는지 판단
		        int columnIndex = 0;			//해당 사진이 몇번째 컬럼에 있는지
		        
		        //매장사진을 띄우기위한 커스텀 다이어로그 생성
				AlertDialog.Builder builder;
				AlertDialog dialog;
				LayoutInflater inflater = (LayoutInflater) ShopInfo.this
						.getSystemService(LAYOUT_INFLATER_SERVICE);
				View layout = inflater.inflate(R.layout.shopinfo_custom_dialog,
						(ViewGroup) findViewById(R.id.shoppiclayout));


		        //타이틀들을 가져옴
		        String[] projection2 = {MediaStore.Images.Media.TITLE};
		        Cursor cursor = managedQuery( MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
		                projection2, // Which columns to return
		                null,       // Return all rows
		                null,       
		                null); 
		        for(int i=0;i<cursor.getCount();i++){
		        	//타이틀이 지정된 DB명과 일치하는 파일의 컬럼번호를 찾는다.
		        	cursor.moveToPosition(i);
		        	if(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.TITLE)).equals(getShopFileName())){
		        		//파일명이 같으면
		        		isFileExist = true;	//존재함 
		        		columnIndex = i;   //해당 컬럼번호 저장
		        		break;
		        	}
		        }
		        
		        if(isFileExist){
		        	//파일이 존재할시 이미지를 띄워줌
			        String[] projection = {MediaStore.Images.Media._ID};
		        	cursor = managedQuery( MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
			                projection, 
			                null,       
			                null,       
			                null); 	        
		            cursor.moveToPosition(columnIndex);//해당 컬럼으로 이동
		            //이미지를 띄우기 위한 이미지뷰
					ImageView img = (ImageView) layout.findViewById(R.id.shopimg);
					//이미지를 가져오기위해 Uri로 만듬
					long id = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID));
					Uri uri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id);
					
					try{
						//ContentResolver를 사용하여 해당 Uri에서 비트맵 이미지를 가져와서 뿌려줌
						Bitmap bm = Images.Media.getBitmap(getContentResolver(), uri);
						img.setImageBitmap(bm);
					} catch (Exception e) {
					}
		        }
		        cursor.close();

				builder = new AlertDialog.Builder(ShopInfo.this);
				builder.setView(layout);
				dialog = builder.create();
				dialog.setTitle("매장사진");
				dialog.setButton("확인", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// 확인 누르면 다이어로그 꺼짐
						dialog.dismiss();
					}
				});
				dialog.show();
			}
		});
		
		Button saveButton = (Button)findViewById(R.id.ShopInfoSaveButton);
		saveButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				//상점 정보 저장
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
