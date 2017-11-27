package com.example.testbackview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Path;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * @author Administrator
 *下午9:35:43
 * TODO：
 */
public class MyProgressView extends View {

	private static final String TAG = "MyProgressView";
	public int mWidth = 200	, mHeight = 200;
	private Paint textPaint;
	private Paint circlePaint;
	private Paint progressPaint;
	private String text = "50%";
	private int max = 100;
	private int progress = 50;
	private int curProgress = 0;
	private Canvas bitmapCanvas;
	private Path path = new Path();
	private Bitmap bitmap;
	private GestureDetector detector;
	private int count = 50;
	private boolean isSimgleTap = false;
	
	private Handler mHandle = new Handler(){
		public void handleMessage(android.os.Message msg) {
			
		};
	};
	
	public MyProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initPaints();
	}


	public MyProgressView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public MyProgressView(Context context) {
		this(context, null);
	}
	
	private void initPaints() {
		circlePaint = new Paint();
		circlePaint.setColor(Color.argb(0xFF, 0x3a, 0x8c, 0x6c));
		circlePaint.setAntiAlias(true);
		
		progressPaint = new Paint();
		progressPaint.setAntiAlias(true);
		progressPaint.setColor(Color.argb(0xFF, 0x4e, 0xc9, 0x63));
		//重叠部分
		progressPaint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		
		
		textPaint = new Paint();
		textPaint.setTextSize(30);
		textPaint.setColor(Color.WHITE);
		textPaint.setAntiAlias(true);
		textPaint.setFakeBoldText(true);//文字加粗
	
		bitmap = Bitmap.createBitmap(mWidth, mHeight, Config.ARGB_8888);
		bitmapCanvas = new Canvas(bitmap);
		
		detector = new GestureDetector(new MyGestrueDetectorListener());
		setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return detector.onTouchEvent(event);
			}
		});
		setClickable(true);
	}
	
	
	
	/**
	 * 
	 */
	public void startDoubleTapAnimation() {
		mHandle.postDelayed(mDoubleTapRunnable, 50);
	}
	
	public void startSimgleTapAnimation(){
		mHandle.postDelayed(mSimgleTapRunnable, 200);
		
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
		bitmapCanvas.drawCircle(mWidth/2, mHeight/2, mWidth/2, circlePaint);
		
		path.reset();
		float y = mHeight - (float)(curProgress*mHeight)/max;
		path.moveTo(mWidth, y);
		path.lineTo(mWidth, mHeight);
		path.lineTo(0, mHeight);
		path.lineTo(0, y);
		if (!isSimgleTap){
			float d = (1 - ((float)curProgress)/progress) * 10;
			for (int i = 0; i < 5; i++){
				path.rQuadTo(10, -d, 20, 0);
				path.rQuadTo(10, d, 20, 0);
			}
		}
		else{
			float d = (((float)count)/50) * 10;
			if (count%2 == 0){
				for (int i = 0; i < 5; i++){
					path.rQuadTo(20, -d, 40, 0);
					path.rQuadTo(20, d, 40, 0);
				}	
			}
			else{
				for (int i = 0; i < 5; i++){
					path.rQuadTo(20, d, 40, 0);
					path.rQuadTo(20, -d, 40, 0);
				}
			}
		}
		path.close();
		bitmapCanvas.drawPath(path, progressPaint);
		
		String text = "" + (int)curProgress*100/max + "%";
		float textWidth = textPaint.measureText(text);
		float x = mWidth/2 - textWidth/2;
		FontMetrics fontMetrics = textPaint.getFontMetrics();//获取文字规格
		float baseLine = mHeight/2 - (fontMetrics.descent+fontMetrics.ascent)/2; 
		bitmapCanvas.drawText(text, x, baseLine, textPaint);
		
		canvas.drawBitmap(bitmap, 0, 0, null);
	}
	
	
	/**
	 * 事件监听器
	 * @author Administrator 2016-10-22上午12:08:35
	 * TODO：
	 */
	class MyGestrueDetectorListener extends SimpleOnGestureListener{
		
		@Override
		public boolean onDoubleTap(MotionEvent e) {
			Toast.makeText(getContext(), "双击了", Toast.LENGTH_SHORT).show();
			startDoubleTapAnimation();
			isSimgleTap = false;
			return super.onDoubleTap(e);
		}
		
		@Override
		public boolean onSingleTapConfirmed(MotionEvent e) {
			Toast.makeText(getContext(), "单击了", Toast.LENGTH_SHORT).show();
			startSimgleTapAnimation();
			curProgress = progress;
			count = 50;
			isSimgleTap = true;
			return super.onSingleTapConfirmed(e);
		}
	}
	
	private DoubleTapRunnable mDoubleTapRunnable = new DoubleTapRunnable();
	class DoubleTapRunnable implements Runnable{

		@Override
		public void run() {
			curProgress++;
			if (curProgress <= progress){
				invalidate();
				mHandle.postDelayed(mDoubleTapRunnable, 50);
			}
			else{
				mHandle.removeCallbacks(mDoubleTapRunnable);
				curProgress = 0;
			}
		}
		
	}
	
	private SimgleTapRunnable mSimgleTapRunnable = new SimgleTapRunnable();
	class SimgleTapRunnable implements Runnable{

	

		@Override
		public void run() {
			count--;
			if (count >= 0){
				invalidate();
				mHandle.postDelayed(mSimgleTapRunnable, 200);
			}
			else{
				count = 50;
				mHandle.removeCallbacks(mSimgleTapRunnable);
			}
		}
		
	}

}

