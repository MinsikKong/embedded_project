package com.example.embedeed_project;

import java.util.ArrayList;
import java.util.Locale;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SalesManagement extends Activity {
	// 일자별 매출 (공민식)
	private ArrayList<salesListBean> list;
	private ItemCustomAdapter adapter;
	private ListView salesList;
	Cursor cursor;
	SQLiteDatabase db;
	public int count;

	// 매출내역은 Bean 사용
	class salesListBean {
		public String soldDate;// 상품명
		public String productName; // 수량
		public int price; // 가격
		public int sales_num; // 가격

		public salesListBean(String soldDate, String productName, int price,
				int sales_num) {
			this.soldDate = soldDate;
			this.productName = productName;
			this.price = price;
			this.sales_num = sales_num;
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sales_management);

		// listview 설정
		list = new ArrayList<salesListBean>();
		salesList = (ListView) findViewById(R.id.SalesManagementList);
		adapter = new ItemCustomAdapter(this, R.layout.sales_custom_listview,
				list);
		salesList.setAdapter(adapter);

		// db 설정
		db = openOrCreateDatabase(Const.DATABASE_NAME, MODE_PRIVATE, null);
		db.setVersion(1);
		db.setLocale(Locale.getDefault());
		db.setLockingEnabled(true);

		// 매출내역 전체 조회
		cursor = db.rawQuery("select * from sales", null);

		if (cursor.moveToFirst()) {
			cursor.moveToFirst();

			// 판매일, 판매상품, 가격, 매출번호 받아옴
			for (count = cursor.getCount(); count > 0; count--) {
				salesListBean item = new salesListBean(cursor.getString(1),
						cursor.getString(3), cursor.getInt(2), cursor.getInt(0));
				list.add(item);
				cursor.moveToNext();
			}
		} else {
			Toast.makeText(SalesManagement.this, "매출 내역이 없습니다",
					Toast.LENGTH_SHORT).show();
		}

		adapter.notifyDataSetChanged(); // listview refresh
		cursor.close();
		db.close();

		// listView의 list 터치시 이벤트
		salesList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> av, View view, int position,
					long id) {

				// Toast.makeText(getApplicationContext(),
				// "" + list.get(position).price, Toast.LENGTH_SHORT)
				// .show();

				// db = openOrCreateDatabase(Const.DATABASE_NAME, MODE_PRIVATE,
				// null);
				// db.setVersion(1);
				// db.setLocale(Locale.getDefault());
				// db.setLockingEnabled(true);
				//
				// cursor = db.rawQuery("select * from sales", null);
				//
				// if (cursor.moveToFirst()) {
				// cursor.moveToFirst();
				//
				// for (count = cursor.getCount(); count > 0; count--) {
				// salesListBean item = new salesListBean(cursor
				// .getString(1), cursor.getString(3), cursor
				// .getInt(2), cursor.getInt(0));
				// list.add(item);
				// cursor.moveToNext();
				// }
				// } else {
				// Toast.makeText(SalesManagement.this, "해당일의 상품 내역이 없습니다",
				// Toast.LENGTH_SHORT).show();
				// }

				// list 클릭시 dialog

				//
				// list = new ArrayList<salesListBean>();
				//
				// adapter = new ItemCustomAdapter(getApplicationContext(),
				// R.layout.product_listview, list);
				// salesList1.setAdapter(adapter);
				//
				// salesListBean item = new salesListBean("a", "a", 1, 1);
				// list.add(item);
				// adapter.notifyDataSetChanged();
				// //
				//
				// Context mContext = getApplicationContext();
				// LayoutInflater inflater = (LayoutInflater) mContext
				// .getSystemService(LAYOUT_INFLATER_SERVICE);
				// View layout = inflater.inflate(R.layout.test,
				// (ViewGroup) findViewById(R.id.layout_root));
				//
				// AlertDialog.Builder aDialog = new AlertDialog.Builder(
				// SalesManagement.this);
				// aDialog.setView(layout);
				//
				// AlertDialog ad = aDialog.create();
				// ad.show();

			}

		});
	}

	// 내역 출력시 listview의 한 list에 여러 항목을 표시하는 Custom Adapter
	public class ItemCustomAdapter extends BaseAdapter {
		Context context;
		LayoutInflater inflater;
		private int layout;

		ArrayList<salesListBean> arrayList = new ArrayList<salesListBean>();
		TextView soldDateTextView, productNameTextView, totalPriceTextView;

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
			soldDateTextView = (TextView) convertView
					.findViewById(R.id.salesCustomListViewTextView1);
			productNameTextView = (TextView) convertView
					.findViewById(R.id.salesCustomListViewTextView2);
			totalPriceTextView = (TextView) convertView
					.findViewById(R.id.salesCustomListViewTextView3);

			soldDateTextView.setText("" + arrayList.get(i).soldDate);
			productNameTextView.setText("" + arrayList.get(i).productName);
			totalPriceTextView.setText("" + arrayList.get(i).price + "원");

			return convertView;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.sales_management, menu);
		return true;
	}

	// menu 클릭시 각 Activity로 이동
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case R.id.SalesManagementByDateMenu: // 일자별
			Intent intent1 = new Intent(getApplicationContext(),
					SalesManagementByDate.class);
			startActivity(intent1);
			break;

		case R.id.SalesManagementByItemMenu: // 상품별
			Intent intent2 = new Intent(getApplicationContext(),
					SalesManagementByItem.class);
			startActivity(intent2);
			break;

		}
		return true;
	}
}