/**
 * 
 */
package com.yc.networkdemo.activity.socket;

/**
 * @author YC2
 * @time 2017-11-24 下午12:01:35
 * TODO:
 */
public interface C {

	interface MsgType{
		int NORMAL = 0;
		int ACK = 1;
		int NCK = 2;
	}
	
	interface HeaderErrorCode{
		int NO_ERROR = 0;
		int LENGTH_ERROR = 1;
		int CRC_ERROR = 2;
	}
	
	interface BodyErrorCode{
		int NO_ERROR = 0;
		int VERSION_ERROR = 1;
		int DECRYPTION_ERROR = 2;
		int TID_ERROR = 3;
		int SID_ERROR = 4;
		int HEADER_ERROR = 5;
		int BODY_ERROR = 6;
		int UNKNOWN_ERROR = 15;
	}
	
	interface Hu2TBoxCmd{
		int CHECK_WLAN = 0x00;
		int CHECK_MOBILE_DATA = 0x01;
		int SET_MOBILE_DATA = 0x02;
		int CHECK_WIFI_STATE = 0x20;
		int SET_WIFI_STATE = 0x21;
		int CHECK_WIFI_SSID = 0x22;
		int SET_WIFI_SSID = 0x23;
		int CHECK_WIFI_PSW = 0x24;
		int SET_WIFI_PSW = 0x25;
		int CHECK_CONNECTION_COUNT = 0x26;
		int TBOX_SELF_CHECK = 0x30;
		int CHECK_ID = 0x31;
		
	}
	
	interface TBox2HuCmd{
		int CHECK_WLAN = 0x80;
		int CHECK_MOBILE_DATA = 0x81;
		int SET_MOBILE_DATA = 0x82;
		int CHECK_WIFI_STATE = 0xA0;
		int SET_WIFI_STATE = 0xA1;
		int CHECK_WIFI_SSID = 0xA2;
		int SET_WIFI_SSID = 0xA3;
		int CHECK_WIFI_PSW = 0xA4;
		int SET_WIFI_PSW = 0xA5;
		int CHECK_CONNECTION_COUNT = 0xA6;
		int TBOX_SELF_CHECK = 0xB0;
		int CHECK_ID = 0xB1;
	}
	
}
