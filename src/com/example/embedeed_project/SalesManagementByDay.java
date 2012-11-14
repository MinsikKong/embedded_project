package com.example.embedeed_project;

import java.util.ArrayList;
import java.util.Calendar;

import org.apache.http.auth.BasicUserPrincipal;

import android.app.Activity;
import android.app.Dialog;
import android.app.ListActivity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.app.DatePickerDialog;
import android.content.Intent;

public class SalesManagementByDay extends Activity {
	// 일자별 매출(공민식)
	private ArrayList<String> list;
	private ArrayAdapter<String> adapter;
	private ListView productList;

	EditText et_startdate, et_enddate;
	private int iYear, iMonth, iDay;

	View tempview;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sales_management);

		et_startdate.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				tempview = v;
				showDialog(0);
			}
		});

		et_enddate.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				tempview = v;
				showDialog(0);
			}
		});

		productList = (ListView) findViewById(R.id.SalesList);
		list = new ArrayList<String>();

		list.add("a");
		list.add("s");
		list.add("d");
		list.add("g");
		list.add("5");
		list.add("6");
		list.add("7");
		list.add("8");
		list.add("9");
		// adapter.notifyDataSetChanged();

		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, list);
		productList.setAdapter(adapter);
	}
}