/**
 * 
 */
package com.yc.ashmemservice;

import java.io.IOException;

import android.os.MemoryFile;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;

import com.yc.ashmemlib.IMemoryService;

/**
 * @author YC2
 * @time 2017-11-7 下午2:48:38
 * TODO:
 */
public class MemoryService extends IMemoryService.Stub {

	private MemoryFile mFile = null;
	
	public MemoryService(){
		try {
			mFile = new MemoryFile("TestAshmem", 4);
			byte[] buffer = {0x01, 0x02, 0x03, 0x04};
			mFile.writeBytes(buffer , 0, 0, 4);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public ParcelFileDescriptor getFileDescriptor() throws RemoteException {
		ParcelFileDescriptor pfd = null;
		
		try {
			pfd = ParcelFileDescriptor.dup(mFile.getFileDescriptor());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return pfd;
	}


}
