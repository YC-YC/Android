/**
 * 
 */
package com.yc.networkdemo.activity.socket;

import java.io.UnsupportedEncodingException;

import android.text.TextUtils;
import android.util.Log;

import com.yc.networkdemo.activity.socket.SocketHelper.onSocketPackageListener;
import com.yc.networkdemo.activity.socket.ThreadPoolUtil.Type;

/**
 * 外置TBox
 * 
 * @author YC2
 * @time 2017-11-24 上午10:47:38 TODO:
 */
public class OuterTBoxSocket implements TBoxInterface, onSocketPackageListener {

	private static final String HOST = "10.0.0.1";
	private static final int PORT = 8000;
	private static final String TAG = "OuterTBox";
	private SocketHelper mSocketHelper = null;
	private int mCount = 0;
	private ThreadPoolUtil mThreadPoolUtil;

	public OuterTBoxSocket() {
		mThreadPoolUtil = new ThreadPoolUtil(Type.SingleThread, 0);
		mSocketHelper = new SocketHelper();
		mSocketHelper.registerOnSocketPackageListener(this);
		startSocket(true);
	}

	@Override
	public void onPackage(TBoxPackage data) {
		// TODO 接收到数据
		if (data != null) {
			Log("onPackage cmd = " + String.format("0x%02x", data.getBodyCmd()));
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
			case C.TBox2HuCmd.TBOX_SELF_CHECK:{
				byte[] bodyData = data.getBodyData();
				int tboxSelfCheckResult = bodyData[0];
				Log("tboxSelfCheckResult = " + tboxSelfCheckResult);
			}
				break;
			case C.TBox2HuCmd.CHECK_ID:{
				
				byte[] bodyData = data.getBodyData();
				int tuidState = bodyData[0];
				String tuid = ByteUtil.toASCIIString(bodyData, 1, 32);
				Log("tuidState = " + tuidState + ", tuid = " + tuid);
				int iccidState = bodyData[33];
				String iccid = ByteUtil.toASCIIString(bodyData, 34, 20);
				Log("iccidState = " + iccidState + ", iccid = " + iccid);
				int certidState = bodyData[54];
				String certid = ByteUtil.toASCIIString(bodyData,55, 21);
				Log("certidState = " + certidState + ", certid = " + certid);
			}
				break;
			default:
				break;
			}
		}
	}

	@Override
	public void checkWan() {
		mThreadPoolUtil.execute(new Runnable() {
			@Override
			public void run() {
				checkOpenSocket();

				if (mSocketHelper.isSocketOpen()) {
					byte[] data = new TBoxPackage(getCount(), true,
							C.MsgType.NORMAL, C.Hu2TBoxCmd.CHECK_WLAN, null)
							.toSocketData();

					writeDataRetry(data, 0, data.length);
				}
			}
		});
	}

	@Override
	public void checkMobileData() {
		mThreadPoolUtil.execute(new Runnable() {

			@Override
			public void run() {

				checkOpenSocket();

				if (mSocketHelper.isSocketOpen()) {
					byte[] data = new TBoxPackage(getCount(), true,
							C.MsgType.NORMAL, C.Hu2TBoxCmd.CHECK_MOBILE_DATA,
							null).toSocketData();
					writeDataRetry(data, 0, data.length);
				}
			}
		});
	}

	@Override
	public void setMobileData(final boolean bOpen) {
		mThreadPoolUtil.execute(new Runnable() {

			@Override
			public void run() {
				checkOpenSocket();

				if (mSocketHelper.isSocketOpen()) {
					byte[] bodyData = { 0x00 };
					if (bOpen) {
						bodyData[0] = 0x01;
					}
					byte[] data = new TBoxPackage(getCount(), true,
							C.MsgType.NORMAL, C.Hu2TBoxCmd.SET_MOBILE_DATA,
							bodyData).toSocketData();
					writeDataRetry(data, 0, data.length);
				}
			}
		});
	}

	@Override
	public void checkWifiState() {
		mThreadPoolUtil.execute(new Runnable() {
			@Override
			public void run() {

				checkOpenSocket();

				if (mSocketHelper.isSocketOpen()) {
					byte[] data = new TBoxPackage(getCount(), true,
							C.MsgType.NORMAL, C.Hu2TBoxCmd.CHECK_WIFI_STATE,
							null).toSocketData();
					writeDataRetry(data, 0, data.length);
				}
			}
		});
	}

	@Override
	public void setWifiState(final boolean bOpen) {
		mThreadPoolUtil.execute(new Runnable() {
			@Override
			public void run() {
				checkOpenSocket();

				if (mSocketHelper.isSocketOpen()) {
					byte[] bodyData = { 0x00 };
					if (bOpen) {
						bodyData[0] = 0x01;
					}
					byte[] data = new TBoxPackage(getCount(), true,
							C.MsgType.NORMAL, C.Hu2TBoxCmd.SET_WIFI_STATE,
							bodyData).toSocketData();
					writeDataRetry(data, 0, data.length);
				}
			}
		});
	}

	@Override
	public void checkWifiSSID() {
		mThreadPoolUtil.execute(new Runnable() {
			@Override
			public void run() {
				checkOpenSocket();

				if (mSocketHelper.isSocketOpen()) {
					byte[] data = new TBoxPackage(getCount(), true,
							C.MsgType.NORMAL, C.Hu2TBoxCmd.CHECK_WIFI_SSID,
							null).toSocketData();
					writeDataRetry(data, 0, data.length);
				}
			}
		});
	}

	@Override
	public void setWifiSSID(final String ssid) {
		mThreadPoolUtil.execute(new Runnable() {
			@Override
			public void run() {
				checkOpenSocket();
				if (mSocketHelper.isSocketOpen()) {
					if (!TextUtils.isEmpty(ssid)) {
						byte[] bytes = null;
						try {
							bytes = ssid.getBytes("UTF-8");
						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
						}
						if (bytes != null) {
							int len = bytes.length;
							if (len > 32) {
								len = 32;
							}
							byte[] bodyData = new byte[32];
							System.arraycopy(bytes, 0, bodyData, 0, len);
							byte[] data = new TBoxPackage(getCount(), true,
									C.MsgType.NORMAL,
									C.Hu2TBoxCmd.SET_MOBILE_DATA, bodyData)
									.toSocketData();
							writeDataRetry(data, 0, data.length);
						}
					}
				}
			}
		});
	}

	@Override
	public void checkWifiPsw() {
		mThreadPoolUtil.execute(new Runnable() {
			@Override
			public void run() {
				checkOpenSocket();

				if (mSocketHelper.isSocketOpen()) {
					byte[] data = new TBoxPackage(getCount(), true,
							C.MsgType.NORMAL, C.Hu2TBoxCmd.CHECK_WIFI_PSW, null)
							.toSocketData();
					writeDataRetry(data, 0, data.length);
				}
			}
		});
	}

	@Override
	public void setWifiPsw(final boolean hasPsw, final String psw) {
		mThreadPoolUtil.execute(new Runnable() {
			@Override
			public void run() {
				checkOpenSocket();
				if (mSocketHelper.isSocketOpen()) {
					byte[] bodyData = new byte[65];
					if (hasPsw) {
						bodyData[0] = 0x1;
						if (!TextUtils.isEmpty(psw)) {
							byte[] bytes = null;
							try {
								bytes = psw.getBytes("UTF-8");
							} catch (UnsupportedEncodingException e) {
								e.printStackTrace();
							}
							if (bytes != null) {
								int len = bytes.length;
								if (len > 64) {
									len = 64;
								}
								System.arraycopy(bytes, 0, bodyData, 1, len);
							}
						}
					} else {

					}
					byte[] data = new TBoxPackage(getCount(), true,
							C.MsgType.NORMAL, C.Hu2TBoxCmd.SET_MOBILE_DATA,
							bodyData).toSocketData();
					writeDataRetry(data, 0, data.length);
				}
			}
		});
	}

	@Override
	public void checkConnectionCount() {
		mThreadPoolUtil.execute(new Runnable() {
			@Override
			public void run() {
				checkOpenSocket();

				if (mSocketHelper.isSocketOpen()) {
					byte[] data = new TBoxPackage(getCount(), true,
							C.MsgType.NORMAL,
							C.Hu2TBoxCmd.CHECK_CONNECTION_COUNT, null)
							.toSocketData();
					writeDataRetry(data, 0, data.length);
				}
			}
		});
	}

	@Override
	public void TBoxSelfCheck() {
		mThreadPoolUtil.execute(new Runnable() {
			@Override
			public void run() {
				checkOpenSocket();

				if (mSocketHelper.isSocketOpen()) {
					byte[] data = new TBoxPackage(getCount(), true,
							C.MsgType.NORMAL, C.Hu2TBoxCmd.TBOX_SELF_CHECK,
							null).toSocketData();
					writeDataRetry(data, 0, data.length);
				}
			}
		});
	}

	@Override
	public void checkID() {
		mThreadPoolUtil.execute(new Runnable() {
			@Override
			public void run() {
				checkOpenSocket();

				if (mSocketHelper.isSocketOpen()) {
					byte[] data = new TBoxPackage(getCount(), true,
							C.MsgType.NORMAL, C.Hu2TBoxCmd.CHECK_ID, null)
							.toSocketData();
					writeDataRetry(data, 0, data.length);
				}
			}
		});
	}

	/**
	 * 写数据到socket(带重试机制)
	 * 
	 * @param data
	 * @param offset
	 * @param count
	 */
	private void writeDataRetry(byte[] data, int offset, int count) {
		if (!mSocketHelper.writeData(data, 0, data.length)) {
			startSocket(false);
			mSocketHelper.writeData(data, 0, data.length);
		}
	}

	/** 检测打开Socket */
	private void checkOpenSocket() {
		if (!mSocketHelper.isSocketOpen()) {
			startSocket(false);
		}
	}

	/** 启动Socket */
	private void startSocket(boolean bThread) {
		if (bThread) {
			mThreadPoolUtil.execute(new Runnable() {
				@Override
				public void run() {
					mSocketHelper.openSocket(HOST, PORT);
					mSocketHelper.startReadSocket();
				}
			});
		} else {
			mSocketHelper.openSocket(HOST, PORT);
			mSocketHelper.startReadSocket();
		}
	}

	/** 关闭Socket */
	public void closeSocket() {
		mSocketHelper.closeSocket();
	}

	private int getCount() {
		mCount++;
		if (mCount >= 255) {
			mCount = 0;
		}
		return mCount;
	}
	
	protected void Log(String msg){
		Log.i(TAG, msg);
	}
	
	protected void DebugLog(String msg){
		Log.i(TAG, msg);
	}
}
