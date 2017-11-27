/**
 * 
 */
package com.example.testbackview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * 
 * 
 * @author YC
 * @time 2017-9-2 上午10:44:27
 * TODO:
 * PathMeasure的使用
 */
public class GranzortView extends View {

	private static final String TAG = "GranzortView";
	
	private static final int MSG_WHAT_UPDATE_CIRCLE_STATE = 1;
	private static final int MSG_WHAT_UPDATE_TRANGLE_STATE = 2;
	private static final int MSG_WHAT_UPDATE_FINISH_STATE = 3;
	
	private Paint paint;
	/**内圆*/
	private Path innerCircle;
	/**外圆*/
	private Path outerCircle;
	private Path trangle1;
	private Path trangle2;
	/**用于截取*/
	private Path drawPath;
	private PathMeasure pathMeasure;
	
	private Handler mHandle;
	private State mCurState;
	private int mWidth, mHeight;
	private float percent;
	
	/**状态*/
	private enum State{
		CIRCLE_STATE,
		TRANGLE_STATE,
		FINISH_STATE;
	}

	public GranzortView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	public GranzortView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	
	public GranzortView(Context context) {
		this(context, null);
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		Log.i(TAG, "onSizeChanged w = " + w + ", h = " + h + ", oldw = " + oldw  + ", oldh = " + oldh);
		mWidth = w;
		mHeight = h;
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		Log.i(TAG, "onDraw mCurState = " + mCurState);
		canvas.save();
		canvas.translate(mWidth/2, mHeight/2);
		switch (mCurState) {
		case CIRCLE_STATE:
			drawPath.reset();
			pathMeasure.setPath(innerCircle, false);
			float endD = percent*pathMeasure.getLength();
			Log.i(TAG, "endD = " + endD);
			//需要关闭硬件加速
			pathMeasure.getSegment(0, endD, drawPath, true);
			canvas.drawPath(drawPath, paint);
			drawPath.reset();
			pathMeasure.setPath(outerCircle, false);
			pathMeasure.getSegment(0, percent*pathMeasure.getLength(), drawPath, true);
			canvas.drawPath(drawPath, paint);
			break;
		case TRANGLE_STATE:
			canvas.drawPath(innerCircle, paint);
			canvas.drawPath(outerCircle, paint);
			drawPath.reset();
			pathMeasure.setPath(trangle1, false);
			float stopD = percent * pathMeasure.getLength();
			float startD = stopD -(0.5f - Math.abs(0.5f - percent)) * 200;
			pathMeasure.getSegment(startD, stopD, drawPath, true);
			canvas.drawPath(drawPath, paint);
			drawPath.reset();
			pathMeasure.setPath(trangle2, false);
			stopD = percent * pathMeasure.getLength();
			startD = stopD -(0.5f - Math.abs(0.5f - percent)) * 200;
			pathMeasure.getSegment(startD, stopD, drawPath, true);
			canvas.drawPath(drawPath, paint);
			break;
		case FINISH_STATE:
			canvas.drawPath(innerCircle, paint);
			canvas.drawPath(outerCircle, paint);
			canvas.drawPath(trangle1, paint);
			canvas.drawPath(trangle2, paint);
			break;
		

		default:
			break;
		}
		
		canvas.restore();
	}
	
	private void init() {
		initPaint();
		initPath();
		initHandle();
		mCurState = State.CIRCLE_STATE;
		mHandle.sendEmptyMessage(MSG_WHAT_UPDATE_CIRCLE_STATE);
	}

	private void initPaint() {
		paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(Color.WHITE);
		paint.setStyle(Style.STROKE);
		paint.setStrokeWidth(5);
		paint.setStrokeCap(Cap.ROUND);
//		白色光影
		paint.setShadowLayer(15, 0, 0, Color.WHITE);
	}

	private void initPath() {
		innerCircle = new Path();
		outerCircle = new Path();
		trangle1 = new Path();
		trangle2 = new Path();
		drawPath = new Path();
		
		pathMeasure = new PathMeasure();
		
		RectF innerRect = new RectF(-220, -220, 220, 220);
		RectF outerRect = new RectF(-280, -280, 280, 280);
		
		innerCircle.addArc(innerRect, 150, -359.9f);
		outerCircle.addArc(outerRect, 60, -359.9f);
		
		pathMeasure.setPath(innerCircle, false);
		
		float[] pos = new float[2];
		
		/**获取路径0的坐标*/
		pathMeasure.getPosTan(0, pos, null);
		Log.i(TAG, "first point [" + pos[0] + "," + pos[1] + "]");
		trangle1.moveTo(pos[0], pos[1]);
		/**获取1/3路径的坐标*/
		pathMeasure.getPosTan(1.0f/3*pathMeasure.getLength(), pos, null);
		trangle1.lineTo(pos[0], pos[1]);
		pathMeasure.getPosTan(2.0f/3*pathMeasure.getLength(), pos, null);
		trangle1.lineTo(pos[0], pos[1]);
		trangle1.close();
		
		/**通过旋转角度获得新三角*/
		Matrix matrix = new Matrix();
		matrix.postRotate(-180);
		trangle1.transform(matrix, trangle2);
	}

	private void initHandle() {
		mHandle = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				switch (msg.what) {
				case MSG_WHAT_UPDATE_CIRCLE_STATE:
					if (percent < 1.0f){
						percent += 0.05;
						invalidate();
						mHandle.sendEmptyMessage(MSG_WHAT_UPDATE_CIRCLE_STATE);
					}
					else{
						mCurState = State.TRANGLE_STATE;
						percent = 0;
						mHandle.sendEmptyMessage(MSG_WHAT_UPDATE_TRANGLE_STATE);
					}
					
					break;
				case MSG_WHAT_UPDATE_TRANGLE_STATE:
					if (percent < 1.0f){
						percent += 0.05;
						invalidate();
						mHandle.sendEmptyMessage(MSG_WHAT_UPDATE_TRANGLE_STATE);
					}
					else{
						mCurState = State.FINISH_STATE;
						percent = 1.0f;
						mHandle.sendEmptyMessage(MSG_WHAT_UPDATE_FINISH_STATE);
					}
					break;
				case MSG_WHAT_UPDATE_FINISH_STATE:
					invalidate();
					break;

				default:
					break;
				}
			}
		};
	}

}
