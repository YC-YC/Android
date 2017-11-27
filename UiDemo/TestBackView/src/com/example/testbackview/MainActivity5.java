package com.example.testbackview;

import android.app.Activity;
import android.graphics.drawable.LevelListDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class MainActivity5 extends Activity {

	private EnvelopeView mLeftMid, mLeftCorner, mRightMid, mRightCorner;
	private SeekBar mSeekBar1;
	private SeekBar mSeekBar2;
	private SeekBar mSeekBar3;
	private SeekBar mSeekBar4;
	private SeekBar mSeekBar5;
	private SeekBar mSeekBar6;
	private SeekBar mSeekBar7;
	private SeekBar mSeekBar8;
	private SeekBar mSeekBar9;
	private SeekBar mSeekBar10;
	private SeekBar mSeekBar11;
	private SeekBar mSeekBar12;

	private OnSeekBarChangeListener mOnSeekBarChangeListener = new MyOnSeekBarChangeListener();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_envolop2);
		mLeftMid = (EnvelopeView) findViewById(R.id.envelope_left_mid);
		mLeftCorner = (EnvelopeView) findViewById(R.id.envelope_left_corner);
		mRightMid = (EnvelopeView) findViewById(R.id.envelope_right_mid);
		mRightCorner = (EnvelopeView) findViewById(R.id.envelope_right_corner);
		mSeekBar1 = (SeekBar) findViewById(R.id.seekBar1);
		mSeekBar1.setOnSeekBarChangeListener(mOnSeekBarChangeListener);
		mSeekBar2 = (SeekBar) findViewById(R.id.seekBar2);
		mSeekBar2.setOnSeekBarChangeListener(mOnSeekBarChangeListener);
		mSeekBar3 = (SeekBar) findViewById(R.id.seekBar3);
		mSeekBar3.setOnSeekBarChangeListener(mOnSeekBarChangeListener);
		mSeekBar4 = (SeekBar) findViewById(R.id.seekBar4);
		mSeekBar4.setOnSeekBarChangeListener(mOnSeekBarChangeListener);
		mSeekBar5 = (SeekBar) findViewById(R.id.seekBar5);
		mSeekBar5.setOnSeekBarChangeListener(mOnSeekBarChangeListener);
		mSeekBar6 = (SeekBar) findViewById(R.id.seekBar6);
		mSeekBar6.setOnSeekBarChangeListener(mOnSeekBarChangeListener);
		mSeekBar7 = (SeekBar) findViewById(R.id.seekBar7);
		mSeekBar7.setOnSeekBarChangeListener(mOnSeekBarChangeListener);
		mSeekBar8 = (SeekBar) findViewById(R.id.seekBar8);
		mSeekBar8.setOnSeekBarChangeListener(mOnSeekBarChangeListener);
		mSeekBar9 = (SeekBar) findViewById(R.id.seekBar9);
		mSeekBar9.setOnSeekBarChangeListener(mOnSeekBarChangeListener);
		mSeekBar10 = (SeekBar) findViewById(R.id.seekBar10);
		mSeekBar10.setOnSeekBarChangeListener(mOnSeekBarChangeListener);
		mSeekBar11 = (SeekBar) findViewById(R.id.seekBar11);
		mSeekBar11.setOnSeekBarChangeListener(mOnSeekBarChangeListener);
		mSeekBar12 = (SeekBar) findViewById(R.id.seekBar12);
		mSeekBar12.setOnSeekBarChangeListener(mOnSeekBarChangeListener);
	
	}
	
	public void doClick(View view){
		switch (view.getId()) {
		case R.id.button1:
//			mSeekBar1.setProgress(50);
//			mSeekBar2.setProgress(50);
//			mSeekBar3.setProgress(50);
//			mSeekBar4.setProgress(50);
//			mSeekBar5.setProgress(50);
//			mSeekBar6.setProgress(50);
//			mSeekBar7.setProgress(50);
//			mSeekBar8.setProgress(50);
//			mSeekBar9.setProgress(50);
//			mSeekBar10.setProgress(50);
//			mSeekBar11.setProgress(50);
//			mSeekBar12.setProgress(50);
			break;
		case R.id.button2:
			moveTaskToBack(false);
			break;

		default:
			break;
		}
	}

	private class MyOnSeekBarChangeListener implements OnSeekBarChangeListener {

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			switch (seekBar.getId()) {
			case R.id.seekBar1:
//				mFrontView.changePoints(1, progress-50);
				mLeftCorner.changeRadio(progress);
				break;
			case R.id.seekBar2:
				mLeftMid.changeRadio(progress);
				break;
			case R.id.seekBar3:
				mRightMid.changeRadio(progress);
				break;
			case R.id.seekBar4:
				mRightCorner.changeRadio(progress);
				break;
//			case R.id.seekBar5:
//				mBackView.changePoints(1, progress-50);
//				break;
//			case R.id.seekBar6:
//				mBackView.changePoints(2, progress-50);
//				break;
//			case R.id.seekBar7:
//				mBackView.changePoints(3, progress-50);
//				break;
//			case R.id.seekBar8:
//				mBackView.changePoints(4, progress-50);
//				break;
//			case R.id.seekBar9:
//				mLeftView.changePoints(1, progress-50);
//				break;
//			case R.id.seekBar10:
//				mLeftView.changePoints(2, progress-50);
//				break;
//			case R.id.seekBar11:
//				mRightView.changePoints(1, progress-50);
//				break;
//			case R.id.seekBar12:
//				mRightView.changePoints(2, progress-50);
//				break;
			default:
				break;
			}

		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {

		}

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {

		}

	}

}
