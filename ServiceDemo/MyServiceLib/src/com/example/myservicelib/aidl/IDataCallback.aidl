package com.example.myservicelib.aidl;

interface IDataCallback {

	void onNotify(int module, String parcel);
}
