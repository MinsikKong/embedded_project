package com.example.embedeed_project;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Vector;

import android.R.string;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class PurchasePay extends Activity {
	SQLiteDatabase db;
	private int totalPay;
	private ArrayList<OrderItemBean> orderArray;
	private enum payType {현금, 카드};
	private payType selected;
	
	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.purchase_pay);
	    
	    //이전 인텐트에서의 주문정보를 받아옴
	    Intent intent = getIntent();
		totalPay = intent.getIntExtra("totalPrice", -1);
		orderArray = (ArrayList<OrderItemBean>) intent.getSerializableExtra("orderArray");
	    
		//총금액 출력
	    TextView totalPayText = (TextView)findViewById(R.id.totalPayPrice);
	    totalPayText.setText(totalPay + "원");
	    
	    Button cashButton = (Button)findViewById(R.id.cashPay);
	    cashButton.setOnClickListener(new OnClickListener(){
	    	//현금버튼을 눌렀을때의 다이어로그정의
			@Override
			public void onClick(View v) {
		    	selected=payType.현금;
				final CharSequence[] items = {"영수증출력", "완료", "취소"};
				AlertDialog.Builder ad = new AlertDialog.Builder(PurchasePay.this);
				ad.setTitle("현금결제");
				ad.setItems(items, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int item) {
						if(item==0){
							//영수증 출력 루틴
							final CharSequence[] receipt_items = {"Wi-fi로 출력", "블루투스로 출력", "취소"};
							AlertDialog.Builder Receipt_ad = new AlertDialog.Builder(PurchasePay.this);
							Receipt_ad.setTitle("영수증 출력");
							Receipt_ad.setItems(receipt_items, new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int receipt_item) {
									if(receipt_item==0){
										//Wi-Fi
										
										salesToDB();	//정상적으로 출력되었으면 DB에 저장함
									} else if(receipt_item==1){
										//BT

										salesToDB();	//정상적으로 출력되었으면 DB에 저장함
									} else if(receipt_item==2){
										//Cancel
										
									}
								}
							});
							AlertDialog ad2 = Receipt_ad.create();
							ad2.show();				
						} else if(item==1){
							//DB에 저장하고 메인화면으로
							salesToDB();
							setResult(RESULT_OK);
							finish();
						} else if(item==2){
							//뒤로
						}
					}
				});
				AlertDialog ad2 = ad.create();
				ad2.show();				
			}
		});

	    Button cardButton = (Button)findViewById(R.id.cardPay);
	    cardButton.setOnClickListener(new OnClickListener(){
	    	//카드 버튼을 눌렀을때의 다이어로그 정의
			@Override
			public void onClick(View v) {
		    	selected=payType.카드;
				
				
			}
		});
	}
	
	
	public void salesToDB(){
		String sql = new String("INSERT INTO sales(all_price,represent_product,sales_type) VALUES("+totalPay+",'"+orderArray.get(0).itemName+
				"','"+selected.name()+"');");
		
		// db옆고 세팅
		db = openOrCreateDatabase(Const.DATABASE_NAME, MODE_PRIVATE, null);
		db.setVersion(1);
		db.setLocale(Locale.getDefault());
		db.setLockingEnabled(true);
		
		//해당 목록 저장
		db.execSQL(sql);

		Cursor cursor = db.rawQuery("select * from sqlite_sequence where name='sales'", null);
		cursor.moveToFirst();
		for(int i=0;i<orderArray.size();i++){
			String sqls = new String("INSERT INTO soldproducts(sales_num,product_code,amount) VALUES("+cursor.getInt(1)+",'"+orderArray.get(i).productCode+
				"','"+orderArray.get(i).amount+"');");
			db.execSQL(sqls);
		}
		db.close();
	}
}
