/**
 * 
 */
package com.example.myservice;
import java.util.ArrayList;
import java.util.List;

import android.os.RemoteException;
import android.util.Log;

import com.example.myservicelib.aidl.*;
import com.example.myservicelib.bean.Person;

/**
 * @author YC
 * @time 2017-4-20 下午3:11:49
 * TODO:
 */
public class MyService extends IMyService.Stub{

	private List<Person> mPersonList = new ArrayList<Person>();
	
	private static MyService sMyService = new MyService();
	public static MyService getDefault(){
		return sMyService;
	}
	
	private MyService(){
		
	}
	
	private int mValue;
	
	@Override
	public int getVal() throws RemoteException {
		return mValue;
	}
	
	@Override
	public void setVal(int arg0, long arg1, char arg2, boolean arg3, String arg4)
			throws RemoteException {
		Log.i("TestService", "int = " + arg0 + ", long = " + arg1
				+ ", char = " + arg2 + ", boolean = " + arg3 + ", Str = " + arg4);
	}

	@Override
	public List<Person> getPersons() throws RemoteException {
		Log.i("TestService", "getPersons");
		return mPersonList;
	}
	
	@Override
	public void setPersons(List<Person> arg0) throws RemoteException {
		Log.i("TestService", "setPersons");
		for (int i = 0; i < arg0.size(); i++){
			Log.i("TestService", "get = " + arg0.get(i).toString());
		}
	}

	@Override
	public void setPerson(Person arg0) throws RemoteException {
		Log.i("TestService", "setPerson = " + arg0.toString());
		mPersonList.add(arg0);
	}

}
