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

public class ItemManagementByDate extends Activity {
	// 일자별 매출(공민식)
	private ArrayList<itemsListBean> list;
	private ItemCustomAdapter adapter;
	private ListView productList;
	Cursor cursor;
	SQLiteDatabase db;
	public int count;
	Button search;

	// DatePicker Dialog에 사용
	EditText et_startdate, et_enddate;
	private int year, month, day;
	static final int DATE_DIALOG_ID = 0;
	View tempView;

	// 매출내역은 Bean 사용
	class itemsListBean {
		public int product_code; // 상품코드
		public String img; // 상품 이미지
		public String category; // 상품 카테고리
		public String name; // 상품명
		public int price; // 상품 가격
		public int stock; // 상품 재고
		public String barcode; // 상품 바코드
		public String note; // 상품 메모

		public itemsListBean(int product_code, String img, String category,
				String name, int price, int stock, String barcode, String note) {
			this.product_code = this.product_code;
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
		setContentView(R.layout.item_management_by_date);

		// listview 설정
		list = new ArrayList<itemsListBean>();
		productList = (ListView) findViewById(R.id.ItemByDateList);
		productList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// 리스트 아이템 터치시
			}
		});

		et_startdate = (EditText) findViewById(R.id.ItemByDateStartDateEditext);
		et_enddate = (EditText) findViewById(R.id.ItemByDateEndDateEditext);

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
		search = (Button) findViewById(R.id.ItemByDateButton1);
		search.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				db = openOrCreateDatabase(Const.DATABASE_NAME, MODE_PRIVATE,
						null);
				db.setVersion(1);
				db.setLocale(Locale.getDefault());
				db.setLockingEnabled(true);

				// 특정 일자 사이에 있는 상품 내역 조회
				cursor = db.rawQuery(
						"select * from product where created_at between" + "'"
								+ et_startdate.getText() + "' and '"
								+ et_enddate.getText() + "'", null);

				// '검색'버튼 중복 클릭시 기존 리스트 클리어 처리
				list.clear();

				// db쿼리 처리
				if (cursor.moveToFirst()) {
					cursor.moveToFirst();

					// 상품코드, 이미지, 카테고리, 상품명, 가격, 재고, 바코드, 메모 가져옴
					for (count = cursor.getCount(); count > 0; count--) {
						itemsListBean item = new itemsListBean(
								cursor.getInt(0), cursor.getString(1), cursor
										.getString(2), cursor.getString(3),
								cursor.getInt(4), cursor.getInt(5), cursor
										.getString(6), cursor.getString(7));
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

		adapter = new ItemCustomAdapter(this, R.layout.item_custom_listview,
				list);
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
			if (tempView.getId() == R.id.ItemByDateStartDateEditext) {
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

		// 내역 출력시 listview의 한 list에 여러 항목을 표시하는 Custom Adapter
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
}