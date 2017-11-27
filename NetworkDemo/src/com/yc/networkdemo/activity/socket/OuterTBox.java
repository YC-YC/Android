/**
 * 
 */
package com.yc.networkdemo.activity.socket;

import java.io.UnsupportedEncodingException;

import android.text.TextUtils;

import com.yc.networkdemo.activity.socket.SocketHelper.onSocketPackageListener;

/**外置TBox
 * @author YC2
 * @time 2017-11-24 上午10:47:38
 * TODO:
 */
public class OuterTBox implements TBoxInterface, onSocketPackageListener{

	private static final String HOST = "10.0.0.1";
	private static final int PORT = 3000;
	private SocketHelper mSocketHelper = null;
	private int mCount = 0;
	
	public OuterTBox(){
		mSocketHelper = new SocketHelper();
		mSocketHelper.registerOnSocketPackageListener(this);
		startSocket();
	}

	@Override
	public void onPackage(TBoxPackage data) {
		//TODO 接收到数据
		if (data != null){
			switch (data.getBodyCmd()) {
			case C.TBox2HuCmd.CHECK_WLAN:

				break;
			case C.TBox2HuCmd.CHECK_MOBILE_DATA:

				break;
			case C.TBox2HuCmd.SET_MOBILE_DATA:

				break;
			case C.TBox2HuCmd.CHECK_WIFI_STATE:

				break;
			case C.TBox2HuCmd.SET_WIFI_STATE:

				break;
			case C.TBox2HuCmd.CHECK_WIFI_SSID:

				break;
			case C.TBox2HuCmd.SET_WIFI_SSID:

				break;
			case C.TBox2HuCmd.CHECK_WIFI_PSW:

				break;
			case C.TBox2HuCmd.SET_WIFI_PSW:

				break;
			case C.TBox2HuCmd.CHECK_CONNECTION_COUNT:

				break;
			case C.TBox2HuCmd.TBOX_SELF_CHECK:

				break;
			case C.TBox2HuCmd.CHECK_ID:

				break;
			default:
				break;
			}
		}
	}
	
	@Override
	public void checkWan() {
		checkOpenSocket();
		
		if (mSocketHelper.isSocketOpen()) {
			byte[] data = new TBoxPackage(getCount(), true, C.MsgType.NORMAL,
					C.Hu2TBoxCmd.CHECK_WLAN, null).toSocketData();

			writeDataRetry(data, 0, data.length);
		}
	}
	


	@Override
	public void checkMobileData() {
		checkOpenSocket();
		
		if (mSocketHelper.isSocketOpen()) {
			byte[] data = new TBoxPackage(getCount(), true, C.MsgType.NORMAL,
					C.Hu2TBoxCmd.CHECK_MOBILE_DATA, null).toSocketData();
			writeDataRetry(data, 0, data.length);
		}
	}

	@Override
	public void setMobileData(boolean bOpen) {
		checkOpenSocket();
		
		if (mSocketHelper.isSocketOpen()) {
			byte[] bodyData = {0x00};
			if (bOpen){
				bodyData[0] = 0x01;
			}
			byte[] data = new TBoxPackage(getCount(), true, C.MsgType.NORMAL,
					C.Hu2TBoxCmd.SET_MOBILE_DATA, bodyData).toSocketData();
			writeDataRetry(data, 0, data.length);
		}
	}

	@Override
	public void checkWifiState() {
		checkOpenSocket();
		
		if (mSocketHelper.isSocketOpen()) {
			byte[] data = new TBoxPackage(getCount(), true, C.MsgType.NORMAL,
					C.Hu2TBoxCmd.CHECK_WIFI_STATE, null).toSocketData();
			writeDataRetry(data, 0, data.length);
		}
	}

	@Override
	public void setWifiState(boolean bOpen) {
		checkOpenSocket();
		
		if (mSocketHelper.isSocketOpen()) {
			byte[] bodyData = {0x00};
			if (bOpen){
				bodyData[0] = 0x01;
			}
			byte[] data = new TBoxPackage(getCount(), true, C.MsgType.NORMAL,
					C.Hu2TBoxCmd.SET_WIFI_STATE, bodyData).toSocketData();
			writeDataRetry(data, 0, data.length);
		}
	}

