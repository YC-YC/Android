package com.example.testbackview;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class MainActivity3 extends Activity {

	private CircleSeekBar mCircleSeekBar;
	private SeekBar mSeekBar1;
	private SeekBar mSeekBar2;
	private LevelView mLevelViewNormal, mLevelViewY, mLevelViewX, mLevelViewXY;
	private int levelNormal, levelRight;
	
	private int[] images = {
			R.drawable.front_full_left1,
			R.drawable.front_full_left2,
			R.drawable.front_full_left3,
			R.drawable.front_full_left4,
			R.drawable.front_full_left5,
			R.drawable.front_full_left6,
			R.drawable.front_full_left7,
			R.drawable.front_full_left8,
			R.drawable.front_full_left9,
			R.drawable.front_full_left10
	};

	private OnSeekBarChangeListener mOnSeekBarChangeListener = new MyOnSeekBarChangeListener();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity3);
		mCircleSeekBar = (CircleSeekBar) findViewById(R.id.circle_seekbar);
		mSeekBar1 = (SeekBar) findViewById(R.id.seekBar1);
		mSeekBar1.setOnSeekBarChangeListener(mOnSeekBarChangeListener);
		mSeekBar2 = (SeekBar) findViewById(R.id.seekBar2);
		mSeekBar2.setOnSeekBarChangeListener(mOnSeekBarChangeListener);
		mLevelViewNormal = (LevelView) findViewById(R.id.levelview_normal);
		mLevelViewNormal.setImageResIds(images);
		mLevelViewNormal.setLevel(levelNormal);
		mLevelViewY = (LevelView) findViewById(R.id.levelview_y);
		mLevelViewY.setImageResIds(images);
		mLevelViewY.setLevel(levelRight);
		mLevelViewX = (LevelView) findViewById(R.id.levelview_x);
		mLevelViewX.setImageResIds(images);
		mLevelViewX.setLevel(levelRight);
		mLevelViewXY = (LevelView) findViewById(R.id.levelview_xy);
		mLevelViewXY.setImageResIds(images);
		mLevelViewXY.setLevel(levelRight);
	}
	
	public void doClick(View view){
		switch (view.getId()) {
		case R.id.btn_level_normal:
			levelNormal++;
			if (levelNormal > images.length-1){
				levelNormal = 0;
			}
			mLevelViewNormal.setLevel(levelNormal);
			mLevelViewX.setLevel(levelNormal);
			break;
		case R.id.btn_level_right:
			levelRight++;
			if (levelRight > images.length-1){
				levelRight = 0;
			}
			mLevelViewY.setLevel(levelRight);
			mLevelViewXY.setLevel(levelRight);
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
				break;
			case R.id.seekBar2:
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
