/**
 * 
 */
package com.example.testbackview;

import org.xutils.x;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.testbackview.activity.BackActivity;
import com.example.testbackview.activity.ClockActivity;
import com.example.testbackview.activity.EnvolopActivity;
import com.example.testbackview.activity.GranzortActivity;
import com.example.testbackview.activity.LevelActivity;

/**
 * @author YC2
 * @time 2017-11-27 下午3:42:24 TODO:
 */
@ContentView(R.layout.activity_main)
public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		x.view().inject(this);
	}

	@Event(type = View.OnClickListener.class, value = { R.id.bt_clockview,
			R.id.bt_granzort , R.id.bt_backview,
			R.id.bt_envolop, R.id.bt_level})
	private void doClick(View view) {
		switch (view.getId()) {
		case R.id.bt_clockview:
			startActivity(new Intent(this, ClockActivity.class));
			break;
		case R.id.bt_granzort:
			startActivity(new Intent(this, GranzortActivity.class));
			break;
		case R.id.bt_backview:
			startActivity(new Intent(this, BackActivity.class));
			break;
		case R.id.bt_envolop:
			startActivity(new Intent(this, EnvolopActivity.class));
			break;
		case R.id.bt_level:
			startActivity(new Intent(this, LevelActivity.class));
			break;
		default:
			break;
		}
	}

}
