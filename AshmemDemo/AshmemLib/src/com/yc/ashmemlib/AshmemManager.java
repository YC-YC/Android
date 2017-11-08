/**
 * 
 */
package com.yc.ashmemlib;

import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.os.ServiceManager;

/**
 * @author YC2
 * @time 2017-11-7 下午3:02:10
 * TODO:
 */
public class AshmemManager {
	
	private static IMemoryService memoryService = null;
	private static AshmemManager sInstances = null;
	public static AshmemManager getInstances(){
		if (sInstances == null){
			synchronized (AshmemManager.class) {
				if (sInstances == null){
					sInstances = new AshmemManager();
				}
			}
		}
		return sInstances;
	}
	
	public ParcelFileDescriptor getFileDescriptor(){
		ParcelFileDescriptor parcelFileDescriptor = null;
		if (memoryService != null){
			try {
				return memoryService.getFileDescriptor();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return parcelFileDescriptor;
	}
	
	private AshmemManager(){
		getMemoryService();
	}

	private IMemoryService getMemoryService(){
		
		if (memoryService != null){
			return memoryService ;
		}
		try {
			memoryService = IMemoryService.Stub.asInterface(ServiceManager.getService("AshmemService"));
		} catch (Exception e) {
		}
		
		return memoryService;
	}
}
