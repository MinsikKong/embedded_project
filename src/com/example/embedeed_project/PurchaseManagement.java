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

public class PurchaseManagement extends Activity {
	// 일자별 매출 (공민식)
	private ArrayList<purchaseListBean> list;
	private ItemCustomAdapter adapter;
	private ListView purchaseList;
	Cursor cursor;
	SQLiteDatabase db;
	public int count;

	// // 매출내역은 Bean 사용
	class purchaseListBean {
		public String purchaseDate; // 매입일
		public String businessName; // 업체이름
		public String productName; // 상품이름
		public int amount; // 수량
		public int price; // 가격
		public int purchase_num; // 매입번호

		public purchaseListBean(String purchaseDate, String businessName,
				String productName, int amount, int price, int purchase_num) {
			this.purchaseDate = purchaseDate;
			this.businessName = businessName;
			this.productName = productName;
			this.amount = amount;
			this.price = price;
			this.purchase_num = purchase_num;
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.purchase_management);

		list = new ArrayList<purchaseListBean>();
		purchaseList = (ListView) findViewById(R.id.PurchaseManagementList);

		adapter = new ItemCustomAdapter(this,
				R.layout.purchase_custom_listview, list);
		purchaseList.setAdapter(adapter);

		db = openOrCreateDatabase(Const.DATABASE_NAME, MODE_PRIVATE, null);
		db.setVersion(1);
		db.setLocale(Locale.getDefault());
		db.setLockingEnabled(true);

		// 특정 일자 사이에 있는 매입 조회
		cursor = db.rawQuery("select * from purchase", null);

		if (cursor.moveToFirst()) {
			cursor.moveToFirst();

			for (count = cursor.getCount(); count > 0; count--) {
				purchaseListBean item = new purchaseListBean(
						cursor.getString(1), cursor.getString(2),
						cursor.getString(3), cursor.getInt(4),
						cursor.getInt(5), cursor.getInt(0));
				list.add(item);
				cursor.moveToNext();
			}
		} else {
			Toast.makeText(PurchaseManagement.this, "매입 내역이 없습니다",
					Toast.LENGTH_SHORT).show();
		}

		adapter.notifyDataSetChanged(); // listview refresh
		cursor.close();
		db.close();

		// listView의 list 터치시 이벤트
		purchaseList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> av, View view, int position,
					long id) {
				// ㅠㅠ
			}

		});
	}

	// 매입내역 출력시 Listview에 상품이름, 개수, 총 가격을 표시하는 Custom Adapter
	public class ItemCustomAdapter extends BaseAdapter {
		Context context;
		LayoutInflater inflater;

		ArrayList<purchaseListBean> arrayList = new ArrayList<purchaseListBean>();

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

			TextView purchaseDateTextView, productNameTextView, amountTextView, totalPriceTextView;

			if (convertView == null) {
				convertView = inflater.inflate(layout, parent, false);
			}
			purchaseDateTextView = (TextView) convertView
					.findViewById(R.id.purchaseCustomListviewTextView1);
			productNameTextView = (TextView) convertView
					.findViewById(R.id.purchaseCustomListviewTextView2);
			amountTextView = (TextView) convertView
					.findViewById(R.id.purchaseCustomListviewTextView3);
			totalPriceTextView = (TextView) convertView
					.findViewById(R.id.purchaseCustomListviewTextView4);

			purchaseDateTextView.setText(""
					+ arrayList.get(i).purchaseDate.substring(0, 9));
			productNameTextView.setText("" + arrayList.get(i).productName);
			amountTextView.setText(arrayList.get(i).amount + "개");
			totalPriceTextView.setText("총" + arrayList.get(i).price
					* arrayList.get(i).amount + "원");

			return convertView;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.purchase_management, menu);
		return true;
	}

	// menu 클릭시 각 Activity로 이동
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case R.id.PurchaseManagementByDateMenu: // 일자별
			Intent intent1 = new Intent(PurchaseManagement.this,
					PurchaseManagementByDate.class);
			startActivity(intent1);
			break;

		case R.id.PurchaseManagementByCompanyMenu: // 업체별
			Intent intent2 = new Intent(PurchaseManagement.this,
					PurchaseManagementByCompany.class);
			startActivity(intent2);
			break;

		case R.id.PurchaseManagementByItemMenu: // 상품별
			Intent intent3 = new Intent(PurchaseManagement.this,
					PurchaseManagementByItem.class);
			startActivity(intent3);
			break;

		case R.id.PurchaseManagementAddListMenu: // 상품별
			Intent intent4 = new Intent(PurchaseManagement.this,
					PurchaseManagementAddList.class);
			startActivity(intent4);
			break;

		}
		return true;
	}
}