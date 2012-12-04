package com.example.embedeed_project;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import com.example.embedeed_project.SalesManagement.salesListBean;

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

public class SalesManagementByDate extends Activity {
	// 일자별 매출(공민식)
	private ArrayList<salesListBean> list;
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
		setContentView(R.layout.sales_management_by_date);

		list = new ArrayList<salesListBean>();
		productList = (ListView) findViewById(R.id.SalesByDateList);
		productList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// 리스트 아이템 터치시
			}
		});

		et_startdate = (EditText) findViewById(R.id.SalesByDateStartDateEditext);
		et_enddate = (EditText) findViewById(R.id.SalesByDateEndDateEditext);

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
		search = (Button) findViewById(R.id.SalesByDateButton1);
		search.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				db = openOrCreateDatabase(Const.DATABASE_NAME, MODE_PRIVATE,
						null);
				db.setVersion(1);
				db.setLocale(Locale.getDefault());
				db.setLockingEnabled(true);

				// 특정 일자 사이에 있는 매입 조회
				cursor = db.rawQuery(
						"select * from sales where created_at between" + "'"
								+ et_startdate.getText() + "' and '"
								+ et_enddate.getText() + "'", null);

				// db쿼리 처리
				if (cursor.moveToFirst()) {
					cursor.moveToFirst();

					for (count = cursor.getCount(); count > 0; count--) {
						salesListBean item = new salesListBean(cursor
								.getString(1), cursor.getString(3), cursor
								.getInt(2), cursor.getInt(0));
						list.add(item);
						cursor.moveToNext();
					}
				} else {
					Toast.makeText(getApplicationContext(), "해당일의 상품 내역이 없습니다",
							Toast.LENGTH_SHORT).show();
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
			if (tempView.getId() == R.id.SalesByDateStartDateEditext) {
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

		ArrayList<salesListBean> arrayList = new ArrayList<salesListBean>();

		TextView soldDateTextView, productNameTextView, totalPriceTextView;

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

		public View getView(int i, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = inflater.inflate(layout, parent, false);
			}
			soldDateTextView = (TextView) convertView
					.findViewById(R.id.productListview1);
			productNameTextView = (TextView) convertView
					.findViewById(R.id.productListview2);
			totalPriceTextView = (TextView) convertView
					.findViewById(R.id.productListview3);

			soldDateTextView.setText("" + arrayList.get(i).soldDate);
			productNameTextView.setText("" + arrayList.get(i).productName);
			totalPriceTextView.setText("" + arrayList.get(i).price + "원");

			return convertView;
		}
	}
}