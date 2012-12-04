package com.example.embedeed_project;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

public class Cafe extends Activity {
	// 까페가기(박민성)


	WebView wv;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cafe);
		
        wv = (WebView)findViewById(R.id.cafeView);
		wv.getSettings().setJavaScriptEnabled(true);
		wv.loadUrl("http://www.naver.com");
		wv.setWebViewClient(new CafeWebViewClient());

        ((Button)findViewById(R.id.webBackButton)).setOnClickListener(listener);
        ((Button)findViewById(R.id.webForwardButton)).setOnClickListener(listener);
	}
	
	
	Button.OnClickListener listener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch(v.getId()){
			case R.id.webBackButton:
				if(wv.canGoBack())
					wv.goBack();
				break;
			case R.id.webForwardButton:
				if(wv.canGoForward())
					wv.goForward();
				break;
			default:
				Toast.makeText(getBaseContext(), "Error!", Toast.LENGTH_LONG).show();
			}
		}
	};
	
	private class CafeWebViewClient extends WebViewClient{
		public boolean shouldOverrideUrlLoading(WebView view, String url){
			view.loadUrl(url);
			return true;
		}
	}
}
