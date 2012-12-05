package com.example.embedeed_project;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;

public class Advertisement extends Activity {
	// 광고(공민식)
	Button musicStart, musicStop, movieStart, movieStop;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.advertisement);

		musicStart = (Button) findViewById(R.id.AdvertiseButton1);
		movieStart = (Button) findViewById(R.id.AdvertiseButton2);
		musicStop = (Button) findViewById(R.id.AdvertiseButton3);
		movieStop = (Button) findViewById(R.id.AdvertiseButton4);

		musicStart.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(
						"android.example.embedeed_project.intent1");
				startService(intent);
			}
		});
		
		musicStop.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(
						"android.example.embedeed_project.intent1");
				stopService(intent);
			}
		});

	}

	public class AudioService extends Service {
		private MediaPlayer mp1;

		@Override
		public IBinder onBind(Intent arg0) {
			return null;
		}

		@Override
		public void onStart(Intent intent, int startId) {
			super.onStart(intent, startId);
			mp1 = MediaPlayer.create(this, R.raw.song1);
			mp1.start();
		}

		@Override
		public void onDestroy() {
			super.onDestroy();
			mp1.stop();
		}

	}
}
