package com.example.embedeed_project;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;

public class ShopInfoMap extends MapActivity {
	// 매장 정보의 맵부분 (공민식)
	MapView mv;
	LocationManager lm;
	LocationListener ll;
	Location l;
	String value = "default";

	Geocoder gc;
	List<Address> addr;

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shop_info_map);

		// gps로 현재위치 계속 요청함
		lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		ll = new MyLocationListener();
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, ll);

		// mapview 설정
		mv = (MapView) findViewById(R.id.mapview);
		mv.setBuiltInZoomControls(true);
		mv.setSatellite(true);
		mv.setTraffic(true);

		// geocoder 이용해서 현재 위치의 주소값을 intent로 넘김
		Button set = (Button) findViewById(R.id.ShopInfoMapButton1);
		set.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {

				value = addr.get(0).getAddressLine(0);
				Toast.makeText(getApplicationContext(),
						addr.get(0).getAddressLine(0), Toast.LENGTH_SHORT)
						.show();

				Intent intent = new Intent();
				intent.putExtra("value", value);
				setResult(MapActivity.RESULT_OK, intent);
				finish();
			}
		});

		// 취소버튼
		Button cancel = (Button) findViewById(R.id.ShopInfoMapButton2);
		cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
	}

	// 위치가 변경된 경우(gps로 현재위치가 잡힌 경우) 해당 위치로 맵을 이동
	private class MyLocationListener implements LocationListener {
		public void onLocationChanged(Location l) {

			try {
				gc = new Geocoder(getApplicationContext(), Locale.KOREAN);
				addr = gc.getFromLocation(l.getLatitude(), l.getLongitude(), 1);

				MapController mc = mv.getController();
				GeoPoint gp = new GeoPoint((int) (l.getLatitude() * 1000000),
						(int) (l.getLongitude() * 1000000));
				mc.animateTo(gp);
				mc.setZoom(16);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub

		}

		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub

		}

		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub

		}
	}
}
