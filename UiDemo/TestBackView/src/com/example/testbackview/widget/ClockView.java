/**
 * 
 */
package com.example.testbackview.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.FrameLayout;
import android.widget.ImageView;

/**
 * @author YC2
 * @time 2017-11-27 下午6:06:31
 * TODO:
 */
public class ClockView extends FrameLayout {

	private static final String TAG = "ClockView";
	private ImageView img_Hour;
	private ImageView img_Minute;
	private ImageView img_Second;
	
	private float hourRotation = 0.0f;
	private float minuteRotation = 0.0f;
	private float secondRoatetion = 0.0f;
	
	/**
	 * @param context
	 */
	public ClockView(Context context) {
		this(context, null);
	}
	
	/**
	 * @param context
	 * @param attrs
	 */
	public ClockView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	
	/**
	 * @param context
	 * @param attrs
	 * @param defStyleAttr
	 */
	public ClockView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
//		initView();
	}

	/**
	 * 
	 */
	private void initView() {
		int childCount = getChildCount();
		Log.i(TAG, "getChildCount = " + childCount);
		if (childCount > 0){
			img_Hour = (ImageView) getChildAt(0);
		}
		if (childCount > 1){
			img_Minute = (ImageView) getChildAt(1);
		}
		if (childCount > 2){
			img_Second = (ImageView) getChildAt(2);
		}
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		initView();
//		super.onDraw(canvas);
		if (img_Hour != null){
			img_Hour.setRotation(hourRotation);
		}
		if (img_Minute != null){
			img_Minute.setRotation(minuteRotation);
		}
		if (img_Second != null){
			img_Second.setRotation(secondRoatetion);
		}
	}


	public void setClockTime(int hour, int minute, int second){
		hourRotation = 30*hour + 30*minute/60 + 30*second/3600;
		minuteRotation = 6*minute + 6*second/60;
		secondRoatetion = 6*second;
		Log.i(TAG, "hour = " + hour + ", minute = " + minute + ", second = " + second);
		Log.i(TAG, "hourRotation = " + hourRotation + ", minuteRotation = " + minuteRotation + ", secondRoatetion = " + secondRoatetion);
		invalidate();
	}
	
	

}
