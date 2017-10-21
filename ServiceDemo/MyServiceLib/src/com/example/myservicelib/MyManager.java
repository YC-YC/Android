/**
 * 
 */
package com.example.myservicelib;

import java.util.List;

import android.os.Parcel;
import android.os.RemoteException;
import android.os.ServiceManager;

import com.example.myservicelib.aidl.IMyService;
import com.example.myservicelib.bean.Person;
import com.example.myservicelib.data.Constant;

/**
 * @author YC
 * @time 2017-4-20 下午3:22:49
 * TODO:
 */
public class MyManager {
	
	private static MyManager sManager;
	private IMyService mService;
	
	public static MyManager getInstance() {
		if (sManager == null) {
			synchronized (MyManager.class) {
				if (sManager == null) {
					sManager = new MyManager();
				}
			}
		}
		return sManager;
	}
	
	private MyManager(){
		mService = IMyService.Stub.asInterface(ServiceManager.getService(Constant.SERVICE_NAME));
		
	}
	
	
	public void setVal(int valInt, long valLong, char valChar, boolean valBoolean, String str){
		try {
			mService.setVal(valInt, valLong, valChar, valBoolean, str);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public int getVal(){
		try {
			return mService.getVal();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	
	/**
	 * 保存Person
	 * @param person
	 */
	public void setPerson(Person person){
		try {
			mService.setPerson(person);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 保存Person
	 * @param person
	 */
	public void setPersons(List<Person> persons){
		try {
			mService.setPersons(persons);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取Person
	 */
	public List<Person> getPersons(){
		try {
			return mService.getPersons();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return null;
	}

}
