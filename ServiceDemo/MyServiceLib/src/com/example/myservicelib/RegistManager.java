package com.example.myservicelib;

import java.util.List;
import java.util.ArrayList;

import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;

public class RegistManager {

	public static final String TAG = "RegistManager";
	private RegistManager()
	{
		
	}
	
	private static RegistManager mInstance;
	public static RegistManager getInstance()
	{
		if(mInstance == null) mInstance = new RegistManager();
		return mInstance;
	}
	
	private List<IRadioInfoChangedListener> radioInfoChangedListeners = null;
	private List<IBTInfoChangedListener> btInfoChangedListeners = null;
	private List<ISettingsInfoChangedListener> SettingsInfoChangedListeners = null;
	private List<ISystemInfoChangedListener> mSystemInfoChangedListeners = null;
	protected boolean addRadioInfoChangedListener(IRadioInfoChangedListener listener)
	{
		if(listener == null) return false;
		if(radioInfoChangedListeners == null)
		{
			radioInfoChangedListeners = new ArrayList<IRadioInfoChangedListener>();
			radioInfoChangedListeners.clear();
		}
		
		if(radioInfoChangedListeners.contains(listener))
		{
			Log.i(TAG, "already regist this radio listener, so skip it");
		}
		else
		{
			radioInfoChangedListeners.add(listener);
			if(radioInfoChangedListeners.size() == 1)
			{
				//add first, then regist
				registRadioInfoChangedListener();
			}
		}
		
		return true;
	}
	protected boolean removeRadioInfoChangedListener(IRadioInfoChangedListener listener)
	{
		if(radioInfoChangedListeners != null)
		{
			for(int i=0; i<radioInfoChangedListeners.size(); i++)
			{
				if(radioInfoChangedListeners.get(i) == listener)
				{
					radioInfoChangedListeners.remove(i);
					if(radioInfoChangedListeners.size() == 0)
					{
						unregistRadioInfoChangedListener();
					}
					return true;
				}
			}
		}
		return false;
	}
	protected boolean addBTInfoChangedListener(IBTInfoChangedListener listener)
	{
		
		if(listener == null) return false;
		if(btInfoChangedListeners == null)
		{
			btInfoChangedListeners = new ArrayList<IBTInfoChangedListener>();
			btInfoChangedListeners.clear();
		}
		
		if(btInfoChangedListeners.contains(listener))
		{
			Log.i(TAG, "already regist this bt listener, so skip it");
		}
		else
		{
			btInfoChangedListeners.add(listener);
			if(btInfoChangedListeners.size() == 1)
			{
				//add first, then regist
				registBTInfoChangedListener();
			}
		}
		
		return true;
	}
	protected boolean removeBTInfoChangedListener(IBTInfoChangedListener listener)
	{
		
		if(btInfoChangedListeners != null)
		{
			for(int i=0; i<btInfoChangedListeners.size(); i++)
			{
				
				if(btInfoChangedListeners.get(i) == listener)
				{
					btInfoChangedListeners.remove(i);
					if(btInfoChangedListeners.size() == 0)
					{
						btInfoChangedListeners = null;
						unregistBTInfoChangedListener();
					}
					return true;
				}
			}
		}
		return false;
	}
	protected boolean addSettingsInfoChangedListener(ISettingsInfoChangedListener listener)
	{
		if(listener == null) return false;
		if(SettingsInfoChangedListeners == null)
		{
			SettingsInfoChangedListeners = new ArrayList<ISettingsInfoChangedListener>();
			SettingsInfoChangedListeners.clear();
		}
		
		if(SettingsInfoChangedListeners.contains(listener))
		{
			Log.i(TAG, "already regist this settings listener, so skip it");
		}
		else
		{
			SettingsInfoChangedListeners.add(listener);
			if(SettingsInfoChangedListeners.size() == 1)
			{
				//add first, then regist
				registSettingsInfoChangedListener();
			}
		}
		
		return true;
	}
	
	protected boolean removeSettingsInfoChangedListener(ISettingsInfoChangedListener listener)
	{
		if(SettingsInfoChangedListeners != null)
		{
			for(int i=0; i<SettingsInfoChangedListeners.size(); i++)
			{
				
				if(SettingsInfoChangedListeners.get(i) == listener)
				{
					SettingsInfoChangedListeners.remove(i);
					if(SettingsInfoChangedListeners.size() == 0)
					{
						SettingsInfoChangedListeners = null;
						unregistSettingsInfoChangedListener();
					}
					return true;
				}
			}
		}
		return false;
	}
	protected boolean addSystemInfoChangedListener(ISystemInfoChangedListener listener)
	{
		if(listener == null) return false;
		if(mSystemInfoChangedListeners == null)
		{
			mSystemInfoChangedListeners = new ArrayList<ISystemInfoChangedListener>();
			mSystemInfoChangedListeners.clear();
		}
		
		if(mSystemInfoChangedListeners.contains(listener))
		{
			Log.i(TAG, "already regist this system listener, so skip it");
		}
		else
		{
			mSystemInfoChangedListeners.add(listener);
			if(mSystemInfoChangedListeners.size() == 1)
			{
				//add first, then regist
				registSystemInfoChangedListener();
			}
		}
		
		return true;
	}
	
