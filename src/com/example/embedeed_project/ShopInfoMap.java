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
	MapView mv;
	LocationManager lm;
	LocationListener ll;
	Location l;
	String value = "default";

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shop_info_map);

		lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		ll = new MyLocationListener();
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, ll);

		mv = (MapView) findViewById(R.id.mapview);
		mv.setBuiltInZoomControls(true);
		mv.setSatellite(true);
		mv.setTraffic(true);

		Button set = (Button) findViewById(R.id.ShopInfoMapButton1);
		set.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {

				// List<Address> addr = gc.getFromLocation(l.getLatitude(),
				// l.getLongitude(), 2);

				try {
					Geocoder gc = new Geocoder(getApplicationContext(),
							Locale.KOREAN);
					List<Address> addr = gc.getFromLocation(37.222281,
							127.187283, 2);
					value = addr.get(0).getAddressLine(0);
					Toast.makeText(getApplicationContext(),
							addr.get(0).getAddressLine(0), Toast.LENGTH_SHORT)
							.show();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				Intent intent = new Intent();
				intent.putExtra("value", value);
				setResult(MapActivity.RESULT_OK, intent);
				finish();

			}
		});
	}

	private class MyLocationListener implements LocationListener {
		public void onLocationChanged(Location l) {

			// Toast.makeText(
			// getApplicationContext(),
			// (int) (l.getLatitude() * 1000000) + " , "
			// + (int) (l.getLongitude() * 1000000),
			// Toast.LENGTH_SHORT).show();

			MapController mc = mv.getController();
			GeoPoint gp = new GeoPoint((int) (l.getLatitude() * 1000000),
					(int) (l.getLongitude() * 1000000));
			mc.animateTo(gp);
			mc.setZoom(16);

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
