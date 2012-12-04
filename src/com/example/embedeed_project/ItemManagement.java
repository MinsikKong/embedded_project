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
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ItemManagement extends Activity {
	// 일자별 매출 (공민식)
	private ArrayList<itemsListBean> list;
	private ItemCustomAdapter adapter;
	private ListView salesList;
	Cursor cursor;
	SQLiteDatabase db;
	public int count;
	Button search;

	// // 매출내역은 Bean 사용
	class itemsListBean {
		public int product_code;
		public String img;
		public String category;
		public String name;
		public int price;
		public int stock;
		public String barcode;
		public String note;

		public itemsListBean(int product_code, String img, String category,
				String name, int price, int stock, String barcode, String note) {
			this.product_code = this.price;
			this.img = img;
			this.category = category;
			this.name = name;
			this.price = price;
			this.stock = stock;
			this.barcode = barcode;
			this.note = note;
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.item_management);

		list = new ArrayList<itemsListBean>();
		salesList = (ListView) findViewById(R.id.ItemManagementList);
		// salesList1 = (ListView) findViewById(R.id.SalesManagementList2);

		adapter = new ItemCustomAdapter(this, R.layout.item_custom_listview,
				list);
		salesList.setAdapter(adapter);

		db = openOrCreateDatabase(Const.DATABASE_NAME, MODE_PRIVATE, null);
		db.setVersion(1);
		db.setLocale(Locale.getDefault());
		db.setLockingEnabled(true);

		// 특정 일자 사이에 있는 매입 조회
		cursor = db.rawQuery("select * from product", null);

		if (cursor.moveToFirst()) {
			cursor.moveToFirst();

			for (count = cursor.getCount(); count > 0; count--) {
				itemsListBean item = new itemsListBean(cursor.getInt(0),
						cursor.getString(1), cursor.getString(2),
						cursor.getString(3), cursor.getInt(4),
						cursor.getInt(5), cursor.getString(6),
						cursor.getString(7));
				list.add(item);
				cursor.moveToNext();
			}
		} else {
			Toast.makeText(ItemManagement.this, "매출 내역이 없습니다",
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
			}

		});
	}

	// 매입내역 출력시 Listview에 상품이름, 개수, 총 가격을 표시하는 Custom Adapter
	public class ItemCustomAdapter extends BaseAdapter {
		Context context;
		LayoutInflater inflater;

		ArrayList<itemsListBean> arrayList = new ArrayList<itemsListBean>();

		TextView productNameTextView, PriceTextView, amountTextView;

		private int layout;

		public ItemCustomAdapter(Context context, int layout,
				ArrayList<itemsListBean> arrayList) {
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
			if (convertView == null) {
				convertView = inflater.inflate(layout, parent, false);
			}
			productNameTextView = (TextView) convertView
					.findViewById(R.id.itemCustomListviewTextview1);
			PriceTextView = (TextView) convertView
					.findViewById(R.id.itemCustomListviewTextview2);
			amountTextView = (TextView) convertView
					.findViewById(R.id.itemCustomListviewTextview3);

			productNameTextView.setText("" + arrayList.get(i).name);
			PriceTextView.setText("" + arrayList.get(i).price + "원");
			amountTextView.setText("" + arrayList.get(i).stock + "개");

			return convertView;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.item_management, menu);
		return true;
	}

	// menu 클릭시 각 Activity로 이동
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case R.id.ItemManagementByDateMenu: // 일자별
			Intent intent1 = new Intent(ItemManagement.this,
					ItemManagementByDate.class);
			startActivity(intent1);
			break;

		case R.id.ItemManagementAddListMenu: // 업체별
			Intent intent2 = new Intent(ItemManagement.this,
					ItemManagementAddItem.class);
			startActivity(intent2);
			break;
		}
		return true;
	}
}