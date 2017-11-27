/**
 * 
 */
package com.example.testbackview.activity;

import org.xutils.x;
import org.xutils.view.annotation.ContentView;

import com.example.testbackview.R;

import android.app.Activity;
import android.os.Bundle;

/**
 * @author YC2
 * @time 2017-11-27 下午3:41:19
 * TODO:
 */
@ContentView(R.layout.activity_clock)
public class ClockActivity extends Activity {

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		x.view().inject(this);
	}
	
	
}
