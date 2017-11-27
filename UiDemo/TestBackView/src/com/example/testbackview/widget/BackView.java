/**
 * 
 */
package com.example.testbackview.widget;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
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
 * @author YC
 * @time 2017-8-12 上午11:52:06
 * TODO:
 */
public class BackView extends View {
	
	private static final String TAG = "BackView";
	private Paint pointPaint;
	private Paint circlePaint;
	private int mWidth = 500, mHeight = 400;
	private Path path = new Path();
	private final int NUMBER = 6;
	private final int OFFSET = 20;
	private final int backViewHeight = (int) (mHeight*0.89);
	private final float START_DEGREED = 75.0f;
	private final float START_DX = 50.0f;
	/**增加因子 (375-260)/35*/
	private final float ADD_FACTOR = 2.5f;
	/**中间线因子(375-250)/35*/
	private final float MID_FACTOR = 3.0f;
	/**最大转角*/
	private final float MAX_CHANGE_DEGREED = 35.0f;
	/**转角*/
	private float changeDegreed = 0.0f;
	
	private final int PADDING = 50;
	private Point mBottomLeft, mBottomMid, mBottomRight;
	private Point mTopLeft, mTopMid, mTopRight;
	private Point mCtrlLeft, mCtrlMid, mCtrlRight;
	
