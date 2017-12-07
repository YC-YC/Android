/**
 * 
 */
package com.car.tboxaudio.service;

import android.app.Service;
import android.content.Intent;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.car.tboxaudio.audio.AudioIn;
import com.car.tboxaudio.audio.AudioOut;
import com.car.tboxaudio.data.C;
import com.incall.proxy.can.CanManager;
import com.incall.proxy.can.CanManager.CanChangedListener;
import com.incall.proxy.can.TboxInfo;

/**
 * @author YC2
 * @time 2017-11-16 上午10:00:55
 * TODO:
 */
public class TBoxAudioService extends Service {

	private static final String TAG = "TBoxAudioService";
	private TboxInfo mTBoxInfo;
	private AudioIn mAudioIn = new AudioIn();
	private AudioOut mAudioOut = new AudioOut();

	@Override
	public IBinder onBind(Intent arg0) {
		return new Binder();
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Log("onCreate");
		mTBoxInfo = CanManager.getInstance().getTboxInfo();
		if (mTBoxInfo == null){
			Log("null TBoxInfo");
		}
		else{
			Log("getTBoxinfo = " + mTBoxInfo.toString());
		}
//		onInit();
		CanManager.getInstance().addCanChangedListener(mCanChangedListener);
	}

	/**
	 * 
	 */
	private void onInit() {
		if (isTBoxReqAudio(mTBoxInfo)){
			startPlay();
		}
		if (isBCallCalling(mTBoxInfo) || isCallCalling(mTBoxInfo)){
			startRecord();
		}
	}
	
	private void startRecord(){
		mAudioIn.initAudioIn(8000, AudioFormat.CHANNEL_IN_STEREO, AudioFormat.ENCODING_PCM_16BIT);
		mAudioIn.startRecord();
	}
	
	private void stopRecord(){
		mAudioIn.stopRecord();
	}
	
	private void startPlay(){
		mAudioOut.initAudioOut(AudioManager.STREAM_MUSIC, 44100, AudioFormat.CHANNEL_OUT_STEREO, AudioFormat.ENCODING_PCM_16BIT);
		mAudioOut.startPlay();
	}
	
	private void stopPlay(){
		mAudioOut.stopPlay();
	}
	
	private boolean isTBoxReqAudio(TboxInfo tboxInfo){
		return (tboxInfo.getAudioRequest() == C.TBoxAudioReq.REQUEST_TYPE1);
	}
	
	private boolean isBCallCalling(TboxInfo tboxInfo){
		return (tboxInfo.getbCallStatus() == C.TBoxBCallStatus.CALLING
				|| tboxInfo.getbCallStatus() == C.TBoxBCallStatus.ON_PHONE
				|| tboxInfo.getbCallStatus() == C.TBoxBCallStatus.BEING_CALLED);
	}
	
	private boolean isCallCalling(TboxInfo tboxInfo){
		return (tboxInfo.getCallStatus() == C.TBoxCallStatus.CALLING
				|| tboxInfo.getCallStatus() == C.TBoxCallStatus.ON_PHONE
				|| tboxInfo.getCallStatus() == C.TBoxCallStatus.BEING_CALLED);
	}

	private final CanChangedListener mCanChangedListener = new CanChangedListener(){

		@Override
		public void onTboxInfoNotify(TboxInfo paramTboxInfo) {
			super.onTboxInfoNotify(paramTboxInfo);
			Log(paramTboxInfo.toString());
//			checkTBoxStatus(mTBoxInfo, paramTboxInfo);
			mTBoxInfo = paramTboxInfo;
		}
	};
	
	private void checkTBoxStatus(TboxInfo newTBoxInfo, TboxInfo oldTboxInfo) {
		if (isTBoxReqAudio(newTBoxInfo)&& !isTBoxReqAudio(oldTboxInfo)){
			startPlay();
		}
		else if (!isTBoxReqAudio(newTBoxInfo)&& isTBoxReqAudio(oldTboxInfo)){
			stopPlay();
		}
		
		if ((isBCallCalling(newTBoxInfo) || isCallCalling(newTBoxInfo))
				&&(!isBCallCalling(oldTboxInfo) && !isCallCalling(oldTboxInfo))){
			startRecord();
		}
		else if ((!isBCallCalling(newTBoxInfo) && !isCallCalling(newTBoxInfo))
				&&(isBCallCalling(oldTboxInfo) || isCallCalling(oldTboxInfo))){
			stopRecord();
		}
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log("onStartCommand");
		flags = START_STICKY;
		return super.onStartCommand(intent, flags, startId);
	}

	private void Log(String msg){
		Log.i(TAG, msg);
	}
}
