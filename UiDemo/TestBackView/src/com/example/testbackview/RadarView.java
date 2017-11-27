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
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * @author YC
 * @time 2017-8-12 上午11:52:06
 * TODO:
 */
public class RadarView extends View {
	
	private static final String TAG = "BackCarView";
	private Paint pointPaint;
	private Paint circlePaint;
	private Paint progressPaint;
	private Paint textPaint;
//	private Canvas bitmapCanvas;
//	private Bitmap bitmap;
	private Path path = new Path();
	private int mRadio = 200;
	private int backviewLength = 80;
	private int mWidth = 2*mRadio, mHeight = 2*mRadio;
	private int mDegreed = 100;
	private final int NUMBER = 8;
	
	private float testControlX;
	private float testControlY;
	private float testControlX2;
	private float testControlY2;
	
	/**原始点*/
	private List<Point> mInPoints = new ArrayList<Point>();
	private List<Point> mOutPoints = new ArrayList<Point>();
	
	/**中间点*/
	private List<Point> mMidPoints = new ArrayList<Point>();
	/**中间点的中间点*/
	private List<Point> mMidMidPoints = new ArrayList<Point>();
	/**控制点*/
	private List<Point> mCtrlPoints = new ArrayList<Point>();
	
	public RadarView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initPaints();
	}


	public RadarView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public RadarView(Context context) {
		this(context, null);
	}
	
	private void initPaints() {
		
		calcInPoints();
		calcOutPoint();
		
		pointPaint = new Paint();
		pointPaint.setStrokeWidth(3);
		//画笔样式
		pointPaint.setStyle(Paint.Style.FILL); 
		pointPaint.setColor(Color.RED);
		//设置笔刷类型为圆形
		pointPaint.setStrokeCap(Cap.ROUND);
		//抗锯齿
		pointPaint.setAntiAlias(true);
		
		circlePaint = new Paint();
		circlePaint.setColor(Color.argb(0xFF, 0x3a, 0x8c, 0x6c));
		circlePaint.setAntiAlias(true);
		//不填充
		circlePaint.setStyle(Paint.Style.STROKE);
		
		progressPaint = new Paint();
		progressPaint.setAntiAlias(true);
		progressPaint.setColor(Color.argb(0xFF, 0x00, 0x00, 0xFF));
		
		//不填充
		progressPaint.setStyle(Paint.Style.FILL_AND_STROKE);
//		 /* 设置渐变色 这个正方形的颜色是改变的 */  
//        Shader mShader = new LinearGradient(0, 0, 100, 100,  
//                new int[] { Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW,  
//                        Color.LTGRAY }, null, Shader.TileMode.REPEAT); // 一个材质,打造出一个线性梯度沿著一条线。  
        
		RadialGradient radialGradient = new RadialGradient(mRadio, mRadio, 
				mRadio, 
				Color.RED, 
				Color.YELLOW, 
				Shader.TileMode.MIRROR);
		/*LinearGradient linearGradient = new LinearGradient(
				mOutPoints.get(4).x, mInPoints.get(0).y, 
				mOutPoints.get(4).x, mOutPoints.get(4).y, 
				 new int[] { Color.RED, Color.YELLOW}, 
				 null, 
				Shader.TileMode.MIRROR);*/
		progressPaint.setShader(radialGradient); 
		//重叠部分
//		progressPaint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		
		
		textPaint = new Paint();
		textPaint.setTextSize(30);
		textPaint.setColor(Color.WHITE);
		textPaint.setAntiAlias(true);
		textPaint.setFakeBoldText(true);//文字加粗
	
//		bitmap = Bitmap.createBitmap(mWidth, mHeight, Config.ARGB_8888);
//		bitmapCanvas = new Canvas(bitmap);
		
		
		
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
		int centerX = mRadio;
		int centerY = mRadio;
//		canvas.drawCircle(mWidth/2, mHeight/2, mWidth/2, progressPaint);
		RectF out = new RectF(0, 0, mWidth, mHeight);
//		第四个参数如果为True时，在绘制圆弧时将圆心包括在内，通常用来绘制扇形
		final float startAngle = (180-mDegreed)/2;
//		bitmapCanvas.save();
		canvas.drawArc(out, startAngle, mDegreed, true, circlePaint);
		
		int inRadio = mRadio -backviewLength;
//		bitmapCanvas.drawCircle(mWidth/2, mHeight/2, inRadio, circlePaint);
		RectF in = new RectF(backviewLength, backviewLength, inRadio*2 + backviewLength, inRadio*2+ backviewLength);
		canvas.drawArc(in, startAngle, mDegreed, true, circlePaint);
		
		//画分割线
//		for (int i = 0; i < NUMBER; i++){
//			canvas.drawLine(centerX, centerY, mOutPoints.get(i).x, mOutPoints.get(i).y, circlePaint);
//		}
		
		pointPaint.setColor(Color.RED);
		for(int i = 0; i < NUMBER; i++){
			canvas.drawPoint(mInPoints.get(i).x, mInPoints.get(i).y, pointPaint);//画多个点
		}

		mMidPoints.clear();
		mMidPoints = calcMidPoints(mInPoints);
		
		mMidMidPoints.clear();
		mMidMidPoints = calcMidPoints(mMidPoints);
		
		calcCtrlPoints();
		
		pointPaint.setColor(Color.WHITE);
		for(int i = 0; i < mCtrlPoints.size(); i++){
			canvas.drawPoint(mCtrlPoints.get(i).x, mCtrlPoints.get(i).y, pointPaint);//画多个点
		}
		
		path.reset();
		
		for (int i = 0; i < mInPoints.size(); i++){
            if (i == 0){// 第一条为二阶贝塞尔
            	path.moveTo(mInPoints.get(i).x, mInPoints.get(i).y);// 起点
            	path.quadTo(mCtrlPoints.get(i).x, mCtrlPoints.get(i).y,// 控制点
            			mInPoints.get(i + 1).x,mInPoints.get(i + 1).y);
            }else if(i < mInPoints.size() - 2){// 三阶贝塞尔
            	path.cubicTo(mCtrlPoints.get(2*i-1).x,mCtrlPoints.get(2*i-1).y,// 控制点
            			mCtrlPoints.get(2*i).x,mCtrlPoints.get(2*i).y,// 控制点
                        mInPoints.get(i+1).x,mInPoints.get(i+1).y);// 终点
            }else if(i == mInPoints.size() - 2){// 最后一条为二阶贝塞尔
//            	path.moveTo(mInPoints.get(i).x, mInPoints.get(i).y);// 起点
            	path.quadTo(mCtrlPoints.get(mCtrlPoints.size()-1).x,mCtrlPoints.get(mCtrlPoints.size()-1).y,
            			mInPoints.get(i+1).x,mInPoints.get(i+1).y);// 终点
            }
        }
		
		
		path.lineTo(mOutPoints.get(NUMBER-1).x, mOutPoints.get(NUMBER-1).y);
		path.arcTo(out, startAngle, mDegreed);
		path.lineTo(mInPoints.get(0).x, mInPoints.get(0).y);
		path.close();
		canvas.drawPath(path, progressPaint);
		
	}
	
	
	
	/**
	 * @param points
	 * @param i
	 * @param j
	 */
	public void changePoints(int index, int d) {
		int centerX = mRadio;
		int centerY = mRadio;
		int radio = mRadio -backviewLength + d;
		double perDegreed = mDegreed * 1.0f/(NUMBER-1);
		double startAngle = (180-mDegreed)*1.0f/2;
		double angle = startAngle + index*perDegreed;
		double dy = radio * Math.sin(Math.PI*angle/180);
		double dx = radio * Math.cos(Math.PI*angle/180);
		mInPoints.get(index).x = (int) (centerX - dx);
		mInPoints.get(index).y = (int) (centerY + dy);
		invalidate();
	}


	private void calcOutPoint(){
		int centerX = mRadio;
		int centerY = mRadio;
		double perDegreed = mDegreed * 1.0f/(NUMBER-1);
		double startAngle = (180-mDegreed)*1.0f/2;
		for(int i = 0; i < NUMBER; i++){
			double angle = startAngle + i*perDegreed;
			double dy = mRadio * Math.sin(Math.PI*angle/180);
			double dx = mRadio * Math.cos(Math.PI*angle/180);
			Point point = new Point((int)(centerX - dx), (int)(centerY + dy));
			Log.i(TAG, String.format("point[%d], angle[%f] = (%d, %d)", i, angle, point.x, point.y));
			mOutPoints.add(point);
		}
	}

	private void calcInPoints(){
		int centerX = mRadio;
		int centerY = mRadio;
		int radio = mRadio -backviewLength;
		double perDegreed = mDegreed * 1.0f/(NUMBER-1);
		double startAngle = (180-mDegreed)*1.0f/2;
		for(int i = 0; i < NUMBER; i++){
			double angle = startAngle + i*perDegreed;
			double dy = radio * Math.sin(Math.PI*angle/180);
			double dx = radio * Math.cos(Math.PI*angle/180);
			Point point = new Point((int)(centerX - dx), (int)(centerY + dy));
			Log.i(TAG, String.format("point[%d], angle[%f] = (%d, %d)", i, angle, point.x, point.y));
			mInPoints.add(point);
		}
	}
	
	/**
	 * 计算中间点
	 */
	private List<Point> calcMidPoints(List<Point> points){
		List<Point> midPoints = new ArrayList<Point>();
		for(int i = 0; i < points.size(); i++){
			Point midPoint = null;
            if (i == points.size()-1){
                return midPoints;
            }else {
                midPoint = new Point((points.get(i).x + points.get(i + 1).x)/2, 
                		(points.get(i).y + points.get(i + 1).y)/2);
            }
            midPoints.add(midPoint);
		}
		return midPoints;
	}
	
	private void calcCtrlPoints(){
		mCtrlPoints.clear();
		for (int i = 0; i < mInPoints.size(); i ++){
            if (i ==0 || i == mInPoints.size()-1){
                continue;
            }else{
                Point before = new Point();
                Point after = new Point();
                before.x = mInPoints.get(i).x - mMidMidPoints.get(i - 1).x + mMidPoints.get(i - 1).x;
                before.y = mInPoints.get(i).y - mMidMidPoints.get(i - 1).y + mMidPoints.get(i - 1).y;
                after.x = mInPoints.get(i).x - mMidMidPoints.get(i - 1).x + mMidPoints.get(i).x;
                after.y = mInPoints.get(i).y - mMidMidPoints.get(i - 1).y + mMidPoints.get(i).y;
                mCtrlPoints.add(before);
                mCtrlPoints.add(after);
            }
        }
	}
	
}
