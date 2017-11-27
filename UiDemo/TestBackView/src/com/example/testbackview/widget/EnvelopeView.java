/**
 * 
 */
package com.example.testbackview.widget;

import java.util.ArrayList;
import java.util.List;

import com.example.testbackview.R;
import com.example.testbackview.R.styleable;
import com.example.testbackview.utils.GeometricUtil;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.FontMetrics;
import android.graphics.Path;
import android.graphics.Path.FillType;
import android.graphics.PathMeasure;
import android.graphics.Point;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * 倒车包络线
 * @author YC
 * @time 2017-8-12 上午11:52:06
 * TODO:
 */
public class EnvelopeView extends View {
	
	private static final String TAG = "BackEnvelopeView";
	
	private Paint linePaint;
	private Paint pointPaint;
	private Paint textPaint;
	private Path path = new Path();
	
	/**半径*/
	private int mMaxRadio;
	/**当前半径*/
	private int mCurRadio;
	/**扇区的高度*/
	private int mViewLength;
	/**扇区角度*/
	private int mDegreed;
	/**扇区平分个数*/
	private int mNumber;
	/**阴影变化颜色*/
	private int mFromColor, mToColor;
	/**整个布局宽高*/
	private int mWidth, mHeight;
	private int mRotateAngle;
	/**文字方向*/
	private boolean bTextClockWise;
	private String mTipStr = "";
	
	
	/**原始点*/
	private List<Point> mInPoints = new ArrayList<Point>();
	private List<Point> mOutPoints = new ArrayList<Point>();
	
	/**中间点*/
	private List<Point> mMidPoints = new ArrayList<Point>();
	/**中间点的中间点*/
	private List<Point> mMidMidPoints = new ArrayList<Point>();
	/**控制点*/
	private List<Point> mCtrlPoints = new ArrayList<Point>();
	/**中心点*/
	private Point mCenterPoint;
	
