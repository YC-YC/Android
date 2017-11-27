/**
 * 
 */
package com.example.testbackview.widget;

import com.example.testbackview.R;
import com.example.testbackview.R.styleable;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

/**
 * 显示等级的View
 * @author YC
 * @time 2017-8-28 下午8:51:28
 * TODO:
 */
public class LevelView extends ImageView {
	/**不旋转*/
	private static final int TYPE_NONE = 0;
	/**X轴对称*/
	private static final int TYPE_X = 1;
	/**Y轴对称*/
	private static final int TYPE_Y = 2;
	/**对角对称*/
	private static final int TYPE_XY = 3;
	
	
	private int mLevel = TYPE_NONE;
	private int[] mImages;
	private Matrix matrix;
	/**旋转样式*/
	private int mRotateType;

	public LevelView(Context context) {
		this(context, null);
	}
	
	public LevelView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	
	public LevelView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		getStyleAttributes(attrs);
		init();
	}
	
	private void getStyleAttributes(AttributeSet attrs) {
		final TypedArray ta = getContext().obtainStyledAttributes(attrs,
				R.styleable.LevelView);

		mRotateType = (int) ta.getInt(R.styleable.LevelView_rotate_type,TYPE_NONE);
		Log("mRotateType = " + mRotateType);
		ta.recycle();
	}
	
	private void init() {
		matrix = new Matrix();
		switch (mRotateType) {
		case TYPE_NONE:
			matrix.preScale(1, 1);
			break;
		case TYPE_X:
			matrix.preScale(1, -1);
			break;
		case TYPE_Y:
			matrix.preScale(-1, 1);
			break;
		case TYPE_XY:
			matrix.preScale(-1, -1);
			break;
		}

	}
	
	public void setImageResIds(int[] imageId){
		this.mImages = imageId;
	}
	
	public void setLevel(int level){
		this.mLevel = level;
		
		refreshView();
	}
	
	private void refreshView() {
		if (mImages != null){
			setImageBitmap(getBitmap());
		}
		invalidate();
	}
	
	private Bitmap getBitmap() {
		if (mLevel >= 0 && mLevel < mImages.length){
			Bitmap bitmap = BitmapFactory.decodeResource(getResources(), mImages[mLevel]);
			if (bitmap != null){
			Bitmap reflectionImage = Bitmap.createBitmap(bitmap, 0,
				                 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
			return reflectionImage;
			}
			else{
				Log("null bitmap level =" + mLevel);
			}
		}
		return null;
	}
	
	private void Log(String msg){
		Log.i("LevelView", msg);
	}
	
}
