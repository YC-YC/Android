package com.example.testbackview;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class MainActivity extends Activity {

	private RadarView mBackCarView;
	private SeekBar mSeekBar1;
	private SeekBar mSeekBar2;
	private SeekBar mSeekBar3;
	private SeekBar mSeekBar4;
	private SeekBar mSeekBar5;
	private SeekBar mSeekBar6;
	private SeekBar mSeekBar7;
	private SeekBar mSeekBar8;

	private OnSeekBarChangeListener mOnSeekBarChangeListener = new MyOnSeekBarChangeListener();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_radar);
		mBackCarView = (RadarView) findViewById(R.id.radar);
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
	
	}
	
	public void doClick(View view){
		switch (view.getId()) {
		case R.id.button1:
			mSeekBar1.setProgress(50);
			mSeekBar2.setProgress(50);
			mSeekBar3.setProgress(50);
			mSeekBar4.setProgress(50);
			mSeekBar5.setProgress(50);
			mSeekBar6.setProgress(50);
			mSeekBar7.setProgress(50);
			mSeekBar8.setProgress(50);
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
				mBackCarView.changePoints(0, progress - 50); 
				break;
			case R.id.seekBar2:
				mBackCarView.changePoints(1, progress - 50); 
				break;
			case R.id.seekBar3:
				mBackCarView.changePoints(2, progress - 50); 
				break;
			case R.id.seekBar4:
				mBackCarView.changePoints(3, progress - 50); 
				break;
			case R.id.seekBar5:
				mBackCarView.changePoints(4, progress - 50); 
				break;
			case R.id.seekBar6:
				mBackCarView.changePoints(5, progress - 50); 
				break;
			case R.id.seekBar7:
				mBackCarView.changePoints(6, progress - 50); 
				break;
			case R.id.seekBar8:
				mBackCarView.changePoints(7, progress - 50); 
				break;
				
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