	public EnvelopeView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		getStyleAttributes(attrs);
		init();
	}

	public EnvelopeView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public EnvelopeView(Context context) {
		this(context, null);
	}
	
	/**
	 * @param attrs
	 */
	private void getStyleAttributes(AttributeSet attrs) {
		final TypedArray ta = getContext().obtainStyledAttributes(attrs, 
				R.styleable.BackEnveloper);
		
		mMaxRadio = (int) ta.getInt(R.styleable.BackEnveloper_radio, 150);
		mCurRadio = mMaxRadio;
		mWidth = 2*mMaxRadio;
		mHeight = 2*mMaxRadio;
		float persent = ta.getFloat(R.styleable.BackEnveloper_persent, 0.5f);
		mViewLength = (int) (mMaxRadio*persent);
		mDegreed = (int) ta.getInt(R.styleable.BackEnveloper_sweepAngle, 120);
		mNumber = (int) ta.getInt(R.styleable.BackEnveloper_number, 4);
		mFromColor = ta.getColor(R.styleable.BackEnveloper_from_color, 0xFF00FF00);
		mToColor = ta.getColor(R.styleable.BackEnveloper_to_color, 0x0000FF00);
		mRotateAngle = ta.getInt(R.styleable.BackEnveloper_rotate_angle, 1);
		bTextClockWise = ta.getBoolean(R.styleable.BackEnveloper_text_clockwise, true);
//		Log.i(TAG, String.format("get attr: " +
//				"\nmRadio[%d]" +
//				"\npersent[%f] " +
//				"\nmDegreed[%d] " +
//				"\nNUMBER[%d] " +
//				"\nmFromColor[%d]" +
//				"\nmToColor[%d]" +
//				"\nmRotateAngle[%d]", 
//				mRadio, persent, mDegreed, mNumber, mFromColor, mToColor, mRotateAngle));
		ta.recycle();
	}
	
	private void init() {
		
		mCenterPoint = new Point(mMaxRadio, mMaxRadio);
		
		calcInPoints();
		if (mRotateAngle != 0){
			fixPoint(mInPoints);
		}
		calcOutPoint();
		if (mRotateAngle != 0){
			fixPoint(mOutPoints);
		}
		
		linePaint = new Paint();
		linePaint.setAntiAlias(true);
		linePaint.setColor(Color.argb(0xFF, 0x00, 0x00, 0xFF));
		
		//不填充
		linePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        
		RadialGradient radialGradient = new RadialGradient(mMaxRadio, mMaxRadio, 
				mMaxRadio, 
				mFromColor, 
				mToColor, 
				Shader.TileMode.MIRROR);
		linePaint.setShader(radialGradient); 
		
//		linePaint.setXfermode(new PorterDuffXfermode(Mode.DST_OUT));
		
		pointPaint = new Paint();
		pointPaint.setStrokeCap(Cap.ROUND);
		pointPaint.setStrokeWidth(6);
		pointPaint.setAntiAlias(true);
		pointPaint.setColor(Color.GREEN);
		
		textPaint = new Paint();
		textPaint.setTextSize(30);
		textPaint.setColor(Color.WHITE);
		textPaint.setAntiAlias(true);
		textPaint.setFakeBoldText(true);//文字加粗
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int width = MeasureSpec.makeMeasureSpec(mWidth, MeasureSpec.EXACTLY);
		int height = MeasureSpec.makeMeasureSpec(mHeight, MeasureSpec.EXACTLY);
		super.onMeasure(width, height);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {

		mMidPoints.clear();
		mMidPoints = calcMidPoints(mInPoints);

		mMidMidPoints.clear();
		mMidMidPoints = calcMidPoints(mMidPoints);

		calcCtrlPoints();

//		 if (mRotateAngle != 0){
//		 drawPoints(canvas, mInPoints);
//		 }
		
		drawEnvelopeLine(canvas);
		
//		drawTipStrAcr(canvas);
		drawTipStr(canvas);

	}

	/**
	 * 画包络线
	 * @param canvas
	 */
	private void drawEnvelopeLine(Canvas canvas) {
		RectF out = new RectF(mMaxRadio-mCurRadio, mMaxRadio-mCurRadio, mMaxRadio+mCurRadio, mMaxRadio+mCurRadio);
		final float startAngle = (180 - mDegreed) / 2;

		path.reset();
		/** 注意需要设置填充类型,若是同一个方向不需要设置 */
		path.setFillType(FillType.EVEN_ODD);

		for (int i = 0; i < mInPoints.size(); i++) {
			if (i == 0) {// 第一条为二阶贝塞尔
				path.moveTo(mInPoints.get(i).x, mInPoints.get(i).y);// 起点
				path.quadTo(mCtrlPoints.get(i).x, mCtrlPoints.get(i).y,// 控制点
						mInPoints.get(i + 1).x, mInPoints.get(i + 1).y);
			} else if (i < mInPoints.size() - 2) {// 三阶贝塞尔
				path.cubicTo(mCtrlPoints.get(2 * i - 1).x,
						mCtrlPoints.get(2 * i - 1).y,// 控制点
						mCtrlPoints.get(2 * i).x, mCtrlPoints.get(2 * i).y,// 控制点
						mInPoints.get(i + 1).x, mInPoints.get(i + 1).y);// 终点
			} else if (i == mInPoints.size() - 2) {// 最后一条为二阶贝塞尔
				path.quadTo(mCtrlPoints.get(mCtrlPoints.size() - 1).x,
						mCtrlPoints.get(mCtrlPoints.size() - 1).y,
						mInPoints.get(i + 1).x, mInPoints.get(i + 1).y);// 终点
			}
		}
		path.lineTo(mOutPoints.get(mNumber - 1).x,
				mOutPoints.get(mNumber - 1).y);
		path.arcTo(out, startAngle + mRotateAngle, mDegreed);
		path.lineTo(mInPoints.get(0).x, mInPoints.get(0).y);

		canvas.drawPath(path, linePaint);
		
		path.reset();
	}
	
	/**
	 * 圆弧上画文字
	 * @param canvas
	 */
	private void drawTipStrAcr(Canvas canvas) {
		if (!TextUtils.isEmpty(mTipStr)) {
			float textWidth = textPaint.measureText(mTipStr);
			int offset = mViewLength/2;
			float radio = mMaxRadio - offset;
			float textAngle = (float) GeometricUtil.calcArcDistAngle(textWidth, radio);
			RectF out = new RectF(offset, offset, 2*radio + offset, 2*radio + offset);
			final float startAngle = (180 - mDegreed) / 2;
			path.reset();
			if (bTextClockWise){
				path.arcTo(out, startAngle + mRotateAngle + (mDegreed - textAngle)/2, textAngle);
			}
			else{
				path.arcTo(out, startAngle + mRotateAngle + (mDegreed - textAngle)/2 + textAngle, -textAngle);
			}
			canvas.drawTextOnPath(mTipStr, path, 0, 0, textPaint);
		}
	}
	
	/**
	 * 直线上画文字
	 * @param canvas
	 */
	private void drawTipStr(Canvas canvas) {
		if (!TextUtils.isEmpty(mTipStr)) {
			float textWidth = textPaint.measureText(mTipStr);
			int offset = mViewLength/2;
			float radio = mMaxRadio - offset;
			float textAngle = (float) GeometricUtil.calcDistAngle(textWidth, radio);
//			Log.i(TAG, "textWidth = " + textWidth + ", textAngle = " + textAngle + ", radio = " + radio);
			final float startAngle = (180 - mDegreed) / 2;
			float angle = startAngle + mRotateAngle + (mDegreed - textAngle)/2;
			path.reset();
			if (bTextClockWise){
				Point startPoint = GeometricUtil.getPointOnCircle(mCenterPoint, radio, angle);
				Point endPoint = GeometricUtil.calcNewPoint(startPoint, mCenterPoint, textAngle);
				path.moveTo(startPoint.x, startPoint.y);
				path.lineTo(endPoint.x, endPoint.y);
//				canvas.drawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y, pointPaint);
			}
			else{
				FontMetrics fontMetrics = textPaint.getFontMetrics();// 获取文字规格
				int textHeight = (int) (fontMetrics.descent - fontMetrics.ascent);
				radio += textHeight;
				Point startPoint = GeometricUtil.getPointOnCircle(mCenterPoint, radio, angle);
				Point endPoint = GeometricUtil.calcNewPoint(startPoint, mCenterPoint, textAngle);
				
				path.moveTo(endPoint.x, endPoint.y);
				path.lineTo(startPoint.x, startPoint.y);
//				canvas.drawLine(endPoint.x, endPoint.y, startPoint.x, startPoint.y, pointPaint);
			}
//			canvas.drawPath(path, pointPaint);
			canvas.drawTextOnPath(mTipStr, path, 0, 0, textPaint);
		}
	}
	
	private void drawPoints(Canvas canvas, List<Point> points){
		for(int i = 0; i < points.size(); i++){
			Point point = points.get(i);
			canvas.drawPoint(point.x, point.y, pointPaint);
		}
	}
	
	/**
	 * 
	 * @param index
	 * @param d
	 */
	public void changePoints(int index, int d) {
		if (index > mNumber -1){
			Log.e(TAG, "Error index");
			return;
		}
		int centerX = mMaxRadio;
		int centerY = mMaxRadio;
		int radio = mMaxRadio -mViewLength + d;
		double perDegreed = mDegreed * 1.0f/(mNumber-1);
		double startAngle = (180-mDegreed)*1.0f/2;
		double angle = startAngle + index*perDegreed;
		double dy = radio * Math.sin(Math.PI*angle/180);
		double dx = radio * Math.cos(Math.PI*angle/180);
		mInPoints.get(index).x = (int) (centerX - dx);
		mInPoints.get(index).y = (int) (centerY + dy);
		if (mRotateAngle != 0){
			Point newPoint = GeometricUtil.calcNewPoint(mInPoints.get(index), mCenterPoint, mRotateAngle);
			mInPoints.set(index, newPoint);
		}
		invalidate();
	}
	
	public void changeRadio(int radio){
		mCurRadio = mMaxRadio - radio;
		calcInPoints();
		if (mRotateAngle != 0){
			fixPoint(mInPoints);
		}
		calcOutPoint();
		if (mRotateAngle != 0){
			fixPoint(mOutPoints);
		}
		if (radio <25){
			mFromColor = 0xFFFFFF00;
			mToColor = 0x88FFFF00;
		}
		else if (radio < 50){
			mFromColor = 0xFFFF8800;
			mToColor = 0x88FF8800;
		}
		else if (radio < 75){
			mFromColor = 0xFFFF5500;
			mToColor = 0x88FF5500;
		}
		else {
			mFromColor = 0xFFFF0000;
			mToColor = 0x88FF0000;
		}
		RadialGradient radialGradient = new RadialGradient(mMaxRadio, mMaxRadio, 
				mCurRadio, 
				mFromColor, 
				mToColor, 
				Shader.TileMode.MIRROR);
		linePaint.setShader(radialGradient); 
		invalidate();
	}
	
	public void setTipStr(String str){
		mTipStr = str;
		invalidate();
	}
	
	private void fixPoint(List<Point> points){
		for(int i = 0; i < points.size(); i++){
			Point point = points.get(i);
			Point newPoint = GeometricUtil.calcNewPoint(point, mCenterPoint, mRotateAngle);
			points.set(i, newPoint);
		}
	}
	
	private void calcOutPoint(){
		double perDegreed = mDegreed * 1.0f/(mNumber-1);
		double startAngle = (180-mDegreed)*1.0f/2;
		mOutPoints.clear();
		for(int i = 0; i < mNumber; i++){
			double angle = startAngle + i*perDegreed;
			double dy = mCurRadio * Math.sin(Math.PI*angle/180);
			double dx = mCurRadio * Math.cos(Math.PI*angle/180);
			Point point = new Point((int)(mCenterPoint.x - dx), (int)(mCenterPoint.y + dy));
//			Log.i(TAG, String.format("point[%d], angle[%f] = (%d, %d)", i, angle, point.x, point.y));
			mOutPoints.add(point);
		}
	}

	private void calcInPoints(){
		int radio = mCurRadio -mViewLength;
		double perDegreed = mDegreed * 1.0f/(mNumber-1);
		double startAngle = (180-mDegreed)*1.0f/2;
		mInPoints.clear();
		for(int i = 0; i < mNumber; i++){
			double angle = startAngle + i*perDegreed;
			double dy = radio * Math.sin(Math.PI*angle/180);
			double dx = radio * Math.cos(Math.PI*angle/180);
			Point point = new Point((int)(mCenterPoint.x - dx), (int)(mCenterPoint.y + dy));
//			Log.i(TAG, String.format("point[%d], angle[%f] = (%d, %d)", i, angle, point.x, point.y));
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
