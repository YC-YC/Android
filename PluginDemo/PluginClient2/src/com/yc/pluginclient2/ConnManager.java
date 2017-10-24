/**
 * 
 */
package com.yc.pluginclient2;

import java.util.HashSet;
import java.util.Set;

import android.content.Context;
import android.os.SystemClock;
import android.util.Log;

import com.yc.pluginlib.PluginCallback;
import com.yc.pluginlib.PluginInterface;

/**
 * @author YC2
 * @time 2017-10-21 下午6:09:12
 * TODO:
 */
public class ConnManager implements PluginInterface{

	
	private static final String TAG = "TestPlugin";
	private Set<PluginInterface> mConnInterfaces = new HashSet<PluginInterface>();
	private Set<PluginCallback> mCallbacks = new HashSet<PluginCallback>();
	
	public void addConnInterface(PluginInterface conn){
		mConnInterfaces.add(conn);
	}
	
	public void removeInterface(PluginInterface conn){
		mConnInterfaces.remove(conn);
	}

	@Override
	public void PluginInit(Context context) {
		Log.i(TAG, "Client2 PulgInInit");
		for (PluginInterface conn: mConnInterfaces){
			conn.PluginInit(context);
		}
	}

	@Override
	public int PluginCtrlForInt(Integer cmd, Integer value) {
		Log.i(TAG, "Client2 PulgInCtrlForInt");
		for (PluginInterface conn: mConnInterfaces){
			int result = 0;
			if ((result = conn.PluginCtrlForInt(cmd, value)) > 0){
				return result;
			};
		}
		return 0;
	}

	@Override
	public String PluginCtrlForString(Integer cmd, String value) {
		Log.i(TAG, "Client2 PulgInInit");
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				SystemClock.sleep(1000);
				notifyCallback("from client 2");
			}
		}).start();
		for (PluginInterface conn: mConnInterfaces){
			String result = null;
			if ((result = conn.PluginCtrlForString(cmd, value)) != null){
				return result;
			};
		}
		return null;
	}

	@Override
	public Object PluginCtrlForObject(Integer cmd, Object value) {
		Log.i(TAG, "Client2 PulgInInit");
		for (PluginInterface conn: mConnInterfaces){
			Object result = null;
			if ((result = conn.PluginCtrlForObject(cmd, value)) != null){
				return result;
			};
		}
		return null;
	}

	@Override
	public boolean setPluginCallback(Integer cmd, PluginCallback callback) {
		if (cmd == 104){
			if (!mCallbacks.contains(callback)){
				mCallbacks.add(callback);
			}
			return true;
		}
		return false;
	}
	
	private void notifyCallback(String str){
		for (PluginCallback callback: mCallbacks){
			callback.onCallback(str);
		}
	}
	
	


}
