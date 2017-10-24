/**
 * 
 */
package com.yc.pluginlib;

import java.io.File;
import java.lang.reflect.Method;

import android.content.Context;
import android.util.Log;
import dalvik.system.DexClassLoader;

/**
 * @author YC2
 * @time 2017-10-21 下午5:21:23
 * TODO:
 */
public class PluginLoader implements PluginInterface {

	private static final String TAG = "TestPulgin";
	private String mJarPath;
	private String mClassName;
	private Object mObject;
	
	private Method mInit;
	private Method mForStringMethod;
	private Method mForIntMethod;
	private Method mForObjectMethod;
	private Method mSetCallback;
	
	
	/**
	 * @param mContext
	 * @param mJarPath
	 * @param mClassName
	 */
	public PluginLoader(Context context, String jarPath, String className) {
		super();
		this.mJarPath = jarPath;
		this.mClassName = className;
		Log.i(TAG, "Loader jarPath = " + jarPath + ", className = " + className);
		init(context);
	}

	/**
	 * 
	 */
	private void init(Context context) {
		try {
			File jarFile = new File(this.mJarPath);
			DexClassLoader dexClassLoader = new DexClassLoader(jarFile.toString(),
				context.getDir("dex", 0).getAbsolutePath(), null, context.getClassLoader()/*ClassLoader.getSystemClassLoader()*/);
			Class<?> c = dexClassLoader.loadClass(this.mClassName);
			mObject = c.newInstance();
			this.mInit = c.getDeclaredMethod("PluginInit",
					Context.class);
			this.mForIntMethod = c.getDeclaredMethod("PluginCtrlForInt",
					Integer.class, Integer.class );
			this.mForStringMethod = c.getDeclaredMethod("PluginCtrlForString",
					Integer.class, String.class);
			this.mForObjectMethod = c.getDeclaredMethod("PluginCtrlForObject",
					Integer.class, Object.class);
			this.mSetCallback = c.getDeclaredMethod("setPluginCallback", 
					Integer.class, PluginCallback.class);
			
			this.mInit.setAccessible(true);
			this.mForIntMethod.setAccessible(true);
			this.mForStringMethod.setAccessible(true);
			this.mForObjectMethod.setAccessible(true);
			this.mSetCallback.setAccessible(true);
			
			Log.e(TAG, "声明函数成功");
		} catch (Exception e) {
			Log.e(TAG, "声明函数异常");
			e.printStackTrace();
		}
	}

	@Override
	public void PluginInit(Context context) {
		try {
			mInit.invoke(mObject, context);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public int PluginCtrlForInt(Integer cmd, Integer value) {
		try {
			mForIntMethod.invoke(mObject, cmd, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public String PluginCtrlForString(Integer cmd, String value) {
		try {
			return (String) mForStringMethod.invoke(mObject, cmd, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Object PluginCtrlForObject(Integer cmd, Object value) {
		try {
			return (Object) mForObjectMethod.invoke(mObject, cmd, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean setPluginCallback(Integer cmd, PluginCallback callback) {
		try {
			return (boolean) mSetCallback.invoke(mObject, cmd, callback);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}


}
