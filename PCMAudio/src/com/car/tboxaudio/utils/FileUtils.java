package com.car.tboxaudio.utils;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import android.util.Log;

public class FileUtils {
  
    private byte[] getBytes(String filePath){  
        byte[] buffer = null;  
        try {  
            File file = new File(filePath);  
            FileInputStream fis = new FileInputStream(file);  
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);  
            byte[] b = new byte[1000];  
            int n;  
            while ((n = fis.read(b)) != -1) {  
                bos.write(b, 0, n);  
            }  
            fis.close();  
            bos.close();  
            buffer = bos.toByteArray();  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return buffer;  
    }
    
    //追加方式写文件 
    public static void saveByteToFile(byte[] buf, int buflen, String filePath) {  
        byte[] tmp = new byte[buflen];
        System.arraycopy(buf, 0, tmp, 0, buflen);
        FileOutputStream outputStream = null;
        Log.i("carlife_FileUtils", "save file len = " +  buflen);
        try {
        	outputStream = new FileOutputStream(new File(filePath), true);
        	outputStream.write(tmp);
        	outputStream.flush();
		} catch (Exception e) {
			// TODO: handle exception
		}
        
        if(outputStream != null) {
        	try {
        		outputStream.close();
			} catch (Exception e) {
				// TODO: handle exception
			}
        }
    }
    
    public static void writeFileAppend(String fileName, String content) {   
        FileWriter writer = null;  
        try {     
            // 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件     
            writer = new FileWriter(fileName, true);     
            writer.write(content);       
        } catch (IOException e) {     
            e.printStackTrace();     
        } finally {     
            try {     
                if(writer != null){  
                    writer.close();     
                }  
            } catch (IOException e) {     
                e.printStackTrace();     
            }     
        }   
    }  
}
