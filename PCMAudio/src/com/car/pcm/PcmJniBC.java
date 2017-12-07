/**
 * 
 */
package com.car.pcm;

/**
 * TBox音频JNI接口
 * 
 * @author YC2
 * @time 2017-11-10 上午10:49:04 TODO:
 */
public class PcmJniBC {
	/**
	 * 调用类必需实现如下方法
	 * public synchronized static void jniWriteAudioData(byte[]data, int length)
	 * @param className 调用该方法的类名（.用/替换）
	 */
	public static native void initPcmIn(String callClassName);
	public static native int openPcmIn(int card, int device, int channels, int rate, int bits);
	public static native int readPcmIn();
	public static native void stopPcmIn();
	public static native void deInitPcmIn();
	
	public static native int openPcmOut(int card, int device, int channels, int rate, int bits);
	public static native int writeDataToPcmOut(byte[] data, int count);
	public static native void closePcmOut();
	
	static {
		System.loadLibrary("PcmAudio");
	}
}
