/**
 * 
 */
package com.yc.networkdemo.activity.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;

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
	private BufferedReader mReader;
	private OutputStream mWriter;
	
	
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
			mReader = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
			mWriter = mSocket.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
			closeSocket();
		}
		
	}

	/**
	 * 关闭Socket
	 */
	public synchronized void closeSocket() {
		if (mWriter != null){
			try {
				mWriter.close();
				mWriter = null;
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		if (mReader != null){
			try {
				mReader.close();
				mReader = null;
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		if (mSocket != null){
			try {
				mSocket.close();
				mSocket = null;
			} catch (IOException e1) {
				e1.printStackTrace();
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
				char[] buffer = new char[1024];
				while(mReader != null){
					try {
						int len = 0;
						while((len = mReader.read(buffer)) > 0){
							onReadData(buffer, len);
						}
					} catch (IOException e) {
						e.printStackTrace();
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
				mWriter.write(buffer, offset, count);
				mWriter.flush();
				return true;
			} catch (IOException e) {
				e.printStackTrace();
				closeSocket();
			}
		}
		return false;
	}
	
	abstract protected void onReadData(char[] data, int len);
	
	protected void Log(String msg){
		Log.i(TAG, msg);
	}

}
