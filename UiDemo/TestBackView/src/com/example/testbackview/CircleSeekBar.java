/**
 * 
 */
package com.example.testbackview;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * 
 * @author YC
 * @time 2017-8-22 下午3:53:49
 * TODO:
 */
public class CircleSeekBar extends View {
	
	private static final String TAG = "CircleSeekBar";
	private Paint pointPaint;
	private Paint circlePaint;
	private Path path = new Path();
	private int mRadioHorizontal = 150;
	private int mRadioVertical = 200;
	private int padding = 10;
	private int mWidth,mHeight;
	
	
	/**原始点*/
	private List<Point> mLeftPoints = new ArrayList<Point>();
	
	
	public CircleSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		getStyleAttributes(attrs);
		initPaints();
	}


	public CircleSeekBar(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public CircleSeekBar(Context context) {
		this(context, null);
	}
	
	/**
	 * @param attrs
	 */
	private void getStyleAttributes(AttributeSet attrs) {
//		final TypedArray ta = getContext().obtainStyledAttributes(attrs, 
//				R.styleable.BackEnveloper);
//		
//		mRadio = (int) ta.getInt(R.styleable.BackEnveloper_radio, 150);
//		mWidth = 2*mRadio;
//		mHeight = 2*mRadio;
//		float persent = ta.getFloat(R.styleable.BackEnveloper_persent, 0.5f);
//		mViewLength = (int) (mRadio*persent);
//		mDegreed = (int) ta.getInt(R.styleable.BackEnveloper_sweepAngle, 120);
//		mNumber = (int) ta.getInt(R.styleable.BackEnveloper_number, 4);
//		mFromColor = ta.getColor(R.styleable.BackEnveloper_from_color, 0xFF00FF00);
//		mToColor = ta.getColor(R.styleable.BackEnveloper_to_color, 0x0000FF00);
//		mDirection = ta.getInt(R.styleable.BackEnveloper_direction, 1);
//		
//		Log.i(TAG, String.format("get attr: " +
//				"\nmRadio[%d]" +
//				"\npersent[%f] " +
//				"\nmDegreed[%d] " +
//				"\nNUMBER[%d] " +
//				"\nmFromColor[%d]" +
//				"\nmToColor[%d]" +
//				"\nmDirection[%d]", 
//				mRadio, persent, mDegreed, mNumber, mFromColor, mToColor, mDirection));
//		
//		ta.recycle();
		
		mWidth = 2*(mRadioHorizontal+padding);
		mHeight = 2*(mRadioVertical+padding);
	}
	
	private void initPaints() {
		
		
		pointPaint = new Paint();
		pointPaint.setStrokeWidth(5);
		//画笔样式
		pointPaint.setStyle(Paint.Style.FILL); 
		pointPaint.setColor(Color.RED);
		//设置笔刷类型为圆形
		pointPaint.setStrokeCap(Cap.ROUND);
		//抗锯齿
		pointPaint.setAntiAlias(true);
		
		circlePaint = new Paint();
		circlePaint.setStrokeWidth(3);
		circlePaint.setColor(Color.GREEN);
		circlePaint.setAntiAlias(true);
		//不填充
		circlePaint.setStyle(Paint.Style.STROKE);
		
		
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int width = MeasureSpec.makeMeasureSpec(mWidth, MeasureSpec.EXACTLY);
		int height = MeasureSpec.makeMeasureSpec(mHeight, MeasureSpec.EXACTLY);
		super.onMeasure(width, height);
		Log.i(TAG, "onMeasure width = " + getMeasuredWidth());
		
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		Log.i(TAG, "onDraw");
		
//		path.reset();
		RectF oval=new RectF();
		oval.left = padding;
		oval.top = padding;
		oval.right = oval.left + mRadioHorizontal*2;
		oval.bottom = oval.top + mRadioVertical*2;
//		canvas.drawOval(oval, circlePaint);
		
		canvas.drawArc(oval, 30, 300, false, circlePaint);
//		path.close();
//		canvas.drawPath(path, circlePaint);
	}

	


	




	
}
