/**
 * 
 */
package com.car.tboxaudio.data;

/**
 * @author YC2
 * @time 2017-11-15 下午12:22:20
 * TODO:
 */
public interface C {
	
	int PCM_CARD = 3;
	int PCM_DEVICE = 0;
	
	interface PcmChannals{
		/**单声道*/
		int CHANNEL_MONO = 1;
		/**立体声*/
		int CHANNEL_STEREO = 2;
	}
	
	interface PcmBits{
		int BIT_16 = 16;
		int BIT_24 = 24;
		int BIT_32 = 32;
	}
	
	/**
	 * 扬声器请求
	 * 0x0=No Request；0x1=Request type 1； 0x2=Reserved；0x3=Reserve
	 */
	interface TBoxAudioReq{
		int NO_REQUEST = 0x0;
		int REQUEST_TYPE1 = 0x1;
		int RESERVED = 0x2;
		int RESERVE = 0x3;
	}
	
	/**
	 * 一键呼通话状态 
	 * 0x0 = IDLE；0x1 = On the phone；
	 * 0x2 = Calling；0x3 = being Called(somebody call)；
	 * 0x4 =Call Ended；0x5 = Reserve；0x6 = Reserved；0x7 = Invalid
	 */
	interface TBoxBCallStatus{
		int IDLE = 0x0;
		int ON_PHONE = 0x1;
		int CALLING = 0x2;
		int BEING_CALLED = 0x3;
		int CALL_ENDED = 0x4;
		int RESERVE = 0x5;
		int RESERVED = 0x6;
		int INVALID = 0x7;
	}
	
	/**
	 * 通话状态
	 * 0x0 = IDLE；0x1 = On the phone；
	 * 0x2 = Calling；0x3 = being Called(somebody call)；
	 * 0x4 =Call Ended；0x5 = Reserve；0x6 = Reserved；0x7 = Invalid
	 */
	interface TBoxCallStatus{
		int IDLE = 0x0;
		int ON_PHONE = 0x1;
		int CALLING = 0x2;
		int BEING_CALLED = 0x3;
		int CALL_ENDED = 0x4;
		int RESERVE = 0x5;
		int RESERVED = 0x6;
		int INVALID = 0x7;
	}
}
