/**
 * 
 */
package com.yc.networkdemo.activity;

import org.xutils.x;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.yc.networkdemo.R;
import com.yc.networkdemo.activity.socket.OuterTBoxSocket;

/**
 * @author YC2
 * @time 2017-12-5 上午10:54:09
 * TODO:
 */
@ContentView(R.layout.activity_tbox)
public class TBoxActivity extends Activity {
	
	private OuterTBoxSocket mOuterTBox;
	private boolean mobileState = false;
	private boolean wifiState = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//		StrictMode.setThreadPolicy(policy);
		x.view().inject(this);
		mOuterTBox = new OuterTBoxSocket();
	}
	
	@Event(type = View.OnClickListener.class,value = {R.id.bt_exit,
		R.id.bt_check_mobiledata, R.id.bt_set_mobiledata,
		R.id.bt_check_wifistate, R.id.bt_set_wifistate,
		R.id.bt_check_wifissid, R.id.bt_set_wifissid,
		R.id.bt_check_wifipsw, R.id.bt_set_wifipsw,
		R.id.bt_check_connectioncount, R.id.bt_tbox_selcheck,
		R.id.bt_check_id, R.id.bt_check_wan})
	private void testTBox(View view){
		switch (view.getId()) {
		case R.id.bt_exit:
			mOuterTBox.closeSocket();
			finish();
			break;
		case R.id.bt_check_mobiledata:
			mOuterTBox.checkMobileData();
			break;
		case R.id.bt_set_mobiledata:
			if (mobileState){
				mobileState = false;
			}
			else{
				mobileState = true;
			}
			mOuterTBox.setMobileData(mobileState);
			break;
		case R.id.bt_check_wifistate:
			mOuterTBox.checkWifiState();
			break;
		case R.id.bt_set_wifistate:
			if (wifiState){
				wifiState = false;
			}
			else{
				wifiState = true;
			}
			mOuterTBox.setWifiState(wifiState);
			break;
		case R.id.bt_check_wifissid:
			mOuterTBox.checkWifiSSID();
			break;
		case R.id.bt_set_wifissid:
			mOuterTBox.setWifiSSID("helloworld");
			break;
		case R.id.bt_check_wifipsw:
			mOuterTBox.checkWifiPsw();
			break;
		case R.id.bt_set_wifipsw:
			mOuterTBox.setWifiPsw(true, "123456789");
			break;
		case R.id.bt_check_connectioncount:
			mOuterTBox.checkConnectionCount();
			break;
		case R.id.bt_tbox_selcheck:
			mOuterTBox.TBoxSelfCheck();
			break;
		case R.id.bt_check_id:
			mOuterTBox.checkID();
			break;
		case R.id.bt_check_wan:
			mOuterTBox.checkWan();
			break;
		default:
			break;
		}
	}
}
