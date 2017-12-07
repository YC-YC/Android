package com.car.tboxaudio;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.car.tboxaudio.service.TBoxAudioService;


public class BaseApplication extends Application {
    
	private static final String TAG = "AVM_Application";
	private static final String SELF_SERVICE_ACTION = "com.car.tboxaudio.service.ACTION";
	public static Context mContext;

	@Override
	public void onCreate() {
		super.onCreate();
		Log.i(TAG, "onCreate");
		mContext = this;
		startSelfService();
	}
	
	private void startSelfService(){
		Intent intent = new Intent();
		intent.setAction(SELF_SERVICE_ACTION);
		intent.setComponent(new ComponentName(getPackageName(), TBoxAudioService.class.getName()));
		startService(intent );
	}
}
