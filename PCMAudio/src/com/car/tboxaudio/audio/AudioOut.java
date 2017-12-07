/**
 * 
 */
package com.car.tboxaudio.audio;

import com.car.pcm.PcmJniBC;
import com.car.tboxaudio.data.C;

import android.media.AudioTrack;
import android.os.SystemClock;
import android.util.Log;

/**
 * 音频输出
 * @author YC2
 * @time 2017-11-10 上午9:53:36 TODO:
 */
public class AudioOut {

	private static final String TAG = "TBox_AudioOut";
	private static final int MIN_PCM_BUF_LEN = 2*1024;
	 private static AudioOut mAudioOut;
	 
	private AudioTrack mAudio = null;
	 private boolean bReqPlay = false;
	
	public synchronized static void jniWriteAudioData(byte[]data, int length) {
        if (null != mAudioOut) {
        	mAudioOut.writeAudioData(data, length);
        }
    }
	
	 public AudioOut(){
	 }

	 /**
	  * 初始化音频输出
	  * @param streamType: AudioManager.STREAM_
	  * @param sampleRate: inHZ
	  * @param channal: AudioFormat.CHANNEL_OUT_XX
	  * @param audioFormat: AudioFormat.ENCODING_PCM_XX
	  */
	public void initAudioOut(int streamType, int sampleRate, int channal,
			int audioFormat) {
		int minsz = AudioTrack.getMinBufferSize(sampleRate, channal,
				audioFormat);
		Log("getMinBufferSize = " + minsz);
		if (minsz < MIN_PCM_BUF_LEN){
			minsz = MIN_PCM_BUF_LEN;
		}
		mAudio = new AudioTrack(streamType, sampleRate, channal, audioFormat,
				minsz, AudioTrack.MODE_STREAM);
		try {
			mAudio.play();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 开始播放
	 */
	public void startPlay(){
		mAudioOut = this;
		bReqPlay = true;
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				String name = AudioOut.class.getName().replace('.', '/');
				PcmJniBC.initPcmIn(name);
				int printCount = 0;
				int readCnt = 0;
//				int len = AudioJniBC.openPcmIn(2, C.PCM_DEVICE, C.PcmChannals.CHANNEL_MONO, 44100, C.PcmBits.BIT_16);
				int len = PcmJniBC.openPcmIn(C.PCM_CARD, C.PCM_DEVICE, C.PcmChannals.CHANNEL_MONO, 8000, C.PcmBits.BIT_16);
				if (len > 0){
					while(true){																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																															
						if (!bReqPlay){
							Log("req exit");
							break;
						}
						if ((readCnt = PcmJniBC.readPcmIn()) <= 0){
							Log("read error");
							break;
						}
		                if (printCount > 50){
		                	printCount = 0;
		                	Log("read pcm data lenght = " + readCnt);
		                }
		                else{
		                	printCount++;
		                }
//						sleep(1);
					}
				}
				else{
					Log("len < 0");
				}
				PcmJniBC.stopPcmIn();
				PcmJniBC.deInitPcmIn();
				Log("exit play");
			}
		}).start();
	}
	
	public void writeAudioData(byte[]data, int length) {
        if (null != mAudio) {
        	try {
//        		Log("writeAudioData length = " + length);
            	mAudio.write(data, 0, length);
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
    }
	
	/**
	 * 停止播放
	 */
	public void stopPlay() {
		mAudioOut = null;
		bReqPlay = false;
		if (null != mAudio) {
			try {
				mAudio.stop();
			} catch (Exception e) {
				e.printStackTrace();
			}
			mAudio.release();
			mAudio = null;
		}
	}

	private void Log(String msg) {
		Log.i(TAG, msg);
	}

	private void sleep(long ms) {
		SystemClock.sleep(ms);
	}
}
