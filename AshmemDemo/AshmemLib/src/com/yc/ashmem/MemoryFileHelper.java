/**
 * 
 */
package com.yc.ashmem;

import java.io.FileDescriptor;
import java.lang.reflect.Method;

import android.os.MemoryFile;
import android.os.ParcelFileDescriptor;

/**
 * @author YC2
 * @time 2017-11-7 下午4:14:38
 * TODO:
 */
public class MemoryFileHelper {

	/**只读方式打开*/
	public static final int PROT_READ = 0x01;
	/**可写方式打开*/
	public static final int PROT_WRITE = 0x02;
	
	/**
	 * @see openMemoryFile
	 * @param pfd
	 * @param length
	 * @param mode
	 * @return
	 */
	public static MemoryFile openMemoryFile(ParcelFileDescriptor pfd, int length, int mode){
		if (pfd != null){
			return openMemoryFile(pfd.getFileDescriptor(), length, mode);
		}
		else{
			return null;
		}
	}
		
	
	/**
	 * 获取客户端的Memory（高版本framework没有直接打开的方法，通过反射实现）
	 * @param fd
	 * @param length
	 * @param mode PROT_READ = 0x1只读方式打开,
     *             PROT_WRITE = 0x2可写方式打开
     *             PROT_WRITE|PROT_READ可读可写方式打开
	 * @return
	 */
	public static MemoryFile openMemoryFile(FileDescriptor fd, int length, int mode){
		MemoryFile memoryFile = null;
		
		try {
			memoryFile = new MemoryFile("tem", 1);
			memoryFile.close();
			Class<?> c = MemoryFile.class;
			Method native_mmap = null;
			Method[] methods = c.getDeclaredMethods();
			for(int i = 0; methods!=null&& i < methods.length; i++){
				if (methods[i].getName().equals("native_mmap")){
					native_mmap = methods[i];
				}
			}
			ReflectUtil.setField("android.os.MemoryFile", memoryFile, "mFD", fd);
			ReflectUtil.setField("android.os.MemoryFile", memoryFile, "mLength", length);
			int addr = (int) ReflectUtil.invokeMethod(null, native_mmap, fd, length, mode);
			ReflectUtil.setField("android.os.MemoryFile", memoryFile, "mAddress", addr);
//			Log.i(TAG, "openMemoryFile ok");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return memoryFile;
	}
}
