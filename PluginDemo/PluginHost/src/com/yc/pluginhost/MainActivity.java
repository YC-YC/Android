package com.yc.pluginhost;

import java.io.File;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import com.yc.pluginlib.PluginCallback;

public class MainActivity extends Activity {

	protected static final String TAG = "TestPlugin";
	private ConnManager mConnManager1;
	private ConnManager mConnManager2;
	private PluginCallback mPluginCallback = new PluginCallback() {
		
		@Override
		public void onCallback(String str) {
			Log.i(TAG, "onCallback str = " + str);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void doClick(View view) {
		switch (view.getId()) {
		case R.id.button1:
			if (mConnManager1 == null) {
				mConnManager1 = new ConnManager();
				mConnManager1.loadPulgIn(this, 
						Environment.getExternalStorageDirectory() + File.separator + "/PluginClient1.apk",
						"com.yc.pluginclient1.ConnManager");
			}
			mConnManager1.JavaInInit(this);
			mConnManager1.JavaCtrlForInt(100, 0);
			mConnManager1.JavaCtrlForString(101, "Hello");
			mConnManager1.JavaCtrlForObject(102, null);
			mConnManager1.setCallback(103, mPluginCallback);
			break;

		case R.id.button2:
			if (mConnManager2 == null) {
				mConnManager2 = new ConnManager();
				mConnManager2.loadPulgIn(this, Environment.getExternalStorageDirectory() + File.separator + "/PluginClient2.apk",
						"com.yc.pluginclient2.ConnManager");
			}
			mConnManager2.JavaInInit(this);
			mConnManager2.JavaCtrlForInt(100, 0);
			mConnManager2.JavaCtrlForString(101, "Hello");
			mConnManager2.JavaCtrlForObject(102, null);
			mConnManager2.setCallback(104, mPluginCallback);
			break;

		default:
			break;
		}
	}

}
