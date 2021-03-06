package com.example.embedeed_project;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
	// 그리드뷰에서 이미지를 보여주기 위한 어댑터
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
			imageView.setLayoutParams(new GridView.LayoutParams(150, 150)); // 버튼 그림 사이즈가 150x150임
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
		} else {
			imageView = (ImageView) convertView;
		}

		//
		imageView.setImageResource(mThumbIds[position]);
		return imageView;
	}

	// 버튼 9개 추가
	private Integer[] mThumbIds = { R.drawable.main_button1,
			R.drawable.main_button2, R.drawable.main_button3,
			R.drawable.main_button4, R.drawable.main_button5,
			R.drawable.main_button6, R.drawable.main_button7,
			R.drawable.main_button8, R.drawable.main_button9 };
}