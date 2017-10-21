/**
 * 
 */
package com.example.myservice;

import com.example.myservicelib.data.Constant;

import android.app.Application;
import android.os.ServiceManager;
import android.util.Log;

/**
 * @author YC
 * @time 2017-4-20 下午2:44:44
 * TODO:
 */
public class MyApplication extends Application {

	private static final String TAG = "MyApplication";

	@Override
	public void onCreate() {
		super.onCreate();
		Log.i(TAG, "onCreate add service");
		//需要系统权限
		ServiceManager.addService(Constant.SERVICE_NAME, MyService.getDefault());
	}
}
