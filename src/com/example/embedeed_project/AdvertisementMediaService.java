package com.example.embedeed_project;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class AdvertisementMediaService extends Service {
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
