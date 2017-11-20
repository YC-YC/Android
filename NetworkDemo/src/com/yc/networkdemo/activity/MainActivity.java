package com.yc.networkdemo.activity;

import com.yc.networkdemo.R;
import com.yc.networkdemo.R.id;
import com.yc.networkdemo.R.layout;
import com.yc.networkdemo.R.menu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void doClick(View view) {
		switch (view.getId()) {
		case R.id.bt_asynchttpclient:
			startActivity(new Intent(this, AsyncHttpClientActivity.class));
			break;
		case R.id.bt_volley:
			startActivity(new Intent(this, VollyActivity.class));
			break;
		case R.id.bt_OkHttp:
			startActivity(new Intent(this, OkHttpActivity.class));
			break;
		case R.id.bt_xutil:
			startActivity(new Intent(this, XUtilActivity.class));
			break;
		case R.id.bt_socket:
			startActivity(new Intent(this, SocketActivity.class));
			break;
		case R.id.bt_exit:
			finish();
			break;

		default:
			break;
		}
	}
}
