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

public class PurchaseManagementByItem extends Activity {
	// 판매(공민식)
	private ArrayList<purchaseListBean> list;
	private ItemCustomAdapter adapter;
	private ListView productList;
	Cursor cursor;
	SQLiteDatabase db;
	public int count;

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
		setContentView(R.layout.purchase_management_by_company);

		// Spinner에 업체 리스트(strings.xml)추가
		final Spinner spinner = (Spinner) findViewById(R.id.PurchaseManagementByCompanySpinner);
		ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter
				.createFromResource(this,
						R.array.PurchaseManagementByItemSpinnerArray,
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
				cursor = db.rawQuery(
						"select * from purchase where product_name=" + "'"
								+ (String) spinner.getSelectedItem() + "'",
						null);

				// Spinner 선택값이 변경될 경우 list clear
				list.clear();

				// 해당 내역이 없으면 메시지 뿌림
				if (cursor.moveToFirst()) {
					cursor.moveToFirst();

					for (count = cursor.getCount(); count > 0; count--) {
						purchaseListBean item = new purchaseListBean(cursor
								.getString(1), cursor.getString(2), cursor
								.getString(3), cursor.getInt(4), cursor
								.getInt(5), cursor.getInt(0));
						list.add(item);
						cursor.moveToNext();
					}
				} else {
					Toast.makeText(PurchaseManagementByItem.this,
							"매입 내역이 없습니다", Toast.LENGTH_SHORT).show();
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
		productList = (ListView) findViewById(R.id.PurchaseManagementByCompanyList);
		productList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// 리스트 아이템 터치시
			}
		});

		adapter = new ItemCustomAdapter(this,
				R.layout.purchase_custom_listview, list);
		productList.setAdapter(adapter);

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

			purchaseDateTextView.setText("" + arrayList.get(i).purchaseDate);
			productNameTextView.setText("" + arrayList.get(i).productName);
			amountTextView.setText(arrayList.get(i).amount + "개");
			totalPriceTextView.setText("총" + arrayList.get(i).price
					* arrayList.get(i).amount + "원");

			return convertView;
		}
	}
}
