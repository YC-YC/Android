/**
 * 
 */
package com.yc.pluginlib;

import android.content.Context;

/**
 * @author YC2
 * @time 2017-10-21 下午5:18:25
 * TODO:
 * 定义的插件通信接口
 */
public interface PluginInterface {
	
	public void PluginInit(Context context);
	
	public int PluginCtrlForInt(Integer cmd, Integer value);

	public String PluginCtrlForString(Integer cmd, String value);

	public Object PluginCtrlForObject(Integer cmd, Object value);
	
	public boolean setPluginCallback(Integer cmd, PluginCallback callback);
}
