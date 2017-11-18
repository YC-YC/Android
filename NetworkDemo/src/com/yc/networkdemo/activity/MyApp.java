/**
 * 
 */
package com.yc.networkdemo.activity;

import org.xutils.x;

import android.app.Application;

/**
 * @author YC2
 * @time 2017-11-18 下午2:13:39
 * TODO:
 */
public class MyApp extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		x.Ext.init(this);
		x.Ext.setDebug(true);
	}
	
}
