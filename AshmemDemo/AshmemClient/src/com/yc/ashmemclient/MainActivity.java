package com.yc.ashmemclient;

import java.io.IOException;

import android.app.Activity;
import android.os.Bundle;
import android.os.MemoryFile;
import android.util.Log;
import android.view.View;

import com.yc.ashmem.MemoryFileHelper;
import com.yc.ashmemlib.AshmemManager;

public class MainActivity extends Activity {

	private static final String TAG = "TestAshmem";
	private MemoryFile file;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void doClick(View view) {
		switch (view.getId()) {
		case R.id.create:
			file = MemoryFileHelper.openMemoryFile(AshmemManager.getInstances()
					.getFileDescriptor(), 4, MemoryFileHelper.PROT_READ
					| MemoryFileHelper.PROT_WRITE);
			Log.i(TAG, "openMemoryFile ok");

			break;
		case R.id.read:
			if (file != null) {
				byte[] buffer = new byte[4];

				try {
					int readBytes = file.readBytes(buffer, 0, 0, 4);
					Log.i(TAG, "read bytes len = " + readBytes);
				} catch (IOException e) {
					e.printStackTrace();
				}
				Log.i(TAG, String.format("read data:%x, %x, %x, %x", buffer[0],
						buffer[1], buffer[2], buffer[3]));
			}
			break;
		case R.id.write:
			if (file != null) {
				byte[] buffer = new byte[]{0x4, 0x5, 0x6, 0x7};

				try {
					file.writeBytes(buffer,0, 0, 4);
					Log.i(TAG, "write ok");
				} catch (IOException e) {
					e.printStackTrace();
				}
//				Log.i(TAG, String.format("read data:%x, %x, %x, %x", buffer[0],
//						buffer[1], buffer[2], buffer[3]));
			}
			break;

		default:
			break;
		}
	}
	

}
