package com.yc.networkdemo.activity;

import com.yc.networkdemo.R;
import com.yc.networkdemo.R.id;
import com.yc.networkdemo.R.layout;
import com.yc.networkdemo.R.menu;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class XUtilActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_xutil);
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
