/**
 * 
 */
package com.yc.ashmemservice;

import java.io.IOException;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.MemoryFile;
import android.os.ParcelFileDescriptor;
import android.os.ServiceManager;
import android.util.Log;

/**
 * @author YC2
 * @time 2017-11-6 上午11:23:37
 * TODO:
 */
public class AshmemService extends Service {

	private static final String TAG = "TestAshmem";
	
	@Override
	public IBinder onBind(Intent intent) {
		Log.i(TAG, "onBind");
		return null;
	}
	
	@Override
	public void onCreate() {
		Log.i(TAG, "onCreate");
		super.onCreate();
		ServiceManager.addService("AshmemService", new MemoryService());
		/*try {
			MemoryFile file = new MemoryFile("TestAshmem", 4);
			byte[] buffer = {0x01, 0x02, 0x03, 0x04};
			file.writeBytes(buffer , 0, 0, 4);
			file.getFileDescriptor();
			ParcelFileDescriptor.dup(file.getFileDescriptor());
			byte[] readbuf = new byte[4];
			file.readBytes(readbuf, 0, 0, 4);
			Log.i(TAG, String.format("Service read data:%x, %x, %x, %x", readbuf[0], readbuf[1], readbuf[2], readbuf[3]));
		} catch (IOException e) {
			e.printStackTrace();
		}*/
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.i(TAG, "onStartCommand");
		return super.onStartCommand(intent, flags, startId);
	}
	
	@Override
	public void onDestroy() {
		Log.i(TAG, "onDestroy");
		super.onDestroy();
	}

}
