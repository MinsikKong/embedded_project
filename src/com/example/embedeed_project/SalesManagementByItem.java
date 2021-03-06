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

public class SalesManagementByItem extends Activity {
	// 판매(공민식)
	private ArrayList<salesListBean> list;
	private ItemCustomAdapter adapter;
	private ListView productList;
	Cursor cursor;
	SQLiteDatabase db;
	public int count;

	class salesListBean {
		public String productName;// 상품명
		public int quantity; // 수량
		public int price; // 가격

		public salesListBean(String productName, int quantity, int price) {
			this.productName = productName;
			this.quantity = quantity;
			this.price = price;
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sales_management_by_item);

		// Spinner에 업체 리스트(strings.xml)추가
		final Spinner spinner = (Spinner) findViewById(R.id.SalesManagementByItemSpinner);
		ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter
				.createFromResource(this,
						R.array.SalesManagementByItemSpinnerArray,
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

				// 각 상품명에 해당하는 매출내역 찾음
				cursor = db
						.rawQuery(
								"select b.date, c.amount, a.price from product a, sales b, soldproducts c where a.name='"
										+ (String) spinner.getSelectedItem()
										+ "' and b.sales_num=c.sales_num and a.product_code=c.product_code",
								null);

				// 해당 내역이 없으면 메시지 뿌림
				if (cursor.moveToFirst()) {
					cursor.moveToFirst();
				} else {
					Toast.makeText(SalesManagementByItem.this,
							"해당 상품의 매입내역이 없습니다", Toast.LENGTH_SHORT).show();
				}

				// spinner의 선택 항목이 바뀌었을때 list의 내용 clear
				list.clear();

				// 판매일, 판매상품, 가격, 매출번호 받아옴
				for (count = cursor.getCount(); count > 0; count--) {
					salesListBean item = new salesListBean(cursor.getString(0),
							cursor.getInt(1), cursor.getInt(2));
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

		list = new ArrayList<salesListBean>();
		productList = (ListView) findViewById(R.id.SalesManagementByItemList);
		productList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// 리스트 아이템 터치시
			}
		});

		adapter = new ItemCustomAdapter(this, R.layout.sales_custom_listview,
				list);
		productList.setAdapter(adapter);

	}

	// 내역 출력시 listview의 한 list에 여러 항목을 표시하는 Custom Adapter
	public class ItemCustomAdapter extends BaseAdapter {
		Context context;
		LayoutInflater inflater;

		ArrayList<salesListBean> arrayList = new ArrayList<salesListBean>();

		TextView itemNameText, quantityText, totalPriceText;

		private int layout;

		public ItemCustomAdapter(Context context, int layout,
				ArrayList<salesListBean> arrayList) {
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

		// 판매일, 상품명, 가격을 해당 Textview에 settext함
		public View getView(int i, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = inflater.inflate(layout, parent, false);
			}
			itemNameText = (TextView) convertView
					.findViewById(R.id.salesCustomListViewTextView1);
			quantityText = (TextView) convertView
					.findViewById(R.id.salesCustomListViewTextView2);
			totalPriceText = (TextView) convertView
					.findViewById(R.id.salesCustomListViewTextView3);
			itemNameText.setText(arrayList.get(i).productName);
			quantityText.setText(arrayList.get(i).quantity + "개");
			totalPriceText.setText("총 " + arrayList.get(i).price
					* arrayList.get(i).quantity + "원");

			return convertView;
		}
	}
}
