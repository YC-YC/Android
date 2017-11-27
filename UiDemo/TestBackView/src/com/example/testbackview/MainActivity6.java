package com.example.testbackview;

import android.app.Activity;
import android.graphics.drawable.LevelListDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class MainActivity6 extends Activity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main6);
	
	}
	
	public void doClick(View view){
		switch (view.getId()) {
		case R.id.button1:
			break;
		case R.id.button2:
			break;
		default:
			break;
		}
	}


}
