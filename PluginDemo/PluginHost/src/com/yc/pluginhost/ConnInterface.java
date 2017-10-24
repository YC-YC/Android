/**
 * 
 */
package com.yc.pluginhost;

import android.content.Context;

import com.yc.pluginlib.PluginCallback;

/**
 * @author YC2
 * @time 2017-10-21 下午5:18:25
 * TODO:
 * 定义的插件通信接口
 */
public interface ConnInterface {
	
	public void JavaInInit(Context context);
	
	public int JavaCtrlForInt(Integer cmd, Integer value);

	public String JavaCtrlForString(Integer cmd, String value);

	public Object JavaCtrlForObject(Integer cmd, Object value);
	
	public boolean setCallback(Integer cmd, PluginCallback callback);
}
