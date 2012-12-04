package com.example.embedeed_project;

import java.util.ArrayList;
import java.util.Locale;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SalesManagementByCompany extends Activity {
	// 판매(공민식)
	private ArrayList<purchaseListBean> list;
	private ItemCustomAdapter adapter;
	private ListView productList;
	Cursor cursor;
	SQLiteDatabase db;
	public int count;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sales_management_by_company);

		// Spinner에 업체 리스트(strings.xml)추가
		final Spinner spinner = (Spinner) findViewById(R.id.SalesManagementByCompanySpinner);
		ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter
				.createFromResource(this,
						R.array.SalesManagementByCompanySpinnerArray,
						android.R.layout.simple_spinner_item);
		spinnerAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(spinnerAdapter);

		// Spinner의 Item 선택시 event
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {

				db = openOrCreateDatabase(Const.DATABASE_NAME, MODE_PRIVATE,
						null);
				db.setVersion(1);
				db.setLocale(Locale.getDefault());
				db.setLockingEnabled(true);

				// 특정 일자 사이에 있는 매입 조회
				// ////////////////////////////////////////////////////////////////////////////////////
				// ////////////////////////////////////////////////////////////////////////////////////////////////////////
				// ////////////////////////////////////////////////////////////////////////////////////////////////////////
				// ////////////////////////////////////////////////////////////////////////////////////////////////////////
				// ////////////////////////////////////////////////////////////////////////////////////////////////////////
				// //////////////////
				// product table에 회사 컬럼 ㅇ벗음
				cursor = db.rawQuery("select * from product where price=" + "'"
						+ (String) spinner.getSelectedItem() + "'", null);

				// 해당 내역이 없으면 메시지 뿌림
				if (cursor.moveToFirst()) {
					cursor.moveToFirst();
				} else {
					Toast.makeText(SalesManagementByCompany.this,
							"해당 업체의 상품이 없습니다", Toast.LENGTH_SHORT).show();
				}

				// select된 매입 내역을 list에 추가
				for (count = cursor.getCount(); count > 0; count--) {
					purchaseListBean item = new purchaseListBean(cursor
							.getString(3), cursor.getInt(4), cursor.getInt(5));
					list.add(item);
					cursor.moveToNext();

				}
				adapter.notifyDataSetChanged(); // listview refresh
				cursor.close();
				db.close();

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		list = new ArrayList<purchaseListBean>();
		productList = (ListView) findViewById(R.id.SalesManagementByCompanyList);
		productList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// 리스트 아이템 터치시
			}
		});

		adapter = new ItemCustomAdapter(this, R.layout.product_listview, list);
		productList.setAdapter(adapter);

	}

	class purchaseListBean {
		public String productName;// 상품명
		public int quantity; // 수량
		public int price; // 가격

		public purchaseListBean(String productName, int quantity, int price) {
			this.productName = productName;
			this.quantity = quantity;
			this.price = price;
		}
	}

	// 매입내역 출력시 Listview에 상품이름, 개수, 총 가격을 표시하는 Custom Adapter
	public class ItemCustomAdapter extends BaseAdapter {
		Context context;
		LayoutInflater inflater;

		ArrayList<purchaseListBean> arrayList = new ArrayList<purchaseListBean>();

		TextView itemNameText, quantityText, totalPriceText;

		private int layout;

		public ItemCustomAdapter(Context context, int layout,
				ArrayList<purchaseListBean> arrayList) {
			this.context = context;
			inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			this.layout = layout;
			this.arrayList = arrayList;
		}

		public int getCount() {
			return arrayList.size();
		}

		public Object getItem(int position) {
			return arrayList.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int i, View convertView, ViewGroup parent) {
			final int finalPosition = i;
			if (convertView == null) {
				convertView = inflater.inflate(layout, parent, false);
			}
			itemNameText = (TextView) convertView
					.findViewById(R.id.productListviewProductName);
			quantityText = (TextView) convertView
					.findViewById(R.id.productListviewQuantity);
			totalPriceText = (TextView) convertView
					.findViewById(R.id.productListviewTotal);
			itemNameText.setText(arrayList.get(i).productName);
			quantityText.setText(arrayList.get(i).quantity + "개");
			totalPriceText.setText("총 " + arrayList.get(i).price
					* arrayList.get(i).quantity + "원");

			return convertView;
		}
	}
}
