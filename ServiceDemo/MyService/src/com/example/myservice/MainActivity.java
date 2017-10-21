package com.example.myservice;

import com.example.myservicelib.data.Constant;

import android.app.Activity;
import android.os.Bundle;
import android.os.IBinder;
import android.os.ServiceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends Activity {

	private static final String TAG = "TestService";


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	
	public void doClick(View view){
		IBinder service = ServiceManager.getService(Constant.SERVICE_NAME);
		Log.i(TAG, "getService = " + service);
		ServiceManager.addService(Constant.SERVICE_NAME, MyService.getDefault());
	}

}
