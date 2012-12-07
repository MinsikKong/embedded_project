package com.example.embedeed_project;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NFCListener extends Activity {

	/** Called when the activity is first created. */
	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    
	    LinearLayout layout = new LinearLayout(this);
	    TextView v = new TextView(this);
	    Button b = new Button(this);
	    layout.addView(v);
	    layout.addView(b);
	    
	    setContentView(layout);
	    
	    Intent intent = getIntent();   
		String action = intent.getAction();   
		
		
		if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
			NdefMessage[] messages = getNdefMessages(getIntent());
			byte[] payload = messages[0].getRecords()[0].getPayload();
			byte[] readString = new byte[payload.length-3];
			byte[] stock = new byte[payload.length-20];
			if(payload[18]!=47){
				//3개(인코딩문자) + 16개(카드번호)이후에는 슬래시(/)가 와야함
				finish();
			}
			for(int i=3;i<payload.length;i++){
				readString[i-3]=payload[i];
				if(i>19){
					//19번재 문자 이후는 남은 금액이므로 stock에 맏음
					stock[i-20]=payload[i];
				}
			}
			Log.d(ACTIVITY_SERVICE,new String(stock));		
			finish();
		} else {
			v.setText("NFC를 읽혀주세요");
		}
		
		b.setText("back");
		b.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				setResult(-1);
				finish();
			}
		});
		
	}

	NdefMessage[] getNdefMessages(Intent intent) {
		// Parse the intent
		NdefMessage[] msgs = null;
		String action = intent.getAction(); 
		if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action))
		{
			Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
			
			if (rawMsgs != null) {

				msgs = new NdefMessage[rawMsgs.length];

				for (int i = 0; i < rawMsgs.length; i++)
				{
					msgs[i] = (NdefMessage) rawMsgs[i];
				}
			}
		}
		return msgs;
	}
}
