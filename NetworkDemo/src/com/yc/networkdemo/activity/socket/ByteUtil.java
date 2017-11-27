/**
 * 
 */
package com.yc.networkdemo.activity.socket;

/**
 * @author YC2
 * @time 2017-11-23 下午4:55:13
 * TODO:
 */
public class ByteUtil {

	/**byte转int*/
	public static int byte2int(byte data){
		return (data&0xFF);
	}
	
	/**char数组转成byte*/
	public static byte[] getBytes(char[] chars) {
		byte[] bytes = new byte[chars.length];
		for (int i = 0; i < chars.length; i++){
			bytes[i] = (byte) chars[i];
		}
		return bytes;
	}
	
	/**char数组转成byte*/
	public static byte[] getBytes(char[] chars, int len) {
		byte[] bytes = new byte[len];
		for (int i = 0; i < len; i++){
			bytes[i] = (byte) chars[i];
		}
		return bytes;
	}
	
}