	private final List<Float> DIVIDRE = new ArrayList<Float>(){
		{
			add(1.0f);
			add(2.5f);
			add((float) (2.5f*1.5));
			add((float) (2.5f*1.5*1.5));
			add((float) (2.5f*1.5*1.5*1.5));
			add((float) (2.5f*1.5*1.5*1.5*1.5));
//			add((float) (2.0f*1.5*1.5*1.5*1.5*1.5));
		}
	};
	
	
	/**原始点*/
	private List<Point> mLeftPoints = new ArrayList<Point>();
	private List<Point> mRightPoints = new ArrayList<Point>();
	
	
	public BackView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initPaints();
	}


	public BackView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public BackView(Context context) {
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
		
		circlePaint = new Paint();
		circlePaint.setStrokeWidth(3);
		circlePaint.setColor(Color.GREEN);
		circlePaint.setAntiAlias(true);
		//不填充
		circlePaint.setStyle(Paint.Style.STROKE);
		
		mBottomLeft = new Point(PADDING, mHeight);
		mBottomMid = new Point(PADDING + (mWidth - 2*PADDING)/2, mHeight);
		mBottomRight = new Point(mWidth - PADDING, mHeight);
		
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
		
		calcTopPoints();
		calcCtrlPoints();
		calcDividePoints();
//		drawPoints(canvas);
		path.reset();
		path.moveTo(mBottomLeft.x, mBottomLeft.y);
		path.quadTo(mCtrlLeft.x, mCtrlLeft.y, mTopLeft.x, mTopLeft.y);
		
		path.moveTo(mBottomMid.x, mBottomMid.y);
//		path.lineTo(mTopMid.x, mTopMid.y);
		path.quadTo(mCtrlMid.x, mCtrlMid.y, mTopMid.x, mTopMid.y);
		
		path.moveTo(mBottomRight.x, mBottomRight.y);
		path.quadTo(mCtrlRight.x, mCtrlRight.y, mTopRight.x, mTopRight.y);
		
		for (int i = 0; i < mLeftPoints.size(); i++){
			path.moveTo(mLeftPoints.get(i).x, mLeftPoints.get(i).y);
			path.lineTo(mRightPoints.get(i).x, mRightPoints.get(i).y);
		}
		
//		path.close();
		canvas.drawPath(path, circlePaint);
	}

	public void changeDegreed(float degreed) {
		if (degreed > MAX_CHANGE_DEGREED){
			degreed = MAX_CHANGE_DEGREED;
		}
		if (degreed < -MAX_CHANGE_DEGREED){
			degreed = -MAX_CHANGE_DEGREED;
		}
		this.changeDegreed = degreed;
		invalidate();
	}
	

	/**
	 * 
	 */
	private void drawPoints(Canvas canvas) {
		
		canvas.drawPoint(mBottomLeft.x, mBottomLeft.y, pointPaint);
		canvas.drawPoint(mBottomMid.x, mBottomMid.y, pointPaint);
		canvas.drawPoint(mBottomRight.x, mBottomRight.y, pointPaint);
		
		
		canvas.drawPoint(mTopLeft.x, mTopLeft.y, pointPaint);
		canvas.drawPoint(mTopMid.x, mTopMid.y, pointPaint);
		canvas.drawPoint(mTopRight.x, mTopRight.y, pointPaint);
		
		canvas.drawPoint(mCtrlLeft.x, mCtrlLeft.y, pointPaint);
		canvas.drawPoint(mCtrlRight.x, mCtrlRight.y, pointPaint);
		canvas.drawPoint(mCtrlMid.x, mCtrlMid.y, pointPaint);
		
		for (int i = 0; i < mLeftPoints.size(); i++){
			canvas.drawPoint(mLeftPoints.get(i).x, mLeftPoints.get(i).y, pointPaint);
		}
		
		for (int i = 0; i < mRightPoints.size(); i++){
			canvas.drawPoint(mRightPoints.get(i).x, mRightPoints.get(i).y, pointPaint);
		}
		
	}

	/**
	 * 计算顶部三点
	 */
	private void calcTopPoints(){
		
		float height1 = backViewHeight - Math.abs(changeDegreed) * ADD_FACTOR;
		float height2 = backViewHeight - Math.abs(changeDegreed) * MID_FACTOR;
		
		int width1 = Math.abs((int) (int) (height1/Math.tan(Math.PI*(START_DEGREED - Math.abs(changeDegreed))/180)));
		int width2 = Math.abs((int) (int) (height2*Math.tan(Math.PI*(Math.abs(changeDegreed))/180)));
		
		if (changeDegreed >= 0){
//			算顶部左边两个点
			mTopLeft = new Point(mBottomLeft.x + width1, (int) (mHeight - height1));
			mTopMid = new Point(mBottomMid.x + width2, (int) (mHeight - height2));
			mTopRight = new Point(2*mTopMid.x - mTopLeft.x, 2*mTopMid.y - mTopLeft.y);
		}
		else{
//			算顶部右边两个点
			mTopRight = new Point(mBottomRight.x - width1, (int) (mHeight - height1));
			mTopMid = new Point(mBottomMid.x - width2, (int) (mHeight - height2));
			mTopLeft = new Point(2*mTopMid.x - mTopRight.x, 2*mTopMid.y - mTopRight.y);
		}
	}
	
	/**
	 * 计算控制点
	 */
	private void calcCtrlPoints(){
		Point leftMid = new Point((mBottomLeft.x + mTopLeft.x)/2, (mBottomLeft.y + mTopLeft.y)/2);
	
		float d;
		float k = (mBottomLeft.y - mTopLeft.y)*1.0f/(mBottomLeft.x - mTopLeft.x);
		int x, y;
		
		final float dxIn = 60.0f/(MAX_CHANGE_DEGREED + (90 - START_DEGREED));
		final float dxOut = 40.0f/(MAX_CHANGE_DEGREED - (90 - START_DEGREED));
		
		final float dxMid = 50.0f/(MAX_CHANGE_DEGREED - (90 - START_DEGREED));
		
		
		/**直角向右*/
		if (changeDegreed > (START_DEGREED - 90)){
			d = (changeDegreed + (90 - START_DEGREED))*dxIn;
//			Log.i(TAG, "degreed = " + changeDegreed + ", d = " + d);
			x = leftMid.x - (int) (Math.sqrt(d*d - (1/(k*k) + 1)));
		}
		else {
			d = (changeDegreed + (90 - START_DEGREED))*dxOut;
//			Log.i(TAG, "degreed = " + changeDegreed + ", d = " + d);
			x = leftMid.x + (int) (Math.sqrt(d*d - (1/(k*k) + 1)));
		}
		y = (int) (leftMid.y - (x - leftMid.x)/k);
		mCtrlLeft = new Point(x, y);
		
		Point rightMid = new Point((mBottomRight.x + mTopRight.x)/2, (mBottomRight.y + mTopRight.y)/2);
		k = (mBottomRight.y - mTopRight.y)*1.0f/(mBottomRight.x - mTopRight.x);
		/**直角向左*/
		if (changeDegreed > (90 - START_DEGREED)){
			d = (changeDegreed - (90 - START_DEGREED))*dxOut;
			x = rightMid.x - (int) (Math.sqrt(d*d - (1/(k*k) + 1)));
		}
		else{
			d = (changeDegreed - (90 - START_DEGREED))*dxIn;
			x = rightMid.x + (int) (Math.sqrt(d*d - (1/(k*k) + 1)));
		}
		y = (int) (rightMid.y - (x - rightMid.x)/k);
		mCtrlRight = new Point(x, y);
		
		Point midMid = new Point((mBottomMid.x + mTopMid.x)/2, (mBottomMid.y + mTopMid.y)/2);
		k = (mBottomMid.y - mTopMid.y)*1.0f/(mBottomMid.x - mTopMid.x);
		if (changeDegreed > 0){
			d = changeDegreed *dxMid;
			x = midMid.x - (int) (Math.sqrt(d*d - (1/(k*k) + 1)));
		}
		else{
			d = changeDegreed *dxMid;
			x = midMid.x + (int) (Math.sqrt(d*d - (1/(k*k) + 1)));
		}
		y = (int) (midMid.y - (x - midMid.x)/k);
		mCtrlMid = new Point(x, y);
		
	}
	
	/**
	 * 计算Bezier曲线上的点坐标	
	 * bezier = (1-t)(1-t)*start + 2*t*(1-t)*ctrl + t*t*end
	 * @param start
	 * @param end
	 * @param ctrl
	 * @param t
	 * @return
	 */
	private Point getBezierPoint(Point start, Point end, Point ctrl, float t) {
        int x = (int) ((1-t)*(1-t)*start.x + 2*t*(1-t)*ctrl.x + t*t*end.x);
        int y = (int) ((1-t)*(1-t)*start.y + 2*t*(1-t)*ctrl.y + t*t*end.y);
        return new Point(x, y);
    }

	/**
	 * 
	 */
	private void calcDividePoints() {
		mLeftPoints.clear();
		for(int i = 0; i < DIVIDRE.size(); i++){
			float t = 1.0f - DIVIDRE.get(DIVIDRE.size() - 1 - i) * 20/backViewHeight;
			mLeftPoints.add(getBezierPoint(mBottomLeft, mTopLeft, mCtrlLeft, t));
		}
		
		mRightPoints.clear();
		for(int i = 0; i < DIVIDRE.size(); i++){
			float t = 1.0f - DIVIDRE.get(DIVIDRE.size() - 1 - i) * 20/backViewHeight;
			mRightPoints.add(getBezierPoint(mBottomRight, mTopRight, mCtrlRight, t));
		}
		
	}



	
}
