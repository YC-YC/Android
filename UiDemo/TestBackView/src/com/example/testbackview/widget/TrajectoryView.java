/**
 * 
 */
package com.example.testbackview.widget;

import java.util.ArrayList;
import java.util.List;

import com.example.testbackview.utils.GeometricUtil;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PathMeasure;
import android.graphics.PointF;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.Paint.Cap;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * 轨迹线
 * @author YC
 * @time 2017-8-12 上午11:52:06
 * TODO:
 */
public class TrajectoryView extends View {
	
	private static final String TAG = "BackView";
	private Paint pointPaint;
	private Paint linePaint;
	private int mWidth = 1000, mHeight = 600;
	private int mBottomLen = 900;
	private int mLength = 500;
	private int PADDING = 50;
	private float mAngle = 60.0f;
	private Path path = new Path();
	private Path drawPath = new Path();
	
	private PointF mLeftBottom, mLeftTop;
	private PointF mRightBottm, mRightTop;
	
	/**原始点*/
	private List<PointF> mLeftPoints = new ArrayList<PointF>();
	private List<PointF> mRightPoints = new ArrayList<PointF>();
	
	
	public TrajectoryView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initPaints();
	}


	public TrajectoryView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public TrajectoryView(Context context) {
		this(context, null);
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
		
		linePaint = new Paint();
		linePaint.setStrokeWidth(5);
		linePaint.setColor(Color.GREEN);
		linePaint.setStrokeCap(Cap.ROUND);
		linePaint.setAntiAlias(true);
		//不填充
		linePaint.setStyle(Paint.Style.STROKE);
		
		
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int width = MeasureSpec.makeMeasureSpec(mWidth, MeasureSpec.EXACTLY);
		int height = MeasureSpec.makeMeasureSpec(mHeight, MeasureSpec.EXACTLY);
		super.onMeasure(width, height);
//		Log.i(TAG, "onMeasure width = " + getMeasuredWidth());
		
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
//		Log.i(TAG, "onDraw");
		calcDividePoints();
		drawPoints(canvas, mLeftPoints);
		drawPoints(canvas, mRightPoints);
		drawTrajectory(canvas);
	}
	
	private void drawPoints(Canvas canvas, List<PointF> points){
		for(int i = 0; i < points.size(); i++){
			canvas.drawPoint(points.get(i).x, points.get(i).y, pointPaint);
			Log.i(TAG, "point[" + i + "] = " + points.get(i).toString());
		}
	}

	private void drawTrajectory(Canvas canvas){
		
		
		linePaint.setColor(Color.GREEN);
		canvas.drawLine(mLeftPoints.get(2).x, mLeftPoints.get(2).y, 
				mLeftPoints.get(3).x, mLeftPoints.get(3).y, linePaint);
		canvas.drawLine(mLeftPoints.get(3).x, mLeftPoints.get(3).y, 
				mLeftPoints.get(3).x+PADDING, mLeftPoints.get(3).y, linePaint);
		canvas.drawLine(mRightPoints.get(2).x, mRightPoints.get(2).y, 
				mRightPoints.get(3).x, mRightPoints.get(3).y, linePaint);
		canvas.drawLine(mRightPoints.get(3).x, mRightPoints.get(3).y, 
				mRightPoints.get(3).x-PADDING, mRightPoints.get(3).y, linePaint);
		
		linePaint.setColor(Color.YELLOW);
		canvas.drawLine(mLeftPoints.get(1).x, mLeftPoints.get(1).y, 
				mLeftPoints.get(2).x, mLeftPoints.get(2).y, linePaint);
		canvas.drawLine(mLeftPoints.get(2).x, mLeftPoints.get(2).y, 
				mLeftPoints.get(2).x+PADDING, mLeftPoints.get(2).y, linePaint);
		canvas.drawLine(mRightPoints.get(1).x, mRightPoints.get(1).y, 
				mRightPoints.get(2).x, mRightPoints.get(2).y, linePaint);
		canvas.drawLine(mRightPoints.get(2).x, mRightPoints.get(2).y, 
				mRightPoints.get(2).x-PADDING, mRightPoints.get(2).y, linePaint);
		
		linePaint.setColor(Color.RED);
		canvas.drawLine(mLeftPoints.get(0).x, mLeftPoints.get(0).y, 
				mLeftPoints.get(1).x, mLeftPoints.get(1).y, linePaint);
		canvas.drawLine(mLeftPoints.get(1).x, mLeftPoints.get(1).y, 
				mLeftPoints.get(1).x+PADDING, mLeftPoints.get(1).y, linePaint);
		canvas.drawLine(mRightPoints.get(0).x, mRightPoints.get(0).y, 
				mRightPoints.get(1).x, mRightPoints.get(1).y, linePaint);
		canvas.drawLine(mRightPoints.get(1).x, mRightPoints.get(1).y, 
				mRightPoints.get(1).x-PADDING, mRightPoints.get(1).y, linePaint);
		
	}

	

	/**
	 * 
	 */
	private void calcDividePoints() {
		
		float x = (mWidth - mBottomLen)/2;
		float y = mHeight;
		mLeftBottom = new PointF(x, y);
		mLeftPoints.clear();
		mLeftPoints.add(mLeftBottom);
		float dist = 0.5f*mLength;
		mLeftPoints.add(GeometricUtil.getPointOnDistAndAngle(mLeftBottom, dist, 180-mAngle, false));
		dist = 0.8f*mLength;
		mLeftPoints.add(GeometricUtil.getPointOnDistAndAngle(mLeftBottom, dist, 180-mAngle, false));
		dist = 1.0f*mLength;
		mLeftPoints.add(GeometricUtil.getPointOnDistAndAngle(mLeftBottom, dist, 180-mAngle, false));
		
//		mLeftTop = GeometricUtil.getPointOnDistAndAngle(mLeftBottom, mLength, mAngle, true);
		
		x += mBottomLen;
		mRightBottm = new PointF(x, y);
//		mRightTop = GeometricUtil.getPointOnDistAndAngle(mRightBottm, mLength, 180-mAngle, true);
		mRightPoints.clear();
		mRightPoints.add(mRightBottm);
		dist = 0.5f*mLength;
		mRightPoints.add(GeometricUtil.getPointOnDistAndAngle(mRightBottm, dist, mAngle, false));
		dist = 0.8f*mLength;
		mRightPoints.add(GeometricUtil.getPointOnDistAndAngle(mRightBottm, dist, mAngle, false));
		dist = 1.0f*mLength;
		mRightPoints.add(GeometricUtil.getPointOnDistAndAngle(mRightBottm, dist, mAngle, false));
		
	}



	
}
