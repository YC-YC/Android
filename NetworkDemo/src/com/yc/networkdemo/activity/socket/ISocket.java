/**
 * 
 */
package com.yc.networkdemo.activity.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Arrays;

import android.os.SystemClock;
import android.util.Log;


/**
 * @author YC2
 * @time 2017-11-23 上午10:24:43
 * TODO:
 */
public abstract class ISocket {
	
	private final String TAG = getClass().getSimpleName();
	
	private Socket mSocket;
//	private BufferedReader mReader;
	private OutputStream mWriter;
	private InputStream mReader;
	
	
	/**
	 * 打开Socket
	 * @param host
	 * @param port
	 */
	public synchronized void openSocket(String host, int port){
		Log("openSocket host:" + host + ", prot:" + port);
		mSocket = new Socket();
		try {
			mSocket.connect(new InetSocketAddress(host, port));
//			mSocket.sendUrgentData(value)
			mReader = mSocket.getInputStream();
//			mReader = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
			mWriter = mSocket.getOutputStream();
		} catch (IOException e) {
			Log(e.toString());
			closeSocket();
		}
		
	}

	/**
	 * 关闭Socket
	 */
	public synchronized void closeSocket() {
		Log("closeSocket");
		if (mWriter != null){
			try {
				mWriter.close();
				mWriter = null;
			} catch (IOException e1) {
				Log(e1.toString());
			}
		}
		if (mReader != null){
			try {
				mReader.close();
				mReader = null;
			} catch (IOException e1) {
				Log(e1.toString());
			}
		}
		if (mSocket != null){
			try {
				mSocket.close();
				mSocket = null;
			} catch (IOException e1) {
				Log(e1.toString());
			}
		}
	}
	
	/**
	 * 开始读Socket，前提是判断Socket已经连接
	 */
	public synchronized void startReadSocket(){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				Log("start read thread");
				byte[] buffer = new byte[128];
				while(mReader != null){
					try {
						int len = 0;
						while((len = mReader.read(buffer)) > 0){
							DebugLog("socket read len = " + len);
							DebugLog("read Data = " + ByteUtil.toString(buffer, len));
							onReadData(buffer, len);
						}
					} catch (IOException e) {
						Log(e.toString());
						closeSocket();
					}
					SystemClock.sleep(10);
				}
				Log("exit read thread");
			}
		}).start();
	}

	public synchronized boolean isSocketOpen(){
		return mSocket != null && mSocket.isConnected();
	}
	
	public synchronized boolean writeData(byte[] buffer, int offset, int count) {
		if (isSocketOpen() && mWriter != null){
			try {
				DebugLog("writeData = " + ByteUtil.toString(buffer));
				mWriter.write(buffer, offset, count);
				mWriter.flush();
				return true;
			} catch (IOException e) {
				Log(e.toString());
				closeSocket();
			}
		}
		return false;
	}
	
	abstract protected void onReadData(byte[] data, int len);
	
	protected void Log(String msg){
		Log.i(TAG, msg);
	}
	
	protected void DebugLog(String msg){
		Log.i(TAG, msg);
	}

}
