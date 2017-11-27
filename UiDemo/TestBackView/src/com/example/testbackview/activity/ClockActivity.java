/**
 * 
 */
package com.example.testbackview.activity;

import java.util.Calendar;
import java.util.Date;

import org.xutils.x;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;

import com.example.testbackview.R;
import com.example.testbackview.widget.ClockView;

/**
 * @author YC2
 * @time 2017-11-27 下午3:41:19
 * TODO:
 */
@ContentView(R.layout.activity_clock)
public class ClockActivity extends Activity {
	
	private static final int MSG_WHAT_UPDATE_TIME = 100;

	@ViewInject(R.id.img_customer_clock_hour)
	private ImageView mHour;
	
	@ViewInject(R.id.img_customer_clock_minute)
	private ImageView mMinute;
	
	@ViewInject(R.id.img_customer_clock_second)
	private ImageView mSecond;
	
	@ViewInject(R.id.clockview)
	private ClockView mClockView;
	
	private float mHourRotationVal = 0.0f;
//	private int hour, minute, second;
	private BroadcastReceiver mTimeReceiver;
	private Handler mHandler;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		x.view().inject(this);
		mHandler = new MyHandler();
	}
	
	@Event(type = View.OnClickListener.class, value = {R.id.bt_test_clock})
	private void doClick(View view){
		switch (view.getId()) {
		case R.id.bt_test_clock:
//			mHourRotationVal += 1.0f;
//			if (mHourRotationVal > 360.0f){
//				mHourRotationVal = 0.0f;
//			}
//			mHour.setRotation(mHourRotationVal);
			
			updateTime();
			break;

		default:
			break;
		}
	}

	/**
	 * 
	 */
	private void updateTime() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		if (hour > 12){
			hour -= 12;
		}
		int minute = calendar.get(Calendar.MINUTE);
		int second = calendar.get(Calendar.SECOND);
		
		mClockView.setClockTime(hour, minute, second);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
//		registerTimeChanger();
		mHandler.sendEmptyMessage(MSG_WHAT_UPDATE_TIME);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
//		unregisterTimeChange();
		mHandler.removeMessages(MSG_WHAT_UPDATE_TIME);
	}
	
	private class MyHandler extends Handler{
		
		@Override
		public void dispatchMessage(Message msg) {
			super.dispatchMessage(msg);
			switch (msg.what) {
			case MSG_WHAT_UPDATE_TIME:
				updateTime();
				mHandler.sendEmptyMessageDelayed(MSG_WHAT_UPDATE_TIME, 500);
				break;

			default:
				break;
			}
		}
	}
	
	private void registerTimeChanger() {
		IntentFilter filter = new IntentFilter();

		filter.addAction(Intent.ACTION_TIME_TICK);
		filter.addAction(Intent.ACTION_TIME_CHANGED);
		filter.addAction(Intent.ACTION_TIMEZONE_CHANGED);
		filter.addAction(Intent.ACTION_CONFIGURATION_CHANGED);

		mTimeReceiver = new BroadcastReceiver() {

			@Override
			public void onReceive(Context arg0, Intent arg1) {
				updateTime();
			}
		};

		try {
			registerReceiver(mTimeReceiver, filter);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void unregisterTimeChange(){
		try {
			unregisterReceiver(mTimeReceiver);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