	protected boolean removeSystemInfoChangedListener(ISystemInfoChangedListener listener)
	{
		if(mSystemInfoChangedListeners != null)
		{
			for(int i=0; i<mSystemInfoChangedListeners.size(); i++)
			{
				
				if(mSystemInfoChangedListeners.get(i) == listener)
				{
					mSystemInfoChangedListeners.remove(i);
					if(mSystemInfoChangedListeners.size() == 0)
					{
						mSystemInfoChangedListeners = null;
						unregistSystemInfoChangedListener();
					}
					return true;
				}
			}
		}
		return false;
	}
	DataChangedListener radioDataChangedListener = null;
	//only regist once from mcuserver
	private void registRadioInfoChangedListener()
	{
		if(radioDataChangedListener == null)
		{
			radioDataChangedListener = new DataChangedListener();
		}
		McuManagerService.getInstance().registDataChangedListener(RadioInfo.RADIO_DOMAIN, radioDataChangedListener);
	}
	
	private void unregistRadioInfoChangedListener()
	{
		if(radioDataChangedListener != null)
		{
			McuManagerService.getInstance().unregistDataChangedListener(RadioInfo.RADIO_DOMAIN, radioDataChangedListener);
		}
	}
	
	DataChangedListener btDataChangedListener = null;
	//only regist once from mcuserver
	private void registBTInfoChangedListener()
	{
		if(btDataChangedListener == null)
		{
			btDataChangedListener = new DataChangedListener();
		}
		McuManagerService.getInstance().registDataChangedListener(BTInfo.BT_DOMAIN, btDataChangedListener);
	}
	private void unregistBTInfoChangedListener()
	{
		if(btDataChangedListener != null)
		{
			McuManagerService.getInstance().unregistDataChangedListener(BTInfo.BT_DOMAIN, btDataChangedListener);
		}
	}
	
	
	DataChangedListener settingsDataChangedListener = null;
	//only regist once from mcuserver
	private void registSettingsInfoChangedListener()
	{
		if(settingsDataChangedListener == null)
		{
			settingsDataChangedListener = new DataChangedListener();
		}
		McuManagerService.getInstance().registDataChangedListener(SettingsInfo.SETTINGS_DOMAIN, settingsDataChangedListener);
	}
	private void unregistSettingsInfoChangedListener()
	{
		if(settingsDataChangedListener != null)
		{
			McuManagerService.getInstance().unregistDataChangedListener(SettingsInfo.SETTINGS_DOMAIN, settingsDataChangedListener);
		}
	}
	
	DataChangedListener systemDataChangedListener = null;
	//only regist once from mcuserver
	private void registSystemInfoChangedListener()
	{
		if(systemDataChangedListener == null)
		{
			systemDataChangedListener = new DataChangedListener();
		}
		McuManagerService.getInstance().registDataChangedListener(SystemInfo.SYSTEM_DOMAIN, systemDataChangedListener);
	}
	private void unregistSystemInfoChangedListener()
	{
		if(systemDataChangedListener != null)
		{
			McuManagerService.getInstance().unregistDataChangedListener(SystemInfo.SYSTEM_DOMAIN, systemDataChangedListener);
		}
	}
	
	
	private void doNotify(int msg, int ext0, int ext1, Parcel parcel)
	{
		if(msg == RadioInfo.RADIO_DOMAIN)
		{
			RadioInfo radio = RadioInfo.CREATOR.createFromParcel(parcel);
			if(radio != null)
			{
				for(int i=0; radioInfoChangedListeners != null && i< radioInfoChangedListeners.size(); i++)
				{
					radioInfoChangedListeners.get(i).notify(null, radio);
				}
			}
		}
		else if(msg == BTInfo.BT_DOMAIN)
		{
			BTInfo bt = BTInfo.CREATOR.createFromParcel(parcel);
			if(bt != null)
			{
				for(int i=0; btInfoChangedListeners != null && i<btInfoChangedListeners.size(); i++)
				{
					btInfoChangedListeners.get(i).notify(null, bt);
				}
			}
		}
		else if(msg == SettingsInfo.SETTINGS_DOMAIN)
		{
			SettingsInfo settings = SettingsInfo.CREATOR.createFromParcel(parcel);
			if(settings != null)
			{
				for(int i=0; SettingsInfoChangedListeners != null && i<SettingsInfoChangedListeners.size(); i++)
				{
					SettingsInfoChangedListeners.get(i).notify(null, settings);
				}
			}
		}
	}
	
	class DataChangedListener extends IDataChangedListener.Stub
	{
		@Override
		public void notify(int msg, int ext0, int ext1, Parcel parcel)
				throws RemoteException {
			// TODO Auto-generated method stub
			doNotify(msg, ext0, ext1, parcel);
		}
	}
	
	public interface IRadioInfoChangedListener {
		void notify(int[] changeCMDs, RadioInfo radio);
	}
	public interface IBTInfoChangedListener {
		void notify(int[] changeCMDs, BTInfo btinfo);
	}
	public interface ISettingsInfoChangedListener {
		void notify(int[] changeCMDs, SettingsInfo settings);
	}
	public interface ISystemInfoChangedListener {
		void notify(int[] changeCMDs, SystemInfo settings);
	}
	
}
