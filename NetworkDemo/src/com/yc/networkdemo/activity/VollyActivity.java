package com.yc.networkdemo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.yc.networkdemo.R;

public class VollyActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_volley);
	}

	public void doClick(View view) {
		switch (view.getId()) {
		case R.id.bt_asynchttpclient:

			break;
		case R.id.bt_volley:

			break;
		case R.id.bt_OkHttp:

			break;
		case R.id.bt_xutil:

			break;
		case R.id.bt_exit:
			finish();
			break;
		default:
			break;
		}
	}
}
