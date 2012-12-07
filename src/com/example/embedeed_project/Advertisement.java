package com.example.embedeed_project;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

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

		// 음악 재생
		musicStart.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(
						"android.example.embedeed_project.intent1");
				startService(intent);
			}
		});

		// 음악 재생 중지
		musicStop.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(
						"android.example.embedeed_project.intent1");
				stopService(intent);
			}
		});

		// 영상 재생
		movieStart.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				VideoView vv = (VideoView) findViewById(R.id.video);
				MediaController mc = new MediaController(
						getApplicationContext());
				mc.setAnchorView(vv);
				vv.setVideoURI(Uri
						.parse("android.resource://com.example.embedeed_project/"
								+ R.raw.test2));
				vv.setMediaController(mc);
				vv.start();
			}
		});

		// 영상 재생 중지
		movieStop.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				VideoView vv = (VideoView) findViewById(R.id.video);
				vv.stopPlayback();

			}
		});

	}
}
