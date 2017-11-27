/**
 * 
 */
package com.yc.networkdemo.activity.socket;

/**
 * @author YC2
 * @time 2017-11-24 上午11:31:18
 * TODO:
 */
public interface TBoxInterface {

	/** 检测Wan */
	public void checkWan();

	/** 检测移动数据 */
	public void checkMobileData();

	/** 设置移动数据开关 */
	public void setMobileData(boolean bOpen);

	/** 检测wifi状态 */
	public void checkWifiState();

	/** 设置wifi开关 */
	public void setWifiState(boolean bOpen);

	/** 检测wifi的SSID */
	public void checkWifiSSID();

	/** 设置wifi的SSID */
	public void setWifiSSID(String ssid);

	/** 检测wifi密码 */
	public void checkWifiPsw();

	/** 设置wifi密码 */
	public void setWifiPsw(boolean hasPsw, String psw);

	/** 检测wifi连接个数 */
	public void checkConnectionCount();

	/** 通知TBox自检 */
	public void TBoxSelfCheck();

	/** 检测TUID/ICCID/CertID */
	public void checkID();
}
