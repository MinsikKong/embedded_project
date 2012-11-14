package com.example.embedeed_project;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
	private Context mContext;

	public ImageAdapter(Context c) {
		mContext = c;
	}

	public int getCount() {
		return mThumbIds.length;
	}

	public Object getItem(int position) {
		return null;
	}

	public long getItemId(int position) {
		return 0;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView;
		if (convertView == null) {
			imageView = new ImageView(mContext);
			imageView.setLayoutParams(new GridView.LayoutParams(150, 150));// ��ư �׸� ���� ũ�Ⱑ 150x150
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
		} else {
			imageView = (ImageView) convertView;
		}

		// ��ư �̹��� �߰�
		imageView.setImageResource(mThumbIds[position]);
		return imageView;
	}

	// ����ȭ���� ��ư �̹���
	private Integer[] mThumbIds = { R.drawable.main_button1,
			R.drawable.main_button2, R.drawable.main_button3,
			R.drawable.main_button4, R.drawable.main_button5,
			R.drawable.main_button6, R.drawable.main_button7,
			R.drawable.main_button8, R.drawable.main_button9 };
}