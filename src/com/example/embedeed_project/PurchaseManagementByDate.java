package com.example.embedeed_project;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class PurchaseManagementByDate extends Activity {
	// 일자별 매출(공민식)
	private ArrayList<purchaseListBean> list;
	private ItemCustomAdapter adapter;
	private ListView productList;
	Cursor cursor;
	SQLiteDatabase db;
	public int count;
	Button search;

	EditText et_startdate, et_enddate;
	private int year, month, day;
	static final int DATE_DIALOG_ID = 0;
	View tempView;

	// 매입내역은 Bean 사용
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

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.purchase_management_by_date);

		list = new ArrayList<purchaseListBean>();
		productList = (ListView) findViewById(R.id.PurchaseByDateList);
		productList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// 리스트 아이템 터치시
			}
		});

		et_startdate = (EditText) findViewById(R.id.PurchaseByDateStartDateEditext);
		et_enddate = (EditText) findViewById(R.id.PurchaseByDateEndDateEditext);

		final Calendar objTime = Calendar.getInstance();
		year = objTime.get(Calendar.YEAR);
		month = objTime.get(Calendar.MONTH);
		day = objTime.get(Calendar.DAY_OF_MONTH);

		// 각 edittext 터치하면 DatePicker Dialog 호출함
		et_startdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				tempView = v;
				showDialog(DATE_DIALOG_ID);
			}
		});
		et_enddate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				tempView = v;
				showDialog(DATE_DIALOG_ID);
			}
		});

		// 검색 버튼 터치시 선택된 기간 내의 결과 찾음
		search = (Button) findViewById(R.id.PurchaseByDateButton1);
		search.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				db = openOrCreateDatabase(Const.DATABASE_NAME, MODE_PRIVATE,
						null);
				db.setVersion(1);
				db.setLocale(Locale.getDefault());
				db.setLockingEnabled(true);

				// 특정 일자 사이에 있는 매입 조회
				cursor = db.rawQuery(
						"select * from purchase where created_at between" + "'"
								+ et_startdate.getText() + "' and '"
								+ et_enddate.getText() + "'", null);

				// 해당 내역이 없으면 메시지 뿌림
				if (cursor.moveToFirst()) {
					cursor.moveToFirst();
				} else {
					Toast.makeText(PurchaseManagementByDate.this,
							"해당일의 매입 내역이 없습니다", Toast.LENGTH_SHORT).show();
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
		});

		adapter = new ItemCustomAdapter(this, R.layout.product_listview, list);
		productList.setAdapter(adapter);
	}

	protected Dialog onCreateDialog(int id) {
		// DATE_DIALOG_ID로 호출받으면 DatePicker Dialog뜸
		switch (id) {
		case DATE_DIALOG_ID:
			return new DatePickerDialog(this, mDateSetListener, year, month,
					day);
		}
		return null;
	}

	// DatePicker Dialog에서 날짜를 정하면 해당 날짜를 해당 edittext에 넣음(보여
	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
		public void onDateSet(DatePicker view, int year, int month, int day) {
			if (tempView.getId() == R.id.PurchaseByDateStartDateEditext) {
				et_startdate.setText(year + "-" + (month + 1) + "-" + day);
			} else {
				et_enddate.setText(year + "-" + (month + 1) + "-" + day);
			}
		}
	};

	// 매입내역 출력시 Listview에 상품이름, 개수, 총 가격을 표시하는 Custom Adapter
	public class ItemCustomAdapter extends BaseAdapter {
		Context context;
		LayoutInflater inflater;

		ArrayList<purchaseListBean> arrayList = new ArrayList<purchaseListBean>();

		TextView itemNameText, quantityText, totalPriceText,
				PurchaseByDateSummary;

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