	@Override
	public void checkWifiSSID() {
		checkOpenSocket();
		
		if (mSocketHelper.isSocketOpen()) {
			byte[] data = new TBoxPackage(getCount(), true, C.MsgType.NORMAL,
					C.Hu2TBoxCmd.CHECK_WIFI_SSID, null).toSocketData();
			writeDataRetry(data, 0, data.length);
		}
	}

	@Override
	public void setWifiSSID(String ssid) {
		checkOpenSocket();
		if (mSocketHelper.isSocketOpen()) {
			if (!TextUtils.isEmpty(ssid)){
				byte[] bytes = null;
				try {
					bytes = ssid.getBytes("UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				if (bytes != null){
					int len = bytes.length;
					if (len > 32){
						len = 32;
					}
					byte[] bodyData = new byte[32];
					System.arraycopy(bytes, 0, bodyData, 0, len);
					byte[] data = new TBoxPackage(getCount(), true, C.MsgType.NORMAL,
							C.Hu2TBoxCmd.SET_MOBILE_DATA, bodyData).toSocketData();
					writeDataRetry(data, 0, data.length);
				}
			}
		}
	}

	@Override
	public void checkWifiPsw() {
		checkOpenSocket();
		
		if (mSocketHelper.isSocketOpen()) {
			byte[] data = new TBoxPackage(getCount(), true, C.MsgType.NORMAL,
					C.Hu2TBoxCmd.CHECK_WIFI_PSW, null).toSocketData();
			writeDataRetry(data, 0, data.length);
		}
	}

	@Override
	public void setWifiPsw(boolean hasPsw, String psw) {
		checkOpenSocket();
		if (mSocketHelper.isSocketOpen()) {
			byte[] bodyData = new byte[65];
			if (hasPsw){
				bodyData[0] = 0x1;
				if (!TextUtils.isEmpty(psw)){
					byte[] bytes = null;
					try {
						bytes = psw.getBytes("UTF-8");
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					if (bytes != null){
						int len = bytes.length;
						if (len > 64){
							len = 64;
						}
						System.arraycopy(bytes, 0, bodyData, 1, len);
					}
				}
			}
			else{
				
			}
			byte[] data = new TBoxPackage(getCount(), true, C.MsgType.NORMAL,
					C.Hu2TBoxCmd.SET_MOBILE_DATA, bodyData).toSocketData();
			writeDataRetry(data, 0, data.length);
		}
	}

	@Override
	public void checkConnectionCount() {
		checkOpenSocket();
		
		if (mSocketHelper.isSocketOpen()) {
			byte[] data = new TBoxPackage(getCount(), true, C.MsgType.NORMAL,
					C.Hu2TBoxCmd.CHECK_CONNECTION_COUNT, null).toSocketData();
			writeDataRetry(data, 0, data.length);
		}
	}

	@Override
	public void TBoxSelfCheck() {
		checkOpenSocket();
		
		if (mSocketHelper.isSocketOpen()) {
			byte[] data = new TBoxPackage(getCount(), true, C.MsgType.NORMAL,
					C.Hu2TBoxCmd.TBOX_SELF_CHECK, null).toSocketData();
			writeDataRetry(data, 0, data.length);
		}
	}

	@Override
	public void checkID() {
		checkOpenSocket();
		
		if (mSocketHelper.isSocketOpen()) {
			byte[] data = new TBoxPackage(getCount(), true, C.MsgType.NORMAL,
					C.Hu2TBoxCmd.CHECK_ID, null).toSocketData();
			writeDataRetry(data, 0, data.length);
		}
	}
	
	/**
	 * 写数据到socket(带重试机制)
	 * @param data
	 * @param offset
	 * @param count
	 */
	private void writeDataRetry(byte[] data, int offset, int count){
		if (!mSocketHelper.writeData(data, 0, data.length)){
			startSocket();
			mSocketHelper.writeData(data, 0, data.length);
		}
	}

	/**检测打开Socket*/
	private void checkOpenSocket() {
		if (!mSocketHelper.isSocketOpen()){
			startSocket();
		}
	}

	/**启动Socket*/
	private void startSocket() {
		mSocketHelper.openSocket(HOST, PORT);
		mSocketHelper.startReadSocket();
	}
	
	private int getCount(){
		mCount++;
		if (mCount >= 255){
			mCount = 0;
		}
		return mCount;
	}
}
