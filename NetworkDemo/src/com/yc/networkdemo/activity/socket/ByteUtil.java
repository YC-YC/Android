/**
 * 
 */
package com.yc.networkdemo.activity.socket;

import java.io.UnsupportedEncodingException;

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
	
	public static String toString(byte[] array){
		if (array == null) {
            return "null";
        }
        if (array.length == 0) {
            return "[]";
        }
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        sb.append(String.format("0x%02x", array[0]));
        for (int i = 1; i < array.length; i++) {
            sb.append(String.format(", 0x%02x", array[i]));
        }
        sb.append(']');
        return sb.toString();
	}
	
	public static String toString(byte[] array, int length){
		if (array == null) {
            return "null";
        }
        if (array.length == 0) {
            return "[]";
        }
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        sb.append(String.format("0x%02x", array[0]));
        for (int i = 1; (i < array.length) && (i < length); i++) {
            sb.append(String.format(", 0x%02x", array[i]));
        }
        sb.append(']');
        return sb.toString();
	}
	
	public static String toString(char[] array){
		if (array == null) {
            return "null";
        }
        if (array.length == 0) {
            return "[]";
        }
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        sb.append(String.format("0x%02x", (byte)array[0]));
        for (int i = 1; i < array.length; i++) {
            sb.append(String.format(", 0x%02x", (byte)array[i]));
        }
        sb.append(']');
        return sb.toString();
	}
	
	public static String toASCIIString(byte[] array, int start, int length){
		 StringBuilder sb = new StringBuilder();
		char[]tChars=new char[length];
	     
	     for(int i = 0; i < length; i++){
	    	 if (array[start + i] == 0){
	    		 sb.append(tChars, 0, i);
	    		 return sb.toString();
	    	 }
	    	 tChars[i]=(char)array[start + i];
	     }
	     sb.append(tChars);
	     return sb.toString();
	}
	
	public static byte[] toASCIIBytes(String str){
	     try {
			return str.getBytes("US-ASCII");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	     return null;
	}
}
