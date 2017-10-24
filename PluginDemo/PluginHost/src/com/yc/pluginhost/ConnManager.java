/**
 * 
 */
package com.yc.pluginhost;

import java.util.HashSet;
import java.util.Set;

import android.content.Context;

import com.yc.pluginlib.PluginCallback;
import com.yc.pluginlib.PluginLoader;

/**
 * @author YC2
 * @time 2017-10-21 下午6:09:12
 * TODO:
 */
public class ConnManager implements ConnInterface{

	private PluginLoader mPluginLoader = null;;
	
	private Set<ConnInterface> mConnInterfaces = new HashSet<ConnInterface>();
	
	public void loadPulgIn(Context context, String jarPath, String className){
		mPluginLoader = new PluginLoader(context, jarPath, className);
	}
	
	public void addConnInterface(ConnInterface conn){
		mConnInterfaces.add(conn);
	}
	
	public void removeInterface(ConnInterface conn){
		mConnInterfaces.remove(conn);
	}
	
	
	@Override
	public void JavaInInit(Context context) {
		for (ConnInterface conn: mConnInterfaces){
			conn.JavaInInit(context);
		}
		if (mPluginLoader != null){
			mPluginLoader.PluginInit(context);
		}
		
	}

	@Override
	public int JavaCtrlForInt(Integer cmd, Integer value) {
		for (ConnInterface conn: mConnInterfaces){
			int result = 0;
			if ((result = conn.JavaCtrlForInt(cmd, value)) > 0){
				return result;
			};
		}
		if (mPluginLoader != null){
			return mPluginLoader.PluginCtrlForInt(cmd, value);
		}
		return 0;
	}

	@Override
	public String JavaCtrlForString(Integer cmd, String value) {
		for (ConnInterface conn: mConnInterfaces){
			String result = null;
			if ((result = conn.JavaCtrlForString(cmd, value)) != null){
				return result;
			};
		}
		if (mPluginLoader != null){
			return mPluginLoader.PluginCtrlForString(cmd, value);
		}
		return null;
	}

	@Override
	public Object JavaCtrlForObject(Integer cmd, Object value) {
		for (ConnInterface conn: mConnInterfaces){
			Object result = null;
			if ((result = conn.JavaCtrlForObject(cmd, value)) != null){
				return result;
			};
		}
		if (mPluginLoader != null){
			return mPluginLoader.PluginCtrlForObject(cmd, value);
		}
		return null;
	}

	@Override
	public boolean setCallback(Integer cmd, PluginCallback callback) {
		for (ConnInterface conn: mConnInterfaces){
			if (conn.setCallback(cmd, callback)){
				return true;
			}
		}
		if (mPluginLoader != null){
			return mPluginLoader.setPluginCallback(cmd, callback);
		}
		return false;
	}
}
